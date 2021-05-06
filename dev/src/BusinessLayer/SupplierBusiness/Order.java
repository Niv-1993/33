package BusinessLayer.SupplierBusiness;

import DAL.DALObject;
import DAL.DalSuppliers.DalOrder;
import DAL.DalSuppliers.DalSupplierCard;
import DAL.Mapper;
import Utility.Tuple;
import Utility.Util;
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

    public Order(int supplierBN, int orderId , LocalDate deliverTime , int branchId){
        List<Tuple<Object,Class>> list=new ArrayList<>();
        list.add(new Tuple<>(orderId,Integer.class));
        list.add(new Tuple<>(supplierBN,Integer.class));
        list.add(new Tuple<>(0.0,Double.class));
        list.add(new Tuple<>(deliverTime.toString(),String.class));
        list.add(new Tuple<>(branchId,Integer.class));
        Mapper map=Mapper.getMap();
        map.setItem(DalOrder.class,list);
        List<Integer> keyList=new ArrayList<>();
        keyList.add(orderId);
        DALObject check =map.getItem(DalOrder.class ,keyList);
        if (DalOrder.class==null || check==null ||(check.getClass()!=DalOrder.class)){
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

    public List<Item> showAllItemsOfOrder(){
        return new LinkedList<>(items.keySet());
    }

    public Order showDeliverTime() { return this; }

    public int getOrderId() { return dalOrder.getOrderID(); }

    public double getTotalAmount() {
        return dalOrder.getTotalAmount();
    }

    public LocalDate getDeliverTime() {
        return LocalDate.parse(dalOrder.getDeliverTime());
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
}
