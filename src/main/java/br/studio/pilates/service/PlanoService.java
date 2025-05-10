package br.studio.pilates.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.studio.pilates.model.entity.Plano;
import br.studio.pilates.model.entity.repository.PlanoRepository;

@Service
public class PlanoService {

    @Autowired
    private PlanoRepository planoRepository;

   public List<Plano> getAllPlanos() {
        return planoRepository.findAll();
    }

    public Plano getPlanoById(String Id) {
        return planoRepository.findPlanoById(Id);
    }

    public Plano savePlano(Plano plano) {
        return planoRepository.save(plano);
    }

    public void deletePlano(String Id) {
        planoRepository.deleteById(Id);
    }
}