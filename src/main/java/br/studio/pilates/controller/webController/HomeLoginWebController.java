package br.studio.pilates.controller.webController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;  


@Controller
@RequestMapping("web")
public class HomeLoginWebController {
    
    @GetMapping("/login")
    public String mostrarLogin() {
        return "home/login"; 
    }

    @GetMapping("/home")
    public String mostrarHome() {
        return "home/home";
}

}
