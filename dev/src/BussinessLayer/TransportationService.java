package BussinessLayer;

import DataLayer.DataController;
import enums.Pair;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
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
    public List<Transportation> getTransportationsList() {
        return new ArrayList<>(transportations.values());
    }
    public Transportation setDriver(long transId, Driver driver){
        Transportation t = getTransportationById(transId);
        t.setDriver(driver);
        return t;
    }
    public void newTransportation(){
        transportations.put(idCounter,new Transportation(idCounter));
        idCounter++;
    }
    public Transportation setSuppliers(long transId, List<Supplier> s){
        Transportation t = getTransportationById(transId);
        t.setSuppliers(s);
        return t;
    }
    public Transportation setItems(long transId, HashMap<Branch,List<Pair<Item,Integer>>> items){
        Transportation t = getTransportationById(transId);
        t.setDeliveryItems(items);
        return t;
    }
    public Transportation setTruck(long transId,Truck truck){
        Transportation t = getTransportationById(transId);
        t.setTruck(truck);
        return t;
    }
    public Transportation setDateAndLivingTime(long transId, LocalDate date, LocalTime livingTime){
        Transportation t = getTransportationById(transId);
        t.setDate(date);
        t.setLeavingTime(livingTime);
        return t;
    }
    public Transportation getTransportationById(long id){
        if(transportations.containsKey(id)){
            return transportations.get(id);
        }
        throw new IllegalArgumentException("No transportation match to id:" + id);
    }
    public Transportation setDeliveryItems(long transId, HashMap<Branch,List<Pair<Item,Integer>>> deliveryItems){
        Transportation t = getTransportationById(transId);
        t.setDeliveryItems(deliveryItems);
        return t;
    }
    public Transportation setWeight(long transId,int kg){
        Transportation t = getTransportationById(transId);
        t.setWeight(kg);
        return t;
    }


    public void loadData(DataControl dataControl) {
        transportations=dataControl.loadTrans();
    }
}
