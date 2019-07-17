package codesquad.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class UnAuthenticationException extends RuntimeException {

    public UnAuthenticationException() {
        super();
    }

    public UnAuthenticationException(String message) {
        super(message);
    }
}
