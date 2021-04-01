package BussinessLayer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

//yuval
public class Transportation {
    private long id;
    private LocalDate date;
    private LocalTime leavingTime;
    private Driver driver;
    private Truck truck;
    //ask if they call this item or product?
    private HashMap<Site, List<Item>> deliveryItems;
    private int weight;

    public Transportation(long id, LocalDate date,LocalTime leavingTime, Driver driver,Truck truck,int weight,HashMap<Site, List<Item>> deliveryItems){
        this.date=date;
        this.deliveryItems=deliveryItems;
        this.id=id;
        this.driver=driver;
        this.truck=truck;
        this.weight=weight;
        this.leavingTime=leavingTime;
    }
    public long getId() { return id; }
    public LocalDate getDate() { return date; }
    public Driver getDriver() { return driver; }
    public LocalTime getLeavingTime() { return leavingTime; }

    public void setDate(LocalDate date) {
        if(!LocalDate.now().isEqual(date)){
            throw new IllegalArgumentException("the date is: "+ LocalDate.now()+" but u set: " + date + "to be the date.");
        }
        this.date = date;
    }
    public void setDeliveryItems(HashMap<Site, List<Item>> deliveryItems) { this.deliveryItems = deliveryItems; }

    public void setDriver(Driver driver) {
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

    public HashMap<Site, List<Item>> getDeliveryItems() {
        return deliveryItems;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }

    public int getWeight() {
        return weight;
    }

    public Truck getTruck() {
        return truck;
    }

    public List<Item> getSiteItems(Site site){
        if(!deliveryItems.containsKey(site)){
            throw new IllegalArgumentException("No items for this Site.");
        }
        return deliveryItems.get(site);
    }

    private boolean checkSameArea(List<Site> sites){
        if(sites.isEmpty()){
            throw new IllegalArgumentException("no sites...");
        }
        ShippingArea curr = sites.get(0).getShippingArea();
        for (Site s: sites){
            if(!(curr.equals(s.getShippingArea()))){
                return false;
            }
        }
        return true;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transportation that = (Transportation) o;
        return id == that.id &&
                Objects.equals(date, that.date) &&
                Objects.equals(leavingTime, that.leavingTime) &&
                Objects.equals(driver, that.driver) &&
                Objects.equals(deliveryItems, that.deliveryItems);
    }

    @Override
    public String toString() {
        return "Transportation: " +
                "id=" + id + '\n' +
                ", date=" + date + '\n' +
                ", leavingTime=" + leavingTime + '\n' +
                ", driver=" + driver + '\n' +
                ", deliveryItems=" + deliveryItems + '\n';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, leavingTime, driver, deliveryItems);
    }


}
