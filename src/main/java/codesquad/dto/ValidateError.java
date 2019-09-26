package codesquad.dto;

public class ValidateError {
    private String fieldName;
    private String errorMessage;

    public ValidateError(String fieldName, String errorMessage) {
        this.fieldName = fieldName;
        this.errorMessage = errorMessage;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String toString() {
        return "ValidationError [fieldName=" + fieldName + ", errorMessage=" + errorMessage + "]";
    }
}
