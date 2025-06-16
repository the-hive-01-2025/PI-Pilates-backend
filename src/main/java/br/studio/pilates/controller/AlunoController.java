package br.studio.pilates.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.studio.pilates.model.entity.Aluno;
import br.studio.pilates.service.AlunoService;

@RestController
@RequestMapping("/api")
public class AlunoController {


	@Autowired
	private AlunoService alunoService;

	@GetMapping("aluno")
	public List<Aluno> listar() {
		return alunoService.listarTodos();
	}


	@GetMapping("aluno/{id}")
	public Optional<Aluno> getById(@PathVariable("id") String Id) {
		return alunoService.getById(Id);
	}

	@GetMapping("aluno/cpf/{cpf}")
	public Aluno getByCpf(@PathVariable String cpf) {
		return alunoService.getByCpf(cpf);
	}
 
	@GetMapping("aluno/nome/{nome}")
	public Aluno getByNomeAluno(@PathVariable String nome) {
		return alunoService.getByNome(nome);
	}

	@GetMapping("aluno/primeironome/{nome}")
	public List<Aluno> getByFirstName(@PathVariable String nome) {
		return alunoService.getByPrimeiroNome(nome);
	}

	@PostMapping("/aluno")
	public ResponseEntity<?> cadastrarAluno(@RequestBody Aluno aluno) {
		try {
			Aluno novoAluno = alunoService.saveAluno(aluno);
			return ResponseEntity.ok(novoAluno);
		} catch (IllegalArgumentException e) {
			return ResponseEntity
					.status(HttpStatus.CONFLICT)
					.body("Não foi possível cadastrar o aluno: " + e.getMessage());
		}
	}
	
    @PutMapping("/aluno/{id}")
    public ResponseEntity<?> atualizarAluno(@PathVariable String id, @RequestBody Aluno aluno) {
        try {
            Aluno alunoAtualizado = alunoService.atualizarAluno(id, aluno);
            return ResponseEntity.ok(alunoAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Não foi possível atualizar o aluno: " + e.getMessage());
        }
    }

	@DeleteMapping("aluno/{id}")
	public String delete(@PathVariable("id") String Id) {
		if (alunoService.getById(Id) != null) {

			alunoService.deleteAluno(Id);
			return "Aluno Excluido com sucesso!!";
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: recurso não encontrado!").toString();
		}
	}

	@DeleteMapping("aluno/nome/{nome}")
	public String deleteByName(@PathVariable String nome) {
		try {
			alunoService.deleteAlunoByName(nome);
			return "Aluno Excluido com sucesso!!";
		} catch (Exception e) {
			return "Aluno nao encontrado!!";
		}
	}

}