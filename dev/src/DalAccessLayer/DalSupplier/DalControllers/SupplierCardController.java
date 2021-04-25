package DataAccessLayer.DalControllers;

import DataAccessLayer.DalObjects.DalSupplierCard;

import java.util.List;

public class SupplierCardController extends DalController<DalSupplierCard>{

    private final String tableName = "SupplierCards";

    public SupplierCardController(){super("SupplierCards");}

    @Override
    public boolean insert(DalSupplierCard dalObject) {
        return false;
    }

    @Override
    public boolean delete(DalSupplierCard dalObject) {
        return false;
    }

    @Override
    public DalSupplierCard reader(DalSupplierCard dalObject) {
        return null;
    }

    @Override
    public List<DalSupplierCard> loadData(DalSupplierCard dalObject) {
        return null;
    }

    @Override
    public boolean update(DalSupplierCard dalObject) {
        return false;
    }

    @Override
    public List<DalSupplierCard> select(DalSupplierCard dalObject) {
        return null;
    }
}
