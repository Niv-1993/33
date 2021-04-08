package BussinessLayer;

import DataLayer.TransportationDTO;
import Responses.Response;
import Responses.ResponseT;
import ServiceLayer.Objects.*;
import enums.Area;
import enums.Pair;

import java.security.KeyStore;
import java.util.*;

public class ServiceFaced {
    private DriverService driverService;
    private TruckService truckService;
    private SiteService siteService;
    private TransportationService transportationService;
    private ItemService itemService;
    private static ServiceFaced serviceFaced = null;
    private DataControl dataControl;


    private ServiceFaced(){
        driverService = new DriverService();
        truckService = new TruckService();
        siteService = new SiteService();
        transportationService = new TransportationService();
        itemService = new ItemService();
        dataControl=DataControl.init();
    }
    private void loadData(){

        this.truckService.loadData(dataControl);
        this.driverService.loadData(dataControl);
        this.transportationService.loadData(dataControl);
        this.itemService.loadData(dataControl);
        this.siteService.loadData(dataControl);
    };
    public static ServiceFaced initial() {
        if(serviceFaced == null){
            serviceFaced = new ServiceFaced();
        }
        serviceFaced.loadData();
        return serviceFaced;
    }
    public ResponseT<DriverServiceDTO> getDriver(long id){
        try {
            return new ResponseT<>(toDriverServiceDTO(driverService.getDriver(id)));
        }catch (Exception e){
            return new ResponseT<>(e.getMessage());
        }
    }
    public ResponseT<BranchServiceDTO> getBranch(int id){
        try{
            return new ResponseT<>(toBranchServiceDTO(siteService.getBranch(id)));
        }catch (Exception e){
            return new ResponseT<>(e.getMessage());
        }
    }
    public ResponseT<SupplierServiceDTO> getSuppliers(int id){
        try {
            return new ResponseT<>(toSupplierServiceDTO(siteService.getSupplier(id)));
        }catch (Exception e){
            return new ResponseT<>(e.getMessage());
        }
    }
    public ResponseT<TruckServiceDTO> getTruck(long id){
        try{
            return new ResponseT<>(toTruckServiceDTO(truckService.getTruck(id)));
        }catch (Exception e){
            return new ResponseT<>(e.getMessage());
        }
    }
    public ResponseT<List<DriverServiceDTO>> getDTODrivers(){
        List<DriverServiceDTO> returnD = new LinkedList<>();
        try {
            List<Driver> drivers = driverService.getDriversList();
            for (Driver d: drivers) {
                returnD.add(toDriverServiceDTO(d));
            }
            return new ResponseT<>(returnD);
        }catch (Exception e){
            return new ResponseT<>(e.getMessage());
        }
    }
    public ResponseT<List<BranchServiceDTO>> getDTOBranches(){
        List<BranchServiceDTO> returnB = new LinkedList<>();
        try {
            List<Branch> branches = siteService.getBranchesList();
            for (Branch b: branches) {
                returnB.add(toBranchServiceDTO(b));
            }
            return new ResponseT<>(returnB);
        }catch (Exception e){
            return new ResponseT<>(e.getMessage());
        }
    }
    public ResponseT<List<SupplierServiceDTO>> getDTOSuppliers(){
        List<SupplierServiceDTO> returnS = new LinkedList<>();
        try {
            List<Supplier> suppliers = siteService.getSuppliersList();
            for (Supplier s:suppliers) {
                returnS.add(toSupplierServiceDTO(s));
            }
            return new ResponseT<>(returnS);
        }catch (Exception e){
            return new ResponseT<>(e.getMessage());
        }
    }
    public ResponseT<List<TransportationServiceDTO>> getDTOtransportations(){
        List<TransportationServiceDTO> returnT = new LinkedList<>();
        try {
            List<Transportation> transportations = transportationService.getTransportationsList();
            for (Transportation t: transportations){
                returnT.add(toTransportationServiceDTO(t));
            }
            return new ResponseT<>(returnT);
        }catch (Exception e){
            return new ResponseT<>(e.getMessage());
        }
    }
    public ResponseT<List<ItemServiceDTO>> getAllItems(){
        List<ItemServiceDTO> returnI = new LinkedList<>();
        try {
            List<Item> allItems = itemService.getItemsList();
            for (Item i: allItems){
                returnI.add(toItemServiceDTO(i));
            }
            return new ResponseT<>(returnI);
        }catch (Exception e){
            return new ResponseT<>(e.getMessage());
        }
    }
    public ResponseT<ItemServiceDTO> getItem(long id){
        try {
            return new ResponseT<>(toItemServiceDTO(itemService.getItem(id)));
        }catch (Exception e){
            return new ResponseT<>(e.getMessage());
        }
    }
    public ResponseT<List<TruckServiceDTO>> getDTOTrucks(){
        List<TruckServiceDTO> returnT = new LinkedList<>();
        try {
            List<Truck> trucks = truckService.getTrucksList();
            for (Truck t:trucks) {
                returnT.add(toTruckServiceDTO(t));
            }
            return new ResponseT<>(returnT);
        }catch (Exception e){
            return new ResponseT<>(e.getMessage());
        }
    }

    public ResponseT<TransportationServiceDTO> setTransportationDriver(TransportationServiceDTO t){
        try {
            Driver d = driverService.getDriver(t.getDriver().getId());
            transportationService.setDriver(t.getId(), d);
            //if we success just return the same
            return new ResponseT<>(t);
        }catch (Exception e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }
    public ResponseT<TransportationServiceDTO> setTransportationDeliveryItems(TransportationServiceDTO t ){
             HashMap<Branch,List<Pair<Item,Integer>>> deliveryItemsb = new HashMap<>();
        try {
            HashMap<BranchServiceDTO,List<Pair<ItemServiceDTO,Integer>>> deliveryItems = t.getDeliveryItems();
            for (Map.Entry<BranchServiceDTO,List<Pair<ItemServiceDTO,Integer>>> entry: deliveryItems.entrySet()){
                List<Pair<Item,Integer>> delivery = new LinkedList<>();
                Branch b = siteService.getBranch(entry.getKey().getId());
                for (Pair<ItemServiceDTO,Integer> item : entry.getValue()){
                    delivery.add(new Pair<>(itemService.getItem(item.getFir().getId()),item.getSec()));
                }
                deliveryItemsb.put(b,delivery);
            }
            transportationService.setDeliveryItems(t.getId(),deliveryItemsb);
            return new ResponseT<>(t);
        }catch (Exception e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }
    public ResponseT<TransportationServiceDTO> setTransportationSuppliersItems(TransportationServiceDTO t ){
        HashMap<Supplier,List<Pair<Item,Integer>>> deliveryItemsb = new HashMap<>();
        try {
            HashMap<SupplierServiceDTO,List<Pair<ItemServiceDTO,Integer>>> deliveryItems = t.getSuppliers();
            for (Map.Entry<SupplierServiceDTO,List<Pair<ItemServiceDTO,Integer>>> entry: deliveryItems.entrySet()){
                List<Pair<Item,Integer>> delivery = new LinkedList<>();
                Supplier b = siteService.getSupplier(entry.getKey().getId());
                for (Pair<ItemServiceDTO,Integer> item : entry.getValue()){
                    delivery.add(new Pair<>(itemService.getItem(item.getFir().getId()),item.getSec()));
                }
                deliveryItemsb.put(b,delivery);
            }
            transportationService.setSuppliersItem(t.getId(),deliveryItemsb);
            return new ResponseT<>(t);
        }catch (Exception e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public ResponseT<TransportationServiceDTO> setTransportationTruck(TransportationServiceDTO t){
        try {
            Truck truck = truckService.getTruck(t.getTruck().getId());
            transportationService.setTruck(t.getId(),truck);
            return new ResponseT<>(t);
        }catch (Exception e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }
    public ResponseT<TransportationServiceDTO> setTransportationTime(TransportationServiceDTO t){
        try {
            transportationService.setTransportationTime(t.getId(),t.getLeavingTime());
            return new ResponseT<>(t);
        }catch (Exception e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }
    public ResponseT<TransportationServiceDTO> setTransportationDate(TransportationServiceDTO t){
        try {
            transportationService.setDate(t.getId(),t.getDate());
            return new ResponseT<>(t);
        }catch (Exception e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }


    private DriverServiceDTO toDriverServiceDTO(Driver d){
        if(d==null){
            return null;
        }
        return new DriverServiceDTO(d.getId(),d.getName(),(d.getLicense()).getKg());
    }
    private BranchServiceDTO toBranchServiceDTO(Branch b){
        if(b==null)
            return null;
        return new BranchServiceDTO(b.getPhone(),b.getContactName(),b.getId(),b.getShippingArea().getArea().toString());
    }
    private Pair<ItemServiceDTO,Integer> toItemPairServiceDTO(Pair<Item,Integer> i){
        if(i==null)
            return null;
        return new Pair<ItemServiceDTO,Integer>(new ItemServiceDTO(i.getFir().getId(),i.getFir().getName()),i.getSec());
    }
    private ItemServiceDTO toItemServiceDTO(Item i){
        if(i==null)
            return null;
        return new ItemServiceDTO(i.getId(),i.getName());
    }
    private SupplierServiceDTO toSupplierServiceDTO(Supplier s){
        if(s==null)
            return null;
        return new SupplierServiceDTO(s.getPhone(),s.getContactName(),s.getId(),s.getShippingArea().getArea().toString());
    }
    private TruckServiceDTO toTruckServiceDTO(Truck t){
        if(t==null)
            return null;
        return new TruckServiceDTO(t.getId(),t.getLicense().getKg(),t.getMaxWeight(),t.getNetWeight(),t.getModel());
    }
    private TransportationServiceDTO toTransportationServiceDTO(Transportation t){
        List<Pair<Item,Integer>> i;
        HashMap<Supplier, List<Pair<Item, Integer>>> suppliers = t.getSuppliers();
        HashMap<SupplierServiceDTO, List<Pair<ItemServiceDTO, Integer>>> newSup=null;
        if(t.getSuppliers()!=null) {
            newSup = new HashMap<>();
            for (Map.Entry<Supplier, List<Pair<Item, Integer>>> entry : suppliers.entrySet()) {
                i = entry.getValue();
                List<Pair<ItemServiceDTO, Integer>> iDTO = new LinkedList<>();
                for (Pair<Item, Integer> it : i) {
                    iDTO.add(toItemPairServiceDTO(it));
                }
                newSup.put(toSupplierServiceDTO(entry.getKey()), iDTO);
            }
        }
        HashMap<Branch, List<Pair<Item, Integer>>> items = t.getDeliveryItems();
        HashMap<BranchServiceDTO, List<Pair<ItemServiceDTO, Integer>>> newItems = null;
        if(t.getDeliveryItems()!=null) {
            newItems = new HashMap<>();
            for (Map.Entry<Branch, List<Pair<Item, Integer>>> entry : items.entrySet()) {
                i = entry.getValue();
                List<Pair<ItemServiceDTO, Integer>> iDTO = new LinkedList<>();
                for (Pair<Item, Integer> it : i) {
                    iDTO.add(toItemPairServiceDTO(it));
                }
                newItems.put(toBranchServiceDTO(entry.getKey()), iDTO);
            }
        }
        TransportationServiceDTO ret=new TransportationServiceDTO(t.getId(),t.getDate(),t.getLeavingTime(),toDriverServiceDTO(t.getDriver()),toTruckServiceDTO(t.getTruck()),t.getWeight(),newItems,newSup,toArea( t.getShippingArea()));
        return ret;
    }

    private Area toArea(ShippingArea shippingArea) {
        if(shippingArea==null)
            return null;
        return shippingArea.getArea();
    }

    public ResponseT<TransportationServiceDTO> createNewTransportation() {

        try {
            ResponseT<TransportationServiceDTO> ret=new ResponseT<>(toTransportationServiceDTO(transportationService.newTransportation()));
            return ret;
        }
        catch (Exception e){
            return new ResponseT<>(e.getMessage());
        }

    }
    public ResponseT<TransportationServiceDTO> setTransportation(TransportationServiceDTO t){
        try {

            return new ResponseT<>(toTransportationServiceDTO( transportationService.saveTransportation(t.getId())));
        }
        catch (Exception e){
            return new ResponseT<>(e.getMessage());
        }
    }
    public ResponseT<TransportationServiceDTO> setArea(TransportationServiceDTO t){
        try {
            transportationService.setArea(t.getId(),t.getArea());
            return new ResponseT<>(t);
        }catch (Exception e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public ResponseT<TransportationServiceDTO> setTransportationWeight(TransportationServiceDTO t) {
        try {
            transportationService.setTransportationWeight(t.getId(),t.getWeight());
            return new ResponseT<>(t);
        }catch (Exception e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
