package DataAccess;

import Business.Employees.EmployeePKG.Driver;
import Business.Transportation.*;
import Business.Type.Pair;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

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

    private List< Transportation> selectAll(TruckMapper tru,ItemMapper item,SupplierMapper supplierMapper,BranchMapper branchMapper,DriverMapper driverMapper) throws Exception {
        String sql = "SELECT * FROM Transportations ";
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            // loop through the result set
            while (rs.next()) {
                Driver tempD= driverMapper.select(rs.getInt("driverID"));
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

    private Transportation select(long id,TruckMapper tru,ItemMapper item,SupplierMapper supplierMapper,BranchMapper branchMapper,DriverMapper driverMapper) throws Exception{

        String sql = "SELECT * FROM Transactions WHERE ID="+ id ;
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            // loop through the result set
            while (rs.next()) {
                Driver tempD= driverMapper.select(rs.getInt("driverID"));
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

    public void addTransportation(long idCounter, Transportation tra) {

        transportations.put(idCounter,tra);
    }
    public void saveTransportation(long id) throws Exception {
        Transportation tra=transportations.get(id);
        insert(id,tra.getShippingArea().getArea().toString(),tra.getDate().toString(),tra.getLeavingTime().toString(),tra.getWeight(),tra.getDriver().getEID(),tra.getTruck().getId());
        for(Map.Entry<Supplier, List<Pair<Item, Integer>>> set : tra.getSuppliers().entrySet()) {
            for (Pair<Item, Integer> pai : set.getValue()) {
               saveSupplierItemOnTrans(set.getKey().getId(),id,pai.getFir().getId(), pai.getSec());
            }
        }
        for(Map.Entry<Branch, List<Pair<Item, Integer>>> set : tra.getDeliveryItems().entrySet()) {
            for (Pair<Item, Integer> pai : set.getValue()) {
                saveBranchItemOnTrans(set.getKey().getId(),id,pai.getFir().getId(), pai.getSec());
            }
        }
    }

    public void saveSupplierItemOnTrans(long supid,long tranid, long itemid,int quantity){
        String query = "INSERT INTO SupplierItemsOnTran (SupID,TranID,ItemID,Quantity) VALUES(?,?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setLong(1,supid );
            pstmt.setLong(2,tranid );
            pstmt.setLong(3,itemid );
            pstmt.setLong(4,quantity );
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }}
    public void saveBranchItemOnTrans(long branid,long tranid, long itemid,int quantity){
        String query = "INSERT INTO BranchesItemsOnTran (BranID,TranID,ItemID,Quantity) VALUES(?,?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setLong(1,branid );
            pstmt.setLong(2,tranid );
            pstmt.setLong(3,itemid );
            pstmt.setLong(4,quantity );
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }}

    public Transportation getTransportation(long id, TruckMapper truckMapper, ItemMapper itemMapper, SupplierMapper supplierMapper, BranchMapper branchMapper,DriverMapper driverMapper) throws Exception {

        if(transportations.containsKey(id)){
            return transportations.get(id);
        }
        else {
            Transportation tra=select(id,truckMapper,itemMapper,supplierMapper,branchMapper, driverMapper);
            if(tra!=null)
                return tra;
            throw new IllegalArgumentException("No transportation match to id:" + id);
        }
    }

    public List<Transportation> getTransportations(TruckMapper truckMapper, ItemMapper itemMapper, SupplierMapper supplierMapper, BranchMapper branchMapper,DriverMapper driverMapper) throws Exception {
        return selectAll(truckMapper,itemMapper,supplierMapper,branchMapper, driverMapper);
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
}
