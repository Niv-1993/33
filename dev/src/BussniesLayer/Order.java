package BussniesLayer;

import java.util.Date;
import java.util.List;

public class Order {
    private int orderId;
    private int supplierBN;
    private List<Item> items;
    private int totalAmount;
    private Date deliverTime;
}
