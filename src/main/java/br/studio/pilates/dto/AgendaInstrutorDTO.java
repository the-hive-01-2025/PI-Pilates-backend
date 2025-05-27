package br.studio.pilates.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgendaInstrutorDTO {
    private String id;
    private LocalDate data;
    private LocalTime horario;
    private List<String> alunos;
    private String status;
    private String observacoes;
}