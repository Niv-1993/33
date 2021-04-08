package BussinessLayer;
import enums.Area;
import enums.Pair;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//yuval
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
        try {
            t.setDriver(driver);
        }
        catch (Exception e){
            throw e;
        }
    }
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
    public void setTruck(long transId, Truck truck){
        Transportation t = getTransportationById(transId);
        t.setTruck(truck);
    }
    public void setDeliveryItems(long transId, HashMap<Branch,List<Pair<Item,Integer>>> deliveryItems){
        Transportation t = getTransportationById(transId);
        List< List<Pair<Item, Integer>>> pairs=new ArrayList(deliveryItems.values());
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
    public void setTransportationTime(long id, LocalTime leavingTime) {
        getTransportationById(id).setLeavingTime(leavingTime);
    }

    public void setDate(long id, LocalDate date) {
        getTransportationById(id).setDate(date);
    }
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

    private void checkValidation(List<List<Pair<Item, Integer>>> pairss, List<List<Pair<Item, Integer>>> pairss2) {

        if(pairss!=null&pairss2!=null){
          for (List<Pair<Item, Integer>> lis1:pairss){
                boolean contains=false;
                for (Pair<Item, Integer> pair:lis1) {
                    for (List<Pair<Item, Integer>> lis2:pairss2) {
                       if(lis2.contains(pair))
                           contains=true;
                    }
                    if(contains==false)
                        throw new IllegalArgumentException("items in brances and suppliers and quantities must be equal.");
                    contains=false;
                }
            }
        }
    }



    public Transportation getTransportationById(long id){
        if(transportations.containsKey(id)){
            return transportations.get(id);
        }
        throw new IllegalArgumentException("No transportation match to id:" + id);
    }

    public void loadData(DataControl dataControl) {
        transportations=dataControl.loadTrans();
    }


    public Transportation saveTransportation(long id) {
        Transportation tra=getTransportationById(id);
        if(!tra.isComplete())
            throw new IllegalArgumentException("Please fill all details");
        dataControl.addTransportation(tra);
        return tra;
    }

    public void setArea(long id, Area area) {
        getTransportationById(id).setShippingArea(new ShippingArea(area));
    }


    public List<Transportation> getTransportationsList() {
        return new ArrayList<>(transportations.values());
    }

}
