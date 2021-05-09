package DataAccess;


import Business.Transportation.Truck;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TruckMapper extends Mapper{

    static  private TruckMapper mapper=null;
    static  private String dbName;
    private Map<Long,Truck> trucks;

    public static TruckMapper getMapper(String name){
        if(mapper==null){
            mapper=new TruckMapper(name);
        }
        return mapper;
    }

    private TruckMapper(String name){
        dbName=name;
        trucks=new HashMap<>();
    }

    private List< Truck> selectAll() throws Exception {
        String sql = "select * from Trucks";
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement()){
            ResultSet rs    = stmt.executeQuery(sql);
            while (rs.next()) {
                Truck tr=new Truck(rs.getInt("ID"),rs.getInt("License"),rs.getInt("MaxWeight"),rs.getInt("NetWeight"),rs.getString("Model"));
               trucks.put(rs.getLong("ID"),tr);
            }
        } catch (SQLException e) {
            throw new IOException("failed to get all branches from database");
        }
        return new ArrayList<>(trucks.values());
    }

    private Truck select(long id) throws  Exception {

            String sql = "SELECT * FROM Trucks WHERE ID=" + id;
            try (Connection conn = connect();
                 Statement stmt  = conn.createStatement();
                 ResultSet rs    = stmt.executeQuery(sql)){
                // loop through the result set
                while (rs.next()) {
                    return new Truck(rs.getLong("ID"),rs.getInt("License"),rs.getInt("MaxWeight"),rs.getInt("NetWeight"),rs.getString("Model"));
                }
            } catch (SQLException e) {
                throw new IOException("failed to get all branches from database");
            }
            //TODO:return transportation object.
            return null;
    }

    public List<Truck> getTrucks() throws Exception {

        return selectAll();
    }

    public Truck getTruck(long id) throws Exception {

        if(trucks.containsKey(id))
            return trucks.get(id);
        else{
            Truck it=select(id);
            if (it!=null)
                return it;
            throw new IOException("Truck does not exists");
        }
    }
}
