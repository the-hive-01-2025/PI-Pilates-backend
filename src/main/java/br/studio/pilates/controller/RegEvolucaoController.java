package br.studio.pilates.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import br.studio.pilates.model.entity.RegEvolucao;
import br.studio.pilates.service.RegEvolucaoService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
public class RegEvolucaoController {
    
    @Autowired
    private RegEvolucaoService regService;

    @GetMapping("evolucao/")
    public List<RegEvolucao> teste(){
        return regService.listarTodos();
    }

    @GetMapping("evolucao/{aluno}")
    public List<RegEvolucao> getByAluno(@PathVariable("aluno") String aluno) {
        return regService.listarPorAluno(aluno);
    }
    
    @PostMapping("evolucao/")
    public String salvarRegistro(@ModelAttribute RegEvolucao registro) {
        return regService.salvar(registro);
    }

    @DeleteMapping("evolucao/")
    public String deletarRegistro(RegEvolucao registro){
        return regService.deletar(registro);
    }
}
