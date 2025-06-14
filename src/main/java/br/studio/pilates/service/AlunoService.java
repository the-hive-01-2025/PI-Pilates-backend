package br.studio.pilates.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.studio.pilates.model.entity.Aluno;
import br.studio.pilates.model.entity.repository.AlunoRepository;

@Service
public class AlunoService {

    @Autowired
    AlunoRepository alunoRepository;

    public List<Aluno> listarTodos() {
        return alunoRepository.findAll();
    }

    public Optional<Aluno> getById(String Id) {
        return alunoRepository.findAlunoById(Id);
    }

    public Aluno getByNome(String nome) {
        return alunoRepository.findByNome(nome);
    }

    public Aluno getByCpf(String cpf) {
        return alunoRepository.findByCpf(cpf).orElse(null);
    }

    public List<Aluno> getByPrimeiroNome(String nome) {
        return alunoRepository.findByNomeStartsWith(nome);
    }

    public Aluno saveAluno(Aluno aluno) {
        Optional<Aluno> existente = alunoRepository.findByCpf(aluno.getCpf());

        if (existente.isPresent() && !existente.get().getId().equals(aluno.getId())) {
            throw new IllegalArgumentException("Já existe um aluno com este CPF.");
        }

        return alunoRepository.save(aluno);
    }

    public Aluno atualizarAluno(String id, Aluno aluno) {
        Optional<Aluno> alunoExistente = alunoRepository.findByCpf(aluno.getCpf());

        if (alunoExistente.isPresent() && !alunoExistente.get().getId().equals(id)) {
            throw new IllegalArgumentException("Já existe um aluno com este CPF.");
        }

        aluno.setId(id); // garante que estamos atualizando o aluno certo
        return alunoRepository.save(aluno);
    }

    public void deleteAluno(String Id) {
        alunoRepository.deleteAlunoById(Id);
    }

    public void deleteAlunoByName(String nome) {
        alunoRepository.deleteByNome(nome);
    }

    public void removerPlano(String cpf) {
        Aluno aluno = getByCpf(cpf);
        if (aluno != null) {
            aluno.setPlano(null);
            aluno.setHistoricoPagamento(null);
            saveAluno(aluno);
        }
    }

}
