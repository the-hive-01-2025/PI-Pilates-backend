package br.studio.pilates.model.entity;

import java.time.LocalDate;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
@Document(collection = "Alunos")

public class Aluno {
	
	@Id
	private String Id;
	
	private String nome;
	private LocalDate data;
	private Integer cpf; 
	private	String email;
	private Integer contato;
	private String profissao;
	
	//ficha de avaliação
	private FichaAvaliacao fichaAvaliacao;
	
	//planos
	private Plano plano;
		
	//Aulas marcadas
	private List<Aula> aulasMarcadas;
	
	//histórico de pagamento
	private List<Financeiro> historicoPagamento;
	
	private List<String> fotos;
	
	
	public Aluno() {
		
	}


	public Aluno(String id, String nome, LocalDate data, Integer cpf, String email, Integer contato, String profissao,
			FichaAvaliacao fichaAvaliacao, Plano plano, List<Aula> aulasMarcadas, List<Financeiro> historicoPagamento,
			List<String> fotos) {
		super();
		Id = id;
		this.nome = nome;
		this.data = data;
		this.cpf = cpf;
		this.email = email;
		this.contato = contato;
		this.profissao = profissao;
		this.fichaAvaliacao = fichaAvaliacao;
		this.plano = plano;
		this.aulasMarcadas = aulasMarcadas;
		this.historicoPagamento = historicoPagamento;
		this.fotos = fotos;
	}


	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public Integer getCpf() {
		return cpf;
	}

	public void setCpf(Integer cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getContato() {
		return contato;
	}

	public void setContato(Integer contato) {
		this.contato = contato;
	}

	public String getProfissao() {
		return profissao;
	}

	public void setProfissao(String profissao) {
		this.profissao = profissao;
	}

	public FichaAvaliacao getFichaAvaliacao() {
		return fichaAvaliacao;
	}

	public void setFichaAvaliacao(FichaAvaliacao fichaAvaliacao) {
		this.fichaAvaliacao = fichaAvaliacao;
	}

	public Plano getPlano() {
		return plano;
	}

	public void setPlano(Plano plano) {
		this.plano = plano;
	}

	public List<Aula> getAulasMarcadas() {
		return aulasMarcadas;
	}

	public void setAulasMarcadas(List<Aula> aulasMarcadas) {
		this.aulasMarcadas = aulasMarcadas;
	}

	public List<Financeiro> getHistoricoPagamento() {
		return historicoPagamento;
	}

	public void setHistoricoPagamento(List<Financeiro> historicoPagamento) {
		this.historicoPagamento = historicoPagamento;
	}

	public List<String> getFotos() {
		return fotos;
	}

	public void setFotos(List<String> fotos) {
		this.fotos = fotos;
	}
	
	
}



	
