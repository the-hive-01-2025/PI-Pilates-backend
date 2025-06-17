package br.studio.pilates.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.studio.pilates.model.entity.Aluno;
import br.studio.pilates.service.AlunoService;

/**
 * Controller responsável pela API REST de gerenciamento de alunos.
 * Disponibiliza endpoints para CRUD completo e buscas específicas.
 */
@RestController
@RequestMapping("/api")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    // ===========================================
    // ============== LISTAGENS ==================
    // ===========================================

    /**
     * Lista todos os alunos cadastrados.
     */
    @GetMapping("/aluno")
    public List<Aluno> listar() {
        return alunoService.listarTodos();
    }

    /**
     * Busca um aluno pelo ID.
     */
    @GetMapping("/aluno/{id}")
    public Optional<Aluno> getById(@PathVariable("id") String id) {
        return alunoService.getById(id);
    }

    /**
     * Busca um aluno pelo CPF.
     */
    @GetMapping("/aluno/cpf/{cpf}")
    public Aluno getByCpf(@PathVariable String cpf) {
        return alunoService.getByCpf(cpf);
    }

    /**
     * Busca um aluno pelo nome completo.
     */
    @GetMapping("/aluno/nome/{nome}")
    public Aluno getByNomeAluno(@PathVariable String nome) {
        return alunoService.getByNome(nome);
    }

    /**
     * Busca alunos pelo primeiro nome (parcial).
     */
    @GetMapping("/aluno/primeironome/{nome}")
    public List<Aluno> getByFirstName(@PathVariable String nome) {
        return alunoService.getByPrimeiroNome(nome);
    }

    // ===========================================
    // =============== CADASTRO ==================
    // ===========================================

    /**
     * Cadastra um novo aluno.
     */
    @PostMapping("/aluno")
    public ResponseEntity<?> cadastrarAluno(@RequestBody Aluno aluno) {
        try {
            Aluno novoAluno = alunoService.saveAluno(aluno);
            return ResponseEntity.ok(novoAluno);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Não foi possível cadastrar o aluno: " + e.getMessage());
        }
    }

    // ===========================================
    // ============= ATUALIZAÇÃO =================
    // ===========================================

    /**
     * Atualiza os dados de um aluno pelo ID.
     */
    @PutMapping("/aluno/{id}")
    public ResponseEntity<?> atualizarAluno(@PathVariable String id, @RequestBody Aluno aluno) {
        try {
            Aluno alunoAtualizado = alunoService.atualizarAluno(id, aluno);
            return ResponseEntity.ok(alunoAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Não foi possível atualizar o aluno: " + e.getMessage());
        }
    }

    // ===========================================
    // ================ EXCLUSÃO =================
    // ===========================================

    /**
     * Exclui um aluno pelo ID.
     */
    @DeleteMapping("/aluno/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        Optional<Aluno> aluno = alunoService.getById(id);

        if (aluno.isPresent()) {
            alunoService.deleteAluno(id);
            return ResponseEntity.ok("Aluno excluído com sucesso!");
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Erro: recurso não encontrado!");
        }
    }

    /**
     * Exclui um aluno pelo nome completo.
     */
    @DeleteMapping("/aluno/nome/{nome}")
    public ResponseEntity<?> deleteByName(@PathVariable String nome) {
        try {
            alunoService.deleteAlunoByName(nome);
            return ResponseEntity.ok("Aluno excluído com sucesso!");
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Erro: aluno não encontrado!");
        }
    }
}
