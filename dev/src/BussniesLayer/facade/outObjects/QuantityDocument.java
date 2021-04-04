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
                "\tminimalAmount = " + minimalAmount + "\n" +
                "\tdiscount = " + discount + "\n";
    }
}
