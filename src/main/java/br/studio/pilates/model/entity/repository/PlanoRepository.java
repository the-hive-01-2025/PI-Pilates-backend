package br.studio.pilates.model.entity.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.studio.pilates.model.entity.Plano;

public interface PlanoRepository extends MongoRepository<Plano, String>{

	public Plano findPlanoById(String Id);

    void deletePlanoById(String Id);
	
}