package codesquad.interceptor;

import codesquad.domain.Account;
import codesquad.exception.NotAdminException;
import codesquad.sequrity.HttpSessionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class AdminInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("-------------------------log-----------------------------");
        log.info("session info: {}", request.getSession().getAttribute(HttpSessionUtils.SESSIONED_USER));

        Object session = request.getSession().getAttribute(HttpSessionUtils.SESSIONED_USER);
        Account sessionedAccount = (Account) session;

        if (sessionedAccount == null) {
            response.sendRedirect("/member/login");

            return false;
        }

        if (!sessionedAccount.isAdmin()) throw new NotAdminException();

        return super.preHandle(request, response, handler);
    }
}
