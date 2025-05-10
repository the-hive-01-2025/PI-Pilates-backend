package br.studio.pilates.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.studio.pilates.model.entity.Aula;
import br.studio.pilates.model.entity.repository.AulaRepository;

@Service
public class AulaService {

    @Autowired
    private AulaRepository aulaRepository;

   public List<Aula> getAllAulas() {
        return aulaRepository.findAll();
    }

    public Aula getAulaById(String Id) {
        return aulaRepository.findAulaById(Id);
    }

    public Aula saveAula(Aula aula) {
        return aulaRepository.save(aula);
    }

    public void deleteAula(String Id) {
        aulaRepository.deleteAulaById(Id);
    }
}