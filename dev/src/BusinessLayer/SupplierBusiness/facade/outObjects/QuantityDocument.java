package BusinessLayer.SupplierBusiness.facade.outObjects;

public class QuantityDocument{
    private final int minimalAmount;
    private final int discount;

    public QuantityDocument(BusinessLayer.SupplierBusiness.QuantityDocument QD) {
        minimalAmount = QD.getMinimalAmount();
        discount = QD.getDiscount();
    }

    public String toString() {
        return "Quantity Document: \n" +
                "\tminimal amount: " + minimalAmount + "\n" +
                "\tdiscount: " + discount;
    }

    public int getDiscount() {
        return discount;
    }
}
