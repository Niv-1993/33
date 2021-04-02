package BussniesLayer.facade.outObjects;

import java.util.Dictionary;


public class SupplierCard{
    private int supplierBN;
    private int accountNumber;
    private String payWay;
    private Dictionary<String , String> contactPhone;
    private Dictionary<String , String> contactEmail;

    public SupplierCard(BussniesLayer.SupplierCard supplierCard) {
        supplierBN = supplierCard.getSupplierBN();
        accountNumber = supplierCard.getSupplierAccountNumber();
        payWay = supplierCard.getSupplierPayWay();
        contactPhone = supplierCard.getcontactPhone();
        contactEmail =  supplierCard.getcontactEmail();
    }

    @Override
    public String toString() {
        return "SupplierCard{" +
                "supplierBN=" + supplierBN +
                ", accountNumber=" + accountNumber +
                ", payWay='" + payWay + '\'' +
                ", contactPhone=" + contactPhone +
                ", contactEmail=" + contactEmail +
                '}';
    }
}
