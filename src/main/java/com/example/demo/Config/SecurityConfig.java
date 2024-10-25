package com.example.demo.Config;

import com.example.demo.Services.UsuarioDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UsuarioDetailsService usuarioDetailsService;
    private final CorsConfigurationSource corsConfigurationSource;

    public SecurityConfig(UsuarioDetailsService usuarioDetailsService, CorsConfigurationSource corsConfigurationSource) {
        this.usuarioDetailsService = usuarioDetailsService;
        this.corsConfigurationSource = corsConfigurationSource;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource)) // Habilitar CORS usando la configuración definida
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/register", "/api/auth/login").permitAll() // Permitir acceso sin autenticación
                        .anyRequest().authenticated() // Requerir autenticación para todas las demás rutas
                )
                .formLogin(form -> form
                        .loginProcessingUrl("/api/auth/login") // URL donde se maneja el login
                        .permitAll() // Permitir el acceso a la URL de login
                )
                .logout(logout -> logout.permitAll());

        return http.build();
    }
}
