package br.studio.pilates.model.entity;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "Usuario")
public class Usuario {
	
	private String id;

	private String nome;
	private String email;
	private String tipo;
	private String DataNasc;
	private Long contato;
	private String formacao;
	private List<Conselho> conselho;
	private String foto;
	private String dataContratacao;
	private Long cpf;
	private List<Permissoes> permissoes;
	

	public Usuario() {
		
	}

	public Usuario(String id, String nome, String email, String tipo, String DataNasc, Long contato,
			String formacao, List<Conselho> conselho, String foto, String dataContratacao, Long cpf,
			List<Permissoes> permissoes) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.tipo = tipo;
		this.DataNasc = DataNasc;
		this.contato = contato;
		this.formacao = formacao;
		this.conselho = conselho;
		this.foto = foto;
		this.dataContratacao = dataContratacao;
		this.cpf = cpf;
		this.permissoes = permissoes;
	}

}


