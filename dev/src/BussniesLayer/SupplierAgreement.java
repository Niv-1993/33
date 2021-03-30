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

    public void updateMinimalAmountOfSA(int minimalAmount) {
        this.minimalAmount = minimalAmount;
    }

    public void updateDiscountOfSA(int discount) {
        this.discount = discount;
    }

    public void updateConstantTime(boolean constantTime) {
        this.constantTime = constantTime;
    }

    public void updateShipToUs(boolean shipToUs) {
        this.shipToUs = shipToUs;
    }

    public int getMinimalAmount() {
        return minimalAmount;
    }

    public int getDiscount() {
        return discount;
    }

    public boolean getConstantTime() {
        return constantTime;
    }

    public boolean getShipToUs() {
        return shipToUs;
    }
}
