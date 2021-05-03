package BusinessLayer.SupplierBusiness;

import DAL.DalSuppliers.DalSupplierAgreement;

public class SupplierAgreement {

    private DalSupplierAgreement dalSupplierAgreement;

    public SupplierAgreement(int minimalAmount , int discount , boolean constantTime , boolean shipToUs){
        dalSupplierAgreement = new DalSupplierAgreement();
    }

    public void updateMinimalAmountOfSA(int minimalAmount) throws Exception {
        if(minimalAmount < 0) throw new Exception("minimal amount must be a positive number");
        dalSupplierAgreement.updateMinimalAmountOfSA(minimalAmount);
    }

    public void updateDiscountOfSA(int discount) throws Exception {
        if(discount < 0) throw new Exception("discount amount must be a positive number between 0 to 100");
        dalSupplierAgreement.updateDiscountOfSA(discount);
    }

    public void updateConstantTime(boolean constantTime) throws Exception { dalSupplierAgreement.updateConstantTime(constantTime); }

    public void updateShipToUs(boolean shipToUs) throws Exception {
        dalSupplierAgreement.updateShipToUs(shipToUs);
    }

    public int getMinimalAmount() {
        return dalSupplierAgreement.getMinimalAmount();
    }

    public int getDiscount() {
        return dalSupplierAgreement.getDiscount();
    }

    public boolean getConstantTime() {
        return dalSupplierAgreement.getConstantTime();
    }

    public boolean getShipToUs() {
        return dalSupplierAgreement.getShipToUs();
    }
}
