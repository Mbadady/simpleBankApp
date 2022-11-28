package com.mbadady.simpleBankApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class SwaggerConfig {

    //    Customizing for JWT
    public static final String AUTHORIZATION_HEADER = "Authorization";

    //    Customizing for JWT
    private ApiKey apiKey(){
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }


    private ApiInfo apiInfo(){
        return new ApiInfo(
                "Spring Boot Bank Rest Api",
                "Spring Boot Bank Rest Api Documentation",
                "1",
                "Terms of Service",
                new Contact("Mbah Victor Somtochukwu", "github.com/Mbadady","victorsomtochukw@gmail.com"),
                "License of API",
                "License URL",
                Collections.emptyList()
        );
    }

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .securityContexts(Arrays.asList(securityContext())) //    Customizing for JWT
                .securitySchemes(Arrays.asList(apiKey())) //    Customizing for JWT
                .select()
                .apis(RequestHandlerSelectors.any()) // this is to check endpoints to document
                .paths(PathSelectors.any()) // this is to provide the paths for api you want to document
                .build();
    }

    private SecurityContext securityContext(){
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    //    Customizing for JWT. this is what swagger uses for authorization
    private List<SecurityReference> defaultAuth(){
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        System.out.println(Arrays.asList(new SecurityReference("JWT", authorizationScopes)));
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }
}
