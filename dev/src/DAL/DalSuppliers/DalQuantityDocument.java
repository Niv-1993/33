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
        LinkedList<Tuple<String,String>> list = new LinkedList<>();
        list.add(new Tuple<>("TEXT", "minimalAmount"));
        list.add(new Tuple<>("INTEGER", Double.toString(minimalAmount)));
        DC.noSelect(getUpdate(), list);
    }

    public void updateDiscountOfQD(int discount) throws Exception {
        this.discount = discount;
        LinkedList<Tuple<String,String>> list = new LinkedList<>();
        list.add(new Tuple<>("TEXT", "discount"));
        list.add(new Tuple<>("INTEGER", Double.toString(discount)));
        DC.noSelect(getUpdate(), list);
    }

}
