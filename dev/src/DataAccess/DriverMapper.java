package DataAccess;

import Business.Transportation.Driver;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DriverMapper extends Mapper{

    static  private DriverMapper mapper=null;
    private Map <Integer, Driver> drivers;

    public static DriverMapper getMapper(){
        if(mapper==null){
            mapper=new DriverMapper();
        }
        return mapper;
    }

    private DriverMapper(){
        super();
        drivers=new HashMap<>();
    }

    public HashMap<Integer, Driver>  selectAll() throws Exception {
        String sql = "SELECT * FROM Drivers";
        HashMap<Integer, Driver>  ret=new HashMap<>();
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            while (rs.next()) {
               ret.put(rs.getInt("EID"),new Driver(rs.getInt("EID"),rs.getInt("License")));
            }
        } catch (SQLException e) {
            throw new IOException("failed to get all branches from database");
        }
        return ret;
    }

    public void insert(int eid, int license){

        String query = "INSERT INTO Drivers (EID,License) VALUES(?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1,eid );
            pstmt.setInt(2,license );
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public Driver select(int id) throws  Exception {

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

}
