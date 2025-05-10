package br.studio.pilates.model.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "Estudios")
public class Estudio { 

    @Id
    private String id;
    private String nome;
    private String endereco;
    private String horariosFuncionamento;
    private String maxAlunosAulas;

    public Estudio(String id, String nome, String endereco, String horariosFuncionamento, String maxAlunosAulas) {
    
        super();
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.horariosFuncionamento = horariosFuncionamento;
        this.maxAlunosAulas = maxAlunosAulas;
    }
}