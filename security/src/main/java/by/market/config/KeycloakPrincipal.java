package by.market.config;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Data
@Builder
public class KeycloakPrincipal {

    private String username;

    private String firstName;
    private String lastName;
    private String otherName;

    private String token;

    private String phoneNumber;
    private String email;

    private List<GrantedAuthority> authorities;

}
