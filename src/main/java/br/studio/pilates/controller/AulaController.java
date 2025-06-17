package br.studio.pilates.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.studio.pilates.dto.AulaDetalhadaDTO;
import br.studio.pilates.model.entity.Aula;
import br.studio.pilates.service.AulaService;

/**
 * Controller REST para gerenciamento das aulas.
 * Fornece endpoints para CRUD e consultas filtradas.
 */
@RestController
@RequestMapping("/aula")
public class AulaController {

    @Autowired
    private AulaService aulaService;

    // ===========================================
    // =============== LISTAGEM ==================
    // ===========================================

    /**
     * Lista todas as aulas cadastradas.
     * Retorna 404 caso nenhuma aula seja encontrada.
     */
    @GetMapping
    public ResponseEntity<?> listar() {
        List<Aula> aulas = aulaService.getAllAulas();
        if (aulas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Nenhuma aula marcada encontrada.");
        }
        return ResponseEntity.ok(aulas);
    }

    /**
     * Busca uma aula detalhada pelo ID.
     * Retorna dados completos da aula (DTO).
     * Retorna 404 caso não exista aula com o ID informado.
     */
    @GetMapping("/detalhada/{id}")
    public ResponseEntity<?> getAulaDetalhada(@PathVariable String id) {
        AulaDetalhadaDTO dto = aulaService.getAulaDetalhadaById(id);
        if (dto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Aula não encontrada para o id informado.");
        }
        return ResponseEntity.ok(dto);
    }

    /**
     * Lista aulas filtradas pelos parâmetros opcionais.
     * Parâmetros possíveis: data, id do instrutor e alunos.
     * Retorna 404 caso não encontre aulas com os filtros aplicados.
     */
    @GetMapping("/filtro")
    public ResponseEntity<?> listarFiltrado(
            @RequestParam(required = false) LocalDate data,
            @RequestParam(required = false) String idInstrutor,
            @RequestParam(required = false) String alunos) {
        
        List<Aula> aulas = aulaService.getAulasFiltradas(data, idInstrutor, alunos);
        if (aulas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Nenhuma aula encontrada com os filtros informados.");
        }
        return ResponseEntity.ok(aulas);
    }

    /**
     * Busca uma aula pelo seu ID.
     * Retorna 404 se não encontrada.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") String id) {
        Aula aula = aulaService.getAulaById(id);
        if (aula == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Aula não encontrada para o id informado.");
        }
        return ResponseEntity.ok(aula);
    }

    // ===========================================
    // =============== INSERÇÃO ==================
    // ===========================================

    /**
     * Marca uma nova aula.
     * Retorna status 201 e o ID da aula criada.
     */
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Aula aula) {
        Aula novaAula = aulaService.saveAula(aula);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Aula marcada com sucesso! Id: " + novaAula.getId());
    }

    // ===========================================
    // ============== ATUALIZAÇÃO =================
    // ===========================================

    /**
     * Atualiza os dados de uma aula pelo ID.
     * Retorna 404 caso a aula não exista.
     * Retorna mensagem de erro 500 em caso de falha na atualização.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody Aula aulaAtualizada) {
        Aula aulaExistente = aulaService.getAulaById(id);
        if (aulaExistente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Aula não encontrada para o id informado.");
        }
        try {
            aulaAtualizada.setId(id);
            Aula aulaSalva = aulaService.saveAula(aulaAtualizada);
            return ResponseEntity.ok("Aula alterada com sucesso! Id: " + aulaSalva.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Não foi possível realizar a alteração da aula.");
        }
    }

    // ===========================================
    // ================ EXCLUSÃO =================
    // ===========================================

    /**
     * Exclui uma aula pelo ID.
     * Retorna 404 caso a aula não seja encontrada.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        Aula aula = aulaService.getAulaById(id);
        if (aula == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Não foi possível encontrar aula a ser deletada pelo id informado.");
        }
        aulaService.deleteAula(id);
        return ResponseEntity.ok("Aula excluída com sucesso!");
    }
}
