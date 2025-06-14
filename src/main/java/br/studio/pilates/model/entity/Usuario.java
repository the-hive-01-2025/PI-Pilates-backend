package br.studio.pilates.model.entity;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "Usuario")
public class Usuario implements UserDetails {
	
	@Id
	private String id;

	private String nome;
	private String sexo;
	private String email;
	private String senha;          // nova senha para login
	private String tipo;
	private String dataNasc;       // camelCase
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

	public Usuario(String id, String nome, String sexo, String email, String senha, String tipo, String dataNasc, 
			String contato, String formacao, List<Conselho> conselho, String foto, String dataContratacao, 
			String cpf, String rg, String cep, String cref, List<Permissoes> permissoes, Estudio estudio) {
		this.id = id;
		this.nome = nome;
		this.sexo = sexo;
		this.email = email;
		this.senha = senha;
		this.tipo = tipo;
		this.dataNasc = dataNasc;
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

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		switch (this.tipo.toUpperCase()) {
			case "INSTRUTOR":
				return List.of(() -> "ROLE_INSTRUTOR");
			case "RECEPCAO":
				return List.of(() -> "ROLE_RECEPCAO");
			case "ADMIN":
				return List.of(() -> "ROLE_ADMIN");
			default:
				return List.of(() -> "ROLE_USER");
		}
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
        return true;  // pode customizar conforme desejar
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
