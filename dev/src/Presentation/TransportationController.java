package Presentation;

import Business.ApplicationFacade.Objects.*;
import Business.ApplicationFacade.ResponseData;
import Business.Transportation.ServiceFaced;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class TransportationController {
    private final ServiceFaced serviceControl;


    public TransportationController(){
        serviceControl = new ServiceFaced();
    }

    /**
     * Setter methods, for each field of the transportation object try to update in the business layer
     * @param t : the presentation's transportation object to show the user and to contact the business layer
     */
    public void setDriverOnTransportation(TransportationServiceDTO t){
        ResponseData<TransportationServiceDTO> tra = serviceControl.setTransportationDriver(t);
        if (tra.isError()){
            throw new IllegalArgumentException(tra.getError());
        }
    }

    public void setTruckOnTransportation(TransportationServiceDTO t){
        ResponseData<TransportationServiceDTO> tra = serviceControl.setTransportationTruck(t);
        if (tra.isError()){
            throw new IllegalArgumentException(tra.getError());
        }
    }
    public void setTransportationWeight(TransportationServiceDTO t){
        ResponseData<TransportationServiceDTO> tra = serviceControl.setTransportationWeight(t);
        if (tra.isError()){
            throw new IllegalArgumentException(tra.getError());
        }
    }
    public void setTransportation(TransportationServiceDTO t){
        ResponseData<TransportationServiceDTO> tra = serviceControl.setTransportation(t);
        if (tra.isError()){
            throw new IllegalArgumentException(tra.getError());
        }
    }
    public void setTransportationDate(TransportationServiceDTO t){
        ResponseData<TransportationServiceDTO> tra = serviceControl.setTransportationDate(t);
        if (tra.isError()){
            throw new IllegalArgumentException(tra.getError());
        }
    }
    public void setTransportationLeavingTime(TransportationServiceDTO t){
        ResponseData<TransportationServiceDTO> tra = serviceControl.setTransportationTime(t);
        if (tra.isError()){
            throw new IllegalArgumentException(tra.getError());
        }
    }
    public void setSuppliersToTransportation(TransportationServiceDTO tr){
        ResponseData<TransportationServiceDTO> res = serviceControl.setTransportationSuppliersItems(tr);
        if(res.isError()){
            throw new IllegalArgumentException(res.getError());
        }
    }
    public void setDeliveryItemsToTransportation(TransportationServiceDTO tr){
        ResponseData<TransportationServiceDTO> res = serviceControl.setTransportationDeliveryItems(tr);
        if(res.isError()){
            throw new IllegalArgumentException(res.getError());
        }
    }
    public void setTransportationArea(TransportationServiceDTO t){
        ResponseData<TransportationServiceDTO> res=serviceControl.setArea(t);
        if(res.isError())
        {
            throw new IllegalArgumentException(res.getError());
        }
    }

    /**
     * Getters for every object's list.
     * @return : the objects list
     */
    public List<DriverServiceDTO> getAllDrivers(LocalDate date, LocalTime leavingTime){
        ResponseData<List<DriverServiceDTO>> res = serviceControl.getDTODrivers(date, leavingTime);
        if(res.isError()){
            throw new IllegalArgumentException(res.getError());
        }
        return res.getData();
    }
    public List<ItemServiceDTO> getAllItems(){
        ResponseData<List<ItemServiceDTO>> res = serviceControl.getAllDTOItems();
        if(res.isError()) {
            throw new IllegalArgumentException(res.getError());
        }
        return res.getData();
    }

    public List<SupplierServiceDTO> getAllSuppliers(){
        ResponseData<List<SupplierServiceDTO>> res = serviceControl.getDTOSuppliers();
        if(res.isError()){
            throw new IllegalArgumentException(res.getError());
        }
        return res.getData();
    }
    public List<ItemServiceDTO> getItemsBySupplier(int id) {

        ResponseData<List<ItemServiceDTO>> res=serviceControl.getDTOItemsBySupplier(id);
        if(res.isError()){
            throw new IllegalArgumentException(res.getError());
        }
        return res.getData();
    }
    public List<TransportationServiceDTO> getAllTransportations(){

        ResponseData<List<TransportationServiceDTO>> res = serviceControl.getDTOTransportations();
        if(res.isError()){
            throw new IllegalArgumentException(res.getError());
        }
        return res.getData();
    }
    public List<TruckServiceDTO> getAllTrucks(){
        ResponseData<List<TruckServiceDTO>> res = serviceControl.getDTOTrucks();
        if(res.isError()) {
            throw new IllegalArgumentException(res.getError());
        }
        return res.getData();
    }
    public List<BranchServiceDTO> getAllBranches(){
        ResponseData<List<BranchServiceDTO>> res = serviceControl.getDTOBranches();
        if(res.isError()){
            throw new IllegalArgumentException(res.getError());
        }
        return res.getData();
    }

    /**
     * Getters for return an object by it's identifier.
     * @param id: the identifier of the object.
     * @return : the object.
     */
    public TransportationServiceDTO getTransportation(long id){
        ResponseData<TransportationServiceDTO> res = serviceControl.getTransportation(id);
        if(res.isError()){
            throw new IllegalArgumentException(res.getError());
        }
        return res.getData();
     }
    public DriverServiceDTO getDriver(long id){
        ResponseData<DriverServiceDTO> driver =  serviceControl.getDriver(id);
        if(driver.isError()){
            throw new IllegalArgumentException(driver.getError());
        }
        return driver.getData();
    }
    public ItemServiceDTO getItem(long id){
        ResponseData<ItemServiceDTO> item =  serviceControl.getItem(id);
        if(item.isError()){
            throw new IllegalArgumentException(item.getError());
        }
        return item.getData();
    }
    public BranchServiceDTO getBranch(int id){
        ResponseData<BranchServiceDTO> res = serviceControl.getBranch(id);
        if(res.isError()){
            throw new IllegalArgumentException(res.getError());
        }
        return res.getData();
    }
    public SupplierServiceDTO getSupplier(int id) {
        ResponseData<SupplierServiceDTO> res = serviceControl.getSuppliers(id);
        if (res.isError()) {
            throw new IllegalArgumentException(res.getError());
        }
        return res.getData();
    }
    public TruckServiceDTO getTruck(long id){
        ResponseData<TruckServiceDTO> res = serviceControl.getTruck(id);
        if(res.isError()) {
            throw new IllegalArgumentException(res.getError());
        }
        return res.getData();
    }

    /**
     * Method for initializing new transportation to create.
     * @return : returns the new transportation object
     */
    public TransportationServiceDTO createNewTransportation() {
        ResponseData<TransportationServiceDTO> res = serviceControl.createNewTransportation();
        if(res.isError()){
            throw new IllegalArgumentException(res.getError());
        }
        return res.getData();
    }

    public void delete() {
        try{
            serviceControl.deleteTrans();
        }
        catch (Exception e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }



}
