package ch.zucchinit.zauction.Security;

import ch.zucchinit.zauction.Auth.Token;
import ch.zucchinit.zauction.Auth.TokenService;
import ch.zucchinit.zauction.Auth.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class TokenAuthenticationFilter extends OncePerRequestFilter {
    public static final String authCookieName = "Authorization";
    private final TokenService tokenService;

    public TokenAuthenticationFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        Cookie cookieToken = tokenService.extractCookieToken(request.getCookies());

        if (cookieToken != null) {
            Token token = tokenService.findTokenById(cookieToken.getValue());

            if (token != null && token.isValid()) {
                User user = token.getUser();
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken
                    (
                        user, null, user.getAuthorities()
                    );

                authentication.setDetails(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            else {
                Cookie cookie = tokenService.deleteCookieToken();
                response.addCookie(cookie);
            }
        }

        filterChain.doFilter(request, response);
    }
}
