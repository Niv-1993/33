package BussniesLayer;

import java.util.*;


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


    public int showSupplierBN(String supplierName) {

    }

    public void updateSupplierPayWay(String payWay) {
    }

    public void updateSupplierBankAccount(int bankAccount) {
    }

    public void addContactPhone(String phone, String name) {
    }

    public void addContactEmail(String email, String name) {
    }

    public void removeContactPhone(String phone) {
    }

    public void removeContactEmail(String email) {
    }

    public void updateContactPhone(String phone) {
    }

    public void updateContactEmail(String email) {
    }


    public void showAllItemsOfSupplier() {
    }

    public void addItem(String category) {
    }

    public void removeItemFsromSupplier(int itemId) {
    }

    public void addOrder() {
    }

    public void addItemToOrder(int orderId, int itemId) {
    }

    public void showOrderOfSupplier(int orderId) {
    }

    public void showAllOrdersOfSupplier() {
    }

    public void showTotalAmount(int orderId) {
    }

    public void showDeliverTime(int orderId) {
    }

    public void updateDeliverTime(int orderId, Date deliverTime) {
    }

    public void addQuantityDocument(int itemId, int minimalAmount, int discount) {

    }

    public void removeQuantityDocument(int itemId) {
    }

    public void showQuantityDocument(int itemId) {
    }

    public void updateMinimalAmountOfQD(int itemId, int minimalAmount) {
    }

    public void updateDiscountOfQD(int itemId, int discount) {
    }

    public void addSupplierAgreement(int minimalAmount, int discount, boolean constantTime, boolean shipToUs) {
        SupplierAgreement SA = new SupplierAgreement(supplierBN ,minimalAmount, discount, constantTime, shipToUs);
        supplierAgreement = SA;
    }

    public void showSupplierAgreement() {
    }

    public void updateMinimalAmountOfSA(int minimalAmount) {
    }

    public void updateDiscountOfSA(int discount) {
    }

    public void updateConstantTime(boolean constantTime) {
    }

    public void updateShipToUs(boolean shipToUs) {
    }
}
