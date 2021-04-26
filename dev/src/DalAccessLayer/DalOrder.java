package DalAccessLayer;


import BusinessLayer.SupplierBusiness.Item;

import java.time.LocalDate;
import java.util.Hashtable;

public class DalOrder extends DalObject<DalOrder>{

    private int orderId;
    private Hashtable<Integer, Integer> items;
    private double totalAmount;
    private LocalDate deliverTime;
    private int branchId;
    private int supplierBN;

    public DalOrder() {
        super();
    }

}
