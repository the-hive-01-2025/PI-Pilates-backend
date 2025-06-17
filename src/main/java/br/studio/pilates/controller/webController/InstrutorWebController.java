package br.studio.pilates.controller.webController;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import br.studio.pilates.dto.AgendaInstrutorDTO;
import br.studio.pilates.model.entity.Aluno;
import br.studio.pilates.model.entity.FichaAvaliacao;
import br.studio.pilates.service.AgendaInstrutorService;
import br.studio.pilates.service.AlunoService;
import br.studio.pilates.service.FichaAvaliacaoService;
import jakarta.websocket.server.PathParam;

/**
 * Controller responsável pelas funcionalidades do painel do instrutor,
 * incluindo agenda, marcação de presença e avaliações dos alunos.
 */
@Controller
@RequestMapping("web/instrutor")
public class InstrutorWebController {

    @Autowired
    private AgendaInstrutorService agendaInstrutorService;

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private FichaAvaliacaoService fichaAvaliacaoService;

    /**
     * Página inicial (dashboard) do instrutor.
     */
    @GetMapping("/home")
    public String homeInstrutor() {
        return "instrutor/homeInstrutor";
    }

    /**
     * Exibe a agenda de aulas do instrutor com base no seu ID.
     */
    @GetMapping("/agenda/{id}")
    public String listarAulas(Model model, @PathVariable("id") String instrutorId) {
        List<AgendaInstrutorDTO> aulas = agendaInstrutorService.listarAulasPorInstrutor(instrutorId);
        model.addAttribute("aulas", aulas);
        return "instrutor/agenda";
    }

    /**
     * Marca presença do aluno em uma aula.
     */
    @PostMapping("/marcar-presenca")
    public String marcarPresenca(
            @RequestParam String idAula,
            @RequestParam String idAluno,
            @RequestParam boolean presente) {

        agendaInstrutorService.marcarPresenca(idAula, idAluno, presente);
        return "redirect:/web/instrutor/agenda/" + idAula;
    }

    /**
     * Cancela uma aula específica.
     */
    @PostMapping("/aulas/cancelar/{id}")
    public String cancelarAula(@PathVariable("id") String id) {
        agendaInstrutorService.cancelarAula(id);
        return "redirect:/web/instrutor/home";
    }

    /**
     * Exibe o formulário de avaliação física dos alunos.
     */
    @GetMapping("/avaliacao")
    public String exibirAvaliacao(Model model) {
        List<Aluno> alunos = alunoService.listarTodos();
        model.addAttribute("alunos", alunos);
        return "instrutor/avaliacao";
    }

    /**
     * Atualiza a ficha de avaliação de um aluno.
     */
    @PostMapping("/avaliacao/editar/{id}")
    public String atualizarAvaliacao(@PathVariable("id") String id, FichaAvaliacao ficha) {
        Optional<FichaAvaliacao> fichaAtual = fichaAvaliacaoService.getByAluno(id);
        fichaAvaliacaoService.atualizaFicha(id, fichaAtual.get());
        return "redirect:/web/instrutor/avaliacao";
    }

    /**
     * Adiciona uma patologia na ficha de avaliação do aluno.
     */
    @PostMapping("/avaliacao/pat/{id}")
    public String addPatologia(@PathVariable("id") String id, String patologia, FichaAvaliacao ficha) {
        fichaAvaliacaoService.addPat(id, patologia, ficha);
        return "redirect:/web/instrutor/avaliacao";
    }

    /**
     * Adiciona um medicamento na ficha de avaliação do aluno.
     */
    @PostMapping("/avaliacao/med/{id}")
    public String addMedicamento(@PathVariable("id") String id, String medicamentos, FichaAvaliacao ficha) {
        fichaAvaliacaoService.addMed(id, medicamentos, ficha);
        return "redirect:/web/instrutor/avaliacao";
    }

    /**
     * Adiciona um tratamento na ficha de avaliação do aluno.
     */
    @PostMapping("/avaliacao/trat/{id}")
    public String addTratamento(@PathVariable("id") String id, String tratamento, FichaAvaliacao ficha) {
        fichaAvaliacaoService.addTrat(id, tratamento, ficha);
        return "redirect:/web/instrutor/avaliacao";
    }

    /**
     * Adiciona um objetivo na ficha de avaliação do aluno.
     */
    @PostMapping("/avaliacao/obj/{id}")
    public String addObjetivo(@PathVariable("id") String id, String objetivo, FichaAvaliacao ficha) {
        fichaAvaliacaoService.addObj(id, objetivo, ficha);
        return "redirect:/web/instrutor/avaliacao";
    }
}
