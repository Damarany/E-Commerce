package com.ARD.eCommerce.configurations.security;

import com.ARD.eCommerce.configurations.security.JWT.JwtAuthEntryPoint;
import com.ARD.eCommerce.configurations.security.JWT.JwtRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Configuration
@Component
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtAuthEntryPoint jwtAuthEntryPoint;
    private final JwtRequestFilter jwtRequestFilter;


    @Bean
    public PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(config->config
//                                .requestMatchers("/${request.api.point}/auth/login").permitAll()
                 .requestMatchers("/api/v1/auth/login").permitAll()
                                .requestMatchers(
                                        "/v3/api-docs/**",
                                        "/swagger-ui/**",
                                        "/swagger-ui.html"
                                ).permitAll()
                                .anyRequest().authenticated()
                )
                .exceptionHandling(exceptions -> exceptions.authenticationEntryPoint(jwtAuthEntryPoint))  // Set custom entry point)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // Disable session management
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        // 1. Basic Authentication
        // Enables HTTP Basic Authentication (username/password in header)
        // Example header: Authorization: Basic base64(username:password)
        http.httpBasic(Customizer.withDefaults());

        // 2. CORS (Cross-Origin Resource Sharing)
        // Disables CORS - allows requests from any origin
        // WARNING: In production, you typically want to configure CORS properly:
        http.cors(AbstractHttpConfigurer::disable);
        // 3. CSRF (Cross-Site Request Forgery)
        // Disables CSRF protection
        // WARNING: Only disable in specific cases (like REST APIs with no cookies)
        http.csrf(AbstractHttpConfigurer::disable);
        // 4. Security Headers
        // Disables default security headers
        // WARNING: These headers are important for security in production
        http.headers(AbstractHttpConfigurer::disable);
        // 5. Form Login
        // Enables default login form
        // Provides /login and /logout endpoints
        http.formLogin(Customizer.withDefaults());

        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration)throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

}















