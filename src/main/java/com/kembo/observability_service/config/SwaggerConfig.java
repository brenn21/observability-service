package com.kembo.observability_service.config;

import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    @Timed(description = "Time spend processing all tenants", histogram = true)
    OpenAPI api() {
        return new OpenAPI()
                .info(new Info()
                        .title("Observability API")
                        .description("Tracing our API.")
                        .version("v1.0.0")
                        .termsOfService("Observe anything from our API")
                        .license(new License().name("Kembo License")
                                .url("https://kembo.ru"))
                        .contact(new Contact().name("Mfouna Brenn")
                                .email("gloire2007@hotmail.fr"))
                ).externalDocs(new ExternalDocumentation().url("https://brenn.org")
                );
    }
}
