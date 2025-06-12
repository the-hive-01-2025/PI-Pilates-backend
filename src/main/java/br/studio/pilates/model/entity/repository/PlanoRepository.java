package br.studio.pilates.model.entity.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.studio.pilates.model.entity.Plano;

public interface PlanoRepository extends MongoRepository<Plano, String>{

	public Plano findPlanoById(String Id);

    void deletePlanoById(String Id);
    
    Optional<Plano> findByNomePlano(String nomePlano);
	
}