package DAL.DalStock;

import DAL.DALObject;

public class DALStoreController extends DALObject {

    public DALStoreController(){}

    public DALStoreController(int storeID){}

    public DALStoreController(int storeID, int storeShelves, int shelves, int discountCounter,
                       int typeCounter, int categoryCounter, int maxProductsOnType){}
    //get categories controllers discounts and product types from controller

    @Override
    public String getCreate() {
        return "CREATE TABLE IF NOT EXISTS StoreController (\n" +
                "\tstoreID INTEGER PRIMARY KEY,\n" +
                "\tstoreShelves INTEGER,\n" +
                "\tnumberOfShelves INTEGER,\n" +
                "\tdiscountCounter INTEGER,\n" +
                "\ttypeCounter INTEGER,\n" +
                "\tcategoryCounter INTEGER,\n" +
                "\tmaxProductsOnType INTEGER\n" +
                ");\n" +
                "CREATE TABLE IF NOT EXISTS StoreControllerCategories (\n" +
                "\tstoreID INTEGER,\n" +
                "\tcategoryID INTEGER,\n" +
                "\tPRIMARY KEY (storeID, categoryID),\n" +
                "\tFOREIGN KEY (storeID) REFERENCES StoreController (storeID)\n" +
                "\tON DELETE CASCADE ON UPDATE CASCADE,\n" +
                "\tFOREIGN KEY (categoryID) REFERENCES Category (categoryID)\n" +
                "\tON DELETE CASCADE ON UPDATE CASCADE\n" +
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
