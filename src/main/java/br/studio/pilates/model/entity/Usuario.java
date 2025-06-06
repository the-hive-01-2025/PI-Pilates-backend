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
	private Long contato;
	private String formacao;
	private List<Conselho> conselho;
	private String foto;
	private String dataContratacao;
	private Long cpf;
	private Long rg;
	private Long cep;
	private List<Permissoes> permissoes;
	private List<Estudio> estudio;
	

	public Usuario() {
		
	}

	public Usuario(String id, String nome,String sexo, String email, String tipo, String DataNasc, Long contato,
			String formacao, List<Conselho> conselho, String foto, String dataContratacao, Long cpf,
			Long rg, Long cep, List<Permissoes> permissoes, List<Estudio> estudio) {
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
		this.permissoes = permissoes;
		this.estudio = estudio;
	}

}


