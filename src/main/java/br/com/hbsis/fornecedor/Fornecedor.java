package br.com.hbsis.fornecedor;

import javax.persistence.*;

/**
 * Classe responsável pelo mapeamento da entidade do banco de dados
 */

@Entity
@Table(name = "seg_fornecedores")
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "razao_social", unique = true, nullable = false, length = 100)
    private String RazaoSocial;
    @Column(name = "cnpj", unique = true, nullable = false, length = 14)
    private String cnpj;
    @Column(name = "nome_fantasia", unique = true, nullable = false, length = 100)
    private String NomeFantasia;
    @Column(name = "endereco", unique = true, nullable = false, length = 100)
    private String Endereco;
    @Column(name = "telefone", unique = true, nullable = false, length = 14)
    private String Telefone;
    @Column(name = "email", unique = true, nullable = false, length = 50)
    private String Email;

    public Long getId() {
        return id;
    }

    public String getRazaoSocial() {
        return RazaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        RazaoSocial = razaoSocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNomeFantasia() {
        return NomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        NomeFantasia = nomeFantasia;
    }

    public String getEndereco() {
        return Endereco;
    }

    public void setEndereco(String endereco) {
        Endereco = endereco;
    }

    public String getTelefone() {
        return Telefone;
    }

    public void setTelefone(String telefone) {
        Telefone = telefone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    //
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Fornecedor{" +
        "Id=" + id +
                ", RazaoSocial='" + RazaoSocial + '\'' +
                ", CNPJ='" + cnpj + '\'' +
                ", Nome Fantasia='" + NomeFantasia + '\'' +
                ", Endereço='" + Endereco + '\'' +
                ", Telefone='" + Telefone + '\'' +
                ", Email='" + Email + '\'' +
                '}';
    }

}
