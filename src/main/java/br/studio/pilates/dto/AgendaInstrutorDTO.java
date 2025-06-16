package br.studio.pilates.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import br.studio.pilates.model.entity.Estudio;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgendaInstrutorDTO {
    private String id;
    private Estudio idStudio;
    private LocalDate data;
    private LocalTime horario;
    private List<String> alunos;
    private String status;
    private String observacoes;   
}