package DataLayer;

import BussinessLayer.*;
import BussinessLayer.Driver;
import enums.Pair;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class TransportationMapper {

    static  private TransportationMapper mapper=null;
    static  private String dbName;
    private HashMap<Long,Transportation> transportations;

    public static TransportationMapper getMapper(String name){
        if(mapper==null){
            mapper=new TransportationMapper(name);
        }
        return mapper;
    }

    private TransportationMapper(String name){
        dbName=name;
        transportations=new HashMap<>();
    }

    private List< Transportation> selectAll(TruckMapper tru,ItemMapper item,SupplierMapper supplierMapper,BranchMapper branchMapper) throws Exception {
        String sql = "SELECT * FROM Transportations ";
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            // loop through the result set
            while (rs.next()) {
                Driver tempD= selectDriver(rs.getLong("driverID"));
                Truck tempT= tru.getTruck(rs.getLong("truckID"));
                Long tID=rs.getLong("ID");
                HashMap<Supplier,List<Pair<Item,Integer>>> supplierListMap= getSuppliersItems(tID,item,supplierMapper);
                HashMap<Branch,List<Pair<Item,Integer>>> branchesListMap=getBranchesItems(tID,item,branchMapper);
                LocalDate date=LocalDate.parse( rs.getString("Date"));
                LocalTime time=LocalTime.parse(rs.getString("LeavingTime"));
                int weight=rs.getInt("Weight");
                transportations.put( tID,new Transportation(tID,date,time,
                        tempD,tempT,weight,branchesListMap,supplierListMap));

            }
        } catch (SQLException e) {
            throw new IOException("failed to get all branches from database");
        }
        return new ArrayList<>(transportations.values());
    }

    private Transportation select(long id,TruckMapper tru,ItemMapper item,SupplierMapper supplierMapper,BranchMapper branchMapper) throws Exception{

        String sql = "SELECT * FROM Transactions WHERE ID="+ id ;
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            // loop through the result set
            while (rs.next()) {
                Driver tempD= selectDriver(rs.getInt("driverID"));
                Truck tempT= tru.getTruck(rs.getInt("truckID"));
                Long tID=rs.getLong("ID");
                HashMap<Supplier,List<Pair<Item,Integer>>> supplierListMap= getSuppliersItems(tID,item,supplierMapper);
                HashMap<Branch,List<Pair<Item,Integer>>> branchesListMap=getBranchesItems(tID,item,branchMapper);
                return new Transportation(tID,rs.getDate("Date").toLocalDate(),rs.getTime("LeavingTime").toLocalTime(),
                        tempD,tempT,rs.getInt("Weight"),branchesListMap,supplierListMap);
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
    public Driver selectDriver(long id) throws  Exception {

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
    public HashMap<Supplier,List<Pair<Item,Integer>>> getSuppliersItems(long id,ItemMapper itemMapper,SupplierMapper supplierMapper) throws  Exception{

        String sql = "SELECT SupID,ItemID,Quantity FROM SupplierItemsOnTran WHERE TranID="+ id ;
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            HashMap<Supplier,List<Pair<Item,Integer>>> ret =new HashMap<>();
            while (rs.next()) {
                int suplier= rs.getInt("SupID");
                long item =rs.getInt("ItemID");
                int quantity= rs.getInt("Quantity");
                Supplier sup= supplierMapper.getSupplier(suplier);
                if (!ret.containsKey(sup)) {
                    ret.put(sup, new LinkedList<>());
                }
                ret.get(sup).add(new Pair<>(itemMapper.getItem(item),quantity ));
            }
            return ret;
        } catch (SQLException e) {
            throw new IOException("failed to get all branches from database");
        }
    }
    public HashMap<Branch,List<Pair<Item,Integer>>> getBranchesItems(long id,ItemMapper itemMapper,BranchMapper branchMapper) throws  Exception{

        String sql = "SELECT BranID,ItemID,Quantity FROM BranchesItemsOnTran WHERE TranID="+ id ;
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            HashMap<Branch,List<Pair<Item,Integer>>> ret =new HashMap<>();
            while (rs.next()) {
                int branch= rs.getInt("BranID");
                long item =rs.getInt("ItemID");
                int quantity= rs.getInt("Quantity");
                Branch bra=branchMapper.getBranch(branch);
                if (!ret.containsKey(bra)) {
                    ret.put(bra, new LinkedList<>());
                }
                ret.get(bra).add(new Pair<>(itemMapper.getItem(item),quantity ));
            }
            return ret;
        } catch (SQLException e) {
            throw new IOException("failed to get all branches to transportations from database");
        }

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
    private void insertlists(String command) throws Exception {

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(command)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void addTransportation(long idCounter, Transportation tra) {

        transportations.put(idCounter,tra);
    }
    public void saveTransportation(long id) throws Exception {
        Transportation tra=transportations.get(id);
        insert(id,tra.getShippingArea().getArea().toString(),tra.getDate().toString(),tra.getLeavingTime().toString(),tra.getWeight(),tra.getDriver().getId(),tra.getTruck().getId());
        for(Map.Entry<Supplier, List<Pair<Item, Integer>>> set : tra.getSuppliers().entrySet()) {
            for (Pair<Item, Integer> pai : set.getValue()) {
                String com = "INSERT INTO SupplierItemsOnTran VALUES(" + set.getKey().toString() + "," + id+","+pai.getFir()+","+pai.getSec()+")";
                insertlists(com);
            }
        }
        for(Map.Entry<Branch, List<Pair<Item, Integer>>> set : tra.getDeliveryItems().entrySet()) {
            for (Pair<Item, Integer> pai : set.getValue()) {
                String com = "INSERT INTO BranchesItemsOnTran VALUES(" + set.getKey().toString() + "," + id+","+pai.getFir()+","+pai.getSec()+")";
                insertlists(com);
            }
        }
    }


    public Transportation getTransportation(long id, TruckMapper truckMapper, ItemMapper itemMapper, SupplierMapper supplierMapper, BranchMapper branchMapper) throws Exception {

        if(transportations.containsKey(id)){
            return transportations.get(id);
        }
        else {
            Transportation tra=select(id,truckMapper,itemMapper,supplierMapper,branchMapper);
            if(tra!=null)
                return tra;
            throw new IllegalArgumentException("No transportation match to id:" + id);
        }
    }

    public List<Transportation> getTransportations(TruckMapper truckMapper, ItemMapper itemMapper, SupplierMapper supplierMapper, BranchMapper branchMapper) throws Exception {
        return selectAll(truckMapper,itemMapper,supplierMapper,branchMapper);
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

    public void setDeliveryItems(long transId, HashMap<Branch, List<Pair<Item, Integer>>> deliveryItems) {
        transportations.get(transId).setDeliveryItems(deliveryItems);
    }
    public void setSuppliersItems(long transId, HashMap<Supplier, List<Pair<Item, Integer>>> deliveryItems) {
        transportations.get(transId).setSuppliers(deliveryItems);
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
}
