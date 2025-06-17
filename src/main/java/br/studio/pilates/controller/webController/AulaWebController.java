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

/**
 * Controller responsável por gerenciar as ações de agendamento de aulas
 * no painel web dos administradores e funcionários.
 */
@Controller
@RequestMapping("/web/aula")
public class AulaWebController {

    @Autowired
    private AulaService aulaService;

    @Autowired
    private EstudioService estudioService;

    @Autowired
    private AlunoService alunoService;

    /**
     * Exibe a tela de agendamento de aulas, com as informações de alunos,
     * estúdios e aulas cadastradas.
     */
    @GetMapping("/agendamento")
    public String agendamento(Model model) {

        // Buscar dados necessários
        List<Aula> aulas = aulaService.getAllAulas();
        List<Estudio> estudios = estudioService.getAllEstudio();
        List<Aluno> alunos = alunoService.listarTodos();

        // Adicionar dados no model
        model.addAttribute("estudios", estudios);
        model.addAttribute("alunos", alunos);

        // Transformar entidades Aula em DTOs para exibição
        List<AulaAgendamentoDTO> aulasDTO = aulas.stream()
                .map(aula -> {
                    AulaAgendamentoDTO dto = new AulaAgendamentoDTO();
                    dto.setId(aula.getId());
                    dto.setData(aula.getData());
                    dto.setHorario(aula.getHorario());
                    dto.setStatus(aula.getStatus());
                    dto.setModalidade(aula.getModalidade() != null ? aula.getModalidade() : "Não informado");
                    dto.setInstrutorNome("Não informado");

                    String nomeEstudio = "Não informado";
                    if (aula.getIdStudio() != null) {
                        Estudio estudio = estudioService.getEstudioById(aula.getIdStudio());
                        if (estudio != null && estudio.getNome() != null) {
                            nomeEstudio = estudio.getNome();
                        }
                    }
                    dto.setEstudioNome(nomeEstudio);

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

        // Adicionar lista de aulas no model
        model.addAttribute("aulas", aulasDTO);

        return "aluno/aulas";
    }

    /**
     * Salva uma nova aula no sistema.
     */
    @PostMapping("/salvar")
    public String salvarAula(Aula aula, RedirectAttributes redirectAttributes) {
        try {
            if (aula.getStatus() == null || aula.getStatus().isEmpty()) {
                aula.setStatus("Pendente");
            }

            aulaService.saveAula(aula);

            redirectAttributes.addFlashAttribute("mensagem", "Aula agendada com sucesso!");
            redirectAttributes.addFlashAttribute("mensagemTipo", "sucesso");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao agendar aula: " + e.getMessage());
            redirectAttributes.addFlashAttribute("mensagemTipo", "erro");
        }
        return "redirect:/web/aula/agendamento";
    }

    /**
     * Atualiza uma aula existente (reagendamento).
     */
    @PostMapping("/reagendar/{id}")
    public String reagendarAula(@PathVariable("id") String id, Aula aula, RedirectAttributes redirectAttributes) {
        try {
            aula.setId(id);
            if (aula.getStatus() == null || aula.getStatus().isEmpty()) {
                aula.setStatus("Pendente");
            }

            aulaService.saveAula(aula);

            redirectAttributes.addFlashAttribute("mensagem", "Aula reagendada com sucesso!");
            redirectAttributes.addFlashAttribute("mensagemTipo", "sucesso");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao reagendar aula: " + e.getMessage());
            redirectAttributes.addFlashAttribute("mensagemTipo", "erro");
        }
        return "redirect:/web/aula/agendamento";
    }

    /**
     * Exclui uma aula com base no ID informado.
     */
    @PostMapping("/deletar/{id}")
    public String deletarAula(@PathVariable("id") String id) {
        aulaService.deleteAula(id);
        return "redirect:/web/aula/agendamento";
    }
}
