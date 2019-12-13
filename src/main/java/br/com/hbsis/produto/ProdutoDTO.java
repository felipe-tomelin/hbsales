package br.com.hbsis.produto;

import java.time.LocalDate;

public class ProdutoDTO {
    private Long id;
    private String codigoProduto;
    private String nome_produto;
    private Double preco_produto;
    private Long id_linha;
    private Long unidade_por_caixa;
    private Double peso_unidade;
    private String unidade_medida;
    private LocalDate validade;

    public ProdutoDTO() {
    }

    public ProdutoDTO(Long id, String codigoProduto, String nome_produto, Double preco_produto, Long id_linha, Long unidade_por_caixa, Double peso_unidade, String unidade_medida , LocalDate validade) {
        this.id = id;
        this.codigoProduto = codigoProduto;
        this.nome_produto = nome_produto;
        this.preco_produto = preco_produto;
        this.id_linha = id_linha;
        this.unidade_por_caixa = unidade_por_caixa;
        this.peso_unidade = peso_unidade;
        this.unidade_medida = unidade_medida;
        this.validade = validade;
    }

    public static ProdutoDTO of(Produto produto){
        return new ProdutoDTO(
                produto.getId(),
                produto.getCodigoProduto(),
                produto.getNome_produto(),
                produto.getPreco_produto(),
                produto.getLinhaCategoria().getId(),
                produto.getUnidade_por_caixa(),
                produto.getPeso_unidade(),
                produto.getUnidade_medida(),
                produto.getValidade()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(String codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public String getNome_produto() {
        return nome_produto;
    }

    public void setNome_produto(String nome_produto) {
        this.nome_produto = nome_produto;
    }

    public Double getPreco_produto() {
        return preco_produto;
    }

    public void setPreco_produto(Double preco_produto) {
        this.preco_produto = preco_produto;
    }

    public Long getId_linha() {
        return id_linha;
    }

    public void setId_linha(Long id_linha) {
        this.id_linha = id_linha;
    }

    public Long getUnidade_por_caixa() {
        return unidade_por_caixa;
    }

    public void setUnidade_por_caixa(Long unidade_por_caixa) {
        this.unidade_por_caixa = unidade_por_caixa;
    }

    public Double getPeso_unidade() {
        return peso_unidade;
    }

    public void setPeso_unidade(Double peso_unidade) {
        this.peso_unidade = peso_unidade;
    }

    public LocalDate getValidade() {
        return validade;
    }

    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }

    public String getUnidade_medida() {
        return unidade_medida;
    }

    public void setUnidade_medida(String unidade_medida) {
        this.unidade_medida = unidade_medida;
    }

    @Override
    public String toString() {
        return "ProdutoDTO{" +
                "id=" + id +
                ", codigo_produto='" + codigoProduto + '\'' +
                ", nome_produto='" + nome_produto + '\'' +
                ", preco_produto=" + preco_produto +
                ", id_linha=" + id_linha +
                ", unidade_por_caixa=" + unidade_por_caixa +
                ", peso_unidade=" + peso_unidade +
                ", unidade_medida='" + unidade_medida + '\'' +
                ", validade=" + validade +
                '}';
    }
}
