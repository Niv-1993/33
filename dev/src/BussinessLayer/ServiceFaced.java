package BussinessLayer;

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
    private static ServiceFaced serviceFaced = null;

    public static ServiceFaced initial() {
        if(serviceFaced == null){
            serviceFaced = new ServiceFaced();
        }
        return serviceFaced;
    }
    public StringBuilder createTransportation(){
        StringBuilder acc = new StringBuilder();
        return acc;
    }
    public void orderMisses(List<Integer> missingItems){

    }
    public List<DriverServiceDTO> getDTODrivers(){
        List<DriverServiceDTO> returnD = new LinkedList<>();
        List<Driver> drivers = driverService.getDriversList();
        for (Driver d: drivers) {
            returnD.add(toDriverServiceDTO(d));
        }
        return returnD;
    }
    public List<BranchServiceDTO> getDTOBranches(){
        List<BranchServiceDTO> returnB = new LinkedList<>();
        List<Branch> branches = siteService.getBranches();
        for (Branch b: branches) {
            returnB.add(toBranchServiceDTO(b));
        }
        return returnB;
    }
    public List<SupplierServiceDTO> getDTOSuppliers(){
        List<SupplierServiceDTO> returnS = new LinkedList<>();
        List<Supplier> suppliers = siteService.getSuppliers();
        for (Supplier s:suppliers) {
            returnS.add(toSupplierServiceDTO(s));
        }
        return returnS;
    }
    public List<TransportationServiceDTO> getDTOTransportations(){
        List<TransportationServiceDTO> returnT = new LinkedList<>();
        List<Transportation> transportations = transportationService.getTransportationsList();
        for (Transportation t: transportations){
            returnT.add(toTransportationServiceDTO(t));
        }
        return returnT;
    }
    public List<TruckServiceDTO> getDTOTrucks(){
        List<TruckServiceDTO> returnT = new LinkedList<>();
        List<Truck> trucks = truckService.getTrucksList();
        for (Truck t:trucks) {
            returnT.add(toTruckServiceDTO(t));
        }
        return returnT;
    }
    public StringBuilder registerADriver(int id){
        StringBuilder acc = new StringBuilder();
        try{
            acc.append(driverService.getDriver(id));
        }catch(Exception e){
            return acc.append(e.getMessage());
        }
        return acc;
    }
    public StringBuilder registerATruck(int truckId, int transportationId){
        StringBuilder acc = new StringBuilder();
        try{
            //set a the truck to this transportation
            transportationService.getTransportationById(transportationId).setTruck(truckService.getTruck(truckId));
            acc.append("Truck :").append(truckId).append("was register to a transportation: ").append((transportationId));
        }catch(Exception e){
            acc.append(e.getMessage());
        }
        return acc;
    }
    public StringBuilder registerADriver(int driverID, int transportationId){
        StringBuilder acc = new StringBuilder();
        try {
            transportationService.getTransportationById(transportationId).setDriver(driverService.getDriver(driverID));
            acc.append("Driver : ").append(driverID).append("was register to a transportation: ").append(transportationId);
        }catch (Exception e){
            acc.append(e.getMessage());
        }
        return acc;
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
