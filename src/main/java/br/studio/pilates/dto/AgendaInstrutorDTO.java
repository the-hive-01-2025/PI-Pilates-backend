package br.studio.pilates.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import br.studio.pilates.model.entity.Estudio;
import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) para representar a agenda de um instrutor.
 * Contém informações sobre o estúdio, data, horário, alunos presentes, status e observações.
 */
@Getter
@Setter
public class AgendaInstrutorDTO {

    /**
     * Identificador único da agenda.
     */
    private String id;

    /**
     * Estúdio onde a aula ou atividade será realizada.
     */
    private Estudio idStudio;

    /**
     * Data da aula ou atividade.
     */
    private LocalDate data;

    /**
     * Horário da aula ou atividade.
     */
    private LocalTime horario;

    /**
     * Lista de nomes dos alunos presentes na aula/atividade.
     */
    private List<String> alunos;

    /**
     * Status da agenda (exemplo: confirmado, cancelado, pendente).
     */
    private String status;

    /**
     * Observações adicionais relacionadas à agenda.
     */
    private String observacoes;
}
