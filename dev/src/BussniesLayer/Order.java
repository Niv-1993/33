package BussniesLayer;

import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.LinkedList;

public class Order {
    private int orderId;
    private int supplierBN;
    private Hashtable<Item , Integer> items;
    private double totalAmount;
    private Date deliverTime;

    public Order(int orderId , int supplierBN , Date deliverTime){
        this.orderId = orderId;
        this.supplierBN = supplierBN;
        items = new Hashtable<Item , Integer>();
        this.totalAmount = 0;
        this.deliverTime = deliverTime;
    }

    public void addItemToOrder(int itemId) {
        for(Item item : items.keySet()) {
            if(item.getItemId() == itemId) items.put(item , items.get(item) + 1);
        }
    }

    public double showTotalAmount() {
        for(Item item : items.keySet()) {
            QuantityDocument qd = item.getQuantityDocument();
            totalAmount = totalAmount + item.getPrice();
            if(qd.getMinimalAmount() <= items.get(item)) {
                totalAmount = totalAmount - (item.getPrice()*(qd.getDiscount() / 100));
            }
        }
        return totalAmount;
    }

    public Date showDeliverTime() {
        return deliverTime;
    }

    public void updateDeliverTime(Date deliverTime) {
        this.deliverTime = deliverTime;
    }

    public int getOrderId() {
        return orderId;
    }

    public void addItem(Item item) {
        items.put(item , 1);
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public Date getDeliverTime() {
        return deliverTime;
    }
}
