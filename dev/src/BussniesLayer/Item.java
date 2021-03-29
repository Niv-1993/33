package BussniesLayer;

public class Item{
    private int supplierBN;
    private String category;
    private int itemId;
    private QuantityDocument quantityDocument;

    public Item(int supplierBN , String category , int itemId){
        this.supplierBN = supplierBN;
        this.category = category;
        this.itemId = itemId;
        quantityDocument = null;
    }

    public void addQuantityDocument(int supplierBN, int itemId, int minimalAmount, int discount) {
    }

    public void removeQuantityDocument() {
    }

    public void showQuantityDocument(int itemId) {
    }

    public void updateMinimalAmountOfQD(int minimalAmount) {
    }

    public void updateDiscountOfQD(int discount) {
    }

    public int getItemId() {
    }
}
