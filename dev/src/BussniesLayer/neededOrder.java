package BussniesLayer;

import java.time.LocalDate;

public final class neededOrder extends Order{

    public neededOrder(int orderId, LocalDate deliverTime,int branchID, Item item, int amount) {
        super(orderId, deliverTime, branchID);
        items.put(item, amount);
    }

}
