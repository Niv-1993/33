package DataAccess;

import Business.Employees.EmployeePKG.Driver;
import Business.SupplierBusiness.Order;
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

        String sql = "SELECT * FROM Transportations ";
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            List<Transportation> toRet=new ArrayList<>();
            while (rs.next()) {
                Driver tempD= driverMapper.select(rs.getInt("driverID"));
                Truck tempT= tru.getTruck(rs.getLong("truckID"));
                Long tID=rs.getLong("ID");
                LocalDate date=LocalDate.parse( rs.getString("Date"));
                LocalTime time=LocalTime.parse(rs.getString("LeavingTime"));
                int weight=rs.getInt("Weight");
                Area area= Area.valueOf(rs.getString("Area"));
                List<Order> orders= new Order().getOrdersByTransportation(tID.intValue());
                HashMap <Integer,Order> ordersMap=new HashMap<>();
                for (Order order: orders)
                    ordersMap.put(order.getOrderId(), order);
                toRet.add(new Transportation(tID,date,time,tempD,tempT,weight,ordersMap));
            }
            for (Transportation tran: toRet)
                transportations.put(tran.getId(),tran);
            return toRet;
        } catch (SQLException e) {
            throw new IOException("failed to get all branches from database");
        }
    }

    private Transportation select(long id,TruckMapper tru,DriverMapper driverMapper) throws Exception{
        String sql = "SELECT * FROM Transportations WHERE ID="+ id ;
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
                List<Order> orders= new Order().getOrdersByTransportation(tID.intValue());
                HashMap <Integer,Order> ordersMap=new HashMap<>();
                for (Order order: orders)
                    ordersMap.put(order.getOrderId(), order);
                Transportation tran= new Transportation(tID,date,time,tempD,tempT,weight,ordersMap);
                if (!transportations.containsKey(tran.getId()))
                    transportations.put(tran.getId(),tran);
                return tran;
            }
        } catch (SQLException e) {
            throw new IOException("failed to get all branches from database");
        }
        return null;
    }


    private void insert(long id, String area, String date,String time, double weight, long driverID,long truckID) throws Exception {
        String sql = "INSERT INTO Transportations (ID,Area,Date,LeavingTime,Weight,driverID,truckID) VALUES(?,?,?,?,?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1,id );
            pstmt.setString(2,area );
            pstmt.setString(3,date );
            pstmt.setString(4,time );
            pstmt.setDouble(5, weight);
            pstmt.setLong(6, driverID);
            pstmt.setLong(7, truckID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new IOException(e.getMessage());
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
            Transportation tra=select(id,truckMapper, driverMapper);
            if(tra!=null)
                return tra;
            throw new IOException("No transportation match to id:" + id);
        }
    }

    public List<Transportation> getAllTransportations(TruckMapper truckMapper, DriverMapper driverMapper) throws Exception {
        return selectAll(truckMapper, driverMapper);
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

    public void insert(int id, String area, String date, String time, double weight, int driverID, int truckID) throws IOException {

        String sql = "INSERT INTO Transportations (ID,Area,Date,LeavingTime,Weight,driverID,truckID) VALUES(?,?,?,?,?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1,id );
            pstmt.setString(2,area );
            pstmt.setString(3,date );
            pstmt.setString(4,time );
            pstmt.setDouble(5, weight);
            pstmt.setLong(6, driverID);
            pstmt.setLong(7, truckID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new IOException(e.getMessage());
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }


    public List<Transportation> getTransportationsByArea(TruckMapper truckMapper,  DriverMapper driverMapper, Area area) throws IOException {
        String sql = "SELECT * FROM Transportations WHERE Area="+ area  ;
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
             List<Transportation> toRet=new ArrayList<>();
            while (rs.next()) {
                Driver tempD= driverMapper.select(rs.getInt("driverID"));
                Truck tempT= truckMapper.getTruck(rs.getInt("truckID"));
                Long tID=rs.getLong("ID");
                Area are= Area.valueOf(rs.getString("Area"));
                LocalDate date=LocalDate.parse( rs.getString("Date"));
                LocalTime time=LocalTime.parse(rs.getString("LeavingTime"));
                double weight=rs.getDouble("Weight");
                List<Order> orders= new Order().getOrdersByTransportation(tID.intValue());
                HashMap <Integer,Order> ordersMap=new HashMap<>();
                for (Order order: orders)
                    ordersMap.put(order.getOrderId(), order);
                toRet.add(new Transportation(tID,date,time,tempD,tempT,weight,ordersMap));
            }
            return toRet;
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    public void updateTransWeight(long id, double weight, Order order) throws IOException {
        String sql = "UPDATE Transportations " +
                "SET Weight="+weight
                +"Where ID="+id;
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new IOException(e.getMessage());
        } catch (Exception e) {
            throw new IOException(e.getMessage());
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

    public List<Transportation> getTransportationsByDate( LocalDate date, LocalTime time,TruckMapper tm,DriverMapper dm) throws IOException {
        String tim="AND LeavingTime < 14:00";
        if(time.compareTo(LocalTime.parse("14:00"))>=0)
            tim="AND LeavingTime >= 14:00";
        String sql = "SELECT * FROM Transportations WHERE Date="+ date.toString() +tim  ;
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            List<Transportation> toRet= new ArrayList<>();
            // loop through the result set
            while (rs.next()) {
                Long tID=rs.getLong("ID");
                List<Order> orders= new Order().getOrdersByTransportation(tID.intValue());
                HashMap <Integer,Order> ordersMap=new HashMap<>();
                for (Order order: orders) {
                    ordersMap.put(order.getOrderId(), order);
                }
                if(!orders.get(0).isArrived()) {
                    LocalDate Date=LocalDate.parse( rs.getString("Date"));
                    LocalTime leavingTime=LocalTime.parse(rs.getString("LeavingTime"));
                    Area area= Area.valueOf(rs.getString("Area"));
                    int weight=rs.getInt("Weight");
                    Driver tempD= dm.select(rs.getInt("driverID"));
                    Truck tempT= tm.getTruck(rs.getInt("truckID"));
                    Transportation tran = new Transportation(tID, date, time, tempD, tempT, weight, ordersMap);
                    if(!transportations.containsKey(tran.getId()))
                        transportations.put(tran.getId(),tran);
                    toRet.add(tran);
                }
            }
            return toRet;
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    public void remove(Long idCounter) throws IOException {
        String sql = "DELETE FROM  Transactions WHERE ID="+ idCounter ;
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            transportations.remove(idCounter);
            Order order=new Order();
            order.removeOrdersByTransportationId( idCounter.intValue() );
        }
        catch (SQLException e) {
            throw new IOException("failed to delete transportation "+ idCounter );
        } catch (Exception e) {
            throw new IOException("failed to delete transportation "+ idCounter+" Error :" + e.getMessage());
        }
    }
}
