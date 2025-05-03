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

import br.studio.pilates.model.entity.Estudio;
import br.studio.pilates.service.EstudioService;


@RestController
public class EstudioController {

	@Autowired
	private EstudioService estudioService;

	@GetMapping("estudio")
	public List<Estudio> listar() {
		return estudioService.getAllEstudio();
	}

	@GetMapping("estudio/{id}")
	public Estudio getById(@PathVariable("id") String Id) {
		return estudioService.getEstudioById(Id);
	}

	@GetMapping("estudio/nome/{nome}")
	public Estudio getByNomeEstudio(@PathVariable("nome") String nome) {
		return estudioService.getByNome(nome);
	}

	@PostMapping("estudio")
	public Estudio insert(@RequestBody Estudio estudio) {
		return estudioService.saveEstudio(estudio);

	}

	@DeleteMapping("estudio/{id}")
	public String delete(@PathVariable("id") String Id) {
		if(estudioService.getEstudioById(Id) != null){

		estudioService.deleteEstudio(Id);
		return "Estudio Excluido com sucesso!!";
		} else{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: recurso não encontrado!").toString();
	}
		}
	@DeleteMapping("estudio/nome/{nome}")
	public String deleteByName(@PathVariable("nome") String nome) {
		try {estudioService.deleteEstudioByName(nome);
		return "Estudio Excluido com sucesso!!";
		}
		catch (Exception e) {
			return "Estudio nao encontrado!!";
		}
	}

}
