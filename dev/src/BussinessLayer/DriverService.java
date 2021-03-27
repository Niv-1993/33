package BussinessLayer;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

//bar
public class DriverService {

    private List<Driver> drivers;

    public DriverService(){drivers=new LinkedList<>(); }
    public DriverService(List<Driver> driverList){this.drivers=driverList;}

    public List<Driver> getDrivers() { return drivers;  }

    public Driver getDriver(License lic ) throws Exception {
        for (Driver driver:drivers) {
            if(driver.compatibleLicense(lic)){
                Driver ret=driver;
                removeDriver(ret); //moving the driver to the end of the list
                addDriver(ret);
                return ret;

            }
        }
        throw  new Exception("no suck driver available for this loicense type");
    }
    public Driver getDriver(int id){
        for (Driver driver: drivers) {
            if(driver.getId()==id)
                return driver;
        }
        throw new NoSuchElementException("driver is not exists");
    }
  //TODO: check if driver name is unique.
  /*
    public Driver getDriver(String name) {
        for (Driver driver: drivers) {
            if(driver.getName()==name)
               return driver;
        }
        throw new NoSuchElementException("driver is not exists");
    }
  */
    public void setDrivers(List<Driver> drivers) { this.drivers = drivers; }
    public void addDriver(Driver driver){ drivers.add(driver);}
    public void removeDriver(Driver driver){ drivers.remove(driver);}
    public void removeDriver(int id){
        for (Driver driver: drivers) {
            if(driver.getId()==id)
                removeDriver(driver);
        }
    }
    public void removeDriver(String name){
        for (Driver driver: drivers) {
            if(driver.getName()==name)
                removeDriver(driver);
        }
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
