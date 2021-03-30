package BussniesLayer.facade.outObjects;

import java.util.Date;

public class Order {
    private int orderId;
    private double totalAmount;
    private Date deliverTime;

    public Order(BussniesLayer.Order order) {
        orderId = order.getOrderId();
        totalAmount = order.getTotalAmount();
        deliverTime = order.getDeliverTime();
    }
}
