package BussniesLayer;

import java.util.Dictionary;
import java.util.List;

public class SupplierCard {
    private int supplierBN;
    private String supplierName;
    private int accountNumber;
    private String payWay;
    private List<Order> orders;
    private List<Item> item;
    private SupplierAgreement supplierAgreement;
    private Dictionary<String , String> contactPhone;
    private Dictionary<String , String> contactEmail;
}
