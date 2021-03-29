package BussniesLayer;

public class QuantityDocument {
    private int supplierBN;
    private int itemId;
    private int minimalAmount;
    private int discount;

    public QuantityDocument(int supplierBN , int itemId , int minimalAmount , int discount){
        this.supplierBN = supplierBN;
        this.itemId = itemId;
        this.minimalAmount = minimalAmount;
        this.discount = discount;
    }

    public void updateMinimalAmountOfQD(int itemId, int minimalAmount) {
    }

    public void updateDiscountOfQD(int itemId, int discount) {
    }
}
