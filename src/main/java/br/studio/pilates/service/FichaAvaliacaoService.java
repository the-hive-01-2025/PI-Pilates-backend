package br.studio.pilates.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.studio.pilates.model.entity.FichaAvaliacao;
import br.studio.pilates.model.entity.repository.FichaAvaliacaoRepository;

@Service
public class FichaAvaliacaoService {

    @Autowired
    private FichaAvaliacaoRepository fichaRepository;

    // GETTERS
    public Optional<FichaAvaliacao> pegaFichaById(String id) {
        return fichaRepository.findById(id);
    }

    public List<FichaAvaliacao> pegaFichas() {
        return fichaRepository.findAll();
    }

    public Optional<FichaAvaliacao> getByAluno(String idAluno) {
        return fichaRepository.findByIdAluno(idAluno);
    }

    public List<FichaAvaliacao> getByData(String data) {
        return fichaRepository.findByData(data);
    }

    public List<FichaAvaliacao> getByDataAvaliacao(String dataAvaliacao) {
        return fichaRepository.findByDataAvaliacao(dataAvaliacao);
    }

    public List<FichaAvaliacao> getByDiagnostico(String diagnostico) {
        return fichaRepository.findByDiagnosticoContains(diagnostico);
    }

    public List<FichaAvaliacao> getByHistoricoMedico(String historicoMedico) {
        return fichaRepository.findByhistoricoMedicoContains(historicoMedico);
    }

    public List<FichaAvaliacao> getByPatologias(String patologias) {
        return fichaRepository.findByPatologiasContains(patologias);
    }

    public List<FichaAvaliacao> getByExames(String exames) {
        return fichaRepository.findByExamesContains(exames);
    }

    public List<FichaAvaliacao> getByMedicamentos(String medicamentos) {
        return fichaRepository.findByMedicamentosContains(medicamentos);
    }

    public List<FichaAvaliacao> getByTratamentos(String tratamentos) {
        return fichaRepository.findByTratamentosContains(tratamentos);
    }

    public List<FichaAvaliacao> getByObjetivos(String objetivos) {
        return fichaRepository.findByObjetivosContains(objetivos);
    }

    // SAVES
    public FichaAvaliacao salvaFicha(FichaAvaliacao ficha) {
        return fichaRepository.save(ficha);
    }

    // UPDATES
    public FichaAvaliacao atualizaFicha(String id, FichaAvaliacao ficha) {
       Optional<FichaAvaliacao> fichaExiste = fichaRepository.findByIdAluno(ficha.getIdAluno());

        if (fichaExiste.isPresent() && !fichaExiste.get().getId().equals(id)) {
            throw new IllegalArgumentException("Já existe um aluno com este id:" + id);
        }

        ficha.setId(id); // garante que estamos atualizando o aluno certo

        return fichaRepository.save(ficha);
    }

    // DELETES
    public void deletaFicha(String id) {
        fichaRepository.deleteById(id);
    }
}
