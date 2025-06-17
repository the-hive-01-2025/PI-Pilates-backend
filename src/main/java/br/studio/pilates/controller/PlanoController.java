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

/**
 * Controller REST para gerenciamento dos Planos.
 * Fornece endpoints para listar, buscar, inserir e excluir Planos.
 */
@RestController("/api")
public class PlanoController {

    @Autowired
    private PlanoService planoService;

    /**
     * Retorna a lista de todos os planos cadastrados.
     * 
     * @return Lista de objetos Plano
     */
    @GetMapping("/plano")
    public List<Plano> listar() {
        return planoService.getAllPlanos();
    }

    /**
     * Busca um Plano pelo seu ID.
     * 
     * @param id Identificador do Plano
     * @return ResponseEntity com o Plano encontrado ou status 404 caso não exista
     */
    @GetMapping("plano/{id}")
    public ResponseEntity<Plano> getById(@PathVariable("id") String id) {
        return planoService.getPlanoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Insere um novo Plano no sistema.
     * 
     * @param plano Objeto Plano a ser salvo
     * @return Plano salvo com ID gerado
     */
    @PostMapping("plano")
    public Plano insert(@RequestBody Plano plano) {
        return planoService.savePlano(plano);
    }

    /**
     * Exclui um Plano pelo seu ID.
     * 
     * @param id Identificador do Plano a ser excluído
     * @return ResponseEntity com mensagem de sucesso ou erro (404) caso o Plano não exista
     */
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
