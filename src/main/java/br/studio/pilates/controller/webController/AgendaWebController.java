package br.studio.pilates.controller.webController;

import br.studio.pilates.dto.AulaAgendamentoDTO;
import br.studio.pilates.model.entity.Aluno;
import br.studio.pilates.model.entity.Aula;
import br.studio.pilates.model.entity.Estudio;
import br.studio.pilates.service.AlunoService;
import br.studio.pilates.service.AulaService;
import br.studio.pilates.service.EstudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/web/instrutor/agenda")
public class AgendaWebController {

    @Autowired
    private AulaService aulaService;

    @Autowired
    private EstudioService estudioService;

    @Autowired
    private AlunoService alunoService;

    @GetMapping
    public String agenda(Model model) {
        List<Aula> aulas = aulaService.getAllAulas();
        List<Estudio> estudios = estudioService.getAllEstudio();
        List<Aluno> alunos = alunoService.listarTodos();

        List<AulaAgendamentoDTO> aulasDTO = aulas.stream()
            .map(aula -> {
                AulaAgendamentoDTO dto = new AulaAgendamentoDTO();
                dto.setId(aula.getId());
                dto.setData(aula.getData());
                dto.setHorario(aula.getHorario());
                dto.setStatus(aula.getStatus());
                dto.setPresentes(aula.getPresentes() != null ? aula.getPresentes() : List.of());
                dto.setObservacoes(aula.getObservacoes());
                dto.setModalidade("Não informado");
                dto.setInstrutorNome("Não informado");

                String nomeEstudio = "Não informado";
                if (aula.getIdStudio() != null) {
                    Estudio estudio = estudioService.getEstudioById(aula.getIdStudio());
                    if (estudio != null && estudio.getNome() != null) {
                        nomeEstudio = estudio.getNome();
                    }
                }
                dto.setEstudioNome(nomeEstudio);

                if (aula.getAlunos() != null) {
                    List<String> nomesAlunos = aula.getAlunos().stream()
                        .map(alunoId -> {
                            Aluno aluno = alunoService.getById(alunoId).orElse(null);
                            return aluno != null ? aluno.getNome() : "";
                        })
                        .toList();
                    dto.setAlunos(nomesAlunos);
                } else {
                    dto.setAlunos(List.of());
                }

                return dto;
            })
            .sorted((a1, a2) -> {
                if (a1.getData() == null && a2.getData() == null) return 0;
                if (a1.getData() == null) return 1;
                if (a2.getData() == null) return -1;
                int cmp = a1.getData().compareTo(a2.getData());
                if (cmp != 0) return cmp;
                if (a1.getHorario() == null && a2.getHorario() == null) return 0;
                if (a1.getHorario() == null) return 1;
                if (a2.getHorario() == null) return -1;
                return a1.getHorario().compareTo(a2.getHorario());
            })
            .toList();

        model.addAttribute("estudios", estudios);
        model.addAttribute("alunos", alunos);
        model.addAttribute("aulas", aulasDTO);
        return "instrutor/agenda";
    }

    @PostMapping("/deletar/{id}")
    public String cancelarAula(String id) {
        Aula aula = aulaService.getAulaById(id);
        if (aula != null) {
            aula.setStatus("Cancelada");
            aulaService.saveAula(aula);
        }
        return "redirect:/web/instrutor/agenda";
    }
}
