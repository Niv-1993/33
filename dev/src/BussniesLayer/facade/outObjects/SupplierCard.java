package BussniesLayer.facade.outObjects;

import javax.swing.*;
import java.util.Dictionary;


public class SupplierCard{
    private final int supplierBN;
    private final String supplierName;
    private final int bankNumber;
    private final int brunchNumber;
    private final int accountNumber;
    private final String payWay;
    private final Dictionary<String , String> contactPhone;
    private final Dictionary<String , String> contactEmail;

    public SupplierCard(BussniesLayer.SupplierCard supplierCard) {
        supplierBN = supplierCard.getSupplierBN();
        supplierName = supplierCard.getSupplierName();
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
                "\tsupplier name: " + supplierName + "\n" +
                "\tbank number: " + bankNumber + "\n" +
                "\tbrunch number: " + brunchNumber + "\n" +
                "\taccount number: " + accountNumber + "\n" +
                "\tpayWay: " + payWay + "\n" +
                "\tcontact Phone: " + helpPrint(contactPhone , true) + "\n" +
                "\tcontact Email: " + helpPrint(contactEmail , false);
    }

    private String helpPrint(Dictionary<String , String> dictionary , boolean p_e){
        int size = 0;
        String toReturn = "";
        while (dictionary.keys().hasMoreElements() && size < dictionary.size()){
            String element = dictionary.keys().nextElement();
            if(p_e) toReturn = toReturn + "\n\t\tname: " + dictionary.get(element) + " , phone: " + element;
            else toReturn = toReturn + "\n\t\tname: " + dictionary.get(element) + " , email: " + element;
            size ++;
        }
        return toReturn;
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

    public String getSupplierName() {
        return supplierName;
    }

    public int getBankNumber() {
        return bankNumber;
    }

    public int getBrunchNumber() {
        return brunchNumber;
    }
}
