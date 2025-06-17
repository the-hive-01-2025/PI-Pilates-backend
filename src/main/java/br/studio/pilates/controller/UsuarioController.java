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

/**
 * Controller REST para gerenciamento dos usuários.
 * Disponibiliza endpoints para listar, buscar, inserir e excluir usuários.
 */
@RestController
@RequestMapping("/api")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Retorna a lista de todos os usuários cadastrados.
     * 
     * @return Lista de objetos Usuario
     */
    @GetMapping("/usuario")
    public List<Usuario> listar() {
        return usuarioService.listarTodos();
    }

    /**
     * Endpoint simples para testar se a API está funcionando.
     * 
     * @return String informativa
     */
    @GetMapping("/teste")
    public String teste() {
        return "API funcionando!";
    }

    /**
     * Busca um usuário pelo seu ID.
     * 
     * @param Id Identificador do usuário
     * @return Objeto Usuario correspondente ao ID
     */
    @GetMapping("usuario/{id}")
    public Usuario getById(@PathVariable("id") String Id) {
        return usuarioService.getById(Id);
    }

    /**
     * Busca um usuário pelo nome completo.
     * 
     * @param nome Nome completo do usuário
     * @return Objeto Usuario correspondente ao nome
     */
    @GetMapping("usuario/nome/{nome}")
    public Usuario getByNomeUsuario(@PathVariable String nome) {
        return usuarioService.getByNome(nome);
    }

    /**
     * Busca usuários pelo primeiro nome.
     * 
     * @param nome Primeiro nome do usuário
     * @return Lista de usuários cujo primeiro nome corresponde ao parâmetro
     */
    @GetMapping("usuario/primeironome/{nome}")
    public List<Usuario> getByFirstName(@PathVariable String nome) {
        return usuarioService.getByPrimeiroNome(nome);
    }

    /**
     * Insere um novo usuário no sistema.
     * A senha é criptografada antes de salvar.
     * 
     * @param usuario Objeto Usuario a ser salvo
     * @return Usuario salvo com ID gerado
     */
    @PostMapping("usuario")
    public Usuario insert(@RequestBody Usuario usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioService.saveUsuario(usuario);
    }

    /**
     * Exclui um usuário pelo seu ID.
     * 
     * @param Id Identificador do usuário a ser excluído
     * @return Mensagem de sucesso ou erro caso o usuário não seja encontrado
     */
    @DeleteMapping("usuario/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") String Id) {
        if (usuarioService.getById(Id) != null) {
            usuarioService.deleteUsuario(Id);
            return ResponseEntity.ok("Usuário excluído com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Erro: recurso não encontrado!");
        }
    }

    /**
     * Exclui um usuário pelo nome completo.
     * 
     * @param nome Nome completo do usuário a ser excluído
     * @return Mensagem de sucesso ou erro caso o usuário não seja encontrado
     */
    @DeleteMapping("usuario/nome/{nome}")
    public ResponseEntity<String> deleteByName(@PathVariable String nome) {
        try {
            usuarioService.deleteUsuarioByName(nome);
            return ResponseEntity.ok("Usuário excluído com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuário não encontrado!");
        }
    }

}
