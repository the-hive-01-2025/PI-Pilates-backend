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
	
	public List<Aluno> listarTodos(){
		return alunoRepository.findAll();
	}
	
	public Aluno getById(String Id) {
		return alunoRepository.findAlunoById(Id);
				
	}
	
	public Aluno getByNome(String nome) {
		return alunoRepository.findByNome(nome);
	}

	public Optional<Aluno> getByCpf(Long cpf) {
		return alunoRepository.findByCpf(cpf);
	}

	
	public List<Aluno> getByPrimeiroNome (String nome) {
		return alunoRepository.findByNomeStartsWith(nome);
	}
	
	public Aluno saveAluno(Aluno aluno) {
		Optional<Aluno> alunoExistente = alunoRepository.findByCpf(aluno.getCpf());

    if (alunoExistente.isPresent()) {
        throw new IllegalArgumentException("Já existe um aluno com este CPF.");
    }

    return alunoRepository.save(aluno);
	}
	
	public void deleteAluno(String Id) {
		alunoRepository.deleteAlunoById(Id);
	}

	public void deleteAlunoByName(String nome) {
		alunoRepository.deleteByNome(nome);
	}

public Aluno updateAluno(String id, Aluno alunoAtualizado) {
    Aluno existente = alunoRepository.findAlunoById(id);

    if (existente == null) {
        throw new IllegalArgumentException("Aluno com ID " + id + " não encontrado.");
    }

    // Atualiza apenas os campos não nulos (ou diferentes de zero no caso de Long)
    if (alunoAtualizado.getNome() != null) {
        existente.setNome(alunoAtualizado.getNome());
    }
    if (alunoAtualizado.getCpf() != null && alunoAtualizado.getCpf() != 0) {
        existente.setCpf(alunoAtualizado.getCpf());
    }
    if (alunoAtualizado.getEmail() != null) {
        existente.setEmail(alunoAtualizado.getEmail());
    }
    if (alunoAtualizado.getContato() != null && alunoAtualizado.getContato() != 0) {
        existente.setContato(alunoAtualizado.getContato());
    }
    if (alunoAtualizado.getProfissao() != null) {
        existente.setProfissao(alunoAtualizado.getProfissao());
    }
    if (alunoAtualizado.getData() != null) {
        existente.setData(alunoAtualizado.getData());
    }
    if (alunoAtualizado.getFichaAvaliacao() != null) {
        existente.setFichaAvaliacao(alunoAtualizado.getFichaAvaliacao());
    }
    if (alunoAtualizado.getPlano() != null) {
        existente.setPlano(alunoAtualizado.getPlano());
    }
    if (alunoAtualizado.getAulasMarcadas() != null) {
        existente.setAulasMarcadas(alunoAtualizado.getAulasMarcadas());
    }
    if (alunoAtualizado.getHistoricoPagamento() != null) {
        existente.setHistoricoPagamento(alunoAtualizado.getHistoricoPagamento());
    }
    if (alunoAtualizado.getFotos() != null) {
        existente.setFotos(alunoAtualizado.getFotos());
    }

    return alunoRepository.save(existente);
}

}
