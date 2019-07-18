package codesquad.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UnMatchedCheckingPasswordException extends RuntimeException {

    public UnMatchedCheckingPasswordException() {
        super("비밀번호가 일치하지 않습니다.");
    }

    public UnMatchedCheckingPasswordException(String message) {
        super(message);
    }
}
