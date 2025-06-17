package br.studio.pilates.model.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Representa um usuário do sistema Pilates.
 * Pode ser um instrutor, recepcionista ou administrador.
 * Implementa {@link UserDetails} para integração com Spring Security.
 */
@Document(collection = "Usuario")
public class Usuario implements UserDetails {

    @Id
    private String id;

    private String nome;
    private String sexo;
    private String email;
    private String senha;
    private String dataNasc;
    private String contato;
    private String formacao;
    private List<Conselho> conselho;
    private String foto;
    private String dataContratacao;
    private String cpf;
    private String rg;
    private String cep;
    private String cref;
    private String role; // ADMIN, INSTRUTOR, RECEPCAO
    private Estudio estudio;

    /** Construtor padrão. */
    public Usuario() {}

    /**
     * Construtor completo da entidade Usuario.
     * 
     * @param id identificador único
     * @param nome nome completo do usuário
     * @param sexo sexo do usuário
     * @param email email do usuário (login)
     * @param senha senha criptografada
     * @param dataNasc data de nascimento
     * @param contato telefone ou outro contato
     * @param formacao formação profissional
     * @param conselho lista de conselhos profissionais
     * @param foto URL ou caminho da foto do usuário
     * @param dataContratacao data de contratação
     * @param cpf CPF do usuário
     * @param rg RG do usuário
     * @param cep CEP do endereço
     * @param cref registro profissional de educação física
     * @param role papel do usuário no sistema (ex: ADMIN, INSTRUTOR, RECEPCAO)
     * @param estudio estúdio associado ao usuário
     */
    public Usuario(String id, String nome, String sexo, String email, String senha, String dataNasc,
                   String contato, String formacao, List<Conselho> conselho, String foto, String dataContratacao,
                   String cpf, String rg, String cep, String cref, String role, Estudio estudio) {
        this.id = id;
        this.nome = nome;
        this.sexo = sexo;
        this.email = email;
        this.senha = senha;
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
        this.role = role;
        this.estudio = estudio;
    }

    /**
     * Retorna as autoridades (roles) do usuário para o Spring Security.
     * Caso o campo role seja nulo, retorna ROLE_USER por padrão.
     * 
     * @return coleção de autoridades concedidas
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == null) {
            return List.of(() -> "ROLE_USER");
        }
        return List.of(() -> "ROLE_" + this.role.toUpperCase());
    }

    /** Retorna a senha do usuário (criptografada). */
    @Override
    public String getPassword() { 
        return senha; 
    }

    /** Retorna o nome de usuário (email). */
    @Override
    public String getUsername() { 
        return email; 
    }

    /** Indica se a conta não está expirada. Sempre true para este modelo. */
    @Override
    public boolean isAccountNonExpired() { 
        return true; 
    }

    /** Indica se a conta não está bloqueada. Sempre true para este modelo. */
    @Override
    public boolean isAccountNonLocked() { 
        return true; 
    }

    /** Indica se as credenciais não estão expiradas. Sempre true para este modelo. */
    @Override
    public boolean isCredentialsNonExpired() { 
        return true; 
    }

    /** Indica se o usuário está habilitado. Sempre true para este modelo. */
    @Override
    public boolean isEnabled() { 
        return true; 
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

    public String getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getFormacao() {
        return formacao;
    }

    public void setFormacao(String formacao) {
        this.formacao = formacao;
    }

    public List<Conselho> getConselho() {
        return conselho;
    }

    public void setConselho(List<Conselho> conselho) {
        this.conselho = conselho;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getDataContratacao() {
        return dataContratacao;
    }

    public void setDataContratacao(String dataContratacao) {
        this.dataContratacao = dataContratacao;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCref() {
        return cref;
    }

    public void setCref(String cref) {
        this.cref = cref;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Estudio getEstudio() {
        return estudio;
    }

    public void setEstudio(Estudio estudio) {
        this.estudio = estudio;
    }
}
