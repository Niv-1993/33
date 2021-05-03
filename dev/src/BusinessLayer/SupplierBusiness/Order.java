package BusinessLayer.SupplierBusiness;

import DAL.DalSuppliers.DalOrder;

import java.time.LocalDate;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

public class Order {
    protected Hashtable<Item , Integer> items;
    private DalOrder dalOrder;

    public Order(int orderId , LocalDate deliverTime , int branchId){
        items = new Hashtable<>();
        dalOrder = new DalOrder();
    }

    public Order(){}
    //remove Item from Order.

    public List<Item> showAllItemsOfOrder(){
        return new LinkedList<>(items.keySet());
    }

    public Order showDeliverTime() { return this; }

    public int getOrderId() { return dalOrder.getOrderID(); }

    public double getTotalAmount() {
        return dalOrder.getTotalAmount();
    }

    public String getDeliverTime() {
        return dalOrder.getDeliverTime();
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
}
