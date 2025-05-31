package br.studio.pilates.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.studio.pilates.dto.AulaAgendamentoDTO;
import br.studio.pilates.model.entity.Aluno;
import br.studio.pilates.model.entity.Aula;
import br.studio.pilates.model.entity.Estudio;
import br.studio.pilates.service.AlunoService;
import br.studio.pilates.service.AulaService;
import br.studio.pilates.service.EstudioService;


@Controller
@RequestMapping("web/aluno")
public class AlunoWebController {

	@Autowired
	private AlunoService alunoService;
	
	@GetMapping("/home")
	public String home() {
		return "front-aluno/home";
	}

  	@GetMapping("/list")
	public String listarTodos(Model model) {
		List<Aluno> alunos = alunoService.listarTodos();
		model.addAttribute("aluno", alunos);
		return "front-aluno/consultar-aluno";
	}

	@GetMapping("/{id}")
	public String getById(Model model, @PathVariable String id ) {
		Optional<Aluno> alunos = alunoService.getById(id);		
		model.addAttribute("aluno", alunos.get());
		return "front-aluno/read-aluno";
	}

	@GetMapping("nome/{nome}")
	public String getByNome(Model model, @PathVariable String nome ) {
		Aluno alunos = alunoService.getByNome(nome);		
		model.addAttribute("aluno", alunos);
		return "front-aluno/read-aluno";
	}

	@GetMapping("cpf/{cpf}")
	public String getByCpf(Model model, @PathVariable String cpf ) {
		Aluno alunos = alunoService.getByCpf(cpf);		
		model.addAttribute("aluno", alunos);
		return "front-aluno/read-aluno";
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
		return "front-aluno/cadastrar-aluno";
	}

@GetMapping("/editar/{id}")
public String editar(@PathVariable("id") String Id, Model model) {
    Optional<Aluno> aluno = alunoService.getById(Id);   
    if (aluno.isEmpty()) {
        return "redirect:/web/aluno/new";
    }
    model.addAttribute("aluno", aluno.get());
    model.addAttribute("novo", false);
    return "front-aluno/cadastrar-aluno";
}
	@GetMapping("/deletar/{id}")
	public String deleteByName(@PathVariable("id") String Id) {
		alunoService.deleteAluno(Id);
		return "redirect:/web/aluno/list";
	}
	@Autowired
	private EstudioService estudioService;

	@Autowired
	private AulaService aulaService; 

	@GetMapping("/agendamento")
	public String agendamento(Model model) {
		List<Aula> aulas = aulaService.getAllAulas();
		List<AulaAgendamentoDTO> aulasDTO = aulas.stream().map(aula -> {
			AulaAgendamentoDTO dto = new AulaAgendamentoDTO();
			dto.setId(aula.getId());
			dto.setData(aula.getData());
			dto.setHorario(aula.getHorario());
			dto.setStatus(aula.getStatus());
			// Modalidade (ainda não existe)
			dto.setModalidade("Não informado");
			String instrutorNome = "Não informado";
			// if (aula.getIdInstrutor() != null) {
			//     Usuario instrutor = usuarioService.getById(aula.getIdInstrutor());
			//     if (instrutor != null && instrutor.getNome() != null) {
			//         instrutorNome = instrutor.getNome();
			//     }
			// }
			dto.setInstrutorNome(instrutorNome);
			String nomeEstudio = "Não informado";
			if (aula.getIdStudio() != null) {
				Estudio estudio = estudioService.getEstudioById(aula.getIdStudio());
				if (estudio != null && estudio.getNome() != null) {
					nomeEstudio = estudio.getNome();
				}
			}
			dto.setEstudioNome(nomeEstudio);
			return dto;
		}).toList();
		List<Estudio> estudios = estudioService.getAllEstudio();
		model.addAttribute("estudios", estudios);
		model.addAttribute("aulas", aulasDTO);
		return "front-aluno/agendamento";
	}
}