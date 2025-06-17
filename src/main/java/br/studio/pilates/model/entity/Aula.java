package br.studio.pilates.model.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidade que representa uma Aula de Pilates.
 * Contém informações sobre o estúdio, instrutor, alunos, data, horário,
 * status, modalidade, presença e observações relacionadas à aula.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "Aulas")
public class Aula { 

    /** Identificador único da aula. */
    @Id
    private String id;

    /** Identificador do estúdio onde a aula será realizada. */
    private String idStudio;

    /** Identificador do instrutor responsável pela aula. */
    private String idInstrutor;

    /** Lista de IDs dos alunos inscritos na aula. */
    private List<String> alunos;

    /** Lista de IDs dos alunos presentes na aula. */
    private List<String> presentes;

    /** Data da aula.
     *  Formato JSON: "yyyy-MM-dd"
     */
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate data;

    /** Horário da aula.
     *  Formato JSON: "HH:mm"
     */
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonFormat(pattern = "HH:mm")
    private LocalTime horario;

    /** Status atual da aula (ex: agendada, concluída, cancelada). */
    private String status;

    /** Observações adicionais relacionadas à aula. */
    private String observacoes;

    /** Modalidade da aula (ex: Pilates clássico, solo, etc). */
    private String modalidade;       
}
