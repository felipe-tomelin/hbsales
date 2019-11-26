package br.com.hbsis.fornecedor;

import javax.persistence.*;

/**
 * Classe responsável pelo mapeamento da entidade do banco de dados
 */

@Entity
@Table(name = "seg_fornecedores")
class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "razao_social", unique = true, nullable = false, length = 100)
    private String RazaoSocial;
    @Column(name = "cnpj", unique = true, nullable = false, length = 18)
    private String CNPJ;
    @Column(name = "nome_fantasia", unique = true, nullable = false, length = 70)
    private String NomeFantasia;
    @Column(name = "endereco", unique = true, nullable = false, length = 70)
    private String Endereco;
    @Column(name = "telefone", unique = true, nullable = false, length = 9)
    private int Telefone;
    @Column(name = "email", unique = true, nullable = false, length = 70)
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

    public String getCNPJ() {
        return CNPJ;
    }

    public void setCNPJ(String CNPJ) {
        this.CNPJ = CNPJ;
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

    public int getTelefone() {
        return Telefone;
    }

    public void setTelefone(int telefone) {
        Telefone = telefone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    @Override
    public String toString() {
        return "Fornecedor{" +
        "Id=" + id +
                ", RazaoSocial='" + RazaoSocial + '\'' +
                ", CNPJ='" + CNPJ + '\'' +
                ", Nome Fantasia='" + NomeFantasia + '\'' +
                ", Endereço='" + Endereco + '\'' +
                ", Telefone='" + Telefone + '\'' +
                ", Email='" + Email + '\'' +
                '}';
    }

}
