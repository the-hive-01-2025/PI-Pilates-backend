package br.studio.pilates.model.entity.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.studio.pilates.model.entity.Estudio;

public interface EstudioRepository extends MongoRepository<Estudio, String>{

	public Estudio findByNome(String estudio);

    public Estudio findEstudioById(String Id);

	void deleteEstudioById(String Id);

	void deleteEstudioByNome(String Id);
	
}