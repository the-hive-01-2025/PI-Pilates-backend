package br.studio.pilates.model.entity.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.studio.pilates.model.entity.RegEvolucao;

@Repository
public interface RegEvolucaoRepository extends MongoRepository<RegEvolucao, String>{

    List<RegEvolucao> findByAlunoIgnoreCase(String Aluno);
    
}
