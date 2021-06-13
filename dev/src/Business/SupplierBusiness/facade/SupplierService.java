
package Business.SupplierBusiness.facade;

import Business.StockBusiness.Fcade.StorageService;
import Business.StockBusiness.Fcade.outObjects.NeededReport;
import Business.SupplierBusiness.ISupplierService;
import Business.SupplierBusiness.SupplierController;
import Business.SupplierBusiness.facade.outObjects.*;
import Business.Transportation.TransportationService;
import Presentation.TransportationController;
import Utility.Tuple;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class SupplierService implements ISupplierService {
    private SupplierController supplierController;
    private StorageService stockService;
    private TransportationController transportationController;
    private int branchID;

    public SupplierService() {
        supplierController = null;
        this.branchID = branchID;
    }

    @Override
    public response LoadData() {
        try {
            supplierController = new SupplierController(true);
            stockService.loadAllStores();
        }catch(Exception e){
//            supplierController = new SupplierController();
            stockService.loadAllStores();
        }
        return new response();
    }

    public void setStockService(StorageService service){
        stockService=service;
    }


    @Override
    public response deleteData() { return new response(); }

    @Override
    public Tresponse<SupplierCard> showSupplier(int supplierBN) {
        Business.SupplierBusiness.SupplierCard supplierCard;
        try {
            supplierCard = supplierController.showSupplier(supplierBN);
        }catch (Exception e){
            return new Tresponse<>("ERROR: " + e.getMessage());
        }
        if (supplierCard != null)
            return new Tresponse<>(new SupplierCard(supplierCard));
        return new Tresponse<>("ERROR: There is no such supplier");
    }

    @Override
    public response addSupplier(String supplierName, int bankNumber , int branchNumber, int bankAccount, String payWay) {
        try{
            supplierController.addSupplier(supplierName,bankNumber ,branchNumber , bankAccount, payWay);
        }catch (Exception e){
            return new Tresponse<>("ERROR: " + e.getMessage());
        }
        return new response();
    }

    @Override
    public response removeSupplier(int removeSupplier) {
        try{
            supplierController.removeSupplier(removeSupplier);
        }catch (Exception e){
            return new response("ERROR: " + e.getMessage());
        }
        return new response();
    }

    @Override
    public Tresponse<SupplierCard> showSupplierBN(String supplierName) {
        Business.SupplierBusiness.SupplierCard supplierCard;
        try {
            supplierCard = supplierController.showSupplierBN(supplierName);
        }catch (Exception e){
            return new Tresponse<>("ERROR: " + e.getMessage());
        }
        if (supplierCard != null) {
            return new Tresponse<>(new SupplierCard(supplierCard));
        }
        return new Tresponse<>("ERROR: There is no such supplier");
    }

    @Override
    public response updateSupplierPayWay(int supplierBN, String payWay) {
        try{
            supplierController.updateSupplierPayWay(supplierBN, payWay);
        }catch (Exception e){
            return new response("ERROR: " + e.getMessage());
        }
        return new response();
    }

    @Override
    public response updateSupplierBankAccount(int supplierBN,int bankNumber , int branchNumber , int bankAccount) {
        try{
            supplierController.updateSupplierBankAccount(supplierBN,bankNumber , branchNumber , bankAccount);
        }catch (Exception e){
            return new response("ERROR: " + e.getMessage());
        }
        return new response();
    }

    @Override
    public response addContactPhone(int supplierBN, String phone, String name) {
        try{
            supplierController.addContactPhone(supplierBN, phone, name);
        }catch (Exception e){
            return new response("ERROR: " + e.getMessage());
        }
        return new response();
    }

    @Override
    public response addContactEmail(int supplierBN, String Email, String name) {
        try{
            supplierController.addContactEmail(supplierBN, Email, name);
        }catch (Exception e){
            return new response("ERROR: " + e.getMessage());
        }
        return new response();
    }

    @Override
    public response removeContactPhone(int supplierBN, String phone) {
        try{
            supplierController.removeContactPhone(supplierBN, phone);
        }catch (Exception e){
            return new response("ERROR: " + e.getMessage());
        }
        return new response();
    }

    @Override
    public response removeContactEmail(int supplierBN, String email) {
        try{
            supplierController.removeContactEmail(supplierBN, email);
        }catch (Exception e){
            return new response("ERROR: " + e.getMessage());
        }
        return new response();
    }

    @Override
    public response updateContactPhone(int supplierBN, String phone , String name) {
        try{
            supplierController.updateContactPhone(supplierBN, phone , name);
        }catch (Exception e){
            return new response("ERROR: " + e.getMessage());
        }
        return new response();
    }

    @Override
    public response updateContactEmail(int supplierBN, String email , String name) {
        try{
            supplierController.updateContactEmail(supplierBN, email , name);
        }catch (Exception e){
            return new response("ERROR: " + e.getMessage());
        }
        return new response();
    }

    @Override
    public Tresponse<List<SupplierCard>> showAllSuppliers() {
        List<Business.SupplierBusiness.SupplierCard> supplierCards;
        List<SupplierCard> outSupplierCard = new LinkedList<>();
        try {
            supplierCards = supplierController.showAllSuppliers();
            for(Business.SupplierBusiness.SupplierCard supplierCard : supplierCards){
                outSupplierCard.add(new SupplierCard(supplierCard));
            }
        }catch (Exception e){
            return new Tresponse<>("ERROR: " + e.getMessage());
        }
        if(outSupplierCard.size() == 0) return new Tresponse<>("there is no suppliers.");
        return new Tresponse<>(outSupplierCard);

    }

    @Override
    public Tresponse<List<Item>> showAllItemsOfSupplier(int SupplierBN) {
        List<Business.SupplierBusiness.Item> items;
        List<Item> outItems = new LinkedList<>();
        try {
            items = supplierController.showAllItemsOfSupplier(SupplierBN);
            for(Business.SupplierBusiness.Item item : items){
                outItems.add(new Item(item));
            }
        }catch (Exception e){
            return new Tresponse<>("ERROR: " + e.getMessage());
        }
        if(outItems.size() == 0) return new Tresponse<>("supplier does not have any items.");
        return new Tresponse<>(outItems);
    }

    @Override
    public Tresponse<Item> showItemOfSupplier(int SupplierBN, int itemId) {
        Business.SupplierBusiness.Item item;
        try{
            item = supplierController.showItemOfSupplier(SupplierBN, itemId);
        }catch (Exception e){
            return new Tresponse<>("ERROR: " + e.getMessage());
        }
        return new Tresponse<>(new Item(item));
    }

    @Override
    public Tresponse<List<Item>> showAllItemsOfOrder(int SupplierBN, int orderId) {
        List<Business.SupplierBusiness.Item> items;
        List<Item> outItems = new LinkedList<>();
        try {
            items = supplierController.showAllItemsOfOrder(SupplierBN , orderId);
            for(Business.SupplierBusiness.Item item : items){
                outItems.add(new Item(item));
            }
        }catch (Exception e){
            return new Tresponse<>("ERROR: " + e.getMessage());
        }
        if(outItems.size() == 0) return new Tresponse<>("supplier does not have any orders.");
        return new Tresponse<>(outItems);
    }

    @Override
    public Tresponse<List<Item>> showAllItems() {
        List<Business.SupplierBusiness.Item> items;
        List<Item> outItems = new LinkedList<>();
        try {
            items = supplierController.showAllItems();
            for(Business.SupplierBusiness.Item item : items){
                outItems.add(new Item(item));
            }
        }catch (Exception e){
            return new Tresponse<>("ERROR: " + e.getMessage());
        }
        if(outItems.size() == 0) return new Tresponse<>("there is np items.");
        return new Tresponse<>(outItems);
    }

    @Override
    public Tresponse<Item> addItem(int storeId , int supplierBN, String name , double basePrice , double salePrice , int min , String producer , int category, LocalDate expirationDate, double weight) {
        Business.SupplierBusiness.Item item;
        try{
            List<Business.SupplierBusiness.SupplierCard> sc=supplierController.showAllSuppliers();
            if(sc.size()==0 || sc.get(supplierBN) == null) throw new Exception("supplier BN does not exist.");
            response response1 = stockService.useStore(storeId);
            if(response1.isError()) return new Tresponse<>("ERROR: " + response1.getError());
            response response2 = stockService.addProductType(name, min , basePrice , salePrice , producer , supplierBN ,category);
            if(response2.isError()) return new Tresponse<>("ERROR: " + response2.getError());
            Tresponse<Integer> responseData = stockService.getProductTypeId(name);
            if(responseData.isError()) return new Tresponse<>("ERROR: " + responseData.getError());
            item = supplierController.addItem(responseData.getOutObject() , supplierBN,name , basePrice , expirationDate, weight);
        }catch (Exception e){
            return new Tresponse<>("ERROR: " + e.getMessage());
        }
        return new Tresponse<>(new Item(item));
    }

    @Override
    public response removeItem(int supplierBN , int itemId) {
        try{
            supplierController.removeItem(supplierBN , itemId);
            stockService.removeSupplier(supplierBN , itemId);
        }catch (Exception e){
            return new response("ERROR: " + e.getMessage());
        }
        return new response();
    }

    @Override
    public response removeItemFromRegularOrder(int supplierBN, int orderId, int itemId) {
        try{
            supplierController.removeItemFromRegularOrder(supplierBN , orderId , itemId);
        }catch (Exception e){
            return new response("ERROR: " + e.getMessage());
        }
        return new response();
    }

    @Override
    public response removeAmountItemFromRegularOrder(int supplierBN, int orderId, int itemId, int amount) {
        try{
            supplierController.removeAmountItemFromRegularOrder(supplierBN , orderId , itemId , amount);
        }catch (Exception e){
            return new response("ERROR: " + e.getMessage());
        }
        return new response();
    }

    @Override
    public Tresponse<Order> addRegularOrder(int supplierBN,int branchID, Hashtable<Integer, Integer> items) {
        Business.SupplierBusiness.Order order;
        Tuple<Business.SupplierBusiness.Order , Boolean> tuple;
        try {
            tuple = supplierController.addRegularOrder(supplierBN, branchID, items);
            order = tuple.item1;
            if(tuple.item2){
                ZoneId zone = ZoneId.systemDefault();
                for(int i = 0 ; i < order.showAllItemsOfOrder().size() ; i++) {
                    stockService.addProduct(order.showAllItemsOfOrder().get(i).getItemId(), Date.from(order.showAllItemsOfOrder().get(i).getExpirationDate().atStartOfDay(zone).toInstant()));
                }
            }
            else {
                transportationController.addOrderToTransportation(order);
            }
        }catch (Exception e){
            return new Tresponse<>("ERROR: " + e.getMessage());
        }
        return new Tresponse<>(new Order(order));
    }


    public Tresponse<NeededReport> getNeededItems(){
        NeededReport report;
        try{
            Tresponse<NeededReport> responseData = stockService.getNeededReportToOrder();
            if(responseData.isError())  return new Tresponse<>("ERROR: " + responseData.getError());
            report = responseData.getOutObject();
        }catch (Exception e){
            return new Tresponse<>("ERROR: " + e.getMessage());
        }
        return new Tresponse<>(report);
    }

    public response addNeededOrder(int itemId ,int neededAmount, int branchID) {
        Business.SupplierBusiness.Order order;
        Tuple<Business.SupplierBusiness.Order , Boolean> tuple;
        try {
            tuple = supplierController.addNeededOrder(itemId , neededAmount, branchID);
            order = tuple.item1;
            if(tuple.item2){
                ZoneId zone = ZoneId.systemDefault();
                for(int i = 0 ; i < order.showAllItemsOfOrder().size() ; i++) {
                    stockService.addProduct(order.showAllItemsOfOrder().get(i).getItemId(), Date.from(order.showAllItemsOfOrder().get(i).getExpirationDate().atStartOfDay(zone).toInstant()));
                }
            }
            else {
                transportationController.addOrderToTransportation(order);
            }
        }catch (Exception e){
            return new Tresponse<>("ERROR: " + e.getMessage());
        }
        return new Tresponse<>(new Order(order));
    }

    @Override
    public response addItemToOrder(int supplierBN, int orderId, int itemId , int amount) {
        Business.SupplierBusiness.Item item;
        Tuple<Business.SupplierBusiness.Order, Boolean> order;
        try{
            order = supplierController.addItemToOrder(supplierBN, orderId, itemId , amount);
            item = order.item1.getItem(itemId);
            ZoneId zone = ZoneId.systemDefault();
            if(order.item2){
                for(int i = 0 ; i < order.item1.showAllItemsOfOrder().size() ; i++) {
                    stockService.addProduct(order.item1.showAllItemsOfOrder().get(i).getItemId(), Date.from(order.item1.showAllItemsOfOrder().get(i).getExpirationDate().atStartOfDay(zone).toInstant()));
                }
            }
            else {
                transportationController.addOrderToTransportation(order.item1);
            }
            for(int i = 0 ; i < amount ; i++) {
                stockService.addProduct(itemId, Date.from(item.getExpirationDate().atStartOfDay(zone).toInstant()));
            }
        }catch (Exception e){
            return new Tresponse("ERROR: " + e.getMessage());
        }
        return new response();
    }

    @Override
    public boolean removeOrder() {
        Scanner scanner = new Scanner(System.in);
        Tresponse<List<SupplierCard>> suppliers = showAllSuppliers();
        if(suppliers.isError()){
            System.out.println(suppliers.getError());
            return false;
        }
        for (SupplierCard supplierCard : suppliers.getOutObject()) {
            Tresponse<List<Order>> orders = showAllOrdersOfSupplier(supplierCard.getSupplierBN());
            if(orders.isError()){
                System.out.println(orders.getError());
                return false;
            }
            for (Order order : orders.getOutObject()) {
                System.out.println(order.toString());
                Tresponse<List<Item>> items = showAllItemsOfOrder(supplierCard.getSupplierBN(), Integer.parseInt(order.toStringId()));
                if (items.isError()) {
                    System.out.println(items.getError() + "\n");
                    return false;
                }
                else {
                    Tresponse<SupplierAgreement> supplierAgreement = showSupplierAgreement(supplierCard.getSupplierBN());
                    if (supplierAgreement.isError()) {
                        System.out.println(supplierAgreement.getError() + "\n");
                        return true;
                    }
                    else {
                        System.out.println("\tship to us: " + supplierAgreement.getOutObject().toStringShipToUs());
                    }
                    List<Item> responseItem = items.getOutObject();
                    for (Item item : responseItem) {
                        System.out.println(item.toString(order.toStringAmount(item.toStringId())));
                    }
                }
            }
        }
        int toReturnSupplier;
        String answer;
        while (true) {
            System.out.println("please enter the orderId you want to delete");
            try {
                answer = read(scanner);
                toReturnSupplier = Integer.parseInt(answer);
                break;
            } catch (Exception e) {
                System.out.println("orderId does not exist or something want wrong");
            }
        }
        int toReturnOrderId;
        while (true) {
            System.out.println("please enter the supplierBN of the order you want to delete");
            try {
                answer = read(scanner);
                toReturnOrderId = Integer.parseInt(answer);
                break;
            } catch (Exception e) {
                System.out.println("supplierBN does not exist or something want wrong");
            }
        }
        return removeOrder(toReturnSupplier , toReturnOrderId);
    }

    private String read(Scanner scanner) {
        return scanner.nextLine().toLowerCase().replaceAll("\\s", "");
    }

    private boolean removeOrder(int supplierBN, int orderId) {
        try{
            int transportationId = showOrderOfSupplier(supplierBN , orderId).getOutObject().getTransportationId();
            supplierController.removeOrder(supplierBN, orderId);
            transportationController.removeOrderFromTransportation(transportationId , orderId);
        }catch (Exception e){
            return false;
        }
        return true;
    }

        @Override
    public Tresponse<Order> showOrderOfSupplier(int supplierBN, int orderId) {
        Business.SupplierBusiness.Order order;
        try {
            order = supplierController.showOrderOfSupplier(supplierBN, orderId);
        }catch (Exception e){
            return new Tresponse<>("ERROR: " + e.getMessage());
        }
        return new Tresponse<>(new Order(order));
    }

    @Override
    public Tresponse<List<Order>> showAllOrdersOfSupplier(int supplierBN) {
        List<Business.SupplierBusiness.Order> orders;
        List<Order> outOrder = new LinkedList<>();
        try {
            orders = supplierController.showAllOrdersOfSupplier(supplierBN);
            for(Business.SupplierBusiness.Order order : orders){
                outOrder.add(new Order(order));
            }
        }catch (Exception e){
            return new Tresponse<>("ERROR: " + e.getMessage());
        }
        if(outOrder.size() == 0) return new Tresponse<>("supplier does not have any orders.");
        return new Tresponse<>(outOrder);
    }

    @Override
    public Tresponse<Order> showTotalAmount(int supplierBN, int orderId) {
        Business.SupplierBusiness.Order order;
        try{
            order = supplierController.showTotalAmount(supplierBN, orderId);
        }catch (Exception e){
            return new Tresponse<>("ERROR: " + e.getMessage());
        }
        return new Tresponse<>(new Order(order));
    }

//    @Override
//    public Tresponse<Order> showDeliverTime(int supplierBN, int orderId) {
//        BusinessLayer.SupplierBusiness.Order order;
//        try{
//            order = supplierController.showDeliverTime(supplierBN, orderId);
//        }catch (Exception e){
//            return new Tresponse<>("ERROR: " + e.getMessage());
//        }
//        return new Tresponse<>(new Order(order));
//    }

//    @Override
//    public response updateDeliverTime(int supplierBN, int orderId, LocalDate deliverTime){
//        try{
//            supplierController.updateDeliverTime(supplierBN, orderId, deliverTime);
//        }catch (Exception e){
//            return new response("ERROR: " + e.getMessage());
//        }
//        return new response();
//    }

    @Override
    public response addQuantityDocument(int supplierBN, int itemId, int minimalAmount, int discount){
        try{
            supplierController.addQuantityDocument(supplierBN, itemId, minimalAmount,discount);
        }catch (Exception e){
            return new response("ERROR: " + e.getMessage());
        }
        return new response();
    }

    @Override
    public response removeQuantityDocument(int supplierBN, int itemId) {
        try{
            supplierController.removeQuantityDocument(supplierBN , itemId);
        }catch (Exception e){
            return new response("ERROR: " + e.getMessage());
        }
        return new response();
    }

    @Override
    public Tresponse<QuantityDocument> showQuantityDocument(int supplierBN, int itemId) {
        Business.SupplierBusiness.QuantityDocument quantityDocument;
        try {
            quantityDocument =  supplierController.showQuantityDocument(supplierBN, itemId);
        }catch (Exception e){
            return new Tresponse<>("ERROR: " + e.getMessage());
        }
        return new Tresponse<>(new QuantityDocument(quantityDocument));
    }

    @Override
    public response updateMinimalAmountOfQD(int supplierBN, int itemId, int minimalAmount) {
        try{
            supplierController.updateMinimalAmountOfQD(supplierBN, itemId, minimalAmount);
        }catch (Exception e){
            return new response("ERROR: " + e.getMessage());
        }
        return new response();
    }

    @Override
    public response updateDiscountOfQD(int supplierBN, int itemId, int discount) {
        try{
            supplierController.updateDiscountOfQD(supplierBN, itemId, discount);
        }catch (Exception e){
            return new response("ERROR: " + e.getMessage());
        }
        return new response();
    }

    @Override
    public response addSupplierAgreement(int supplierBN, int minimalAmount, int discount, boolean constantTime, boolean shipToUs) {
        try{
            supplierController.addSupplierAgreement(supplierBN, minimalAmount, discount, constantTime, shipToUs);
        }catch (Exception e){
            return new response("ERROR: " + e.getMessage());
        }
        return new response();
    }

    @Override
    public Tresponse<SupplierAgreement> showSupplierAgreement(int supplierBN) {
        Business.SupplierBusiness.SupplierAgreement supplierAgreement;
        try {
            supplierAgreement = supplierController.showSupplierAgreement(supplierBN);
        }catch (Exception e){
            return new Tresponse<>("ERROR: " + e.getMessage());
        }
        if (supplierAgreement != null) {
            return new Tresponse<>(new SupplierAgreement(supplierAgreement));
        }
        return new Tresponse<>("ERROR: There is no such supplier agreement");
    }

    @Override
    public response updateMinimalAmountOfSA(int supplierBN, int minimalAmount) {
        try{
            supplierController.updateMinimalAmountOfSA(supplierBN, minimalAmount);
        }catch (Exception e){
            return new response("ERROR: " + e.getMessage());
        }
        return new response();
    }

    @Override
    public response updateDiscountOfSA(int supplierBN, int discount) {
        try{
            supplierController.updateDiscountOfSA(supplierBN, discount);
        }catch (Exception e){
            return new response("ERROR: " + e.getMessage());
        }
        return new response();
    }

    @Override
    public response updateConstantTime(int supplierBN, boolean constantTime) {
        try{
            supplierController.updateConstantTime(supplierBN, constantTime);
        }catch (Exception e){
            return new response("ERROR: " + e.getMessage());
        }
        return new response();
    }

    @Override
    public response updateShipToUs(int supplierBN, boolean ShipToUs) {
        try{
            supplierController.updateShipToUs(supplierBN, ShipToUs);
        }catch (Exception e){
            return new response("ERROR: " + e.getMessage());
        }
        return new response();
    }

    @Override
    public response updatePrice(int supplierBN, int itemId, double price) {
        try{
            supplierController.updatePrice(supplierBN , itemId , price);
        }catch (Exception e){
            return new response("ERROR: " + e.getMessage());
        }
        return new response();
    }

    public Tresponse<List<Business.SupplierBusiness.Order>> getOrdersByTransportation(int transportationID) {
        List<Business.SupplierBusiness.Order> outOrder = new LinkedList<>();
        try {
            outOrder = supplierController.getOrdersByTransportation(transportationID);
        }
        catch (Exception e) {
            return new Tresponse<>("ERROR: " + e.getMessage());
        }
        return new Tresponse<>(outOrder);
    }

    public response removeOrdersByTransport(int transportationID) {
        try{
            supplierController.removeOrdersByTransport(transportationID);
        }catch (Exception e){
            return new response("ERROR: " + e.getMessage());
        }
        return new response();
    }

    public void newData() {
        supplierController = new SupplierController();
    }
}
