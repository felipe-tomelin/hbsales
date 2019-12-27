package br.com.hbsis.item;

public class ItemDTO {

    private Long id;
    private Long id_produto;
    private Long quantidadeProduto;

    public ItemDTO(){
    }

    public ItemDTO(Long id, Long idProduto, Long quantidadeProduto) {
        this.id = id;
        this.id_produto = idProduto;
        this.quantidadeProduto = quantidadeProduto;
    }

    public static ItemDTO of(Item item){
        return new ItemDTO(
                item.getId(),
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
        return id_produto;
    }

    public void setIdProduto(Long idProduto) {
        this.id_produto = idProduto;
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
                ", id_produto=" + id_produto +
                ", quantidadeProduto=" + quantidadeProduto +
                '}';
    }
}