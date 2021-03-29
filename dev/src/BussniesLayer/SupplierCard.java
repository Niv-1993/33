package BussniesLayer;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

public class SupplierCard {
    private int supplierBN;
    private String supplierName;
    private int accountNumber;
    private String payWay;
    private List<Order> orders;
    private List<Item> items;
    private SupplierAgreement supplierAgreement;
    private Dictionary<String , String> contactPhone;
    private Dictionary<String , String> contactEmail;

    public SupplierCard(int supplierBN , String supplierName , int accountNumber , String payWay){
        this.supplierBN = supplierBN;
        this.supplierName = supplierName;
        this.accountNumber = accountNumber;
        this.payWay = payWay;
        orders = new LinkedList<Order>();
        items = new LinkedList<Item>();
        supplierAgreement = null;
        contactPhone = new Hashtable<String, String>();
        contactEmail = new Hashtable<String, String>();
    }

    public int showSupplierBN(String supplierName){
    }

    public void updateSupplierPayWay(int supplierBN, String payWay) {
    }

    public void updateSupplierBankAccount(int supplierBN, int bankAccount) {
    }

    public void addContactPhone(int supplierBN, String phone, String name) {
    }

    public void addContactEmail(int supplierBN, String email, String name) {
    }

    public void removeContactPhone(int supplierBN, String phone) {
    }

    public void removeContactEmail(int supplierBN, String email) {
    }

    public void updateContactPhone(int supplierBN, String phone) {
    }

    public void updateContactEmail(int supplierBN, String email) {
    }

    public void showAllItemsOfSupplier(int supplierBN) {
    }
    public void removeItemFromSupplier(int supplierBN, int itemId) {
    }
}
