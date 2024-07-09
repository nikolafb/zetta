package zetta.exception;

import org.springframework.http.HttpStatus;

public class BusinessExceptionFactory {

    public static BusinessException entityNotFoundException(String error, String message) {
        // Can be added logging here
        return new BusinessException(error, HttpStatus.NOT_FOUND, message);
    }

    public static BusinessException badRequestException(String error, String message) {

        return new BusinessException(error, HttpStatus.BAD_REQUEST, message);
    }

    public static BusinessException internalException(String error, String message) {

        return new BusinessException(error, HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

}
