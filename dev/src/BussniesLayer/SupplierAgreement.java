package BussniesLayer;

public class SupplierAgreement {
    private int supplierBN;
    private int minimalAmount;
    private int discount;
    private boolean constantTime;
    private boolean shipToUs;

    public SupplierAgreement(int supplierBN , int minimalAmount , int discount , boolean constantTime , boolean shipToUs){
        this.supplierBN = supplierBN;
        this.minimalAmount = minimalAmount;
        this.discount = discount;
        this.constantTime = constantTime;
        this.shipToUs = shipToUs;
    }

    public void updateMinimalAmountOfSA(int supplierBN, int minimalAmount) {
    }

    public void updateDiscountOfSA(int supplierBN, int discount) {
    }

    public void updateConstantTime(int supplierBN, boolean constantTime) {
    }

    public void updateShipToUs(int supplierBN, boolean shipToUs) {
    }
}
