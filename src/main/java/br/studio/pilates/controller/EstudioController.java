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

/**
 * Controller REST para gerenciamento dos Estúdios.
 * Disponibiliza endpoints para listar, buscar, inserir e deletar Estúdios.
 */
@RestController
public class EstudioController {

    @Autowired
    private EstudioService estudioService;

    /**
     * Retorna a lista de todos os estúdios cadastrados.
     * 
     * @return Lista de objetos Estudio
     */
    @GetMapping("estudio")
    public List<Estudio> listar() {
        return estudioService.getAllEstudio();
    }

    /**
     * Busca um Estúdio pelo seu ID.
     * 
     * @param Id Identificador do Estúdio
     * @return Objeto Estudio correspondente ao ID
     */
    @GetMapping("estudio/{id}")
    public Estudio getById(@PathVariable("id") String Id) {
        return estudioService.getEstudioById(Id);
    }

    /**
     * Busca um Estúdio pelo seu nome.
     * 
     * @param nome Nome do Estúdio
     * @return Objeto Estudio correspondente ao nome informado
     */
    @GetMapping("estudio/nome/{nome}")
    public Estudio getByNomeEstudio(@PathVariable String nome) {
        return estudioService.getByNome(nome);
    }

    /**
     * Insere um novo Estúdio no sistema.
     * 
     * @param estudio Objeto Estudio a ser salvo
     * @return Estudio salvo com ID gerado
     */
    @PostMapping("estudio")
    public Estudio insert(@RequestBody Estudio estudio) {
        return estudioService.saveEstudio(estudio);
    }

    /**
     * Exclui um Estúdio pelo seu ID.
     * 
     * @param Id Identificador do Estúdio a ser excluído
     * @return Mensagem de sucesso ou erro caso o Estúdio não seja encontrado
     */
    @DeleteMapping("estudio/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") String Id) {
        if (estudioService.getEstudioById(Id) != null) {
            estudioService.deleteEstudio(Id);
            return ResponseEntity.ok("Estudio excluído com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: recurso não encontrado!");
        }
    }

    /**
     * Exclui um Estúdio pelo seu nome.
     * 
     * @param nome Nome do Estúdio a ser excluído
     * @return Mensagem de sucesso ou erro caso o Estúdio não seja encontrado
     */
    @DeleteMapping("estudio/nome/{nome}")
    public ResponseEntity<String> deleteByName(@PathVariable String nome) {
        try {
            estudioService.deleteEstudioByName(nome);
            return ResponseEntity.ok("Estudio excluído com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Estudio não encontrado!");
        }
    }
}
