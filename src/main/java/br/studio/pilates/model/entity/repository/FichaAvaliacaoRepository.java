package br.studio.pilates.model.entity.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.lang.NonNull;

import br.studio.pilates.model.entity.FichaAvaliacao;

/**
 * Repositório para operações CRUD e consultas customizadas na coleção de Fichas de Avaliação.
 */
public interface FichaAvaliacaoRepository extends MongoRepository<FichaAvaliacao, String> {

    @NonNull
    Optional<FichaAvaliacao> findById(@NonNull String Id);

    /**
     * Busca ficha de avaliação por ID do aluno.
     */
    Optional<FichaAvaliacao> findByIdAluno(String idAluno);

    /**
     * Busca avaliações por data.
     */
    List<FichaAvaliacao> findByData(String data);

    /**
     * Busca avaliações que contenham determinado diagnóstico.
     */
    List<FichaAvaliacao> findByDiagnosticoContains(String diagnostico);

    /**
     * Busca avaliações que contenham determinado histórico médico.
     */
    List<FichaAvaliacao> findByhistoricoMedicoContains(String historicoMedico);

    /**
     * Busca avaliações que contenham determinada patologia.
     */
    List<FichaAvaliacao> findByPatologiasContains(String patologias);

    /**
     * Busca avaliações que contenham determinado exame.
     */
    List<FichaAvaliacao> findByExamesContains(String exames);

    /**
     * Busca avaliações que contenham determinado medicamento.
     */
    List<FichaAvaliacao> findByMedicamentosContains(String medicamentos);

    /**
     * Busca avaliações que contenham determinado tratamento.
     */
    List<FichaAvaliacao> findByTratamentosContains(String tratamentos);

    /**
     * Busca avaliações que contenham determinado objetivo.
     */
    List<FichaAvaliacao> findByObjetivosContains(String objetivos);

    /**
     * Busca avaliações por data de avaliação.
     */
    List<FichaAvaliacao> findByDataAvaliacao(String dataAvaliacao);
}