package BussniesLayer;

import BussniesLayer.facade.response;
import BussniesLayer.facade.Tresponse;
import BussniesLayer.facade.outObjects.SupplierCard;
import BussniesLayer.facade.outObjects.Item;
import BussniesLayer.facade.outObjects.Order;
import BussniesLayer.facade.outObjects.QuantityDocument;
import BussniesLayer.facade.outObjects.SupplierAgreement;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;


public interface ISupplierService {
    response LoadData();
    response deleteData();
    Tresponse<SupplierCard> showSupplier(int supplierBN);
    response addSupplier(String supplierName , int bankNumber , int BrunchNumber , int bankAccount , String payWay);
    response removeSupplier(int supplierBN);
    Tresponse<SupplierCard> showSupplierBN(String supplierName);
    response updateSupplierPayWay(int supplierBN , String payWay);
    response updateSupplierBankAccount(int supplierBN ,int bankNumber , int branchNumber  , int bankAccount);
    response addContactPhone(int supplierBN , String phone , String name);
    response addContactEmail(int supplierBN , String Email , String name);
    response removeContactPhone(int supplierBN , String phone);
    response removeContactEmail(int supplierBN , String email);
    response updateContactPhone(int supplierBN , String phone , String name);
    response updateContactEmail(int supplierBN , String email , String name);
    Tresponse<List<SupplierCard>> showAllSuppliers();
    Tresponse<List<Item>> showAllItemsOfSupplier(int SupplierBN);
    Tresponse<Item> showItemOfSupplier(int SupplierBN , int itemId);
    Tresponse<List<Item>> showAllItemsOfOrder(int SupplierBN , int orderId);
    Tresponse<List<Item>> showAllItems();
    Tresponse<Item> addItem(int supplierBN , String category , String name, double price);
    response removeItem(int itemId);
    Tresponse<Order> addOrder(int supplierBN);
    response addItemToOrder(int supplierBN , int orderId , int itemId , int amount);
    Tresponse<Order> showOrderOfSupplier(int supplierBN , int orderId);
    Tresponse<List<Order>> showAllOrdersOfSupplier(int supplierBN);
    response showTotalAmount(int supplierBN , int orderId);
    response showDeliverTime(int supplierBN , int orderId);
    response updateDeliverTime(int supplierBN , int orderId , LocalDate deliverTime);
    response addQuantityDocument(int supplierBN , int itemId , int minimalAmount , int discount);
    response removeQuantityDocument(int supplierBN , int itemId);
    Tresponse<QuantityDocument> showQuantityDocument(int supplierBN , int itemId);
    response updateMinimalAmountOfQD(int supplierBN , int itemId , int minimalAmount);
    response updateDiscountOfQD(int supplierBN , int itemId , int discount);
    response addSupplierAgreement(int supplierBN , int minimalAmount , int discount , boolean constantTime ,boolean shipToUs);
    Tresponse<SupplierAgreement> showSupplierAgreement(int supplierBN);
    response updateMinimalAmountOfSA(int supplierBN , int minimalAmount);
    response updateDiscountOfSA(int supplierBN , int discount);
    response updateConstantTime(int supplierBN , boolean constantTime);
    response updateShipToUs(int supplierBN , boolean ShipToUs);
    response updatePrice(int supplierBN , int itemId , double Price);
}
