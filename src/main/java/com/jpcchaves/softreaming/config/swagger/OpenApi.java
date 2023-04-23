package com.jpcchaves.softreaming.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApi {
    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI().info(
                new Info()
                        .title("Softreaming - Rest API")
                        .version("v1")
                        .contact(new Contact().url("https://www.linkedin.com/in/joaopaulo-chaves/").email("jpcchaves@outlook.com"))
                        .description("REST API built to manage a streaming application made with Spring Boot")
                        .termsOfService("https://porfolio-jpcchaves.vercel.app/")
                        .license(new License().name("MIT").url("https://porfolio-jpcchaves.vercel.app/")));
    }
}
