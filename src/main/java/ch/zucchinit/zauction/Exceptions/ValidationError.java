package ch.zucchinit.zauction.Exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class ValidationError extends RuntimeException {
    private final List<ExceptionsDTO.ValidationError> validationErrors;

    public ValidationError(List<ExceptionsDTO.ValidationError> validationErrors) {
        super();
        this.validationErrors = validationErrors;
    }

}
