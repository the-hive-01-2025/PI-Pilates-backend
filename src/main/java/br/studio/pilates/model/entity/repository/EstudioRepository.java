package br.studio.pilates.model.entity.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.studio.pilates.model.entity.Estudio;

/**
 * Repositório para operações CRUD e consultas customizadas na coleção de Estúdios.
 */
public interface EstudioRepository extends MongoRepository<Estudio, String> {

    /**
     * Busca um estúdio pelo nome.
     */
    Estudio findByNome(String estudio);

    /**
     * Busca um estúdio pelo ID.
     */
    Estudio findEstudioById(String Id);

    /**
     * Remove um estúdio pelo ID.
     */
    void deleteEstudioById(String Id);

    /**
     * Remove um estúdio pelo nome.
     */
    void deleteEstudioByNome(String Id);
}