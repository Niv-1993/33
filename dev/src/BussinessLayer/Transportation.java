package Bussiness;

import BussinessLayer.Item;

import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

//yuval
public class Transportation {
    private Date date;
    private LocalTime leavingTime;
    private Driver driver;
    //ask if they call this item or product?
    private HashMap<Site, List<Item>> deliveryItems;
}
