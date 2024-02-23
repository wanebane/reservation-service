package com.rivaldy.reservationservice.config;

import com.rivaldy.reservationservice.auth.JwtAuthenticationFilter;
import com.rivaldy.reservationservice.service.UserDetailsServiceImpl;
import com.rivaldy.reservationservice.util.RoleEnum;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AccessDeniedConfig accessDeniedConfig;
    private final LogoutHandlerConfig logoutHandlerConfig;
    private final LogoutSuccessHandlerConfig logoutSuccessHandlerConfig;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        req -> req
                                .requestMatchers("/api/v1/auth/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/concert/**").permitAll()
                                .requestMatchers(
                                        "/api/v1/seat/**",
                                        "/api/v1/concert/add"
                                )
                                .hasAuthority(RoleEnum.ADMIN.name())
                                .requestMatchers(
                                        "/api/v1/reservation/**",
                                        "/api/v1/ticket/**")
                                .hasAuthority(RoleEnum.USER.name())
                                .anyRequest()
                                .authenticated())
                .userDetailsService(userDetailsServiceImpl)
                .sessionManagement(
                        session -> session
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(
                        e -> e.accessDeniedHandler(accessDeniedConfig)
                                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .logout(
                        log -> log
                                .logoutUrl("/api/v1/logout")
                                .addLogoutHandler(logoutHandlerConfig)
                                .logoutSuccessHandler(logoutSuccessHandlerConfig))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
