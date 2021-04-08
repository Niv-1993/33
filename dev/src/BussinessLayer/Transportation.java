package BussinessLayer;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import enums.Area;
import  enums.Pair;

//yuval
public class Transportation {
    private long id;
    private LocalDate date;
    private LocalTime leavingTime;
    private Driver driver;
    private Truck truck;
    //ask if they call this item or product?
    private HashMap<Branch, List<Pair<Item, Integer>>> deliveryItems;
    private int weight;
    private ShippingArea shippingArea;
    private HashMap<Supplier, List<Pair<Item, Integer>>> suppliers;

    public Transportation(long id) {
        this.id = id;
        date = null;
        leavingTime = null;
        driver = null;
        truck = null;
        deliveryItems = null;
        suppliers = null;
        weight = -1;
        shippingArea = null;
    }

    public Transportation(long id, LocalDate date, LocalTime leavingTime, Driver driver, Truck truck, int weight, HashMap<Branch, List<Pair<Item, Integer>>> deliveryItems, HashMap<Supplier, List<Pair<Item, Integer>>> suppliers) {
        this.date = date;
        this.deliveryItems = deliveryItems;
        this.id = id;
        this.driver = driver;
        this.truck = truck;
        this.weight = weight;
        this.leavingTime = leavingTime;
        this.suppliers = suppliers;
    }

    public HashMap<Supplier, List<Pair<Item, Integer>>> getSuppliers() {
        return suppliers;
    }

    public ShippingArea getShippingArea() {
        return shippingArea;
    }

    public void setShippingArea(ShippingArea shippingArea) {
        this.shippingArea = shippingArea;
    }

    public long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Driver getDriver() {
        return driver;
    }

    public LocalTime getLeavingTime() {
        return leavingTime;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDate(LocalDate date) {
        if (LocalDate.now().compareTo(date)>0) {
            throw new IllegalArgumentException("the date is: " + LocalDate.now() + " but u set: " + date + "to be the date.");
        }
        this.date = date;
    }

    public void setDeliveryItems(HashMap<Branch, List<Pair<Item, Integer>>> deliveryItems) {
        List<Branch> branches = new ArrayList<>(deliveryItems.keySet());
        List<Branch> noSameArea = new ArrayList<>();
        boolean exp = false;
        for(Branch b: branches){
            if(!b.getShippingArea().equals(shippingArea)){
                noSameArea.add(b);
                exp = true;
            }
        }
        if(exp){
            throw new IllegalArgumentException(noSameArea.toString() + "not in " + shippingArea .toString());
        }
        this.deliveryItems = deliveryItems;
    }

    public void setSuppliers(HashMap<Supplier, List<Pair<Item, Integer>>> suppliers) {

        this.suppliers = suppliers;
    }

    public void setDriver(Driver driver) {
        //not expected but just in case
        if (truck == null) {
            throw new IllegalArgumentException("Please choose a truck before u choose a Driver");
        } else if ((driver.getLicense().getKg())<(truck.getLicense().getKg())) {
            throw new IllegalArgumentException("ur driver license is:" + driver.getLicense() + "but ur truck license is: " + truck.getNetWeight());
        } else {
            this.driver = driver;
        }
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLeavingTime(LocalTime leavingTime) {
        if ((LocalTime.now().compareTo(leavingTime) < 0) &&  date.compareTo(LocalDate.now())==0) {
            throw new IllegalArgumentException("u choose incorrect living time.");
        }
        this.leavingTime = leavingTime;
    }


    public void setWeight(int weight){
        if(weight > truck.getMaxWeight()){
            //also print the options the user have..
            throw new IllegalArgumentException("Warning the curr weight is mismatch to max truck wight");
        }
        this.weight = weight;
    }

    public HashMap<Branch, List<Pair<Item,Integer>>> getDeliveryItems() {
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

    public List<Pair<Item,Integer>> getSiteItems(Branch b){
        if(!deliveryItems.containsKey(b)){
            throw new IllegalArgumentException("Site " + b + "does not in this delivery");
        }
        return deliveryItems.get(b);
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
        return id == that.id && weight == that.weight && Objects.equals(date, that.date) && Objects.equals(leavingTime, that.leavingTime) && Objects.equals(driver, that.driver) && Objects.equals(truck, that.truck) && Objects.equals(deliveryItems, that.deliveryItems) && Objects.equals(suppliers, that.suppliers);
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

    public boolean isComplete() {

        return !(date == null | leavingTime == null|driver == null| truck == null|deliveryItems == null|shippingArea == null|weight == -1|suppliers == null);
    }
}
