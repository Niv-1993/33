package BussniesLayer;

import java.time.LocalDate;

public class neededOrder extends Order{

    public neededOrder(int orderId, LocalDate deliverTime,int branchID) {
        super(orderId, deliverTime, branchID);
    }

    public int bestSupplierOfItem(int typeID){  // find the supplier who supplies the item in the best price.
        return 0;
    }
}
