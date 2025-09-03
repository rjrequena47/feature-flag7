package com.bytes7.feature_flag7.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("FeatureFlag7 API")
                        .version("1.0")
                        .description("Documentación de la API de FeatureFlag7. Incluye registro y login con JWT."));
    }
}
