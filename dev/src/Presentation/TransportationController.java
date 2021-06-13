package Presentation;

import Business.ApplicationFacade.ResponseData;
import Business.ApplicationFacade.iControllers.iManagerRoleController;
import Business.ApplicationFacade.outObjects.TransportationServiceDTO;
import Business.SupplierBusiness.Order;
import Business.Transportation.TransportationService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TransportationController {
    private final TransportationService serviceControl;


    public List<Integer> checkAvailableDriverSubs(int driverID, String time, LocalDate date,List<Integer> optionalDrivers){
        try {
            return serviceControl.checkAvailableDriverSubs(driverID, time, date, optionalDrivers);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }
    public void swapDrivers(int newDriverID, int oldDriverID, String time, LocalDate date){
        try{
            serviceControl.swapDrivers(newDriverID,oldDriverID,time,date);

        }
        catch (Exception e){
            System.out.println( e.getMessage());
        }
    }
    public TransportationController(iManagerRoleController mc){
        serviceControl = new TransportationService(mc);
    }


    public List<TransportationServiceDTO> getAllTransportations(){

        ResponseData<List<TransportationServiceDTO>> res = serviceControl.getDTOTransportations();
        if(res.isError()){
            throw new IllegalArgumentException(res.getError());
        }
        return res.getData();
    }
    public void delete(long id) {
        try{
            serviceControl.cancelTran(id);
        }
        catch (Exception e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public void addTruck(long id, int maxweight,String model, int netWeight, int license) {
        try {
            serviceControl.addTruck(id, maxweight, model, netWeight, license);
        }
        catch (Exception e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public List<TransportationServiceDTO> getTransportations(int currBID, LocalDate date, LocalTime time) {
        try {
            return serviceControl.getTransportations(date, time);
        }
        catch (Exception e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public long addOrderToTransportation(Order order) {
        try {
            return serviceControl.addOrderToTransportation(order);
        }
        catch (Exception e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
