package br.studio.pilates.model.entity;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Usuario")
public class Usuario {
	
	private String IdUsuario;

	private String nomeUsuario;
	private String emailUsuario;
	private String tipoUsuario;
	private String DataNascUsuario;
	private int contatoUsuario;
	private String formacaoUsuario;
	private List<Conselho> conselho;
	private String fotoUsuario;
	private String dataContratacaoUsuario;
	private int cpfUsuario;
	private List<Permissoes> permissoes;

	// getters and setters

	public String getIdUsuario() {
		return IdUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		IdUsuario = idUsuario;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getEmailUsuario() {
		return emailUsuario;
	}

	public void setEmailUsuario(String emailUsuario) {
		this.emailUsuario = emailUsuario;
	}

	public String getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public String getDataNascUsuario() {
		return DataNascUsuario;
	}

	public void setDataNascUsuario(String dataNascUsuario) {
		DataNascUsuario = dataNascUsuario;
	}

	public int getContatoUsuario() {
		return contatoUsuario;
	}

	public void setContatoUsuario(int contatoUsuario) {
		this.contatoUsuario = contatoUsuario;
	}

	public String getFormacaoUsuario() {
		return formacaoUsuario;
	}

	public void setFormacaoUsuario(String formacaoUsuario) {
		this.formacaoUsuario = formacaoUsuario;
	}

	public List<Conselho> getConselho() {
		return conselho;
	}

	public void setConselho(List<Conselho> conselho) {
		this.conselho = conselho;
	}

	public String getFotoUsuario() {
		return fotoUsuario;
	}

	public void setFotoUsuario(String fotoUsuario) {
		this.fotoUsuario = fotoUsuario;
	}

	public String getDataContratacaoUsuario() {
		return dataContratacaoUsuario;
	}

	public void setDataContratacaoUsuario(String dataContratacaoUsuario) {
		this.dataContratacaoUsuario = dataContratacaoUsuario;
	}

	public int getCpfUsuario() {
		return cpfUsuario;
	}

	public void setCpfUsuario(int cpfUsuario) {
		this.cpfUsuario = cpfUsuario;
	}

	public List<Permissoes> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(List<Permissoes> permissoes) {
		this.permissoes = permissoes;
	}
}
