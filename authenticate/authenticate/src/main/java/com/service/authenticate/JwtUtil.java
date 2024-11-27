package com.service.authenticate;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class JwtUtil {

    // Clave secreta generada automáticamente usando HS256
    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);  // Clave generada de forma automática

    // Método para generar el token
    public String generateToken(String username, Map<String, Object> claims) {
        // Si no hay roles en el claim, asigna un valor por defecto
        if (!claims.containsKey("roles")) {
            claims.put("roles", List.of("ROLE_USER"));  // Rol por defecto si no se proporcionan roles
        }

        // Genera el token JWT con los claims y firma usando la clave secreta
        return Jwts.builder()
                .setClaims(claims)  // Asigna los claims del usuario
                .setSubject(username)  // Asigna el nombre de usuario como subject del token
                .setIssuedAt(new Date())  // Fecha de emisión
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))  // Expiración de 10 horas
                .signWith(secretKey)  // Firma el token usando la clave secreta generada automáticamente
                .compact();  // Genera el token
    }

    // Método para obtener los claims desde el token
    public Claims getClaimsFromToken(String token) {
        try {
            // Parsear el JWT usando la clave secreta generada automáticamente
            return Jwts.parserBuilder()  // Utiliza parserBuilder() (más moderno que parser() en JWT 0.11+)
                    .setSigningKey(secretKey)  // Establece la clave secreta para validar la firma
                    .build()
                    .parseClaimsJws(token)  // Parseamos el token JWT y obtenemos los claims
                    .getBody();  // Retorna los claims del token
        } catch (Exception e) {
            throw new IllegalArgumentException("Token inválido o error al parsear el token.", e);  // Si hay un error, lanzamos excepción
        }
    }

    // Método para validar el token
    public boolean validateToken(String token) {
        try {
            // Si podemos obtener los claims del token, entonces el token es válido
            getClaimsFromToken(token);  
            return true;
        } catch (Exception e) {
            return false;  // Si ocurre un error, el token es inválido
        }
    }

    // Método para obtener los roles desde el token
    public List<String> getRolesFromToken(String token) {
        Claims claims = getClaimsFromToken(token);  // Obtener los claims desde el token

        // Verificar si los claims contienen el atributo "roles" y retornar los roles
        if (claims.containsKey("roles")) {
            return (List<String>) claims.get("roles");  // Retorna los roles como lista
        } else {
            return List.of("ROLE_USER");  // Retorna un rol por defecto si no se encuentran roles
        }
    }
}

