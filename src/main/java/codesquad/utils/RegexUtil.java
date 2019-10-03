package codesquad.utils;

public class RegexUtil {
    public static final String REGEX_PASSWORD = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,16}$";
    public static final String REGEX_PHONE_NUMBER = "^\\d{3}-\\d{3,4}-\\d{4}$";
}
