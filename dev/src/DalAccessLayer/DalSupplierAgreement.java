package DalAccessLayer;


public class DalSupplierAgreement extends DalObject<DalSupplierAgreement>{

    private int minimalAmount;
    private int discount;
    private boolean constantTime;
    private boolean shipToUs;
    private int supplierBN;

    protected DalSupplierAgreement() {
        super();
    }

}
