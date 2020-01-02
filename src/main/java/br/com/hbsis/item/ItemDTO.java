package br.com.hbsis.item;

public class ItemDTO {

    private Long id;
    private Long idProduto;
    private Long idPedido;
    private Long quantidadeProduto;

    public ItemDTO(){
    }

    public ItemDTO(Long id, Long idProduto, Long idPedido, Long quantidadeProduto) {
        this.id = id;
        this.idProduto = idProduto;
        this.idPedido = idPedido;
        this.quantidadeProduto = quantidadeProduto;
    }

    public static ItemDTO of(Item item){
        return new ItemDTO(
                item.getId(),
                item.getProduto().getId(),
                item.getProduto().getId(),
                item.getQuantidadeProduto()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public Long getQuantidadeProduto() {
        return quantidadeProduto;
    }

    public void setQuantidadeProduto(Long quantidadeProduto) {
        this.quantidadeProduto = quantidadeProduto;
    }

    @Override
    public String toString() {
        return "ItemDTO{" +
                "id=" + id +
                ", idProduto=" + idProduto +
                ", idPedido=" + idPedido +
                ", quantidadeProduto=" + quantidadeProduto +
                '}';
    }
}