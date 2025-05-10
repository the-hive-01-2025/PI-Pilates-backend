package br.studio.pilates.model.entity.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.studio.pilates.model.entity.Aluno;

public interface AlunoRepository extends MongoRepository<Aluno, String> {

    // Buscar aluno pelo nome completo
    Aluno findByNome(String nome);

    // Buscar alunos cujo nome começa com determinado texto (prefixo)
    List<Aluno> findByNomeStartsWith(String nome);

    // Buscar aluno pelo ID
    Aluno findAlunoById(String id);

    // Buscar aluno pelo CPF — usando Optional é melhor para tratamento de erro
    Optional<Aluno> findByCpf(Long cpf);  // Para uso no método saveAluno do Service

    // Também permitir acesso direto (sem Optional), se necessário
    Aluno findAlunoByCpf(Long cpf);       // Para uso em GET por CPF

    // Deletar por ID
    void deleteAlunoById(String id);

    // Deletar por nome (ajuste no parâmetro: era "Id")
    void deleteByNome(String nome);
}
