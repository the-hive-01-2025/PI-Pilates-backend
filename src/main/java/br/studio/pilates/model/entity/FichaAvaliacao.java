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

/**
 * Entidade que representa uma ficha de avaliação física do aluno.
 * Contém dados clínicos, histórico, patologias, exames, medicamentos,
 * tratamentos, objetivos e fotos relacionados à avaliação.
 */
@Document(collection = "fichaAvaliacao")
@EntityScan
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FichaAvaliacao {

    /** Identificador único da ficha de avaliação. */
    @Id
    private String id;

    /** Identificador do aluno ao qual a ficha pertence. */
    private String idAluno;

    /** Data da criação ou registro da ficha (formato String). */
    private String data;

    /** Diagnóstico médico ou avaliação clínica do aluno. */
    private String diagnostico;

    /** Histórico médico relevante do aluno. */
    private String historicoMedico;

    /** Lista de patologias diagnosticadas ou conhecidas. */
    @Builder.Default
    private ArrayList<String> patologias = new ArrayList<>();

    /** Lista de exames realizados ou necessários. */
    @Builder.Default
    private ArrayList<String> exames = new ArrayList<>();

    /** Lista de medicamentos em uso pelo aluno. */
    @Builder.Default
    private ArrayList<String> medicamentos = new ArrayList<>();

    /** Lista de tratamentos indicados ou em andamento. */
    @Builder.Default
    private ArrayList<String> tratamentos = new ArrayList<>();

    /** Foto da avaliação física (pode ser URL ou caminho da imagem). */
    private String fotoAvaliacao;

    /** Lista de objetivos definidos para o aluno a partir da avaliação. */
    @Builder.Default
    private ArrayList<String> objetivos = new ArrayList<>();

    /** Data específica da avaliação (pode diferir da data do registro). */
    private String dataAvaliacao;

    /** Fotos adicionais relacionadas à avaliação, armazenadas como String (ex: URLs). */
    private String fotos;
}
