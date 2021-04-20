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

    public void updateTotalAmount(double totalAmount){
        this.totalAmount = totalAmount;
    }

    public void addItemToOrder(Item item , int amount) throws Exception {
        if(amount < 1) throw new Exception("amount must be at least 1");
        if (items.get(item) != null) items.put(item, items.get(item) + amount);
        else items.put(item, amount);
        QuantityDocument qd = item.getQuantityDocument();
        if (qd == null) throw new Exception("quantity document does not exist.");
        totalAmount = totalAmount + item.getPrice()*amount;
        if (qd.getMinimalAmount() <= items.get(item)) {
            double discount = qd.getDiscount()/100.0;
            totalAmount = totalAmount - item.getPrice()*discount*amount;
        }
    }
}
