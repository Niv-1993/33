package BusinessLayer.SupplierBusiness;

import java.time.LocalDate;

public final class neededOrder extends Order {

    public neededOrder(int supplierBN, int orderId, LocalDate deliverTime, int branchID, Item item, int amount , double totalAmount) {
        super(supplierBN, orderId, deliverTime, branchID , 1);
        items.put(item, amount);
        dalOrder.updateTotalAmount(totalAmount);
    }

}
