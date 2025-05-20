package ch.zucchinit.zauction.Auth;

import jakarta.validation.constraints.*;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;

public class AuthDTO {
    public record UserProfile(String firstName, String lastName, BigDecimal balance) {}

    public record UserAccount(
        @Size(min = 1, max = 30, message = "Le prénom doit contenir entre 1 et 30 caractères")
        String firstName,

        @Size(min = 1, max = 30, message = "Le nom doit contenir entre 1 et 30 caractères")
        String lastName,

        String email,

        @Pattern(regexp = "^[\\d\\s+()-]{3,30}$", message = "Format de téléphone invalide")
        String phone,

        @Size(min = 1, max = 200, message = "L'adresse doit contenir entre 1 et 200 caractères")
        String address,

        @Size(min = 1, max = 100, message = "La ville doit contenir entre 1 et 100 caractères")
        String city,

        @Size(min = 1, max = 30, message = "Le code postal doit contenir entre 1 et 30 caractères")
        String zipCode
    ) {}

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
        @Size(min = 12, max = 30, message = "Le mot de passe doit contenir entre 12 et 30 caractères")
        @Pattern(
                regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[^A-Za-z0-9]).{12,30}$",
                message = "Le mot de passe doit contenir au moins une minuscule, une majuscule, un chiffre et un symbole"
        )
        String password
    ){}

    public record UserLogin(
        @NotNull(message = "L'email est obligatoire")
        @Email(message = "Le format de l'email est invalide")
        String email,

        @NotNull(message = "Le mot de passe est obligatoire")
        String password
    ){}

    public record UserResetPassword(
        String oldPassword,

        @NotNull(message = "Le mot de passe est obligatoire")
        @Size(min = 12, max = 30, message = "Le mot de passe doit contenir entre 12 et 30 caractères")
        @Pattern(
                regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[^A-Za-z0-9]).{12,30}$",
                message = "Le mot de passe doit contenir au moins une minuscule, une majuscule, un chiffre et un symbole"
        )
        @Value("#{'${test.myvalue}'.trim()}")
        String newPassword
    ){}
}
