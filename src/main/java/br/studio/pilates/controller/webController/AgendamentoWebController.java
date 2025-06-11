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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/web/recepcionista/agendamento")
public class AgendamentoWebController {

    @Autowired
    private AulaService aulaService;

    @Autowired
    private EstudioService estudioService;

    @Autowired
    private AlunoService alunoService;

    @GetMapping
    public String agendamento(Model model) {
        List<Aula> aulas = aulaService.getAllAulas();
        List<Estudio> estudios = estudioService.getAllEstudio();
        List<Aluno> alunos = alunoService.listarTodos();

        model.addAttribute("estudios", estudios);
        model.addAttribute("alunos", alunos);

        List<AulaAgendamentoDTO> aulasDTO = aulas.stream()
            .map(aula -> {
                AulaAgendamentoDTO dto = new AulaAgendamentoDTO();
                dto.setId(aula.getId());
                dto.setData(aula.getData());
                dto.setHorario(aula.getHorario());
                dto.setStatus(aula.getStatus());
                dto.setPresentes(aula.getPresentes() != null ? aula.getPresentes() : List.of());
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

        model.addAttribute("aulas", aulasDTO);
        return "recepcionista/agendamento";
    }

    @PostMapping("/salvar")
    public String salvarAula(Aula aula, RedirectAttributes redirectAttributes) {
        try {
            aula.setStatus(""); // ou aula.setStatus("Pendente");
            aulaService.saveAula(aula);
            redirectAttributes.addFlashAttribute("mensagem", "Aula agendada com sucesso!");
            redirectAttributes.addFlashAttribute("mensagemTipo", "sucesso");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao agendar aula: " + e.getMessage());
            redirectAttributes.addFlashAttribute("mensagemTipo", "erro");
        }
        return "redirect:/web/recepcionista/agendamento";
    }
    @PutMapping("/presenca/{id}")
    @ResponseBody
    public String marcarPresenca(@PathVariable("id") String aulaId, @RequestBody(required = false) Map<String, List<String>> body) {
        Aula aula = aulaService.getAulaById(aulaId);
        if (aula == null) {
            return "Aula não encontrada";
        }
        List<String> presentes = (body != null) ? body.get("presentes") : null;
        if (presentes == null || presentes.isEmpty()) {
            aula.setStatus("Ausente");
            aula.setPresentes(List.of());
        } else {
            aula.setStatus("Presente");
            aula.setPresentes(presentes);
        }
        aulaService.saveAula(aula);
        return "ok";
    }

    @PostMapping("/deletar/{id}")
    public String deletarAula(@PathVariable("id") String id) {
        aulaService.deleteAula(id);
        return "redirect:/web/recepcionista/agendamento";
    }
}