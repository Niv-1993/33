package BussniesLayer.facade.outObjects;

public class QuantityDocument{
    private int minimalAmount;
    private int discount;

    public QuantityDocument(BussniesLayer.QuantityDocument QD) {
        minimalAmount = QD.getMinimalAmount();
        discount = QD.getDiscount();
    }

    @Override
    public String toString() {
        return "QuantityDocument{" +
                "minimalAmount=" + minimalAmount +
                ", discount=" + discount +
                '}';
    }
}
