package br.studio.pilates.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.studio.pilates.model.entity.FichaAvaliacao;
import br.studio.pilates.model.entity.repository.FichaAvaliacaoRepository;

/**
 * Serviço responsável pelas operações relacionadas à entidade FichaAvaliacao.
 * 
 * Fornece métodos para buscar, salvar, atualizar, adicionar informações específicas
 * e deletar fichas de avaliação de alunos.
 */
@Service
public class FichaAvaliacaoService {

    @Autowired
    private FichaAvaliacaoRepository fichaRepository;

    /**
     * Busca uma ficha de avaliação pelo seu ID.
     * 
     * @param id identificador da ficha
     * @return Optional contendo a ficha caso exista, ou vazio caso contrário
     */
    public Optional<FichaAvaliacao> pegaFichaById(String id) {
        return fichaRepository.findById(id);
    }

    /**
     * Retorna todas as fichas de avaliação cadastradas.
     * 
     * @return lista de todas as fichas
     */
    public List<FichaAvaliacao> pegaFichas() {
        return fichaRepository.findAll();
    }

    /**
     * Busca uma ficha de avaliação pelo ID do aluno.
     * 
     * @param idAluno identificador do aluno
     * @return Optional contendo a ficha do aluno, ou vazio caso não exista
     */
    public Optional<FichaAvaliacao> getByAluno(String idAluno) {
        return fichaRepository.findByIdAluno(idAluno);
    }

    /**
     * Busca fichas por data específica.
     * 
     * @param data data no formato String
     * @return lista de fichas com a data informada
     */
    public List<FichaAvaliacao> getByData(String data) {
        return fichaRepository.findByData(data);
    }

    /**
     * Busca fichas pela data de avaliação.
     * 
     * @param dataAvaliacao data da avaliação
     * @return lista de fichas com a data de avaliação informada
     */
    public List<FichaAvaliacao> getByDataAvaliacao(String dataAvaliacao) {
        return fichaRepository.findByDataAvaliacao(dataAvaliacao);
    }

    /**
     * Busca fichas que contenham determinado diagnóstico.
     * 
     * @param diagnostico texto parcial ou completo do diagnóstico
     * @return lista de fichas que contenham o diagnóstico informado
     */
    public List<FichaAvaliacao> getByDiagnostico(String diagnostico) {
        return fichaRepository.findByDiagnosticoContains(diagnostico);
    }

    /**
     * Busca fichas que contenham determinado histórico médico.
     * 
     * @param historicoMedico texto parcial ou completo do histórico médico
     * @return lista de fichas que contenham o histórico informado
     */
    public List<FichaAvaliacao> getByHistoricoMedico(String historicoMedico) {
        return fichaRepository.findByhistoricoMedicoContains(historicoMedico);
    }

    /**
     * Busca fichas que contenham determinadas patologias.
     * 
     * @param patologias texto parcial ou completo das patologias
     * @return lista de fichas que contenham as patologias informadas
     */
    public List<FichaAvaliacao> getByPatologias(String patologias) {
        return fichaRepository.findByPatologiasContains(patologias);
    }

    /**
     * Busca fichas que contenham determinados exames.
     * 
     * @param exames texto parcial ou completo dos exames
     * @return lista de fichas que contenham os exames informados
     */
    public List<FichaAvaliacao> getByExames(String exames) {
        return fichaRepository.findByExamesContains(exames);
    }

    /**
     * Busca fichas que contenham determinados medicamentos.
     * 
     * @param medicamentos texto parcial ou completo dos medicamentos
     * @return lista de fichas que contenham os medicamentos informados
     */
    public List<FichaAvaliacao> getByMedicamentos(String medicamentos) {
        return fichaRepository.findByMedicamentosContains(medicamentos);
    }

    /**
     * Busca fichas que contenham determinados tratamentos.
     * 
     * @param tratamentos texto parcial ou completo dos tratamentos
     * @return lista de fichas que contenham os tratamentos informados
     */
    public List<FichaAvaliacao> getByTratamentos(String tratamentos) {
        return fichaRepository.findByTratamentosContains(tratamentos);
    }

    /**
     * Busca fichas que contenham determinados objetivos.
     * 
     * @param objetivos texto parcial ou completo dos objetivos
     * @return lista de fichas que contenham os objetivos informados
     */
    public List<FichaAvaliacao> getByObjetivos(String objetivos) {
        return fichaRepository.findByObjetivosContains(objetivos);
    }

    /**
     * Salva uma ficha de avaliação no banco de dados.
     * 
     * @param ficha objeto ficha a ser salvo
     * @return ficha salva
     */
    public FichaAvaliacao salvaFicha(FichaAvaliacao ficha) {
        return fichaRepository.save(ficha);
    }

    /**
     * Atualiza uma ficha existente com base no ID.
     * 
     * @param id identificador da ficha a ser atualizada
     * @param ficha objeto com dados atualizados
     * @return ficha atualizada
     * @throws IllegalArgumentException se já existir uma ficha para outro aluno com o mesmo ID
     */
    public FichaAvaliacao atualizaFicha(String id, FichaAvaliacao ficha) {
        Optional<FichaAvaliacao> fichaExiste = fichaRepository.findByIdAluno(ficha.getIdAluno());

        if (fichaExiste.isPresent() && !fichaExiste.get().getId().equals(id)) {
            throw new IllegalArgumentException("Já existe um aluno com este id:" + id);
        }

        ficha.setId(id); // garante que estamos atualizando o registro correto

        return fichaRepository.save(ficha);
    }

    /**
     * Adiciona uma patologia à ficha de avaliação de um aluno.
     * 
     * @param id identificador da ficha
     * @param patologia nova patologia a ser adicionada
     * @param ficha objeto ficha atualizado
     * @return ficha atualizada com a nova patologia
     * @throws IllegalArgumentException se a ficha do aluno não existir
     */
    public FichaAvaliacao addPat(String id, String patologia, FichaAvaliacao ficha) {
        Optional<FichaAvaliacao> fichaExiste = fichaRepository.findByIdAluno(ficha.getIdAluno());

        if (fichaExiste.isEmpty()) {
            throw new IllegalArgumentException("Nao existe um aluno com este id:" + id);
        }

        ficha.setId(id);
        ficha.getPatologias().add(patologia);
        return fichaRepository.save(ficha);
    }

    /**
     * Adiciona um medicamento à ficha de avaliação de um aluno.
     * 
     * @param id identificador da ficha
     * @param medicamento novo medicamento a ser adicionado
     * @param ficha objeto ficha atualizado
     * @return ficha atualizada com o novo medicamento
     * @throws IllegalArgumentException se a ficha do aluno não existir
     */
    public FichaAvaliacao addMed(String id, String medicamento, FichaAvaliacao ficha) {
        Optional<FichaAvaliacao> fichaExiste = fichaRepository.findByIdAluno(ficha.getIdAluno());

        if (fichaExiste.isEmpty()) {
            throw new IllegalArgumentException("Nao existe um aluno com este id:" + id);
        }

        ficha.setId(id);
        ficha.getMedicamentos().add(medicamento);
        return fichaRepository.save(ficha);
    }

    /**
     * Adiciona um tratamento à ficha de avaliação de um aluno.
     * 
     * @param id identificador da ficha
     * @param tratamento novo tratamento a ser adicionado
     * @param ficha objeto ficha atualizado
     * @return ficha atualizada com o novo tratamento
     * @throws IllegalArgumentException se a ficha do aluno não existir
     */
    public FichaAvaliacao addTrat(String id, String tratamento, FichaAvaliacao ficha) {
        Optional<FichaAvaliacao> fichaExiste = fichaRepository.findByIdAluno(ficha.getIdAluno());

        if (fichaExiste.isEmpty()) {
            throw new IllegalArgumentException("Nao existe um aluno com este id:" + id);
        }

        ficha.setId(id);
        ficha.getTratamentos().add(tratamento);
        return fichaRepository.save(ficha);
    }

    /**
     * Adiciona um objetivo à ficha de avaliação de um aluno.
     * 
     * @param id identificador da ficha
     * @param objetivo novo objetivo a ser adicionado
     * @param ficha objeto ficha atualizado
     * @return ficha atualizada com o novo objetivo
     * @throws IllegalArgumentException se a ficha do aluno não existir
     */
    public FichaAvaliacao addObj(String id, String objetivo, FichaAvaliacao ficha) {
        Optional<FichaAvaliacao> fichaExiste = fichaRepository.findByIdAluno(ficha.getIdAluno());

        if (fichaExiste.isEmpty()) {
            throw new IllegalArgumentException("Nao existe um aluno com este id:" + id);
        }

        ficha.setId(id);
        ficha.getObjetivos().add(objetivo);
        return fichaRepository.save(ficha);
    }

    /**
     * Remove uma ficha de avaliação pelo seu ID.
     * 
     * @param id identificador da ficha a ser deletada
     */
    public void deletaFicha(String id) {
        fichaRepository.deleteById(id);
    }
}
