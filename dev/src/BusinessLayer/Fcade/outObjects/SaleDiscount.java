package BusinessLayer.Fcade.outObjects;

import java.util.Date;

public class SaleDiscount {
    int dicountID;
    Date start;
    Date end;
    float precent;


    public SaleDiscount(int dicountID, Date start, Date end, float precent) {
        this.dicountID = dicountID;
        this.start = start;
        this.end = end;
        this.precent = precent;
    }

    @Override
    public String toString() {
        return "Discount ID: "+dicountID+"\nDiscount is: "+precent+"%\nStarts on: "+start+"\nEnds on: "+end+"\n";
    }
}
