package BussniesLayer.facade.outObjects;

import java.time.LocalDate;
import java.util.Hashtable;

public class Order {
    private final int orderId;
    private final double totalAmount;
    private final LocalDate deliverTime;
    private final int branchId;
    private final Hashtable<Integer , Integer> amounts;

    public Order(BussniesLayer.Order order) {
        orderId = order.getOrderId();
        totalAmount = order.getTotalAmount();
        deliverTime = order.getDeliverTime();
        branchId = order.getBranchID();
        amounts = order.getAmounts();
    }

    public String toString() {
        if(deliverTime == null)
            return "Order: \n" +
                    "\torderId: " + orderId + "\n" +
                    "\ttotal amount: " + totalAmount + "\n" +
                    "\tdeliver time: unknown" +
                    "\tbranchId: " + branchId;
        return "Order: \n" +
                "\torderId: " + orderId + "\n" +
                "\ttotal amount: " + totalAmount + "\n" +
                "\tdeliver time: " + deliverTime +
                "\tbranchId: " + branchId;
    }

    public String toStringId(){
        return ""+ orderId;
    }

    public String toStringTotalAmount(){ return totalAmount + "";}

    public String toStringDeliverTime(){
        if(deliverTime == null) return "unknown";
        return deliverTime.toString();
    }

    public String toStringAmount(String itemId){
        return "" + amounts.get(Integer.parseInt(itemId));
    }
}
