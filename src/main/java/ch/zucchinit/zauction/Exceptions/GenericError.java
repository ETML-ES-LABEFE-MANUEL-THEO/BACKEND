package ch.zucchinit.zauction.Exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GenericError extends RuntimeException {
    private final HttpStatus httpStatus;
    private final String error;

    public GenericError(HttpStatus httpStatus, String error) {
        super();
        this.httpStatus = httpStatus;
        this.error = error;
    }
}
