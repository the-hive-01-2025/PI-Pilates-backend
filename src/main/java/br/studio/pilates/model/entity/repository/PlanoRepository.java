package br.studio.pilates.model.entity.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.studio.pilates.model.entity.Plano;

/**
 * Repositório para operações CRUD e consultas customizadas na coleção de Planos.
 */
public interface PlanoRepository extends MongoRepository<Plano, String> {

    /**
     * Busca um plano pelo ID.
     */
    Plano findPlanoById(String Id);

    /**
     * Remove um plano pelo ID.
     */
    void deletePlanoById(String Id);

    /**
     * Busca um plano pelo nome.
     */
    Optional<Plano> findByNomePlano(String nomePlano);
}