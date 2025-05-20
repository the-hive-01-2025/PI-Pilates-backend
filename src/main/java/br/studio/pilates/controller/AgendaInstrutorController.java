package br.studio.pilates.controller;

import br.studio.pilates.dto.AgendaInstrutorDTO;
import br.studio.pilates.model.entity.Aula;
import br.studio.pilates.service.AgendaInstrutorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/agendaInstrutor")
public class AgendaInstrutorController {

    private final AgendaInstrutorService agendaInstrutorService;

    public AgendaInstrutorController(AgendaInstrutorService agendaInstrutorService) {
        this.agendaInstrutorService = agendaInstrutorService;
    }

    @GetMapping("/{instrutorId}")
    public String listarAulas(@PathVariable String instrutorId, Model model) {
        List<AgendaInstrutorDTO> aulas = agendaInstrutorService.listarAulasPorInstrutor(instrutorId);
        model.addAttribute("aulas", aulas);
        return "agendaInstrutor"; // Página Thymeleaf
    }

    @PostMapping("/cancelar/{id}")
    public String cancelarAula(@PathVariable String id) {
        agendaInstrutorService.cancelarAula(id);
        return "redirect:/agendaInstrutor"; 
    }

    @PostMapping("/atualizar")
    public String atualizarAula(@ModelAttribute Aula aula) {
        agendaInstrutorService.atualizarAula(aula);
        return "redirect:/agendaInstrutor";
    }
}