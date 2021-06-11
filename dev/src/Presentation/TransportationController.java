package Presentation;

import Business.ApplicationFacade.ResponseData;
import Business.ApplicationFacade.iControllers.iManagerRoleController;
import Business.ApplicationFacade.outObjects.TransportationServiceDTO;
import Business.SupplierBusiness.Order;
import Business.Transportation.TransportationService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class TransportationController {
    private final TransportationService serviceControl;


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
            return serviceControl.getTransportations(currBID, date, time);
        }
        catch (Exception e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public void addOrderToTransportation(Order order) {
    }
}
