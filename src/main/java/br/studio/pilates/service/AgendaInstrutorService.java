package br.studio.pilates.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.studio.pilates.dto.AgendaInstrutorDTO;
import br.studio.pilates.mapper.AulaMapper;
import br.studio.pilates.model.entity.Aula;
import br.studio.pilates.model.entity.repository.AulaRepository;

/**
 * Serviço responsável pelas operações relacionadas à agenda de aulas dos instrutores.
 * Permite listar, atualizar, cancelar aulas, buscar por ID e gerenciar presença de alunos.
 */
@Service
public class AgendaInstrutorService {

    private final AulaRepository aulaRepository;

    /**
     * Construtor que injeta o repositório de aulas.
     *
     * @param aulaRepository Repositório para operações de persistência de Aula.
     */
    public AgendaInstrutorService(AulaRepository aulaRepository) {
        this.aulaRepository = aulaRepository;
    }

    /**
     * Lista todas as aulas cadastradas no sistema.
     *
     * @return Lista de objetos AgendaInstrutorDTO representando as aulas.
     */
    public List<AgendaInstrutorDTO> listarTodasAulas() {
        List<Aula> aulas = aulaRepository.findAll();
        return aulas.stream()
                    .map(AulaMapper::toDTO)
                    .collect(Collectors.toList());
    }

    /**
     * Lista todas as aulas de um instrutor específico pelo seu ID.
     *
     * @param idInstrutor ID do instrutor.
     * @return Lista de AgendaInstrutorDTO das aulas do instrutor.
     * @throws IllegalArgumentException se o instrutor não existir.
     */
    public List<AgendaInstrutorDTO> listarAulasPorInstrutor(String idInstrutor) {
        // Verifica se existe aula com o idInstrutor
        if (!aulaRepository.existsById(idInstrutor)) {
            throw new IllegalArgumentException("Instrutor não encontrado.");
        }
        List<Aula> aulas = aulaRepository.findByIdInstrutor(idInstrutor);
        return aulas.stream()
                    .map(AulaMapper::toDTO)
                    .collect(Collectors.toList());
    }

    /**
     * Retorna uma lista contendo a aula com o ID especificado.
     *
     * @param id ID da aula.
     * @return Lista com a Aula convertida para AgendaInstrutorDTO, ou lista vazia se não encontrada.
     */
    public List<AgendaInstrutorDTO> listarAulaById(String id) {
        Optional<Aula> aulas = aulaRepository.findById(id);
        return aulas.stream()
                    .map(AulaMapper::toDTO)
                    .collect(Collectors.toList());
    }

    /**
     * Cancela a aula definida pelo ID, alterando seu status para "Cancelado".
     *
     * @param id ID da aula a ser cancelada.
     * @throws RuntimeException se a aula não for encontrada.
     */
    public void cancelarAula(String id) {
        Aula aula = aulaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Aula não encontrada"));
        aula.setStatus("Cancelado");
        aulaRepository.save(aula);
    }

    /**
     * Atualiza ou salva uma aula no repositório.
     *
     * @param aula Objeto Aula a ser salvo ou atualizado.
     */
    public void atualizarAula(Aula aula) {
        aulaRepository.save(aula);
    }

    /**
     * Busca uma aula pelo seu ID.
     *
     * @param Id ID da aula.
     * @return Aula encontrada, ou null se não existir.
     */
    public Aula getAulaById(String Id) {
        return aulaRepository.findAulaById(Id);
    }

    /**
     * Marca ou desmarca a presença de um aluno em uma aula.
     *
     * @param idAula ID da aula.
     * @param idAluno ID do aluno.
     * @param presente true para marcar presença; false para remover.
     * @throws RuntimeException se a aula não for encontrada.
     */
    public void marcarPresenca(String idAula, String idAluno, boolean presente) {
        Optional<Aula> aulaOpt = aulaRepository.findById(idAula);

        if (aulaOpt.isPresent()) {
            Aula aula = aulaOpt.get();

            if (presente) {
                // Adiciona o aluno à lista de presentes se ainda não estiver nela
                if (!aula.getPresentes().contains(idAluno)) {
                    aula.getPresentes().add(idAluno);
                }
            } else {
                // Remove o aluno da lista de presentes
                aula.getPresentes().remove(idAluno);
            }

            aulaRepository.save(aula);
        } else {
            throw new RuntimeException("Aula não encontrada!");
        }
    }

}
