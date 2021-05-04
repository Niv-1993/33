package DAL.DalSuppliers;

import DAL.DALObject;
import DAL.DalController;
import Utility.Tuple;

import java.util.LinkedList;

public class DalSupplierController extends DALObject {
    private int supplierBN;
    private int numOfItems;
    private int numOfOrders;

    public DalSupplierController() {
        super(null);
    }
    public DalSupplierController(Integer supplierBN , Integer numOfItems , Integer numOfOrders , DalController dalController) {
        super(dalController);
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
                "WHERE supplierBN= (?)";
    }

    @Override
    public String getUpdate() {
        return "UPDATE SupplierController\n" +
                "SET (?) = (?)\n"+
                "WHERE supplierBN = (?)";
    }

    @Override
    public String getInsert() {
        return "INSERT OR REPLACE INTO SupplierController \n"+
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
                "SET numOfItems = " + numOfItems+1+"\n" +
                "WHERE supplierBN = "+ supplierBN;
        DC.noSelect(query, list);
        numOfItems++;
    }

    public void addNumOfOrders() throws Exception {
        LinkedList<Tuple<Object,Class>> list = new LinkedList<>();
        String query = "UPDATE SupplierController\n" +
                "SET numOfItems = " + numOfItems+1+"\n" +
                "WHERE supplierBN = "+ supplierBN;
        DC.noSelect(query, list);
        numOfOrders++;
    }
}
