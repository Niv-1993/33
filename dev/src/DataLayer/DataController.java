package DataLayer;

import BussinessLayer.*;

import java.util.*;

public class DataController {

    private static DataController dataController=null;
    private final DataBase dataBase;

    private DataController(){

        dataBase=DataBase.init();
    }
    public static DataController init(){
        if(dataController==null){
            dataController=new DataController();
        }
        return dataController;
    }
    public void addTrucks(List<Truck> trucks){

        List<TruckDTO> lis=new LinkedList<>();
        for (Truck truck:trucks) {
            lis.add(new TruckDTO(truck.getId(),new LicenseDTO(truck.getLicense().getKg()),truck.getMaxWeight(),truck.getNetWeight(),truck.getModel()));
        }
        dataBase.setTrucks(lis);
    }
    public void addDrivers(List<Driver> drivers){

        List<DriverDTO> lis=new LinkedList<>();
        for (Driver driver:drivers) {
            lis.add(new DriverDTO(driver.getId(),driver.getName(),new LicenseDTO(driver.getLicense().getKg())));
        }
        dataBase.setDrivers(lis);
    }
    public void addTransportations(List<Transportation> trans){

        List<TransportationDTO> lis=new LinkedList<>();
        for (Transportation tran:trans) {
            lis.add(new TransportationDTO(tran.getId(),tran.getDate(),tran.getLeavingTime(),getDriverDTO(tran.getDriver()),getTruckDTO(tran.getTruck()),tran.getWeight(),getItemsList(tran.getDeliveryItems())));
        }
        dataBase.setTrans(lis);
    }


    private HashMap<SiteDTO, List<ItemDTO>> getItemsList(HashMap<Site, List<Item>> deliveryItems) {
        HashMap<SiteDTO, List<ItemDTO>> lis=new HashMap<>();
        for(Map.Entry<Site, List<Item>> entry: deliveryItems.entrySet()){

            SiteDTO newSite= dataBase.getSite(entry.getKey().getPhone());
            List<ItemDTO> newLis=new LinkedList<>();
            for (Item item:entry.getValue()) {
                newLis.add(new ItemDTO(item.getId(),item.getName()));
            }
            lis.put(newSite,newLis);
        }
        return lis;
    }

    private SiteDTO getSite(Site site) {

        SiteDTO sit=dataBase.getSite(site.getPhone());
        if(sit==null){
            AddressDTO addr=getAddress(site.getAddress());
            ShippingAreaDTO ship=getShippingAreaDTO(site.getShippingArea());
            sit=new SiteDTO(site.getPhone(),site.getContactName(),site.getId(),addr,ship);
        }
        return sit;
    }

    private ShippingAreaDTO getShippingAreaDTO(ShippingArea shippingArea) {
        ShippingAreaDTO add= dataBase.getShippingArea(shippingArea.getArea());
        if(add==null){
            add=new ShippingAreaDTO(shippingArea.getArea());
            dataBase.addShippingArea(add);
        }
        return add;

    }

    private AddressDTO getAddress(Address address) {

        AddressDTO add= dataBase.getAddress(address.getNumber(),address.getStreet(),address.getCity());
        if(add==null){
            add=new AddressDTO(address.getNumber(),address.getStreet(), address.getCity());
            dataBase.addAddress(add);
        }
        return add;
    }

    private TruckDTO getTruckDTO(Truck truck) {

        TruckDTO ret=dataBase.getTruck(truck.getId());
        if(ret==null){
            ret=new TruckDTO(truck.getId(), getLicenseDTO(truck.getLicense().getKg()), truck.getMaxWeight(), truck.getNetWeight(), truck.getModel());
        }
        return ret;

    }

    private DriverDTO getDriverDTO(Driver driver) {
        DriverDTO ret=dataBase.getDriver(driver.getId());
        if(ret!=null)
            return ret;
        else{
            LicenseDTO addle=getLicenseDTO(driver.getLicense().getKg());
            DriverDTO add=new DriverDTO(driver.getId(),driver.getName(),addle);
            dataBase.addDriver(add);
            return add;
        }
    }

    private LicenseDTO getLicenseDTO(int kg) {
        LicenseDTO ret= dataBase.getLicense(kg);
        if(ret==null)
        {
            ret=new LicenseDTO(kg);
            dataBase.addLicense(ret);
        }
        return ret;

    }


    public void addDriver(Driver driver) {
       DriverDTO driver1=dataBase.getDriver(driver.getId()) ;
       if(driver1==null){
           driver1=new DriverDTO(driver.getId(), driver.getName(),getLicenseDTO(driver.getLicense().getKg()));
           dataBase.addDriver(driver1);
       }
    }

    public void removeDriver(Driver driver) {
        dataBase.removeDriver(driver.getId());
    }
}
