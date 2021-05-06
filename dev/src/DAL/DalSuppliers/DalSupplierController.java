package DAL.DalSuppliers;

import DAL.DALObject;
import DAL.DalController;
import Utility.Tuple;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

public class DalSupplierController extends DALObject {
    private int supplierBN;
    private int numOfItems;
    private int numOfOrders;
    final static Logger log=Logger.getLogger(DalSupplierController.class);

    public DalSupplierController() {
        super(null);
    }
    public DalSupplierController(Integer supplierBN , Integer numOfItems , Integer numOfOrders , DalController dalController) {
        super(dalController);
        this.supplierBN = supplierBN;
        this.numOfItems = numOfItems;
        this.numOfOrders = numOfOrders;
    }

    @Override
    public String getCreate() {
        return "CREATE TABLE IF NOT EXISTS \"SupplierController\"(\n" +
                "\t\"supplierBN\" INTEGER NOT NULL,\n" +
                "\t\"numOfItems\" INTEGER NOT NULL,\n" +
                "\t\"numOfOrders\" INTEGER NOT NULL,\n" +
                "\tPRIMARY KEY(\"supplierBN\")\n" +
                ");";
    }

    @Override
    public String getSelect() {
        return "Select * FROM SupplierController\n";
    }

    @Override
    public String getDelete() {
        return "DELETE FROM SupplierController\n" +
                "WHERE supplierBN= ?;";
    }

    @Override
    public String getUpdate() {
        return "UPDATE SupplierController\n" +
                "SET (?) = (?)\n"+
                "WHERE supplierBN = ?;";
    }

    @Override
    public String getInsert() {
        return "INSERT OR REPLACE INTO SupplierController\n"+
                "VALUES (?,?,?);";
    }

    public int getNumOfItems() {
        return numOfItems;
    }

    public int getNumOfOrders() {
        return numOfOrders;
    }

    public void addNumOfItems() throws Exception {
        LinkedList<Tuple<Object,Class>> list = new LinkedList<>();
        String query = "UPDATE SupplierController\n" +
                "SET numOfItems = ?\n" +
                "WHERE supplierBN = ?;";
        list.add(new Tuple<>(numOfItems+1, Integer.class));
        list.add(new Tuple<>(supplierBN, Integer.class));
        DC.noSelect(query, list);
        numOfItems++;
    }

    public void addNumOfOrders() throws Exception {
        LinkedList<Tuple<Object,Class>> list = new LinkedList<>();
        String query = "UPDATE SupplierController\n" +
                "SET numOfItems = ?\n" +
                "WHERE supplierBN = ?;";
        list.add(new Tuple<>(numOfOrders+1, Integer.class));
        list.add(new Tuple<>(supplierBN, Integer.class));
        DC.noSelect(query, list);
        numOfOrders++;
    }

    public void load() {
        try {
            String query = "SELECT * FROM SupplierController\n" +
                    "WHERE supplierBN = ?;";
            LinkedList<Integer> list = new LinkedList<>();
            list.add(0);
            Tuple<List<Class>,List<Object>> tuple = DC.Select(query, list);
            numOfItems = (int) tuple.item2.get(1);
            numOfOrders = (int) tuple.item2.get(2);
        }
        catch (Exception e){
            log.warn(e);
        }
    }
}
