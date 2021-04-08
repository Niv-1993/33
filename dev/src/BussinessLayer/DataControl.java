package BussinessLayer;

import DataLayer.*;
import enums.Pair;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DataControl {

    private static DataControl dataControl=null;
    private final DataController dataController;

    private DataControl(){dataController=DataController.init();}


    public static DataControl init(){
        if(dataControl==null){
            dataControl=new DataControl();
        }
        return dataControl;
    }

    public Map<Long, Truck> loadTrucks() {

        Map<Long, Truck> ret=new HashMap<>();
        List<TruckDTO> trucks=dataController.getTrucks();
        for (TruckDTO truck:trucks) {

            ret.put(truck.getId(),new Truck(truck.getId(), getLicense(truck.getLicense()), truck.getMaxWeight(), truck.getNetWeight(),truck.getModel()));
        }
        return ret;
    }

    public Map<Long, Driver> loadDrivers() {

        Map<Long, Driver> ret=new HashMap<>();
        for (DriverDTO driver : dataController.getDrivers()) {
            ret.put(driver.getId(), new Driver(driver.getId(), driver.getName(), new License(driver.getLicense().getKg())));
        }
        return ret;
    }

    public HashMap<Long, Transportation> loadTrans() {

        HashMap<Long,Transportation> ret=new HashMap<>();
        for (TransportationDTO trans:dataController.getTransportations()) {
            ret.put(trans.getId(),new Transportation(trans.getId(),trans.getDate(),trans.getLeavingTime(),getDriver(trans.getDriver().getId()),getTruck(trans.getTruck()), trans.getWeight(),getItemsList(trans.getDeliveryItems()),getSupplierItemsList(trans.getSuppliers())));
        }
        return  ret;
    }
    public Driver getDriver(long id){
        DriverDTO dri=dataController.getDriverDTO(id);
        return new Driver(dri.getId(), dri.getName(),getLicense(dri.getLicense()));
    }
    public Truck getTruck(TruckDTO tru){
        TruckDTO truck=dataController.getTruckDTO(tru);
        return new Truck(truck.getId(), getLicense(truck.getLicense()), truck.getMaxWeight(), tru.getNetWeight(), truck.getModel());
    }
    public TruckDTO getTruckDTO(Truck tru){
        return dataController.getTruckDTO(tru.getId());
    }
    public License getLicense(LicenseDTO lic){
        return new License(lic.getKg());
    }

    private HashMap<Branch, List<Pair<Item,Integer>>> getItemsList(HashMap<BranchDTO, List<Pair<ItemDTO,Integer>>> deliveryItems) {

        List<Pair<Item,Integer>> newLis;
        HashMap<Branch, List<Pair<Item,Integer>>> lis=new HashMap<>();
        for(Map.Entry<BranchDTO, List<Pair<ItemDTO,Integer>>> entry: deliveryItems.entrySet()){

            Branch newSite= new Branch(entry.getKey().getPhone(),entry.getKey().getContactName(),entry.getKey().getId(),getAddress(entry.getKey().getAddress()),getShipping(entry.getKey().getShippingArea()));
            newLis=new LinkedList<>();
            for (Pair<ItemDTO,Integer> item:entry.getValue()) {
                newLis.add(new Pair<>(new Item(item.getFir().getId(), item.getFir().getName()), item.getSec()));
            }
            lis.put(newSite,newLis);
        }
        return lis;
    }
    private HashMap<BranchDTO, List<Pair<ItemDTO,Integer>>> getItemsListDTO(HashMap<Branch, List<Pair<Item,Integer>>> deliveryItems) {

        List<Pair<ItemDTO,Integer>> newLis;
        HashMap<BranchDTO, List<Pair<ItemDTO,Integer>>> lis=new HashMap<>();
        for(Map.Entry<Branch, List<Pair<Item,Integer>>> entry: deliveryItems.entrySet()){

            BranchDTO newSite= new BranchDTO(entry.getKey().getPhone(),entry.getKey().getContactName(),entry.getKey().getId(),getAddressDTO(entry.getKey().getAddress()), dataController.getShippingAreaDTO(entry.getKey().getShippingArea().getArea()));
            newLis=new LinkedList<>();
            for (Pair<Item,Integer> item:entry.getValue()) {
                newLis.add(new Pair<>(new ItemDTO(item.getFir().getId(), item.getFir().getName()), item.getSec()));
            }
            lis.put(newSite,newLis);
        }
        return lis;
    }
    private HashMap<SupplierDTO, List<Pair<ItemDTO,Integer>>> getSupplierItemsListDTO(HashMap<Supplier, List<Pair<Item,Integer>>> deliveryItems) {

        HashMap<SupplierDTO, List<Pair<ItemDTO,Integer>>> lis=new HashMap<>();
        for(Map.Entry<Supplier, List<Pair<Item,Integer>>> entry: deliveryItems.entrySet()){
            SupplierDTO newSite= new SupplierDTO(entry.getKey().getPhone(),entry.getKey().getContactName(),entry.getKey().getId(),getAddressDTO(entry.getKey().getAddress()),dataController.getShippingAreaDTO(entry.getKey().getShippingArea().getArea()));
            List<Pair<ItemDTO,Integer>> newLists=new LinkedList<>();
            for (Pair<Item,Integer> item:entry.getValue()) {
                newLists.add(new Pair<>(new ItemDTO(item.getFir().getId(),item.getFir().getName()), item.getSec()));
            }
            lis.put(newSite,newLists);
        }
        return lis;
    }
    private HashMap<Supplier, List<Pair<Item,Integer>>> getSupplierItemsList(HashMap<SupplierDTO, List<Pair<ItemDTO,Integer>>> deliveryItems) {

        HashMap<Supplier, List<Pair<Item,Integer>>> lis=new HashMap<>();
        for(Map.Entry<SupplierDTO, List<Pair<ItemDTO,Integer>>> entry: deliveryItems.entrySet()){

            Supplier newSite= new Supplier(entry.getKey().getPhone(),entry.getKey().getContactName(),entry.getKey().getId(),getAddress(entry.getKey().getAddress()),getShipping(entry.getKey().getShippingArea()));
            List<Pair<Item,Integer>> newLists=new LinkedList<>();
            for (Pair<ItemDTO,Integer> item:entry.getValue()) {
                newLists.add(new Pair<>(new Item(item.getFir().getId(),item.getFir().getName()), item.getSec()));
            }
            lis.put(newSite,newLists);
        }
        return lis;
    }
    private Address getAddress(AddressDTO add){
        return new Address(add.getNumber(),add.getStreet(),add.getCity());
    }
    private AddressDTO getAddressDTO(Address add){
        return new AddressDTO(add.getNumber(),add.getStreet(),add.getCity());
    }
    private ShippingArea getShipping(ShippingAreaDTO ship){
        return new ShippingArea(ship.getArea());
    }

    public HashMap<Long, Item> getItems() {

        HashMap<Long,Item> ret=new HashMap<>();
        for (ItemDTO item: dataController.getItems()) {
            ret.put(item.getId(),new Item(item.getId(), item.getName()));
        }
        return ret;
    }

    public HashMap<Integer, Supplier> getSuppliers() {
        HashMap<Integer,Supplier> ret=new HashMap<>();
        for (SupplierDTO sup:dataController.getSuppliers()) {
            ret.put(sup.getId(), new Supplier(sup.getPhone(), sup.getContactName(), sup.getId(), getAddress(sup.getAddress()),getShipping(sup.getShippingArea())));
        }
        return ret;
    }

    public HashMap<Integer, Branch> getBranches() {

        HashMap<Integer,Branch> ret=new HashMap<>();
        for (BranchDTO sup:dataController.getBranches()) {
            ret.put(sup.getId(), new Branch(sup.getPhone(), sup.getContactName(), sup.getId(), getAddress(sup.getAddress()),getShipping(sup.getShippingArea())));
        }
        return ret;
    }

    public void addTransportation(Transportation trans) {
       dataController.addTrns(new TransportationDTO(trans.getId(),trans.getDate(),trans.getLeavingTime(), dataController.getDriverDTO(trans.getDriver().getId()), getTruckDTO(trans.getTruck()), trans.getWeight(),getItemsListDTO(trans.getDeliveryItems()),getSupplierItemsListDTO(trans.getSuppliers())));
    }

}
