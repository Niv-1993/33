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

    public void addItem(int supplierBN, String category) {
    }

    public void removeItem(int itemId) {
    }
}
