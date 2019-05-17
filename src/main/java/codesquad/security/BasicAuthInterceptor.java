package codesquad.security;

import codesquad.domain.member.Member;
import codesquad.domain.member.MemberLoginDto;
import codesquad.service.MemberService;
import codesquad.util.HttpSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;
import java.util.Base64;

public class BasicAuthInterceptor extends HandlerInterceptorAdapter {
    private static final Logger log = LoggerFactory.getLogger(BasicAuthInterceptor.class);

    @Autowired
    private MemberService memberService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String authorization = request.getHeader("Authorization");
        log.debug("Authorization : {}", authorization);
        if (authorization == null || !authorization.startsWith("Basic")) {
            return true;
        }

        String base64Credentials = authorization.substring("Basic".length()).trim();
        String credentials = new String(Base64.getDecoder().decode(base64Credentials), Charset.forName("UTF-8"));
        final String[] values = credentials.split(":", 2);
        log.debug("memberId : {}", values[0]);
        log.debug("password : {}", values[1]);


        Member member = memberService.login(new MemberLoginDto(values[0], values[1]));
        log.debug("Login Success : {}", member);
        request.getSession().setAttribute(HttpSessionUtils.MEMBER_SESSION_KEY, member);
        return true;
    }
}
