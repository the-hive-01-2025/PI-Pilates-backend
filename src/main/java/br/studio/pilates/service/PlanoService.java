package br.studio.pilates.service;

import java.util.List;
import java.util.Optional;

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

    public Optional<Plano> getPlanoById(String id) {
        return planoRepository.findById(id);
    }
    
    public Plano getPlanoByNome(String nomePlano) {
        return planoRepository.findByNomePlano(nomePlano)
                .orElseThrow(() -> new RuntimeException("Plano com nome '" + nomePlano + "' não encontrado."));
    }

    public Plano savePlano(Plano plano) {
        return planoRepository.save(plano);
    }

    public void deletePlano(String id) {
        planoRepository.deleteById(id);
    }

}