package DataLayer;

import BussinessLayer.*;

import java.util.*;

public class DataController {

    private static DataController dataController=null;
    private DataBase dataBase;

    private DataController(){

        dataBase=DataBase.init();
    }
    public  DataController init(){
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
        Iterator it = deliveryItems.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            SiteDTO newSite=getSite((Site)pair.getKey());
            List<ItemDTO> newLis=new LinkedList<>();
            for (Item item:(List<Item>)pair.getValue()) {
                newLis.add(new ItemDTO(item.getId(),item.getName()));
            }
            lis.put(newSite,newLis);
        }
        return lis;
    }

    private SiteDTO getSite(Site site) {
        SiteDTO sit=dataBase.getSite(site.getAddress(),site.getPhone());
        if(sit!=null)
            return sit;
        return null;
    }

    private TruckDTO getTruckDTO(Truck truck) {

        TruckDTO ret=dataBase.getTruck(truck.getId());
        if(ret!=null)
            return ret;
        return null;
    }

    private DriverDTO getDriverDTO(Driver driver) {
        DriverDTO ret=dataBase.getDriver(driver.getId());
        if(ret!=null)
            return ret;
        return null;
    }

}
