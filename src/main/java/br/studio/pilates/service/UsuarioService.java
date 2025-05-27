package br.studio.pilates.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.studio.pilates.model.entity.Usuario;
import br.studio.pilates.model.entity.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	UsuarioRepository usuarioRepository;
	
	public List<Usuario> listarTodos(){
		return usuarioRepository.findAll();
	}
	
	public Usuario getById(String Id) {
		return usuarioRepository.findUsuarioById(Id);
				
	}
	
	public Usuario getByNome(String nome) {
		return usuarioRepository.findByNome(nome);
	}
	
	public List<Usuario> getByPrimeiroNome (String nome) {
		return usuarioRepository.findByNomeStartsWith(nome);
	}
	
	public Usuario saveUsuario(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}
	
	public void deleteUsuario(String Id) {
		usuarioRepository.deleteUsuarioById(Id);
	}

	public void deleteUsuarioByName(String nome) {
		usuarioRepository.deleteByNome(nome);
	}
}