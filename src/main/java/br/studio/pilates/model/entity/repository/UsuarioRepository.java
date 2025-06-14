package br.studio.pilates.model.entity.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.studio.pilates.model.entity.Usuario;

public interface UsuarioRepository extends MongoRepository<Usuario, String>{

	public Usuario findByNome(String nome);
	
	public List<Usuario> findByNomeStartsWith(String nome);

	public Usuario findUsuarioById(String Id);

	public Optional<Usuario> findByCpf(String cpf);

	void deleteUsuarioById(String Id);

	void deleteByNome(String Id);

	Usuario findByEmail(String email);
}
