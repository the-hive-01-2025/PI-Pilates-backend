package br.studio.pilates.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.studio.pilates.model.entity.Aula;
import br.studio.pilates.service.AulaService;

@RestController
public class AulaController {

	@Autowired
	private AulaService aulaService;

	@GetMapping("aula")
	public List<Aula> listar() {
		return aulaService.getAllAulas();
	}

	@GetMapping("aula/{id}")
	public Aula getById(@PathVariable("id") String Id) {
		return aulaService.getAulaById(Id);
	}

	@PostMapping("aula")
	public Aula insert(@RequestBody Aula aula) {
		return aulaService.saveAula(aula);

	}

	@DeleteMapping("aula/{id}")
	public String delete(@PathVariable("id") String Id) {
		if(aulaService.getAulaById(Id) != null){

		aulaService.deleteAula(Id);
		return "Aula Excluido com sucesso!!";
		} else{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: recurso não encontrado!").toString();
	}
		}

}
