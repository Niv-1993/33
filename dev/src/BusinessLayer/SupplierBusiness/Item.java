package BusinessLayer.SupplierBusiness;

import DAL.DalSuppliers.DalItem;

import java.time.LocalDate;

public class Item{
    private QuantityDocument quantityDocument;
    private DalItem dalItem;

    public Item(int itemId , String name , double price, int typeId, LocalDate expirationDate){
        quantityDocument = null;
        dalItem = new DalItem();
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
        return dalItem.getItemId();
    }

    public QuantityDocument getQuantityDocument(){
        return quantityDocument;
    }

    public double getPrice() {
        return dalItem.getPrice();
    }

    public void updatePrice(double price) throws Exception {
        if(price < 0) throw new Exception("price must be a positive number");
        dalItem.updatePrice(price);
    }

    public String getName() { return dalItem.getName(); }

    public int getTypeID() { return dalItem.getTypeID(); }

    public LocalDate getExpirationDate() { return dalItem.getExpirationDate(); }
}
