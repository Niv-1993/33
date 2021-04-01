package ServiceLayer;
import BussinessLayer.ServiceFaced;
import BussinessLayer.Transportation;
import Responses.Response;
import ServiceLayer.Objects.*;

import java.util.*;

public class Controller {
    private static Controller control = null;
    private ServiceFaced serviceControl;

    public static Controller initial(){
        if(control == null){
            control=new Controller();
        }
        return control;
    }
    private Controller (){
        serviceControl = ServiceFaced.initial();
    }

    public boolean setDriverOnTransportation(TransportationServiceDTO t){return true;}
    public boolean setSupplierOnTransportation(TransportationServiceDTO t){return true;}
    public boolean setBranchOnTransportation(TransportationServiceDTO t){return true;}
    public boolean setItemsOnTransportation(Transportation t){return true;}
    public boolean setTruckOnTransportation(Transportation t){return true;}
    public boolean setTransportation(Transportation t){return true;}



    public StringBuilder getAllDrivers(){
        StringBuilder acc = new StringBuilder();
        Response<List<DriverServiceDTO>> res = serviceControl.getDTODrivers();
        if(res.isErrorOccurred()){
            acc.append(res.getErrorMessage());
        }else {
            List<DriverServiceDTO> drivers = res.getValue();
            for(DriverServiceDTO d: drivers){
                acc.append(d.toString());
            }
        }
        return acc;
    }
    public StringBuilder getAllItems(){
        StringBuilder acc = new StringBuilder();
        Response<List<ItemServiceDTO>> res = serviceControl.getAllItems();
        if(res.isErrorOccurred()){
            acc.append(res.getErrorMessage());
        }else {
            List<ItemServiceDTO> items = res.getValue();
            for(ItemServiceDTO i: items){
                acc.append(i.toString());
            }
        }
        return acc;
    }

    public StringBuilder getAllSuppliers(){
        StringBuilder acc = new StringBuilder();
        Response<List<SupplierServiceDTO>> res = serviceControl.getDTOSuppliers();
        if(res.isErrorOccurred()){
            acc.append(res.getErrorMessage());
        }else {
            List<SupplierServiceDTO> suppliers = res.getValue();
            for(SupplierServiceDTO s: suppliers){
                acc.append(s.toString());
            }
        }
        return acc;
    }
    public StringBuilder getAllTransportations(){
        StringBuilder acc = new StringBuilder();
        Response<List<TransportationServiceDTO>> res = serviceControl.getDTOtransportations();
        if(res.isErrorOccurred()){
            acc.append(res.getErrorMessage());
        }else {
            List<TransportationServiceDTO> transportations = res.getValue();
            for(TransportationServiceDTO t: transportations){
                acc.append(t.toString());
            }
        }
        return acc;
    }
    public StringBuilder getAllTrucks(){
        StringBuilder acc = new StringBuilder();
        Response<List<TruckServiceDTO>> res = serviceControl.getDTOTrucks();
        if(res.isErrorOccurred()){
            acc.append(res.getErrorMessage());
        }else {
            List<TruckServiceDTO> trucks = res.getValue();
            for(TruckServiceDTO t: trucks){
                acc.append(t.toString());
            }
        }
        return acc;
    }
    public StringBuilder getAllBranches(){
        StringBuilder acc = new StringBuilder();
        Response<List<BranchServiceDTO>> res = serviceControl.getDTOBranches();
        if(res.isErrorOccurred()){
            acc.append(res.getErrorMessage());
        }else {
            List<BranchServiceDTO> branches = res.getValue();
            for(BranchServiceDTO b: branches){
                acc.append(b.toString());
            }
        }
        return acc;
    }

}
