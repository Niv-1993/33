package DAL.DalSuppliers;

import DAL.DALObject;
import DAL.DalController;

public class DalSupplierController extends DALObject {
    private int supplierBN;
    private int numOfItems;
    private int numOfOrders;

    public DalSupplierController() {
        super(null);
    }
    public DalSupplierController(Integer supplierBN , Integer numOfItems , Integer numOfOrders , DalController dalController) {
        super(dalController);
    }

    @Override
    public String getCreate() {
        return "CREATE TABLE IF NOT EXISTS \"SupplierController\"(\n" +
                "\t\"supplierBN\" INTEGER NOT NULL,\n" +
                "\t\"numOfItems\" INTEGER NOT NULL,\n" +
                "\t\"numOfOrders\" INTEGER NOT NULL,\n" +
                "\tPRIMARY KEY(\"supplierBN\")\n" +
                ");";
    }

    @Override
    public String getSelect() {
        return null;
    }

    @Override
    public String getDelete() {
        return null;
    }

    @Override
    public String getUpdate() {
        return null;
    }

    @Override
    public String getInsert() {
        return null;
    }
}
