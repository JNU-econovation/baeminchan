package codesquad.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ErrorResponse {
    private int status;
    private List<String> errors;

    public ErrorResponse(int status) {
        this.status = status;
        errors = new ArrayList<>();
    }

    public void addErrorMessage(String message) {
        errors.add(message);
    }
}
