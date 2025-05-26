package ch.zucchinit.zauction.Auth;

import ch.zucchinit.zauction.APIConfiguration;
import jakarta.servlet.http.Cookie;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;


@Service
public class TokenService {
    private final APIConfiguration apiConfiguration;
    private final TokenRepository tokenRepository;

    public TokenService(APIConfiguration apiConfiguration, TokenRepository tokenRepository) {
        this.apiConfiguration = apiConfiguration;
        this.tokenRepository = tokenRepository;
    }

    public Cookie getCookieToken(String tokenValue) {
        Cookie cookie = new Cookie(apiConfiguration.getCookieName(), tokenValue);
        cookie.setMaxAge(apiConfiguration.getCookieValidity() * 60);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }

    public Cookie extractCookieToken(Cookie[] cookies) {
        if (cookies == null) return null;

        return Arrays.stream(cookies)
                .filter(c -> c.getName().equals(apiConfiguration.getCookieName()))
                .findFirst()
                .orElse(null);
    }

    public Cookie deleteCookieToken() {
        Cookie cookie = new Cookie(apiConfiguration.getCookieName(), null);
        cookie.setMaxAge(0);

        return cookie;
    }

    public Token registerToken(User user) {
        LocalDateTime expireDate = LocalDateTime.now().plusMinutes(apiConfiguration.getCookieValidity());
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
