package br.studio.pilates.controller.webController;

import java.security.Principal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.studio.pilates.dto.AulaAgendamentoDTO;
import br.studio.pilates.model.entity.*;
import br.studio.pilates.service.*;

/**
 * Controller responsável pelo dashboard do aluno na aplicação web.
 */
@Controller
@RequestMapping("web/aluno")
public class AlunoDashboardController {

    @Autowired
    private AulaService aulaService;

    @Autowired
    private EstudioService estudioService;

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private PlanoService planoService;

    /**
     * Página inicial do aluno.
     */
    @GetMapping("/home")
    public String home(Model model, Principal principal) {
        Aluno aluno = alunoService.getByEmail(principal.getName());
        model.addAttribute("aluno", aluno);
        return "aluno/home";
    }

    /**
     * Página de modalidades disponíveis.
     */
    @GetMapping("/modalidades")
    public String modalidades(Model model, Principal principal) {
        Aluno aluno = alunoService.getByEmail(principal.getName());
        model.addAttribute("aluno", aluno);
        return "aluno/modalidades";
    }

    /**
     * Página de aulas agendadas do aluno.
     */
    @GetMapping("/aulas")
    public String agendamento(Model model, Principal principal) {
        Aluno aluno = alunoService.getByEmail(principal.getName());

        List<Aula> aulas = aulaService.getAulasFiltradas(null, null, aluno.getId());
        List<Estudio> estudios = estudioService.getAllEstudio();

        List<AulaAgendamentoDTO> aulasDTO = aulas.stream().map(aula -> {
            AulaAgendamentoDTO dto = new AulaAgendamentoDTO();
            dto.setId(aula.getId());
            dto.setData(aula.getData());
            dto.setHorario(aula.getHorario());
            dto.setStatus(aula.getStatus());
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
            return dto;
        }).sorted(Comparator.comparing(AulaAgendamentoDTO::getData, Comparator.nullsLast(Comparator.naturalOrder()))
                .thenComparing(AulaAgendamentoDTO::getHorario, Comparator.nullsLast(Comparator.naturalOrder())))
                .toList();

        model.addAttribute("aluno", aluno);
        model.addAttribute("estudios", estudios);
        model.addAttribute("aulas", aulasDTO);
        return "aluno/aulas";
    }

    /**
     * Página de visualização dos planos disponíveis.
     */
    @GetMapping("/planos")
    public String planos(Model model, Principal principal) {
        Aluno aluno = alunoService.getByEmail(principal.getName());

        model.addAttribute("aluno", aluno);
        model.addAttribute("mensal", planoService.getPlanoByNome("Mensal"));
        model.addAttribute("trimestral", planoService.getPlanoByNome("Trimestral"));
        model.addAttribute("anual", planoService.getPlanoByNome("Anual"));

        return "aluno/planos";
    }

    /**
     * Página de listagem de todos os planos (geral).
     */
    @GetMapping("/planos/lista")
    public String listAllPlanos(Model model) {
        List<Plano> planos = planoService.getAllPlanos();
        model.addAttribute("planos", planos);
        return "aluno/planos";
    }

    /**
     * Vincula um plano a um aluno específico (usado por administradores).
     */
    @PostMapping("/{id}/plano")
    public String assignPlanoToAluno(@PathVariable("id") String alunoId,
                                     @RequestParam("id") String planoId,
                                     RedirectAttributes redirectAttributes) {

        Optional<Aluno> alunoOpt = alunoService.getById(alunoId);
        Optional<Plano> planoOpt = planoService.getPlanoById(planoId);

        if (alunoOpt.isPresent() && planoOpt.isPresent()) {
            Aluno aluno = alunoOpt.get();
            aluno.setPlano(planoOpt.get());
            alunoService.saveAluno(aluno);
            redirectAttributes.addFlashAttribute("success", "Plano vinculado com sucesso!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Erro ao vincular plano.");
        }

        return "redirect:/web/aluno/planos";
    }

    /**
     * Permite que o aluno assine um plano, gera a fatura e salva no histórico.
     */
    @PostMapping("/assinar")
    public String assinarPlano(@RequestParam String planoId,
                               @RequestParam String cpf,
                               @RequestParam String formaPagamento,
                               Principal principal,
                               RedirectAttributes redirectAttributes) {

        Aluno aluno = alunoService.getByEmail(principal.getName());

        if (aluno == null) {
            redirectAttributes.addFlashAttribute("error", "Aluno não encontrado.");
            return "redirect:/web/aluno/planos";
        }

        if (!aluno.getCpf().equals(cpf)) {
            redirectAttributes.addFlashAttribute("error", "CPF inválido.");
            return "redirect:/web/aluno/planos";
        }

        Plano plano = planoService.getPlanoById(planoId)
                .orElseThrow(() -> new RuntimeException("Plano não encontrado."));

        aluno.setPlano(plano);

        Financeiro fatura = new Financeiro();
        fatura.setAluno(aluno);
        fatura.setPlano(plano);
        fatura.setDataPagamento(LocalDate.now());
        fatura.setValor(plano.getValor());
        fatura.setPaga(true);
        fatura.setFormaPagamento(formaPagamento.toUpperCase());

        if (aluno.getHistoricoPagamento() == null) {
            aluno.setHistoricoPagamento(new ArrayList<>());
        }
        aluno.getHistoricoPagamento().add(fatura);

        alunoService.saveAluno(aluno);

        redirectAttributes.addFlashAttribute("success", "Plano assinado com sucesso!");

        return "redirect:/web/aluno/planos";
    }

    /**
     * Página de visualização das faturas (pagas e em aberto) do aluno.
     */
    @GetMapping("/faturas")
    public String visualizarFaturas(Model model, Principal principal, RedirectAttributes redirectAttributes) {
        Aluno aluno = alunoService.getByEmail(principal.getName());

        if (aluno == null) {
            redirectAttributes.addFlashAttribute("erro", "Aluno não encontrado.");
            return "redirect:/web/aluno/planos";
        }

        List<Financeiro> faturas = Optional.ofNullable(aluno.getHistoricoPagamento()).orElse(new ArrayList<>());

        List<Financeiro> pagas = faturas.stream()
                .filter(Financeiro::getPaga)
                .sorted(Comparator.comparing(Financeiro::getDataPagamento).reversed())
                .collect(Collectors.toList());

        List<Financeiro> emAberto = faturas.stream()
                .filter(f -> !Boolean.TRUE.equals(f.getPaga()))
                .sorted(Comparator.comparing(Financeiro::getDataVencimento).reversed())
                .collect(Collectors.toList());

        model.addAttribute("aluno", aluno);
        model.addAttribute("faturasPagas", pagas);
        model.addAttribute("faturasEmAberto", emAberto);
        model.addAttribute("ultimaFatura", pagas.isEmpty() ? null : pagas.get(0));
        model.addAttribute("proximaFatura", emAberto.isEmpty() ? null : emAberto.get(0));
        model.addAttribute("qtdAtrasadas", emAberto.size());

        return "aluno/faturas";
    }
}
