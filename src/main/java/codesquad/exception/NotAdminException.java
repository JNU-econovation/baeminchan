package codesquad.exception;

import codesquad.utils.ExceptionMessages;

public class NotAdminException extends RuntimeException {

    public NotAdminException() {
        super(ExceptionMessages.NOT_ADMIN_ACCOUNT);
    }
}
