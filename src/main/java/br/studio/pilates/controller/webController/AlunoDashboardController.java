package br.studio.pilates.controller.webController;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.studio.pilates.dto.AulaAgendamentoDTO;
import br.studio.pilates.model.entity.Aluno;
import br.studio.pilates.model.entity.Aula;
import br.studio.pilates.model.entity.Estudio;
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
    public String home() {
        return "aluno/home";
    }

    @GetMapping("/modalidades")
    public String modalidades() {
        return "aluno/modalidades";
    }

    @GetMapping("/aulas")
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
    public String planos(Model model) {
        // Carrega os planos disponíveis (você já faz isso)
        model.addAttribute("mensal", planoService.getPlanoByNome("Mensal"));
        model.addAttribute("trimestral", planoService.getPlanoByNome("Trimestral"));
        model.addAttribute("anual", planoService.getPlanoByNome("Anual"));

        // Buscar ou criar um aluno mock para testes
        Aluno alunoMock = alunoService.getByCpf("000.000.000-00");
        if (alunoMock == null) {
            alunoMock = new Aluno();
            alunoMock.setId("mock123");
            alunoMock.setNome("Guilherme Souza");
            alunoMock.setCpf("000.000.000-00");
            alunoMock.setPlano(planoService.getPlanoByNome("Mensal"));
            // alunoMock.setHistoricoPagamento(List.of(createPagamentoMock(alunoMock)));
            // Outros dados do aluno mock se precisar
        } else {
            if (alunoMock.getPlano() == null) {
                alunoMock.setPlano(planoService.getPlanoByNome("Mensal"));
            }
            if (alunoMock.getHistoricoPagamento() == null || alunoMock.getHistoricoPagamento().isEmpty()) {
                // alunoMock.setHistoricoPagamento(List.of(createPagamentoMock(alunoMock)));
            }
        }

        model.addAttribute("aluno", alunoMock);

        return "aluno/planos";
    }

    // Método auxiliar para criar um pagamento mock
    // private Financeiro createPagamentoMock(Aluno aluno) {
    // Financeiro pagamento = new Financeiro();
    // pagamento.setId("pag123");
    // pagamento.setAluno(aluno);
    // pagamento.setValor(aluno.getPlano().getValor());
    // pagamento.setDataVencimento(LocalDate.now().plusDays(10));
    // pagamento.setDataPagamento(LocalDate.now());
    // pagamento.setPaga(true);
    // pagamento.setFormaPagamento("Cartão");
    // return pagamento;
    // }

    @GetMapping("/planos/lista")
    public String listAllPlanos(Model model) {
        List<Plano> planos = planoService.getAllPlanos();
        model.addAttribute("planos", planos);
        return "aluno/planos";
    }

    @PostMapping("/{id}/plano")
    public String assignPlanoToAluno(
            @PathVariable("id") String alunoId,
            @RequestParam("id") String planoId,
            RedirectAttributes redirectAttributes) {

        Optional<Aluno> alunoOpt = alunoService.getById(alunoId);
        Optional<Plano> planoOpt = planoService.getPlanoById(planoId);

        if (alunoOpt.isPresent() && planoOpt.isPresent()) {
            Aluno aluno = alunoOpt.get();
            aluno.setPlano(planoOpt.get());
            alunoService.saveAluno(aluno);
            redirectAttributes.addFlashAttribute("success", "Plano vinculado com sucesso!");
            return "redirect:/web/aluno/planos";
        }

        redirectAttributes.addFlashAttribute("error", "Erro ao vincular plano.");
        return "redirect:/web/aluno/planos";
    }

    @PostMapping("/assinar")
    public String assinarPlano(
            @RequestParam("planoId") String planoId,
            @RequestParam("nome") String nome,
            @RequestParam("cpf") String cpf,
            @RequestParam("formaPagamento") String formaPagamento,
            Model model,
            RedirectAttributes redirectAttributes) {

        Aluno aluno = alunoService.getByCpf(cpf);
        if (aluno == null) {
            redirectAttributes.addFlashAttribute("erro", "Aluno não encontrado.");
            return "redirect:/web/aluno/planos";
        }

        Optional<Plano> planoOpt = planoService.getPlanoById(planoId);
        if (planoOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("erro", "Plano não encontrado.");
            return "redirect:/web/aluno/planos";
        }

        Plano plano = planoOpt.get();
        aluno.setPlano(plano);

        // Criação de fatura - IMPLEMENTAR FUTURAMENTE
        /*
         * Financeiro fatura = new Financeiro();
         * fatura.setPlano(plano);
         * fatura.setAluno(aluno);
         * fatura.setValor(plano.getValor());
         * fatura.setDataVencimento(LocalDate.now());
         * fatura.setDataPagamento(LocalDate.now());
         * fatura.setPaga(true);
         * fatura.setFormaPagamento(formaPagamento);
         * 
         * if (aluno.getHistoricoPagamento() == null) {
         * aluno.setHistoricoPagamento(new ArrayList<>());
         * }
         * 
         * aluno.getHistoricoPagamento().add(fatura);
         */

        alunoService.saveAluno(aluno);
        return "redirect:/web/aluno/faturas";
    }

}
