package br.studio.pilates.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.studio.pilates.model.entity.Aluno;
import br.studio.pilates.model.entity.RegEvolucao;
import br.studio.pilates.model.entity.repository.RegEvolucaoRepository;

@Service
public class RegEvolucaoService {
    
    @Autowired
    RegEvolucaoRepository regRepository;

    public List<RegEvolucao> listarPorAluno(Aluno aluno){
        return regRepository.findByAluno(aluno).orElse(null);
    }

    public String salvar(RegEvolucao registro){
        try {
            regRepository.save(registro);
            return "salvo";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
