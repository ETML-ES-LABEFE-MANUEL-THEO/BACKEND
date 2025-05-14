package ch.zucchinit.zauction.Auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class AuthDTO {
    public record UserDetails(String firstName, String lastName, BigDecimal balance) {}
    public record UserRegister(
        @NotNull(message = "Le prénom est obligatoire")
        @Size(min = 1, max = 30, message = "Le prénom doit contenir entre 1 et 30 caractères")
        String firstName,

        @NotNull(message = "Le nom est obligatoire")
        @Size(min = 1, max = 30, message = "Le nom doit contenir entre 1 et 30 caractères")
        String lastName,

        @NotNull(message = "L'email est obligatoire")
        @Email(message = "Le format de l'email est invalide")
        @CustomUserValidators.EmailUnique
        String email,

        @NotNull(message = "Le mot de passe est obligatoire")
        @Size(min = 8, max = 30, message = "Le mot de passe doit contenir entre 8 et 30 caractères")
        String password
    ){}

    public record UserLogin(String email, String password) {}
}
