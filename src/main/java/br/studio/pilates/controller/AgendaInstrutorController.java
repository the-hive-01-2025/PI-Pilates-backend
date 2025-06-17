package br.studio.pilates.controller;

import br.studio.pilates.dto.AgendaInstrutorDTO;
import br.studio.pilates.model.entity.Aula;
import br.studio.pilates.service.AgendaInstrutorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsável pelo gerenciamento da agenda dos instrutores,
 * permitindo listar, atualizar e cancelar aulas.
 */
@Controller
@RequestMapping("/agendaInstrutor")
public class AgendaInstrutorController {

    private final AgendaInstrutorService agendaInstrutorService;

    /**
     * Injeção de dependência do serviço de agenda do instrutor.
     */
    public AgendaInstrutorController(AgendaInstrutorService agendaInstrutorService) {
        this.agendaInstrutorService = agendaInstrutorService;
    }

    // =========================================
    // =========== LISTAGEM DE AULAS ===========
    // =========================================

    /**
     * Lista todas as aulas de um instrutor específico.
     *
     * @param instrutorId ID do instrutor
     * @param model       Modelo para enviar dados para a view
     * @return Página Thymeleaf que exibe a agenda do instrutor
     */
    @GetMapping("/{instrutorId}")
    public String listarAulas(@PathVariable String instrutorId, Model model) {
        List<AgendaInstrutorDTO> aulas = agendaInstrutorService.listarAulasPorInstrutor(instrutorId);
        model.addAttribute("aulas", aulas);
        return "agendaInstrutor";
    }

    // =========================================
    // =========== CANCELAMENTO DE AULA ========
    // =========================================

    /**
     * Cancela uma aula pelo ID.
     *
     * @param id ID da aula a ser cancelada
     * @return Redireciona para a agenda do instrutor
     */
    @PostMapping("/cancelar/{id}")
    public String cancelarAula(@PathVariable String id) {
        agendaInstrutorService.cancelarAula(id);
        return "redirect:/agendaInstrutor";
    }

    // =========================================
    // =========== ATUALIZAÇÃO DE AULA =========
    // =========================================

    /**
     * Atualiza as informações de uma aula.
     *
     * @param aula Objeto Aula com os dados atualizados
     * @return Redireciona para a agenda do instrutor
     */
    @PostMapping("/atualizar")
    public String atualizarAula(@ModelAttribute Aula aula) {
        agendaInstrutorService.atualizarAula(aula);
        return "redirect:/agendaInstrutor";
    }
}
