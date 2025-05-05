package ch.zucchinit.zauction.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.*;

@ControllerAdvice
public class ExceptionsAdvice {

    @ResponseBody
    @ExceptionHandler(ResourceNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    void resourceNotFoundHandler(ResourceNotFound ex) {}

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    List<ExceptionsDTO.ValidationError> validationErrorHandler(MethodArgumentNotValidException ex) {
        Map<String, List<String>> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String field = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();

            if (errors.containsKey(errorMessage)) {
                errors.get(errorMessage).add(field);
            } else {
                errors.put(errorMessage, List.of(field));
            }
        });

        return errors.entrySet().stream().map(err -> new ExceptionsDTO.ValidationError(err.getValue(), err.getKey())).toList();
    }

    @ResponseBody
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ExceptionsDTO.GenericError unknownErrorHandler(Exception ex) {
        return new ExceptionsDTO.GenericError(ex.getMessage());
    }
}
