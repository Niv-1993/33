package DataAccess;

import Business.Transportation.Item;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class ItemMapper {

    static  private ItemMapper mapper=null;
    private HashMap<Long,Item> items;
    static  private String dbName;

    public static ItemMapper getMapper(String dbName){
        if(mapper==null){
            mapper=new ItemMapper(dbName);
        }
        return mapper;
    }
    private ItemMapper(String Name){
        dbName=Name;
        items=new HashMap<>();
    }

    private List<Item> selectAll() throws Exception {
        String sql = "SELECT * FROM Items";
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            // loop through the result set
            while (rs.next()) {
                items.put(rs.getLong("ID"),new Item(rs.getLong("ID"), rs.getString("Name")));
            }
        } catch (SQLException e) {
            throw new IOException("failed to get all branches from database");
        }
        return new ArrayList<>(items.values());
    }
    public List<Item> selectBySupplier(int id) throws Exception {
        String sql = "SELECT ItemID FROM SupplierItems WHERE SupID="+id;
        List< Item> ret=new LinkedList<>();
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            // loop through the result set
            while (rs.next()) {
                Item it=select(rs.getLong("ItemID"));
                items.put(it.getId(), it);
                ret.add(it);
            }
        } catch (SQLException e) {
            throw new IOException("failed to get all branches from database");
        }
        return ret;
    }
    private Item select(long id) throws Exception {
        String sql = "SELECT * FROM Items WHERE ID="+ id ;
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
             while (rs.next()) {
                Item it=new Item(rs.getInt("ID"), rs.getString("Name"));
                items.put(it.getId(), it);
                return it;
            }
        } catch (SQLException e) {
            throw new IOException("failed to get all branches from database");
        }
        return null;
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


    public Item getItem(long id) throws Exception {

        if(items.containsKey(id))
            return items.get(id);
        else{
            Item it=select(id);
            if (it!=null)
                return it;
            throw new IOException("Item does not exists");
        }
    }

    public List<Item> getItems() throws Exception {
        return selectAll(); }
}
