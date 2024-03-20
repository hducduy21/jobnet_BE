package com.jobnet.apigateway.configs;

import com.jobnet.apigateway.configs.security.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
        ServerHttpSecurity http,
        JwtAuthFilter jwtAuthFilter
    ) {
        http
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
            .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
            .authorizeExchange((authz) -> authz
                .pathMatchers("/actuator/health/**").hasAuthority("Admin")

                .pathMatchers("/api/auth/**").permitAll()
                .pathMatchers("/api/registration/**").permitAll()

                .pathMatchers("/api/admins/**").hasAuthority("Admin")

                .pathMatchers(HttpMethod.GET,"/api/recruiters/**").permitAll()
                .pathMatchers(HttpMethod.POST,"/api/recruiters/**").permitAll()
                .pathMatchers("/api/recruiters/**").hasAnyAuthority("Admin", "Recruiter")

                .pathMatchers(HttpMethod.GET, "/api/jobSeekers/**").permitAll()
                .pathMatchers(HttpMethod.POST,"/api/jobSeekers/**").permitAll()
                .pathMatchers("/api/jobSeekers/**").hasAnyAuthority("Admin", "JobSeeker")

                .pathMatchers(HttpMethod.GET, "/api/posts/**").permitAll()
                .pathMatchers(HttpMethod.POST, "/api/posts/**").hasAuthority("Recruiter")
                .pathMatchers("/api/posts/**").hasAnyAuthority("Admin", "Recruiter")

                .pathMatchers(
                    HttpMethod.GET,
                    "/api/categories/**",
                    "/api/professions/**",
                    "/api/levels/**",
                        "/api/locations/**"
                ).permitAll()
                .pathMatchers(
                    "/api/categories/**",
                    "/api/professions/**",
                    "/api/levels/**",
                        "/api/locations/**"
                ).hasAuthority("Admin")

                .pathMatchers(HttpMethod.GET, "/api/benefits/**").permitAll()
                .pathMatchers(
                    HttpMethod.POST,
                    "/api/benefits/**"
                ).hasAnyAuthority("Admin", "Recruiter")
                .pathMatchers("/api/benefits/**").hasAuthority("Admin")

                .pathMatchers(HttpMethod.GET, "/api/businesses/**").permitAll()
                .pathMatchers(HttpMethod.POST, "/api/businesses/**").hasAuthority("Recruiter")
                .pathMatchers(HttpMethod.PUT, "/api/businesses/*/follow").hasAuthority("JobSeeker")
                .pathMatchers(
                    HttpMethod.PUT,
                    "/api/businesses/**"
                ).hasAnyAuthority("Admin", "Recruiter")
                .pathMatchers(HttpMethod.DELETE, "/api/businesses/**").hasAuthority("Admin")

                .pathMatchers(HttpMethod.GET, "/api/resumes/**").authenticated()
                .pathMatchers("/api/resumes/**").hasAnyAuthority("Recruiter","JobSeeker")

                .pathMatchers(HttpMethod.GET, "/api/applications/**").authenticated()
                .pathMatchers(HttpMethod.POST, "/api/applications/**").hasAuthority("JobSeeker")
                .pathMatchers("/api/applications/**").hasAnyAuthority("Recruiter", "JobSeeker")

                .pathMatchers("/api/wishlists/**").hasAuthority("JobSeeker")
                .pathMatchers("/api/notifications/**").authenticated()
                .pathMatchers("/api/location/**").permitAll()
            )
            .addFilterAt(jwtAuthFilter, SecurityWebFiltersOrder.AUTHENTICATION);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Collections.singletonList("*"));
        config.setAllowedMethods(Collections.singletonList("*"));
        config.setAllowedHeaders(Collections.singletonList("*"));
        config.setMaxAge(30L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
