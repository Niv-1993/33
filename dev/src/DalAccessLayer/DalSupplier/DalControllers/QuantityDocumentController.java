package DataAccessLayer.DalControllers;

import DataAccessLayer.DalObjects.DalQuantityDocument;

import java.util.List;

public class QuantityDocumentController extends DalController<DalQuantityDocument> {

    private final String tableName = "QuantityDocuments";

    public QuantityDocumentController(){super("QuantityDocuments");}

    @Override
    public boolean insert(DalQuantityDocument dalObject) {
        return false;
    }

    @Override
    public boolean delete(DalQuantityDocument dalObject) {
        return false;
    }

    @Override
    public DalQuantityDocument reader(DalQuantityDocument dalObject) {
        return null;
    }

    @Override
    public List<DalQuantityDocument> loadData(DalQuantityDocument dalObject) {
        return null;
    }

    @Override
    public boolean update(DalQuantityDocument dalObject) {
        return false;
    }

    @Override
    public List<DalQuantityDocument> select(DalQuantityDocument dalObject) {
        return null;
    }
}
