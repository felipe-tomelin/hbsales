package br.com.hbsis.produto;


import br.com.hbsis.linhaCategoria.LinhaCategoria;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "seg_produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "codigo_produto", unique = true, length = 10)
    private String codigoProduto;
    @Column(name = "nome_produto", length = 200)
    private String nome_produto;
    @Column(name = "preco_produto")
    private Double preco_produto;

    @ManyToOne
    @JoinColumn(name = "id_linha", referencedColumnName = "idLinha")
    private LinhaCategoria linhaCategoria;

    @Column(name = "unidade_por_caixa")
    private Long unidade_por_caixa;
    @Column(name = "peso_unidade")
    private Double peso_unidade;
    @Column(name = "unidade_medida")
    private String unidade_medida;
    @Column(name = "validade")
    private LocalDate validade;

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

    public LinhaCategoria getLinhaCategoria() {
        return linhaCategoria;
    }

    public void setLinhaCategoria(LinhaCategoria linhaCategoria) {
        this.linhaCategoria = linhaCategoria;
    }

    public Long getUnidade_por_caixa() {
        return unidade_por_caixa;
    }

    public void setUnidade_por_caixa(Long unidade_por_caixa) {
        this.unidade_por_caixa = unidade_por_caixa;
    }

    public Double  getPeso_unidade() {
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
        return "Produto{" +
                "id=" + id +
                ", codigo_produto='" + codigoProduto + '\'' +
                ", nome_produto='" + nome_produto + '\'' +
                ", preco_produto=" + preco_produto +
                ", linhaCategoria=" + linhaCategoria +
                ", unidade_por_caixa=" + unidade_por_caixa +
                ", peso_unidade=" + peso_unidade +
                ", unidade_medida='" + unidade_medida + '\'' +
                ", validade=" + validade +
                '}';
    }
}
