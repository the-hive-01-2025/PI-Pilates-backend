package br.studio.pilates.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import br.studio.pilates.model.entity.Estudio;
import br.studio.pilates.model.entity.Usuario;
import br.studio.pilates.model.entity.Aluno;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AulaDetalhadaDTO {
    private String id;
    private Estudio estudio;
    private Usuario instrutor;
    private List<Aluno> alunos;
    private LocalDate data;
    private LocalTime horario;
    private String status;
    private String observacoes;
}