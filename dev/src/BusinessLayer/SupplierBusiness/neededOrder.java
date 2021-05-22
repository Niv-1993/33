package BusinessLayer.SupplierBusiness;

import DAL.DalSuppliers.DalOrder;

import java.time.LocalDate;

public final class neededOrder extends Order {

    public neededOrder(int supplierBN, int orderId, LocalDate deliverTime, int branchID, Item item, int amount, double totalAmount) throws Exception {
        super(supplierBN, orderId, deliverTime, branchID, 1);
        try {
            items.put(item, amount);
            if (items.get(item) != null) {
                items.put(item, items.get(item) + amount);
                dalOrder.addItemToOrder(item.getItemId(), items.get(item) + amount);
            } else {
                items.put(item, amount);
                dalOrder.addItemToOrder(item.getItemId(), amount);
            }
            dalOrder.updateTotalAmount(totalAmount);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        dalOrder.updateTotalWeight(item.getWeight()*amount);
    }

    public neededOrder(DalOrder dalOrder) {
        super(dalOrder);
    }
}
