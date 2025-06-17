package br.studio.pilates.model.entity.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.studio.pilates.model.entity.Aluno;

/**
 * Repositório para operações CRUD e consultas customizadas na coleção de Alunos.
 */
public interface AlunoRepository extends MongoRepository<Aluno, String> {

    /**
     * Busca um aluno pelo nome exato.
     */
    Aluno findByNome(String nome);

    /**
     * Busca alunos cujo nome começa com o valor informado.
     */
    List<Aluno> findByNomeStartsWith(String nome);

    /**
     * Busca um aluno pelo ID.
     */
    Optional<Aluno> findAlunoById(String Id);

    /**
     * Busca um aluno pelo CPF.
     */
    Optional<Aluno> findByCpf(String cpf);

    /**
     * Remove um aluno pelo ID.
     */
    void deleteAlunoById(String Id);

    /**
     * Remove um aluno pelo nome.
     */
    void deleteByNome(String Id);

    /**
     * Busca um aluno pelo e-mail.
     */
    Aluno findByEmail(String email);
}