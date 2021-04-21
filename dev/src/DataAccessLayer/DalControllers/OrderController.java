package DataAccessLayer.DalControllers;

import DataAccessLayer.DalObjects.DalOrder;

public class OrderController extends DalController<DalOrder> {

    private final String tableName = "Orders";

    public OrderController(){super("Orders");}
}
