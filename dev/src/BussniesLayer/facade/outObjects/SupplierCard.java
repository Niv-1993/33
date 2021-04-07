package BussniesLayer.facade.outObjects;

import javax.swing.*;
import java.util.Dictionary;


public class SupplierCard{
    private final int supplierBN;
    private final int bankNumber;
    private final int brunchNumber;
    private final int accountNumber;
    private final String payWay;
    private final Dictionary<String , String> contactPhone;
    private final Dictionary<String , String> contactEmail;

    public SupplierCard(BussniesLayer.SupplierCard supplierCard) {
        supplierBN = supplierCard.getSupplierBN();
        bankNumber = supplierCard.getSupplierBankNumber();
        brunchNumber = supplierCard.getSupplierBrunchNumber();
        accountNumber = supplierCard.getSupplierAccountNumber();
        payWay = supplierCard.getSupplierPayWay();
        contactPhone = supplierCard.getContactPhone();
        contactEmail =  supplierCard.getContactEmail();
    }

    public String toString() {
        return "SupplierCard: \n" +
                "\tsupplierBN: " + supplierBN + "\n" +
                "\tbank number: " + bankNumber + "\n" +
                "\tbrunch number: " + brunchNumber + "\n" +
                "\taccount number: " + accountNumber + "\n" +
                "\tpayWay: " + payWay + "\n" +
                "\tcontact Phone: " + contactPhone + "\n" +
                "\tcontact Email: " + contactEmail;
    }

    public String toStringId(){ return supplierBN + "";}

    public int getSupplierBN() {
        return supplierBN;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getPayWay() {
        return payWay;
    }
}
