package codesquad.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ErrorResponse {
    private int status;
    private List<ValidateError> errors;
    private String field;

    public ErrorResponse(int status) {
        this.status = status;
        errors = new ArrayList<>();
    }

    public void addErrorMessage(String field, ValidateError validateError) {
        this.field = field;
        errors.add(validateError);
    }

    public void addErrorMessage(ValidateError validateError) {
        errors.add(validateError);
    }
}
