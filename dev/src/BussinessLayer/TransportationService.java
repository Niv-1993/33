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
    private long idCounter = 0;

    public TransportationService() {

        dataControl=new DataControl();
        idCounter=getId();
    }

    private long getId() {
        try {
            return dataControl.getCurrID()+1;
        }
        catch (Exception e){
            return -1;
        }
    }

    public void setDriver(long transId, Driver driver) throws Exception {
        Transportation t = getTransportationById(transId);
        dataControl.setDriverOnTrans(transId,driver);
        t.setDriver(driver);
    }

    /**
     * Adding the suppliers and their products for a specific transportation.
     * Does a check with the delivery item field for same data
     * @param transId: the transportation id to add to.
     * @param s: the suppliers and their products to add.
     */
    public void setSuppliersItem(long transId, HashMap<Supplier, List<Pair<Item, Integer>>> s) throws Exception {
        Transportation t = getTransportationById(transId);
        List< List<Pair<Item, Integer>>> pairs= new ArrayList<>(s.values());
        checkQuantity(pairs);
        t.setSuppliers(s);
        dataControl.setSuppliersItems(transId,s);
    }

    /**
     * Adding truck to a specific transportation.
     * @param transId: to transportation id to add to.
     * @param truck : the truck object to add.
     */
    public void setTruck(long transId, Truck truck) throws Exception {
        Transportation t = getTransportationById(transId);
        t.setTruck(truck);
        dataControl.setTruckOnTrans(transId,truck);

    }
    /**
     * Adding the branches and their products for a specific transportation.
     * Does a check with the suppliers field for same data
     * @param transId: the transportation id to add to.
     * @param deliveryItems: the suppliers and their products to add.
     */
    public void setDeliveryItems(long transId, HashMap<Branch,List<Pair<Item,Integer>>> deliveryItems) throws Exception {
        Transportation t = getTransportationById(transId);
        List< List<Pair<Item, Integer>>> pairs=new ArrayList<>(deliveryItems.values());
        checkQuantity(pairs);
        t.setDeliveryItems(deliveryItems);
        dataControl.setDeliveryItems(transId,deliveryItems);
    }
    private void checkQuantity(List< List<Pair<Item, Integer>>> pairs){
        for (List<Pair<Item, Integer>> quantity:pairs) {
            for (Pair<Item, Integer> pair: quantity) {
                if(pair.getSec()<=0)
                    throw new IllegalArgumentException("illegal item quantity. item id: "+pair.getFir().getId());
            }
        }
    }

    /**
     * Add time to new transportation.
     * @param id :  the transportation id to add to.
     * @param leavingTime : the time to set.
     */
    public void setTransportationTime(long id, LocalTime leavingTime) throws Exception {
        getTransportationById(id).setLeavingTime(leavingTime);
        dataControl.setTime(id,leavingTime);
    }

    /**
     *
     * @param id :  the transportation id to add to.
     * @param date : the date to set.
     */
    public void setDate(long id, LocalDate date) throws Exception {
        getTransportationById(id).setDate(date);
        dataControl.setDate(id,date);
    }

    /**
     *
     * @param id :  the transportation id to add to.
     * @param weight: the weight to set.
     */
    public void setTransportationWeight(long id, int weight) throws Exception {

        getTransportationById(id).setWeight(weight);
        dataControl.setWeight(id,weight);
    }

    /**
     * Initializing new transportation object.
     * @return : the new object
     */
    public Transportation newTransportation(){
        Transportation tra=new Transportation(idCounter);
        dataControl.addTransportation(idCounter,tra);
        idCounter++;
        return tra;
    }

    /**
     * returns a transportation by it's id.
     * @param id :  the transportation id to add to.
     * @return: The transportation object that wanted.
     */
    public Transportation getTransportationById(long id) throws Exception {

        return dataControl.getTransportation(id);

    }

    /**
     * Loads the database data.
     * @param dataControl : the data control that should bring the data from the database.
     */

    /**
     * Save the new completed transportation to the database.
     * @param id : The transportation id.
     * @return :The added transportation after saved.
     */
    public Transportation saveTransportation(long id) throws Exception {
        Transportation tra=getTransportationById(id);
        if(!tra.isComplete())
            throw new IllegalArgumentException("Please fill all details");
        dataControl.saveTransportation(id);
        return tra;
    }

    /**
     * sets an area to new transportation.
     * @param id : The transportation id.
     * @param area : the area should be added.
     */
    public void setArea(long id, Area area) throws Exception {

        getTransportationById(id).setShippingArea(new ShippingArea(area));
    }

    /**
     * Method to return all transportations that has been made.
     * @return : list of all transportations.
     */
    public List<Transportation> getTransportationsList() throws Exception {
        return dataControl.getTransportationsList();
    }

    public void deleteTransport() {
        idCounter--;
        dataControl.remove(idCounter);
    }
}
