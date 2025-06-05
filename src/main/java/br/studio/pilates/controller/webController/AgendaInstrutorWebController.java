package br.studio.pilates.controller.webController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.studio.pilates.dto.AgendaInstrutorDTO;
import br.studio.pilates.service.AgendaInstrutorService;
import br.studio.pilates.service.AulaService;

@Controller
@RequestMapping("web/agendainstrutor")
public class AgendaInstrutorWebController {

	@Autowired
	private AgendaInstrutorService agendaInstrutorService;

	@Autowired
	private AulaService aulaService;

	@GetMapping("/home")
	public String homeIntrutor() {
		return "instrutor/homeInstrutor";
	}

	@GetMapping("/agenda/{instrutorId}")
	public String listarAulas(Model model, @PathVariable("instrutorId") String instrutorId) {
		List<AgendaInstrutorDTO> aulas = agendaInstrutorService.listarAulasPorInstrutor(instrutorId);
		model.addAttribute("aulas", aulas);
		return "instrutor/agenda"; // Remove o "redirect"
	}

	// @PostMapping("/save")
	// public String saveAluno(Aluno aluno) {
	// agendaInstrutorService.saveAluno(aluno);
	// return "redirect:/web/aluno/list";
	// }

	// @GetMapping("/aulas/presenca/{aulaId}") // marcar presença
	// public String editar(@PathVariable("aulaId") String Id, Model model) {
	// List<AgendaInstrutorDTO> aula = agendaInstrutorService.listarAulaById(Id);
	// model.addAttribute("aula", aula.getClass());
	// model.addAttribute("presenca", true);
	// agendaInstrutorService.marcarPresenca(aula);
	// return "front-aluno/cadastrar-aluno"; //dar uma atenção aqui
	// }

	@PostMapping("/marcar-presenca")
	public String marcarPresenca(@RequestParam String idAula,
			@RequestParam String idAluno,
			@RequestParam boolean presente) {
		agendaInstrutorService.marcarPresenca(idAula, idAluno, presente);
		return "redirect:/web/agendainstrutor/agenda"; // Redireciona para a agenda
	}

	@PostMapping("/aulas/cancelar/{aulaId}")
	public String cancelarAula(@RequestParam String aulaId) {
		agendaInstrutorService.cancelarAula(aulaId);
		return "redirect:/web/agendainstrutor/agenda";
	}

}