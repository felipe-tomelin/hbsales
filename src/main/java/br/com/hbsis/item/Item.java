package br.com.hbsis.item;

import br.com.hbsis.produto.Produto;

import javax.persistence.*;

@Entity
@Table(name = "seg_item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_produto", referencedColumnName = "id")
    private Produto produto;

    @Column(name = "quantidade_produto")
    private Long quantidadeProduto;

    public Item(Long id, Produto produto, Long quantidadeProduto) {
        this.id = id;
        this.produto = produto;
        this.quantidadeProduto = quantidadeProduto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Long getQuantidadeProduto() {
        return quantidadeProduto;
    }

    public void setQuantidadeProduto(Long quantidadeProduto) {
        this.quantidadeProduto = quantidadeProduto;
    }
}
