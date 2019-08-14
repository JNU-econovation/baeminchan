package codesquad.exception;

import codesquad.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionControllerAdvice {
    Logger log = LoggerFactory.getLogger(GlobalExceptionControllerAdvice.class);

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.info("handleMethodArgumentNotValidException : {}", e);

        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value());

        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        errors.forEach(error -> response.addErrorMessage(error.getDefaultMessage()));

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UnMatchedCheckingPasswordException.class)
    public ResponseEntity<ErrorResponse> handleUnMatchedCheckingPasswordException(UnMatchedCheckingPasswordException e) {
        log.error("handleUnMatchedCheckingPasswordException : {}", e);

        return makeResponseEntity(HttpStatus.BAD_REQUEST, e);
    }

    @ExceptionHandler(value = DuplicatedAccountException.class)
    public ResponseEntity<ErrorResponse> handleDuplicatedAccountException(DuplicatedAccountException e) {
        log.error("handleDuplicatedAccountException : {}", e);

        return makeResponseEntity(HttpStatus.BAD_REQUEST, e);
    }

    @ExceptionHandler(value = NotFoundAccountException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundAccountException(NotFoundAccountException e) {
        log.error("handleNotFoundAccountException : {}", e);

        return makeResponseEntity(HttpStatus.NOT_FOUND, e);
    }

    @ExceptionHandler(value = UnAuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleUnAuthenticationException(UnAuthenticationException e) {
        log.error("handleUnAuthenticationException : {}", e);

        return makeResponseEntity(HttpStatus.FORBIDDEN, e);
    }

    private ResponseEntity<ErrorResponse> makeResponseEntity(HttpStatus status, Exception e) {
        ErrorResponse response = new ErrorResponse(status.value());
        response.addErrorMessage(e.getMessage());

        log.debug("check : {}", e);

        return new ResponseEntity<>(response, status);
    }
}
