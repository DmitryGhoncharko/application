package by.ghoncharko.application.config.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import java.io.IOException;

public class AuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,Authentication authentication) throws IOException, ServletException {
        boolean isAdmin = authentication.getAuthorities().stream().
                anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        if(isAdmin){
            setDefaultTargetUrl("/admin/home");
        }else {
            setDefaultTargetUrl("/client/home");
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }
}