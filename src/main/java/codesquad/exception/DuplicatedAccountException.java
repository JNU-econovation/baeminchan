package codesquad.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DuplicatedAccountException extends RuntimeException {

    public DuplicatedAccountException() {
        super("중복된 아이디 입니다.");
    }

    public DuplicatedAccountException(String message) {
        super(message);
    }
}
