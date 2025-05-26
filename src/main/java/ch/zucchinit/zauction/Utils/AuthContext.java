package ch.zucchinit.zauction.Utils;

import ch.zucchinit.zauction.Auth.Token;
import ch.zucchinit.zauction.Auth.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthContext {
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static User getUserFromContext() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static Token getTokenFromContext() {
        return (Token) SecurityContextHolder.getContext().getAuthentication().getDetails();
    }
}
