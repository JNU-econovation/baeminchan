package codesquad.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class UnAuthenticationException extends RuntimeException {

    public UnAuthenticationException() {
        super("비밀번호가 올바르지 않습니다.");
    }

    public UnAuthenticationException(String message) {
        super(message);
    }
}
