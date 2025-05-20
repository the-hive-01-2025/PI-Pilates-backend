package br.studio.pilates.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.studio.pilates.model.entity.Aluno;

@Controller
public class AlunoWebController {
  @GetMapping("/aluno")
	public String listarTodos(Model model) {
		List<Aluno> alunos = alunoService.listarTodos();
		model.addAttribute("alunos", alunos);
		return "consultar-aluno";
	}
}
