package br.com.hbsis.invoiceItem;

public class InvoiceItemDTO {

    private int amount;
    private String itemName;

    public InvoiceItemDTO(int amount, String itemName) {
        this.amount = amount;
        this.itemName = itemName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
