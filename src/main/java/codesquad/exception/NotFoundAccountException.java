package codesquad.exception;

import codesquad.utils.ExceptionMessages;

public class NotFoundAccountException extends RuntimeException {

    public NotFoundAccountException() {
        super(ExceptionMessages.NOT_FOUND_ACCOUNT);
    }

    public NotFoundAccountException(String message) {
        super(message);
    }
}
