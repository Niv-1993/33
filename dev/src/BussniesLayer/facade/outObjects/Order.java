package BussniesLayer.facade.outObjects;

import java.util.Date;

public class Order {
    private final int orderId;
    private final double totalAmount;
    private final Date deliverTime;

    public Order(BussniesLayer.Order order) {
        orderId = order.getOrderId();
        totalAmount = order.getTotalAmount();
        deliverTime = order.getDeliverTime();
    }

    @Override
    public String toString() {
        return "Order: \n" +
                "\torderId = " + orderId + "\n" +
                "\ttotalAmount = " + totalAmount + "\n" +
                "\tdeliverTime = " + deliverTime + "\n";
    }

    public String toStringId(){
        return orderId + "";
    }

    public String toStringTotalAmount(){ return totalAmount + "\n";}

    public String toStringDeliverTime(){ return deliverTime.toString();}
}
