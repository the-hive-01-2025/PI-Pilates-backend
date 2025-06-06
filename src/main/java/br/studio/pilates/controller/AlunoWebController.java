package br.studio.pilates.controller;

import br.studio.pilates.dto.AulaAgendamentoDTO;
import br.studio.pilates.model.entity.Aluno;
import br.studio.pilates.model.entity.Aula;
import br.studio.pilates.model.entity.Estudio;
import br.studio.pilates.service.AlunoService;
import br.studio.pilates.service.AulaService;
import br.studio.pilates.service.EstudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("web/aluno")
public class AlunoWebController {

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private EstudioService estudioService;

    @Autowired
    private AulaService aulaService;

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
    public String getById(Model model, @PathVariable String id) {
        Optional<Aluno> aluno = alunoService.getById(id);
        if (aluno.isPresent()) {
            model.addAttribute("aluno", aluno.get());
            return "front-aluno/read-aluno";
        }
        return "redirect:/web/aluno/list";
    }

    @GetMapping("nome/{nome}")
    public String getByNome(Model model, @PathVariable String nome) {
        Aluno aluno = alunoService.getByNome(nome);
        model.addAttribute("aluno", aluno);
        return "front-aluno/read-aluno";
    }

    @GetMapping("cpf/{cpf}")
    public String getByCpf(Model model, @PathVariable String cpf) {
        Aluno aluno = alunoService.getByCpf(cpf);
        model.addAttribute("aluno", aluno);
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
    public String editar(@PathVariable("id") String id, Model model) {
        Optional<Aluno> aluno = alunoService.getById(id);
        if (aluno.isEmpty()) {
            return "redirect:/web/aluno/new";
        }
        model.addAttribute("aluno", aluno.get());
        model.addAttribute("novo", false);
        return "front-aluno/cadastrar-aluno";
    }

    @GetMapping("/deletar/{id}")
    public String deleteByName(@PathVariable("id") String id) {
        alunoService.deleteAluno(id);
        return "redirect:/web/aluno/list";
    }

    @GetMapping("/agendamento")
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
                if (a1.getData() == null && a2.getData() == null) return 0;
                if (a1.getData() == null) return 1;
                if (a2.getData() == null) return -1;
                int cmp = a1.getData().compareTo(a2.getData());
                if (cmp != 0) return cmp;
                if (a1.getHorario() == null && a2.getHorario() == null) return 0;
                if (a1.getHorario() == null) return 1;
                if (a2.getHorario() == null) return -1;
                return a1.getHorario().compareTo(a2.getHorario());
            })
            .toList();

        model.addAttribute("aulas", aulasDTO);
        return "front-aluno/agendamento";
    }
}