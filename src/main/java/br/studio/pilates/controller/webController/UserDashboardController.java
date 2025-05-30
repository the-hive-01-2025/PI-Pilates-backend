package br.studio.pilates.controller.webController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("web/dashboard")
public class UserDashboardController {
    @GetMapping("/home")
	public String home() {
		return "aluno/home";
	}

    @GetMapping("/modalidades")
    public String modalidades() {
        return "aluno/modalidades";
    }
}
