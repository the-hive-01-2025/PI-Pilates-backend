package br.studio.pilates.model.entity;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "Financeiro")
public class Financeiro { 

    @Id
    private String id;
    private String idAluno;
    private LocalDate dataPagamento;

    public Financeiro(String id, String idAluno,LocalDate dataPagamento) {
    
        super();
        this.id = id;
        this.idAluno = idAluno;
        this.dataPagamento = dataPagamento;
    }
}