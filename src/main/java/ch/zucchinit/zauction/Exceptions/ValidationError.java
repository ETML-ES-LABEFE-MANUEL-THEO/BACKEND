package ch.zucchinit.zauction.Exceptions;

import java.util.List;

public class ValidationError extends RuntimeException {
    private final List<ExceptionsDTO.ValidationError> validationErrors;

    public ValidationError(List<ExceptionsDTO.ValidationError> validationErrors) {
        super();
        this.validationErrors = validationErrors;
    }

    public List<ExceptionsDTO.ValidationError> getValidationErrors() {
        return validationErrors;
    }
}
