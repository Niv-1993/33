package DataAccess;

import Business.Employees.EmployeePKG.Driver;
import Business.Transportation.Order;
import Business.Transportation.Transportation;
import Business.Transportation.Truck;
import Business.Type.Area;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TransportationMapper extends Mapper{

    static  private TransportationMapper mapper=null;
    private HashMap<Long,Transportation> transportations;

    public static TransportationMapper getMapper( ){
        if(mapper==null){
            mapper=new TransportationMapper();
        }
        return mapper;
    }

    private TransportationMapper( ){
        transportations=new HashMap<>();
    }

    private List< Transportation> selectAll(TruckMapper tru,DriverMapper driverMapper) throws Exception {
        //TODO:implement with kfir orders
        String sql = "SELECT * FROM Transportations ";
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            // loop through the result set
            while (rs.next()) {
                Driver tempD= driverMapper.select(rs.getInt("driverID"));
                Truck tempT= tru.getTruck(rs.getLong("truckID"));
                Long tID=rs.getLong("ID");
                LocalDate date=LocalDate.parse( rs.getString("Date"));
                LocalTime time=LocalTime.parse(rs.getString("LeavingTime"));
                int weight=rs.getInt("Weight");
                Area area= Area.valueOf(rs.getString("Area"));
                //TODO:add the orders hashmap.
                //TODO: add transportation to hashmap

            }
        } catch (SQLException e) {
            throw new IOException("failed to get all branches from database");
        }
        return new ArrayList<>(transportations.values());
    }

    private Transportation select(long id,TruckMapper tru,BranchMapper branchMapper,DriverMapper driverMapper) throws Exception{
    //TODO:implement with kfir orders
        String sql = "SELECT * FROM Transactions WHERE ID="+ id ;
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            // loop through the result set
            while (rs.next()) {
                Driver tempD= driverMapper.select(rs.getInt("driverID"));
                Truck tempT= tru.getTruck(rs.getInt("truckID"));
                Long tID=rs.getLong("ID");
                Area area= Area.valueOf(rs.getString("Area"));
                LocalDate date=LocalDate.parse( rs.getString("Date"));
                LocalTime time=LocalTime.parse(rs.getString("LeavingTime"));
                int weight=rs.getInt("Weight");
               //TODO:create new transportation.
            }
        } catch (SQLException e) {
            throw new IOException("failed to get all branches from database");
        }
        return null;
    }


    private void insert(long id, String area, String date,String time, int weight, long driverID,long truckID) throws Exception {
        String sql = "INSERT INTO Transportations (ID,Area,Date,LeavingTime,Weight,driverID,truckID) VALUES(?,?,?,?,?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1,id );
            pstmt.setString(2,area );
            pstmt.setString(3,date );
            pstmt.setString(4,time );
            pstmt.setInt(5, weight);
            pstmt.setLong(6, driverID);
            pstmt.setLong(7, truckID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addTransportation(Transportation tra) throws Exception {
        transportations.put(tra.getId(),tra);
        insert(tra.getId(),tra.getArea().toString(),tra.getDate().toString(),tra.getLeavingTime().toString(),tra.getWeight(),tra.getDriver().getEID(),tra.getTruck().getId());
    }
    public Transportation getTransportation(long id, TruckMapper truckMapper, BranchMapper branchMapper,DriverMapper driverMapper) throws Exception {

        if(transportations.containsKey(id)){
            return transportations.get(id);
        }
        else {
            Transportation tra=select(id,truckMapper,branchMapper, driverMapper);
            if(tra!=null)
                return tra;
            throw new IllegalArgumentException("No transportation match to id:" + id);
        }
    }

    public List<Transportation> getTransportations(TruckMapper truckMapper,DriverMapper driverMapper) throws Exception {
        return selectAll(truckMapper, driverMapper);
    //TODO:implemet after get suppliers from kfir.

    }

    public void remove(long idCounter) {
        transportations.remove(idCounter);
    }

    public void setDriverOnTrans(long transId, Driver driver) {
        transportations.get(transId).setDriver(driver);
    }
    public void setTruckOnTrans(long transId, Truck truck) {
        transportations.get(transId).setTruck(truck);
    }

    public void setTime(long id, LocalTime leavingTime) {
        transportations.get(id).setLeavingTime(leavingTime);
    }

    public void setWeight(long id, int weight) {
        transportations.get(id).setWeight(weight);
    }

    public void setDate(long id, LocalDate date) {
        transportations.get(id).setDate(date);
    }

    public long getCurrId() throws Exception {

        String sql = "SELECT MAX(ID) AS MA FROM Transportations" ;
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            while (rs.next()) {
                return rs.getLong("MA");
            }
        }
        catch (SQLException e) {
            throw new IOException("failed to get all branches from database");
        }
        return -1;
    }

    public void addTransportation(int id, String area, String date, String time, int weight, int driverID, int truckID) {

        String sql = "INSERT INTO Transportations (ID,Area,Date,LeavingTime,Weight,driverID,truckID) VALUES(?,?,?,?,?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1,id );
            pstmt.setString(2,area );
            pstmt.setString(3,date );
            pstmt.setString(4,time );
            pstmt.setInt(5, weight);
            pstmt.setLong(6, driverID);
            pstmt.setLong(7, truckID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public  List<Transportation> getTransportationsByArea( DriverMapper driverMapper, TruckMapper truckMapper,Area area) {
       return getTransportationsByArea(truckMapper,driverMapper, area);
    }

    private List<Transportation> getTransportationsByArea(TruckMapper truckMapper,  DriverMapper driverMapper, Area area) {
        String sql = "SELECT * FROM Transactions WHERE Area="+ area  ;
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            // loop through the result set
            while (rs.next()) {
                Driver tempD= driverMapper.select(rs.getInt("driverID"));
                Truck tempT= truckMapper.getTruck(rs.getInt("truckID"));
                Long tID=rs.getLong("ID");
                Area are= Area.valueOf(rs.getString("Area"));
                LocalDate date=LocalDate.parse( rs.getString("Date"));
                LocalTime time=LocalTime.parse(rs.getString("LeavingTime"));
                int weight=rs.getInt("Weight");
                //TODO: implement creating new transportation.
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void updateTransWeight(long id, int weight, Order order) {
        String sql = "UPDATE Transportations " +
                "SET Weight="+weight
                +"Where ID="+id;
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Transportation tra=transportations.get(id);
        tra.setWeight(weight);
        if(!tra.containOrder(order.getOrderId())){
            tra.addOrder(order);
        }else {
            tra.replaceOrder(order);
        }
        transportations.replace(id,tra);
    }

    public List<Transportation> getTransportations(int currBID, LocalDate date, LocalTime time,TruckMapper tm,DriverMapper dm) {
        String tim="AND LeavingTime < 14:00";
        if(time.compareTo(LocalTime.parse("14:00"))>=0)
            tim="AND LeavingTime >= 14:00";
        String sql = "SELECT * FROM Transactions WHERE Date="+ date +tim  ;
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            // loop through the result set
            while (rs.next()) {
                Driver tempD= dm.select(rs.getInt("driverID"));
                Truck tempT= tm.getTruck(rs.getInt("truckID"));
                Long tID=rs.getLong("ID");
                LocalDate Date=LocalDate.parse( rs.getString("Date"));
                LocalTime leavingTime=LocalTime.parse(rs.getString("LeavingTime"));
                Area area= Area.valueOf(rs.getString("Area"));
                int weight=rs.getInt("Weight");
                //TODO: implement creating new transportation.
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
