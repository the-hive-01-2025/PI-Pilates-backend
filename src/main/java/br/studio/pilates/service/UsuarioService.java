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

/**
 * Serviço responsável pelo gerenciamento dos usuários do sistema.
 * Implementa UserDetailsService para integração com o Spring Security.
 */
@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    UsuarioRepository usuarioRepository;

    /**
     * Retorna a lista de todos os usuários cadastrados.
     * 
     * @return lista de usuários
     */
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    /**
     * Busca um usuário pelo seu ID.
     * 
     * @param Id identificador do usuário
     * @return usuário correspondente ao ID informado
     */
    public Usuario getById(String Id) {
        return usuarioRepository.findUsuarioById(Id);
    }

    /**
     * Busca um usuário pelo nome completo.
     * 
     * @param nome nome do usuário
     * @return usuário correspondente ao nome informado
     */
    public Usuario getByNome(String nome) {
        return usuarioRepository.findByNome(nome);
    }

    /**
     * Busca um usuário pelo CPF.
     * 
     * @param cpf CPF do usuário
     * @return usuário correspondente ao CPF informado ou null se não encontrado
     */
    public Usuario getByCpf(String cpf) {
        return usuarioRepository.findByCpf(cpf).orElse(null);
    }

    /**
     * Busca usuários cujo nome começa com o prefixo informado.
     * 
     * @param nome prefixo do nome
     * @return lista de usuários cujo nome começa com o prefixo
     */
    public List<Usuario> getByPrimeiroNome(String nome) {
        return usuarioRepository.findByNomeStartsWith(nome);
    }

    /**
     * Salva um novo usuário.
     * 
     * @param usuario objeto usuário a ser salvo
     * @throws IllegalArgumentException se já existir um usuário com o mesmo CPF
     * @return usuário salvo
     */
    public Usuario saveUsuario(Usuario usuario) {
        Optional<Usuario> existente = usuarioRepository.findByCpf(usuario.getCpf());

        if (existente.isPresent() && !existente.get().getId().equals(usuario.getId())) {
            throw new IllegalArgumentException("Já existe um instrutor com este CPF.");
        }

        return usuarioRepository.save(usuario);
    }

    /**
     * Atualiza um usuário existente pelo ID.
     * 
     * @param Id identificador do usuário a ser atualizado
     * @param usuario dados do usuário atualizados
     * @throws IllegalArgumentException se já existir outro usuário com o mesmo CPF
     * @return usuário atualizado
     */
    public Usuario atualizarUsuario(String Id, Usuario usuario) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findByCpf(usuario.getCpf());

        if (usuarioExistente.isPresent() && !usuarioExistente.get().getId().equals(Id)) {
            throw new IllegalArgumentException("Já existe um instrutor com este CPF.");
        }

        usuario.setId(Id);
        return usuarioRepository.save(usuario);
    }

    /**
     * Remove um usuário pelo ID.
     * 
     * @param Id identificador do usuário a ser removido
     */
    public void deleteUsuario(String Id) {
        usuarioRepository.deleteUsuarioById(Id);
    }

    /**
     * Remove usuários pelo nome.
     * 
     * @param nome nome do usuário a ser removido
     */
    public void deleteUsuarioByName(String nome) {
        usuarioRepository.deleteByNome(nome);
    }

    /**
     * Carrega um usuário pelo email para autenticação (implementação de UserDetailsService).
     * 
     * @param email email do usuário
     * @throws UsernameNotFoundException se o usuário não for encontrado
     * @return objeto UserDetails do usuário para autenticação
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado com email: " + email);
        }
        return usuario;
    }

    /**
     * Obtém o usuário atualmente logado no contexto de segurança do Spring.
     * 
     * @return usuário logado
     */
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

    /**
     * Busca um usuário pelo email.
     * 
     * @param email email do usuário
     * @return usuário correspondente ao email informado
     */
    public Usuario getByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
}
