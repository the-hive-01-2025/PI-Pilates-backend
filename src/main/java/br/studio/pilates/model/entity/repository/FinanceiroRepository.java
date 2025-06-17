package br.studio.pilates.model.entity.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.studio.pilates.model.entity.Financeiro;

/**
 * Repositório para operações CRUD e consultas customizadas na coleção de Financeiro.
 */
public interface FinanceiroRepository extends MongoRepository<Financeiro, String> {

    /**
     * Busca um registro financeiro pelo ID.
     */
    Financeiro findFinanceiroById(String Id);

    /**
     * Remove um registro financeiro pelo ID.
     */
    void deleteFinanceiroById(String Id);
}