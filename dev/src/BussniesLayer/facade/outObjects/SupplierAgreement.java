package BussniesLayer.facade.outObjects;

public class SupplierAgreement {
    private final int minimalAmount;
    private final int discount;
    private final boolean constantTime;
    private final boolean shipToUs;


    public SupplierAgreement(BussniesLayer.SupplierAgreement SA) {
        minimalAmount = SA.getMinimalAmount();
        discount = SA.getDiscount();
        constantTime = SA.getConstantTime();
        shipToUs = SA.getShipToUs();
    }

    @Override
    public String toString() {
        return "SupplierAgreement: \n" +
                "\tminimalAmount = " + minimalAmount + "\n" +
                "\tdiscount = " + discount + "\n" +
                "\tconstantTime = " + constantTime + " \n" +
                "\tshipToUs = " + shipToUs + "\n" ;
    }
}
