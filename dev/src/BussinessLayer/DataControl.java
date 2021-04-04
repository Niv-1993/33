package BussinessLayer;

import DataLayer.*;

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
        for (TruckDTO truck:dataController.getTrucks()) {

            ret.put(truck.getId(),new Truck(truck.getId(), new License(truck.getLicense().getKg()), truck.getMaxWeight(), truck.getNetWeight(),truck.getModel()));
        }
        return ret;
    }

    public Map<Integer, Driver> loadDrivers() {

        Map<Integer, Driver> ret=new HashMap<>();
        for (DriverDTO driver : dataController.getDrivers()) {
            ret.put(driver.getId(), new Driver(driver.getId(), driver.getName(), new License(driver.getLicense().getKg())));
        }
        return ret;
    }

    public HashMap<Long, Transportation> loadTrans() {

        HashMap<Long,Transportation> ret=new HashMap<>();
        for (TransportationDTO trans:dataController.getTransportations()) {
            ret.put(trans.getId(),new Transportation(trans.getId(),trans.getDate(),trans.getLeavingTime(),getDriver(trans.getDriver().getId()),getTruck(trans.getTruck()), trans.getWeight(),getItemsList(trans.getDeliveryItems()),getSuppliers(trans.getSuppliers())));
        }
        return  ret;
    }
    public Driver getDriver(int id){
        DriverDTO dri=dataController.getDriverDTO(id);
        return new Driver(dri.getId(), dri.getName(),getLicense(dri.getLicense()));
    }
    public Truck getTruck(TruckDTO tru){
        TruckDTO truck=dataController.getTruckDTO(tru);
        return new Truck(truck.getId(), getLicense(truck.getLicense()), truck.getMaxWeight(), tru.getNetWeight(), truck.getModel());
    }
    public License getLicense(LicenseDTO lic){
        return new License(lic.getKg());
    }
    private HashMap<Branch, List<Item>> getItemsList(HashMap<BranchDTO, List<ItemDTO>> deliveryItems) {
        HashMap<Branch, List<Item>> lis=new HashMap<>();
        for(Map.Entry<BranchDTO, List<ItemDTO>> entry: deliveryItems.entrySet()){

            Branch newSite= new Branch(entry.getKey().getPhone(),entry.getKey().getContactName(),entry.getKey().getId(),getAddress(entry.getKey().getAddress()),getShipping(entry.getKey().getShippingArea()));
            List<Item> newLis=new LinkedList<>();
            for (ItemDTO item:entry.getValue()) {
                newLis.add(new Item(item.getId(),item.getName()));
            }
            lis.put(newSite,newLis);
        }
        return lis;
    }
    private Address getAddress(AddressDTO add){
        return new Address(add.getNumber(),add.getStreet(),add.getCity());
    }
    private ShippingArea getShipping(ShippingAreaDTO ship){
        return new ShippingArea(ship.getArea());
    }
    private List<Supplier> getSuppliers(List<supplierDTO> supp){

        List<Supplier> ret=new LinkedList<>();
    for (supplierDTO sup: supp){
        ret.add(new Supplier(sup.getPhone(), sup.getContactName(), sup.getId(),getAddress(sup.getAddress()),getShipping(sup.getShippingArea())));
    }
    return ret;
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
        for (supplierDTO sup:dataController.getSuppliers()) {
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
}
