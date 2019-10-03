package codesquad.exception;

import codesquad.utils.ExceptionMessages;

public class NotFoundCategoryException extends RuntimeException {

    public NotFoundCategoryException() {
        super(ExceptionMessages.NOT_FOUND_CATEGORY);
    }

}
