package Business.Transportation;
import Business.Employees.EmployeePKG.Driver;
import Business.Type.Area;
import Business.SupplierBusiness.Order;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;


public class Transportation {
    private long id;
    private LocalDate date;
    private LocalTime leavingTime;
    private Driver driver;
    private Truck truck;
    private double weight;
    private Area shippingArea;
    private HashMap <Integer, Order> orders;

    public Transportation(long id) {
        this.id = id;
        date = null;
        leavingTime = null;
        driver = null;
        truck = null;
        weight = -1;
        shippingArea = null;
        orders=new HashMap<>();
    }

    public Transportation(long id, LocalDate date, LocalTime leavingTime,Area area, Driver driver ,Truck truck, double weight, HashMap<Integer, Order> orderS) {
        this.date = date;
        this.id = id;
        this.driver = driver;
        this.truck = truck;
        this.shippingArea=area;
        this.weight = weight;
        this.leavingTime = leavingTime;
        orders=orderS;
    }
    public Order removeOrder(int orderId){
        if(!orders.containsKey(orderId)){
            throw new IllegalArgumentException("order id: "+ orderId+" not found on transportation is: "+ id);
        }
        Order retOrder = orders.get(orderId);
        orders.remove(orderId);
        return  retOrder;
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
    public void setShippingArea(Area shippingArea) {

        this.shippingArea = shippingArea;
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
        } else if ((driver.getLicense())<(truck.getLicense())) {
            throw new IllegalArgumentException("ur driver license is:" + driver.getLicense() + "but ur truck license is: " + truck.getNetWeight());
        } else {
            this.driver = driver;
        }
    }
    public List<Integer> getTransBranches(){
        return orders.values().stream().map(o-> o.getBranchID()).collect(Collectors.toList());
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
        if ((LocalTime.now().compareTo(leavingTime) > 0) &&  date.compareTo(LocalDate.now())==0) {
            throw new IllegalArgumentException("u choose incorrect living time.");
        }
        this.leavingTime = leavingTime;
    }

    /**
     * sets the transportation weight.
     * Checks if the weight is legal.
     * @param weight : the weight to set to.
     */
    public void setWeight(double weight){

        if(truck!=null && weight< truck.getNetWeight())
            throw new IllegalArgumentException("Warning!!! The weight must include the truck net weight. \n");
        if(truck!=null && weight > truck.getMaxWeight()){
            throw new IllegalArgumentException("Warning!!! the curr weight is mismatch to max truck wight.\n");
        }
        this.weight = weight;
    }


    public void setTruck(Truck truck) { this.truck = truck; }
    public double getWeight() { return weight; }
    public Truck getTruck() { return truck; }
    public long getId() {
        return id;
    }
    public Area getArea() {
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
        return id == that.id && weight == that.weight && Objects.equals(date, that.date) && Objects.equals(leavingTime, that.leavingTime) && Objects.equals(driver, that.driver) && Objects.equals(truck, that.truck) ;
    }

    @Override
    public String toString() {
        return "Business.Transportation: " +
                "id=" + id + '\n' +
                ", date=" + date + '\n' +
                ", leavingTime=" + leavingTime + '\n' +
                ", driver=" + driver + '\n'
                //TODO: keep implement of orders.
                ;
    }

    /**
     * Method that returns if the user finished to fill all the fields needed to create the transportation.
     * @return : if the trans is completed.
     */
    public boolean isComplete() {
        //TODO: add orders to method.
        return !(date == null | leavingTime == null|driver == null| truck == null|shippingArea == null|weight == -1);
    }
    public boolean canAdd(Order order){
        if(weight+order.getTotalWeight()<=truck.getMaxWeight()){
            orders.put(order.getOrderId(), order);
            weight+=order.getTotalWeight();
            return true;
        }
        return false;
    }
    public boolean canChange(Order newOrder){
        if(orders.containsKey(newOrder.getOrderId())){
            Order oldOrder = orders.get(newOrder.getOrderId());
            double newWeight = weight - oldOrder.getTotalWeight()+newOrder.getTotalWeight();
            if(truck.getMaxWeight() >= newWeight){
                orders.replace(newOrder.getOrderId(),newOrder);
                weight = newWeight;
                return  true;
            }
        }
        return  false;
    }

    public boolean isEmpty(){return orders.isEmpty();}
    public  Map<Integer,Order> getOrders() {return  orders;}
    public List<Order> getOrderList(){return new ArrayList<>(orders.values());}

    public void addOrder(Order order) {
        orders.put(order.getOrderId(),order);
    }

    public boolean containOrder(int orderId) {
        return orders.containsKey(orderId);
    }

    public void replaceOrder(Order order) {
        orders.replace(order.getOrderId(),order);
    }
}
