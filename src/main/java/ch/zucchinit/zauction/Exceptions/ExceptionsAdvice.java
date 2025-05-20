package ch.zucchinit.zauction.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.*;

@ControllerAdvice
public class ExceptionsAdvice {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Void> userNotAuthorizedHandler() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler(ResourceNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    void resourceNotFoundHandler() {}

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<List<ExceptionsDTO.ValidationError>> validationErrorHandler(MethodArgumentNotValidException ex) {
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

        List<ExceptionsDTO.ValidationError> dtoErrors = errors.entrySet().stream().map(
                err -> new ExceptionsDTO.ValidationError(err.getValue(), err.getKey())
        ).toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dtoErrors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> unknownErrorHandler(Exception ex) {
        System.out.print(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
