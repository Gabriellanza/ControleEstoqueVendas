package br.edu.toledoprudente.pojo;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "funcionarios")
public class Funcionarios extends AbstractEntity<Integer>
{

    @Column(name="salario", nullable = true, columnDefinition = "DECIMAL(10,2) DEFAULT 0.00")
    private BigDecimal salario;

    @Column(name="cargo", nullable = false)
    private String cargo;

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

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
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
