package com.service.authenticate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    // Endpoint para autenticación (login)
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest authRequest) {
        try {
            // Usar AuthService para autenticar con la base de datos
            String token = authService.authenticate(authRequest.getUsername(), authRequest.getPassword());
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (Exception e) {
            // Retornar un código de estado 401 si la autenticación falla
            return ResponseEntity.status(401).body("Invalid credentials: " + e.getMessage());
        }
    }

    // Endpoint para registro
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            // Usar UserService para registrar un nuevo usuario
            User newUser = userService.registerUser(user);
            return ResponseEntity.ok(newUser);
        } catch (Exception e) {
            // Retornar un código de estado 400 si algo sale mal
            return ResponseEntity.status(400).body("Error al registrar usuario: " + e.getMessage());
        }
    }
}
