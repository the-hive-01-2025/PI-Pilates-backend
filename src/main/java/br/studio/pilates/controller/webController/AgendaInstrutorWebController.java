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
import br.studio.pilates.model.entity.Aluno;
import br.studio.pilates.model.entity.Aula;
import br.studio.pilates.service.AgendaInstrutorService;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("web/agendainstrutor")
public class AgendaInstrutorWebController {

	@Autowired
	private AgendaInstrutorService agendaInstrutorService;

	@GetMapping("/home")
	public String homeIntrutor() {
	return "instrutor/homeInstrutor";
	}

	@GetMapping("/listaraulas/{instrutorId}")
	public String listarAulas(Model model, @PathVariable String instrutorId) {
	List<AgendaInstrutorDTO> aulas = agendaInstrutorService.listarAulasPorInstrutor(instrutorId);
	model.addAttribute("aulas", aulas);
	return "instrutor/agendaInstrutor";
	}
	

	// @PostMapping("/save")
	// public String saveAluno(Aluno aluno) {
	// agendaInstrutorService.saveAluno(aluno);
	// return "redirect:/web/aluno/list";
	// }

	@GetMapping("/editar/{aulaId}")
	public String editar(@PathVariable("aulaId") String Id, Model model) {
	List<AgendaInstrutorDTO> aulas = agendaInstrutorService.listarAulaById(Id);
	model.addAttribute("aula", aulas.getStatus());
	model.addAttribute("novo", false);
	return "front-aluno/cadastrar-aluno"; //dar uma atenção aqui
	}

	@GetMapping("/deletar/{id}")
	public String deleteByName(@PathVariable("id") String Id) {
	agendaInstrutorService.deleteAluno(Id);
	return "redirect:/web/aluno/list";
	}

}