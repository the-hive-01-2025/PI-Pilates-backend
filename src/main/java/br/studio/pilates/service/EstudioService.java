package br.studio.pilates.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.studio.pilates.model.entity.Estudio;
import br.studio.pilates.model.entity.repository.EstudioRepository;

@Service
public class EstudioService {

    @Autowired
    private EstudioRepository estudioRepository;

   public List<Estudio> getAllEstudio() {
        return estudioRepository.findAll();
    }

    public Estudio getEstudioById(String Id) {
        return estudioRepository.findEstudioById(Id);
    }

    public Estudio getByNome(String nome) {
		return estudioRepository.findByNome(nome);
	}

    public Estudio saveEstudio(Estudio estudio) {
        return estudioRepository.save(estudio);
    }

    public void deleteEstudio(String Id) {
        estudioRepository.deleteById(Id);
    }

    public void deleteEstudioByName(String nome) {
		estudioRepository.deleteEstudioByNome(nome);
	}
}