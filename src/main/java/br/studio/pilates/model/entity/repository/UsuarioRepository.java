package br.studio.pilates.model.entity.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.studio.pilates.model.entity.Usuario;

/**
 * Repositório para operações CRUD e consultas customizadas na coleção de Usuários.
 */
public interface UsuarioRepository extends MongoRepository<Usuario, String> {

    /**
     * Busca um usuário pelo nome exato.
     */
    Usuario findByNome(String nome);

    /**
     * Busca usuários cujo nome começa com o valor informado.
     */
    List<Usuario> findByNomeStartsWith(String nome);

    /**
     * Busca um usuário pelo ID.
     */
    Usuario findUsuarioById(String Id);

    /**
     * Busca um usuário pelo CPF.
     */
    Optional<Usuario> findByCpf(String cpf);

    /**
     * Remove um usuário pelo ID.
     */
    void deleteUsuarioById(String Id);

    /**
     * Remove um usuário pelo nome.
     */
    void deleteByNome(String Id);

    /**
     * Busca um usuário pelo e-mail.
     */
    Usuario findByEmail(String email);
}