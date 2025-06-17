package br.studio.pilates.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import br.studio.pilates.model.entity.RegEvolucao;
import br.studio.pilates.service.RegEvolucaoService;

/**
 * Controller REST para gerenciamento dos Registros de Evolução.
 * Disponibiliza endpoints para listar, buscar por aluno, salvar e deletar registros de evolução.
 */
@RestController
public class RegEvolucaoController {
    
    @Autowired
    private RegEvolucaoService regService;

    /**
     * Retorna todos os registros de evolução cadastrados.
     * 
     * @return Lista de objetos RegEvolucao
     */
    @GetMapping("evolucao/")
    public List<RegEvolucao> listarTodos() {
        return regService.listarTodos();
    }

    /**
     * Busca os registros de evolução de um aluno específico.
     * 
     * @param aluno Identificador ou nome do aluno
     * @return Lista de registros de evolução vinculados ao aluno
     */
    @GetMapping("evolucao/{aluno}")
    public List<RegEvolucao> getByAluno(@PathVariable("aluno") String aluno) {
        return regService.listarPorAluno(aluno);
    }
    
    /**
     * Salva um novo registro de evolução.
     * 
     * @param registro Objeto RegEvolucao enviado via formulário (ModelAttribute)
     * @return Mensagem indicando o resultado da operação
     */
    @PostMapping("evolucao/")
    public String salvarRegistro(@ModelAttribute RegEvolucao registro) {
        return regService.salvar(registro);
    }

    /**
     * Deleta um registro de evolução.
     * 
     * @param registro Objeto RegEvolucao a ser deletado (deve ser enviado pelo corpo da requisição)
     * @return Mensagem indicando o resultado da operação
     */
    @DeleteMapping("evolucao/")
    public String deletarRegistro(RegEvolucao registro) {
        return regService.deletar(registro);
    }
}
