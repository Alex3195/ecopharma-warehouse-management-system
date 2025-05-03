package uz.duol.ecopharmwarehouse.config.audit;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import uz.duol.ecopharmwarehouse.config.security.CustomUserDetails;

import java.util.Optional;

public class SecurityAuditorAware implements AuditorAware<String> {

    @NotNull
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof CustomUserDetails details) {
            return Optional.of(details.getUserId());
        }

        return Optional.empty();
    }

}
