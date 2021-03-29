package BussniesLayer;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Order {
    private int orderId;
    private int supplierBN;
    private List<Item> items;
    private int totalAmount;
    private Date deliverTime;

    public Order(int orderId , int supplierBN , int totalAmount , Date deliverTime){
        this.orderId = orderId;
        this.supplierBN = supplierBN;
        items = new LinkedList<>();
        this.totalAmount = totalAmount;
        this.deliverTime = deliverTime;
    }

    public void addItemToOrder(int supplierBN, int orderId, int itemId) {
    }

    public void showTotalAmount(int supplierBN, int orderId) {
    }

    public void showDeliverTime(int supplierBN, int orderId) {
    }

    public void updateDeliverTime(int supplierBN, int orderId, Date deliverTime) {
    }
}
