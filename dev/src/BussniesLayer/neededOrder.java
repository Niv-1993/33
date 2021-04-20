package BussniesLayer;

import java.time.LocalDate;

public class neededOrder extends Order{

    public neededOrder(int orderId, LocalDate deliverTime,int branchID) {
        super(orderId, deliverTime, branchID);
    }

}
