package DAL.DalSuppliers;


import DAL.DALObject;
import DAL.DalController;

import java.time.LocalDate;

public class DalOrder extends DALObject {
    private int orderId;
    private int supplierBN;
    private double totalAmount;
    private String deliverTime;
    private int branchId;

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
        return "Select * FROM Orders\n" +
                "WHERE orderId = "+ orderId;
    }

    @Override
    public String getDelete() {
        return "DELETE FROM Orders\n" +
                "WHERE orderId = "+ orderId;
    }

    @Override
    public String getUpdate() {
        return "UPDATE Orders\n" +
                "SET (?) = (?)\n"+
                "WHERE orderId = "+ orderId;
    }

    @Override
    public String getInsert() {
        return "INSERT INTO Orders ?\n"+
                "VALUES ?";
    }

    public int getOrderID() {
        return orderId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getDeliverTime() {
        return deliverTime;
    }

    public int getBranchID() {
        return branchId;
    }



}
