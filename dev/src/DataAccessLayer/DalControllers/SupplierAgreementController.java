package DataAccessLayer.DalControllers;

import DataAccessLayer.DalObjects.DalSupplierAgreement;

public class SupplierAgreementController extends DalController<DalSupplierAgreement>{

    private final String tableName = "SupplierAgreements";

    public SupplierAgreementController(){super("SupplierAgreements");}
}
