package DAL.DalStock;

import DAL.DALObject;
import DAL.DalController;

public class DALCategory extends DALObject {

    public DALCategory(){
        super(null);
    }

    public DALCategory(Integer storeID, Integer id, String name, DalController dc){
        super(dc);
    } // get child categories types and discounts from controller

    @Override
    public String getCreate() {
        return "CREATE TABLE IF NOT EXISTS Category (\n" +
                "\tstoreID INTEGER NOT NULL,\n" +
                "\tcategoryID INTEGER NOT NULL,\n" +
                "\tname VARCHAR NOT NULL,\n" +
                "\tPRIMARY KEY (storeID, categoryID),\n" +
                "\tFOREIGN KEY (storeID) REFERENCES StoreController(storeID)\n" +
                "\tON DELETE CASCADE ON UPDATE CASCADE\t\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE IF NOT EXISTS SubCategory (\n" +
                "\tstoreID INTEGER NOT NULL,\n" +
                "\tparentID INTEGER NOT NULL,\n" +
                "\tchildID INTEGER NOT NULL,\n" +
                "\tPRIMARY KEY (storeID, parentID, childID),\n" +
                "\tFOREIGN KEY (storeID) REFERENCES StoreController(storeID)\n" +
                "\tON DELETE CASCADE ON UPDATE CASCADE,\n" +
                "\tFOREIGN KEY (parentID) REFERENCES Category (categoryID)\n" +
                "\tON DELETE CASCADE ON UPDATE CASCADE,\n" +
                "\tFOREIGN KEY (childID) REFERENCES Category (categoryID)\n" +
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
