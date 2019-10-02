package codesquad.sequrity;

import codesquad.domain.Account;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    public static final String SESSIONED_USER = "sessionedUser";

    public static boolean isLoginUser(NativeWebRequest webRequest) {
        Object loginedUser = webRequest.getAttribute(SESSIONED_USER, WebRequest.SCOPE_SESSION);
        return loginedUser != null;
    }

    public static Account getUserFromSession(NativeWebRequest webRequest) {
        if (!isLoginUser(webRequest)) {
            return null;
        }
        return (Account) webRequest.getAttribute(SESSIONED_USER, WebRequest.SCOPE_SESSION);
    }

    public static boolean isLoginUser(HttpSession session) {
        Object sessionedUser = session.getAttribute(SESSIONED_USER);
        if (sessionedUser == null) {
            return false;
        }
        return true;
    }

    public static Account getUserFromSession(HttpSession session) {
        if (!isLoginUser(session)) {
            return null;
        }

        return (Account) session.getAttribute(SESSIONED_USER);
    }

}
