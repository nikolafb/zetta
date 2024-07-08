package zetta.exception;


import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RestControllerExceptionHandler {

    private static final String VALIDATION_FAILED = "Validation failed";

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ExceptionResponseDto> resolveException(EntityNotFoundException exception) {
        List<String> messages = new ArrayList<>(1);
        messages.add(exception.getMessage());

        ExceptionResponseDto exceptionResponseDTO = new ExceptionResponseDto(
                HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND.value(), messages);

        return new ResponseEntity<>(exceptionResponseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<ExceptionResponseDto> resolveException(
            MethodArgumentNotValidException methodArgumentNotValidException) {
        List<String> messages = new ArrayList<>();

        BindingResult bindingResult = methodArgumentNotValidException.getBindingResult();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            messages.add(fieldError.getDefaultMessage());
        }

        ExceptionResponseDto exceptionResponseDTO = new ExceptionResponseDto(
                VALIDATION_FAILED, HttpStatus.BAD_REQUEST.value(), messages);

        return new ResponseEntity<>(exceptionResponseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TechnicalException.class)
    @ResponseBody
    public ResponseEntity<ExceptionResponseDto> resolveException(TechnicalException exception) {
        List<String> messages = new ArrayList<>(1);
        messages.add(exception.getMessage());

        ExceptionResponseDto exceptionResponseDTO = new ExceptionResponseDto(
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR.value(), messages);

        return new ResponseEntity<>(exceptionResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}