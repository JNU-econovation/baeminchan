package codesquad.exception;

import codesquad.utils.ExceptionMessages;

public class DuplicatedAccountException extends RuntimeException {

    public DuplicatedAccountException() {
        super(ExceptionMessages.DUPLICATED_ACCOUNT);
    }

    public DuplicatedAccountException(String message) {
        super(message);
    }
}
