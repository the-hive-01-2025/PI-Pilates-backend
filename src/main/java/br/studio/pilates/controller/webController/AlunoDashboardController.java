package br.studio.pilates.controller.webController;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.studio.pilates.dto.AulaAgendamentoDTO;
import br.studio.pilates.model.entity.Aluno;
import br.studio.pilates.model.entity.Aula;
import br.studio.pilates.model.entity.Estudio;
import br.studio.pilates.model.entity.Financeiro;
import br.studio.pilates.model.entity.Plano;
import br.studio.pilates.service.AulaService;
import br.studio.pilates.service.EstudioService;
import br.studio.pilates.service.AlunoService;
import br.studio.pilates.service.PlanoService;


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


    @GetMapping("/home")
    public String home(Model model, Principal principal) {
        String email = principal.getName(); // pega o email do aluno logado
        Aluno aluno = alunoService.getByEmail(email); // cria esse método no service

        model.addAttribute("aluno", aluno);
        return "aluno/home";
    }

    @GetMapping("/modalidades")
    public String modalidades(Model model, Principal principal) {
        String email = principal.getName(); // pega o email do aluno logado
        Aluno aluno = alunoService.getByEmail(email); // cria esse método no service

        model.addAttribute("aluno", aluno);
        return "aluno/modalidades";
    }

    @GetMapping("/aulas")
    public String agendamento(Model model, Principal principal) {
        String email = principal.getName(); // pega o email do aluno logado
        Aluno aluno = alunoService.getByEmail(email); // cria esse método no service

        model.addAttribute("aluno", aluno);
        // Obtém todas as aulas, estudos e alunos para exibir na página
        List<Aula> aulas = aulaService.getAulasFiltradas(null, null, aluno.getId());
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
                })
                .sorted((a1, a2) -> {
                    if (a1.getData() == null && a2.getData() == null)
                        return 0;
                    if (a1.getData() == null)
                        return 1;
                    if (a2.getData() == null)
                        return -1;
                    int cmp = a1.getData().compareTo(a2.getData());
                    if (cmp != 0)
                        return cmp;
                    if (a1.getHorario() == null && a2.getHorario() == null)
                        return 0;
                    if (a1.getHorario() == null)
                        return 1;
                    if (a2.getHorario() == null)
                        return -1;
                    return a1.getHorario().compareTo(a2.getHorario());
                })
                .toList();

        model.addAttribute("aulas", aulasDTO);
        return "aluno/aulas";
    }

    @GetMapping("/planos")
    public String planos(Model model, Principal principal, @ModelAttribute("aluno") Aluno aluno) {
        String email = principal.getName();
        aluno = alunoService.getByEmail(email);

        model.addAttribute("aluno", aluno);
        model.addAttribute("mensal", planoService.getPlanoByNome("Mensal"));
        model.addAttribute("trimestral", planoService.getPlanoByNome("Trimestral"));
        model.addAttribute("anual", planoService.getPlanoByNome("Anual"));

        return "aluno/planos";
    }


    @GetMapping("/planos/lista")
    public String listAllPlanos(Model model) {
        List<Plano> planos = planoService.getAllPlanos();
        model.addAttribute("planos", planos);
        return "aluno/planos";
    }

    // Esse método pode ser útil para admin, mas não precisa de aluno da sessão
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

    @PostMapping("/assinar")
    public String assinarPlano(
            @RequestParam String planoId,
            @RequestParam String cpf,
            @RequestParam String formaPagamento,
            Principal principal,
            RedirectAttributes redirectAttributes) {

        String email = principal.getName();
        Aluno aluno = alunoService.getByEmail(email);

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


    @GetMapping("/faturas")
    public String visualizarFaturas(@ModelAttribute("aluno") Aluno aluno,
            Model model, Principal principal,
            RedirectAttributes redirectAttributes) {

        String email = principal.getName(); // pega o email do aluno logado
        aluno = alunoService.getByEmail(email); // cria esse método no service
       

        model.addAttribute("aluno", aluno);

        if (aluno == null) {
            redirectAttributes.addFlashAttribute("erro", "Aluno não encontrado.");
            return "redirect:/web/aluno/planos";
        }

        List<Financeiro> faturas = aluno.getHistoricoPagamento();
        if (faturas == null)
            faturas = new ArrayList<>();

        List<Financeiro> pagas = faturas.stream()
                .filter(f -> Boolean.TRUE.equals(f.getPaga()))
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
