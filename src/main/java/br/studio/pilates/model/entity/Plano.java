package br.studio.pilates.model.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
@Document(collection= "planos")

public class Plano {
	
	public Plano(String id, String tipo, String frequencia, int valor_mensal, int valor_total, String cancelamento) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.frequencia = frequencia;
		this.valor_mensal = valor_mensal;
		this.valor_total = valor_total;
		this.cancelamento = cancelamento;
	}
	@Id
	private String id;
	
	private String tipo;
	private String frequencia;
	private int valor_mensal;
	private int valor_total;
	private String cancelamento;
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getFrequencia() {
		return frequencia;
	}
	public void setFrequencia(String frequencia) {
		this.frequencia = frequencia;
	}
	public int getValor_mensal() {
		return valor_mensal;
	}
	public void setValor_mensal(int valor_mensal) {
		this.valor_mensal = valor_mensal;
	}
	public int getValor_total() {
		return valor_total;
	}
	public void setValor_total(int valor_total) {
		this.valor_total = valor_total;
	}
	public String getCancelamento() {
		return cancelamento;
	}
	public void setCancelamento(String cancelamento) {
		this.cancelamento = cancelamento;
	}
	
	
}
