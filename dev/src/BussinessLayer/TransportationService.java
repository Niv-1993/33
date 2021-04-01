package BussinessLayer;

import DataLayer.DataController;

import java.util.LinkedList;
import java.util.List;

//yuval
public class TransportationService {

    private final DataController dataController;
    private List<Transportation> transportations;

    public TransportationService(){
        transportations=new LinkedList<>();
        dataController=DataController.init();
    }
    public List<Transportation> getTransportations() {
        return transportations;
    }

    @Override
    public String toString() {
        StringBuilder acc = new StringBuilder();
        acc.append("Transportations : \n");
        for(Transportation t: transportations){
            acc.append(t);
        }
        return acc.toString();
    }

    public Transportation getTransportationById(long id){
        for(Transportation t: transportations){
            if(t.getId() == id){
                return t;
            }
        }
        throw new IllegalArgumentException("No transportation match to id:" + id);
    }
}
