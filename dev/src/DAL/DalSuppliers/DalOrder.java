package DAL.DalSuppliers;


import DAL.DALObject;
import DAL.DalController;
import Utility.Tuple;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;

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

    public void updateDeliverTime(LocalDate deliverTime) throws Exception {
        LinkedList<Tuple<Object,Class>> list = new LinkedList<>();
        String query = "UPDATE Orders\n" +
                "SET deliverTime = ?\n"+
                "WHERE orderId = "+orderId;
        list.add(new Tuple<>(deliverTime.toString(), String.class));
        DC.noSelect(query, list);
        this.deliverTime = deliverTime.toString();
    }

    public void updateTotalAmount(double totalAmount){
        LinkedList<Tuple<Object,Class>> list = new LinkedList<>();
        String query = "UPDATE Orders\n" +
                "SET totalAmount = ?\n"+
                "WHERE orderId = "+orderId;
        list.add(new Tuple<>(totalAmount, Double.class));
        try {
            DC.noSelect(query, list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.totalAmount = totalAmount;
    }

    public void removeOrder() {
        LinkedList<Tuple<Object,Class>> list = new LinkedList<>();
        String query = "DELETE FROM Orders\n" +
                "WHERE orderId = "+orderId;
        try {
            DC.noSelect(query, list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void removeItemFromOrder(int itemId) {
        LinkedList<Tuple<Object,Class>> list = new LinkedList<>();
        String query = "DELETE FROM ItemsInOrders\n" +
                "WHERE itemId = "+itemId;
        try {
            DC.noSelect(query, list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addItemToOrder(int itemId, int amount) {
        LinkedList<Tuple<Object,Class>> list = new LinkedList<>();
        String query = "INSERT INTO ItemsInOrders\n" +
                "VALUES ("+orderId+","+itemId+","+amount+")";
        try {
            DC.noSelect(query, list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
