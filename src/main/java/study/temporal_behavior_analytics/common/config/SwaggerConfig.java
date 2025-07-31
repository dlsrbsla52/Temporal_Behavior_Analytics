package study.temporal_behavior_analytics.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class SwaggerConfig {

    private static final String BEARER_TOKEN_PREFIX = "Bearer";

    /**
     * OpenAPI Swagger Config
     * 
     * @return OpenAPI
     */
    @Bean
    @Profile("!Prod")
    public OpenAPI openAPI() {
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(BEARER_TOKEN_PREFIX);
        Components components = new Components()
                .addSecuritySchemes(BEARER_TOKEN_PREFIX, new SecurityScheme()
                        .name(BEARER_TOKEN_PREFIX)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme(BEARER_TOKEN_PREFIX)
                        .bearerFormat(BEARER_TOKEN_PREFIX));

        return new OpenAPI()
                .addSecurityItem(securityRequirement)
                .components(components);
    }
}
