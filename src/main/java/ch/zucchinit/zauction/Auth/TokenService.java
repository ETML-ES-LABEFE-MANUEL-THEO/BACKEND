package ch.zucchinit.zauction.Auth;

import ch.zucchinit.zauction.Security.TokenAuthenticationFilter;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;

import static ch.zucchinit.zauction.Security.TokenAuthenticationFilter.authCookieName;

@Service
public class TokenService {
    @Value("${token.validity.minutes}")
    public final Integer tokenMinutesValidity = 15;
    private final TokenRepository tokenRepository;

    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public Cookie getCookieToken(String tokenValue) {
        Cookie cookie = new Cookie(TokenAuthenticationFilter.authCookieName, tokenValue);
        cookie.setMaxAge(tokenMinutesValidity * 60);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }

    public Cookie extractCookieToken(Cookie[] cookies) {
        if (cookies == null) return null;

        return Arrays.stream(cookies)
                .filter(c -> c.getName().equals(authCookieName))
                .findFirst()
                .orElse(null);
    }

    public Cookie deleteCookieToken() {
        Cookie cookie = new Cookie(TokenAuthenticationFilter.authCookieName, null);
        cookie.setMaxAge(0);

        return cookie;
    }

    public Token registerToken(User user) {
        LocalDateTime expireDate = LocalDateTime.now().plusMinutes(tokenMinutesValidity);
        Token token = new Token(expireDate, user);

        return tokenRepository.save(token);
    }

    public Token findTokenById(String tokenValue) {
        return tokenRepository.findById(tokenValue).orElse(null);
    }

    public void unregisterToken(Token token) {
        token.setExpired(true);
        tokenRepository.save(token);
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void purgeExpiredTokens() {
        tokenRepository.deleteByExpireDateBefore(LocalDateTime.now());
    }
}
