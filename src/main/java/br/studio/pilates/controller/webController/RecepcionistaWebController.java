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

import br.studio.pilates.model.entity.Aluno;
import br.studio.pilates.model.entity.Usuario;
import br.studio.pilates.service.AlunoService;
import br.studio.pilates.service.UsuarioService;

@Controller
@RequestMapping("web/recepcionista")
public class RecepcionistaWebController {

	@Autowired
	private AlunoService alunoService;

	@Autowired
	private UsuarioService usuarioService;

	// home de recepcionista
	@GetMapping("/home")
	public String home() {
		return "/recepcionista/home";
	}

	// gerenciamento de alunos
	@GetMapping("/aluno/list")
	public String listAllAlunos(Model model) {
		List<Aluno> alunos = alunoService.listarTodos();
		model.addAttribute("aluno", alunos);
		return "recepcionista/consultar-aluno";
	}

	@GetMapping("/aluno/{id}")
	public String getByIdAluno(Model model, @PathVariable String id) {
		Optional<Aluno> alunos = alunoService.getById(id);
		model.addAttribute("aluno", alunos.get());
		return "recepcionista/read-aluno";
	}

	@GetMapping("aluno/nome/{nome}")
	public String getByNomeAluno(Model model, @PathVariable String nome) {
		Aluno alunos = alunoService.getByNome(nome);
		model.addAttribute("aluno", alunos);
		return "recepcionista/read-aluno";
	}

	@GetMapping("aluno/cpf/{cpf}")
	public String getByCpfAluno(Model model, @PathVariable String cpf) {
		Aluno alunos = alunoService.getByCpf(cpf);
		model.addAttribute("aluno", alunos);
		return "recepcionista/read-aluno";
	}

	@PostMapping("aluno/save")
	public String saveAluno(Aluno aluno) {
		alunoService.saveAluno(aluno);
		return "redirect:/web/recepcionista/aluno/list";
	}

	@GetMapping("aluno/new")
	public String cadastrarAluno(Model model) {
		model.addAttribute("aluno", new Aluno());
		model.addAttribute("novo", true);
		return "recepcionista/cadastrar-aluno";
	}

	@GetMapping("aluno/editar/{id}")
	public String editarAluno(@PathVariable("id") String Id, Model model) {
		Optional<Aluno> aluno = alunoService.getById(Id);

		if (aluno.isEmpty()) {
			// Se o aluno não existe, redireciona para a tela de cadastro
			return "redirect:/web/recepcionista/aluno/new";
		}

		model.addAttribute("aluno", aluno.get());
		model.addAttribute("novo", false);
		return "recepcionista/cadastrar-aluno";
	}

	
	@GetMapping("aluno/deletar/{id}")
	public String deleteByIdAluno(@PathVariable("id") String Id) {
		alunoService.deleteAluno(Id);
		return "redirect:/web/aluno/list";
	}

	// gerenciamento de instrutores (a partir daqui)
	@GetMapping("/instrutor/list")
	public String listarAllInsrutores(Model model) {
		List<Usuario> instrutores = usuarioService.listarTodos();
		model.addAttribute("instrutor", instrutores);
		return "recepcionista/consultar-instrutor";
	}

	@GetMapping("/instrutor/{id}")
	public String getByIdInstrutor(Model model, @PathVariable String id) {
		Usuario instrutores = usuarioService.getById(id);
		model.addAttribute("Instrutor", instrutores);
		return "recepcionista/read-instrutor";
	}

	@GetMapping("instrutor/cpf/{cpf}")
	public String getByCpfInstrutor(Model model, @PathVariable Long cpf) {
		Usuario instrutores = usuarioService.getByCpf(cpf);
		model.addAttribute("instrutor", instrutores);
		return "recepcionista/read-instrutor";
	}

	@GetMapping("instrutor/nome/{nome}")
	public String getByNomeInstrutor(Model model, @PathVariable String nome) {
		Usuario instrutores = usuarioService.getByNome(nome);
		model.addAttribute("instrutor", instrutores);
		return "recepcionista/read-instrutor";
	}

	@PostMapping("instrutor/save")
	public String saveInstrutor(Usuario usuario) {
		usuarioService.saveUsuario(usuario);
		return "redirect:/web/recepcionista/instrutor/list";
	}

	@GetMapping("instrutor/new")
	public String cadastrarInstrutor(Model model) {
		model.addAttribute("instrutor", new Usuario());
		model.addAttribute("novo", true);
		return "recepcionista/cadastrar-instrutor";
	}

	@GetMapping("instrutor/editar/{id}")
	public String editarUsuario(@PathVariable("id") String Id, Model model) {
		Usuario instrutores = usuarioService.getById(Id);

		if (instrutores.getId() == null) {

			return "redirect:/web/recepcionista/instrutor/new";
		}

		model.addAttribute("instrutor", instrutores);
		model.addAttribute("novo", false);
		return "recepcionista/cadastrar-instrutor";
	}

	@GetMapping("/instrutor/deletar/{id}")
	public String deleteByIdUsuario(@PathVariable("id") String Id) {
		usuarioService.deleteUsuario(Id);
		return "redirect:/web/instrutor/list";
	}
}