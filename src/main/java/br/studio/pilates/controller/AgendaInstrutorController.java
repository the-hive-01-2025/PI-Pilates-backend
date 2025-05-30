package br.studio.pilates.controller;

import br.studio.pilates.dto.AgendaInstrutorDTO;
import br.studio.pilates.model.entity.Aula;
import br.studio.pilates.service.AgendaInstrutorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/agendaInstrutor")
public class AgendaInstrutorController {

    private final AgendaInstrutorService agendaInstrutorService;

    public AgendaInstrutorController(AgendaInstrutorService agendaInstrutorService) {
        this.agendaInstrutorService = agendaInstrutorService;
    }


    @GetMapping("/{instrutorId}")
    public List<AgendaInstrutorDTO> listarAulas(@RequestParam String instrutorId, Model model) {
        List<AgendaInstrutorDTO> aulas = agendaInstrutorService.listarAulasPorInstrutor(instrutorId);
        return aulas;
    }

    @PostMapping("/cancelar/{id}")
    public boolean cancelarAula(@PathVariable String id){
        return agendaInstrutorService.cancelarAula(id);
    }

    @PostMapping("/atualizar")
    public boolean atualizarAula(@ModelAttribute Aula aula){
        return agendaInstrutorService.atualizarAula(aula);
    }
}