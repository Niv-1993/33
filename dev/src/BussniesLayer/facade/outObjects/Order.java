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

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", totalAmount=" + totalAmount +
                ", deliverTime=" + deliverTime +
                '}';
    }

    public String toStringId(){
        return orderId + "";
    }
}
