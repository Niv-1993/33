package BussniesLayer.facade;

import BussniesLayer.SupplierController;
import BussniesLayer.ISupplierService;
import BussniesLayer.facade.outObjects.*;

import java.util.Date;
import java.util.List;

public class SupplierService implements ISupplierService {
    private SupplierController supplierController;

    public SupplierService() {
        supplierController = new SupplierController();
    }

    @Override
    public response LoadDate() {
        return null;
    }

    @Override
    public response deleteData() {
        return null;
    }

    @Override
    public Tresponse<SupplierCard> showSupplier(int supplierBN) {
        supplierController.showSupplier(supplierBN);
    }

    @Override
    public response addSupplier(int supplierBN, String supplierName, int bankAccount, String payWay) {
        supplierController.addSupplier(supplierBN, supplierName, bankAccount, payWay);
    }

    @Override
    public response removeSupplier(int removeSupplier) {
        supplierController.removeSupplier(removeSupplier);
    }

    @Override
    public Tresponse<SupplierCard> showSupplierBN(String supplierName) {
        supplierController.showSupplierBN(supplierName);
    }

    @Override
    public response updateSupplierPayWay(int supplierBN, String payWay) {
        supplierController.updateSupplierPayWay(supplierBN, payWay);
    }

    @Override
    public response updateSupplierBankAccount(int supplierBN, int bankAccount) {
        supplierController.updateSupplierBankAccount(supplierBN, bankAccount);
    }

    @Override
    public response addContactPhone(int supplierBN, String phone, String name) {
        supplierController.addContactPhone(supplierBN, phone, name);
    }

    @Override
    public response addContactEmail(int supplierBN, String Email, String name) {
        supplierController.addContactEmail(supplierBN, Email, name);
    }

    @Override
    public response removeContactPhone(int supplierBN, String phone) {
        supplierController.removeContactPhone(supplierBN, phone);
    }

    @Override
    public response removeContactEmail(int supplierBN, String email) {
        supplierController.removeContactEmail(supplierBN, email);
    }

    @Override
    public response updateContactPhone(int supplierBN, String phone) {
        supplierController.updateContactPhone(supplierBN, phone);
    }

    @Override
    public response updateContactEmail(int supplierBN, String email) {
        supplierController.updateContactEmail(supplierBN, email);
    }

    @Override
    public Tresponse<List<SupplierCard>> showAllSuppliers() {
        supplierController.showAllSuppliers();
    }

    @Override
    public Tresponse<List<Item>> showAllItemsOfSupplier(int SupplierBN) {
        supplierController.showAllItemsOfSupplier(SupplierBN);
    }

    @Override
    public Tresponse<List<Item>> showAllItems() {
        supplierController.showAllItems();
    }

    @Override
    public response addItem(int supplierBN, String category) {
        supplierController.addItem(supplierBN, category);
    }

    @Override
    public response removeItem(int itemId) {
        supplierController.removeItem(itemId);
    }

    @Override
    public response removeItemFromSupplier(int supplierBN, int itemId) {
        supplierController.removeItemFromSupplier(supplierBN, itemId);
    }

    @Override
    public Tresponse<Order> addOrder(int supplierBN) {
        supplierController.addOrder(supplierBN);
    }

    @Override
    public response addItemToOrder(int supplierBN, int orderId, int itemId) {
        supplierController.addItemToOrder(supplierBN, orderId, itemId);
    }

    @Override
    public Tresponse<List<Order>> showOrderOfSupplier(int supplierBN, int orderId) {
        supplierController.showOrderOfSupplier(supplierBN, orderId);
    }

    @Override
    public Tresponse<List<Order>> showAllOrdersOfSupplier(int supplierBN) {
        supplierController.showAllOrdersOfSupplier(supplierBN);
    }

    @Override
    public response showTotalAmount(int supplierBN, int orderId) {
        supplierController.showTotalAmount(supplierBN, orderId);
    }

    @Override
    public response showDeliverTime(int supplierBN, int orderId) {
        supplierController.showDeliverTime(supplierBN, orderId);
    }

    @Override
    public response updateDeliverTime(int supplierBN, int orderId, Date deliverTime){
        supplierController.updateDeliverTime(supplierBN, orderId, deliverTime);
    }

    @Override
    public Tresponse<QuantityDocument> addQuantityDocument(int supplierBN, int itemId, int minimalAmount, int discount){
        supplierController.addQuantityDocument(supplierBN, itemId, minimalAmount,discount);
    }

    @Override
    public response removeQuantityDocument(int supplierBN, int itemId) {
        supplierController.removeQuantityDocument(supplierBN , itemId);
    }

    @Override
    public response showQuantityDocument(int supplierBN, int itemId) {
        supplierController.showQuantityDocument(supplierBN, itemId);
    }

    @Override
    public response updateMinimalAmountOfQD(int supplierBN, int itemId, int minimalAmount) {
        supplierController.updateMinimalAmountOfQD(supplierBN, itemId, minimalAmount);
    }

    @Override
    public response updateDiscountOfQD(int supplierBN, int itemId, int discount) {
        supplierController.updateDiscountOfQD(supplierBN, itemId, discount);
    }

    @Override
    public response addSupplierAgreement(int supplierBN, int minimalAmount, int discount, boolean constantTime, boolean shipToUs) {
        supplierController.addSupplierAgreement(supplierBN, minimalAmount, discount, constantTime, shipToUs);
    }

    @Override
    public Tresponse<SupplierAgreement> showSupplierAgreement(int supplierBN) {
        supplierController.showSupplierAgreement(supplierBN);
    }

    @Override
    public response updateMinimalAmountOfSA(int supplierBN, int minimalAmount) {
        supplierController.updateMinimalAmountOfSA(supplierBN, minimalAmount);
    }

    @Override
    public response updateDiscountOfSA(int supplierBN, int discount) {
        supplierController.updateDiscountOfSA(supplierBN, discount);
    }

    @Override
    public response updateConstantTime(int supplierBN, boolean constantTime) {
        supplierController.updateConstantTime(supplierBN, constantTime);
    }

    @Override
    public response updateShipToUs(int supplierBN, boolean ShipToUs) {
        supplierController.updateShipToUs(supplierBN, ShipToUs);
    }
}
