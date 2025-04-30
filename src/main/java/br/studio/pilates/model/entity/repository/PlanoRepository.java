package br.studio.pilates.model.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.studio.pilates.model.entity.Plano;

public interface PlanoRepository extends JpaRepository<Plano, String>{
	
	public void savePlano(Plano plano);
	
	public Plano getPlanoById(String id);
	
	public void deleteById(String id);

}
