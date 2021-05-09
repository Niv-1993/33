package DataAccess;


import Business.Transportation.Address;
import Business.Transportation.ShippingArea;
import Business.Transportation.Supplier;
import Business.Type.Area;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SupplierMapper extends Mapper {

    static  private SupplierMapper mapper=null;
    static  private String dbName;
    private HashMap<Integer, Supplier> suppliers;

    public static SupplierMapper getMapper(String name){
        if(mapper==null){
            mapper=new SupplierMapper(name);
        }
        return mapper;
    }

    private SupplierMapper(String name){
        dbName=name;
        suppliers=new HashMap<>();
    }

    private List<Supplier> selectAll() throws Exception {
        String sql = "SELECT * FROM Suppliers";
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            // loop through the result set
            while (rs.next()) {
                Address add=new Address(rs.getInt("Number"),rs.getString("Street"),rs.getString("City"));
                ShippingArea are=new ShippingArea(Area.valueOf(rs.getString("Area")));
                suppliers.put( rs.getInt("ID"),new Supplier(rs.getString("Phone"),rs.getString("Contact"),rs.getInt("ID"),add,are));
            }
        } catch (SQLException e) {
            throw new IOException("failed to get all suppliers from database");
        }
        return new ArrayList<>(suppliers.values());
    }
    public static Connection connect() throws Exception {

        //DriverManager.registerDriver(new com.sqlite.jdbc.Driver());
        Class.forName("org.sqlite.JDBC");
        String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "\\" + dbName;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new Exception("failed to connect database.");
        }
        return conn;
    }

    private Supplier select(int id) throws Exception {

        String sql = "SELECT * FROM Suppliers WHERE ID="+ id ;
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            // loop through the result set
            while (rs.next()) {
                Address add=new Address(rs.getInt("Number"),rs.getString("Street"),rs.getString("City"));
                ShippingArea are=new ShippingArea(Area.valueOf(rs.getString("Area")));
                return new Supplier(rs.getString("Phone"),rs.getString("Contact"),rs.getInt("ID"),add,are);
            }
        } catch (SQLException e) {
            throw new IOException("failed to get all branches from database");
        }
        //TODO:return transportation object.
        return null;
    }

    public Supplier getSupplier(int id) throws Exception {

        if(suppliers.containsKey(id)){
            return suppliers.get(id);
        }
        else {
            Supplier sup= select(id);
            if(sup!=null){
                return sup;
            }
            throw new IllegalArgumentException("supplier with id: " + id +"does not exist");
        }

    }

    public List< Supplier> getSuppliers() throws Exception {
        return selectAll();
    }
}
