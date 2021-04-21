package BussniesLayer;

import java.time.LocalDate;

public class Item{
    private final int itemId;
    private final String name;
    private QuantityDocument quantityDocument;
    private double price;
    private int typeId;
    private LocalDate expirationDate;

    public Item(int itemId , String name , double price, int typeId, LocalDate expirationDate){
        this.itemId = itemId;
        this.name = name;
        quantityDocument = null;
        this.price = price;
        this.typeId = typeId;
        this.expirationDate = expirationDate;
    }

    public void addQuantityDocument(int minimalAmount, int discount) throws Exception {
        if(minimalAmount < 0) throw new Exception("minimal amount must be a positive number");
        if(discount < 0 || discount > 100) throw new Exception("discount must be a positive number between 0 to 100");
        quantityDocument = new QuantityDocument(minimalAmount, discount);
    }

    public void removeQuantityDocument() throws Exception {
        if(quantityDocument == null) throw new Exception("quantity document all ready removed");
        quantityDocument = null;
    }

    public QuantityDocument showQuantityDocument() throws Exception {
        if(quantityDocument == null) throw new Exception("quantity document all ready removed");
        return quantityDocument;
    }

    public void updateMinimalAmountOfQD(int minimalAmount) throws Exception {
        try {
            quantityDocument.updateMinimalAmountOfQD(minimalAmount);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public void updateDiscountOfQD(int discount) throws Exception {
        try {
            quantityDocument.updateDiscountOfQD(discount);
        } catch (Exception e) {
            throw new Exception(e);
        }
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

    public void updatePrice(double price) throws Exception {
        if(price < 0) throw new Exception("price must be a positive number");
        this.price = price;
    }

    public String getName() { return name; }

    public int getTypeID() { return typeId; }

    public LocalDate getExpirationDate() { return expirationDate; }
}
