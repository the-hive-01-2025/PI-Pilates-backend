package br.studio.pilates.service;

import java.util.List;

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
	
	public List<Aluno> getByPrimeiroNome (String nome) {
		return alunoRepository.findByNomeStartsWith(nome);
	}
	
	public Aluno saveAluno(Aluno aluno) {
		return alunoRepository.save(aluno);
	}
	
	public void deleteAluno(String Id) {
		alunoRepository.deleteAlunoById(Id);
	}

	public void deleteAlunoByName(String nome) {
		alunoRepository.deleteByNome(nome);
	}
}
