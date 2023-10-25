package com.project.euquero.configs;

import com.project.euquero.security.jwt.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private AuthenticationFilter authenticationFilter;

    @Autowired
    private LogoutHandler logoutHandler;

    @Value("${authorizeHttpRequests.permitAll}")
    private String requestAllowed;

    @Value("${authorizeHttpRequests.authenticated}")
    private String requestAuthenticated;

    @Value("${authorizeHttpRequests.denyAll}")
    private String requestDenied;

    @Value("${authorizeHttpRequests.premium}")
    private String requestPremium;

    @Value("${authorizeHttpRequests.adminsOnly}")
    private String adminsOnly;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorization -> authorization
                        .requestMatchers(requestAllowed.split(",")).permitAll()
                        .requestMatchers(requestAuthenticated.split(",")).authenticated()
                        .requestMatchers(requestPremium.split(",")).hasAnyAuthority("PRATA", "OURO", "PLATINUM", "ADMIN")
                        .requestMatchers(adminsOnly.split(",")).hasAnyAuthority("ADMIN")
                        .requestMatchers(requestDenied.split(",")).denyAll())
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authenticationProvider)
                .cors(Customizer.withDefaults())
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler(
                                (request, response, authentication) -> SecurityContextHolder.clearContext()));

        return http.build();
    }
}
