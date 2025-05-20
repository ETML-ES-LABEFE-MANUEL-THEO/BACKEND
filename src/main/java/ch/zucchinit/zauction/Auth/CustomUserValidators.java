package ch.zucchinit.zauction.Auth;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Component
public class CustomUserValidators {
    private final UserRepository userRepository;

    public CustomUserValidators(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public class EmailUniqueValidator implements ConstraintValidator<EmailUnique, String> {
        @Override
        public boolean isValid(String email, ConstraintValidatorContext context) {
            return email != null && !userRepository.existsByEmail(email);
        }
    }

    @Documented
    @Constraint(validatedBy = EmailUniqueValidator.class)
    @Target({ ElementType.FIELD })
    @Retention(RetentionPolicy.RUNTIME)
    public @interface EmailUnique {
        String message() default "Un compte avec cet email existe déjà";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
    }
}
