package br.com.hbsis.fornecedor;

/**
 * Classe para tráfego das informações do Fornecedor
 */

public class FornecedorDTO{
    private Long id;
    private String razaoSocial;
    private String cnpj;
    private String nomeFantasia;
    private String endereco;
    private String telefone;
    private String email;

    public FornecedorDTO() {
    }

    public FornecedorDTO(Long id, String RazaoSocial, String cnpj, String NomeFantasia, String Endereco, String Telefone, String Email){
        this.id = id;
        this.razaoSocial = RazaoSocial;
        this.cnpj = cnpj;
        this.nomeFantasia = NomeFantasia;
        this.endereco = Endereco;
        this.telefone = Telefone;
        this.email = Email;
    }

    public static FornecedorDTO of(Fornecedor fornecedor){
        return new FornecedorDTO(
                fornecedor.getId(),
                fornecedor.getRazaoSocial(),
                fornecedor.getCnpj(),
                fornecedor.getNomeFantasia(),
                fornecedor.getEndereco(),
                fornecedor.getTelefone(),
                fornecedor.getEmail()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Fornecedor{" +
                "Id=" + id +
                ", RazaoSocial='" + razaoSocial + '\'' +
                ", CNPJ='" + cnpj + '\'' +
                ", Nome Fantasia='" + nomeFantasia + '\'' +
                ", Endereço='" + endereco + '\'' +
                ", Telefone='" + telefone + '\'' +
                ", Email='" + email + '\'' +
                '}';
    }

}
