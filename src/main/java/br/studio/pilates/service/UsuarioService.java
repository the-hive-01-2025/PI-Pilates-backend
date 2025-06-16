package br.studio.pilates.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.studio.pilates.model.entity.Usuario;
import br.studio.pilates.model.entity.repository.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {

	@Autowired
	UsuarioRepository usuarioRepository;

	public List<Usuario> listarTodos() {
		return usuarioRepository.findAll();
	}

	public Usuario getById(String Id) {
		return usuarioRepository.findUsuarioById(Id);
	}

	public Usuario getByNome(String nome) {
		return usuarioRepository.findByNome(nome);
	}

	public Usuario getByCpf(String cpf) {
		return usuarioRepository.findByCpf(cpf).orElse(null);
	}

	public List<Usuario> getByPrimeiroNome(String nome) {
		return usuarioRepository.findByNomeStartsWith(nome);
	}

	public Usuario saveUsuario(Usuario usuario) {
		Optional<Usuario> existente = usuarioRepository.findByCpf(usuario.getCpf());

		if (existente.isPresent() && !existente.get().getId().equals(usuario.getId())) {
			throw new IllegalArgumentException("Já existe um instrutor com este CPF.");
		}

		return usuarioRepository.save(usuario);
	}

	public Usuario atualizarUsuario(String Id, Usuario usuario) {
		Optional<Usuario> usuarioExistente = usuarioRepository.findByCpf(usuario.getCpf());

		if (usuarioExistente.isPresent() && !usuarioExistente.get().getId().equals(Id)) {
			throw new IllegalArgumentException("Já existe um instrutor com este CPF.");
		}

		usuario.setId(Id);
		return usuarioRepository.save(usuario);
	}

	public void deleteUsuario(String Id) {
		usuarioRepository.deleteUsuarioById(Id);
	}

	public void deleteUsuarioByName(String nome) {
		usuarioRepository.deleteByNome(nome);
	}

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado com email: " + email);
        }
        return usuario;
    }

	  public Usuario getUsuarioLogado() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String email;

        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername(); // normalmente o username é o email
        } else {
            email = principal.toString(); // fallback
        }

        return usuarioRepository.findByEmail(email);
    }

    public Usuario getByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

}