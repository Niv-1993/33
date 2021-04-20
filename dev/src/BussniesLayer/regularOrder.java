package BussniesLayer;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class regularOrder extends Order{
    
    public regularOrder(int orderId , LocalDate deliverTime, int branchId){
        super(orderId , deliverTime, branchId);
    }

    public void updateDeliverTime(LocalDate deliverTime) throws Exception {
        // check if the date is at least one day before.
        long daysDiffrence = ChronoUnit.DAYS.between(LocalDate.now(), deliverTime);
        if(daysDiffrence >= 1) this.deliverTime = deliverTime;
        else throw new Exception("deliver time must be at least one day after current time");
    }


}
