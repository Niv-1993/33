package BussniesLayer;

import java.time.LocalDate;
import java.util.*;

public class Order {
    private final int orderId;
    private Hashtable<Item , Integer> items;
    private double totalAmount;
    private LocalDate deliverTime;

    public Order(int orderId , LocalDate deliverTime){
        this.orderId = orderId;
        items = new Hashtable<>();
        this.totalAmount = 0;
        this.deliverTime = deliverTime;
    }

    public void addItemToOrder(Item item , int amount) throws Exception {
        if(amount < 1) throw new Exception("amount must be at least 1");
        if (items.get(item) != null) items.put(item, items.get(item) + amount);
        else items.put(item, amount);
        QuantityDocument qd = item.getQuantityDocument();
        if (qd == null) throw new Exception("quantity document does not exist.");
        totalAmount = totalAmount + item.getPrice()*amount;
        if (qd.getMinimalAmount() <= items.get(item)) {
            double discount = qd.getDiscount()/100.0;
            totalAmount = totalAmount - item.getPrice()*discount*amount;
        }
    }

    public List<Item> showAllItemsOfOrder(){
        return new LinkedList<>(items.keySet());
    }

    public Order showDeliverTime() { return this; }

    public void updateDeliverTime(LocalDate deliverTime) throws Exception {
        if(deliverTime.isAfter(LocalDate.now())) this.deliverTime = deliverTime;
        else throw new Exception("deliver time must be after current time");
    }

    public int getOrderId() {
        return orderId;

    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public LocalDate getDeliverTime() {
        return deliverTime;
    }

    public void updateTotalAmount(double totalAmount){
        this.totalAmount = totalAmount;
    }
}
