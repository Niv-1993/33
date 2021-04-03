package BussniesLayer.facade.outObjects;

public class QuantityDocument{
    private final int minimalAmount;
    private final int discount;

    public QuantityDocument(BussniesLayer.QuantityDocument QD) {
        minimalAmount = QD.getMinimalAmount();
        discount = QD.getDiscount();
    }

    @Override
    public String toString() {
        return "QuantityDocument: \n" +
                "minimalAmount = " + minimalAmount + "\n" +
                ", discount = " + discount + "\n";
    }
}
