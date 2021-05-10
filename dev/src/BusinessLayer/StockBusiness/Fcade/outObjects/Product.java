package BusinessLayer.StockBusiness.Fcade.outObjects;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Product {
    int ID;
    int typeID;
    Date expiration;
    boolean inStorage;
    int shelfID;

    public Product(int ID, int typeID, Date expiration, boolean inStorage, int shelfID) {
        this.ID = ID;
        this.typeID = typeID;
        this.expiration = expiration;
        this.inStorage = inStorage;
        this.shelfID = shelfID;
    }

    @Override
    public String toString() {
        return "Product ID: "+ID+"\nIs of type: "+typeID+"\nExpires on: "+dateString(expiration)+"\n"+
                (inStorage?"Item is in Storage ":"Item is in the store ")+"\nOn shelf number: "+shelfID+"\n";
    }
    private String dateString(Date date){
        String pattern="dd-MM-yyyy";
        DateFormat df=new SimpleDateFormat(pattern);
        return df.format(date);
    }
}
