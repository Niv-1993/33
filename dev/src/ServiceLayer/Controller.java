package ServiceLayer;
import BussinessLayer.Branch;
import BussinessLayer.ServiceFaced;
import BussinessLayer.Transportation;
import Responses.Response;
import Responses.ResponseT;
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



    public List<DriverServiceDTO> getAllDrivers(){
        ResponseT<List<DriverServiceDTO>> res = serviceControl.getDTODrivers();
        if(res.ErrorOccured()){
            throw new IllegalArgumentException(res.getErrorMessage());
        }
        return res.getValue();
    }
   /* public void printDrivers(List<DriverServiceDTO> drivers){
        for (DriverServiceDTO driver:drivers) {
            System.out.println(driver);
        }
    }*/
    public List<ItemServiceDTO> getAllItems(){
        ResponseT<List<ItemServiceDTO>> res = serviceControl.getAllItems();
        if(res.ErrorOccured()) {
            throw new IllegalArgumentException(res.getErrorMessage());
        }
        return res.getValue();
    }

    public List<SupplierServiceDTO> getAllSuppliers(){
        ResponseT<List<SupplierServiceDTO>> res = serviceControl.getDTOSuppliers();
        if(res.ErrorOccured()){
            throw new IllegalArgumentException(res.getErrorMessage());
        }
        return res.getValue();
    }
    public List<TransportationServiceDTO> getAllTransportations(){

        ResponseT<List<TransportationServiceDTO>> res = serviceControl.getDTOtransportations();
        if(res.ErrorOccured()){
            throw new IllegalArgumentException(res.getErrorMessage());
        }
        return res.getValue();
    }
    public List<TruckServiceDTO> getAllTrucks(){
        ResponseT<List<TruckServiceDTO>> res = serviceControl.getDTOTrucks();
        if(res.ErrorOccured()) {
            throw new IllegalArgumentException(res.getErrorMessage());
        }
        return res.getValue();
    }
    public List<BranchServiceDTO> getAllBranches(){
        ResponseT<List<BranchServiceDTO>> res = serviceControl.getDTOBranches();
        if(res.ErrorOccured()){
            throw new IllegalArgumentException(res.getErrorMessage());
        }
        return res.getValue();
    }
    public DriverServiceDTO getDriver(int id){
        ResponseT<DriverServiceDTO> driver =  serviceControl.getDriver(id);
        if(driver.ErrorOccured()){
            throw new IllegalArgumentException(driver.getErrorMessage());
        }
        return driver.getValue();
    }
    public BranchServiceDTO getBranch(int id){
        ResponseT<BranchServiceDTO> res = serviceControl.getBranch(id);
        if(res.ErrorOccured()){
            throw new IllegalArgumentException(res.getErrorMessage());
        }
        return res.getValue();
    }
    public SupplierServiceDTO getSupplier(int id) {
        ResponseT<SupplierServiceDTO> res = serviceControl.getSuppliers(id);
        if (res.ErrorOccured()) {
            throw new IllegalArgumentException(res.getErrorMessage());
        }
        return res.getValue();
    }
    public TruckServiceDTO getTruck(int id){
        ResponseT<TruckServiceDTO> res = serviceControl.getTruck(id);
        if(res.ErrorOccured()) {
            throw new IllegalArgumentException(res.getErrorMessage());
        }
        return res.getValue();
    }
}
