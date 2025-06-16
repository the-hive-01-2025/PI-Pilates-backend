package br.studio.pilates.model.entity;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Document(collection = "Alunos")
// @EntityScan -> REMOVIDO, pois não é usado em entidade/model
public class Aluno implements UserDetails {

    @Id
    private String id;

    private String nome;
    private String sexo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate data;
    private String cpf;
    private String email;
    private String senha;
    private String contato;
    private String endereco;
    private String cep;
    private String profissao;
    private FichaAvaliacao fichaAvaliacao;
    private List<Aula> aulasMarcadas;
    private List<String> fotos;
    @DBRef
	private Plano plano;
	@Field("historico_pagamento")
	private List<Financeiro> historicoPagamento;

    public Aluno() {
    }
	

    public Aluno(String id, String nome, String sexo, LocalDate data, String cpf, String email, String senha,
            String contato, String endereco, String cep, String profissao, FichaAvaliacao fichaAvaliacao, Plano plano,
            List<Aula> aulasMarcadas, List<Financeiro> historicoPagamento, List<String> fotos) {
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
        this.fichaAvaliacao = fichaAvaliacao;
        this.plano = plano;
        this.aulasMarcadas = aulasMarcadas;
        this.historicoPagamento = historicoPagamento;
        this.fotos = fotos;
    }

    // Getters e Setters

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

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
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

    // Implementação dos métodos UserDetails

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "ROLE_ALUNO"); // role fixa só para aluno
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // pode personalizar depois
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // pode personalizar depois
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // pode personalizar depois
    }

    @Override
    public boolean isEnabled() {
        return true; // pode personalizar depois
    }

}
