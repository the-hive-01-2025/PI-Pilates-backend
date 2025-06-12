package br.studio.pilates.service;

import java.util.List;
import java.util.Optional;
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

    public List<AgendaInstrutorDTO> listarTodasAulas() {
        List<Aula> aulas = aulaRepository.findAll();
        return aulas.stream().map(AulaMapper::toDTO).collect(Collectors.toList());
    }

    public List<AgendaInstrutorDTO> listarAulasPorInstrutor(String idInstrutor) {

        List<Aula> aulas = aulaRepository.findByIdInstrutor(idInstrutor);
        if (!aulaRepository.existsById(idInstrutor)) {
            throw new IllegalArgumentException("Instrutor não encontrado.");
        }
        return aulas.stream().map(AulaMapper::toDTO).collect(Collectors.toList());
    }

    public List<AgendaInstrutorDTO> listarAulaById(String id) {
        Optional<Aula> aulas = aulaRepository.findById(id);
        return aulas.stream().map(AulaMapper::toDTO).collect(Collectors.toList());
    }

    public void cancelarAula(String id) {
        Aula aula = aulaRepository.findById(id).orElseThrow(() -> new RuntimeException("Aula não encontrada"));
        aula.setStatus("Cancelado");
        aulaRepository.save(aula);
    }

    public void atualizarAula(Aula aula) {
        aulaRepository.save(aula);
    }

    public Aula getAulaById(String Id) {
        return aulaRepository.findAulaById(Id);
    }

    public void marcarPresenca(String idAula, String idAluno, boolean presente) {
        Optional<Aula> aulaOpt = aulaRepository.findById(idAula);

        if (aulaOpt.isPresent()) {
            Aula aula = aulaOpt.get();

            if (presente) {
                // Adiciona o aluno à lista se ainda não estiver presente
                if (!aula.getPresentes().contains(idAluno)) {
                    aula.getPresentes().add(idAluno);
                }
            } else {
                // Remove o aluno da lista
                aula.getPresentes().remove(idAluno);
            }

            aulaRepository.save(aula);
        } else {
            throw new RuntimeException("Aula não encontrada!");
        }
    }

}
