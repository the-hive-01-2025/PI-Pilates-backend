package br.studio.pilates.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgendaInstrutorDTO {
    private String id;
    private String data;
    private String horario;
    private String alunos;
    private String status;
    private String observacoes;
}