package br.studio.pilates.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.studio.pilates.model.entity.RegEvolucao;
import br.studio.pilates.model.entity.repository.RegEvolucaoRepository;

/**
 * Serviço responsável pelas operações relacionadas aos registros de evolução (RegEvolucao).
 * 
 * Fornece métodos para listar registros, buscar registros por aluno, salvar e deletar registros.
 */
@Service
public class RegEvolucaoService {
    
    @Autowired
    RegEvolucaoRepository regRepository;

    /**
     * Retorna todos os registros de evolução cadastrados.
     * 
     * @return lista contendo todos os registros de evolução
     */
    public List<RegEvolucao> listarTodos(){
        return regRepository.findAll();
    }

    /**
     * Busca os registros de evolução de um aluno específico, ignorando maiúsculas e minúsculas.
     * 
     * @param aluno nome do aluno
     * @return lista de registros de evolução relacionados ao aluno informado
     */
    public List<RegEvolucao> listarPorAluno(String aluno){
        return regRepository.findByAlunoIgnoreCase(aluno);
    }

    /**
     * Salva um registro de evolução no banco de dados.
     * 
     * @param registro objeto RegEvolucao a ser salvo
     * @return código de status "200" em caso de sucesso ou mensagem de erro em caso de exceção
     * 
     * @todo Corrigir para não retornar "200" ao tentar salvar registros nulos
     */
    public String salvar(RegEvolucao registro){
        try {
            regRepository.save(registro);
            return "200";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * Deleta um registro de evolução do banco de dados.
     * 
     * @param registro objeto RegEvolucao a ser deletado
     * @return código de status "200" em caso de sucesso ou mensagem de erro em caso de exceção
     */
    public String deletar(RegEvolucao registro){
        try {
            regRepository.delete(registro);
            return "200";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
