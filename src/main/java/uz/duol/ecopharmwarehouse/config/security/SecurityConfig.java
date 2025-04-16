package uz.duol.ecopharmwarehouse.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    @Value("${keycloak.realm}")
    private String realm;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET,
                                "/actuator/**",
                                "/api/v1/wms/v3/api-docs/**",
                                "/api/v1/wms/swagger-ui/**").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        grantedAuthoritiesConverter.setAuthoritiesClaimName("realm_access.roles"); // Extract realm roles

        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
            // Add realm roles
            Collection<GrantedAuthority> authorities = new ArrayList<>(grantedAuthoritiesConverter.convert(jwt));

            // Extract client roles from resource_access.{client_id}.roles
            Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
            if (resourceAccess != null) {
                Map<String, Object> clientRoles = (Map<String, Object>) resourceAccess.get(realm);
                if (clientRoles != null && clientRoles.containsKey("roles")) {
                    List<String> roles = (List<String>) clientRoles.get("roles");
                    roles.forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role)));
                }
            }
            return authorities;
        });
        return jwtConverter;
    }
    private void getRoles(Collection<GrantedAuthority> authorities, Map<String, Object> clientRoles) {
        if (clientRoles != null && clientRoles.containsKey("roles")) {
            List<String> roles = (List<String>) clientRoles.get("roles");
            authorities.addAll(roles.stream()
                    .map(role -> "ROLE_" + role) // Add the ROLE_ prefix
                    .map(SimpleGrantedAuthority::new)
                    .toList());
        }
    }

}
