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

import br.studio.pilates.model.entity.Plano;
import br.studio.pilates.service.PlanoService;

@RestController("/api")
public class PlanoController {

	@Autowired
	private PlanoService planoService;

	@GetMapping("/plano")
	public List<Plano> listar() {
		return planoService.getAllPlanos();
	}

	@GetMapping("plano/{id}")
	public ResponseEntity<Plano> getById(@PathVariable("id") String id) {
		return planoService.getPlanoById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping("plano")
	public Plano insert(@RequestBody Plano Plano) {
		return planoService.savePlano(Plano);

	}

	@DeleteMapping("plano/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") String id) {
		if (planoService.getPlanoById(id).isPresent()) {
			planoService.deletePlano(id);
			return ResponseEntity.ok("Plano excluído com sucesso!");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Erro: recurso não encontrado!");
		}
	}

}
