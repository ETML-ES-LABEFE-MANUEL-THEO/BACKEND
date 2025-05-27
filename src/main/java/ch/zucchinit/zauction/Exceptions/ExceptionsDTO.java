package ch.zucchinit.zauction.Exceptions;

import java.util.List;
import java.util.Map;

public class ExceptionsDTO {
    public record ValidationError(List<String> fields, String message, Map<String, Object> details) {
        public ValidationError(List<String> fields, String message) { this(fields, message, Map.of()); }
        public ValidationError(String field, String message) { this(List.of(field), message); }
        public ValidationError(String field, String message, Map<String, Object> details) { this(List.of(field), message, details); }
    }
    public record GenericError(String error) {}
}
