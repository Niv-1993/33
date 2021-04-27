package DAL.DalStock;

import DAL.DALObject;
import DAL.DalController;

public abstract class DALDiscount extends DALObject {

    public DALDiscount(DalController dc) {
        super(dc);
    }
    @Override
    public String getCreate() {
        return "CREATE TABLE IF NOT EXISTS Discount (\n" +
                "\tstoreID INTEGER NOT NULL,\n" +
                "\tdiscountID INTEGER NOT NULL,\n" +
                "\ttypeID INTEGER,\n" +
                "\tcategoryID INTEGER,\n" +
                "\tsupplierID INTEGER,\n" +
                "\tpercent DOUBLE NOT NULL,\n" +
                "\tstartDate VARCHAR NOT NULL,\n" +
                "\tendDate VARCHAR NOT NULL,\n" +
                "\tPRIMARY KEY (storeID, discountID),\n" +
                "\tFOREIGN KEY (storeID) REFERENCES StoreController(storeID)\n" +
                "\tON DELETE CASCADE ON UPDATE CASCADE,\n" +
                "\tFOREIGN KEY (typeID) REFERENCES ProductType(typeID)\n" +
                "\tON DELETE CASCADE ON UPDATE CASCADE,\n" +
                "\tFOREIGN KEY (categoryID) REFERENCES Category(categoryID)\n" +
                "\tON DELETE CASCADE ON UPDATE CASCADE\n" +
                "\tFOREIGN KEY (supplierID) REFERENCES Supplier(supplierID)\n" +
                "\tON DELETE CASCADE ON UPDATE CASCADE\n" +
                ");";
    }

}
