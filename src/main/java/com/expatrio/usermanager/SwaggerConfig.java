package com.expatrio.usermanager;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import springfox.documentation.builders.LoginEndpointBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.google.common.base.Predicates.or;
import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${security.oauth2.authorization.endpoint}")
    String authorizeEndpoint;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths((regex("/api/.*")))
                .build()
                .securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(authTypes())
                ;
    }

    private List<SecurityScheme> authTypes() {
        List<AuthorizationScope> authorizationScopeList = new ArrayList<>();
        authorizationScopeList.add(new AuthorizationScope("openid", "Optional scope just in case Scope will be supported later on."));
        //Scopes not supported

        List<GrantType> grantTypes = new ArrayList<>();

        LoginEndpoint loginEndpoint = new LoginEndpoint(authorizeEndpoint);
        grantTypes.add(new ImplicitGrant(loginEndpoint, "access_token"));

        OAuth oAuthScheme = new OAuthBuilder()
                .name("oauth2")
                .scopes(authorizationScopeList)
                .grantTypes(grantTypes)
                .build();

        return new ArrayList<>(Arrays.asList(oAuthScheme));
    }


    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth())
                .build();
    }



    private List<SecurityReference> defaultAuth() {

        final AuthorizationScope[] authorizationScopes = new AuthorizationScope[3];
        authorizationScopes[0] = new AuthorizationScope("read", "read all");
        authorizationScopes[1] = new AuthorizationScope("trust", "trust all");
        authorizationScopes[2] = new AuthorizationScope("write", "write all");

        return Collections.singletonList(new SecurityReference("oauth2", authorizationScopes));
    }

    @Bean
    public SecurityConfiguration security() {
        return SecurityConfigurationBuilder.builder()
                .appName("auth-server")
                .clientSecret("client-secret")
                .scopeSeparator("")
                .appName("User Manager")
                .realm("oauth2")
                .build();

    }
}
