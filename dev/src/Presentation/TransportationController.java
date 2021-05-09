package Presentation;
import Business.Transportation.*;
import Business.ApplicationFacade.Responses.ResponseT;
import Business.ApplicationFacade.Objects.*;
import java.util.*;

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
        ResponseT<TransportationServiceDTO> tra = serviceControl.setTransportationDriver(t);
        if (tra.ErrorOccured()){
            throw new IllegalArgumentException(tra.getErrorMessage());
        }
    }

    public void setTruckOnTransportation(TransportationServiceDTO t){
        ResponseT<TransportationServiceDTO> tra = serviceControl.setTransportationTruck(t);
        if (tra.ErrorOccured()){
            throw new IllegalArgumentException(tra.getErrorMessage());
        }
    }
    public void setTransportationWeight(TransportationServiceDTO t){
        ResponseT<TransportationServiceDTO> tra = serviceControl.setTransportationWeight(t);
        if (tra.ErrorOccured()){
            throw new IllegalArgumentException(tra.getErrorMessage());
        }
    }
    public void setTransportation(TransportationServiceDTO t){
        ResponseT<TransportationServiceDTO> tra = serviceControl.setTransportation(t);
        if (tra.ErrorOccured()){
            throw new IllegalArgumentException(tra.getErrorMessage());
        }
    }
    public void setTransportationDate(TransportationServiceDTO t){
        ResponseT<TransportationServiceDTO> tra = serviceControl.setTransportationDate(t);
        if (tra.ErrorOccured()){
            throw new IllegalArgumentException(tra.getErrorMessage());
        }
    }
    public void setTransportationLeavingTime(TransportationServiceDTO t){
        ResponseT<TransportationServiceDTO> tra = serviceControl.setTransportationTime(t);
        if (tra.ErrorOccured()){
            throw new IllegalArgumentException(tra.getErrorMessage());
        }
    }
    public void setSuppliersToTransportation(TransportationServiceDTO tr){
        ResponseT<TransportationServiceDTO> res = serviceControl.setTransportationSuppliersItems(tr);
        if(res.ErrorOccured()){
            throw new IllegalArgumentException(res.getErrorMessage());
        }
    }
    public void setDeliveryItemsToTransportation(TransportationServiceDTO tr){
        ResponseT<TransportationServiceDTO> res = serviceControl.setTransportationDeliveryItems(tr);
        if(res.ErrorOccured()){
            throw new IllegalArgumentException(res.getErrorMessage());
        }
    }
    public void setTransportationArea(TransportationServiceDTO t){
        ResponseT<TransportationServiceDTO> res=serviceControl.setArea(t);
        if(res.ErrorOccured())
        {
            throw new IllegalArgumentException(res.getErrorMessage());
        }
    }

    /**
     * Getters for every object's list.
     * @return : the objects list
     */
    public List<DriverServiceDTO> getAllDrivers(){
        ResponseT<List<DriverServiceDTO>> res = serviceControl.getDTODrivers();
        if(res.ErrorOccured()){
            throw new IllegalArgumentException(res.getErrorMessage());
        }
        return res.getValue();
    }
    public List<ItemServiceDTO> getAllItems(){
        ResponseT<List<ItemServiceDTO>> res = serviceControl.getAllDTOItems();
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
    public List<ItemServiceDTO> getItemsBySupplier(int id) {

        ResponseT<List<ItemServiceDTO>> res=serviceControl.getDTOItemsBySupplier(id);
        if(res.ErrorOccured()){
            throw new IllegalArgumentException(res.getErrorMessage());
        }
        return res.getValue();
    }
    public List<TransportationServiceDTO> getAllTransportations(){

        ResponseT<List<TransportationServiceDTO>> res = serviceControl.getDTOTransportations();
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

    /**
     * Getters for return an object by it's identifier.
     * @param id: the identifier of the object.
     * @return : the object.
     */
    public TransportationServiceDTO getTransportation(long id){
        ResponseT<TransportationServiceDTO> res = serviceControl.getTransportation(id);
        if(res.ErrorOccured()){
            throw new IllegalArgumentException(res.getErrorMessage());
        }
        return res.getValue();
     }
    public DriverServiceDTO getDriver(long id){
        ResponseT<DriverServiceDTO> driver =  serviceControl.getDriver(id);
        if(driver.ErrorOccured()){
            throw new IllegalArgumentException(driver.getErrorMessage());
        }
        return driver.getValue();
    }
    public ItemServiceDTO getItem(long id){
        ResponseT<ItemServiceDTO> item =  serviceControl.getItem(id);
        if(item.ErrorOccured()){
            throw new IllegalArgumentException(item.getErrorMessage());
        }
        return item.getValue();
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
    public TruckServiceDTO getTruck(long id){
        ResponseT<TruckServiceDTO> res = serviceControl.getTruck(id);
        if(res.ErrorOccured()) {
            throw new IllegalArgumentException(res.getErrorMessage());
        }
        return res.getValue();
    }

    /**
     * Method for initializing new transportation to create.
     * @return : returns the new transportation object
     */
    public TransportationServiceDTO createNewTransportation() {
        ResponseT<TransportationServiceDTO> res = serviceControl.createNewTransportation();
        if(res.ErrorOccured()){
            throw new IllegalArgumentException(res.getErrorMessage());
        }
        return res.getValue();
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
