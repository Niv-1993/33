package BusinessLayer.SupplierBusiness.facade;

import BusinessLayer.SupplierBusiness.*;
import DAL.DALObject;
import DAL.DalSuppliers.DalOrderController;
import DAL.DalSuppliers.DalSupplierController;
import DAL.Mapper;
import Utility.Tuple;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.util.*;

public class OrderController {
    private Dictionary<Integer , Order> orders;
    private DalOrderController dalOrderController;
    private SupplierController supplierController;
    final static Logger log=Logger.getLogger(OrderController.class);

    public OrderController(int branchID, SupplierController supplierController){
        List<Tuple<Object,Class>> list=new ArrayList<>();
        list.add(new Tuple<>(branchID,Integer.class));
        list.add(new Tuple<>(1,Integer.class));
        Mapper map=Mapper.getMap();
        map.setItem(DalOrderController.class,list);
        List<Integer> keyList=new ArrayList<>();
        DALObject check =map.getItem(DalOrderController.class ,keyList);
        if (check==null ||(check.getClass()!=DalOrderController.class)){
            String s="the instance that return from Mapper is null";
            log.warn(s);
            throw new IllegalArgumentException(s);
        }
        else{
            log.info("create new Object");
            dalOrderController = (DalOrderController) check;
        }
        orders = new Hashtable<>();
        this.supplierController = supplierController;
    }

    public Order addRegularOrder(int supplierBN, int branchID, Hashtable<Integer, Integer> items) throws Exception {
//        SupplierCard supplierCard = suppliers.get(supplierBN);
//        if(supplierCard == null) throw new Exception("supplier BN does not exist.");
        Order order;
        try {
            order = new regularOrder(supplierBN, dalOrderController.getNumOfOrders(), branchID);
            ///tuple = suppliers.get(supplierBN).addRegularOrder(dalOrderController.getNumOfOrders(), branchID, items);
            orders.put(dalOrderController.getNumOfOrders(), order);
            dalOrderController.addNumOfOrders();
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return order;
    }

    public Order addNeededOrder(int itemId, int neededAmount, int branchID) throws Exception {
        Order order;
        Item item = null;
        double bestPrice = Integer.MAX_VALUE;
        int bestSupplier = 0;
        try {
            Enumeration<SupplierCard> enumeration = supplierController.getSuppliers().elements();
            while(enumeration.hasMoreElements()) {
                SupplierCard temp = enumeration.nextElement();
                List<Item> items = temp.getSupplierItems();
                for (Item i : items) {
                    if (i.getItemId() == itemId) {
                        double currentPrice = i.getPrice()*neededAmount;
                        if (neededAmount >= i.getQuantityDocument().getMinimalAmount()) {
                            currentPrice = currentPrice * (100-i.getQuantityDocument().getDiscount()) /100;
                        }
                        if (currentPrice < bestPrice) {
                            bestSupplier = temp.getSupplierBN();
                        }
                        item = i;
                    }
                }
            }
            order = supplierController.getSuppliers().get(bestSupplier).addNeededOrder(dalOrderController.getNumOfOrders(), branchID, item, neededAmount);
            dalOrderController.addNumOfOrders();
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return order;
    }

    public Item addItemToOrder(int supplierBN, int orderId, int itemId, int amount) throws Exception {
        ///if(supplierCard == null) throw new Exception("supplier BN does not exist.");
        ///////////NEED TO THINK WHAT TO DO
        try{
            Order order = orders.get(orderId);
            ///Order order = supplierCard.showOrderOfSupplier(orderId);
            if(order.getOrderType() == 1) throw new Exception("you cannot add new items to needed order");
            regularOrder regularOrder = (BusinessLayer.SupplierBusiness.regularOrder) order;
            return (regularOrder) regularOrder.addItemToOrder(itemId , amount);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public void removeOrder(int supplierBN, int orderId) throws Exception {
        ////SupplierCard supplierCard = suppliers.get(supplierBN);
        ////if(supplierCard == null) throw new Exception("supplier BN does not exist.");
        try{
            orders.remove(orderId);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public Order showOrderOfSupplier(int supplierBN, int orderId) throws Exception {
//        SupplierCard supplierCard = suppliers.get(supplierBN);
//        if(supplierCard == null) throw new Exception("supplier BN does not exist.");
        try{
            return supplierController.getSuppliers().get(supplierBN).showOrderOfSupplier(orderId);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public List<Order> showAllOrdersOfSupplier(int supplierBN) {
//        SupplierCard supplierCard = suppliers.get(supplierBN);
//        if(supplierCard == null) throw new Exception("supplier BN does not exist.");
        return supplierController.getSuppliers().get(supplierBN).showAllOrdersOfSupplier();
    }

    public Order showTotalAmount(int supplierBN, int orderId) {
//        SupplierCard supplierCard = suppliers.get(supplierBN);
//        if(supplierCard == null) throw new Exception("supplier BN does not exist.");
        return orders.get(orderId);
    }

    public Order showDeliverTime(int supplierBN, int orderId) throws Exception {
//        SupplierCard supplierCard = suppliers.get(supplierBN);
//        if(supplierCard == null) throw new Exception("supplier BN does not exist.");
        try {
            return orders.get(orderId).showDeliverTime();
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public void updateDeliverTime(int supplierBN, int orderId, LocalDate deliverTime) throws Exception {
        SupplierCard supplierCard = supplierController.getSuppliers().get(supplierBN);
        if(supplierCard == null) throw new Exception("supplier BN does not exist.");
        try {
            supplierController.getSuppliers().get(supplierBN).updateDeliverTime(orderId, deliverTime);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

}
