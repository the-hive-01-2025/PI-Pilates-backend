package br.studio.pilates.model.entity;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

/**
 * Entidade que representa um registro financeiro relacionado a um aluno e plano.
 * Contém informações sobre pagamento, valor, datas e forma de pagamento.
 */
@Getter
@Setter
@Document(collection = "Financeiro")
public class Financeiro {

    /** Identificador único do registro financeiro. */
    @Id
    private String id;

    /** Referência ao aluno relacionado ao registro financeiro. */
    @DBRef
    private Aluno aluno;

    /** Referência ao plano associado ao registro financeiro. */
    @DBRef
    private Plano plano;

    /** Valor do pagamento relacionado ao registro financeiro. */
    private Double valor;

    /** Data em que o pagamento foi realizado. */
    private LocalDate dataPagamento;

    /** Data de vencimento do pagamento. */
    private LocalDate dataVencimento;

    /** Indica se o pagamento foi efetuado. */
    private Boolean paga;

    /**
     * Retorna verdadeiro se o pagamento foi efetuado (campo paga é true).
     * 
     * @return true se pago, false caso contrário
     */
    public boolean isPaga() {
        return Boolean.TRUE.equals(paga);
    }

    /** Forma utilizada para realizar o pagamento (ex: cartão, boleto, dinheiro). */
    private String formaPagamento;

    /** Construtor padrão. */
    public Financeiro() {
    }

    /**
     * Construtor completo para inicializar todos os campos do registro financeiro.
     * 
     * @param id identificador único
     * @param aluno aluno associado
     * @param plano plano associado
     * @param valor valor do pagamento
     * @param dataPagamento data do pagamento
     * @param dataVencimento data de vencimento
     * @param paga indica se foi pago
     * @param formaPagamento forma do pagamento
     */
    public Financeiro(String id, Aluno aluno, Plano plano, Double valor, LocalDate dataPagamento,
            LocalDate dataVencimento, Boolean paga, String formaPagamento) {
        super();
        this.id = id;
        this.aluno = aluno;
        this.plano = plano;
        this.valor = valor;
        this.dataPagamento = dataPagamento;
        this.dataVencimento = dataVencimento;
        this.paga = paga;
        this.formaPagamento = formaPagamento;
    }

}
