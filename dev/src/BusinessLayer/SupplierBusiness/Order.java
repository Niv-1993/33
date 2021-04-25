package BussniesLayer;

import java.time.LocalDate;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

public class Order {
    protected int orderId;
    protected Hashtable<Item , Integer> items;
    protected double totalAmount;
    protected LocalDate deliverTime;
    protected int branchId;

    public Order(int orderId , LocalDate deliverTime , int branchId){
        this.orderId = orderId;
        items = new Hashtable<>();
        this.totalAmount = 0;
        this.deliverTime = deliverTime;
        this.branchId=branchId;
    }

    public Order(){}
    //remove Item from Order.

    public List<Item> showAllItemsOfOrder(){
        return new LinkedList<>(items.keySet());
    }

    public Order showDeliverTime() { return this; }

    public int getOrderId() { return orderId; }

    public double getTotalAmount() {
        return totalAmount;
    }

    public LocalDate getDeliverTime() {
        return deliverTime;
    }

    public int getBranchID() {
        return branchId;
    }

    public Hashtable<Integer, Integer> getAmounts() {
        Hashtable<Integer , Integer> amounts = new Hashtable<>();
        for(Item item : items.keySet()){
            amounts.put(item.getItemId() , items.get(item));
        }
        return amounts;
    }
}
