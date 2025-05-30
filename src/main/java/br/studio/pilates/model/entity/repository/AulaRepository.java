package br.studio.pilates.model.entity.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.studio.pilates.model.entity.Aula;

public interface AulaRepository extends MongoRepository<Aula, String>{

	public Aula findAulaById(String Id);
	
	public List<Aula> findByIdInstrutor(String idInstrutor);
	
	public List<Aula> findByIdStudio(String idStudio);
	
	public List<Aula> findByStatus(String status);
	
	public List<Aula> findByData(String data);
	
	public List<Aula> findByHorario(String horario);
	
	public List<Aula> findByAlunos(String alunos);

	void deleteAulaById(String Id);
	
  

}