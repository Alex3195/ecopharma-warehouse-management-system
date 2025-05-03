package uz.duol.ecopharmwarehouse.utils;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import uz.duol.ecopharmwarehouse.config.security.CustomUserDetails;

@UtilityClass
public class WMSUtils {
    public static CustomUserDetails getCurrentUserDetails() {
        // Get the current Authentication object from the SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if authentication is not null, authenticated, and the principal is an instance of CustomUserDetails
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            // Ensure the principal is an instance of CustomUserDetails
            if (principal instanceof CustomUserDetails) {
                return (CustomUserDetails) principal;
            }

            // Optionally, log a warning if the principal is not CustomUserDetails
            if (!(principal instanceof UserDetails)) {
                // Log or handle unexpected principal type
                System.out.println("Unexpected principal type: " + principal.getClass().getName());
            }
        }

        // Return null if no valid CustomUserDetails is found
        return null;
    }
}
