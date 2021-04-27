package BusinessLayer.SupplierBusiness;

import java.time.LocalDate;

public final class neededOrder extends Order {

    public neededOrder(int orderId, LocalDate deliverTime, int branchID, Item item, int amount , double totalAmount) {
        super(orderId, deliverTime, branchID);
        items.put(item, amount);
        this.totalAmount = totalAmount;
    }

}
