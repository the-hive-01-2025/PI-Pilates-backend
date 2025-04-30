package br.studio.pilates.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.studio.pilates.model.entity.Plano;
import br.studio.pilates.model.entity.repository.PlanoRepository;

@Service
public class PlanoService {

	@Autowired
	PlanoRepository planoRepository;

	public Plano getPlano(String id) {
		return planoRepository.getPlanoById(id);
	}

	public void deleteplano(String id) {
		planoRepository.deleteById(id);
	}
	
	public Plano savePlano(Plano plano) {
		return planoRepository.save(plano);
	}
	
}
