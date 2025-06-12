package br.studio.pilates.model.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "Planos")
public class Plano {

    @Id
    private String id;
    
    private String nomePlano;
    private Double valor;
    private Integer diaVencimento; 
    private String descricao;

    public Plano() {

    }

    public Plano(String id, String nomePlano, Double valor, Integer diaVencimento, String descricao) {

        super();
        this.id = id;
        this.nomePlano = nomePlano;
        this.valor = valor;
        this.diaVencimento = diaVencimento;
        this.descricao = descricao;
    }
}