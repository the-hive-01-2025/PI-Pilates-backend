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
    private String aluno;
    private String alunoId;
    

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getInstrutor() {
        return instrutor;
    }

    public void setInstrutor(String instrutor) {
        this.instrutor = instrutor;
    }

    public String getNomeAula() {
        return nomeAula;
    }

    public void setNomeAula(String nomeAula) {
        this.nomeAula = nomeAula;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getAluno(){
        return this.aluno;
    }

    public void setAluno(String aluno){
        this.aluno = aluno;
    }

    public String getAlunoId(){
        return this.alunoId;
    }

    public void setAlunoId(String id){
        this.alunoId = id;
    }
}