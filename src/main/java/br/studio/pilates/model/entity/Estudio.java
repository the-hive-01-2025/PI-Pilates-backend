package br.studio.pilates.model.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

/**
 * Entidade que representa um Estúdio onde são realizadas as aulas de Pilates.
 * Contém informações básicas como nome, endereço, horários de funcionamento
 * e capacidade máxima de alunos por aula.
 */
@Getter
@Setter
@Document(collection = "Estudios")
public class Estudio { 

    /** Identificador único do estúdio. */
    @Id
    private String id;

    /** Nome do estúdio. */
    private String nome;

    /** Endereço físico do estúdio. */
    private String endereco;

    /** Horários de funcionamento do estúdio (ex: "08:00 às 22:00"). */
    private String horariosFuncionamento;

    /** Capacidade máxima de alunos por aula no estúdio. */
    private String maxAlunosAulas;

    /**
     * Construtor completo da entidade Estudio.
     * 
     * @param id Identificador único do estúdio.
     * @param nome Nome do estúdio.
     * @param endereco Endereço físico do estúdio.
     * @param horariosFuncionamento Horários de funcionamento do estúdio.
     * @param maxAlunosAulas Capacidade máxima de alunos por aula.
     */
    public Estudio(String id, String nome, String endereco, String horariosFuncionamento, String maxAlunosAulas) {
        super();
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.horariosFuncionamento = horariosFuncionamento;
        this.maxAlunosAulas = maxAlunosAulas;
    }
}
