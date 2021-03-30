package BussniesLayer;

public class Item{
    private int supplierBN;
    private String category;
    private int itemId;
    private QuantityDocument quantityDocument;
    private double price;

    public Item(int supplierBN , String category , int itemId , double price){
        this.supplierBN = supplierBN;
        this.category = category;
        this.itemId = itemId;
        quantityDocument = null;
        this.price = price;
    }

    public void addQuantityDocument(int supplierBN, int itemId, int minimalAmount, int discount) {
        quantityDocument = new QuantityDocument(supplierBN, itemId, minimalAmount, discount);
    }

    public void removeQuantityDocument() {
        quantityDocument = null;
    }

    public QuantityDocument showQuantityDocument(){ return quantityDocument; }

    public void updateMinimalAmountOfQD(int minimalAmount) { quantityDocument.updateMinimalAmountOfQD(minimalAmount); }

    public void updateDiscountOfQD(int discount) {
        quantityDocument.updateDiscountOfQD(discount);
    }

    public int getItemId() {
        return itemId;
    }

    public QuantityDocument getQuantityDocument(){
        return quantityDocument;
    }

    public double getPrice() {
        return price;
    }

    public void updatePrice(double price) { this.price = price;}

    public String getCategory() {
        return category;
    }
}
