package DAL.DalSuppliers;

import DAL.DALObject;
import DAL.DalController;
import Utility.Tuple;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class DalItem extends DALObject {
    private int supplierBN;
    private int itemId;
    private String name;
    private double price;
    private int typeId;
    private String expirationDate;
    final static Logger log=Logger.getLogger(DalItem.class);

    public DalItem() {
        super(null);
    }

    public DalItem(Integer itemId , Integer supplierBN , String itemName , Double price , Integer typeId , String expirationDate , DalController dalController){
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
                "\tPRIMARY KEY(\"itemID\"),\n" +
                "\tFOREIGN KEY(\"supplierBN\") REFERENCES \"Suppliers\"(\"supplierBN\") ON DELETE CASCADE ON UPDATE CASCADE\n" +
                ");";
    }

    @Override
    public String getSelect() {
        return "Select * FROM Items\n" +
                "WHERE itemId = ?;";
    }

    @Override
    public String getDelete() {
        return "DELETE FROM Items\n" +
                "WHERE itemId= ?;";
    }

    @Override
    public String getUpdate() {
        return "UPDATE Items\n" +
                "SET (?) = (?)\n"+
                "WHERE itemId = ?;";
    }

    @Override
    public String getInsert() {
        return "INSERT OR REPLACE INTO Items\n"+
                "VALUES (?,?,?,?,?,?);";
    }

    public int getItemId() {
        return itemId;
    }

    public double getPrice() {
        try {
            String query = "SELECT price FROM Items\n" +
                    "WHERE itemId = ?;";
            LinkedList<Integer> list = new LinkedList<>();
            list.add(itemId);
            Tuple<List<Class>,List<Object>> tuple = DC.Select(query, list);
            price = (Double) tuple.item2.get(0);
        }
        catch (Exception e){
            log.warn(e);
        }
        return price;
    }

    public String getName() {
        String Temp = "";
        try {
            String query = "SELECT itemName FROM Items\n" +
                    "WHERE itemId = ?;";
            LinkedList<Integer> list = new LinkedList<>();
            list.add(itemId);
            Tuple<List<Class>,List<Object>> tuple = DC.Select(query, list);
            Temp = tuple.item2.get(0).toString();
        }
        catch (Exception e){
            log.warn(e);
        }
        name= Temp;
        return name;
    }

    public int getTypeID() {
        try {
            String query = "SELECT typeId FROM Items\n" +
                    "WHERE itemId = ?;";
            LinkedList<Integer> list = new LinkedList<>();
            list.add(itemId);
            Tuple<List<Class>, List<Object>> tuple = DC.Select(query, list);
            typeId = (Integer) tuple.item2.get(0);
        }
        catch (Exception e) {
            log.warn(e);
        }
        return typeId;
    }

    public String getExpirationDate() {
        String temp = "";
        try {
            String query = "SELECT expirationDate FROM Items\n" +
                    "WHERE itemId = ?;";
            LinkedList<Integer> list = new LinkedList<>();
            list.add(itemId);
            Tuple<List<Class>, List<Object>> tuple = DC.Select(query, list);
            temp = tuple.item2.get(0).toString();
        }
        catch (Exception e) {
            log.warn(e);
        }
        expirationDate = temp;
        return expirationDate;
    }

    public void updatePrice(double price) throws Exception {
        this.price = price;
        LinkedList<Tuple<Object,Class>> list = new LinkedList<>();
        String query = "UPDATE Items \n" +
                "SET price = ? \n"+
                "WHERE itemId = "+ itemId;
        list.add(new Tuple<>(price, Integer.class));
        DC.noSelect(query, list);
    }

}
