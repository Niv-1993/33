package BussniesLayer;

import java.time.LocalDate;

public class regularOrder extends Order{
    
    public regularOrder(int orderId , LocalDate deliverTime){
        super(orderId , deliverTime);
    }

    public void updateDeliverTime(LocalDate deliverTime) throws Exception {
        // check if the date is at least one day before.
        if(deliverTime.isAfter(LocalDate.now())) this.deliverTime = deliverTime;
        else throw new Exception("deliver time must be after current time");
    }


}
