package br.studio.pilates.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) que representa o agendamento de uma aula.
 * Contém informações sobre estúdio, instrutor, modalidade, data, horário, status, alunos e observações.
 */
@Getter
@Setter
public class AulaAgendamentoDTO {

    /**
     * Identificador único do agendamento da aula.
     */
    private String id;

    /**
     * Nome do estúdio onde a aula será realizada.
     */
    private String estudioNome;

    /**
     * Nome do instrutor responsável pela aula.
     */
    private String instrutorNome;

    /**
     * Modalidade da aula (ex: Pilates, Yoga, etc.).
     */
    private String modalidade;

    /**
     * Data agendada para a aula.
     */
    private LocalDate data;

    /**
     * Horário agendado para a aula.
     */
    private LocalTime horario;

    /**
     * Status do agendamento (ex: confirmado, cancelado, pendente).
     */
    private String status;

    /**
     * Lista com nomes dos alunos inscritos para a aula.
     */
    private List<String> alunos;

    /**
     * Lista com nomes dos alunos presentes na aula.
     */
    private List<String> presentes;

    /**
     * Observações adicionais sobre o agendamento da aula.
     */
    private String observacoes;
}
