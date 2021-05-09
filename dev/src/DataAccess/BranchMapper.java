package DataAccess;

import Business.Transportation.Address;
import Business.Transportation.Branch;
import Business.Transportation.ShippingArea;
import Business.Type.Area;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BranchMapper {

    static private BranchMapper mapper = null;
    static private String dbName;
    private HashMap<Integer, Branch> branches;


    public static BranchMapper getMapper(String dbName) {
        if (mapper == null) {
            mapper = new BranchMapper(dbName);
        }
        return mapper;
    }

    private BranchMapper(String name) {
        branches = new HashMap<>();
        dbName=name;
    }

    /**
     * Connect to a sample database
     */
    public static Connection connect() throws Exception {

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

    /**
     * select all rows in the Branch table
     */
    private List<Branch> selectAll() throws Exception {
        String sql = "SELECT * FROM Branchs";
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            // loop through the result set
            while (rs.next()) {
                Address add=new Address(rs.getInt("Number"),rs.getString("Street"),rs.getString("City"));
                ShippingArea are=new ShippingArea(Area.valueOf(rs.getString("Area")));
                branches.put(rs.getInt("BID"), new Branch(rs.getString("Phone"),rs.getString("ContactName"),rs.getInt("BID"),add,are));
            }
        } catch (SQLException e) {
            throw new IOException("failed to get all branches from database");
        }
        return new ArrayList<>(branches.values());
    }

    private Branch select(int id) throws  Exception{

        String sql = "SELECT * FROM Branchs WHERE BID="+ id ;
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            // loop through the result set
            while (rs.next()) {
                Address add=new Address(rs.getInt("Number"),rs.getString("Street"),rs.getString("City"));
                ShippingArea are=new ShippingArea(Area.valueOf(rs.getString("Area")));
               return new Branch(rs.getString("Phone"),rs.getString("ContactName"),rs.getInt("BID"),add,are);
            }
        } catch (SQLException e) {
            throw new IOException("failed to get all branches from database");
        }
        //TODO:return transportation object.
        return null;
    }

    public List<Branch> getBranches() throws Exception {
        return selectAll();
    }

    public Branch getBranch(int id) throws Exception {
        if(branches.containsKey(id)){
            return branches.get(id);
        }
        else {
            Branch sup= select(id);
            if(sup!=null){
                return sup;
            }
            throw new IllegalArgumentException("branch with id: " + id +"does not exist");
        }
    }
}
