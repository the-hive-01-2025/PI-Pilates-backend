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

/**
 * Classe responsável por definir o comportamento após uma autenticação bem-sucedida no sistema.
 * 
 * Implementa AuthenticationSuccessHandler, que permite customizar para onde o usuário será redirecionado
 * após realizar login com sucesso, baseado nas suas roles (perfis).
 */
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * Método chamado automaticamente pelo Spring Security quando a autenticação do usuário é bem-sucedida.
     * 
     * @param request objeto que representa a requisição HTTP
     * @param response objeto que representa a resposta HTTP
     * @param authentication objeto que contém dados da autenticação, incluindo roles do usuário
     * 
     * @throws IOException em caso de erro de redirecionamento HTTP
     * @throws ServletException exceção padrão de servlet
     */
    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {

        // Obtem a coleção de roles/perfis do usuário autenticado
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        // URL padrão para redirecionamento caso nenhuma role específica seja encontrada
        String redirectUrl = "/web/home";

        // Itera sobre as roles para determinar a URL de redirecionamento correta
        for (GrantedAuthority authority : authorities) {
            String role = authority.getAuthority();

            // Escolhe a URL com base no perfil do usuário
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
                // Exemplo de role adicional comentado para possível uso futuro
                // case "ROLE_ADMIN":
                //     redirectUrl = "/web/home-admin";
                //     break;
                default:
                    // Caso a role não seja reconhecida, mantém URL padrão
                    redirectUrl = "/web/home";
                    break;
            }
        }

        // Realiza o redirecionamento HTTP para a URL definida
        response.sendRedirect(redirectUrl);
    }
}
