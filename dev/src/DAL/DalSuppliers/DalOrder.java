package DAL.DalSuppliers;


import DAL.DALObject;
import DAL.DalController;
import Utility.Tuple;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;

public class DalOrder extends DALObject {
    private int orderId;
    private int supplierBN;
    private double totalAmount;
    private String deliverTime;
    private int branchId;
    private int orderType;
    final static Logger log=Logger.getLogger(DalOrder.class);

    public DalOrder() {
        super(null);
    }

    public DalOrder(Integer orderId , Integer supplierBN , Double totalAmount , String deliverTime , Integer branchId , Integer orderType , DalController dalController ){
        super(dalController);
        this.orderId = orderId;
        this.supplierBN = supplierBN;
        this.totalAmount = totalAmount;
        this.deliverTime = deliverTime;
        this.branchId = branchId;
        this.orderType = orderType;
    }

    @Override
    public String getCreate() {
        return "CREATE TABLE IF NOT EXISTS \"Orders\"(\n" +
                "\t\"orderId\" INTEGER NOT NULL,\n" +
                "\t\"supplierBN\" INTEGER NOT NULL,\n" +
                "\t\"totalAmount\" DOUBLE NOT NULL,\n" +
                "\t\"deliverTime\" VARCHAR NOT NULL,\n" +
                "\t\"branchId\" INTEGER NOT NULL,\n" +
                "\t\"orderType\" INTEGER NOT NULL,\n" +
                "\tPRIMARY KEY(\"orderId\"),\n" +
                "\tFOREIGN KEY(\"supplierBN\") REFERENCES \"Suppliers\"(\"supplierBN\") ON DELETE CASCADE ON UPDATE CASCADE\n" +
                ");" +
                "CREATE TABLE IF NOT EXISTS \"ItemsInOrders\"(\n" +
                "\t\"orderId\" INTEGER NOT NULL,\n" +
                "\t\"itemId\" INTEGER NOT NULL,\n" +
                "\t\"amount\" INTEGER NOT NULL,\n" +
                "\tPRIMARY KEY(\"itemId\"),\n" +
                "\tFOREIGN KEY(\"orderId\") REFERENCES \"Orders\"(\"orderId\")  ON DELETE CASCADE ON UPDATE CASCADE ,\n" +
                "\tFOREIGN KEY(\"itemId\") REFERENCES \"Items\"(\"itemId\")  ON DELETE CASCADE ON UPDATE CASCADE\n" +
                ");";
    }

    @Override
    public String getSelect() {
        return "Select * FROM Orders\n" +
                "WHERE orderId = ?;";
    }

    @Override
    public String getDelete() {
        return "DELETE FROM Orders\n" +
                "WHERE orderId = ?;";
    }

    @Override
    public String getUpdate() {
        return "UPDATE Orders\n" +
                "SET (?) = (?)\n"+
                "WHERE orderId = ?;";
    }

    @Override
    public String getInsert() {
        return "INSERT OR REPLACE INTO Orders\n"+
                "VALUES (?,?,?,?,?,?);";
    }

    public int getOrderID() {
        return orderId;
    }

    public double getTotalAmount() {
        try {
            String query = "SELECT totalAmount FROM Orders\n" +
                    "WHERE orderId = ?;";
            LinkedList<Integer> list = new LinkedList<>();
            list.add(orderId);
            Tuple<List<Class>,List<Object>> tuple = DC.Select(query, list);
            totalAmount = (Double) tuple.item2.get(0);
        }
        catch (Exception e){
            log.warn(e);
        }
        return totalAmount;
    }

    public String getDeliverTime() {
        String Temp = "";
        try {
            String query = "SELECT deliverTime FROM Orders\n" +
                    "WHERE orderId = ?;";
            LinkedList<Integer> list = new LinkedList<>();
            list.add(orderId);
            Tuple<List<Class>,List<Object>> tuple = DC.Select(query, list);
            Temp = tuple.item2.get(0).toString();
        }
        catch (Exception e){
            log.warn(e);
        }
        deliverTime= Temp;
        return deliverTime;
    }

    public int getBranchID() {
        try {
            String query = "SELECT branchId FROM Orders\n" +
                    "WHERE orderId = ?;";
            LinkedList<Integer> list = new LinkedList<>();
            list.add(orderId);
            Tuple<List<Class>, List<Object>> tuple = DC.Select(query, list);
            branchId = (Integer) tuple.item2.get(0);
        }
        catch (Exception e) {
            log.warn(e);
        }
        return branchId;
    }

    public int getOrderType() {
        try {
            String query = "SELECT orderType FROM Orders\n" +
                    "WHERE orderId = ?;";
            LinkedList<Integer> list = new LinkedList<>();
            list.add(orderId);
            Tuple<List<Class>, List<Object>> tuple = DC.Select(query, list);
            orderType = (Integer) tuple.item2.get(0);
        }
        catch (Exception e) {
            log.warn(e);
        }
        return orderType;
    }

    public void updateDeliverTime(LocalDate deliverTime) throws Exception {
        LinkedList<Tuple<Object,Class>> list = new LinkedList<>();
        String query = "UPDATE Orders\n" +
                "SET deliverTime = ?\n"+
                "WHERE orderId = ?;";
        list.add(new Tuple<>(deliverTime.toString(), String.class));
        list.add(new Tuple<>(orderId, Integer.class));
        DC.noSelect(query, list);
        this.deliverTime = deliverTime.toString();
    }

    public void updateTotalAmount(double totalAmount){
        LinkedList<Tuple<Object,Class>> list = new LinkedList<>();
        String query = "UPDATE Orders\n" +
                "SET totalAmount = ?\n"+
                "WHERE orderId = ?;";
        list.add(new Tuple<>(totalAmount, Double.class));
        list.add(new Tuple<>(orderId, Integer.class));
        try {
            DC.noSelect(query, list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.totalAmount = totalAmount;
    }

    public void updateBranchId(int branchId){
        LinkedList<Tuple<Object,Class>> list = new LinkedList<>();
        String query = "UPDATE Orders\n" +
                "SET branchId = ?\n"+
                "WHERE orderId = ?;";
        list.add(new Tuple<>(branchId, Integer.class));
        list.add(new Tuple<>(orderId, Integer.class));
        try {
            DC.noSelect(query, list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.branchId = branchId;
    }

    public void removeOrder() {
        LinkedList<Tuple<Object,Class>> list = new LinkedList<>();
        String query = "DELETE FROM Orders\n" +
                "WHERE orderId = ?;";
        list.add(new Tuple<>(orderId, Integer.class));
        try {
            DC.noSelect(query, list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void removeItemFromOrder(int itemId) {
        LinkedList<Tuple<Object,Class>> list = new LinkedList<>();
        String query = "DELETE FROM ItemsInOrders\n" +
                "WHERE itemId = ?;";
        list.add(new Tuple<>(itemId, Integer.class));
        try {
            DC.noSelect(query, list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addItemToOrder(int itemId, int amount) throws Exception {
        LinkedList<Tuple<Object,Class>> list = new LinkedList<>();
        String query = "INSERT INTO ItemsInOrders\n" +
                "VALUES (?,?,?);";
        list.add(new Tuple<>(orderId, Integer.class));
        list.add(new Tuple<>(itemId, Integer.class));
        list.add(new Tuple<>(amount, Integer.class));
        try {
            DC.noSelect(query, list);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public List<Tuple<List<Class>, List<Object>>> loadItems() {
        try {
            String query = "SELECT * FROM ItemsInOrders\n" +
                    "WHERE orderId = ?";
            LinkedList<Integer> list = new LinkedList<>();
            list.add(orderId);
            List<Tuple<List<Class>,List<Object>>> tuple = DC.SelectMany(query, list);
            return tuple;
        }
        catch (Exception e){
            log.warn(e);
        }
        return null;
    }
}
