package DataAccessLayer.DalControllers;

import DataAccessLayer.DalObjects.DalItem;

public class ItemController extends DalController<DalItem> {

    private final String tableName = "Items";

    public ItemController() {
        super("Items");
    }
}
