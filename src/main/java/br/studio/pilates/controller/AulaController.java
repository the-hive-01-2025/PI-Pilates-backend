package br.studio.pilates.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.studio.pilates.dto.AulaDetalhadaDTO;
import br.studio.pilates.model.entity.Aula;
import br.studio.pilates.service.AulaService;

@RestController
public class AulaController {

    @Autowired
    private AulaService aulaService;

    @GetMapping("aula")
    public ResponseEntity<?> listar() {
        List<Aula> aulas = aulaService.getAllAulas();
        if (aulas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma aula marcada encontrada.");
        }
        return ResponseEntity.ok(aulas);
    }

	@GetMapping("aula/detalhada/{id}")
	public ResponseEntity<?> getAulaDetalhada(@PathVariable("id") String id) {
		AulaDetalhadaDTO dto = aulaService.getAulaDetalhadaById(id);
		if (dto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aula não encontrada para o id informado.");
		}
		return ResponseEntity.ok(dto);
	}

    @GetMapping("aula/filtro")
    public ResponseEntity<?> listarFiltrado(
        @RequestParam(required = false) LocalDate data,
        @RequestParam(required = false) String idInstrutor,
        @RequestParam(required = false) String alunos
    ) {
        List<Aula> aulas = aulaService.getAulasFiltradas(data, idInstrutor, alunos);
        if (aulas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma aula encontrada com os filtros informados.");
        }
        return ResponseEntity.ok(aulas);
    }

    @GetMapping("aula/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") String Id) {
        Aula aula = aulaService.getAulaById(Id);
        if (aula == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aula não encontrada para o id informado.");
        }
        return ResponseEntity.ok(aula);
    }

    @PostMapping("aula")
    public ResponseEntity<?> insert(@RequestBody Aula aula) {
        Aula novaAula = aulaService.saveAula(aula);
        return ResponseEntity.status(HttpStatus.CREATED).body("Aula marcada com sucesso! Id: " + novaAula.getId());
    }

    @PutMapping("aula/{id}")
    public ResponseEntity<?> update(@PathVariable("id") String id, @RequestBody Aula aulaAtualizada) {
        Aula aulaExistente = aulaService.getAulaById(id);
        if (aulaExistente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aula não encontrada para o id informado.");
        }
        try {
            aulaAtualizada.setId(id);
            Aula aulaSalva = aulaService.saveAula(aulaAtualizada);
            return ResponseEntity.ok("Aula alterada com sucesso! Id: " + aulaSalva.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Não foi possível realizar a alteração da aula.");
        }
    }

    @DeleteMapping("aula/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String Id) {
        Aula aula = aulaService.getAulaById(Id);
        if (aula == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível encontrar aula a ser deletada pelo id informado.");
        }
        aulaService.deleteAula(Id);
        return ResponseEntity.ok("Aula excluída com sucesso!");
    }
}
