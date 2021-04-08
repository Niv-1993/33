package ServiceLayer;
import BussinessLayer.Branch;
import BussinessLayer.ServiceFaced;
import BussinessLayer.Transportation;
import DataLayer.TransportationDTO;
import Responses.Response;
import Responses.ResponseT;
import ServiceLayer.Objects.*;
import enums.Area;

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

    public TransportationServiceDTO setDriverOnTransportation(TransportationServiceDTO t){
        ResponseT<TransportationServiceDTO> tra = serviceControl.setTransportationDriver(t);
        if (tra.ErrorOccured()){
            throw new IllegalArgumentException(tra.getErrorMessage());
        }
        return t;
    }

    public TransportationServiceDTO setTruckOnTransportation(TransportationServiceDTO t){
        ResponseT<TransportationServiceDTO> tra = serviceControl.setTransportationTruck(t);
        if (tra.ErrorOccured()){
            throw new IllegalArgumentException(tra.getErrorMessage());
        }
        return t;
    }
    public TransportationServiceDTO setTransportationWeight(TransportationServiceDTO t){
        ResponseT<TransportationServiceDTO> tra = serviceControl.setTransportationWeight(t);
        if (tra.ErrorOccured()){
            throw new IllegalArgumentException(tra.getErrorMessage());
        }
        return t;
    }
    public TransportationServiceDTO setTransportation(TransportationServiceDTO t){
        ResponseT<TransportationServiceDTO> tra = serviceControl.setTransportation(t);
        if (tra.ErrorOccured()){
            throw new IllegalArgumentException(tra.getErrorMessage());
        }
        return t;
    }
    public TransportationServiceDTO setTransportationDate(TransportationServiceDTO t){
        ResponseT<TransportationServiceDTO> tra = serviceControl.setTransportationDate(t);
        if (tra.ErrorOccured()){
            throw new IllegalArgumentException(tra.getErrorMessage());
        }
        return t;
    }
    public TransportationServiceDTO setTransportationLeavingTime(TransportationServiceDTO t){
        ResponseT<TransportationServiceDTO> tra = serviceControl.setTransportationTime(t);
        if (tra.ErrorOccured()){
            throw new IllegalArgumentException(tra.getErrorMessage());
        }
        return t;
    }


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
    public TransportationServiceDTO setDriverToTransport(TransportationServiceDTO tr){
        ResponseT<TransportationServiceDTO> res=serviceControl.setTransportationDriver(tr);
        if(res.ErrorOccured())
        {
            throw new IllegalArgumentException(res.getErrorMessage());
        }
        return res.getValue();
    }
    public TransportationServiceDTO setDriverToTransportation(TransportationServiceDTO tr){
        ResponseT<TransportationServiceDTO> res = serviceControl.setTransportationDriver(tr);
        if(res.ErrorOccured()){
            throw new IllegalArgumentException(res.getErrorMessage());
        }
        return res.getValue();
    }
    public TransportationServiceDTO setTruckToTransportation(TransportationServiceDTO tr){
        ResponseT<TransportationServiceDTO> res = serviceControl.setTransportationTruck(tr);
        if(res.ErrorOccured()){
            throw new IllegalArgumentException(res.getErrorMessage());
        }
        return res.getValue();
    }
    public TransportationServiceDTO setSuppliersToTransportation(TransportationServiceDTO tr){
        ResponseT<TransportationServiceDTO> res = serviceControl.setTransportationSuppliersItems(tr);
        if(res.ErrorOccured()){
            throw new IllegalArgumentException(res.getErrorMessage());
        }
        return res.getValue();
    }
    public TransportationServiceDTO setDeliveryItemsToTransportation(TransportationServiceDTO tr){
        ResponseT<TransportationServiceDTO> res = serviceControl.setTransportationDeliveryItems(tr);
        if(res.ErrorOccured()){
            throw new IllegalArgumentException(res.getErrorMessage());
        }
        return res.getValue();
    }

    public TransportationServiceDTO createNewTransportation() {
        ResponseT<TransportationServiceDTO> res = serviceControl.createNewTransportation();
        if(res.ErrorOccured()){
            throw new IllegalArgumentException(res.getErrorMessage());
        }
        return res.getValue();
    }
    public TransportationServiceDTO setTransportationArea(TransportationServiceDTO t){
        ResponseT<TransportationServiceDTO> res=serviceControl.setArea(t);
        if(res.ErrorOccured())
        {
            throw new IllegalArgumentException(res.getErrorMessage());
        }
        return res.getValue();
    }
}
