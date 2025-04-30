package br.studio.pilates.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.studio.pilates.model.entity.Aluno;
import br.studio.pilates.service.AlunoService;


@RestController
public class AlunoController {
	
	@Autowired
	private AlunoService alunoService;
	
	@GetMapping("/aluno")
	public List<Aluno> listar(){
		return alunoService.listarTodos();
	}
	
	@GetMapping("aluno/{id}")
	public Aluno getByRaAluno(@PathVariable("id") String Id) {
		return alunoService.getById(Id);
	}
	
 	@GetMapping("aluno/nome/{nome}")
	public Aluno getByNomeAluno(@PathVariable("nome") String nome) {
		return alunoService.getByNome(nome);
	}

	@GetMapping("aluno/primeironome/{nome}")
	public List<Aluno> getByFirstName(@PathVariable("nome") String nome) {
		return alunoService.getByPrimeiroNome(nome);
	}
	
	@DeleteMapping("/api/alunos/{id}")
	public String delete(@PathVariable("id") String Id) {
		alunoService.deleteAluno(Id);
		return "Aluno Excluido com sucesso!!";
	}
	
	@PostMapping("/api/alunos")
		public Aluno insert(@RequestBody Aluno aluno) {
		return alunoService.saveAluno(aluno);
	
	}

}
