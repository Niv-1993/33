package DAL.DalSuppliers;


import DAL.DALObject;
import DAL.DalController;

import java.time.LocalDate;

public class DalOrder extends DALObject {
    protected int orderId;
    protected double totalAmount;
    protected String deliverTime;
    protected int branchId;

    public DalOrder() {
        super(null);
    }

    public DalOrder(Integer orderId , Integer supplierBN , Integer totalAmount , String deliverTime , Integer branchId , DalController dalController ){
        super(dalController);
    }

    @Override
    public String getCreate() {
        return "CREATE TABLE IF NOT EXISTS \"Orders\"(\n" +
                "\t\"orderId\" INTEGER NOT NULL,\n" +
                "\t\"supplierBN\" INTEGER NOT NULL,\n" +
                "\t\"totalAmount\" DOUBLE NOT NULL,\n" +
                "\t\"deliverTime\" VARCHAR NOT NULL,\n" +
                "\t\"branchId\" INTEGER NOT NULL,\n" +
                "\tPRIMARY KEY(\"orderId\"),\n" +
                "\tFOREIGN KEY(\"supplierBN\") REFERENCES \"Suppliers\"(\"supplierBN\") ON DELETE CASCADE ON UPDATE CASCADE\n" +
                ");" +
                "CREATE TABLE IF NOT EXISTS \"ItemsInOrders\"(\n" +
                "\t\"orderId\" INTEGER NOT NULL,\n" +
                "\t\"itemId\" INTEGER NOT NULL,\n" +
                "\t\"amount\" INTEGER NOT NULL,\n" +
                "\tPRIMARY KEY(\"itemId\"), \n" +
                "\tFOREIGN KEY(\"orderId\") REFERENCES \"Orders\"(\"orderId\")  ON DELETE CASCADE ON UPDATE CASCADE ,\n" +
                "\tFOREIGN KEY(\"itemId\") REFERENCES \"Items\"(\"itemId\")  ON DELETE CASCADE ON UPDATE CASCADE\n" +
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
