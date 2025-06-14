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

import br.studio.pilates.dto.AgendaInstrutorDTO;
import br.studio.pilates.model.entity.Aluno;
import br.studio.pilates.model.entity.FichaAvaliacao;
import br.studio.pilates.service.AgendaInstrutorService;
import br.studio.pilates.service.AlunoService;
import br.studio.pilates.service.FichaAvaliacaoService;
import jakarta.websocket.server.PathParam;

@Controller
@RequestMapping("web/instrutor")
public class InstrutorWebController {

	@Autowired
	private AgendaInstrutorService agendaInstrutorService;

	@Autowired
	private AlunoService alunoService;

	@Autowired
	private FichaAvaliacaoService fichaAvaliacaoService;
	// @Autowired
	// private Aluno aluno;

	@GetMapping("/home")
	public String homeIntrutor() {
		return "instrutor/homeInstrutor";
	}

	@GetMapping("/agenda/{id}")
	public String listarAulas(Model model, @PathVariable("id") String instrutorId) {
		List<AgendaInstrutorDTO> aulas = agendaInstrutorService.listarAulasPorInstrutor(instrutorId);
		model.addAttribute("aulas", aulas);

		return "instrutor/agenda"; // Remove o "redirect"
	}

	// @GetMapping("/agenda")
	// public String listarTodasAulas(Model model) {
	// 	List<AgendaInstrutorDTO> aulas = agendaInstrutorService.listarTodasAulas();
	// 	List<Aluno> aluno = alunoService.listarTodos();
	// 	model.addAttribute("aulas", aulas);
	// 	model.addAttribute("aluno", aluno);
	// 	return "instrutor/agenda";
	// }

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

	@PostMapping("/aulas/cancelar/{id}")
	public String cancelarAula(@PathParam("id") String id) {
		agendaInstrutorService.cancelarAula(id);
		return "redirect:/web/agendainstrutor/agenda";
	}

	@GetMapping("/avaliacao")
	public String exibirAvaliacao(Model model, FichaAvaliacao ficha) {
		// Optional<FichaAvaliacao> fichaAtual = fichaAvaliacaoService.getByAluno(id);
		List<Aluno> alunos = alunoService.listarTodos();
		model.addAttribute("alunos", alunos);

		return "instrutor/avaliacao";
	}

	@PostMapping("/avaliacao/editar/{id}")
	public String atualizarAvaliacao(@PathParam("id") String id, FichaAvaliacao ficha) {
		Optional<FichaAvaliacao> fichaAtual = fichaAvaliacaoService.getByAluno(id);
		fichaAvaliacaoService.atualizaFicha(id, fichaAtual.get());
		return "redirect:/web/instrutor/avaliacao";
	}

	@PostMapping("/avaliacao/pat/{id}")
	public String addPat(@PathParam("id") String id, String patologia, FichaAvaliacao ficha) {
		fichaAvaliacaoService.addPat(id, patologia, ficha);
		return "redirect:/web/instrutor/avaliacao";
	}

	@PostMapping("/avaliacao/med/{id}")
	public String addMed(@PathParam("id") String id, String medicamentos, FichaAvaliacao ficha) {
		fichaAvaliacaoService.addMed(id, medicamentos, ficha);
		return "redirect:/web/instrutor/avaliacao";
	}

	@PostMapping("/avaliacao/trat/{id}")
	public String addTratamentos(@PathParam("id") String id, String tratamento, FichaAvaliacao ficha) {
		fichaAvaliacaoService.addTrat(id, tratamento, ficha);
		return "redirect:/web/instrutor/avaliacao";
	}

	@PostMapping("/avaliacao/obj/{id}")
	public String addObjetivo(@PathParam("id") String id, String objetivo, FichaAvaliacao ficha) {
		fichaAvaliacaoService.addObj(id, objetivo, ficha);
		return "redirect:/web/instrutor/avaliacao";
	}

}