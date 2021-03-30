package BussniesLayer;

import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.LinkedList;

public class Order {
    private int orderId;
    private int supplierBN;
    private Dictionary<Item , Integer> items;
    private double totalAmount;
    private Date deliverTime;

    public Order(int orderId , int supplierBN , Date deliverTime){
        this.orderId = orderId;
        this.supplierBN = supplierBN;
        items = new Hashtable<Item , Integer>();
        this.totalAmount = 0;
        this.deliverTime = deliverTime;
    }

    public void addItemToOrder(int itemId) { }

    public void showTotalAmount(int supplierBN, int orderId) {
        while(items.keys().hasMoreElements()){
            Item item = items.keys().nextElement();
            QuantityDocument qd = item.getQuantityDocument();
            totalAmount = totalAmount + item.getPrice();
            if(qd.getMinimalAmount() <= items.get(item)) {
                totalAmount = totalAmount - (item.getPrice()*(qd.getDiscount() / 100));
            }
        }
    }

    public void showDeliverTime(int supplierBN, int orderId) {
    }

    public void updateDeliverTime(Date deliverTime) {
        this.deliverTime = deliverTime;
    }

    public int getOrderId() {
        return orderId;
    }

    public void addItem(Item item) {
        int numOfItem = items.get(item) + 1;
        items.put(item , numOfItem);
    }
}
