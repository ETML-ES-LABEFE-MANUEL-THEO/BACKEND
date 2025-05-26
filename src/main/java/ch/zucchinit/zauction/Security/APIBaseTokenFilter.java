package ch.zucchinit.zauction.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class APIBaseTokenFilter extends OncePerRequestFilter {
    private final String API_HEADER;
    private final String API_SECRET;

    public APIBaseTokenFilter(String apiHeader, String apiSecret) {
        this.API_HEADER = apiHeader;
        this.API_SECRET = apiSecret;
    }

    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String apiKey = request.getHeader(API_HEADER);

        if (!API_SECRET.equals(apiKey)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
