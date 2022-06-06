package by.market.config;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class KeycloakAuthToken extends AbstractAuthenticationToken {

    private final Object credentials;
    private final KeycloakPrincipal principal;

    public KeycloakAuthToken(KeycloakPrincipal principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.setAuthenticated(true);
        this.credentials = credentials;
        this.principal = principal;
    }


    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public KeycloakPrincipal getPrincipal() {
        return principal;
    }

}
