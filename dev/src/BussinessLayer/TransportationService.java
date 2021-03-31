package BussinessLayer;

import java.util.HashMap;
import java.util.List;

//yuval
public class TransportationService {
    private List<Transportation> transportations;

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
