package BussniesLayer;

import java.util.*;

public class Order {
    private final int orderId;
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

    public void addItemToOrder(Item item) {
        if(items.get(item) != null) items.put(item , items.get(item) + 1);
        items.put(item , 1);
    }

    public Order showTotalAmount() throws Exception {
        for(Item item : items.keySet()) {
            QuantityDocument qd = item.getQuantityDocument();
            if(qd == null) throw new Exception("quantity document does not exist.");
            totalAmount = totalAmount + item.getPrice();
            if(qd.getMinimalAmount() <= items.get(item)) {
                double precent = qd.getDiscount() / 100;
                totalAmount = totalAmount - (item.getPrice()*precent);
            }
        }
        return this;
    }

    public Order showDeliverTime() { return this; }

    public void updateDeliverTime(Date deliverTime) throws Exception {
        if(deliverTime.after(new Date())) this.deliverTime = deliverTime;
        throw new Exception("deliver time must be after current time");
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
