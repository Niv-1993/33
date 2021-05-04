package DAL.DalSuppliers;

import DAL.DALObject;
import DAL.DalController;
import Utility.Tuple;

import java.time.LocalDate;
import java.util.LinkedList;

public class DalItem extends DALObject {
    private int supplierBN;
    private int itemId;
    private String name;
    private double price;
    private int typeId;
    private String expirationDate;

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
                "WHERE itemId = "+ itemId;
    }

    @Override
    public String getDelete() {
        return "DELETE FROM Items\n" +
                "WHERE itemId= "+ itemId;
    }

    @Override
    public String getUpdate() {
        return "UPDATE Items\n" +
                "SET (?) = (?)\n"+
                "WHERE itemId = "+ itemId;
    }

    @Override
    public String getInsert() {
        return "INSERT INTO Items ?\n"+
                "VALUES ?";
    }

    public int getItemId() {
        return itemId;
    }

    public double getPrice() {
        return price;
    }

    public String getName() { return name; }

    public int getTypeID() { return typeId; }

    public String getExpirationDate() { return expirationDate; }

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
