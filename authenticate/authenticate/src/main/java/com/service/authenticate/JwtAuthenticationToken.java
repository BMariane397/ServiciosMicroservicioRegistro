package com.service.authenticate;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final String principal;
    private final String credentials;

    // Constructor para crear un token JWT sin autenticación
    public JwtAuthenticationToken(String principal, String credentials) {
        super(null);  // No hay autoridades cuando no está autenticado
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(false);  // Indicamos que no está autenticado
    }

    // Constructor para crear un token JWT con autenticación
    public JwtAuthenticationToken(String principal, String credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);  // Establecemos las autoridades (roles)
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(true);  // Marcamos como autenticado
    }

    @Override
    public Object getCredentials() {
        return credentials;  // Credenciales (usualmente la contraseña o el token)
    }

    @Override
    public Object getPrincipal() {
        return principal;  // Principal (usualmente el nombre de usuario o el identificador)
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();  // Limpia las credenciales, se sobreentiende que no es necesario agregar lógica extra
    }
}
