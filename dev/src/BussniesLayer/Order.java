package BussniesLayer;

import java.util.*;

public class Order {
    private final int orderId;
    private Hashtable<Item , Integer> items;
    private double totalAmount;
    private Date deliverTime;

    public Order(int orderId , Date deliverTime){
        this.orderId = orderId;
        items = new Hashtable<>();
        this.totalAmount = 0;
        this.deliverTime = deliverTime;
    }

    public void addItemToOrder(Item item , int amount) {
        if(items.get(item) != null) items.put(item , items.get(item) + amount);
        items.put(item , amount);
    }

    public Order showTotalAmount() throws Exception {
        for(Item item : items.keySet()) {
            QuantityDocument qd = item.getQuantityDocument();
            if(qd == null) throw new Exception("quantity document does not exist.");
            totalAmount = totalAmount + item.getPrice();
            if(qd.getMinimalAmount() <= items.get(item)) {
                double present = qd.getDiscount() / 100;
                totalAmount = totalAmount - (item.getPrice()*present);
            }
        }
        return this;
    }

    public List<Item> showAllItemsOfOrder(){
        return new LinkedList<>(items.keySet());
    }

    public Order showDeliverTime() { return this; }

    public void updateDeliverTime(Date deliverTime) throws Exception {
        if(deliverTime.after(new Date())) this.deliverTime = deliverTime;
        throw new Exception("deliver time must be after current time");
    }

    public int getOrderId() {
        return orderId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public Date getDeliverTime() {
        return deliverTime;
    }
}
