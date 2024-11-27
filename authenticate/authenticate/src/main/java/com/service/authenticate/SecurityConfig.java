package com.service.authenticate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // Constructor para inyectar JwtAuthenticationFilter
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    // Configuración del AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, UserService userService) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = 
                http.getSharedObject(AuthenticationManagerBuilder.class);
        
        // Configuramos el UserDetailsService y el PasswordEncoder
        authenticationManagerBuilder
            .userDetailsService(userService)  // UserService debe implementar UserDetailsService
            .passwordEncoder(passwordEncoder());  // Codificador de contraseñas
        
        return authenticationManagerBuilder.build();
    }

    // Configuración del PasswordEncoder para codificar las contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Usamos BCrypt para las contraseñas
    }

    // Configuración de seguridad HTTP y los filtros
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()  // Desactivamos CSRF ya que estamos usando JWT
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**").permitAll()  // Permitir sin autenticación
                .anyRequest().authenticated()  // Requiere autenticación para cualquier otro endpoint
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);  // Añadir filtro JWT antes del filtro de autenticación por usuario/contraseña

        return http.build();
    }
}
