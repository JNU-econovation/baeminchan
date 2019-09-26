package codesquad.response;

import codesquad.dto.ErrorResponse;
import codesquad.dto.ValidateError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class ResponseGenerator {

    public static <T>ResponseEntity<T> generateResponseEntity(T body, HttpStatus httpStatus) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<T>(body, headers, httpStatus);
    }

    public static <T>ResponseEntity<T> generateResponseEntity(HttpStatus httpStatus) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<T>(headers, httpStatus);
    }

    public static ResponseEntity<ErrorResponse> generateErrorResponseEntity(HttpStatus status, Exception e) {
        ErrorResponse response = new ErrorResponse(status.value());
        response.addErrorMessage(new ValidateError("" ,e.getMessage()));

        return new ResponseEntity<>(response, status);
    }
}
