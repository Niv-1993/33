package BussniesLayer;

import java.util.*;

public class SupplierController{
    private Dictionary<Integer , SupplierCard> suppliers;
    private int numOfItems;
    private int numOfOrders;

    public SupplierController(){
        suppliers = new Hashtable<>();
        numOfItems = 0;
        numOfOrders = 0;
    }

    public SupplierCard showSupplier(int supplierBN) throws Exception {
        SupplierCard supplierCard;
        try{
            supplierCard = suppliers.get(supplierBN);
        }catch (Exception e){
            throw new Exception("supplier BN does not exist.");
        }
        return supplierCard;
    }

    public void addSupplier(String supplierName, int bankAccount, String payWay) throws Exception {
        if(bankAccount < 0) throw new Exception("bank account must be a positive number");
        Enumeration<Integer> enumeration = suppliers.keys();
        while(enumeration.hasMoreElements()){
            Integer supplierID = enumeration.nextElement();
            if(suppliers.get(supplierID).getSupplierName().equals(supplierName)) throw new Exception("supplier name all ready exist");
        }
        BussniesLayer.SupplierCard supplierCard = new BussniesLayer.SupplierCard(suppliers.size() ,supplierName,bankAccount,payWay);
        suppliers.put(suppliers.size() , supplierCard);
    }

    public void removeSupplier(int removeSupplier) throws Exception {
        try {
            suppliers.remove(removeSupplier);
        } catch (Exception e){
            throw new Exception("supplier BN does not exist.");
        }
    }

    public SupplierCard showSupplierBN(String supplierName) throws Exception {
        Enumeration<Integer> enumeration = suppliers.keys();
        while(enumeration.hasMoreElements()) {
            int current = enumeration.nextElement();
            if (suppliers.get(current).getSupplierName().equals(supplierName)) {
                return suppliers.get(current);
            }
        }
        throw new Exception("supplier name is not exist");
    }

    public void updateSupplierPayWay(int supplierBN, String payWay) throws Exception {
        try {
            suppliers.get(supplierBN);
        } catch (Exception e){
            throw new Exception("supplier BN is not exist");
        }
        try {
            suppliers.get(supplierBN).updateSupplierPayWay(payWay);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    public void updateSupplierBankAccount(int supplierBN, int bankAccount) throws Exception {
        try {
            suppliers.get(supplierBN);
        } catch (Exception e){
            throw new Exception("supplier BN is not exist");
        }
        try {
            suppliers.get(supplierBN).updateSupplierBankAccount(bankAccount);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    public void addContactPhone(int supplierBN, String phone, String name) throws Exception {
        try {
            suppliers.get(supplierBN);
        } catch (Exception e){
            throw new Exception("supplier BN is not exist");
        }
        try {
            suppliers.get(supplierBN).addContactPhone(phone, name);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    public void addContactEmail(int supplierBN, String email, String name) throws Exception {
        try {
            suppliers.get(supplierBN);
        } catch (Exception e){
            throw new Exception("supplier BN is not exist");
        }
        try {
            suppliers.get(supplierBN).addContactEmail(email, name);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    public void removeContactPhone(int supplierBN, String phone) throws Exception {
        try {
            suppliers.get(supplierBN).removeContactPhone(phone);
        } catch (Exception e){
            throw new Exception("supplier BN is not exist");
        }
    }

    public void removeContactEmail(int supplierBN, String email) throws Exception {
        try {
            suppliers.get(supplierBN).removeContactEmail(email);
        } catch (Exception e){
            throw new Exception("supplier BN is not exist");
        }
    }

    public void updateContactPhone(int supplierBN, String phone) throws Exception {
        try {
            suppliers.get(supplierBN);
        } catch (Exception e){
            throw new Exception("supplier BN is not exist");
        }
        try {
            suppliers.get(supplierBN).updateContactPhone(phone);
        } catch (Exception e){
            throw new Exception(e);
        }
    };

    public void updateContactEmail(int supplierBN, String email) throws Exception {
        try {
            suppliers.get(supplierBN);
        } catch (Exception e){
            throw new Exception("supplier BN is not exist");
        }
        try {
            suppliers.get(supplierBN).updateContactEmail(email);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    public List<SupplierCard> showAllSuppliers() {
        List<SupplierCard> list = new LinkedList<>();
        Enumeration<SupplierCard> enumeration = suppliers.elements();
        while (enumeration.hasMoreElements()) {
            list.add(enumeration.nextElement());
        }
        return list;
    }

    public List<Item> showAllItemsOfSupplier(int supplierBN) throws Exception {
        try {
            suppliers.get(supplierBN);
        } catch (Exception e){
            throw new Exception("supplier BN is not exist");
        }
        return suppliers.get(supplierBN).showAllItemsOfSupplier();
    }

    public List<Item> showAllItemsOfOrder(int supplierBN , int orderId) throws Exception {
        try {
            suppliers.get(supplierBN);
        } catch (Exception e){
            throw new Exception("supplier BN is not exist");
        }
        return suppliers.get(supplierBN).showAllItemsOfOrder(orderId);
    }

    public List<Item> showAllItems() {
        List<Item> list = new LinkedList<>();
        Enumeration<SupplierCard> enumeration = suppliers.elements();
        while (enumeration.hasMoreElements()) {
            List<Item> toAdd = enumeration.nextElement().showAllItemsOfSupplier();
            list.addAll(toAdd);
        }
        return list;
    }

    public Item addItem(int supplierBN, String category ,double price) throws Exception {
        Item item;
        try {
            suppliers.get(supplierBN);
        }catch (Exception e){
            throw new Exception("supplier BN is not exist");
        }
        try {
            item = suppliers.get(supplierBN).addItem(category, numOfItems , price);
        }catch (Exception e){
            throw new Exception(e);
        }
        numOfItems++;
        return item;
    }

    public void removeItem(int itemId) {
        Enumeration<SupplierCard> enumeration = suppliers.elements();
        while (enumeration.hasMoreElements()) {
            SupplierCard supplierCard = enumeration.nextElement();
            supplierCard.removeItemFromSupplier(itemId);
        }
    }

    public void removeItemFromSupplier(int supplierBN, int itemId) throws Exception {
        try {
            suppliers.get(supplierBN);
        } catch (Exception e){
            throw new Exception("supplier BN is not exist");
        }
        suppliers.get(supplierBN).removeItemFromSupplier(itemId);
    }

    public Order addOrder(int supplierBN) throws Exception {
        Order order;
        try {
            suppliers.get(supplierBN);
        } catch (Exception e){
            throw new Exception("supplier BN is not exist");
        }
        try {
            order = suppliers.get(supplierBN).addOrder(numOfOrders);
        }catch (Exception e){
            throw new Exception(e);
        }
        numOfOrders++;
        return order;
    }

    public void addItemToOrder(int supplierBN, int orderId, int itemId , int amount) throws Exception {
        try {
            suppliers.get(supplierBN);
        } catch (Exception e){
            throw new Exception("supplier BN is not exist");
        }
        try{
            suppliers.get(supplierBN).addItemToOrder(orderId, itemId , amount);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    public Order showOrderOfSupplier(int supplierBN, int orderId) throws Exception {
        try {
            suppliers.get(supplierBN);
        } catch (Exception e){
            throw new Exception("supplier BN is not exist");
        }
        try{
            return suppliers.get(supplierBN).showOrderOfSupplier(orderId);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    public List<Order> showAllOrdersOfSupplier(int supplierBN) throws Exception {
        try {
            suppliers.get(supplierBN);
        } catch (Exception e){
            throw new Exception("supplier BN is not exist");
        }
        return suppliers.get(supplierBN).showAllOrdersOfSupplier();
    }

    public Order showTotalAmount(int supplierBN, int orderId) throws Exception {
        try {
            suppliers.get(supplierBN);
        } catch (Exception e){
            throw new Exception("supplier BN is not exist");
        }
        return suppliers.get(supplierBN).showTotalAmount(orderId);
    }

    public Order showDeliverTime(int supplierBN, int orderId) throws Exception {
        try {
            suppliers.get(supplierBN);
        } catch (Exception e){
            throw new Exception("supplier BN is not exist");
        }
        try {
            return suppliers.get(supplierBN).showDeliverTime(orderId);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    public void updateDeliverTime(int supplierBN, int orderId, Date deliverTime) throws Exception {
        try {
            suppliers.get(supplierBN);
        } catch (Exception e){
            throw new Exception("supplier BN is not exist");
        }
        try {
            suppliers.get(supplierBN).updateDeliverTime(orderId, deliverTime);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    public void addQuantityDocument(int supplierBN, int itemId, int minimalAmount, int discount) throws Exception {
        try {
            suppliers.get(supplierBN);
        } catch (Exception e){
            throw new Exception("supplier BN is not exist");
        }
        try {
            suppliers.get(supplierBN).addQuantityDocument(itemId, minimalAmount, discount);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public void removeQuantityDocument(int supplierBN, int itemId) throws Exception {
        try {
            suppliers.get(supplierBN);
        } catch (Exception e){
            throw new Exception("supplier BN is not exist");
        }
        try {
            suppliers.get(supplierBN).removeQuantityDocument(itemId);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public QuantityDocument showQuantityDocument(int supplierBN, int itemId) throws Exception {
        try {
            suppliers.get(supplierBN);
        } catch (Exception e){
            throw new Exception("supplier BN is not exist");
        }
        try {
            return suppliers.get(supplierBN).showQuantityDocument(itemId);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public void updateMinimalAmountOfQD(int supplierBN, int itemId, int minimalAmount) throws Exception {
        try {
            suppliers.get(supplierBN);
        } catch (Exception e){
            throw new Exception("supplier BN is not exist");
        }
        try {
            suppliers.get(supplierBN).updateMinimalAmountOfQD(itemId, minimalAmount);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public void updateDiscountOfQD(int supplierBN, int itemId, int discount) throws Exception {
        try {
            suppliers.get(supplierBN);
        } catch (Exception e){
            throw new Exception("supplier BN is not exist");
        }
        try {
            suppliers.get(supplierBN).updateDiscountOfQD(itemId,discount);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public void addSupplierAgreement(int supplierBN, int minimalAmount, int discount, boolean constantTime, boolean shipToUs) throws Exception {
        try {
            suppliers.get(supplierBN);
        } catch (Exception e){
            throw new Exception("supplier BN is not exist");
        }
        try {
            suppliers.get(supplierBN).addSupplierAgreement(minimalAmount, discount, constantTime, shipToUs);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public SupplierAgreement showSupplierAgreement(int supplierBN) throws Exception {
        try {
            suppliers.get(supplierBN);
        } catch (Exception e){
            throw new Exception("supplier BN is not exist");
        }
        return suppliers.get(supplierBN).showSupplierAgreement();
    }

    public void updateMinimalAmountOfSA(int supplierBN, int minimalAmount) throws Exception {
        try {
            suppliers.get(supplierBN);
        } catch (Exception e){
            throw new Exception("supplier BN is not exist");
        }
        try {
            suppliers.get(supplierBN).updateMinimalAmountOfSA(minimalAmount);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    public void updateDiscountOfSA(int supplierBN, int discount) throws Exception {
        try {
            suppliers.get(supplierBN);
        } catch (Exception e){
            throw new Exception("supplier BN is not exist");
        }
        try {
            suppliers.get(supplierBN).updateDiscountOfSA(discount);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    public void updateConstantTime(int supplierBN, boolean constantTime) throws Exception {
        try {
            suppliers.get(supplierBN);
        } catch (Exception e){
            throw new Exception("supplier BN is not exist");
        }
        suppliers.get(supplierBN).updateConstantTime(constantTime);
    }

    public void updateShipToUs(int supplierBN, boolean shipToUs) throws Exception {
        try {
            suppliers.get(supplierBN);
        } catch (Exception e){
            throw new Exception("supplier BN is not exist");
        }
        suppliers.get(supplierBN).updateShipToUs(shipToUs);
    }

    public void updatePrice(int supplierBN, int itemId, double price) throws Exception {
        try {
            suppliers.get(supplierBN);
        } catch (Exception e){
            throw new Exception("supplier BN is not exist");
        }
        try {
            suppliers.get(supplierBN).updatePrice(itemId,price);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

}
