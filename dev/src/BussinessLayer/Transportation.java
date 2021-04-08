package BussinessLayer;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import  enums.Pair;


public class Transportation {
    private long id;
    private LocalDate date;
    private LocalTime leavingTime;
    private Driver driver;
    private Truck truck;
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
    public void setShippingArea(ShippingArea shippingArea) {
        this.shippingArea = shippingArea;
    }

    /**
     * Sets new data.
     * Check if the date is later than the date it was typed..
     * @param date: the date to set to.
     */
    public void setDate(LocalDate date) {
        if (LocalDate.now().compareTo(date)>0) {
            throw new IllegalArgumentException("the date is: " + LocalDate.now() + " but u set: " + date + "to be the date.");
        }
        this.date = date;
    }

    /**
     * Set the delivery items with branches.
     * Checks that all areas of branches are the same, otherwise throws an exception.
     * @param deliveryItems: the items and branched hashmap.
     */
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

    /**
     * Set driver to the transportation.
     * Will not allow to set driver before truck.
     * Check that driver's license is compatible.
     * @param driver: The driver to set to.
     */
    public void setDriver(Driver driver) {
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

    /**
     * Adding the new transportation time.
     * Checks the time and date are after the current time and dates(When the transportation was created).
     * @param leavingTime
     */
    public void setLeavingTime(LocalTime leavingTime) {
        if ((LocalTime.now().compareTo(leavingTime) < 0) &&  date.compareTo(LocalDate.now())==0) {
            throw new IllegalArgumentException("u choose incorrect living time.");
        }
        this.leavingTime = leavingTime;
    }

    /**
     * sets the transportation weight.
     * Checks if the weight is legal.
     * @param weight : the weight to set to.
     */
    public void setWeight(int weight){
        if(weight< truck.getNetWeight())
            throw new IllegalArgumentException("Warning! The weight must include the truck net weight ");
        if(weight > truck.getMaxWeight()){
            throw new IllegalArgumentException("Warning! the curr weight is mismatch to max truck wight");
        }
        this.weight = weight;
    }


    public void setTruck(Truck truck) { this.truck = truck; }
    public HashMap<Branch, List<Pair<Item,Integer>>> getDeliveryItems() { return deliveryItems; }
    public int getWeight() { return weight; }
    public Truck getTruck() { return truck; }
    public long getId() {
        return id;
    }
    public HashMap<Supplier, List<Pair<Item, Integer>>> getSuppliers() {
        return suppliers;
    }
    public ShippingArea getShippingArea() {
        return shippingArea;
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

    /**
     * Method that returns if the user finished to fill all the fields needed to create the transportation.
     * @return : if the trans is completed.
     */
    public boolean isComplete() {

        return !(date == null | leavingTime == null|driver == null| truck == null|deliveryItems == null|shippingArea == null|weight == -1|suppliers == null);
    }
}
