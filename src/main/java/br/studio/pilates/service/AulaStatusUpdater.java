package br.studio.pilates.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.studio.pilates.model.entity.Aula;
import br.studio.pilates.model.entity.repository.AulaRepository;

import jakarta.annotation.PostConstruct;

@Component
public class AulaStatusUpdater {

    @Autowired
    private AulaRepository aulaRepository;

    @PostConstruct
    public void updateNullStatusToPendente() {
        List<Aula> aulas = aulaRepository.findAll();
        for (Aula aula : aulas) {
            if (aula.getStatus() == null || aula.getStatus().isEmpty()) {
                aula.setStatus("Pendente");
                aulaRepository.save(aula);
            }
        }
    }
}
