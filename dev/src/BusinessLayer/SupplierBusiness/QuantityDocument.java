package BusinessLayer.SupplierBusiness;

import DAL.DalSuppliers.DalQuantityDocument;

public class QuantityDocument {
    DalQuantityDocument dalQuantityDocument;

    public QuantityDocument(int minimalAmount , int discount){
        dalQuantityDocument = new DalQuantityDocument();
    }

    public void updateMinimalAmountOfQD(int minimalAmount) throws Exception {
        if(minimalAmount < 0) throw new Exception("minimal amount must be a positive number");
        this.minimalAmount = minimalAmount;
    }

    public void updateDiscountOfQD(int discount) throws Exception {
        if(discount < 0) throw new Exception("discount amount must be a positive number");
        this.discount = discount;
    }

    public int getMinimalAmount(){
        return minimalAmount;
    }

    public int getDiscount(){
        return discount;
    }
}
