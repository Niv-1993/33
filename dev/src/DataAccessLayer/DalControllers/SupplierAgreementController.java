package DataAccessLayer.DalControllers;

import DataAccessLayer.DalObjects.DalSupplierAgreement;

import java.util.List;

public class SupplierAgreementController extends DalController<DalSupplierAgreement>{

    private final String tableName = "SupplierAgreements";

    public SupplierAgreementController(){super("SupplierAgreements");}

    @Override
    public boolean insert(DalSupplierAgreement dalObject) {
        return false;
    }

    @Override
    public boolean delete(DalSupplierAgreement dalObject) {
        return false;
    }

    @Override
    public DalSupplierAgreement reader(DalSupplierAgreement dalObject) {
        return null;
    }

    @Override
    public List<DalSupplierAgreement> loadData(DalSupplierAgreement dalObject) {
        return null;
    }

    @Override
    public boolean update(DalSupplierAgreement dalObject) {
        return false;
    }

    @Override
    public List<DalSupplierAgreement> select(DalSupplierAgreement dalObject) {
        return null;
    }
}
