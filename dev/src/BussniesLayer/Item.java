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

    public void removeQuantityDocument(int supplierBN, int itemId) {
    }

    public void showQuantityDocument(int supplierBN, int itemId) {
    }

    public void updateMinimalAmountOfQD(int supplierBN, int itemId, int minimalAmount) {
    }

    public void updateDiscountOfQD(int supplierBN, int itemId, int discount) {
    }

}
