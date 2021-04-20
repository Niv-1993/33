package BussniesLayer;

import java.time.LocalDate;

public class neededOrder extends Order{

    public neededOrder(int orderId, LocalDate deliverTime) {
        super(orderId, deliverTime);
    }

    public int bestSupplierOfItem(int itemId){  // find the supplier hows supplied the item in the best price.
        return supplierBn;
    }
}
