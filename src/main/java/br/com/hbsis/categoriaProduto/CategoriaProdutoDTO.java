package br.com.hbsis.categoriaProduto;

public class CategoriaProdutoDTO {
    private Long id;
    private String nome_categoria;
    private Long fornecedor_categoria;

    public CategoriaProdutoDTO(){

    }

    public CategoriaProdutoDTO(Long id, String nome_categoria, Long fornecedor_categoria){
        this.id = id;
        this.nome_categoria = nome_categoria;
        this.fornecedor_categoria = fornecedor_categoria;
    }

    public static CategoriaProdutoDTO of(Categoria categoria){
        return new CategoriaProdutoDTO(
                categoria.getId(),
                categoria.getNome_categoria(),
                categoria.getFornecedor_categoria()
        );
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

    public Long getFornecedor_categoria() {
        return fornecedor_categoria;
    }

    public void setFornecedor_categoria(Long fornecedor_categoria) {
        this.fornecedor_categoria = fornecedor_categoria;
    }

    @Override
    public String toString(){
        return "Categoria{" +
                "id=" + id +
                ", nome_categoria'" + nome_categoria + '\'' +
                ", fornecedor_categoria" + fornecedor_categoria + '\'' +
                '}';
    }

}
