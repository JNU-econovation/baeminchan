package codesquad.exception;

import codesquad.utils.ExceptionMessages;

public class UnAuthenticationException extends RuntimeException {

    public UnAuthenticationException() {
        super(ExceptionMessages.WRONG_PASSWORD);
    }

    public UnAuthenticationException(String message) {
        super(message);
    }
}
