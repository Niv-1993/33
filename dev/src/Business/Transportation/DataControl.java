package Business.Transportation;

import Business.SupplierBusiness.Order;
import Business.Type.Area;
import DataAccess.BranchMapper;
import DataAccess.DriverMapper;
import DataAccess.TransportationMapper;
import DataAccess.TruckMapper;

import java.io.IOException;
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
        return transportationMapper.getTransportation(id,truckMapper,branchMapper,driverMapper);
    }

    public void addTransportation( Transportation tra) throws Exception {
        transportationMapper.addTransportation(tra);
    }

    public List<Transportation> getTransportationsList() throws Exception {
        return transportationMapper.getAllTransportations(truckMapper,driverMapper);
    }

    public void remove(long idCounter) throws IOException { transportationMapper.remove(idCounter); }


    public void setDate(long id, LocalDate date) {
        transportationMapper.setDate(id,date);
    }

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
}
