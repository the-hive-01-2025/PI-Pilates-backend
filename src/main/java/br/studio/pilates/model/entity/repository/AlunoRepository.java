package br.studio.pilates.model.entity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.studio.pilates.model.entity.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, String>{

	public Aluno findByNome(String nome);
	
	public List<Aluno> findByNomeStartsWith(String nome);
}
