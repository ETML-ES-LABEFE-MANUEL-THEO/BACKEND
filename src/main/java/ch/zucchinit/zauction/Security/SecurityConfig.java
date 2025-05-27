package ch.zucchinit.zauction.Security;

import ch.zucchinit.zauction.APIConfiguration;
import ch.zucchinit.zauction.Auth.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, TokenService tokenService) throws Exception {
        httpSecurity
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests -> requests.anyRequest().permitAll())
                .httpBasic(AbstractHttpConfigurer::disable)
                .addFilterBefore(
                        new APIBaseTokenFilter(apiConfiguration.getApiHeader(), apiConfiguration.getApiSecret()),
                        UsernamePasswordAuthenticationFilter.class
                )
                .addFilterBefore(new UserAuthTokenFilter(tokenService), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}
