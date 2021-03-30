package BussniesLayer.facade.outObjects;

public class SupplierAgreement {
    private int minimalAmount;
    private int discount;
    private boolean constantTime;
    private boolean shipToUs;


    public SupplierAgreement(BussniesLayer.SupplierAgreement SA) {
        minimalAmount = SA.getMinimalAmount();
        discount = SA.getDiscount();
        constantTime = SA.getConstantTime();
        shipToUs = SA.getShipToUs();
    }
}
