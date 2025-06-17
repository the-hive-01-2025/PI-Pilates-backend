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

/**
 * Serviço responsável pelas operações relacionadas à entidade Plano.
 * 
 * Fornece métodos para buscar, salvar, deletar planos e para associar planos a alunos,
 * gerando faturas financeiras correspondentes.
 */
@Service
public class PlanoService {

    @Autowired
    private PlanoRepository planoRepository;

    @Autowired
    private AlunoService alunoService;

    /**
     * Retorna a lista de todos os planos cadastrados.
     * 
     * @return lista de objetos Plano
     */
    public List<Plano> getAllPlanos() {
        return planoRepository.findAll();
    }

    /**
     * Busca um plano pelo seu identificador único.
     * 
     * @param id identificador do plano
     * @return Optional contendo o Plano caso encontrado, ou vazio caso contrário
     */
    public Optional<Plano> getPlanoById(String id) {
        return planoRepository.findById(id);
    }

    /**
     * Busca um plano pelo nome do plano.
     * 
     * @param nome nome do plano
     * @return objeto Plano correspondente ao nome informado, ou null se não encontrado
     */
    public Plano getPlanoByNome(String nome) {
        return planoRepository.findByNomePlano(nome).orElse(null);
    }

    /**
     * Salva um plano no banco de dados.
     * 
     * @param plano objeto Plano a ser salvo
     * @return objeto Plano salvo com as informações atualizadas
     */
    public Plano savePlano(Plano plano) {
        return planoRepository.save(plano);
    }

    /**
     * Deleta um plano pelo seu identificador.
     * 
     * @param id identificador do plano a ser deletado
     */
    public void deletePlano(String id) {
        planoRepository.deleteById(id);
    }

    /**
     * Associa um plano a um aluno identificado pelo CPF, criando uma fatura no financeiro
     * para registro da assinatura.
     * 
     * @param cpf CPF do aluno que irá assinar o plano
     * @param plano objeto Plano a ser associado ao aluno
     * @param formaPagamento forma de pagamento utilizada para a assinatura
     * @return objeto Aluno atualizado com o novo plano e fatura no histórico de pagamento
     * @throws IllegalArgumentException se o aluno não for encontrado ou o plano for inválido
     */
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
