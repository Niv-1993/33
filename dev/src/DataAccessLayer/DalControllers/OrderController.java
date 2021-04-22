package DataAccessLayer.DalControllers;

import DataAccessLayer.DalObjects.DalOrder;

import java.util.List;

public class OrderController extends DalController<DalOrder> {

    private final String tableName = "Orders";

    public OrderController(){super("Orders");}

    @Override
    public boolean insert(DalOrder dalObject) {
        return false;
    }

    @Override
    public boolean delete(DalOrder dalObject) {
        return false;
    }

    @Override
    public DalOrder reader(DalOrder dalObject) {
        return null;
    }

    @Override
    public List<DalOrder> loadData(DalOrder dalObject) {
        return null;
    }

    @Override
    public boolean update(DalOrder dalObject) {
        return false;
    }

    @Override
    public List<DalOrder> select(DalOrder dalObject) {
        return null;
    }
}
