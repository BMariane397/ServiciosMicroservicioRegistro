package com.service.authenticate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;  // Asegúrate de que UserService esté correctamente configurado

    // Método para autenticar un usuario y generar el token JWT
    public String authenticate(String username, String password) {
        // Intentar autenticar con las credenciales proporcionadas
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        // Obtener los detalles del usuario autenticado
        User userDetails = (User) authentication.getPrincipal();

        // Crear el mapa de claims, agregando los roles del usuario
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userDetails.getAuthorities().stream()
                .map(auth -> auth.getAuthority())  // Obtiene el nombre del rol
                .collect(Collectors.toList()));

        // Generar y retornar el token JWT
        return jwtUtil.generateToken(userDetails.getUsername(), claims);
    }
}

