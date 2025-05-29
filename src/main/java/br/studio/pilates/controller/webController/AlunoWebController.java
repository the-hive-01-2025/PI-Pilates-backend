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
import br.studio.pilates.service.AlunoService;


@Controller
@RequestMapping("web/aluno")
public class AlunoWebController {

	@Autowired
	private AlunoService alunoService;
	
	@GetMapping("/home")
	public String home() {
		return "recepcionista/home";
	}

  	@GetMapping("/list")
	public String listarTodos(Model model) {
		List<Aluno> alunos = alunoService.listarTodos();
		model.addAttribute("aluno", alunos);
		return "recepcionista/consultar-aluno";
	}

	@GetMapping("/{id}")
	public String getById(Model model, @PathVariable String id ) {
		Optional<Aluno> alunos = alunoService.getById(id);		
		model.addAttribute("aluno", alunos.get());
		return "recepcionista/read-aluno";
	}

	@GetMapping("nome/{nome}")
	public String getByNome(Model model, @PathVariable String nome ) {
		Aluno alunos = alunoService.getByNome(nome);		
		model.addAttribute("aluno", alunos);
		return "recepcionista/read-aluno";
	}

	@GetMapping("cpf/{cpf}")
	public String getByCpf(Model model, @PathVariable String cpf ) {
		Aluno alunos = alunoService.getByCpf(cpf);		
		model.addAttribute("aluno", alunos);
		return "recepcionista/read-aluno";
	}

	@PostMapping("/save")
	public String saveAluno(Aluno aluno) {
		alunoService.saveAluno(aluno);
		return "redirect:/web/aluno/list";
	}

	@GetMapping("/new")
	public String cadastrarAluno(Model model) {
		model.addAttribute("aluno", new Aluno());
		model.addAttribute("novo", true);
		return "recepcionista/cadastrar-aluno";
	}

@GetMapping("/editar/{id}")
public String editar(@PathVariable("id") String Id, Model model) {
    Optional<Aluno> aluno = alunoService.getById(Id);
    
    if (aluno.isEmpty()) {
        // Se o aluno não existe, redireciona para a tela de cadastro
        return "redirect:/web/aluno/new";
    }

    model.addAttribute("aluno", aluno.get());
    model.addAttribute("novo", false);
    return "recepcionista/cadastrar-aluno";
}


	@GetMapping("/deletar/{id}")
	public String deleteByName(@PathVariable("id") String Id) {
		alunoService.deleteAluno(Id);
		return "redirect:/web/aluno/list";
	}

}