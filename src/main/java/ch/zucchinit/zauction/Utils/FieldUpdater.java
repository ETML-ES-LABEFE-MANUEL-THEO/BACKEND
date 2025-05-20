package ch.zucchinit.zauction.Utils;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class FieldUpdater {
    public static <T> void updateIfChanged(Supplier<T> getter, Consumer<T> setter, T newValue) {
        if (!Objects.equals(getter.get(), newValue)) {
            setter.accept(newValue);
        }
    }
}
