package BussinessLayer;
import java.util.*;


public class TruckService {

    private Map<Long,Truck> trucks;

    public TruckService(){
        trucks=new HashMap<>();
    }
    public List<Truck> getTrucksList() { return new ArrayList<>(trucks.values());}


    @Override
    public String toString() {
        StringBuilder acc = new StringBuilder();
        for (Map.Entry<Long,Truck> entry: trucks.entrySet()) {
            Truck t = entry.getValue();
            acc.append(t);
        }
        return acc.toString();
    }

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

    public Truck getTruck(Long truckId) {
        if(!trucks.containsKey(truckId)){
            throw new IllegalArgumentException("Truck with id: "+ truckId + "is not exist");
        }
        return trucks.get(truckId);
    }

    /**
     * load all the trucks from the database using the control
     * @param dataControl : the control that ask the database for the data.
     */
    public void loadData(DataControl dataControl) {
        trucks=dataControl.loadTrucks();
    }

    public void addTruck(Truck t) {
        trucks.put(t.getId(),t);
    }
}
