package DAL.DalStock;

import DAL.DALObject;
import DAL.DalController;

public class DALStoreController extends DALObject {

    public DALStoreController(){
        super(null);
    }

    public DALStoreController(int storeID, int storeShelves, int shelves, int discountCounter,
                       int typeCounter, int categoryCounter, int maxProductsOnType, DalController dc){
        super(dc);
    }
    //get categories controllers discounts and product types from controller

    @Override
    public String getCreate() {
        return "CREATE TABLE IF NOT EXISTS StoreController (\n" +
                "\tstoreID INTEGER PRIMARY KEY NOT NULL,\n" +
                "\tstoreShelves INTEGER NOT NULL,\n" +
                "\tnumberOfShelves INTEGER NOT NULL,\n" +
                "\tdiscountCounter INTEGER NOT NULL,\n" +
                "\ttypeCounter INTEGER NOT NULL,\n" +
                "\tcategoryCounter INTEGER NOT NULL,\n" +
                "\tmaxProductsOnType INTEGER NOT NULL\n" +
                ");\n";
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
