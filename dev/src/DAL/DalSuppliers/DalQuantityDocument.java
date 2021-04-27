package DAL.DalSuppliers;

import DAL.DALObject;
import DAL.DalController;

public class DalQuantityDocument extends DALObject {
    public DalQuantityDocument() {
        super(null);
    }

    public DalQuantityDocument(int itemId , double totalAmount , String deliverTime , int branchId , DalController dalController){
        super(dalController);
    }

    @Override
    public String getCreate() {
        return "\"itemId\" INTEGER NOT NULL,\n" +
                "\t\"totalAmount\" DOUBLE NOT NULL,\n" +
                "\t\"deliverTime\" VARCHAR NOT NULL,\n" +
                "\t\"branchId\" INTEGER NOT NULL,\n" +
                "\tPRIMARY KEY(\"itemId\"),\n" +
                "\tFOREIGN KEY(\"itemId\") REFERENCES \"Items\"(\"itemId\") ON DELETE CASCADE ON UPDATE CASCADE\n" +
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
