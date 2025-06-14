package br.studio.pilates.model.entity;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Document(collection = "registrosEvolucao")
public class RegEvolucao{

    @Id
    private String id;
    
    private LocalDate data;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String instrutor;
    private String nomeAula;
    private String obs; //obersações
    private Aluno aluno;
    
}