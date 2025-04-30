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
import br.studio.pilates.service.PlanoService;
import br.studio.pilates.model.entity.Plano;

@RestController
public class PlanoController {
	
	@Autowired
	private PlanoService planoService;

	//get por aluno?
	
	@GetMapping("/api/plano/{id}")
	public Plano getPlano(@PathVariable("id") String id) {
		return planoService.getPlano(id);
	}
		
	@DeleteMapping("/api/plano/{id}")
	public String delete(@PathVariable("id") String Id) {
		planoService.deleteplano(Id);
		return "Aluno Excluido com sucesso!!";
	}
	
	@PostMapping("/api/plano")
		public Plano insert(@RequestBody Plano plano) {
		return planoService.savePlano(plano);
	
	}
}
