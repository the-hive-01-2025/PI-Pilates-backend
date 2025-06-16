package br.studio.pilates.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.studio.pilates.model.entity.RegEvolucao;
import br.studio.pilates.model.entity.repository.RegEvolucaoRepository;

@Service
public class RegEvolucaoService {
    
    @Autowired
    RegEvolucaoRepository regRepository;


    public List<RegEvolucao> listarTodos(){
        return regRepository.findAll();
    }

    public List<RegEvolucao> listarPorAluno(String aluno){
        return regRepository.findByAlunoIgnoreCase(aluno);
    }

    public String salvar(RegEvolucao registro){
        try {
            regRepository.save(registro);
            return "200";
            //TODO esta retornando 200 ao salvar registros nulos
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String deletar(RegEvolucao registro){
        try {
            regRepository.delete(registro);
            return "200";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
