package br.studio.pilates.model.entity.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.studio.pilates.model.entity.Financeiro;

public interface FinanceiroRepository extends MongoRepository<Financeiro, String>{

	public Financeiro findFinanceiroById(String Id);
	
    void deleteFinanceiroById(String Id);
}