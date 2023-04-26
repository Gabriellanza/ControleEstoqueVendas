package br.edu.toledoprudente.pojo;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "clientes")
public class Clientes extends AbstractEntity<Integer>{

	@Column(length = 150, nullable = false)
	private String nome;

	@Column(name="data_nascimento", nullable = false, columnDefinition = "DATE")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate data_nascimento;

	@Column(name="rua", nullable = false)
	private String rua;

	@Column(name="numero", nullable = false, columnDefinition = "INT")
	private Integer numero;

	@Column(name="bairro", nullable = false)
	private String bairro;

	@Column(name="cidade", nullable = false)
	private String cidade;

	@Column(name="telefone", nullable = false)
	private String telefone;

	@Column(name="cpf", nullable = false)
	private String cpf;


 	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="idUsuario")
	private Users usuarios;

	public Users getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Users usuarios) {
		this.usuarios = usuarios;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getData_nascimento() {
		return data_nascimento;
	}

	public void setData_nascimento(LocalDate data_nascimento) {
		this.data_nascimento = data_nascimento;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
}
