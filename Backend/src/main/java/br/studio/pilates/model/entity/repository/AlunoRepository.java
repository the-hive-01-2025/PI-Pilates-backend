package br.studio.pilates.model.entity.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.studio.pilates.model.entity.Aluno;

public interface AlunoRepository extends MongoRepository<Aluno, String>{

	public Aluno findByNome(String nome);

	
	public List<Aluno> findByNomeStartsWith(String nome);

	public Aluno findAlunoById(String Id);

	void deleteAlunoById(String Id);

	void deleteByNome(String Id);
}
