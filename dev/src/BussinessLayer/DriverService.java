package BussinessLayer;

import DataLayer.DataController;

import java.util.*;

//bar
public class DriverService {

    private Map<Long,Driver> drivers;

    public DriverService(){
        drivers=new HashMap<>();
    }
    public DriverService(Map<Long,Driver> driverList){
        this.drivers=driverList;
    }
    public List<Driver> getDriversList(){
        return new ArrayList<Driver>(drivers.values());
    }
    public Map<Long,Driver> getDrivers() { return drivers;  }


    public Driver getDriver(long id){
        if(drivers.containsKey(id)){
            return drivers.get(id);
        }
        throw new NoSuchElementException("driver is not exists");
    }

    public void setDrivers(HashMap<Long,Driver> drivers) { this.drivers = drivers; }


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


    public void loadData(DataControl dataControl) {
        drivers=dataControl.loadDrivers();
    }
}
