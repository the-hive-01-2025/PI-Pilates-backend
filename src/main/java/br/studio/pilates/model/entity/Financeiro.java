package br.studio.pilates.model.entity;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "Financeiro")
public class Financeiro {

    @Id
    private String id;

    @DBRef
    private Aluno aluno;

    @DBRef
    private Plano plano;

    private Double valor;

    private LocalDate dataPagamento;
    private LocalDate dataVencimento;

    private Boolean paga;

    public boolean isPaga() {
        return Boolean.TRUE.equals(paga);
    }

    private String formaPagamento;

    public Financeiro() {

    }

    public Financeiro(String id, Aluno aluno, Plano plano, Double valor, LocalDate dataPagamento,
            LocalDate dataVencimento, Boolean paga, String formaPagamento) {
        super();

        this.id = id;
        this.aluno = aluno;
        this.plano = plano;
        this.valor = valor;
        this.dataPagamento = dataPagamento;
        this.dataVencimento = dataVencimento;
        this.paga = paga;
        this.formaPagamento = formaPagamento;
    }

}