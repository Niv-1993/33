package DAL.DalStock;

import DAL.DALObject;
import DAL.DalController;

public class DALProductType extends DALObject {

    public DALProductType(){
        super(null);
    }

    public DALProductType(int storeID, int typeID, String name, int category, int min, int shelfCurr, int storageCurr,
                   double basePrice, double salePrice, String producer, DalController dc){
        super(dc);
    }
    // get products, suppliers and discounts from controller

    @Override
    public String getCreate() {
        return "CREATE TABLE IF NOT EXISTS ProductType (\n" +
                "\tstoreID INTEGER NOT NULL,\n" +
                "\ttypeID INTEGER NOT NULL,\n" +
                "\tname VARCHAR NOT NULL,\n" +
                "\tcategoryID INTEGER NOT NULL,\n" +
                "\tminimum INTEGER NOT NULL,\n" +
                "\tshelfCurr INTEGER NOT NULL,\n" +
                "\tstorageCurr INTEGER NOT NULL,\n" +
                "\tbasePrice DOUBLE NOT NULL,\n" +
                "\tsalePrice DOUBLE NOT NULL,\n" +
                "\tproducer VARCHAR NOT NULL,\n" +
                "\tPRIMARY KEY (storeID, typeID),\n" +
                "\tFOREIGN KEY (storeID) REFERENCES StoreController(storeID)\n" +
                "\tON DELETE CASCADE ON UPDATE CASCADE\n" +
                "\tFOREIGN KEY (categoryID) REFERENCES Category(categoryID)\n" +
                "\tON DELETE CASCADE ON UPDATE CASCADE\n" +
                ");\n"+
                "CREATE TABLE IF NOT EXISTS Supplier (\n" +
                "\tstoreID INTEGER NOT NULL,\n" +
                "\ttypeID INTEGER NOT NULL,\n" +
                "\tsupplierID INTEGER NOT NULL,\n" +
                "\tPRIMARY KEY (storeID, typeID, supplierID),\n" +
                "\tFOREIGN KEY (storeID) REFERENCES StoreController(storeID)\n" +
                "\tON DELETE CASCADE ON UPDATE CASCADE,\n" +
                "\tFOREIGN KEY (typeID) REFERENCES ProductType(typeID)\n" +
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
