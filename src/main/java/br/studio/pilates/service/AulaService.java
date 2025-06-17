package br.studio.pilates.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.studio.pilates.dto.AulaDetalhadaDTO;
import br.studio.pilates.model.entity.Aula;
import br.studio.pilates.model.entity.Usuario;
import br.studio.pilates.model.entity.repository.AlunoRepository;
import br.studio.pilates.model.entity.repository.AulaRepository;
import br.studio.pilates.model.entity.repository.EstudioRepository;
import br.studio.pilates.model.entity.repository.UsuarioRepository;

/**
 * Serviço responsável por operações relacionadas à entidade Aula,
 * incluindo buscas detalhadas, filtragem, validações e manipulação dos dados.
 */
@Service
public class AulaService {

    @Autowired
    private EstudioRepository estudioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private AulaRepository aulaRepository;

    /**
     * Retorna um DTO detalhado da aula com informações do estúdio, instrutor e alunos.
     * 
     * @param id ID da aula.
     * @return AulaDetalhadaDTO contendo os dados completos da aula, ou null se não encontrada.
     */
    public AulaDetalhadaDTO getAulaDetalhadaById(String id) {
        Aula aula = aulaRepository.findAulaById(id);
        if (aula == null) return null;

        AulaDetalhadaDTO dto = new AulaDetalhadaDTO();
        dto.setId(aula.getId());
        dto.setEstudio(estudioRepository.findEstudioById(aula.getIdStudio()));
        dto.setInstrutor(aula.getIdInstrutor() != null ? usuarioRepository.findUsuarioById(aula.getIdInstrutor()) : null);
        dto.setAlunos(
            aula.getAlunos() != null
                ? aula.getAlunos().stream()
                    .map(alunoRepository::findAlunoById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList())
                : java.util.Collections.emptyList()
        );
        dto.setData(aula.getData());
        dto.setHorario(aula.getHorario());
        dto.setStatus(aula.getStatus());
        dto.setObservacoes(aula.getObservacoes());
        return dto;
    }

    /**
     * Retorna todas as aulas cadastradas.
     * 
     * @return Lista de todas as aulas.
     */
    public List<Aula> getAllAulas() {
        return aulaRepository.findAll();
    }

    /**
     * Retorna uma lista de aulas filtradas por data, instrutor e aluno.
     * 
     * @param data Data da aula (pode ser null para não filtrar).
     * @param idInstrutor ID do instrutor (pode ser null para não filtrar).
     * @param alunos ID do aluno (pode ser null para não filtrar).
     * @return Lista filtrada de aulas.
     */
    public List<Aula> getAulasFiltradas(LocalDate data, String idInstrutor, String alunos) {
        return aulaRepository.findAll().stream()
            .filter(aula -> data == null || data.equals(aula.getData()))
            .filter(aula -> idInstrutor == null || idInstrutor.equals(aula.getIdInstrutor()))
            .filter(aula -> alunos == null || (aula.getAlunos() != null && aula.getAlunos().contains(alunos)))
            .toList();
    }

    /**
     * Busca uma aula pelo seu ID.
     * 
     * @param Id ID da aula.
     * @return Objeto Aula encontrado ou null se não existir.
     */
    public Aula getAulaById(String Id) {
        return aulaRepository.findAulaById(Id);
    }

    /**
     * Salva uma aula, validando se estúdio, instrutor e alunos existem e se os dados estão corretos.
     * Define status "Pendente" se não informado.
     * 
     * @param aula Objeto Aula a ser salvo.
     * @return Aula salva.
     * @throws IllegalArgumentException se estúdio, instrutor ou aluno(s) não existirem ou forem inválidos.
     */
    public Aula saveAula(Aula aula) {
        if (aula.getStatus() == null) {
            aula.setStatus("Pendente");
        }
        // Valida Estúdio
        if (!estudioRepository.existsById(aula.getIdStudio())) {
            throw new IllegalArgumentException("Estúdio não encontrado.");
        }
        // Valida Instrutor
        if (aula.getIdInstrutor() != null) {
            Usuario instrutor = usuarioRepository.findById(aula.getIdInstrutor()).orElse(null);
            if (instrutor == null || !"instrutor".equalsIgnoreCase(instrutor.getRole())) {
                throw new IllegalArgumentException("Instrutor não encontrado ou inválido.");
            }
        }
        // Valida Alunos
        for (String alunoId : aula.getAlunos()) {
            if (!alunoRepository.existsById(alunoId)) {
                throw new IllegalArgumentException("Aluno não encontrado: " + alunoId);
            }
        }
        return aulaRepository.save(aula);
    }

    /**
     * Remove uma aula pelo seu ID.
     * 
     * @param Id ID da aula a ser removida.
     */
    public void deleteAula(String Id) {
        aulaRepository.deleteAulaById(Id);
    }
}
