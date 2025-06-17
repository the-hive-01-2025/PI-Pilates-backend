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

import br.studio.pilates.model.entity.Financeiro;
import br.studio.pilates.service.FinanceiroService;

/**
 * Controller REST para gerenciamento dos registros financeiros.
 * Disponibiliza endpoints para listar, buscar, inserir e excluir registros financeiros.
 */
@RestController
public class FinanceiroController {

    @Autowired
    private FinanceiroService financeiroService;

    /**
     * Retorna a lista de todos os registros financeiros cadastrados.
     * 
     * @return Lista de objetos Financeiro
     */
    @GetMapping("financeiro")
    public List<Financeiro> listar() {
        return financeiroService.getAllFinanceiro();
    }

    /**
     * Busca um registro financeiro pelo seu ID.
     * 
     * @param Id Identificador do registro financeiro
     * @return Objeto Financeiro correspondente ao ID
     */
    @GetMapping("financeiro/{id}")
    public Financeiro getById(@PathVariable("id") String Id) {
        return financeiroService.getFinanceiroById(Id);
    }

    /**
     * Insere um novo registro financeiro no sistema.
     * 
     * @param financeiro Objeto Financeiro a ser salvo
     * @return Financeiro salvo com ID gerado
     */
    @PostMapping("financeiro")
    public Financeiro insert(@RequestBody Financeiro financeiro) {
        return financeiroService.saveFinanceiro(financeiro);
    }

    /**
     * Exclui um registro financeiro pelo seu ID.
     * 
     * @param Id Identificador do registro financeiro a ser excluído
     * @return Mensagem de sucesso ou erro caso o registro não seja encontrado
     */
    @DeleteMapping("financeiro/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") String Id) {
        if (financeiroService.getFinanceiroById(Id) != null) {
            financeiroService.deleteFinanceiro(Id);
            return ResponseEntity.ok("Financeiro excluído com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: recurso não encontrado!");
        }
    }
}
