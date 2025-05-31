package br.studio.pilates.controller;

import br.studio.pilates.dto.AulaAgendamentoDTO;
import br.studio.pilates.model.entity.Aula;
import br.studio.pilates.model.entity.Estudio;
import br.studio.pilates.service.AulaService;
import br.studio.pilates.service.EstudioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/web/aula")
public class AulaWebController {

    @Autowired
    private AulaService aulaService;
    @Autowired
    private EstudioService estudioService;

    @GetMapping("/agendamento")
    public String agendamento(Model model) {
        List<Aula> aulas = aulaService.getAllAulas();
        List<Estudio> estudios = estudioService.getAllEstudio();
        model.addAttribute("estudios", estudios);
        List<AulaAgendamentoDTO> aulasDTO = aulas.stream().map(aula -> {
            AulaAgendamentoDTO dto = new AulaAgendamentoDTO();
            dto.setId(aula.getId());
            dto.setData(aula.getData());
            dto.setHorario(aula.getHorario());
            dto.setStatus(aula.getStatus());
            dto.setModalidade("Não informado");
            dto.setInstrutorNome("Não informado");

            // Busca o nome do estúdio pelo idStudio
            String nomeEstudio = "Não informado";
            if (aula.getIdStudio() != null) {
                var estudio = estudioService.getEstudioById(aula.getIdStudio());
                if (estudio != null && estudio.getNome() != null) {
                    nomeEstudio = estudio.getNome();
                }
            }
            dto.setEstudioNome(nomeEstudio);

            return dto;
        }).toList();
        model.addAttribute("aulas", aulasDTO);
        return "front-aluno/agendamento";
    }

    @PostMapping("/salvar")
    public String salvarAula(Aula aula) {
        aulaService.saveAula(aula);
        return "redirect:/web/aula/agendamento";
    }

    @PostMapping("/reagendar/{id}")
    public String reagendarAula(@PathVariable("id") String id, Aula aula) {
        aula.setId(id);
        aulaService.saveAula(aula);
        return "redirect:/web/aula/agendamento";
    }

    @PostMapping("/deletar/{id}")
    public String deletarAula(@PathVariable("id") String id) {
        aulaService.deleteAula(id);
        return "redirect:/web/aula/agendamento";
    }
}