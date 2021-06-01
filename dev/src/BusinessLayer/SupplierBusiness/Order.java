package BusinessLayer.SupplierBusiness;

import DAL.DALObject;
import DAL.DalSuppliers.DalItem;
import DAL.DalSuppliers.DalOrder;
import DAL.Mapper;
import Utility.Tuple;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

public class Order {
    protected Hashtable<Item , Integer> items;
    protected DalOrder dalOrder;
    final static Logger log=Logger.getLogger(Order.class);

    public Order(int supplierBN, int orderId , int branchId , int orderType){
        List<Tuple<Object,Class>> list=new ArrayList<>();
        list.add(new Tuple<>(orderId,Integer.class));
        list.add(new Tuple<>(supplierBN,Integer.class));
        list.add(new Tuple<>(0.0,Double.class));
        list.add(new Tuple<>(branchId,Integer.class));
        list.add(new Tuple<>(orderType,Integer.class));
        list.add(new Tuple<>(0.0,Double.class));
        list.add(new Tuple<>(-1,Integer.class));
        Mapper map=Mapper.getMap();
        map.setItem(DalOrder.class,list);
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
        }
        items = new Hashtable<>();
    }

    public Order getOrder(int orderID) {
        Order retOrder;
        Mapper map=Mapper.getMap();
        List<Integer> keyList=new ArrayList<>();
        keyList.add(orderID);
        DALObject check =map.getItem(DalOrder.class ,keyList);
        if (check==null ||(check.getClass()!=DalOrder.class)){
            String s="the instance that return from Mapper is null";
            log.warn(s);
            throw new IllegalArgumentException(s);
        }
        else{
            log.info("create new Object");
            retOrder = new Order(dalOrder);
        }
        return retOrder;
    }

    public Order(DalOrder dalOrder) {
        this.dalOrder = dalOrder;
        loadItemsOfOrders();
    }

    public void updateTransportation(int tranID) {
        dalOrder.updateTransportation(tranID);
    }

    private void loadItemsOfOrders() {
        items = new Hashtable<>();
        List<Tuple<List<Class>,List<Object>>> list1 = dalOrder.loadItems();
        if (list1.size() > 0) {
            for (int i = 1; i < list1.get(0).item2.size(); i = i + 4) {
                int key = (int) list1.get(0).item2.get(i);
                int amount =(int) list1.get(0).item2.get(i+1);
                Mapper map = Mapper.getMap();
                List<Integer> keyList = new ArrayList<>();
                keyList.add(key);
                DALObject check = map.getItem(DalItem.class, keyList);
                if (DalItem.class == null || check == null || (check.getClass() != DalItem.class)) {
                    String s = "the instance that return from Mapper is null";
                    log.warn(s);
                    throw new IllegalArgumentException(s);
                } else {
                    log.info("loaded new Object");
                    Item item = new Item((DalItem) check);
                    items.put(item, amount);
                }
            }
        }
    }

    public String toString() {
        return "Order: \n" +
                "\torderId: " + dalOrder.getOrderID() + "\n" +
                "\ttotal amount: " + dalOrder.getTotalAmount() + "\n" +
                "\tbranchId: " + dalOrder.getBranchID();
    }

    public List<Item> showAllItemsOfOrder(){
        return new LinkedList<>(items.keySet());
    }

    public Order showDeliverTime() { return this; }

    public int getOrderId() { return dalOrder.getOrderID(); }

    public double getTotalAmount() {
        return dalOrder.getTotalAmount();
    }


    public int getBranchID() {
        return dalOrder.getBranchID();
    }

    public Hashtable<Integer, Integer> getAmounts() {
        Hashtable<Integer , Integer> amounts = new Hashtable<>();
        for(Item item : items.keySet()){
            amounts.put(item.getItemId() , items.get(item));
        }
        return amounts;
    }

    public void removeOrder() {
        dalOrder.removeOrder();
    }

    public int getOrderType() {return dalOrder.getOrderType();}

    public int getSupplierBN() { return dalOrder.getSupplierBN(); }


    public regularOrder removeItemFromRegularOrder(Item item) {
        items.remove(item);
    }
}
