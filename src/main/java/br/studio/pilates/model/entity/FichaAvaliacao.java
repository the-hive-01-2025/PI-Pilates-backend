package br.studio.pilates.model.entity;

import java.util.ArrayList;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "fichaAvaliacao")
@EntityScan
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FichaAvaliacao {

    @Id
    private String id;
    private String idAluno;
    private String data;
    private String diagnostico;
    private String historicoMedico;
    private ArrayList<String> patologias = new ArrayList<>();
    private ArrayList<String> exames = new ArrayList<>();
    private ArrayList<String> medicamentos = new ArrayList<>();
    private ArrayList<String> tratamentos = new ArrayList<>();
    private String fotoAvaliacao;
    private ArrayList<String> objetivos = new ArrayList<>();
    private String dataAvaliacao;

}
