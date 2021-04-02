package BussinessLayer;

import Responses.Response;
import ServiceLayer.Objects.*;

import java.util.*;
// costumer ask for a new transportation
// the system ask for missing <dic<item,Integer>,sites> ,we get the trucks, we get the driver
// we check for the validity of the input
// if yes => create new transportation
// if not => return error
// return the error message

public class ServiceFaced {
    private DriverService driverService;
    private TruckService truckService;
    private SiteService siteService;
    private TransportationService transportationService;
    private ItemService itemService;
    private static ServiceFaced serviceFaced = null;
    private ServiceFaced(){
        driverService = new DriverService();
        truckService = new TruckService();
        siteService = new SiteService();
        transportationService = new TransportationService();
        itemService = new ItemService();

    }
    public static ServiceFaced initial() {
        if(serviceFaced == null){
            serviceFaced = new ServiceFaced();
        }
        return serviceFaced;
    }
    public Response<DriverServiceDTO> getDriver(int id){
        Response<DriverServiceDTO> res = new Response<>();
        try {
            res.setValue(toDriverServiceDTO(driverService.getDriver(id)));
        }catch (Exception e){
            res.setErrorOccurred(e.getMessage());
        }
        return res;
    }
    public Response<List<DriverServiceDTO>> getDTODrivers(){
        Response<List<DriverServiceDTO>> returnResponse = new Response<>();
        List<DriverServiceDTO> returnD = new LinkedList<>();
        try {
            List<Driver> drivers = driverService.getDriversList();
            for (Driver d: drivers) {
                returnD.add(toDriverServiceDTO(d));
            }
            returnResponse.setValue(returnD);
        }catch (Exception e){
            returnResponse.setErrorOccurred(e.getMessage());
        }
        return returnResponse;
    }
    public Response<List<BranchServiceDTO>> getDTOBranches(){
        Response<List<BranchServiceDTO>> returnResponse = new Response<>();
        List<BranchServiceDTO> returnB = new LinkedList<>();
        try {
            List<Branch> branches = siteService.getBranches();
            for (Branch b: branches) {
                returnB.add(toBranchServiceDTO(b));
            }
            returnResponse.setValue(returnB);
        }catch (Exception e){
            returnResponse.setErrorOccurred(e.getMessage());
        }

        return returnResponse;
    }
    public Response<List<SupplierServiceDTO>> getDTOSuppliers(){
        Response<List<SupplierServiceDTO>> returnResponse = new Response<>();
        List<SupplierServiceDTO> returnS = new LinkedList<>();
        try {
            List<Supplier> suppliers = siteService.getSuppliers();
            for (Supplier s:suppliers) {
                returnS.add(toSupplierServiceDTO(s));
            }
            returnResponse.setValue(returnS);
        }catch (Exception e){
            returnResponse.setErrorOccurred(e.getMessage());
        }
        return returnResponse;
    }
    public Response<List<TransportationServiceDTO>> getDTOtransportations(){
        Response<List<TransportationServiceDTO>> returnResponse = new Response<>();
        List<TransportationServiceDTO> returnT = new LinkedList<>();
        try {
            List<Transportation> transportations = transportationService.getTransportationsList();
            for (Transportation t: transportations){
                returnT.add(toTransportationServiceDTO(t));
            }
            returnResponse.setValue(returnT);
        }catch (Exception e){
            returnResponse.setErrorOccurred(e.getMessage());
        }
        return returnResponse;
    }
    public Response<List<ItemServiceDTO>> getAllItems(){
        Response<List<ItemServiceDTO>> returnResponse = new Response<>();
        List<ItemServiceDTO> returnI = new LinkedList<>();
        try {
            List<Item> allItems = itemService.getItemsList();
            for (Item i: allItems){
                returnI.add(toItemServiceDTO(i));
            }
            returnResponse.setValue(returnI);
        }catch (Exception e){
            returnResponse.setErrorOccurred(e.getMessage());
        }
        return returnResponse;


    }
    public Response<List<TruckServiceDTO>> getDTOTrucks(){
        Response<List<TruckServiceDTO>> returnResponse = new Response<>();
        List<TruckServiceDTO> returnT = new LinkedList<>();
        try {
            List<Truck> trucks = truckService.getTrucksList();
            for (Truck t:trucks) {
                returnT.add(toTruckServiceDTO(t));
            }
            returnResponse.setValue(returnT);
        }catch (Exception e){
            returnResponse.setErrorOccurred(e.getMessage());
        }
        return returnResponse;
    }

    public Response<StringBuilder> setTransportationDriver(TransportationServiceDTO t, int id){
        return null;
    }
    public Response<StringBuilder> setTransportationBranch(TransportationServiceDTO t,int id ){
        return null;
    }
    public Response<StringBuilder> setTransportationItems(TransportationServiceDTO t, List<Integer> items){
        return null;
    }
    public Response<StringBuilder> setTransportationSupplier(TransportationServiceDTO t, int id){
        return null;
    }
    public Response<StringBuilder> setTransportationTruck(TransportationServiceDTO t, int id){
        return null;
    }
    public Response<StringBuilder> setTransportation(TransportationServiceDTO t){
        return null;
    }


    private DriverServiceDTO toDriverServiceDTO(Driver d){
        return new DriverServiceDTO(d.getId(),d.getName(),(d.getLicense()).getKg());
    }
    private BranchServiceDTO toBranchServiceDTO(Branch b){
        return new BranchServiceDTO(b.getPhone(),b.getContactName(),b.getId(),b.getShippingArea().toString());
    }
    private ItemServiceDTO toItemServiceDTO(Item i){
        return new ItemServiceDTO(i.getId(),i.getName());
    }
    private SupplierServiceDTO toSupplierServiceDTO(Supplier s){
        return new SupplierServiceDTO(s.getPhone(),s.getContactName(),s.getId(),s.getShippingArea().toString());
    }
    private TruckServiceDTO toTruckServiceDTO(Truck t){
        return new TruckServiceDTO(t.getId(),t.getLicense().getKg(),t.getMaxWeight(),t.getNetWeight(),t.getModel());
    }
    private TransportationServiceDTO toTransportationServiceDTO(Transportation t){
        List<Supplier> s = t.getSuppliers();
        List<SupplierServiceDTO> newSup = new LinkedList<>();
        for(Supplier sup: s){
            newSup.add(toSupplierServiceDTO(sup));
        }
        HashMap<Branch,List<Item>> items = t.getDeliveryItems();
        HashMap<BranchServiceDTO,List<ItemServiceDTO>> newItems = new HashMap<>();
        for(Map.Entry<Branch,List<Item>> entry: items.entrySet()){
            List<Item> i = entry.getValue();
            List<ItemServiceDTO> iDTO = new LinkedList<>();
            for (Item it: i){
                iDTO.add(toItemServiceDTO(it));
            }
            newItems.put(toBranchServiceDTO(entry.getKey()),iDTO);
        }
        return new TransportationServiceDTO(t.getId(),t.getDate(),t.getLeavingTime(),toDriverServiceDTO(t.getDriver()),toTruckServiceDTO(t.getTruck()),t.getWeight(),newItems,newSup);
    }
}
