package br.studio.pilates.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import br.studio.pilates.model.entity.Estudio;
import br.studio.pilates.model.entity.Usuario;
import br.studio.pilates.model.entity.Aluno;
import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) que representa os detalhes completos de uma aula.
 * Contém informações detalhadas sobre o estúdio, instrutor, alunos, data, horário, status e observações.
 */
@Getter
@Setter
public class AulaDetalhadaDTO {

    /**
     * Identificador único da aula.
     */
    private String id;

    /**
     * Estúdio onde a aula será realizada.
     */
    private Estudio estudio;

    /**
     * Usuário que é o instrutor responsável pela aula.
     */
    private Usuario instrutor;

    /**
     * Lista dos alunos que participarão da aula.
     */
    private List<Aluno> alunos;

    /**
     * Data em que a aula ocorrerá.
     */
    private LocalDate data;

    /**
     * Horário em que a aula ocorrerá.
     */
    private LocalTime horario;

    /**
     * Status atual da aula (exemplo: confirmada, cancelada, pendente).
     */
    private String status;

    /**
     * Observações adicionais relacionadas à aula.
     */
    private String observacoes;
}
