package br.studio.pilates.mock;

import br.studio.pilates.model.entity.Aluno;
import br.studio.pilates.model.entity.Plano;
import br.studio.pilates.service.PlanoService;
import org.springframework.stereotype.Component;

@Component
public class AlunoMockFactory {

    private final PlanoService planoService;

    public AlunoMockFactory(PlanoService planoService) {
        this.planoService = planoService;
    }

    public Aluno criarAlunoMock() {
        Aluno aluno = new Aluno();
        aluno.setId("mock123");
        aluno.setNome("Guilherme Souza");
        aluno.setCpf("000.000.000-00");

        Plano plano = planoService.getPlanoByNome("Mensal");
        aluno.setPlano(plano);

        // Aqui você pode colocar o histórico de pagamentos se quiser
        return aluno;
    }
}
