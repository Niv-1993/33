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

public class TransportationMapper extends Mapper {

    static private TransportationMapper mapper = null;
    private HashMap<Long, Transportation> transportations;

    public static TransportationMapper getMapper() {
        if (mapper == null) {
            mapper = new TransportationMapper();
        }
        return mapper;
    }

    private TransportationMapper() {
        transportations = new HashMap<>();
    }

    /**
     * load from database all transportations.
     *
     * @return
     * @throws Exception
     */
    private List<Transportation> selectAll(TruckMapper tru, DriverMapper driverMapper) throws Exception {

        String sql = "SELECT * FROM Transportations ";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            List<Transportation> toRet = new ArrayList<>();
            while (rs.next()) {
                Driver tempD = driverMapper.select(rs.getInt("driverID"));
                Truck tempT = tru.getTruck(rs.getLong("truckID"));
                Long tID = rs.getLong("ID");
                LocalDate date = LocalDate.parse(rs.getString("Date"));
                LocalTime time = LocalTime.parse(rs.getString("LeavingTime"));
                int weight = rs.getInt("Weight");
                Area area = Area.valueOf(rs.getString("Area"));
                List<Order> orders;
                try {
                     orders = new Order().getOrdersByTransportation(tID.intValue());
                }
                catch (Exception e){
                    orders =new ArrayList<>();
                }
                HashMap<Integer, Order> ordersMap = new HashMap<>();
                for (Order order : orders)
                    ordersMap.put(order.getOrderId(), order);
                toRet.add(new Transportation(tID, date, time, area,tempD, tempT, weight, ordersMap));
            }
            for (Transportation tran : toRet)
                transportations.put(tran.getId(), tran);
            return toRet;
        } catch (SQLException e) {
            System.out.println("selectAll()-> TransportationMapper ->"+e.getMessage());
            throw new IOException("Failed to load all transportations from database. Error: " + e.getMessage());
        }
    }

    /**
     * load single transportation from database by its id.
     *
     * @return Transportation
     * @throws Exception
     */
    private Transportation select(long id, TruckMapper tru, DriverMapper driverMapper) throws Exception {
        String sql = "SELECT * FROM Transportations WHERE ID=" + id;
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            // loop through the result set
            while (rs.next()) {
                Driver tempD = driverMapper.select(rs.getInt("driverID"));
                Truck tempT = tru.getTruck(rs.getInt("truckID"));
                Long tID = rs.getLong("ID");
                Area area = Area.valueOf(rs.getString("Area"));
                LocalDate date = LocalDate.parse(rs.getString("Date"));
                LocalTime time = LocalTime.parse(rs.getString("LeavingTime"));
                int weight = rs.getInt("Weight");
                List<Order> orders;
                try {
                    orders = new Order().getOrdersByTransportation(tID.intValue());
                }
                catch (Exception e){
                    orders =new ArrayList<>();
                }
                HashMap<Integer, Order> ordersMap = new HashMap<>();
                for (Order order : orders)
                    ordersMap.put(order.getOrderId(), order);
                Transportation tran = new Transportation(tID, date, time, area,tempD, tempT, weight, ordersMap);
                if (!transportations.containsKey(tran.getId()))
                    transportations.put(tran.getId(), tran);
                return tran;
            }
        } catch (SQLException e) {
            System.out.println("select()-> TransportationMapper ->"+e.getMessage());
            throw new IOException("Failed to get transportation from database. Transportation id: " + id + ". Error: " + e.getMessage());
        }
        return null;
    }

    /**
     * returns transportation ID by its driver,date and shift.
     *
     * @return
     * @throws Exception
     */
    private Long selectTransportationIdByDriverAndDate(int driverID, LocalDate date, String time) throws Exception {
        String tim = " AND LeavingTime < 14:00";
        if (time == "Night")
            tim = " AND LeavingTime >= 14:00";
        String sql = "SELECT * FROM Transportations WHERE driverID=" + driverID + " AND Date=" + date.toString() + tim;
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            // loop through the result set
            while (rs.next()) {
                return rs.getLong("ID");
            }
            return null;
        } catch (SQLException e) {
            System.out.println("selectTransportationIdByDriverAndDate()-> TransportationMapper ->"+e.getMessage());
            throw new IOException("Failed to get transportation id by driver and date from database. Error: " + e.getMessage());
        }
    }

    /**
     * returns transportation by its driver,date and shift.
     *
     * @return Transportation.
     * @throws Exception
     */
    public Transportation selectTransWithDriverShift(int driverID, LocalDate date, String time, TruckMapper tru, DriverMapper driverMapper) throws Exception {
        Long id = selectTransportationIdByDriverAndDate(driverID, date, time);
        if (id != null) {
            return select(id, tru, driverMapper);
        }
        throw new IOException("No such transportation by those driver, date and time in database");
    }

    private void insert(long id, String area, String date, String time, double weight, long driverID, long truckID) throws Exception {
        String sql = "INSERT INTO Transportations (ID,Area,Date,LeavingTime,Weight,driverID,truckID) VALUES(?,?,?,?,?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            pstmt.setString(2, area);
            pstmt.setString(3, date);
            pstmt.setString(4, time);
            pstmt.setDouble(5, weight);
            pstmt.setLong(6, driverID);
            pstmt.setLong(7, truckID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("insert()-> TransportationMapper ->"+e.getMessage());
            throw new IOException(e.getMessage());
        }
    }

    /**
     * add new transportation to database and to the hashmap.
     *
     * @throws Exception
     */
    public void addTransportation(Transportation tra) throws Exception {
        transportations.put(tra.getId(), tra);
        insert(tra.getId(), tra.getArea().toString(), tra.getDate().toString(), tra.getLeavingTime().toString(), tra.getWeight(), tra.getDriver().getEID(), tra.getTruck().getId());
    }

    /**
     * return transportation by id.
     *
     * @return
     * @throws Exception
     */
    public Transportation getTransportation(long id, TruckMapper truckMapper, DriverMapper driverMapper) throws Exception {

        if (transportations.containsKey(id)) {
            return transportations.get(id);
        }
        else {
            Transportation tra = select(id, truckMapper, driverMapper);
            if (tra != null)
                return tra;
            return null;
        }
    }

    /**
     * returns all transportation objects from database.
     *
     * @return
     * @throws Exception
     */
    public List<Transportation> getAllTransportations(TruckMapper truckMapper, DriverMapper driverMapper) throws Exception {
        return selectAll(truckMapper, driverMapper);
    }

    /**
     * @return the next Transportation id to be assigned.
     * @throws Exception
     */
    public long getCurrId() throws Exception {

        String sql = "SELECT MAX(ID) AS MA FROM Transportations";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                return rs.getLong("MA");
            }
        } catch (SQLException e) {
            throw new IOException("Failed to get next transportation id from database");
        }
        return -1;
    }

    /**
     * insert new transportation to data base.
     *
     * @throws IOException
     */
    public void insert(int id, String area, String date, String time, double weight, int driverID, int truckID) throws IOException {

        String sql = "INSERT INTO Transportations (ID,Area,Date,LeavingTime,Weight,driverID,truckID) VALUES(?,?,?,?,?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            pstmt.setString(2, area);
            pstmt.setString(3, date);
            pstmt.setString(4, time);
            pstmt.setDouble(5, weight);
            pstmt.setLong(6, driverID);
            pstmt.setLong(7, truckID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Failed to insert new transportation. Id: " + id + " Error: " + e.getMessage());
            throw new IOException(e.getMessage());
        } catch (Exception e) {
            System.out.println("Failed to insert new transportation. Id: " + id + " Error: " + e.getMessage());
            throw new IOException(e.getMessage());
        }
    }


    /**
     * returns all transportations from specific area.
     *
     * @return transportation list.
     * @throws IOException
     */
    public List<Transportation> getTransportationsByArea(TruckMapper truckMapper, DriverMapper driverMapper, Area area) throws IOException {
        String sql =String.format( "SELECT * FROM Transportations WHERE Area='%s'",area.name()) ;
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            List<Transportation> toRet = new ArrayList<>();
            while (rs.next()) {
                Driver tempD = driverMapper.select(rs.getInt("driverID"));
                Truck tempT = truckMapper.getTruck(rs.getInt("truckID"));
                Long tID = rs.getLong("ID");
                Area are = Area.valueOf(rs.getString("Area"));
                LocalDate date = LocalDate.parse(rs.getString("Date"));
                LocalTime time = LocalTime.parse(rs.getString("LeavingTime"));
                double weight = rs.getDouble("Weight");
                List<Order> orders = new Order().getOrdersByTransportation(tID.intValue());
                HashMap<Integer, Order> ordersMap = new HashMap<>();
                for (Order order : orders)
                    ordersMap.put(order.getOrderId(), order);
                toRet.add(new Transportation(tID, date, time, are,tempD, tempT, weight, ordersMap));
            }
            return toRet;
        } catch (Exception e) {
            System.out.println("Failed to get transportations list by area. Error: " + e.getMessage());
            throw new IOException(e.getMessage());
        }
    }

    /**
     * update transportation new weight in db.
     *
     * @throws IOException
     */
    public void updateTransWeight(long id, double weight, Order order) throws IOException {
        String sql = "UPDATE Transportations " +
                "SET Weight=" + weight
                + "Where ID=" + id;
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Failed to update transportation weight. Transportation id: " + id + " Error: " + e.getMessage());
            throw new IOException(e.getMessage());
        } catch (Exception e) {
            System.out.println("Failed to update transportation weight. Transportation id: " + id + " Error: " + e.getMessage());
            throw new IOException(e.getMessage());
        }
        Transportation tra = transportations.get(id);
        tra.setWeight(weight);
        if (!tra.containOrder(order.getOrderId())) {
            tra.addOrder(order);
        } else {
            tra.replaceOrder(order);
        }
        transportations.replace(id, tra);
    }

    /**
     * update transportation new weight in db.
     *
     * @throws IOException
     */
    public void updateTransDriver(long id, int driverId) throws IOException {
        String sql = "UPDATE Transportations " +
                "SET driverID=" + driverId
                + "Where ID=" + id;
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new IOException(e.getMessage());
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    /**
     * return  all transportations under the given date and time from db.
     *
     * @return transportation list
     * @throws IOException
     */
    public List<Transportation> getTransportationsByDate(LocalDate date, LocalTime time, TruckMapper tm, DriverMapper dm) throws IOException {
        String tim="<";
        if(time.compareTo(LocalTime.parse("14:00"))>=0)
            tim=">=";
        String sql =String.format( "SELECT * FROM Transportations WHERE Date='%s' AND LeavingTime %s '14:00'",date.toString(),tim) ;
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            List<Transportation> toRet=new ArrayList<>();
            // loop through the result set
            while (rs.next()) {
                Driver tempD= dm.select(rs.getInt("driverID"));
                Truck tempT= tm.getTruck(rs.getInt("truckID"));
                Long tID=rs.getLong("ID");
                LocalDate Date=LocalDate.parse( rs.getString("Date"));
                LocalTime leavingTime=LocalTime.parse(rs.getString("LeavingTime"));
                Area area= Area.valueOf(rs.getString("Area"));
                int weight=rs.getInt("Weight");
                List<Order> orders = new Order().getOrdersByTransportation(tID.intValue());
                HashMap<Integer, Order> ordersMap = new HashMap<>();
                for (Order order : orders) {
                    ordersMap.put(order.getOrderId(), order);
                }
                Transportation tran = new Transportation(tID, Date, leavingTime,area ,tempD, tempT, weight, ordersMap);
                if (!transportations.containsKey(tran.getId()))
                    transportations.put(tran.getId(), tran);
                toRet.add(tran);

            }
            return toRet;
        } catch (Exception e) {
            System.out.println("Failed to get transportations list by date. Error: " + e.getMessage());
            throw new IOException(e.getMessage());
        }
    }

    /**
     * Remove transportation from db.
     *
     * @param idCounter
     * @throws IOException
     */
    public void remove(Long idCounter) throws IOException {
        String sql = "DELETE FROM  Transportations WHERE ID=" + idCounter;
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new IOException("failed to delete transportation " + idCounter);
        } catch (Exception e) {
            throw new IOException("failed to delete transportation " + idCounter + " Error :" + e.getMessage());
        }
    }

    /**
     * Inserts new message to human resources managers.
     */
    public void insertAlerts(int bid, int eid, LocalDate date, String message) {
        String sql = "INSERT INTO ManagerAlerts (MID,BID, EID, Date, Message) VALUES(?,?,?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, getCurrMessageId());
            pstmt.setInt(2, bid);
            pstmt.setInt(3, eid);
            pstmt.setString(4, date.toString());
            pstmt.setString(5, message);
            pstmt.executeUpdate();
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * @return The next message id.
     * @throws Exception
     */
    public int getCurrMessageId() throws Exception {

        String sql = "SELECT MAX(MID) AS MA FROM ManagerAlerts";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                return rs.getInt("MA");
            }
        } catch (SQLException e) {
            System.out.println("getCurrMessageId()-> TransportationMapper ->"+e.getMessage());
            throw new IOException("Failed to get current message id from database. Error: " + e.getMessage());
        }
        return -1;
    }

    /**
     * replace driverID in transportation table in database.
     *
     * @throws IOException
     */
    public void replaceDrivers(long id, Driver newDriverID) throws IOException {
        if (transportations.containsKey(id))
        transportations.get(id).setDriver(newDriverID);
        updateTransDriver(id, newDriverID.getEID());
    }

    public boolean removeOrderFromTransportation(long transID, int orderId){
       transportations.get(transID).removeOrder(orderId);
       return transportations.get(transID).getOrderList().isEmpty();
    }

    public boolean deleteTrans(long idCounter) {
       try {
           remove(idCounter);
           transportations.remove(idCounter);
           return true;
       }
       catch (Exception e){
           return false;
       }
    }

    public void addTransportation(int id, LocalDate date, LocalTime time, Area south, int driver, int truck, double weight) {
        try {
            insert(id,south.toString(),date.toString(),time.toString(),weight,driver,truck);
        } catch (IOException e) {
            System.out.println("could not add new transportation to db. Error: "+e.getMessage());
        }
    }
}
