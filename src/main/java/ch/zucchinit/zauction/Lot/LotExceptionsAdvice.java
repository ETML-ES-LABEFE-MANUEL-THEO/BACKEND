package ch.zucchinit.zauction.Lot;

import ch.zucchinit.zauction.Exceptions.ExceptionsDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class LotExceptionsAdvice {

    @ExceptionHandler({
            LotExceptions.NotPublishedException.class,
            LotExceptions.AlreadyPublishedException.class,
            LotExceptions.NotClosedException.class,
            LotExceptions.AlreadyClosedException.class,
            LotExceptions.AlreadyTransferredException.class
    })
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionsDTO.GenericError handleLotExceptionsConflicts(Exception ex) {
        return new ExceptionsDTO.GenericError(ex.getMessage());
    }

}
