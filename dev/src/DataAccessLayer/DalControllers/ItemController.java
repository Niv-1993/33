package DataAccessLayer.DalControllers;

import DataAccessLayer.DalObjects.DalItem;

import java.util.List;

public class ItemController extends DalController<DalItem> {

    private final String tableName = "Items";

    public ItemController() {
        super("Items");
    }

    @Override
    public boolean insert(DalItem dalObject) {
        return false;
    }

    @Override
    public boolean delete(DalItem dalObject) {
        return false;
    }

    @Override
    public DalItem reader(DalItem dalObject) {
        return null;
    }

    @Override
    public List<DalItem> loadData(DalItem dalObject) {
        return null;
    }

    @Override
    public boolean update(DalItem dalObject) {
        return false;
    }

    @Override
    public List<DalItem> select(DalItem dalObject) {
        return null;
    }
}
