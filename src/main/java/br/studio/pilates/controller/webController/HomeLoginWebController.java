package br.studio.pilates.controller.webController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller responsável por gerenciar as rotas de login
 * e da página inicial (home) do sistema web.
 */
@Controller
@RequestMapping("/web")
public class HomeLoginWebController {

    /**
     * Exibe a tela de login do sistema.
     *
     * @return Caminho da view de login (home/login.html)
     */
    @GetMapping("/login")
    public String mostrarLogin() {
        return "home/login";
    }

    /**
     * Exibe a tela inicial (home) após o login.
     *
     * @return Caminho da view da home (home/home.html)
     */
    @GetMapping("/home")
    public String mostrarHome() {
        return "home/home";
    }
}
