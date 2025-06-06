package br.studio.pilates.model.entity.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.studio.pilates.model.entity.FichaAvaliacao;

public interface FichaAvaliacaoRepository extends MongoRepository<FichaAvaliacao, String> {

    public Optional<FichaAvaliacao> findById(String Id);

    public Optional<FichaAvaliacao> findByIdAluno(String idAluno); // busca por id do aluno

    public List<FichaAvaliacao> findByData(String data); // busca avaliações por datas

    public List<FichaAvaliacao> findByDiagnosticoContains(String diagnostico); // que contém diagnóstico

    public List<FichaAvaliacao> findByhistoricoMedicoContains(String historicoMedico); // que contém histórico
                                                                                       // mContains()

    public List<FichaAvaliacao> findByPatologiasContains(String patologias); // que contém patologias

    public List<FichaAvaliacao> findByExamesContains(String exames);

    public List<FichaAvaliacao> findByMedicamentosContains(String medicamentos); // que contém medicamentos

    public List<FichaAvaliacao> findByTratamentosContains(String tratamentos); // busca por tratamentos

    public List<FichaAvaliacao> findByObjetivosContains(String objetivos); // busca por objetivos

    public List<FichaAvaliacao> findByDataAvaliacao(String dataAvaliacao); // busca por data de avaliação
}
