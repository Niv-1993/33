package ServiceLayer.Objects;

import BussinessLayer.Driver;
import BussinessLayer.Item;
import BussinessLayer.Site;
import BussinessLayer.Truck;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

public class TransportationService {

    private long id;
    private LocalDate date;
    private LocalTime leavingTime;
    private Driver driver;
    private Truck truck;
    private HashMap<BranchService, List<ItemService>> deliveryItems;
    private int weight;

    public TransportationService(long id, LocalDate date,LocalTime leavingTime, Driver driver,Truck truck,int weight,HashMap<BranchService, List<ItemService>> deliveryItems){
        this.date=date;
        this.deliveryItems=deliveryItems;
        this.id=id;
        this.driver=driver;
        this.truck=truck;
        this.weight=weight;
        this.leavingTime=leavingTime;
    }

    public void setId(long id) { this.id = id; }

    public Driver getDriver() { return driver; }

    public int getWeight() { return weight; }

    public LocalDate getDate() { return date; }

    public LocalTime getLeavingTime() { return leavingTime; }

    public long getId() { return id; }

    public Truck getTruck() { return truck; }

    public HashMap<BranchService, List<ItemService>> getDeliveryItems() { return deliveryItems; }

    public void setDate(LocalDate date) { this.date = date; }

    public void setDeliveryItems(HashMap<BranchService, List<ItemService>> deliveryItems) { this.deliveryItems = deliveryItems; }

    public void setDriver(Driver driver) { this.driver = driver; }

    public void setLeavingTime(LocalTime leavingTime) { this.leavingTime = leavingTime; }

    public void setTruck(Truck truck) { this.truck = truck; }

    public void setWeight(int weight) { this.weight = weight; }

}
