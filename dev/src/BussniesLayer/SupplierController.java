package BussniesLayer;

import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Set;

import BussniesLayer.facade.outObjects.SupplierCard;

public class SupplierController{
    private Dictionary<Integer , BussniesLayer.SupplierCard> suppliers;
    private int numOfItems;
    private int numOfOrders;

    public SupplierController(){
        suppliers = new Hashtable<Integer, BussniesLayer.SupplierCard>();
        numOfItems = 0;
        numOfOrders = 0;
    }

    public SupplierCard showSupplier(int supplierBN){
        return suppliers.get(supplierBN);
    }

    public void addSupplier(int supplierBN, String supplierName, int bankAccount, String payWay){
         BussniesLayer.SupplierCard supplierCard = new BussniesLayer.SupplierCard(supplierBN,supplierName,bankAccount,payWay);
         suppliers.put(supplierBN, supplierCard);
    }

    public void removeSupplier(int removeSupplier) {
        suppliers.remove(removeSupplier);
    }

    public void showSupplierBN(String supplierName) {
        while(suppliers.keys().hasMoreElements()) {
            int current = suppliers.keys().nextElement();
            if (suppliers.get(current).getSupplierBN().equals(supplierName)) {
                //need to add what do do
                break;
            }
        }
    }

    public void updateSupplierPayWay(int supplierBN, String payWay) {
        suppliers.get(supplierBN).updateSupplierPayWay(payWay);
    }

    public void updateSupplierBankAccount(int supplierBN, int bankAccount) {
        suppliers.get(supplierBN).updateSupplierBankAccount(bankAccount);
    }

    public void addContactPhone(int supplierBN, String phone, String name) {
        suppliers.get(supplierBN).addContactPhone(phone, name);
    }

    public void addContactEmail(int supplierBN, String email, String name) {
        suppliers.get(supplierBN).addContactEmail(email, name);
    }

    public void removeContactPhone(int supplierBN, String phone) {
        suppliers.get(supplierBN).removeContactPhone(phone);
    }

    public void removeContactEmail(int supplierBN, String email) {
        suppliers.get(supplierBN).removeContactEmail(email);
    }

    public void updateContactPhone(int supplierBN, String phone) {
        suppliers.get(supplierBN).updateContactPhone(phone);
    }

    public void updateContactEmail(int supplierBN, String email) {
        suppliers.get(supplierBN).updateContactEmail(email);
    }

    public void showAllSuppliers() {
    }

    public void showAllItemsOfSupplier(int supplierBN) {
    }

    public void showAllItems() {
    }

    public void addItem(int supplierBN, String category) {
        Item newItem = new Item(supplierBN, category, numOfItems);
        numOfItems++;
        suppliers.get(supplierBN).addItem(category, newItem.getItemId());
    }

    public void removeItem(int itemId) {
        //???
    }

    public void removeItemFromSupplier(int supplierBN, int itemId) {
        suppliers.get(supplierBN).removeItemFromSupplier(itemId);
    }

    public void addOrder(int supplierBN) {
        Order newOrder = new Order(numOfOrders, supplierBN, 0, null);
    }

    public void addItemToOrder(int supplierBN, int orderId, int itemId) {
        suppliers.get(supplierBN).addItemToOrder(orderId, itemId);
    }

    public void showOrderOfSupplier(int supplierBN, int orderId) {
        suppliers.get(supplierBN).showOrderOfSupplier(orderId);
    }

    public void showAllOrdersOfSupplier(int supplierBN) {
    }

    public void showTotalAmount(int supplierBN, int orderId) {
    }

    public void showDeliverTime(int supplierBN, int orderId) {
    }

    public void updateDeliverTime(int supplierBN, int orderId, Date deliverTime) {
        suppliers.get(supplierBN).updateDeliverTime(orderId, deliverTime);
    }

    public void addQuantityDocument(int supplierBN, int itemId, int minimalAmount, int discount) {
        QuantityDocument newQD = new QuantityDocument(supplierBN,itemId, minimalAmount, discount);
        suppliers.get(supplierBN).addQuantityDocument(itemId, minimalAmount, discount);
    }

    public void removeQuantityDocument(int supplierBN, int itemId) {
        suppliers.get(supplierBN).removeQuantityDocument(itemId);
    }

    public void showQuantityDocument(int supplierBN, int itemId) {
    }

    public void updateMinimalAmountOfQD(int supplierBN, int itemId, int minimalAmount) {
        suppliers.get(supplierBN).updateMinimalAmountOfQD(itemId, minimalAmount);
    }

    public void updateDiscountOfQD(int supplierBN, int itemId, int discount) {
        suppliers.get(supplierBN).updateDiscountOfQD(itemId,discount);
    }

    public void addSupplierAgreement(int supplierBN, int minimalAmount, int discount, boolean constantTime, boolean shipToUs) {
        SupplierAgreement newSA = new SupplierAgreement(supplierBN, minimalAmount, discount, constantTime, shipToUs);
        suppliers.get(supplierBN).addSupplierAgreement(minimalAmount, discount, constantTime, shipToUs);
    }

    public void showSupplierAgreement(int supplierBN) {
    }

    public void updateMinimalAmountOfSA(int supplierBN, int minimalAmount) {
        suppliers.get(supplierBN).updateMinimalAmountOfSA(minimalAmount);
    }

    public void updateDiscountOfSA(int supplierBN, int discount) {
        suppliers.get(supplierBN).updateDiscountOfSA(discount);
    }

    public void updateConstantTime(int supplierBN, boolean constantTime) {
        suppliers.get(supplierBN).updateConstantTime(constantTime);
    }

    public void updateShipToUs(int supplierBN, boolean shipToUs) {
        suppliers.get(supplierBN).updateShipToUs(shipToUs);
    }
}
