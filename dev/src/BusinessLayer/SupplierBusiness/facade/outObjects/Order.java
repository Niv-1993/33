package BusinessLayer.SupplierBusiness.facade.outObjects;

import java.time.LocalDate;
import java.util.Hashtable;

public class Order {
    private final int orderId;
    private final double totalAmount;
    private final LocalDate deliverTime;
    private final int branchId;
    private final Hashtable<Integer, Integer> items;

    public Order(BusinessLayer.SupplierBusiness.Order order) {
        orderId = order.getOrderId();
        totalAmount = order.getTotalAmount();
        deliverTime = order.getDeliverTime();
        branchId = order.getBranchID();
        items = order.getAmounts();
    }

    public String toString() {
        return "Order: \n" +
                "\torderId: " + orderId + "\n" +
                "\ttotal amount: " + totalAmount + "\n" +
                "\tdeliver time: " + toStringDeliverTime() + "\n" +
                "\tbranchId: " + branchId;
    }

    public String toStringId() {
        return "" + orderId;
    }

    public String toStringTotalAmount() {
        return totalAmount + "";
    }

    public String toStringDeliverTime() {
        if (deliverTime == null) return "unknown";
        return deliverTime.toString();
    }

    public String toStringAmount(String itemId) {
        return "" + items.get(Integer.parseInt(itemId));
    }
}
