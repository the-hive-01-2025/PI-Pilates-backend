package br.studio.pilates.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.studio.pilates.model.entity.Financeiro;
import br.studio.pilates.model.entity.repository.FinanceiroRepository;

/**
 * Serviço responsável pelas operações relacionadas à entidade Financeiro.
 * 
 * Fornece métodos para buscar, salvar e deletar registros financeiros.
 */
@Service
public class FinanceiroService {

    @Autowired
    private FinanceiroRepository financeiroRepository;

    /**
     * Retorna todos os registros financeiros cadastrados.
     * 
     * @return lista com todos os objetos Financeiro
     */
    public List<Financeiro> getAllFinanceiro() {
        return financeiroRepository.findAll();
    }

    /**
     * Busca um registro financeiro pelo seu ID.
     * 
     * @param Id identificador do registro financeiro
     * @return objeto Financeiro correspondente ao ID informado, ou null se não encontrado
     */
    public Financeiro getFinanceiroById(String Id) {
        return financeiroRepository.findFinanceiroById(Id);
    }

    /**
     * Salva um registro financeiro no banco de dados.
     * 
     * @param financeiro objeto Financeiro a ser salvo
     * @return objeto Financeiro salvo com as informações atualizadas
     */
    public Financeiro saveFinanceiro(Financeiro financeiro) {
        return financeiroRepository.save(financeiro);
    }

    /**
     * Deleta um registro financeiro pelo seu ID.
     * 
     * @param Id identificador do registro financeiro a ser deletado
     */
    public void deleteFinanceiro(String Id) {
        financeiroRepository.deleteById(Id);
    }
}
