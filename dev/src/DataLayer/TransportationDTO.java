package DataLayer;

import BussinessLayer.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class TransportationDTO {

    private long id;
    private LocalDate date;
    private LocalTime leavingTime;
    private DriverDTO driver;
    private TruckDTO truck;
    //ask if they call this item or product?
    private HashMap<BranchDTO, List<ItemDTO>> deliveryItems;
    private List<supplierDTO> suppliers;
    private int weight;

    public TransportationDTO(long id, LocalDate date,LocalTime leavingTime, DriverDTO driver,TruckDTO truck,int weight,HashMap<BranchDTO, List<ItemDTO>> deliveryItems,List<supplierDTO> supp ){
        this.date=date;
        this.deliveryItems=deliveryItems;
        this.id=id;
        this.driver=driver;
        this.truck=truck;
        this.weight=weight;
        this.leavingTime=leavingTime;
        this.suppliers=supp;
    }
    public long getId() { return id; }
    public LocalDate getDate() { return date; }
    public DriverDTO getDriver() { return driver; }
    public LocalTime getLeavingTime() { return leavingTime; }
    public int getWeight() {
        return weight;
    }
    public TruckDTO getTruck() {
        return truck;
    }
    public List<supplierDTO> getSuppliers(){return suppliers;}
    public void addSupplier(supplierDTO sup){suppliers.add(sup);}
    public void removeSupplier(supplierDTO sup){suppliers.remove(sup);}
    public List<ItemDTO> getSiteItems(Site site){
        if(!deliveryItems.containsKey(site)){
            throw new IllegalArgumentException("No items for this Site.");
        }
        return deliveryItems.get(site);
    }


    public void setDate(LocalDate date) {
        if(!LocalDate.now().isEqual(date)){
            throw new IllegalArgumentException("the date is: "+ LocalDate.now()+" but u set: " + date + "to be the date.");
        }
        this.date = date;
    }
    public void setDeliveryItems(HashMap<BranchDTO, List<ItemDTO>> deliveryItems) { this.deliveryItems = deliveryItems; }
    public void setDriver(DriverDTO driver) {
        //not expected but just in case
        if(truck == null){
            throw new IllegalArgumentException ("Please choose a truck before u choose a Driver");
        }else if(!(driver.getLicense()).equals(truck.getLicense())){
            throw new IllegalArgumentException("ur driver license is:" + driver.getLicense() + "but ur truck license is: " + truck.getNetWeight());
        }else {
            this.driver = driver;
        }
    }
    public void setId(long id) { this.id = id; }
    public void setLeavingTime(LocalTime leavingTime) { this.leavingTime = leavingTime; }
    public void setWeight(int weight){
        if(weight > truck.getMaxWeight()){
            //also print the options the user have..
            throw new IllegalArgumentException("Warning the curr weight is mismatch to max truck wight");
        }
        this.weight = weight;
    }

    public HashMap<BranchDTO, List<ItemDTO>> getDeliveryItems() {
        return deliveryItems;
    }

    public void setTruck(TruckDTO truck) {
        this.truck = truck;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransportationDTO that = (TransportationDTO) o;
        return id == that.id && weight == that.weight && Objects.equals(date, that.date) && Objects.equals(leavingTime, that.leavingTime) && Objects.equals(driver, that.driver) && Objects.equals(truck, that.truck) && Objects.equals(deliveryItems, that.deliveryItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, leavingTime, driver, truck, deliveryItems, weight);
    }
}
