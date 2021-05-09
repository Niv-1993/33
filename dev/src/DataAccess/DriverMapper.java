package DataAccess;

import Business.Transportation.Driver;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DriverMapper {

    static  private DriverMapper mapper=null;
    private Map <Long, Driver> drivers;
    static  private String dbName;

    public static DriverMapper getMapper(String name){
        if(mapper==null){
            mapper=new DriverMapper(name);
        }
        return mapper;
    }

    private DriverMapper(String name){
        drivers=new HashMap<>();
        dbName=name;
    }

    public HashMap<Long, Driver>  selectAll() throws Exception {
        String sql = "SELECT * FROM Drivers";
        HashMap<Long, Driver>  ret=new HashMap<>();
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            while (rs.next()) {
               ret.put(rs.getLong("EID"),new Driver(rs.getInt("EID"),rs.getInt("License")));
            }
        } catch (SQLException e) {
            throw new IOException("failed to get all branches from database");
        }
        return ret;
    }

    public Driver select(long id) throws  Exception {

        String sql = "SELECT * FROM Drivers WHERE EID="+ id ;
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            while (rs.next()) {
                return new Driver(rs.getInt("EID"),rs.getInt("License"));

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

}
