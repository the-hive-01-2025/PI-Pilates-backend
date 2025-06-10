package br.studio.pilates.model.entity;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

@Document(collection = "Alunos")
@EntityScan

public class Aluno {

	@Id
	private String id;

	private String nome;
	private String sexo;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate data;

	private String cpf;
	private String email;
	private String senha;
	private Long contato;
	private String endereco;
	private String cep;
	private String profissao;
	private FichaAvaliacao fichaAvaliacao;
	private Plano plano;
	private List<Aula> aulasMarcadas;
	private List<Financeiro> historicoPagamento;
	private List<String> fotos;

	public Aluno() {

	}

	public Aluno(String id, String nome, String sexo, LocalDate data, String cpf, String email, String senha, Long contato, String endereco, String cep, String profissao,
			FichaAvaliacao fichaAvaliacao, Plano plano, List<Aula> aulasMarcadas, List<Financeiro> historicoPagamento,
			List<String> fotos) {
		super();
		this.id = id;
		this.nome = nome;
		this.sexo = sexo;
		this.data = data;
		this.cpf = cpf;
		this.email = email;
		this.senha = senha;
		this.contato = contato;
		this.endereco = endereco;
		this.cep = cep;
		this.profissao = profissao;
		// this.fichaAvaliacao = fichaAvaliacao;
		// this.plano = plano;
		// this.aulasMarcadas = aulasMarcadas;
		// this.historicoPagamento = historicoPagamento;
		// this.fotos = fotos;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Long getContato() {
		return contato;
	}

	public void setContato(Long contato) {
		this.contato = contato;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
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
