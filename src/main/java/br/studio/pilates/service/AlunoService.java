package br.studio.pilates.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.studio.pilates.model.entity.Aluno;
import br.studio.pilates.model.entity.repository.AlunoRepository;

/**
 * Serviço responsável por operações relacionadas à entidade Aluno,
 * incluindo CRUD, busca por campos específicos e integração com Spring Security
 * para autenticação via UserDetailsService.
 */
@Service
public class AlunoService implements UserDetailsService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Encoder para criptografia de senha

    public UserDetailsService usuarioService;

    /**
     * Retorna a lista de todos os alunos cadastrados.
     * 
     * @return Lista de objetos Aluno.
     */
    public List<Aluno> listarTodos() {
        return alunoRepository.findAll();
    }

    /**
     * Busca um aluno pelo seu ID.
     * 
     * @param Id ID do aluno.
     * @return Optional contendo o aluno, ou vazio se não encontrado.
     */
    public Optional<Aluno> getById(String Id) {
        return alunoRepository.findAlunoById(Id);
    }

    /**
     * Busca um aluno pelo nome completo.
     * 
     * @param nome Nome completo do aluno.
     * @return Objeto Aluno encontrado ou null se não existir.
     */
    public Aluno getByNome(String nome) {
        return alunoRepository.findByNome(nome);
    }

    /**
     * Busca um aluno pelo CPF.
     * 
     * @param cpf CPF do aluno.
     * @return Aluno encontrado ou null caso não exista.
     */
    public Aluno getByCpf(String cpf) {
        return alunoRepository.findByCpf(cpf).orElse(null);
    }

    /**
     * Busca alunos cujo nome começa com o prefixo informado.
     * 
     * @param nome Prefixo do nome.
     * @return Lista de alunos cujo nome começa com o prefixo.
     */
    public List<Aluno> getByPrimeiroNome(String nome) {
        return alunoRepository.findByNomeStartsWith(nome);
    }

    /**
     * Salva um novo aluno no banco, validando duplicidade de CPF e codificando senha.
     * 
     * @param aluno Objeto Aluno a ser salvo.
     * @return Aluno salvo.
     * @throws IllegalArgumentException se já existir aluno com o mesmo CPF diferente do atual.
     */
    public Aluno saveAluno(Aluno aluno) {
        Optional<Aluno> existente = alunoRepository.findByCpf(aluno.getCpf());

        if (existente.isPresent() && !existente.get().getId().equals(aluno.getId())) {
            throw new IllegalArgumentException("Já existe um aluno com este CPF.");
        }

        // Codifica a senha antes de salvar
        aluno.setSenha(passwordEncoder.encode(aluno.getSenha()));

        return alunoRepository.save(aluno);
    }

    /**
     * Atualiza os dados de um aluno existente, incluindo validação de CPF e codificação da senha.
     * 
     * @param id ID do aluno a ser atualizado.
     * @param aluno Objeto com os novos dados do aluno.
     * @return Aluno atualizado.
     * @throws IllegalArgumentException se CPF estiver duplicado para outro aluno.
     */
    public Aluno atualizarAluno(String id, Aluno aluno) {
        Optional<Aluno> alunoExistente = alunoRepository.findByCpf(aluno.getCpf());

        if (alunoExistente.isPresent() && !alunoExistente.get().getId().equals(id)) {
            throw new IllegalArgumentException("Já existe um aluno com este CPF.");
        }

        aluno.setId(id); // Assegura atualização do aluno correto

        // Codifica a senha (sempre codifica aqui, pode-se melhorar para checar se já está codificada)
        aluno.setSenha(passwordEncoder.encode(aluno.getSenha()));

        return alunoRepository.save(aluno);
    }

    /**
     * Remove um aluno pelo seu ID.
     * 
     * @param Id ID do aluno a ser removido.
     */
    public void deleteAluno(String Id) {
        alunoRepository.deleteAlunoById(Id);
    }

    /**
     * Remove um aluno pelo seu nome.
     * 
     * @param nome Nome do aluno a ser removido.
     */
    public void deleteAlunoByName(String nome) {
        alunoRepository.deleteByNome(nome);
    }

    /**
     * Método usado pelo Spring Security para carregar um usuário pelo email para autenticação.
     * 
     * @param email Email do aluno (username).
     * @return UserDetails do aluno autenticado.
     * @throws UsernameNotFoundException se aluno não for encontrado pelo email.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Aluno aluno = alunoRepository.findByEmail(email);
        if (aluno == null) {
            throw new UsernameNotFoundException("Aluno não encontrado com o email: " + email);
        }
        return aluno;
    }

    /**
     * Busca um aluno pelo email.
     * 
     * @param email Email do aluno.
     * @return Aluno encontrado ou null.
     */
    public Aluno getByEmail(String email) {
        return alunoRepository.findByEmail(email);
    }

    /**
     * Remove o plano e o histórico de pagamento de um aluno identificado pelo CPF.
     * 
     * @param cpf CPF do aluno.
     */
    public void removerPlano(String cpf) {
        Aluno aluno = getByCpf(cpf);
        if (aluno != null) {
            aluno.setPlano(null);
            aluno.setHistoricoPagamento(null);
            saveAluno(aluno);
        }
    }

}
