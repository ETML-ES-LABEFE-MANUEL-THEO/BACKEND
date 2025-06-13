package ch.zucchinit.zauction.Security;

import ch.zucchinit.zauction.Configurations.APIConfiguration;
import ch.zucchinit.zauction.Auth.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {
    private final APIConfiguration apiConfiguration;

    public SecurityConfig(APIConfiguration apiConfiguration) { this.apiConfiguration = apiConfiguration; }

    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(apiConfiguration.getCorsOrigin());
        configuration.setAllowedHeaders(List.of(apiConfiguration.getApiHeader(), "accept", "content-type", "origin", "authorization", "x-requested-with"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    @Order(0)
    public SecurityFilterChain noAuthSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .securityMatcher("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html")
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests -> requests.anyRequest().permitAll());

        return httpSecurity.build();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain authSecurityFilterChain(HttpSecurity httpSecurity, TokenService tokenService) throws Exception {
        httpSecurity
                .securityMatcher("/**")
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests -> requests.anyRequest().permitAll())
                .addFilterBefore(
                        new APIBaseTokenFilter(apiConfiguration.getApiHeader(), apiConfiguration.getApiSecret()),
                        UsernamePasswordAuthenticationFilter.class
                )
                .addFilterBefore(new UserAuthTokenFilter(tokenService), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}
