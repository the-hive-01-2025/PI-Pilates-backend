// Pacote onde o controller está localizado
package br.studio.pilates.controller.webController;

// Importações dos modelos, DTOs e serviços necessários
import br.studio.pilates.dto.AulaAgendamentoDTO;
import br.studio.pilates.model.entity.Aluno;
import br.studio.pilates.model.entity.Aula;
import br.studio.pilates.model.entity.Estudio;
import br.studio.pilates.service.AlunoService;
import br.studio.pilates.service.AulaService;
import br.studio.pilates.service.EstudioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

// Define que essa classe é um controller que irá responder para a rota "/web/instrutor/agenda"
@Controller
@RequestMapping("/web/instrutor/agenda")
public class AgendaWebController {

    // Injeta o serviço de Aula para buscar e manipular dados das aulas
    @Autowired
    private AulaService aulaService;

    // Injeta o serviço de Estúdio para buscar dados dos estúdios
    @Autowired
    private EstudioService estudioService;

    // Injeta o serviço de Aluno para buscar informações dos alunos
    @Autowired
    private AlunoService alunoService;

    // Método que trata requisições GET na URL /web/instrutor/agenda
    @GetMapping
    public String agenda(Model model) {
        // Busca todas as aulas do banco
        List<Aula> aulas = aulaService.getAllAulas();

        // Busca todos os estúdios do banco
        List<Estudio> estudios = estudioService.getAllEstudio();

        // Busca todos os alunos cadastrados
        List<Aluno> alunos = alunoService.listarTodos();

        // Converte as aulas para um DTO que será exibido na view
        List<AulaAgendamentoDTO> aulasDTO = aulas.stream()
            .map(aula -> {
                AulaAgendamentoDTO dto = new AulaAgendamentoDTO();
                dto.setId(aula.getId());
                dto.setData(aula.getData());
                dto.setHorario(aula.getHorario());
                dto.setStatus(aula.getStatus());
                dto.setPresentes(aula.getPresentes() != null ? aula.getPresentes() : List.of());
                dto.setObservacoes(aula.getObservacoes());

                // Valores padrões para Modalidade e Instrutor (não foram implementados ainda)
                dto.setModalidade("Não informado");
                dto.setInstrutorNome("Não informado");

                // Busca o nome do estúdio associado à aula, se existir
                String nomeEstudio = "Não informado";
                if (aula.getIdStudio() != null) {
                    Estudio estudio = estudioService.getEstudioById(aula.getIdStudio());
                    if (estudio != null && estudio.getNome() != null) {
                        nomeEstudio = estudio.getNome();
                    }
                }
                dto.setEstudioNome(nomeEstudio);

                // Converte a lista de IDs dos alunos da aula para uma lista de nomes
                if (aula.getAlunos() != null) {
                    List<String> nomesAlunos = aula.getAlunos().stream()
                        .map(alunoId -> {
                            Aluno aluno = alunoService.getById(alunoId).orElse(null);
                            return aluno != null ? aluno.getNome() : "";
                        })
                        .toList();
                    dto.setAlunos(nomesAlunos);
                } else {
                    dto.setAlunos(List.of());
                }

                return dto;
            })
            // Ordena as aulas por data e horário, para exibição cronológica na agenda
            .sorted((a1, a2) -> {
                if (a1.getData() == null && a2.getData() == null) return 0;
                if (a1.getData() == null) return 1;
                if (a2.getData() == null) return -1;
                int cmp = a1.getData().compareTo(a2.getData());
                if (cmp != 0) return cmp;
                if (a1.getHorario() == null && a2.getHorario() == null) return 0;
                if (a1.getHorario() == null) return 1;
                if (a2.getHorario() == null) return -1;
                return a1.getHorario().compareTo(a2.getHorario());
            })
            .toList();

        // Adiciona os dados no Model para serem acessíveis no Thymeleaf (front-end)
        model.addAttribute("estudios", estudios);
        model.addAttribute("alunos", alunos);
        model.addAttribute("aulas", aulasDTO);

        // Retorna o template da agenda do instrutor
        return "instrutor/agenda";
    }

    // Endpoint para cancelar uma aula (POST na URL /web/instrutor/agenda/deletar/{id})
    @PostMapping("/deletar/{id}")
    public String cancelarAula(String id) {
        // Busca a aula pelo ID
        Aula aula = aulaService.getAulaById(id);

        if (aula != null) {
            // Define o status da aula como "Cancelada"
            aula.setStatus("Cancelada");

            // Salva a alteração no banco
            aulaService.saveAula(aula);
        }

        // Redireciona de volta para a página da agenda após o cancelamento
        return "redirect:/web/instrutor/agenda";
    }
}
