package ch.zucchinit.zauction.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;

@RestControllerAdvice
public class ExceptionsAdvice {

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    void userNotAuthorizedHandler() {}

    @ExceptionHandler(ResourceNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    void resourceNotFoundHandler() {}

    @ExceptionHandler(ValidationError.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    List<ExceptionsDTO.ValidationError> validationErrorHandler(ValidationError ex) {
        return ex.getValidationErrors();
    }

    @ExceptionHandler(GenericError.class)
    ResponseEntity<ExceptionsDTO.GenericError> genericErrorHandler(GenericError ex) {
        return new ResponseEntity<>(new ExceptionsDTO.GenericError(ex.getError()), ex.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    List<ExceptionsDTO.ValidationError> springValidationErrorHandler(MethodArgumentNotValidException ex) {
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

        return errors.entrySet().stream().map(
                err -> new ExceptionsDTO.ValidationError(err.getValue(), err.getKey())
        ).toList();
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ExceptionsDTO.ValidationError> handleMissingServletRequestParameter(MissingServletRequestParameterException ex) {
        String field = ex.getParameterName();
        String message = "Le paramètre requis '" + field + "' est manquant";
        return List.of(new ExceptionsDTO.ValidationError(List.of(field), message));
    }
}
