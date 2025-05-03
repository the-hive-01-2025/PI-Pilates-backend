package br.studio.pilates.model.entity.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.studio.pilates.model.entity.Aula;

public interface AulaRepository extends MongoRepository<Aula, String>{

	public Aula findAulaById(String Id);

	void deleteAulaById(String Id);
	
}