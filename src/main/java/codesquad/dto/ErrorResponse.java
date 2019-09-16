package codesquad.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
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

    @Override
    public String toString() {
        return "ErrorResponse(" +
                "status=" + status +
                ", errors=" + errors +
                ", field='" + field + '\'' +
                ')';
    }
}
