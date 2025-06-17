package br.studio.pilates.controller.webController;

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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Controller responsável pelo gerenciamento das operações relacionadas ao agendamento de aulas,
 * acessível pelo recepcionista através da interface web.
 * Rota base: /web/recepcionista/agendamento
 */
@Controller
@RequestMapping("/web/recepcionista/agendamento")
public class AgendamentoWebController {

    @Autowired
    private AulaService aulaService;

    @Autowired
    private EstudioService estudioService;

    @Autowired
    private AlunoService alunoService;

    /**
     * Método GET que carrega a página de agendamento.
     * Prepara os dados necessários para a view, incluindo lista de aulas, estúdios e alunos.
     *
     * @param model Model usado para enviar atributos para a view Thymeleaf.
     * @return nome da página HTML para renderizar.
     */
    @GetMapping
    public String agendamento(Model model) {
        // Busca todas as aulas cadastradas
        List<Aula> aulas = aulaService.getAllAulas();
        // Busca todos os estúdios cadastrados
        List<Estudio> estudios = estudioService.getAllEstudio();
        // Busca todos os alunos cadastrados
        List<Aluno> alunos = alunoService.listarTodos();

        // Adiciona estúdios e alunos ao model para uso na página
        model.addAttribute("estudios", estudios);
        model.addAttribute("alunos", alunos);

        // Mapeia as aulas para um DTO adequado para exibição, incluindo dados relacionados
        List<AulaAgendamentoDTO> aulasDTO = aulas.stream()
            .map(aula -> {
                AulaAgendamentoDTO dto = new AulaAgendamentoDTO();

                // Preenchendo dados básicos da aula
                dto.setId(aula.getId());
                dto.setData(aula.getData());
                dto.setHorario(aula.getHorario());
                dto.setStatus(aula.getStatus());
                dto.setPresentes(aula.getPresentes() != null ? aula.getPresentes() : List.of());
                dto.setObservacoes(aula.getObservacoes());

                // Campos default caso não informados
                dto.setModalidade("Não informado");
                dto.setInstrutorNome("Não informado");

                // Busca o nome do estúdio relacionado, se houver
                String nomeEstudio = "Não informado";
                if (aula.getIdStudio() != null) {
                    Estudio estudio = estudioService.getEstudioById(aula.getIdStudio());
                    if (estudio != null && estudio.getNome() != null) {
                        nomeEstudio = estudio.getNome();
                    }
                }
                dto.setEstudioNome(nomeEstudio);

                // Busca nomes dos alunos presentes na aula, caso haja alunos cadastrados
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
            // Ordena a lista de aulas primeiro por data, depois por horário
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

        // Adiciona a lista de aulas processadas ao model
        model.addAttribute("aulas", aulasDTO);

        // Retorna o template Thymeleaf da página de agendamento
        return "recepcionista/agendamento";
    }

    /**
     * Método POST que salva uma nova aula ou atualiza uma existente.
     * Caso o status não seja informado, define como "Pendente".
     *
     * @param aula             Objeto Aula preenchido a partir do formulário.
     * @param redirectAttributes Para passar mensagens flash após redirecionamento.
     * @return redireciona de volta para a página de agendamento.
     */
    @PostMapping("/salvar")
    public String salvarAula(Aula aula, RedirectAttributes redirectAttributes) {
        try {
            // Define status padrão "Pendente" caso não informado
            if (aula.getStatus() == null || aula.getStatus().isEmpty()) {
                aula.setStatus("Pendente");
            }
            // Salva ou atualiza a aula no banco
            aulaService.saveAula(aula);

            // Mensagem de sucesso para o usuário
            redirectAttributes.addFlashAttribute("mensagem", "Aula agendada com sucesso!");
            redirectAttributes.addFlashAttribute("mensagemTipo", "sucesso");
        } catch (Exception e) {
            // Em caso de erro, informa mensagem para o usuário
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao agendar aula: " + e.getMessage());
            redirectAttributes.addFlashAttribute("mensagemTipo", "erro");
        }
        // Redireciona para a página de agendamento para evitar reenvio do formulário
        return "redirect:/web/recepcionista/agendamento";
    }

    /**
     * Método PUT para marcar presença dos alunos em uma aula.
     * Recebe lista de IDs dos alunos presentes e atualiza a aula.
     *
     * @param aulaId   ID da aula que terá presença atualizada.
     * @param presentes Lista opcional com IDs dos alunos presentes.
     * @return String simples com resultado da operação ("ok" ou mensagem de erro).
     */
    @PutMapping("/presenca/{id}")
    @ResponseBody
    public String marcarPresenca(@PathVariable("id") String aulaId, @RequestBody(required = false) List<String> presentes) {
        // Busca a aula pelo ID
        Aula aula = aulaService.getAulaById(aulaId);
        if (aula == null) {
            return "Aula não encontrada";
        }

        // Se não houver presentes informados, marca como "Ausente" e limpa lista
        if (presentes == null || presentes.isEmpty()) {
            aula.setStatus("Ausente");
            aula.setPresentes(List.of());
        } else {
            // Caso contrário, marca como "Presente" e define lista de presentes
            aula.setStatus("Presente");
            aula.setPresentes(presentes);
        }
        // Salva a atualização da aula
        aulaService.saveAula(aula);
        return "ok";
    }

    /**
     * Método POST para cancelar uma aula.
     * Altera o status da aula para "Cancelada".
     *
     * @param id ID da aula a ser cancelada.
     * @return redireciona para a página de agendamento.
     */
    @PostMapping("/deletar/{id}")
    public String cancelarAula(@PathVariable("id") String id) {
        // Busca a aula pelo ID
        Aula aula = aulaService.getAulaById(id);
        if (aula != null) {
            // Altera status para "Cancelada"
            aula.setStatus("Cancelada");
            aulaService.saveAula(aula);
        }
        // Redireciona para agendamento para atualizar tela
        return "redirect:/web/recepcionista/agendamento";
    }
}
