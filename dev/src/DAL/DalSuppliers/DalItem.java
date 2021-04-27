package DAL.DalSuppliers;

import DAL.DALObject;
import DAL.DalController;

public class DalItem extends DALObject {
    public DalItem() {
        super(null);
    }

    public DalItem(int itemId , int supplierBN , String itemName , double price , int typeId , String expirationDate , DalController dalController){
        super(dalController);
    }

    @Override
    public String getCreate() {
        return "CREATE TABLE IF NOT EXISTS \"Items\"(\n" +
                "\t\"itemId\" INTEGER NOT NULL,\n" +
                "\t\"supplierBN\" INTEGER NOT NULL,\n" +
                "\t\"itemName\" VARCHAR NOT NULL,\n" +
                "\t\"price\" DOUBLE NOT NULL ,\n" +
                "\t\"typeId\" INTEGER NOT NULL,\n" +
                "\t\"expirationDate\" TEXT NOT NULL,\n" +
                "\tPRIMARY KEY(\"EID\"),\n" +
                "\tFOREIGN KEY(\"supplierBN\") REFERENCES \"Suppliers\"(\"supplierBN\") ON DELETE CASCADE ON UPDATE CASCADE\n" +
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
