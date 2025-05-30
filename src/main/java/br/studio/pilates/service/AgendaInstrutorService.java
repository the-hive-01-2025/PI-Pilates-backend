package br.studio.pilates.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.studio.pilates.dto.AgendaInstrutorDTO;
import br.studio.pilates.mapper.AulaMapper;
import br.studio.pilates.model.entity.Aula;
import br.studio.pilates.model.entity.repository.AulaRepository;

@Service
public class AgendaInstrutorService {

    private final AulaRepository aulaRepository;

    public AgendaInstrutorService(AulaRepository aulaRepository) {
        this.aulaRepository = aulaRepository;
    }

    public List<AgendaInstrutorDTO> listarAulasPorInstrutor(String idInstrutor) {
        List<Aula> aulas = aulaRepository.findByIdInstrutor(idInstrutor);
        return aulas.stream().map(AulaMapper::toDTO).collect(Collectors.toList());
    }

    public boolean cancelarAula(String id) {
        try {
            Aula aula = aulaRepository.findById(id).orElseThrow(() -> new RuntimeException("Aula não encontrada"));
            aula.setStatus("Cancelado");
            aulaRepository.save(aula);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean atualizarAula(Aula aula) {
        try {
            aulaRepository.save(aula);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    
}