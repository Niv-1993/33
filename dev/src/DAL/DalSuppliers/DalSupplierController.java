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
                "\t\"controller\" INTEGER NOT NULL,\n" +
                "\t\"numOfItems\" INTEGER NOT NULL,\n" +
                "\t\"numOfOrders\" INTEGER NOT NULL,\n" +
                "\tPRIMARY KEY(\"controller\")\n" +
                ");";
    }

    @Override
    public String getSelect() {
        return "Select * FROM SupplierController\n";
    }

    @Override
    public String getDelete() {
        return "DELETE FROM SupplierController\n" +
                "WHERE controller= ?;";
    }

    @Override
    public String getUpdate() {
        return "UPDATE SupplierController\n" +
                "SET (?) = (?)\n"+
                "WHERE controller = ?;";
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
                "WHERE controller = ?;";
        list.add(new Tuple<>(numOfItems+1, Integer.class));
        list.add(new Tuple<>(supplierBN, Integer.class));
        DC.noSelect(query, list);
        numOfItems++;
    }

    public void addNumOfOrders() throws Exception {
        LinkedList<Tuple<Object,Class>> list = new LinkedList<>();
        String query = "UPDATE SupplierController\n" +
                "SET numOfItems = ?\n" +
                "WHERE controller = ?;";
        list.add(new Tuple<>(numOfOrders+1, Integer.class));
        list.add(new Tuple<>(supplierBN, Integer.class));
        DC.noSelect(query, list);
        numOfOrders++;
    }

    public List<Tuple<List<Class>,List<Object>>> load1() {
        try {
            String query = "SELECT * FROM Suppliers;";
            LinkedList<Integer> list = new LinkedList<>();
            List<Tuple<List<Class>,List<Object>>> tuple = DC.SelectMany(query, list);
            return tuple;
        }
        catch (Exception e){
            log.warn(e);
        }
        return null;
    }

}
