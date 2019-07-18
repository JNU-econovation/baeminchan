package codesquad.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundAccountException extends RuntimeException {

    public NotFoundAccountException() {
        super("아이디가 존재하지 않습니다.");
    }

    public NotFoundAccountException(String message) {
        super(message);
    }
}
