package BussinessLayer;

import DataLayer.DataController;

import java.util.*;

//bar
public class DriverService {

    private HashMap<Integer,Driver> drivers;
    private DataController dataController;

    public DriverService(){drivers=new HashMap<>();
    drivers.put(1,new Driver(1,"bar",new License(200)));
    }
    public DriverService(HashMap<Integer,Driver> driverList){
        this.drivers=driverList;
        dataController=DataController.init();
    }
    public List<Driver> getDriversList(){
        return new ArrayList<Driver>(drivers.values());
    }
    public HashMap<Integer,Driver> getDrivers() { return drivers;  }


    public Driver getDriver(int id){
        if(drivers.containsKey(id)){
            return drivers.get(id);
        }
        throw new NoSuchElementException("driver is not exists");
    }

    public void setDrivers(HashMap<Integer,Driver> drivers) { this.drivers = drivers; }


    public boolean compatibleDriver(Driver driver , License lice){return driver.compatibleLicense(lice);}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DriverService that = (DriverService) o;
        return Objects.equals(drivers, that.drivers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(drivers);
    }
}
