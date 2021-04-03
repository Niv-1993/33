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
        return "Order: \n" +
                "orderId = " + orderId + "\n" +
                "totalAmount = " + totalAmount + "\n" +
                ", deliverTime = " + deliverTime + "\n";
    }

    public String toStringId(){
        return orderId + "";
    }

    public String toStringTotalAmount(){ return totalAmount + "";}

    public String toStringDeliverTime(){ return deliverTime.toString();}
}
