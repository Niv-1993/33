package DalAccessLayer;


import BusinessLayer.SupplierBusiness.SupplierAgreement;
import BusinessLayer.SupplierBusiness.regularOrder;

import java.util.Dictionary;
import java.util.List;

public class DalSupplierCard extends DalObject<DalSupplierCard>{

    private int supplierBN;
    private String supplierName;
    private int bankNumber;
    private int branchNumber;
    private int accountNumber;
    private String payWay;
    private List<Integer> orders;
    private List<Integer> items;
    private SupplierAgreement supplierAgreement;
    private Dictionary<String , String> contactPhone;
    private Dictionary<String , String> contactEmail;
    private regularOrder constantOrder;

    protected DalSupplierCard() {
        super();
    }

    public void DeleteItem(String attribute , int itemI){ }
}
