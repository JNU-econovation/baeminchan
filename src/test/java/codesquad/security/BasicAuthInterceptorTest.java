package codesquad.security;

import codesquad.domain.member.Member;
import codesquad.domain.member.MemberLoginDto;
import codesquad.service.MemberService;
import codesquad.util.HttpSessionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BasicAuthInterceptorTest {
    @Mock
    private MemberService memberService;

    @InjectMocks
    private BasicAuthInterceptor basicAuthInterceptor;

    @Test
    public void preHandle_로그인_성공() throws Exception {
        String memberId = "esp2ar0@gmail.com";
        String password = "q12345678";
        MockHttpServletRequest request = basicAuthHttpRequest(memberId, password);
        Member loginMember = Member.builder()
                .memberId(memberId)
                .password(password)
                .name("창환")
                .cell1("010")
                .cell2("1111")
                .cell3("2222")
                .build();

        when(memberService.login(new MemberLoginDto(memberId, password))).thenReturn(loginMember);

        basicAuthInterceptor.preHandle(request, null, null);
        assertThat(request.getSession().getAttribute(HttpSessionUtils.MEMBER_SESSION_KEY)).isEqualTo(loginMember);
    }

    private MockHttpServletRequest basicAuthHttpRequest(String userId, String password) {
        String encodedBasicAuth = Base64.getEncoder()
                .encodeToString(String.format("%s:%s", userId, password).getBytes());
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Basic " + encodedBasicAuth);
        return request;
    }
}