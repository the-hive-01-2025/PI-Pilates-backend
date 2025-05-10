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

    public Plano(String id) {
    
        super();
        this.id = id;
    }
}