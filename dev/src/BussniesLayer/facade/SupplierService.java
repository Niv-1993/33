package BussniesLayer.facade;

import BussniesLayer.SupplierController;
import BussniesLayer.ISupplierService;
import BussniesLayer.facade.outObjects.SupplierCard;
import BussniesLayer.facade.outObjects.Item;
import BussniesLayer.facade.outObjects.Order;
import BussniesLayer.facade.outObjects.QuantityDocument;
import BussniesLayer.facade.outObjects.SupplierAgreement;
import DBMS.data;


import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class SupplierService implements ISupplierService {
    private final SupplierController supplierController;

    public SupplierService() {
        supplierController = new SupplierController();
    }

    @Override
    public response LoadData() {
        data.getInstance();
        return new response();
    }

    @Override
    public response deleteData() { return new response(); } // check!

    @Override
    public Tresponse<SupplierCard> showSupplier(int supplierBN) {
        BussniesLayer.SupplierCard supplierCard;
        try {
            supplierCard = supplierController.showSupplier(supplierBN);
        }catch (Exception e){
            return new Tresponse<>("ERROR: " + e);
        }
        return new Tresponse<>(new SupplierCard(supplierCard));
    }

    @Override
    public response addSupplier(String supplierName, int bankAccount, String payWay) {
        try{
            supplierController.addSupplier(supplierName, bankAccount, payWay);
        }catch (Exception e){
            return new response("ERROR: " + e);
        }
        return new response();
    }

    @Override
    public response removeSupplier(int removeSupplier) {
        try{
            supplierController.removeSupplier(removeSupplier);
        }catch (Exception e){
            return new response("ERROR: " + e);
        }
        return new response();
    }

    @Override
    public Tresponse<SupplierCard> showSupplierBN(String supplierName) {
        BussniesLayer.SupplierCard supplierCard;
        try {
            supplierCard = supplierController.showSupplierBN(supplierName);
        }catch (Exception e){
            return new Tresponse<>("ERROR: " + e);
        }
        return new Tresponse<>(new SupplierCard(supplierCard));
    }

    @Override
    public response updateSupplierPayWay(int supplierBN, String payWay) {
        try{
            supplierController.updateSupplierPayWay(supplierBN, payWay);
        }catch (Exception e){
            return new response("ERROR: " + e);
        }
        return new response();
    }

    @Override
    public response updateSupplierBankAccount(int supplierBN, int bankAccount) {
        try{
            supplierController.updateSupplierBankAccount(supplierBN, bankAccount);
        }catch (Exception e){
            return new response("ERROR: " + e);
        }
        return new response();
    }

    @Override
    public response addContactPhone(int supplierBN, String phone, String name) {
        try{
            supplierController.addContactPhone(supplierBN, phone, name);
        }catch (Exception e){
            return new response("ERROR: " + e);
        }
        return new response();
    }

    @Override
    public response addContactEmail(int supplierBN, String Email, String name) {
        try{
            supplierController.addContactEmail(supplierBN, Email, name);
        }catch (Exception e){
            return new response("ERROR: " + e);
        }
        return new response();
    }

    @Override
    public response removeContactPhone(int supplierBN, String phone) {
        try{
            supplierController.removeContactPhone(supplierBN, phone);
        }catch (Exception e){
            return new response("ERROR: " + e);
        }
        return new response();
    }

    @Override
    public response removeContactEmail(int supplierBN, String email) {
        try{
            supplierController.removeContactEmail(supplierBN, email);
        }catch (Exception e){
            return new response("ERROR: " + e);
        }
        return new response();
    }

    @Override
    public response updateContactPhone(int supplierBN, String phone) {
        try{
            supplierController.updateContactPhone(supplierBN, phone);
        }catch (Exception e){
            return new response("ERROR: " + e);
        }
        return new response();
    }

    @Override
    public response updateContactEmail(int supplierBN, String email) {
        try{
            supplierController.updateContactEmail(supplierBN, email);
        }catch (Exception e){
            return new response("ERROR: " + e);
        }
        return new response();
    }

    @Override
    public Tresponse<List<SupplierCard>> showAllSuppliers() {
        List<BussniesLayer.SupplierCard> supplierCards;
        List<SupplierCard> outSupplierCard = new LinkedList<>();
        try {
            supplierCards = supplierController.showAllSuppliers();
            for(BussniesLayer.SupplierCard supplierCard : supplierCards){
                outSupplierCard.add(new SupplierCard(supplierCard));
            }
        }catch (Exception e){
            return new Tresponse<>("ERROR: " + e);
        }
        return new Tresponse<>(outSupplierCard);
    }

    @Override
    public Tresponse<List<Item>> showAllItemsOfSupplier(int SupplierBN) {
        List<BussniesLayer.Item> items;
        List<Item> outItems = new LinkedList<>();
        try {
            items = supplierController.showAllItemsOfSupplier(SupplierBN);
            for(BussniesLayer.Item item : items){
                outItems.add(new Item(item));
            }
        }catch (Exception e){
            return new Tresponse<>("ERROR: " + e);
        }
        return new Tresponse<>(outItems);
    }

    @Override
    public Tresponse<List<Item>> showAllItemsOfOrder(int SupplierBN, int orderId) {
        List<BussniesLayer.Item> items;
        List<Item> outItems = new LinkedList<>();
        try {
            items = supplierController.showAllItemsOfOrder(SupplierBN , orderId);
            for(BussniesLayer.Item item : items){
                outItems.add(new Item(item));
            }
        }catch (Exception e){
            return new Tresponse<>("ERROR: " + e);
        }
        return new Tresponse<>(outItems);
    }

    @Override
    public Tresponse<List<Item>> showAllItems() {
        List<BussniesLayer.Item> items;
        List<Item> outItems = new LinkedList<>();
        try {
            items = supplierController.showAllItems();
            for(BussniesLayer.Item item : items){
                outItems.add(new Item(item));
            }
        }catch (Exception e){
            return new Tresponse<>("ERROR: " + e);
        }
        return new Tresponse<>(outItems);
    }

    @Override
    public Tresponse<Item> addItem(int supplierBN, String category , double price) {
        BussniesLayer.Item item;
        try{
            item = supplierController.addItem(supplierBN, category , price);
        }catch (Exception e){
            return new Tresponse<>("ERROR: " + e);
        }
        return new Tresponse<>(new Item(item));
    }

    @Override
    public response removeItem(int itemId) {
        try{
            supplierController.removeItem(itemId);
        }catch (Exception e){
            return new response("ERROR: " + e);
        }
        return new response();
    }

    @Override
    public response removeItemFromSupplier(int supplierBN, int itemId) {
        try{
            supplierController.removeItemFromSupplier(supplierBN, itemId);
        }catch (Exception e){
            return new response("ERROR: " + e);
        }
        return new response();
    }

    @Override
    public Tresponse<Order> addOrder(int supplierBN) {  // return an empty order that show the orderID.
        BussniesLayer.Order order;
        try {
            order = supplierController.addOrder(supplierBN);
        }catch (Exception e){
            return new Tresponse<>("ERROR: " + e);
        }
        return new Tresponse<>(new Order(order));
    }

    @Override
    public response addItemToOrder(int supplierBN, int orderId, int itemId , int amount) {
        try{
            supplierController.addItemToOrder(supplierBN, orderId, itemId , amount);
        }catch (Exception e){
            return new response("ERROR: " + e);
        }
        return new response();
    }

    @Override
    public Tresponse<Order> showOrderOfSupplier(int supplierBN, int orderId) {
        BussniesLayer.Order order;
        try {
            order = supplierController.showOrderOfSupplier(supplierBN, orderId);
        }catch (Exception e){
            return new Tresponse<>("ERROR: " + e);
        }
        return new Tresponse<>(new Order(order));
    }

    @Override
    public Tresponse<List<Order>> showAllOrdersOfSupplier(int supplierBN) {
        List<BussniesLayer.Order> orders;
        List<Order> outOrder = new LinkedList<>();
        try {
            orders = supplierController.showAllOrdersOfSupplier(supplierBN);
            for(BussniesLayer.Order order : orders){
                outOrder.add(new Order(order));
            }
        }catch (Exception e){
            return new Tresponse<>("ERROR: " + e);
        }
        return new Tresponse<>(outOrder);
    }

    @Override
    public Tresponse<Order> showTotalAmount(int supplierBN, int orderId) {
        BussniesLayer.Order order;
        try{
            order = supplierController.showTotalAmount(supplierBN, orderId);
        }catch (Exception e){
            return new Tresponse<>("ERROR: " + e);
        }
        return new Tresponse<>(new Order(order));
    }

    @Override
    public Tresponse<Order> showDeliverTime(int supplierBN, int orderId) {
        BussniesLayer.Order order;
        try{
            order = supplierController.showDeliverTime(supplierBN, orderId);
        }catch (Exception e){
            return new Tresponse<>("ERROR: " + e);
        }
        return new Tresponse<>(new Order(order));
    }

    @Override
    public response updateDeliverTime(int supplierBN, int orderId, Date deliverTime){
        try{
            supplierController.updateDeliverTime(supplierBN, orderId, deliverTime);
        }catch (Exception e){
            return new response("ERROR: " + e);
        }
        return new response();
    }

    @Override
    public response addQuantityDocument(int supplierBN, int itemId, int minimalAmount, int discount){
        try{
            supplierController.addQuantityDocument(supplierBN, itemId, minimalAmount,discount);
        }catch (Exception e){
            return new response("ERROR: " + e);
        }
        return new response();
    }

    @Override
    public response removeQuantityDocument(int supplierBN, int itemId) {
        try{
            supplierController.removeQuantityDocument(supplierBN , itemId);
        }catch (Exception e){
            return new response("ERROR: " + e);
        }
        return new response();
    }

    @Override
    public Tresponse<QuantityDocument> showQuantityDocument(int supplierBN, int itemId) {
        BussniesLayer.QuantityDocument quantityDocument;
        try {
            quantityDocument =  supplierController.showQuantityDocument(supplierBN, itemId);
        }catch (Exception e){
            return new Tresponse<>("ERROR: " + e);
        }
        return new Tresponse<>(new QuantityDocument(quantityDocument));
    }

    @Override
    public response updateMinimalAmountOfQD(int supplierBN, int itemId, int minimalAmount) {
        try{
            supplierController.updateMinimalAmountOfQD(supplierBN, itemId, minimalAmount);
        }catch (Exception e){
            return new response("ERROR: " + e);
        }
        return new response();
    }

    @Override
    public response updateDiscountOfQD(int supplierBN, int itemId, int discount) {
        try{
            supplierController.updateDiscountOfQD(supplierBN, itemId, discount);
        }catch (Exception e){
            return new response("ERROR: " + e);
        }
        return new response();
    }

    @Override
    public response addSupplierAgreement(int supplierBN, int minimalAmount, int discount, boolean constantTime, boolean shipToUs) {
        try{
            supplierController.addSupplierAgreement(supplierBN, minimalAmount, discount, constantTime, shipToUs);
        }catch (Exception e){
            return new response("ERROR: " + e);
        }
        return new response();
    }

    @Override
    public Tresponse<SupplierAgreement> showSupplierAgreement(int supplierBN) {
        BussniesLayer.SupplierAgreement supplierAgreement;
        try {
            supplierAgreement = supplierController.showSupplierAgreement(supplierBN);
        }catch (Exception e){
            return new Tresponse<>("ERROR: " + e);
        }
        return new Tresponse<>(new SupplierAgreement(supplierAgreement));
    }

    @Override
    public response updateMinimalAmountOfSA(int supplierBN, int minimalAmount) {
        try{
            supplierController.updateMinimalAmountOfSA(supplierBN, minimalAmount);
        }catch (Exception e){
            return new response("ERROR: " + e);
        }
        return new response();
    }

    @Override
    public response updateDiscountOfSA(int supplierBN, int discount) {
        try{
            supplierController.updateDiscountOfSA(supplierBN, discount);
        }catch (Exception e){
            return new response("ERROR: " + e);
        }
        return new response();
    }

    @Override
    public response updateConstantTime(int supplierBN, boolean constantTime) {
        try{
            supplierController.updateConstantTime(supplierBN, constantTime);
        }catch (Exception e){
            return new response("ERROR: " + e);
        }
        return new response();
    }

    @Override
    public response updateShipToUs(int supplierBN, boolean ShipToUs) {
        try{
            supplierController.updateShipToUs(supplierBN, ShipToUs);
        }catch (Exception e){
            return new response("ERROR: " + e);
        }
        return new response();
    }

    @Override
    public response updatePrice(int supplierBN, int itemId, double price) {
        try{
            supplierController.updatePrice(supplierBN , itemId , price);
        }catch (Exception e){
            return new response("ERROR: " + e);
        }
        return new response();
    }
}
