package br.studio.pilates.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import br.studio.pilates.model.entity.RegEvolucao;
import br.studio.pilates.service.RegEvolucaoService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class RegEvolucaoController {
    
    @Autowired
    private RegEvolucaoService regService;

    @GetMapping("evolucao/{aluno}")
    public String getById(@PathVariable("aluno") String aluno) {
        return regService.listarPorAluno(aluno);
    }
    
    @PostMapping("evolucao/")
    public String salvarRegistro(@ModelAttribute RegEvolucao registro) {
        return regService.salvar(registro);
    }
}
