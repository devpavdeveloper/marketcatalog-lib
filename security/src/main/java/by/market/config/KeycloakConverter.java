package by.market.config;

import com.nimbusds.jose.shaded.json.JSONArray;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class KeycloakConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private static final String AUTHORITY_PREFIX = "ROLE_";
    private static final String CLAIM_ROLES = "roles";
    private static final String REALM_ACCESS = "realm_access";

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        final List<GrantedAuthority> authorities = getGrantedAuthorities(jwt);

        final KeycloakPrincipal principal = KeycloakPrincipal.builder()
                .authorities(authorities)
                .token(jwt.getTokenValue())
                .username(jwt.getClaimAsString("preferred_username"))
                .firstName(jwt.getClaimAsString("given_name"))
                .lastName(jwt.getClaimAsString("family_name"))
                .email(jwt.getClaimAsString("email"))
                .build();

        return new KeycloakAuthToken(principal, new Object(), authorities);
    }


    private List<GrantedAuthority> getGrantedAuthorities(Jwt jwt) {
        final Map<String, Object> rolesAsMap = jwt.getClaimAsMap(REALM_ACCESS);

        final String[] roles = ((JSONArray) rolesAsMap.get(CLAIM_ROLES)).toArray(new String[0]);

        return Stream.of(roles)
                .filter(it -> !it.equalsIgnoreCase("offline_access"))
                .filter(it -> !it.equalsIgnoreCase("uma_authorization"))
                .map(it -> new SimpleGrantedAuthority(AUTHORITY_PREFIX + it))
                .collect(Collectors.toList());
    }

}
