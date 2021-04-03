package BussniesLayer.facade.outObjects;

import java.util.Dictionary;


public class SupplierCard{
    private final int supplierBN;
    private final int accountNumber;
    private final String payWay;
    private final Dictionary<String , String> contactPhone;
    private final Dictionary<String , String> contactEmail;

    public SupplierCard(BussniesLayer.SupplierCard supplierCard) {
        supplierBN = supplierCard.getSupplierBN();
        accountNumber = supplierCard.getSupplierAccountNumber();
        payWay = supplierCard.getSupplierPayWay();
        contactPhone = supplierCard.getContactPhone();
        contactEmail =  supplierCard.getContactEmail();
    }

    @Override
    public String toString() {
        return "SupplierCard: \n" +
                "supplierBN = " + supplierBN + "\n" +
                "accountNumber =" + accountNumber + "\n" +
                "payWay ='" + payWay + "\n" +
                "contactPhone=" + contactPhone + "\n" +
                "contactEmail=" + contactEmail + "\n";
    }

    public String toStringId(){ return supplierBN + "";}
}
