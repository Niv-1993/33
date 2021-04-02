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


    public DriverServiceDTO getDriver(int id){
        Response<DriverServiceDTO> driver =  serviceControl.getDriver(id);
        if(driver.isErrorOccurred()){
            throw new IllegalArgumentException(driver.getErrorMessage());
        }
        return driver.getValue();
    }
    public List<DriverServiceDTO> getAllDrivers(){
        Response<List<DriverServiceDTO>> res = serviceControl.getDTODrivers();
        if(res.isErrorOccurred()){
            throw new IllegalArgumentException(res.getErrorMessage());
        }
        return res.getValue();
    }
    public List<ItemServiceDTO> getAllItems(){
        Response<List<ItemServiceDTO>> res = serviceControl.getAllItems();
        if(res.isErrorOccurred()) {
            throw new IllegalArgumentException(res.getErrorMessage());
        }
        return res.getValue();
    }

    public List<SupplierServiceDTO> getAllSuppliers(){
        Response<List<SupplierServiceDTO>> res = serviceControl.getDTOSuppliers();
        if(res.isErrorOccurred()){
            throw new IllegalArgumentException(res.getErrorMessage());
        }
        return res.getValue();
    }
    public List<TransportationServiceDTO> getAllTransportations(){

        Response<List<TransportationServiceDTO>> res = serviceControl.getDTOtransportations();
        if(res.isErrorOccurred()){
            throw new IllegalArgumentException(res.getErrorMessage());
        }
        return res.getValue();
    }
    public List<TruckServiceDTO> getAllTrucks(){
        Response<List<TruckServiceDTO>> res = serviceControl.getDTOTrucks();
        if(res.isErrorOccurred()) {
            throw new IllegalArgumentException(res.getErrorMessage());
        }
        return res.getValue();
    }
    public List<BranchServiceDTO> getAllBranches(){
        Response<List<BranchServiceDTO>> res = serviceControl.getDTOBranches();
        if(res.isErrorOccurred()){
            throw new IllegalArgumentException(res.getErrorMessage());
        }
        return res.getValue();
    }

}
