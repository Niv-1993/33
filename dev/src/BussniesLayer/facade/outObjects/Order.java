package BussniesLayer.facade.outObjects;

import java.time.LocalDate;

public class Order {
    private final int orderId;
    private final double totalAmount;
    private final LocalDate deliverTime;
    private final int branchId;

    public Order(BussniesLayer.Order order) {
        orderId = order.getOrderId();
        totalAmount = order.getTotalAmount();
        deliverTime = order.getDeliverTime();
    }

    public String toString() {
        if(deliverTime == null)
            return "Order: \n" +
                    "\torderId: " + orderId + "\n" +
                    "\ttotal amount: " + totalAmount + "\n" +
                    "\tdeliver time: unknown";
        return "Order: \n" +
                "\torderId: " + orderId + "\n" +
                "\ttotal amount: " + totalAmount + "\n" +
                "\tdeliver time: " + deliverTime;
    }

    public String toStringId(){
        return ""+ orderId;
    }

    public String toStringTotalAmount(){ return totalAmount + "";}

    public String toStringDeliverTime(){
        if(deliverTime == null) return "unknown";
        return deliverTime.toString();
    }
}
