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

import br.studio.pilates.dto.AgendaInstrutorDTO;
import br.studio.pilates.service.AgendaInstrutorService;

@Controller
@RequestMapping("web/agendainstrutor")
public class AgendaInstrutorWebController {

	// @Autowired
	// private AgendaInstrutorService agendaInstrutorService;

	// @GetMapping("/home")
	// public String home() {
	// return "front-aluno/home";
	// }

	// @GetMapping("/list")
	// public String listarTodos(Model model) {
	// List<Aluno> alunos = agendaInstrutorService.listarTodos();
	// model.addAttribute("alunos", alunos);
	// return "front-aluno/consultar-aluno";
	// }

	// @GetMapping("/{id}")
	// public String getById(Model model, @PathVariable String id ) {
	// Optional<Aluno> alunos = agendaInstrutorService.getById(id);
	// model.addAttribute("alunos", alunos.get());
	// return "front-aluno/read-aluno";
	// }

	// @GetMapping("nome/{nome}")
	// public String getByNome(Model model, @PathVariable String nome ) {
	// Aluno alunos = agendaInstrutorService.getByNome(nome);
	// model.addAttribute("alunos", alunos);
	// return "front-aluno/read-aluno";
	// }

	// @GetMapping("cpf/{cpf}")
	// public String getByCpf(Model model, @PathVariable long cpf ) {
	// Aluno alunos = agendaInstrutorService.getByCpf(cpf);
	// model.addAttribute("alunos", alunos);
	// return "front-aluno/read-aluno";
	// }

	// @PostMapping("/save")
	// public String saveAluno(Aluno aluno) {
	// agendaInstrutorService.saveAluno(aluno);
	// return "redirect:/web/aluno/list";
	// }

	// @GetMapping("/new")
	// public String cadastrarAluno(Model model) {
	// model.addAttribute("aluno", new Aluno());
	// model.addAttribute("novo", true);
	// return "front-aluno/cadastrar-aluno";
	// }

	// @GetMapping("/editar/{id}")
	// public String editar(@PathVariable("id") String Id, Model model) {
	// Optional<Aluno> aluno = agendaInstrutorService.getById(Id);
	// model.addAttribute("aluno", aluno.get());
	// model.addAttribute("novo", false);
	// return "front-aluno/cadastrar-aluno"; //dar uma atenção aqui
	// }

	// @GetMapping("/deletar/{id}")
	// public String deleteByName(@PathVariable("id") String Id) {
	// agendaInstrutorService.deleteAluno(Id);
	// return "redirect:/web/aluno/list";
	// }

}