package BusinessLayer.SupplierBusiness.facade.outObjects;

import java.time.LocalDate;
import java.util.Hashtable;

public class Order {
    private final int orderId;
    private final int supplierBN;
    private final double totalAmount;
    private final int branchId;
    private final Hashtable<Integer, Integer> items;

    public Order(BusinessLayer.SupplierBusiness.Order order) {
        orderId = order.getOrderId();
        supplierBN = order.getSupplierBN();
        totalAmount = order.getTotalAmount();
        branchId = order.getBranchID();
        items = order.getAmounts();
    }

    public String toString() {
        return "Order: \n" +
                "\torderId: " + orderId + "\n" +
                "\tsupplierBN: " + supplierBN + "\n" +
                "\ttotal amount: " + totalAmount + "\n" +
                "\tbranchId: " + branchId;
    }

    public String toStringId() {
        return "" + orderId;
    }

    public String toStringTotalAmount() {
        return totalAmount + "";
    }

    public String toStringAmount(String itemId) {
        return "" + items.get(Integer.parseInt(itemId));
    }

    public String toStringSupplierBN() { return "" + supplierBN; }

}
