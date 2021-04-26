package BusinessLayer.SupplierBusiness;

import DalAccessLayer.DalItem;

import java.time.LocalDate;

public class Item{
    private final int itemId;
    private final String name;
    private QuantityDocument quantityDocument;
    private double price;
    private final int typeId;
    private final LocalDate expirationDate;
    private DalItem dalItem;

    public Item(int itemId , String name , double price, int typeId, LocalDate expirationDate){
        this.itemId = itemId;
        this.name = name;
        quantityDocument = null;
        this.price = price;
        this.typeId = typeId;
        this.expirationDate = expirationDate;
        //dalItem = new DalItem();
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

    public int getItemId() { return dalItem.GetId(); }

    public QuantityDocument getQuantityDocument(){
        return quantityDocument;
    }

    public double getPrice() { return dalItem.GetPrice(); }

    public void updatePrice(double price) throws Exception {
        if(price < 0) throw new Exception("price must be a positive number");
        dalItem.Save(price);
    }

    public String getName() { return dalItem.GetName(); }

    public int getTypeID() { return dalItem.GetTypeID(); }

    public LocalDate getExpirationDate() { return expirationDate; }

}
