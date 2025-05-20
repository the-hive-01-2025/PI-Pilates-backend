package br.studio.pilates.mapper;

import br.studio.pilates.dto.AgendaInstrutorDTO;
import br.studio.pilates.model.entity.Aula;

public class AulaMapper {
    public static AgendaInstrutorDTO toDTO(Aula aula) {
        AgendaInstrutorDTO dto = new AgendaInstrutorDTO();
        dto.setId(aula.getId());
        dto.setData(aula.getData());
        dto.setHorario(aula.getHorario());
        dto.setAlunos(aula.getAlunos());
        dto.setStatus(aula.getStatus());
        dto.setObservacoes(aula.getObservacoes());
        return dto;
    }
}