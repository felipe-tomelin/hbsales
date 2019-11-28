package br.com.hbsis.categoriaProduto;

import br.com.hbsis.fornecedor.Fornecedor;

public class CategoriaProdutoDTO {
    private Long id;
    private String nome_categoria;
    private Long id_fornecedor;
    private int codigo;

    public CategoriaProdutoDTO(){
    }

    public CategoriaProdutoDTO(Long id, String nome_categoria, Long id_fornecedor, int codigo){
        this.id = id;
        this.nome_categoria = nome_categoria;
        this.id_fornecedor = id_fornecedor;
        this.codigo = codigo;
    }


    public static CategoriaProdutoDTO of(Categoria categoria){
        return new CategoriaProdutoDTO(
                categoria.getId(),
                categoria.getNome_categoria(),
                categoria.getFornecedor().getId(),
                categoria.getCodigo()
        );
    }

    public Long getId_fornecedor() {
        return id_fornecedor;
    }

    public void setId_fornecedor(Long id_fornecedor) {
        this.id_fornecedor = id_fornecedor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome_categoria() {
        return nome_categoria;
    }

    public void setNome_categoria(String nome_categoria) {
        this.nome_categoria = nome_categoria;
    }

    @Override
    public String toString(){
        return "Categoria{" +
                "id=" + id +
                ", nome_categoria'" + nome_categoria + '\'' +
                ", ID fornecedor'" + id_fornecedor + '\'' +
                '}';
    }

}
