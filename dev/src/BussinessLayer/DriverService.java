package BussinessLayer;

import DataLayer.DataController;

import java.util.*;

//bar
public class DriverService {

    private HashMap<Integer,Driver> drivers;
    private DataController dataController;

    public DriverService(){drivers=new HashMap<>(); }
    public DriverService(HashMap<Integer,Driver> driverList){
        this.drivers=driverList;
        dataController=DataController.init();
    }

    public HashMap<Integer,Driver> getDrivers() { return drivers;  }

    public Driver getDriver(License lic ) throws Exception {
        for (Driver driver:drivers) {
            if(driver.compatibleLicense(lic)){
                removeDriver(driver); //moving the driver to the end of the list
                addDriver(driver);
                return driver;

            }
        }
        throw  new Exception("no suck driver available for this license type");
    }
    public Driver getDriver(int id){
        for (Driver driver: drivers) {
            if(driver.getId()==id)
                return driver;
        }
        throw new NoSuchElementException("driver is not exists");
    }

    public void setDrivers(HashMap<Integer,Driver> drivers) { this.drivers = drivers; }

    public void addDriver(Driver driver){
        drivers.add(driver);
        dataController.addDriver(driver);
    }

    public void removeDriver(Driver driver){
        drivers.remove(driver);
        dataController.removeDriver(driver);
    }

    public void removeDriver(int id){
        for (Driver driver: drivers) {
            if(driver.getId()==id)
                removeDriver(driver);
        }
        dataController.removeDriver(getDriver(id));
    }

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
