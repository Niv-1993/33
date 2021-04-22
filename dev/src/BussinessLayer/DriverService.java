package BussinessLayer;
import java.util.*;


public class DriverService {

    private Map<Long,Driver> drivers;

    public DriverService(){
        drivers=new HashMap<>();
    }

    /**
     * returns all the drivers exists.
     * @return : the drivers list.
     */
    public List<Driver> getDriversList(){ return new ArrayList<>(drivers.values()); }

    /**
     * returns a driver by it's id.
     * @param id: the wanted driver's id.
     * @return: the driver obj.
     */
    public Driver getDriver(long id){
        if(drivers.containsKey(id)){
            return drivers.get(id);
        }
        throw new NoSuchElementException("driver with id: " + id + " is not exists");
    }

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

    public void addDriver(Driver d) {
        drivers.put(d.getId(),d);
    }
}