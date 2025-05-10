package br.studio.pilates.model.entity.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.studio.pilates.model.entity.Usuario;

public interface UsuarioRepository extends MongoRepository<Usuario, String>{

	public Usuario findByNome(String nome);

	
	public List<Usuario> findByNomeStartsWith(String nome);

	public Usuario findUsuarioById(String Id);

	void deleteUsuarioById(String Id);

	void deleteByNome(String Id);
}
