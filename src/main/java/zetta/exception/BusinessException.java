package zetta.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BusinessException extends RuntimeException{

    private final ErrorDetails errorDetails;

    private final HttpStatus httpStatus;


    public BusinessException(String error, HttpStatus httpStatus, String message) {
        super(message);
        this.errorDetails = new ErrorDetails(message, error);
        this.httpStatus = httpStatus;
    }


    public ResponseEntity<ErrorDetails> getResponseEntity() {
        return ResponseEntity.status(httpStatus).body(errorDetails);
    }
}
