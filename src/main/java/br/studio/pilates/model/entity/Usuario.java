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
	private String sexo;
	private String email;
	private String tipo;
	private String DataNasc;
	private String contato;
	private String formacao;
	private List<Conselho> conselho;
	private String foto;
	private String dataContratacao;
	private String cpf;
	private String rg;
	private String cep;
	private String cref;
	private List<Permissoes> permissoes;
	private Estudio estudio;
	

	public Usuario() {
		
	}

	public Usuario(String id, String nome,String sexo, String email, String tipo, String DataNasc, String contato,
			String formacao, List<Conselho> conselho, String foto, String dataContratacao, String cpf,
			String rg, String cep, String cref, List<Permissoes> permissoes, Estudio estudio) {
		super();
		this.id = id;
		this.nome = nome;
		this.sexo = sexo;
		this.email = email;
		this.tipo = tipo;
		this.DataNasc = DataNasc;
		this.contato = contato;
		this.formacao = formacao;
		this.conselho = conselho;
		this.foto = foto;
		this.dataContratacao = dataContratacao;
		this.cpf = cpf;
		this.rg = rg;
		this.cep = cep;
		this.cref = cref;
		this.permissoes = permissoes;
		this.estudio = estudio;
	}

}


