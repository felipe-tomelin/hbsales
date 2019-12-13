package br.com.hbsis.categoriaProduto;


public class CategoriaProdutoDTO {
    private Long id;
    private String nome_categoria;
    private Long id_fornecedor;
    private String codigoCategoria;

    public CategoriaProdutoDTO(){
    }

    public CategoriaProdutoDTO(Long id, String nome_categoria, Long id_fornecedor, String codigoCategoria){
        this.id = id;
        this.nome_categoria = nome_categoria;
        this.id_fornecedor = id_fornecedor;
        this.codigoCategoria = codigoCategoria;
    }


    public static CategoriaProdutoDTO of(Categoria categoria){
        return new CategoriaProdutoDTO(
                categoria.getId(),
                categoria.getNome_categoria(),
                categoria.getFornecedor().getId(),
                categoria.getCodigoCategoria()
        );
    }


    public String getCodigoCategoria() {
        return codigoCategoria;
    }

    public void setCodigoCategoria(String codigoCategoria) {
        this.codigoCategoria = codigoCategoria;
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
                ", codigo'" + codigoCategoria + '\'' +
                ", ID fornecedor'" + id_fornecedor + '\'' +
                '}';
    }

}
