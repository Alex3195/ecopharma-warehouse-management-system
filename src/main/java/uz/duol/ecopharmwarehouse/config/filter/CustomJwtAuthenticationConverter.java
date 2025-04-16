package uz.duol.ecopharmwarehouse.config.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import uz.duol.ecopharmwarehouse.config.security.CustomUserDetails;
import uz.duol.ecopharmwarehouse.module.permissions.dto.UserPermissionDto;
import uz.duol.ecopharmwarehouse.module.permissions.service.UserPermissionService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Value("${keycloak.realm}")
    private String realm;

    private final UserPermissionService userPermissionService;

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        // Manually extract realm roles
        Map<String, Object> realmAccess = jwt.getClaim("realm_access");
        getRoles(authorities, realmAccess);

        // Extract client roles from resource_access.{client_id}.roles
        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
        if (resourceAccess != null) {
            Map<String, Object> clientRoles = (Map<String, Object>) resourceAccess.get(realm); // Use the correct client ID
            getRoles(authorities, clientRoles);
        }

        // Load additional authorities from the database
        authorities.addAll(loadAuthoritiesFromDatabase(jwt.getSubject()));

        // Extract user details from the JWT
        String userId = jwt.getSubject();
        String username = jwt.getClaimAsString("preferred_username");
        String email = jwt.getClaimAsString("email");

        // Create a custom UserDetails object
        CustomUserDetails userDetails = new CustomUserDetails(userId, username, email, authorities);

        // Return a custom Authentication object
        return new JwtAuthenticationToken(jwt, authorities, userDetails.getUsername()) {
            @Override
            public Object getPrincipal() {
                return userDetails; // Set CustomUserDetails as the principal
            }
        };
    }

    private void getRoles(Collection<GrantedAuthority> authorities, Map<String, Object> clientRoles) {
        if (clientRoles != null && clientRoles.containsKey("roles")) {
            List<String> roles = (List<String>) clientRoles.get("roles");
            authorities.addAll(roles.stream()
                    .map(role -> "ROLE_"+role) // Add the ROLE_ prefix
                    .map(SimpleGrantedAuthority::new)
                    .toList());
        }
    }

    private List<SimpleGrantedAuthority> loadAuthoritiesFromDatabase(String userId) {
        List<UserPermissionDto> userPermissions = userPermissionService.findUserPermissionByUserId(userId);
        return userPermissions.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getName().name()))
                .toList();
    }
}