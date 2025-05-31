package br.studio.pilates.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AulaAgendamentoDTO {
    private String id;
    private String estudioNome;
    private String instrutorNome;
    private String modalidade;
    private LocalDate data;
    private LocalTime horario;
    private String status;
}