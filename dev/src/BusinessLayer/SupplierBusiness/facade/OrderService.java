package BusinessLayer.SupplierBusiness.facade;

import BusinessLayer.StockBusiness.Fcade.StorageService;
import BusinessLayer.SupplierBusiness.IOrderService;
import BusinessLayer.SupplierBusiness.SupplierController;
import BusinessLayer.SupplierBusiness.facade.outObjects.Order;
import DAL.DALObject;
import DAL.DalSuppliers.DalOrder;
import DAL.Mapper;
import Utility.Tuple;
import org.apache.log4j.Logger;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

public class OrderService implements IOrderService {

    private Hashtable<Integer, OrderController> orderControllers;
    private StorageService stockService;
    private int branchID;
    final static Logger log=Logger.getLogger(OrderService.class);

    public OrderService(int branchID) {
        orderControllers = new Hashtable<>();
        this.branchID = branchID;
    }

    @Override
    public response LoadData() {
            //////////////////////////////////////
            //NEED TP WRITE THIS FUNCTION/////////
            //////////////////////////////////////
        return new response();
    }

    @Override
    public Tresponse<Order> addRegularOrder(int supplierBN, int branchId, Hashtable<Integer, Integer> items) {
        BusinessLayer.SupplierBusiness.Order order;
        Tuple<BusinessLayer.SupplierBusiness.Order , Boolean> tuple;
        try {
            order = orderControllers.get(branchID).addRegularOrder(supplierBN, branchID, items);
//            if(tuple.item2){
//                ZoneId zone = ZoneId.systemDefault();
//                for(int i = 0 ; i < order.showAllItemsOfOrder().size() ; i++) {
//                    stockService.addProduct(order.showAllItemsOfOrder().get(i).getItemId(), Date.from(order.showAllItemsOfOrder().get(i).getExpirationDate().atStartOfDay(zone).toInstant()));
//                }
//            }
        }catch (Exception e){
            return new Tresponse<>("ERROR: " + e.getMessage());
        }
        return new Tresponse<>(new Order(order));
    }

    @Override
    public response addNeededOrder(int itemId, int neededAmount, int branchID) {
        BusinessLayer.SupplierBusiness.Order order;
        try {
            order = orderControllers.get(branchID).addNeededOrder(itemId , neededAmount, branchID);
            if (order == null) {
                return new Tresponse<>("ERROR: unsuccessful adding");
            }else{
                ZoneId zone = ZoneId.systemDefault();
                for(int i = 0 ; i < neededAmount ; i++) {
                    stockService.addProduct(order.showAllItemsOfOrder().get(i).getItemId(), Date.from(order.showAllItemsOfOrder().get(i).getExpirationDate().atStartOfDay(zone).toInstant()));
                }
            }
        }catch (Exception e){
            return new Tresponse<>("ERROR: " + e.getMessage());
        }
        return new Tresponse<>(new Order(order));
    }

    @Override
    public response addItemToOrder(int supplierBN, int orderId, int itemId, int amount) {
        BusinessLayer.SupplierBusiness.Item item;
        try{
            item = orderControllers.get(branchID).addItemToOrder(supplierBN, orderId, itemId , amount);
            ZoneId zone = ZoneId.systemDefault();
            for(int i = 0 ; i < amount ; i++) {
                stockService.addProduct(item.getItemId(), Date.from(item.getExpirationDate().atStartOfDay(zone).toInstant()));
            }
        }catch (Exception e){
            return new response("ERROR: " + e.getMessage());
        }
        return new response();
    }

    @Override
    public response removeOrder(int supplierBN, int orderId) {
        try{
            orderControllers.get(branchID).removeOrder(supplierBN, orderId);
        }catch (Exception e){
            return new response("ERROR: " + e.getMessage());
        }
        return new response();
    }

    @Override
    public Tresponse<Order> showOrderOfSupplier(int supplierBN, int orderId) {
        BusinessLayer.SupplierBusiness.Order order;
        try {
            order = orderControllers.get(branchID).showOrderOfSupplier(supplierBN, orderId);
        }catch (Exception e){
            return new Tresponse<>("ERROR: " + e.getMessage());
        }
        return new Tresponse<>(new Order(order));
    }

    @Override
    public Tresponse<List<Order>> showAllOrdersOfSupplier(int supplierBN) {
        List<BusinessLayer.SupplierBusiness.Order> orders;
        List<Order> outOrder = new LinkedList<>();
        try {
            orders = orderControllers.get(branchID).showAllOrdersOfSupplier(supplierBN);
            for(BusinessLayer.SupplierBusiness.Order order : orders){
                outOrder.add(new Order(order));
            }
        }catch (Exception e){
            return new Tresponse<>("ERROR: " + e.getMessage());
        }
        if(outOrder.size() == 0) return new Tresponse<>("supplier does not have any orders.");
        return new Tresponse<>(outOrder);
    }

    @Override
    public response showTotalAmount(int supplierBN, int orderId) {
        BusinessLayer.SupplierBusiness.Order order;
        try{
            order = orderControllers.get(branchID).showTotalAmount(supplierBN, orderId);
        }catch (Exception e){
            return new Tresponse<>("ERROR: " + e.getMessage());
        }
        return new Tresponse<>(new Order(order));
    }

    @Override
    public response showDeliverTime(int supplierBN, int orderId) {
        BusinessLayer.SupplierBusiness.Order order;
        try{
            order = orderControllers.get(branchID).showDeliverTime(supplierBN, orderId);
        }catch (Exception e){
            return new Tresponse<>("ERROR: " + e.getMessage());
        }
        return new Tresponse<>(new Order(order));
    }

    @Override
    public response updateDeliverTime(int supplierBN, int orderId, LocalDate deliverTime) {
        try{
            orderControllers.get(branchID).updateDeliverTime(supplierBN, orderId, deliverTime);
        }catch (Exception e){
            return new response("ERROR: " + e.getMessage());
        }
        return new response();
    }

    @Override
    public Tresponse<Order> getOrder(int orderId) {
        DalOrder dalOrder;
        BusinessLayer.SupplierBusiness.Order retOrder;
        Mapper map=Mapper.getMap();
        List<Integer> keyList=new ArrayList<>();
        keyList.add(orderId);
        DALObject check =map.getItem(DalOrder.class ,keyList);
        if (check==null ||(check.getClass()!=DalOrder.class)){
            String s="the instance that return from Mapper is null";
            log.warn(s);
            throw new IllegalArgumentException(s);
        }
        else{
            log.info("create new Object");
            dalOrder = (DalOrder) check;
            retOrder = new BusinessLayer.SupplierBusiness.Order(dalOrder);
        }
        return new Tresponse(retOrder);
    }

    @Override
    public Tresponse<List<Order>> getOrdersByTransportation(int transportationID) {
        return null;
    }

    @Override
    public Tresponse<Order> addOrderToTransportation(BusinessLayer.SupplierBusiness.Order Order) {
        return null;
    }

    public void newData() {
        orderControllers.put(1, new OrderController(1));
        orderControllers.put(2, new OrderController(2));
        orderControllers.put(3, new OrderController(3));
        orderControllers.put(4, new OrderController(4));
        orderControllers.put(5, new OrderController(5));
        orderControllers.put(6, new OrderController(6));
        orderControllers.put(7, new OrderController(7));
        orderControllers.put(8, new OrderController(8));
        orderControllers.put(9, new OrderController(9));
    }
}
