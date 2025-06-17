package br.studio.pilates.model.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

/**
 * Representa um Plano no sistema Pilates.
 * Contém informações como nome, valor, dia de vencimento e descrição do plano.
 */
@Getter
@Setter
@Document(collection = "Planos")
public class Plano {

    /** Identificador único do plano. */
    @Id
    private String id;
    
    /** Nome do plano. */
    private String nomePlano;

    /** Valor do plano (mensalidade ou preço). */
    private Double valor;

    /** Dia do mês para vencimento do plano (ex: 5 = 5º dia do mês). */
    private Integer diaVencimento;

    /** Descrição detalhada do plano. */
    private String descricao;

    /** Construtor padrão. */
    public Plano() {
    }

    /**
     * Construtor completo para inicializar todos os campos do plano.
     * 
     * @param id identificador único
     * @param nomePlano nome do plano
     * @param valor valor do plano
     * @param diaVencimento dia do mês para vencimento
     * @param descricao descrição do plano
     */
    public Plano(String id, String nomePlano, Double valor, Integer diaVencimento, String descricao) {
        super();
        this.id = id;
        this.nomePlano = nomePlano;
        this.valor = valor;
        this.diaVencimento = diaVencimento;
        this.descricao = descricao;
    }
}
