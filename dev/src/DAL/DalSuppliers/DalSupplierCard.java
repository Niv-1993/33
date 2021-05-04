package DAL.DalSuppliers;

import DAL.DALObject;
import DAL.DalController;
import Utility.Tuple;

import java.util.Dictionary;
import java.util.LinkedList;

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
        return "Select * FROM Suppliers\n" +
                "WHERE supplierBN = "+ supplierBN;
    }

    @Override
    public String getDelete() {
        return "DELETE FROM Suppliers\n" +
                "WHERE supplierBN = "+ supplierBN;
    }

    @Override
    public String getUpdate() {
        return "UPDATE (?)\n" +
                "SET (?) = (?)\n"+
                "WHERE SupplierBN = "+ supplierBN;
    }

    @Override
    public String getInsert() {
        return "INSERT OR REPLACE INTO Suppliers\n"+
                "VALUES (?,?,?)";
    }

    public int getSupplierBN() {
        return supplierBN;
    }

    public int getSupplierBankNumber() {
        return bankNumber;
    }

    public int getSupplierBranchNumber() {
        return branchNumber;
    }

    public int getSupplierAccountNumber() {
        return accountNumber;
    }

    public String getSupplierPayWay() {
        return payWay;
    }

    public Dictionary<String , String> getContactPhone() {
        return contactPhone;
    }

    public Dictionary<String , String> getContactEmail() {
        return contactEmail;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void updateSupplierPayWay(String payWay) throws Exception {
        this.payWay = payWay;
        String p = payWay;
        LinkedList<Tuple<Object,Class>> list = new LinkedList<>();
        String query = "UPDATE Suppliers\n" +
                "SET payWay = ?\n"+
                "WHERE supplierBN = "+ supplierBN;
        list.add(new Tuple<>(p, Integer.class));
        DC.noSelect(query, list);

    }

    public void addContactPhone(String phone, String name) throws Exception {
        contactPhone.put(phone, name);
        String query = "INSERT INTO SupplierPhones (supplierBN, phone, name)\n" +
                "VALUES (?,?,?)";
        LinkedList<Tuple<Object,Class>> list = new LinkedList<>();
        list.add(new Tuple<>(supplierBN, Integer.class));
        list.add(new Tuple<>(phone, String.class));
        list.add(new Tuple<>(name, String.class));
        DC.noSelect(query, list);
    }

    public void updateSupplierBankAccount(int bankNumber , int branchNumber , int bankAccount) throws Exception {
        this.bankNumber = bankNumber;
        this.branchNumber = branchNumber;
        this.accountNumber = bankAccount;
        String query = "INSERT INTO BankInfo (supplierBN, bankNumber, branchNumber, bankAccount)\n" +
                "VALUES (?,?,?,?)";
        LinkedList<Tuple<Object,Class>> list = new LinkedList<>();
        list.add(new Tuple<>(supplierBN, Integer.class));
        list.add(new Tuple<>(bankNumber, Integer.class));
        list.add(new Tuple<>(branchNumber, Integer.class));
        list.add(new Tuple<>(bankAccount, Integer.class));
        DC.noSelect(query, list);
    }

    public void addContactEmail(String email, String name) throws Exception {
        contactEmail.put(email, name);
        String query = "INSERT INTO SupplierEmails (supplierBN, email, name)\n" +
                "VALUES (?,?,?)";
        LinkedList<Tuple<Object,Class>> list = new LinkedList<>();
        list.add(new Tuple<>(supplierBN, Integer.class));
        list.add(new Tuple<>(email, String.class));
        list.add(new Tuple<>(name, String.class));
        DC.noSelect(query, list);
    }

    public void removeContactPhone(String phone) throws Exception {
        contactPhone.remove(phone);
        String query = "DELETE From SupplierPhones\n" +
                "WHERE phone = ?";
        LinkedList<Tuple<Object,Class>> list = new LinkedList<>();
        list.add(new Tuple<>(phone, String.class));;
        DC.noSelect(query, list);
    }

    public void removeContactEmail(String email) throws Exception {
        contactEmail.remove(email);
        String query = "DELETE From SupplierEmails\n" +
                "WHERE email = ?";
        LinkedList<Tuple<Object,Class>> list = new LinkedList<>();
        list.add(new Tuple<>(email, String.class));;
        DC.noSelect(query, list);
    }

    public void removeSupplier() throws Exception {
        String query = "DELETE From Suppliers\n" +
                "WHERE supplierBN = "+supplierBN;
        LinkedList<Tuple<Object,Class>> list = new LinkedList<>();;
        DC.noSelect(query, list);
    }
}
