package br.studio.pilates.controller.webController;

import java.time.LocalDate;
import java.util.ArrayList;
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
    public String listAllPlanos(Model model) {
        List<Plano> planos = planoService.getAllPlanos();
        model.addAttribute("planos", planos);
        return "aluno/planos";
    }

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
            return "redirect:/aluno/dashboard"; // Ou outra tela final
        }

        redirectAttributes.addFlashAttribute("error", "Erro ao vincular plano.");
        return "redirect:/aluno/planos";
    }

    @PostMapping("/assinar")
    public String assinarPlano(
            @RequestParam("planoId") String planoId,
            @RequestParam("nome") String nome,
            @RequestParam("cpf") String cpf,
            @RequestParam("formaPagamento") String formaPagamento,
            Model model) {
        Aluno aluno = alunoService.getByCpf(cpf);
        if (aluno == null) {
            model.addAttribute("erro", "Aluno não encontrado.");
            return "redirect:/web/aluno/planos";
        }

        Plano planoSelecionado = planoService.getPlanoById(planoId).orElse(null);
        if (planoSelecionado == null) {
            model.addAttribute("erro", "Plano não encontrado.");
            return "redirect:/web/aluno/planos";
        }

        // Vincula plano
        aluno.setPlano(planoSelecionado);

        // Cria a fatura
        // Financeiro fatura = new Financeiro();
        // fatura.setPlano(planoSelecionado);
        // fatura.setValor(planoSelecionado.getValor());
        // fatura.setDataVencimento(LocalDate.now());
        // fatura.setPaga(true);
        // fatura.setDataPagamento(LocalDate.now());
        // fatura.setFormaPagamento(formaPagamento); 

        // Adiciona ao histórico
        if (aluno.getHistoricoPagamento() == null)
            aluno.setHistoricoPagamento(new ArrayList<>());

        // aluno.getHistoricoPagamento().add(fatura);

        alunoService.saveAluno(aluno); 

        return "redirect:/web/aluno/faturas";
    }

}
