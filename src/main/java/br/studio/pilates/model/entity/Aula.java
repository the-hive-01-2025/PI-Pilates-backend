package br.studio.pilates.model.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "Aulas")
public class Aula { 

    @Id
    private String id;     
    private String idStudio;
    private String idInstrutor;
    private String alunos;
    private String data;
    private String horario;
    private String status;
    private String observacoes;    
}
