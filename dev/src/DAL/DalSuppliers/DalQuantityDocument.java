package DAL.DalSuppliers;

import DAL.DALObject;
import DAL.DalController;
import Utility.Tuple;

import java.util.LinkedList;

public class DalQuantityDocument extends DALObject {
    private int minimalAmount;
    private int discount;
    private int itemId;
    private int branchId;

    public DalQuantityDocument() {
        super(null);
    }

    public DalQuantityDocument(Integer itemId , Double totalAmount , String deliverTime , Integer branchId , DalController dalController){
        super(dalController);
    }

    @Override
    public String getCreate() {
        return "CREATE TABLE IF NOT EXISTS \"SupplierAgreements\"(\n"+
                "\"itemId\" INTEGER NOT NULL,\n" +
                "\t\"minimalAmount\" DOUBLE NOT NULL,\n" +
                "\t\"discount\" VARCHAR NOT NULL,\n" +
                "\t\"branchId\" INTEGER NOT NULL,\n" +
                "\tPRIMARY KEY(\"itemId\"),\n" +
                "\tFOREIGN KEY(\"itemId\") REFERENCES \"Items\"(\"itemId\") ON DELETE CASCADE ON UPDATE CASCADE\n" +
                ");";
    }

    @Override
    public String getSelect() {
        return "Select * FROM QuantityDocuments\n" +
                "WHERE itemId = "+ itemId;
    }

    @Override
    public String getDelete() {
       return "DELETE FROM QuantityDocuments\n" +
               "WHERE itemId = "+ itemId;
    }

    @Override
    public String getUpdate() {
        return "UPDATE QuantityDocuments \n" +
                "SET (?) = (?) \n"+
                "WHERE itemId = "+ itemId;
    }

    @Override
    public String getInsert() {
        return "INSERT INTO QuantityDocuments ?\n"+
                "VALUES ?";
    }

    public int getMinimalAmount(){
        return minimalAmount;
    }

    public int getDiscount(){
        return discount;
    }

    public void updateMinimalAmountOfQD(int minimalAmount) throws Exception {
        this.minimalAmount = minimalAmount;
        LinkedList<Tuple<Object,Class>> list = new LinkedList<>();
        String query = "UPDATE QuantityDocuments \n" +
                    "SET minimalAmount = ? \n"+
                    "WHERE itemId = "+ itemId;
        list.add(new Tuple<>(minimalAmount, Integer.class));
        DC.noSelect(query, list);
    }

    public void updateDiscountOfQD(int discount) throws Exception {
        this.discount = discount;
        LinkedList<Tuple<Object,Class>> list = new LinkedList<>();
        String query = "UPDATE QuantityDocuments \n" +
                "SET discount = ? \n"+
                "WHERE itemId = "+ itemId;
        list.add(new Tuple<>(discount, Integer.class));
        DC.noSelect(query, list);
    }

}
