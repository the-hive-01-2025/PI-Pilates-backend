package br.studio.pilates.controller.webController;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import br.studio.pilates.model.entity.Aluno;
import br.studio.pilates.model.entity.Usuario;
import br.studio.pilates.service.AlunoService;
import br.studio.pilates.service.UsuarioService;

/**
 * Controller responsável pelas funcionalidades da interface web do Recepcionista,
 * incluindo o gerenciamento de alunos e instrutores.
 */
@Controller
@RequestMapping("web/recepcionista")
public class RecepcionistaWebController {

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private UsuarioService usuarioService;

    // ======================================
    // ============ HOME ====================
    // ======================================

    /**
     * Página inicial da recepção.
     */
    @GetMapping("/home")
    public String home(Model model) {
        Usuario usuario = usuarioService.getUsuarioLogado();
        model.addAttribute("usuario", usuario);
        return "recepcionista/home";
    }

    // ======================================
    // ====== GERENCIAMENTO DE ALUNOS =======
    // ======================================

    /**
     * Lista todos os alunos cadastrados.
     */
    @GetMapping("/aluno/list")
    public String listAllAlunos(Model model) {
        List<Aluno> alunos = alunoService.listarTodos();
        model.addAttribute("aluno", alunos);
        return "recepcionista/consultar-aluno";
    }

    /**
     * Consulta aluno por ID e exibe seus detalhes.
     */
    @GetMapping("/aluno/{id}")
    public String getByIdAluno(Model model, @PathVariable String id) {
        Aluno aluno = alunoService.getById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado"));

        // Formatação da data de nascimento
        String dataNascimentoFormatada = (aluno.getData() != null)
                ? aluno.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                : "Não informado";

        // Formatação das aulas marcadas
        List<String> aulasFormatadas = Optional.ofNullable(aluno.getAulasMarcadas())
                .orElse(List.of())
                .stream()
                .map(aula -> aula.getData() != null
                        ? aula.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                        : "Data não informada")
                .toList();

        // Formatação do histórico de pagamentos
        List<String> pagamentosFormatados = Optional.ofNullable(aluno.getHistoricoPagamento())
                .orElse(List.of())
                .stream()
                .map(pagamento -> pagamento.getDataPagamento() != null
                        ? pagamento.getDataPagamento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                        : "Data não informada")
                .toList();

        model.addAttribute("aluno", aluno);
        model.addAttribute("dataFormatada", dataNascimentoFormatada);
        model.addAttribute("aulasFormatadas", aulasFormatadas);
        model.addAttribute("pagamentosFormatados", pagamentosFormatados);

        return "recepcionista/read-aluno";
    }

    /**
     * Busca aluno pelo nome.
     */
    @GetMapping("/aluno/nome/{nome}")
    public String getByNomeAluno(Model model, @PathVariable String nome) {
        Aluno alunos = alunoService.getByNome(nome);
        model.addAttribute("aluno", alunos);
        return "recepcionista/read-aluno";
    }

    /**
     * Busca aluno pelo CPF.
     */
    @GetMapping("/aluno/cpf/{cpf}")
    public String getByCpfAluno(Model model, @PathVariable String cpf) {
        Aluno alunos = alunoService.getByCpf(cpf);
        model.addAttribute("aluno", alunos);
        return "recepcionista/read-aluno";
    }

    /**
     * Salva um novo aluno ou atualiza um existente.
     */
    @PostMapping("/aluno/save")
    public String saveAluno(Aluno aluno) {
        alunoService.saveAluno(aluno);
        return "redirect:/web/recepcionista/aluno/" + aluno.getId();
    }

    /**
     * Abre a página de cadastro de novo aluno.
     */
    @GetMapping("/aluno/new")
    public String cadastrarAluno(Model model) {
        model.addAttribute("aluno", new Aluno());
        model.addAttribute("novo", true);
        return "recepcionista/cadastrar-aluno";
    }

    /**
     * Abre a página de edição de um aluno existente.
     */
    @GetMapping("/aluno/editar/{id}")
    public String editarAluno(@PathVariable("id") String id, Model model) {
        Optional<Aluno> aluno = alunoService.getById(id);

        if (aluno.isEmpty()) {
            return "redirect:/web/recepcionista/aluno/new";
        }

        model.addAttribute("aluno", aluno.get());
        model.addAttribute("novo", false);
        return "recepcionista/cadastrar-aluno";
    }

    /**
     * Exclui um aluno pelo ID.
     */
    @GetMapping("/aluno/deletar/{id}")
    public String deleteByIdAluno(@PathVariable("id") String id) {
        alunoService.deleteAluno(id);
        return "redirect:/web/recepcionista/aluno/list";
    }

    // ======================================
    // ===== GERENCIAMENTO DE INSTRUTORES ===
    // ======================================

    /**
     * Lista todos os instrutores cadastrados.
     */
    @GetMapping("/instrutor/list")
    public String listarAllInstrutores(Model model) {
        List<Usuario> instrutores = usuarioService.listarTodos();
        model.addAttribute("instrutor", instrutores);
        return "recepcionista/consultar-instrutor";
    }

    /**
     * Consulta um instrutor por ID.
     */
    @GetMapping("/instrutor/{id}")
    public String getByIdInstrutor(Model model, @PathVariable String id) {
        Usuario instrutor = usuarioService.getById(id);

        if (instrutor == null || instrutor.getId() == null) {
            return "redirect:/web/recepcionista/instrutor/list";
        }

        model.addAttribute("instrutor", instrutor);
        return "recepcionista/read-instrutor";
    }

    /**
     * Busca instrutor pelo CPF.
     */
    @GetMapping("/instrutor/cpf/{cpf}")
    public String getByCpfInstrutor(Model model, @PathVariable String cpf) {
        Usuario instrutor = usuarioService.getByCpf(cpf);
        model.addAttribute("instrutor", instrutor);
        return "recepcionista/read-instrutor";
    }

    /**
     * Busca instrutor pelo nome.
     */
    @GetMapping("/instrutor/nome/{nome}")
    public String getByNomeInstrutor(Model model, @PathVariable String nome) {
        Usuario instrutor = usuarioService.getByNome(nome);
        model.addAttribute("instrutor", instrutor);
        return "recepcionista/read-instrutor";
    }

    /**
     * Salva um novo instrutor ou atualiza um existente.
     */
    @PostMapping("/instrutor/save")
    public String saveInstrutor(Usuario usuario) {
        usuarioService.saveUsuario(usuario);
        return "redirect:/web/recepcionista/instrutor/" + usuario.getId();
    }

    /**
     * Abre a página de cadastro de novo instrutor.
     */
    @GetMapping("/instrutor/new")
    public String cadastrarInstrutor(Model model) {
        model.addAttribute("instrutor", new Usuario());
        model.addAttribute("novoInstrutor", true);
        return "recepcionista/cadastrar-instrutor";
    }

    /**
     * Abre a página de edição de um instrutor existente.
     */
    @GetMapping("/instrutor/editar/{id}")
    public String editarInstrutor(@PathVariable("id") String id, Model model) {
        Usuario instrutor = usuarioService.getById(id);

        if (instrutor.getId() == null) {
            return "redirect:/web/recepcionista/instrutor/new";
        }

        model.addAttribute("instrutor", instrutor);
        model.addAttribute("novoInstrutor", false);
        return "recepcionista/cadastrar-instrutor";
    }

    /**
     * Exclui um instrutor pelo ID.
     */
    @GetMapping("/instrutor/deletar/{id}")
    public String deleteByIdInstrutor(@PathVariable("id") String id) {
        usuarioService.deleteUsuario(id);
        return "redirect:/web/recepcionista/instrutor/list";
    }
}
