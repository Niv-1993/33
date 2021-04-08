package ServiceLayer.Objects;
import enums.Area;
import enums.Pair;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class TransportationServiceDTO {

    private long id;
    private LocalDate date;
    private LocalTime leavingTime;
    private DriverServiceDTO driver;
    private TruckServiceDTO truck;
    private HashMap<SupplierServiceDTO,List<Pair<ItemServiceDTO,Integer>>> suppliers;
    private HashMap<BranchServiceDTO, List<Pair<ItemServiceDTO,Integer>>> deliveryItems;
    private Area area;
    private int weight;
    public TransportationServiceDTO(){
        id = -1;
        date = null;
        leavingTime = null;
        driver = null;
        truck = null;
        suppliers = new HashMap<>();
        deliveryItems = new HashMap<>();
        area=null;
    }

    @Override
    public String toString() {
        return "\n\tid = " + id +
                "\n\tdate = " + date +
                "\n\tleavingTime = " + leavingTime +
                "\n\tdriver = " + driver +
                "\n\ttruck = " + truck +
                "\n\tsuppliers = " + suppliers +
                "\n\tdeliveryItems = " + deliveryItems +
                "\n\tweight = " + weight +
                "\n\t}\n";
    }

    public TransportationServiceDTO(long id, LocalDate date, LocalTime leavingTime, DriverServiceDTO driver, TruckServiceDTO truck, int weight, HashMap<BranchServiceDTO, List<Pair<ItemServiceDTO,Integer>>> deliveryItems, HashMap<SupplierServiceDTO,List<Pair<ItemServiceDTO,Integer>>> sup,Area area){
        this.date=date;
        this.deliveryItems=deliveryItems;
        this.id=id;
        this.driver=driver;
        this.truck=truck;
        this.weight=weight;
        this.leavingTime=leavingTime;
        this.suppliers=sup;
        this.area=area;
    }

    public void setSuppliers(HashMap<SupplierServiceDTO,List<Pair<ItemServiceDTO,Integer>>> suppliers) {
        this.suppliers = suppliers;
    }

    public HashMap<SupplierServiceDTO,List<Pair<ItemServiceDTO,Integer>>>  getSuppliers() {
        return suppliers;
    }


    public void setId(int id) { this.id = id; }

    public DriverServiceDTO getDriver() { return driver; }

    public int getWeight() { return weight; }

    public LocalDate getDate() { return date; }

    public LocalTime getLeavingTime() { return leavingTime; }

    public long getId() { return id; }

    public TruckServiceDTO getTruck() { return truck; }

    public HashMap<BranchServiceDTO, List<Pair<ItemServiceDTO,Integer>>> getDeliveryItems() { return deliveryItems; }

    public void setDate(LocalDate date) { this.date = date; }

    public void setDeliveryItems(HashMap<BranchServiceDTO, List<Pair<ItemServiceDTO,Integer>>> deliveryItems) { this.deliveryItems = deliveryItems; }

    public void setDriver(DriverServiceDTO driver) { this.driver = driver; }

    public void setLeavingTime(LocalTime leavingTime) { this.leavingTime = leavingTime; }

    public void setTruck(TruckServiceDTO truck) { this.truck = truck; }

    public void setWeight(int weight) { this.weight = weight; }

    public Area getArea() { return area; }

    public void setArea(Area area) { this.area = area; }

}