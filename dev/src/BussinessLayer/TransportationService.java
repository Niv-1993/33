package BussinessLayer;
import enums.Area;
import enums.Pair;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class TransportationService {

    private final DataControl dataControl;
    private HashMap<Long,Transportation> transportations;
    private long idCounter = 0;

    public TransportationService(){
        transportations=new HashMap<>();
        dataControl=DataControl.init();
    }

    public void setDriver(long transId, Driver driver){
        Transportation t = getTransportationById(transId);
        t.setDriver(driver);
    }

    /**
     * Adding the suppliers and their products for a specific transportation.
     * Does a check with the delivery item field for same data
     * @param transId: the transportation id to add to.
     * @param s: the suppliers and their products to add.
     */
    public void setSuppliersItem(long transId, HashMap<Supplier, List<Pair<Item, Integer>>> s){
        Transportation t = getTransportationById(transId);
        List< List<Pair<Item, Integer>>> pairs= new ArrayList<>(s.values());
        List< List<Pair<Item, Integer>>> pairs2=null;
        if (t.getDeliveryItems()!=null)
            pairs2= new ArrayList<>(t.getDeliveryItems().values());
        checkValidation(pairs,pairs2);
        for (List<Pair<Item, Integer>> quantity:pairs) {
            for (Pair<Item, Integer> pair: quantity) {
                if(pair.getSec()<0)
                    throw new IllegalArgumentException("illegal item quantity. item id: "+pair.getFir().getId());
            }
        }
        t.setSuppliers(s);
    }

    /**
     * Adding truck to a specific transportation.
     * @param transId: to transportation id to add to.
     * @param truck : the truck object to add.
     */
    public void setTruck(long transId, Truck truck){
        Transportation t = getTransportationById(transId);
        t.setTruck(truck);
    }
    /**
     * Adding the branches and their products for a specific transportation.
     * Does a check with the suppliers field for same data
     * @param transId: the transportation id to add to.
     * @param deliveryItems: the suppliers and their products to add.
     */
    public void setDeliveryItems(long transId, HashMap<Branch,List<Pair<Item,Integer>>> deliveryItems){
        Transportation t = getTransportationById(transId);
        List< List<Pair<Item, Integer>>> pairs=new ArrayList<>(deliveryItems.values());
        List< List<Pair<Item, Integer>>> pairs2=null;
        if (t.getSuppliers()!=null)
            pairs2= new ArrayList<>(t.getSuppliers().values());
        checkValidation(pairs,pairs2);
        for (List<Pair<Item, Integer>> quantity:pairs) {
            for (Pair<Item, Integer> pair: quantity) {
                if(pair.getSec()<0)
                    throw new IllegalArgumentException("illegal item quantity. item id: "+pair.getFir().getId());
            }
        }
        t.setDeliveryItems(deliveryItems);
    }

    /**
     * Add time to new transportation.
     * @param id :  the transportation id to add to.
     * @param leavingTime : the time to set.
     */
    public void setTransportationTime(long id, LocalTime leavingTime) {
        getTransportationById(id).setLeavingTime(leavingTime);
    }

    /**
     *
     * @param id :  the transportation id to add to.
     * @param date : the date to set.
     */
    public void setDate(long id, LocalDate date) {
        getTransportationById(id).setDate(date);
    }

    /**
     *
     * @param id :  the transportation id to add to.
     * @param weight: the weight to set.
     */
    public void setTransportationWeight(long id, int weight) {

        getTransportationById(id).setWeight(weight);
    }

    /**
     * Initializing new transportation object.
     * @return : the new object
     */
    public Transportation newTransportation(){
        Transportation tra=new Transportation(idCounter);
        transportations.put(idCounter,tra);
        idCounter++;
        return tra;
    }

    /**
     * Method that check if all items from list's list pairsS are in list's list pairsS2.
     * @param pairsS:  the first list's list to check if all it's values are in the second list.
     * @param pairsS2: the second list we check in.
     */
    private void checkValidation(List<List<Pair<Item, Integer>>> pairsS, List<List<Pair<Item, Integer>>> pairsS2) {

        if(pairsS!=null&pairsS2!=null){
          for (List<Pair<Item, Integer>> lis1:pairsS){
                boolean contains=false;
                for (Pair<Item, Integer> pair:lis1) {
                    for (List<Pair<Item, Integer>> lis2:pairsS2) {
                        if (lis2.contains(pair)) {
                            contains = true;
                            break;
                        }
                    }
                    if(!contains)
                        throw new IllegalArgumentException("items in branches and suppliers and quantities must be equal.");
                    contains=false;
                }
            }
        }
    }


    /**
     * returns a transportation by it's id.
     * @param id :  the transportation id to add to.
     * @return: The transportation object that wanted.
     */
    public Transportation getTransportationById(long id){
        if(transportations.containsKey(id)){
            return transportations.get(id);
        }
        throw new IllegalArgumentException("No transportation match to id:" + id);
    }

    /**
     * Loads the database data.
     * @param dataControl : the data control that should bring the data from the database.
     */
    public void loadData(DataControl dataControl) {
        transportations=dataControl.loadTrans();
    }

    /**
     * Save the new completed transportation to the database.
     * @param id : The transportation id.
     * @return :The added transportation after saved.
     */
    public Transportation saveTransportation(long id) {
        Transportation tra=getTransportationById(id);
        if(!tra.isComplete())
            throw new IllegalArgumentException("Please fill all details");
        dataControl.addTransportation(tra);
        return tra;
    }

    /**
     * sets an area to new transportation.
     * @param id : The transportation id.
     * @param area : the area should be added.
     */
    public void setArea(long id, Area area) {
        getTransportationById(id).setShippingArea(new ShippingArea(area));
    }

    /**
     * Method to return all transportations that has been made.
     * @return : list of all transportations.
     */
    public List<Transportation> getTransportationsList() {
        return new ArrayList<>(transportations.values());
    }

}
