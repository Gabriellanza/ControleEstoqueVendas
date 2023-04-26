package br.edu.toledoprudente.pojo;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;


@Entity
@Table(name="categorias")
public class Categorias extends AbstractEntity<Integer>
{
	@NotBlank(message = "O nome da Categoria é obrigatório.")
	@Size(min = 3, max = 150, message = "O nome da Categoria deve conter de 3 a 150 caracteres.")
	@Column(length = 150, nullable = false)
		private String descricao;


		@OneToMany(mappedBy = "categorias")
		List<Produtos> produtos;


	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Produtos> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produtos> produtos) {
		this.produtos = produtos;
	}
}
