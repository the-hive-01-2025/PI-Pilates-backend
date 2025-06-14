package br.studio.pilates.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.studio.pilates.model.entity.Usuario;
import br.studio.pilates.service.UsuarioService;


@RestController
@RequestMapping("api")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private PasswordEncoder passwordEncoder;


	@GetMapping("usuario")
	public List<Usuario> listar() {
		return usuarioService.listarTodos();
	}

	@GetMapping("usuario/{id}")
	public Usuario getById(@PathVariable("id") String Id) {
		return usuarioService.getById(Id);
	}

	@GetMapping("usuario/nome/{nome}")
	public Usuario getByNomeUsuario(@PathVariable String nome) {
		return usuarioService.getByNome(nome);
	}

	@GetMapping("usuario/primeironome/{nome}")
	public List<Usuario> getByFirstName(@PathVariable String nome) {
		return usuarioService.getByPrimeiroNome(nome);
	}

	@PostMapping("usuario")
	public Usuario insert(@RequestBody Usuario usuario) {
		// Criptografa a senha antes de salvar
		usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		return usuarioService.saveUsuario(usuario);
	}


	@DeleteMapping("usuario/{id}")
	public String delete(@PathVariable("id") String Id) {
		if(usuarioService.getById(Id) != null){

		usuarioService.deleteUsuario(Id);
		return "usuario Excluido com sucesso!!";
		} else{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: recurso não encontrado!").toString();
	}
		}
	@DeleteMapping("usuario/nome/{nome}")
	public String deleteByName(@PathVariable String nome) {
		try {usuarioService.deleteUsuarioByName(nome);
		return "usuario Excluido com sucesso!!";
		}
		catch (Exception e) {
			return "usuario nao encontrado!!";
		}
	}

}