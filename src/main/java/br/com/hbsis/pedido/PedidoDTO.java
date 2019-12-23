package br.com.hbsis.pedido;

import br.com.hbsis.item.ItemDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PedidoDTO {

    private Long id;
    private String codigo;
    private String status;
    private Long idFornecedor;
    private LocalDate dataCriacao;
    private List<ItemDTO> itemDTOList;
    private int totalValue;

    public PedidoDTO() {
    }

    public PedidoDTO(Long id, String codigo, String status, Long idFornecedor, LocalDate dataCriacao, List<ItemDTO> itemDTOList, int totalValue) {
        this.id = id;
        this.codigo = codigo;
        this.status = status;
        this.idFornecedor = idFornecedor;
        this.dataCriacao = dataCriacao;
        this.itemDTOList = itemDTOList;
        this.totalValue = totalValue;
    }

    public static PedidoDTO of(Pedido pedido){
        return new PedidoDTO(
                pedido.getId(),
                pedido.getCodigo(),
                pedido.getStatus(),
                pedido.getFornecedor().getId(),
                pedido.getDataCriacao(),
                pedido.getItemList(),
                pedido.getTotalValue()
        );
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

    public Long getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(Long idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public List<ItemDTO> getItemDTOList() {
        return itemDTOList;
    }

    public void setItemDTOList(List<ItemDTO> itemDTOList) {
        this.itemDTOList = itemDTOList;
    }

    public int getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(int totalValue) {
        this.totalValue = totalValue;
    }

    @Override
    public String toString() {
        return "PedidoDTO{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", status='" + status + '\'' +
                ", idFornecedor=" + idFornecedor +
                ", dataCriacao=" + dataCriacao +
                ", itemDTOList=" + itemDTOList +
                ", totalValue=" + totalValue +
                '}';
    }
}

