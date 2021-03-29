package BussniesLayer;

import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;
import BussniesLayer.facade.outObjects.SupplierCard;

public class SupplierController{
    private Dictionary<Integer ,SupplierCard> suppliers;
    private int numOfItems;
    private int numOfOrders;

    public SupplierController(){
        suppliers = new Hashtable<Integer, SupplierCard>();
        numOfItems = 0;
        numOfOrders = 0;
    }

    public SupplierCard showSupplier(int supplierBN){
        return suppliers.get(supplierBN);
    }

    public void addSupplier(int supplierBN, String supplierName, int bankAccount, String payWay){

    }

    public void removeSupplier(int removeSupplier) {
    }

    public int showSupplierBN(String supplierName) {
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

    public void showAllSuppliers() {
    }

    public void showAllItemsOfSupplier(int supplierBN) {
    }

    public void showAllItems() {
    }

    public void addItem(int supplierBN, String category) {
    }

    public void removeItem(int itemId) {
    }

    public void removeItemFromSupplier(int supplierBN, int itemId) {
    }

    public void addOrder(int supplierBN) {
    }

    public void addItemToOrder(int supplierBN, int orderId, int itemId) {
    }

    public void showOrderOfSupplier(int supplierBN, int orderId) {
    }

    public void showAllOrdersOfSupplier(int supplierBN) {
    }

    public void showTotalAmount(int supplierBN, int orderId) {
    }

    public void showDeliverTime(int supplierBN, int orderId) {
    }

    public void updateDeliverTime(int supplierBN, int orderId, Date deliverTime) {
    }

    public void addQuantityDocument(int supplierBN, int itemId, int minimalAmount, int discount) {
    }

    public void removeQuantityDocument(int supplierBN, int itemId) {
    }

    public void showQuantityDocument(int supplierBN, int itemId) {
    }

    public void updateMinimalAmountOfQD(int supplierBN, int itemId, int minimalAmount) {
    }

    public void updateDiscountOfQD(int supplierBN, int itemId, int discount) {
    }

    public void addSupplierAgreement(int supplierBN, int minimalAmount, int discount, boolean constantTime, boolean shipToUs) {
    }

    public void showSupplierAgreement(int supplierBN) {
    }

    public void updateMinimalAmountOfSA(int supplierBN, int minimalAmount) {
    }

    public void updateDiscountOfSA(int supplierBN, int discount) {
    }

    public void updateConstantTime(int supplierBN, boolean constantTime) {
    }

    public void updateShipToUs(int supplierBN, boolean shipToUs) {
    }
}
