package BussniesLayer;

public class SupplierAgreement {
    private int minimalAmount;
    private int discount;
    private boolean constantTime;
    private boolean shipToUs;

    public SupplierAgreement(int minimalAmount , int discount , boolean constantTime , boolean shipToUs){
        this.minimalAmount = minimalAmount;
        this.discount = discount;
        this.constantTime = constantTime;
        this.shipToUs = shipToUs;
    }

    public void updateMinimalAmountOfSA(int minimalAmount) throws Exception {
        if(minimalAmount < 0) throw new Exception("minimal amount must be a positive number");
        this.minimalAmount = minimalAmount;
    }

    public void updateDiscountOfSA(int discount) throws Exception {
        if(discount < 0) throw new Exception("discount amount must be a positive number between 0 to 100");
        this.discount = discount;
    }

    public void updateConstantTime(boolean constantTime) { this.constantTime = constantTime; }

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
