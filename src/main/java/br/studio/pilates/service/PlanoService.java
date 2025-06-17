package br.studio.pilates.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.studio.pilates.model.entity.Aluno;
import br.studio.pilates.model.entity.Financeiro;
import br.studio.pilates.model.entity.Plano;
import br.studio.pilates.model.entity.repository.PlanoRepository;

@Service
public class PlanoService {

    @Autowired
    private PlanoRepository planoRepository;

    @Autowired
    private AlunoService alunoService;

    public List<Plano> getAllPlanos() {
        return planoRepository.findAll();
    }

    public Optional<Plano> getPlanoById(String id) {
        return planoRepository.findById(id);
    }

    public Plano getPlanoByNome(String nome) {
    return planoRepository.findByNomePlano(nome).orElse(null);
    }


    public Plano savePlano(Plano plano) {
        return planoRepository.save(plano);
    }

    public void deletePlano(String id) {
        planoRepository.deleteById(id);
    }

    public Aluno assinarPlano(String cpf, Plano plano, String formaPagamento) {
        Aluno aluno = alunoService.getByCpf(cpf);
        if (aluno == null) {
            throw new IllegalArgumentException("Aluno não encontrado.");
        }

        if (plano == null) {
            throw new IllegalArgumentException("Plano inválido.");
        }

        aluno.setPlano(plano);

        // Criação da fatura
        Financeiro fatura = new Financeiro();
        fatura.setAluno(aluno);
        fatura.setPlano(plano);
        fatura.setValor(plano.getValor());
        fatura.setDataVencimento(LocalDate.now().plusDays(5)); // vencimento em 5 dias
        fatura.setDataPagamento(LocalDate.now());
        fatura.setPaga(true);
        fatura.setFormaPagamento(formaPagamento.toUpperCase());

        // Adiciona ao histórico
        if (aluno.getHistoricoPagamento() == null) {
            aluno.setHistoricoPagamento(new ArrayList<>());
        }

        aluno.getHistoricoPagamento().add(fatura);

        return alunoService.saveAluno(aluno);
    }

}
