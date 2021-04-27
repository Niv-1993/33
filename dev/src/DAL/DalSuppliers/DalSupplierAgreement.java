package DAL.DalSuppliers;

import DAL.DALObject;
import DAL.DalController;

public class DalSupplierAgreement extends DALObject {
    public DalSupplierAgreement() {
        super(null);
    }

    public DalSupplierAgreement(Integer supplierBN , Integer minimalAmount , Integer discount , Integer constantTime , Integer shipToUs , DalController dalController){
        super(dalController);
    }

    @Override
    public String getCreate() {
        return "CREATE TABLE IF NOT EXISTS \"SupplierAgreements\"(\n" +
                "\t\"supplierBN\" INTEGER NOT NULL,\n" +
                "\t\"minimalAmount\" INTEGER NOT NULL,\n" +
                "\t\"discount\" INTEGER NOT NULL,\n" +
                "\t\"constantTime\" INTEGER NOT NULL,\n" +
                "\t\"shipToUs\" INTEGER NOT NULL,\n" +
                "\tPRIMARY KEY(\"supplierBN\"),\n" +
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
