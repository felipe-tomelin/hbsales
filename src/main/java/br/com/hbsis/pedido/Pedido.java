package br.com.hbsis.pedido;

import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.item.Item;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "seg_pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "codigo", length = 10, unique = true)
    private String codigo;
    @Column(name = "status")
    private String status;

    @ManyToMany
    @JoinTable(name = "pedido_item", joinColumns = @JoinColumn(name = "id_pedido", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "id_item", referencedColumnName = "id"))
    private List<Item> itemList;

    @ManyToOne
    @JoinColumn(name = "id_fornecedor", referencedColumnName = "id")
    private Fornecedor fornecedor;
    @Column(name = "data_criacao")
    private LocalDate dataCriacao;
    @Column(name = "total_value")
    private int totalValue;

    public Pedido() {
    }

    public Pedido(Long id, String codigo, String status, List<Item> itemList, Fornecedor fornecedor, LocalDate dataCriacao, int totalValue) {
        this.id = id;
        this.codigo = codigo;
        this.status = status;
        this.itemList = itemList;
        this.fornecedor = fornecedor;
        this.dataCriacao = dataCriacao;
        this.totalValue = totalValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public int getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(int totalValue) {
        this.totalValue = totalValue;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", status='" + status + '\'' +
                ", itemList=" + itemList +
                ", fornecedor=" + fornecedor +
                ", dataCriacao=" + dataCriacao +
                ", totalValue=" + totalValue +
                '}';
    }
}
