package BussinessLayer;

import DataLayer.DataController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

//yuval
public class TransportationService {

    private final DataController dataController;
    private HashMap<Integer,Transportation> transportations;

    public TransportationService(){
        transportations=new HashMap<>();
        dataController=DataController.init();
    }
    public List<Transportation> getTransportationsList() {
        return new ArrayList<>(transportations.values());
    }



    public Transportation getTransportationById(long id){

        throw new IllegalArgumentException("No transportation match to id:" + id);
    }
}
