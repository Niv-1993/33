package DAL.DalSuppliers;

import DAL.DALObject;
import DAL.DalController;

import java.util.Dictionary;

public class DalSupplierCard extends DALObject {
    private int supplierBN;
    private String supplierName;
    private int bankNumber;
    private int branchNumber;
    private int accountNumber;
    private String payWay;
    private Dictionary<String , String> contactPhone;
    private Dictionary<String , String> contactEmail;

    public DalSupplierCard() {
        super(null);
    }

    public DalSupplierCard(Integer supplierBN , String supplierName , String payWay , DalController dalController) {
        super(dalController);
    }

    @Override
    public String getCreate() {
        return "CREATE TABLE IF NOT EXISTS \"Suppliers\"(\n" +
                "\t\"supplierBN\" INTEGER NOT NULL,\n" +
                "\t\"supplierName\" VARCHAR NOT NULL,\n" +
                "\t\"payWay\" VARCHAR NOT NULL,\n" +
                "\tPRIMARY KEY(\"supplierBN\")\n" +
                "\tFOREIGN KEY(\"supplierBN\") REFERENCES \"SupplierController\"(\"supplierBN\") ON DELETE CASCADE ON UPDATE CASCADE\n" +
                ");" +
                "CREATE TABLE IF NOT EXISTS \"BankInfo\"(\n" +
                "\t\"supplierBN\" INTEGER NOT NULL,\n" +
                "\t\"bankNumber\" INTEGER NOT NULL,\n" +
                "\t\"branchNumber\" INTEGER NOT NULL,\n" +
                "\t\"bankAccount\" INTEGER NOT NULL,\n" +
                "\tPRIMARY KEY(\"supplierBN\"),\n" +
                "\tFOREIGN KEY(\"supplierBN\") REFERENCES \"Suppliers\"(\"supplierBN\") ON DELETE CASCADE ON UPDATE CASCADE\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE IF NOT EXISTS \"SupplierPhones\"(\n" +
                "\t\"supplierBN\" INTEGER NOT NULL,\n" +
                "\t\"phone\" TEXT NOT NULL,\n" +
                "\t\"name\" TEXT NOT NULL,\n" +
                "\tPRIMARY KEY(\"phone\"),\n" +
                "\tFOREIGN KEY(\"supplierBN\") REFERENCES \"Suppliers\"(\"supplierBN\") ON DELETE CASCADE ON UPDATE CASCADE\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE IF NOT EXISTS \"SupplierEmails\"(\n" +
                "\t\"supplierBN\" INTEGER NOT NULL,\n" +
                "\t\"email\" TEXT NOT NULL,\n" +
                "\t\"name\" TEXT NOT NULL,\n" +
                "\tPRIMARY KEY(\"email\"),\n" +
                "\tFOREIGN KEY(\"supplierBN\") REFERENCES \"Suppliers\"(\"supplierBN\")  ON DELETE CASCADE ON UPDATE CASCADE\n" +
                ");";
    }

    @Override
    public String getSelect() {
        return null;
    }

    @Override
    public String getDelete() {
        return null;
    }

    @Override
    public String getUpdate() {
        return null;
    }

    @Override
    public String getInsert() {
        return null;
    }
}
