package br.studio.pilates.model.entity.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.studio.pilates.model.entity.RegEvolucao;

@Repository
public interface RegEvolucaoRepository extends MongoRepository<RegEvolucao, String>{
    
}
