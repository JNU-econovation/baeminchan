package codesquad.exception;

import codesquad.utils.ExceptionMessages;

public class UnMatchedCheckingPasswordException extends RuntimeException {

    public UnMatchedCheckingPasswordException() {
        super(ExceptionMessages.UNMATCHED_PASSWORD);
    }

    public UnMatchedCheckingPasswordException(String message) {
        super(message);
    }
}
