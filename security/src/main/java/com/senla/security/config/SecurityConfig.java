package com.senla.security.config;

import com.senla.security.JwtConfigurer;
import com.senla.security.JwtTokenProvider;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    private static final String LOGIN_ENDPOINT = "/auth/login";
    private static final String REG_USER_ENDPOINT = "/users/register";

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .in(SecurityScheme.In.HEADER)
                                .scheme("bearer")
                                .bearerFormat("jwt")
                                .name("JWT Authentication")
                        )
                )
                .addSecurityItem(new SecurityRequirement()
                        .addList("bearer-key")
                );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(LOGIN_ENDPOINT, REG_USER_ENDPOINT).permitAll()
                .antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security",
                        "/swagger-ui.html", "/webjars/**", "/swagger-resources/configuration/ui", "/swagger-ui.html", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }


}
