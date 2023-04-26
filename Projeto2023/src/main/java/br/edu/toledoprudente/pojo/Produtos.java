package br.edu.toledoprudente.pojo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "produtos")
public class Produtos extends AbstractEntity<Integer> {

    @NotBlank(message = "O nome do Produto é obrigatório.")
    @Size(min = 3, max = 150, message = "O nome do Produto deve conter de 3 a 150 caracteres.")
    @Column(length = 150, nullable = false)
    private String descricao;

    @NotNull(message = "Informe um Valor !")
    @PositiveOrZero(message = "Valor deve ser maior ou igual a 0")
    @Column(name="valor", nullable = false, columnDefinition = "DECIMAL(10,2) DEFAULT 0.00")
    @NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
    private BigDecimal valor;

    @NotNull(message = "Informe uma Data !")
    @Column(name="data_vencimento", nullable = false, columnDefinition = "DATE")
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate data_vencimento;

    @NotNull(message = "Informe a quantidade !")
    @Column(name="quantidade", nullable = false, columnDefinition = "INT")
    private Integer quantidade;

    @Column(name="imagem")
    private String imagem;

    @NotNull(message = "Informe uma Categoria !")
    @ManyToOne
    @JoinColumn(name="idCategoria")
    private Categorias categorias;

    @NotNull(message = "Informe um Fornecedor !")
    @ManyToOne
    @JoinColumn(name="idFornecedor")
    private Fornecedores fornecedores;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getData_vencimento() {
        return data_vencimento;
    }

    public void setData_vencimento(LocalDate data_vencimento) {
        this.data_vencimento = data_vencimento;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public Categorias getCategorias() {
        return categorias;
    }

    public void setCategorias(Categorias categorias) {
        this.categorias = categorias;
    }

    public Fornecedores getFornecedores() {
        return fornecedores;
    }

    public void setFornecedores(Fornecedores fornecedores) {
        this.fornecedores = fornecedores;
    }
}
