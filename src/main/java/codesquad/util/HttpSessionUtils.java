package codesquad.util;

import codesquad.domain.member.Member;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    public static final String MEMBER_SESSION_KEY = "loginedMember";

    public static boolean isLoginMember(HttpSession session) {
        Object sessionedMember = session.getAttribute(MEMBER_SESSION_KEY);
        if (sessionedMember == null) {
            return false;
        }
        return true;
    }

    public static Member getMemberFromSession(HttpSession session) {
        if (!isLoginMember(session)) {
            return null;
        }

        return (Member) session.getAttribute(MEMBER_SESSION_KEY);
    }
}