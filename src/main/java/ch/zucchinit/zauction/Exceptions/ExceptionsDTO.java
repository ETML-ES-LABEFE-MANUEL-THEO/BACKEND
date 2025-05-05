package ch.zucchinit.zauction.Exceptions;

import java.util.List;

public class ExceptionsDTO {
    public record ValidationError(List<String> path, String error) {}
    public record GenericError(String error) {}
}
