package br.studio.pilates.model.entity.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.studio.pilates.model.entity.RegEvolucao;

/**
 * Repositório para operações CRUD e consultas customizadas na coleção de Registros de Evolução.
 */
@Repository
public interface RegEvolucaoRepository extends MongoRepository<RegEvolucao, String> {

    /**
     * Busca registros de evolução por nome do aluno (ignorando maiúsculas/minúsculas).
     */
    List<RegEvolucao> findByAlunoIgnoreCase(String Aluno);
}