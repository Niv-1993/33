package Business.Transportation;

import Business.Employees.EmployeePKG.Driver;
import Business.SupplierBusiness.Order;
import Business.Type.Area;
import DataAccess.BranchMapper;
import DataAccess.DriverMapper;
import DataAccess.TransportationMapper;
import DataAccess.TruckMapper;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DataControl {

    private BranchMapper branchMapper;
    private TruckMapper truckMapper;
    private DriverMapper driverMapper;
    private TransportationMapper transportationMapper;
    static  private String dbName;

    public DataControl(){
        dbName="databaseDemo.db";
        branchMapper=BranchMapper.getMapper();
        truckMapper=TruckMapper.getMapper();
        transportationMapper=TransportationMapper.getMapper();
        driverMapper=DriverMapper.getMapper();

    }

    public List<Truck> getTrucks() throws Exception {
        return truckMapper.getTrucks();
    }
    public List<Truck> getTrucksByWeight(double weight) throws Exception {
        return truckMapper.getTrucksByWeight(weight);
    }

    public Branch getBranch(int id) throws Exception {
        return branchMapper.getBranch(id);
    }

    public Truck getTruck(long id) throws Exception {
        return truckMapper.getTruck(id);
    }
    public Transportation getTransportation(long id) throws Exception {
        return transportationMapper.getTransportation(id,truckMapper,driverMapper);
    }

    public void insertAlerts (int bid, int eid, LocalDate date, String message){
        transportationMapper.insertAlerts(bid,eid,date,message);
    }
    public void addTransportation( Transportation tra) throws Exception {
        transportationMapper.addTransportation(tra);
    }

    public List<Transportation> getTransportationsList() throws Exception {
        return transportationMapper.getAllTransportations(truckMapper,driverMapper);
    }

    public void remove(long idCounter) throws IOException { transportationMapper.remove(idCounter); }


    public long getCurrID() throws Exception {
        return transportationMapper.getCurrId();
    }

    public void addTruck(long id, int maxweight,String model, int netWeight, int license){truckMapper.addTruck(id,maxweight,model,netWeight,license);}

    public List<Transportation> getTransportationsByArea(Area area) throws IOException {
       return transportationMapper.getTransportationsByArea(truckMapper,driverMapper,area);
    }

    public void updateTransWeight(long id, double weight,Order order) throws IOException {
        transportationMapper.updateTransWeight(id,weight,order);
    }

    public List<Transportation> getTransportations( LocalDate date, LocalTime time) throws IOException {
        return transportationMapper.getTransportationsByDate(date,time,truckMapper,driverMapper);
    }
    public Transportation selectTransWithDriverShift(int driverID, String time,LocalDate date) throws Exception {
        return transportationMapper.selectTransWithDriverShift(driverID,date,time,truckMapper,driverMapper);
    }

    public List<Driver> getDriversList(List<Integer> optionalDrivers) {
        List<Driver> ret= new ArrayList<>();
        try {
            for (Integer id : optionalDrivers) {
                ret.add(driverMapper.select(id));
            }
            return ret;
        }
        catch (Exception e){
            System.out.println("Fail to launch drivers from database. Error: "+e.getMessage());
            throw new IllegalArgumentException("Failed to launch drivers to change drivers in transportation.");
        }
    }

    public void replaceDrivers(long id, int newDriverID) throws Exception {
        transportationMapper.replaceDrivers(id,driverMapper.select(newDriverID));
    }

    public void removeOrderFromTransportation(long transID, int orderId) {
        transportationMapper.removeOrderFromTransportation(transID,orderId);
    }
}
