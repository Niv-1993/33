package ServiceLayer.Objects;

import BussinessLayer.Driver;
import BussinessLayer.Truck;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class TransportationServiceDTO {

    private long id;
    private LocalDate date;
    private LocalTime leavingTime;
    private DriverServiceDTO driver;
    private TruckServiceDTO truck;
    private List<SupplierServiceDTO> suppliers;
    private HashMap<BranchServiceDTO, List<ItemServiceDTO>> deliveryItems;
    private int weight;
    public TransportationServiceDTO(){
        id = -1;
        date = null;
        leavingTime = null;
        driver = null;
        truck = null;
        suppliers = new LinkedList<>();
        deliveryItems = new HashMap<>();
    }
    @Override
    public String toString() {
        return "TransportationServiceDTO{" +
                "id=" + id +
                ", date=" + date +
                ", leavingTime=" + leavingTime +
                ", driver=" + driver +
                ", truck=" + truck +
                ", suppliers=" + suppliers +
                ", deliveryItems=" + deliveryItems +
                ", weight=" + weight +
                '}';
    }

    public TransportationServiceDTO(long id, LocalDate date, LocalTime leavingTime, DriverServiceDTO driver, TruckServiceDTO truck, int weight, HashMap<BranchServiceDTO, List<ItemServiceDTO>> deliveryItems, List<SupplierServiceDTO> sup){
        this.date=date;
        this.deliveryItems=deliveryItems;
        this.id=id;
        this.driver=driver;
        this.truck=truck;
        this.weight=weight;
        this.leavingTime=leavingTime;
        this.suppliers=sup;
    }

    public void setId(int id) { this.id = id; }

    public DriverServiceDTO getDriver() { return driver; }

    public int getWeight() { return weight; }

    public LocalDate getDate() { return date; }

    public LocalTime getLeavingTime() { return leavingTime; }

    public long getId() { return id; }

    public TruckServiceDTO getTruck() { return truck; }

    public HashMap<BranchServiceDTO, List<ItemServiceDTO>> getDeliveryItems() { return deliveryItems; }

    public void setDate(LocalDate date) { this.date = date; }

    public void setDeliveryItems(HashMap<BranchServiceDTO, List<ItemServiceDTO>> deliveryItems) { this.deliveryItems = deliveryItems; }

    public void setDriver(DriverServiceDTO driver) { this.driver = driver; }

    public void setLeavingTime(LocalTime leavingTime) { this.leavingTime = leavingTime; }

    public void setTruck(TruckServiceDTO truck) { this.truck = truck; }

    public void setWeight(int weight) { this.weight = weight; }

}
