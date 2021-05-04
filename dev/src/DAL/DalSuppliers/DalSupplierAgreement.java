package DAL.DalSuppliers;

import DAL.DALObject;
import DAL.DalController;
import Utility.Tuple;

import java.util.LinkedList;

public class DalSupplierAgreement extends DALObject {
    private int supplierBN;
    private int minimalAmount;
    private int discount;
    private boolean constantTime;
    private boolean shipToUs;

    public DalSupplierAgreement() {
        super(null);
    }

    public DalSupplierAgreement(Integer supplierBN , Integer minimalAmount , Integer discount , Integer constantTime , Integer shipToUs , DalController dalController){
        super(dalController);
    }

    @Override
    public String getCreate() {
        return "CREATE TABLE IF NOT EXISTS \"SupplierAgreements\"(\n" +
                "\t\"supplierBN\" INTEGER NOT NULL,\n" +
                "\t\"minimalAmount\" INTEGER NOT NULL,\n" +
                "\t\"discount\" INTEGER NOT NULL,\n" +
                "\t\"constantTime\" INTEGER NOT NULL,\n" +
                "\t\"shipToUs\" INTEGER NOT NULL,\n" +
                "\tPRIMARY KEY(\"supplierBN\"),\n" +
                "\tFOREIGN KEY(\"supplierBN\") REFERENCES \"Suppliers\"(\"supplierBN\") ON DELETE CASCADE ON UPDATE CASCADE\n" +
                ");";
    }

    @Override
    public String getSelect() {
        return "Select * FROM SupplierAgreements\n" +
                "WHERE supplierBN = "+ supplierBN;
    }

    @Override
    public String getDelete() {
        return "DELETE FROM SupplierAgreements\n" +
                "WHERE supplierBN = "+ supplierBN;
    }

    @Override
    public String getUpdate() {
        return "UPDATE SupplierAgreements\n" +
                "SET (?) = (?)\n"+
                "WHERE SupplierBN = "+ supplierBN;
    }

    @Override
    public String getInsert() {
        return "INSERT INTO SupplierAgreements ?\n"+
                "VALUES ?";
    }

    public int getMinimalAmount() {
        return minimalAmount;
    }

    public int getDiscount() {
        return discount;
    }

    public boolean getConstantTime() {
        return constantTime;
    }

    public boolean getShipToUs() {
        return shipToUs;
    }

    public void updateMinimalAmountOfSA(int minimalAmount) throws Exception {
        this.minimalAmount = minimalAmount;
        LinkedList<Tuple<Object,Class>> list = new LinkedList<>();
        String query = "UPDATE SupplierAgreements \n" +
                "SET minimalAmount = ? \n"+
                "WHERE supplierBN = "+ supplierBN;
        list.add(new Tuple<>(minimalAmount, Integer.class));
        DC.noSelect(query, list);
    }

    public void updateDiscountOfSA(int discount) throws Exception {
        this.discount = discount;
        LinkedList<Tuple<Object,Class>> list = new LinkedList<>();
        String query = "UPDATE SupplierAgreements \n" +
                "SET discount = ? \n"+
                "WHERE supplierBN = "+ supplierBN;
        list.add(new Tuple<>(discount, Integer.class));
        DC.noSelect(query, list);
    }

    public void updateConstantTime(boolean constantTime) throws Exception {
        this.constantTime = constantTime;
        LinkedList<Tuple<Object,Class>> list = new LinkedList<>();
        String query = "UPDATE SupplierAgreements \n" +
                "SET constantTime = ? \n"+
                "WHERE supplierBN = "+ supplierBN;
        list.add(new Tuple<>(constantTime, Boolean.class));
        DC.noSelect(query, list);
    }

    public void updateShipToUs(boolean shipToUs) throws Exception {
        this.shipToUs = shipToUs;
        LinkedList<Tuple<Object,Class>> list = new LinkedList<>();
        String query = "UPDATE SupplierAgreements \n" +
                "SET shipToUs = ? \n"+
                "WHERE supplierBN = "+ supplierBN;
        list.add(new Tuple<>(shipToUs, Boolean.class));
        DC.noSelect(query, list);
    }
}
