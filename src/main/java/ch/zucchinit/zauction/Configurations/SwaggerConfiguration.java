package ch.zucchinit.zauction.Configurations;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI getOpenAPI() {
        return new OpenAPI()
            .info(new Info().title("Swagger ZAuction API docs"))
            .components(new Components()
                    .addSecuritySchemes("apiKey", new SecurityScheme()
                            .type(SecurityScheme.Type.APIKEY)
                            .in(SecurityScheme.In.HEADER)
                            .name("X-API-Key")
                            .description("API Key")
                    )
                    .addSecuritySchemes("cookieAuth", new SecurityScheme()
                            .type(SecurityScheme.Type.APIKEY)
                            .in(SecurityScheme.In.COOKIE)
                            .name("Authorization")
                            .description("Cookie Authorization")
                    )
            )
            .addSecurityItem(new SecurityRequirement().addList("apiKey").addList("cookieAuth"));
    }
}
