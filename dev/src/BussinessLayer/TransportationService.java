package BussinessLayer;

import DataLayer.DataController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

//yuval
public class TransportationService {

    private final DataControl dataControl;
    private HashMap<Long,Transportation> transportations;


    public TransportationService(){
        transportations=new HashMap<>();
        dataControl=DataControl.init();
    }
    public List<Transportation> getTransportationsList() {
        return new ArrayList<>(transportations.values());
    }



    public Transportation getTransportationById(long id){

        throw new IllegalArgumentException("No transportation match to id:" + id);
    }

    //TODO:complete method
    public void loadData(DataControl dataControl) {
        transportations=dataControl.loadTrans();
    }
}
