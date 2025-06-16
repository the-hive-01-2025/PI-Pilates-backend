package br.studio.pilates.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        String redirectUrl = "/web/home";

        for (GrantedAuthority authority : authorities) {
            String role = authority.getAuthority();

            switch (role) {
                case "ROLE_RECEPCAO":
                    redirectUrl = "/web/recepcionista/home";
                    break;
                case "ROLE_ALUNO":
                    redirectUrl = "/web/aluno/home";
                    break;
                case "ROLE_INSTRUTOR":
                    redirectUrl = "/web/instrutor/home";
                    break;
                // case "ROLE_ADMIN":
                //     redirectUrl = "/web/home-admin";
                //     break;
                default:
                    redirectUrl = "/web/home";
                    break;
            }
        }

        response.sendRedirect(redirectUrl);
    }
}
