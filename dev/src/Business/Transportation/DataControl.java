package Business.Transportation;

import Business.Employees.EmployeePKG.Driver;
import Business.Type.Area;
import DataAccess.BranchMapper;
import DataAccess.DriverMapper;
import DataAccess.TransportationMapper;
import DataAccess.TruckMapper;

import java.time.LocalDate;
import java.time.LocalTime;
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

    public List< Branch> getBranches() throws Exception {
        return branchMapper.getBranches();
    }

    public List<Truck> getTrucks() throws Exception {
        return truckMapper.getTrucks();
    }
    public List<Truck> getTrucksByWeight(int weight) throws Exception {
        return truckMapper.getTrucksByWeight(weight);
    }

    public List< Transportation> getTransportations() throws Exception {
        return transportationMapper.getTransportations(truckMapper,driverMapper);
    }


    public Branch getBranch(int id) throws Exception {
        return branchMapper.getBranch(id);
    }

    public Truck getTruck(long id) throws Exception {
        return truckMapper.getTruck(id);
    }
    public Transportation getTransportation(long id) throws Exception {
        return transportationMapper.getTransportation(id,truckMapper,branchMapper,driverMapper);
    }

    public void addTransportation( Transportation tra) throws Exception {
        transportationMapper.addTransportation(tra);
    }

    public List<Transportation> getTransportationsList() throws Exception {
        return transportationMapper.getTransportations(truckMapper,driverMapper);
    }

    public void remove(long idCounter) {
        transportationMapper.remove(idCounter);
    }

    public void setDriverOnTrans(long transId, Driver driver) {

        transportationMapper.setDriverOnTrans(transId,driver);
    }
    public void setTruckOnTrans(long transId, Truck truck) {

        transportationMapper.setTruckOnTrans(transId,truck);
    }

    public void setTime(long id, LocalTime leavingTime) {
        transportationMapper.setTime(id,leavingTime);
    }

    public void setWeight(long id, int weight) {
        transportationMapper.setWeight(id,weight);
    }

    public void setDate(long id, LocalDate date) {
        transportationMapper.setDate(id,date);
    }

    public long getCurrID() throws Exception {
        return transportationMapper.getCurrId();
    }

    public void addTruck(long id, int maxweight,String model, int netWeight, int license){truckMapper.addTruck(id,maxweight,model,netWeight,license);}
    public void addTransportation(int i, String center, String s, String s1, int i1, int i2, int i3) {
        transportationMapper.addTransportation( i,  center,  s,  s1,  i1,  i2,  i3);
    }

    public List<Transportation> getTransportationsByArea(Area area) {
       return transportationMapper.getTransportationsByArea(driverMapper,truckMapper,area);
    }

    public void updateTransWeight(long id, int weight,Order order) {
        transportationMapper.updateTransWeight(id,weight,order);
    }

    public List<Transportation> getTransportations(int currBID, LocalDate date, LocalTime time) {
        return transportationMapper.getTransportations(currBID,date,time);
    }
}
