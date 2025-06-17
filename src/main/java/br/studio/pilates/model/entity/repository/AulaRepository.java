package br.studio.pilates.model.entity.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.studio.pilates.model.entity.Aula;

/**
 * Repositório para operações CRUD e consultas customizadas na coleção de Aulas.
 */
public interface AulaRepository extends MongoRepository<Aula, String> {

    /**
     * Busca uma aula pelo ID.
     */
    Aula findAulaById(String Id);

    /**
     * Busca aulas por instrutor.
     */
    List<Aula> findByIdInstrutor(String idInstrutor);

    /**
     * Busca aulas por estúdio.
     */
    List<Aula> findByIdStudio(String idStudio);

    /**
     * Busca aulas por status.
     */
    List<Aula> findByStatus(String status);

    /**
     * Busca aulas por data.
     */
    List<Aula> findByData(String data);

    /**
     * Busca aulas por horário.
     */
    List<Aula> findByHorario(String horario);

    /**
     * Busca aulas por aluno.
     */
    List<Aula> findByAlunos(String alunos);

    /**
     * Remove uma aula pelo ID.
     */
    void deleteAulaById(String Id);
}