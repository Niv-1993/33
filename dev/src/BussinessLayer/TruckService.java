package BussinessLayer;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

//bar
public class TruckService {

    private List<Truck> trucks;

    public TruckService(){ trucks=new LinkedList<>(); }
    public TruckService(List<Truck> tru){ this.trucks=tru;}

    public List<Truck> getTrucks() { return trucks; }

    public void setTrucks(List<Truck> trucks) { this.trucks = trucks; }

    public Truck getTruckByWeight(int add ) throws Exception {
        for (Truck truck: trucks) {
            if(truck.getMaxWeight()>= truck.getNetWeight()+add){
                Truck ret=truck;
                removeTruck(ret); //moving the driver to the end of the list
                addTruck(ret);
                return ret;
            }
        }
        throw new Exception("there is no compatible truck for mission");
    }
    public Truck getTruck(int licNum){
        for (Truck truck: trucks) {
            if(truck.getLicenseNumber()==licNum)
                return truck;
        }
        throw new NoSuchElementException("driver is not exists");
    }

    public void removeTruck(Truck tru){
        trucks.remove(tru);
    }
    public void removeTruck(int licenseNum){
        for (Truck truck:trucks) {
            if(truck.getLicenseNumber()==licenseNum)
                trucks.remove(truck);
        }
    }
    public void addTruck(Truck tru){ trucks.add(tru);}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TruckService that = (TruckService) o;
        return Objects.equals(trucks, that.trucks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trucks);
    }
}
