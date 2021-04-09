package DataLayer;

import BussinessLayer.*;
import enums.Area;
import java.util.*;

public class DataController {

    private final DataBase dataBase;

    public DataController(){
        dataBase=DataBase.init();
    }




    private HashMap<BranchDTO, List<ItemDTO>> getItemsList(HashMap<Site, List<Item>> deliveryItems) {
        HashMap<BranchDTO, List<ItemDTO>> lis=new HashMap<>();
        for(Map.Entry<Site, List<Item>> entry: deliveryItems.entrySet()){

            BranchDTO newSite= dataBase.getBranch(entry.getKey().getPhone());
            List<ItemDTO> newLis=new LinkedList<>();
            for (Item item:entry.getValue()) {
                newLis.add(new ItemDTO(item.getId(),item.getName()));
            }
            lis.put(newSite,newLis);
        }
        return lis;
    }

    public List<TruckDTO> getTrucks(){return dataBase.getTrucks();}
    public List<DriverDTO> getDrivers(){return dataBase.getDrivers();}
    public List<TransportationDTO> getTransportations(){return dataBase.getTrans();}
    public List<BranchDTO> getBranches(){return dataBase.getBranches();}
    public List<ItemDTO> getItems(){return dataBase.getItems();}
    public List<LicenseDTO> getLicenses(){return dataBase.getLicenses();}

    public ShippingAreaDTO getShippingAreaDTO(Area shippingArea) { return dataBase.getShippingArea(shippingArea); }

    public AddressDTO getAddress(Address address) { return dataBase.getAddress(address.getNumber(),address.getStreet(),address.getCity()); }

    public TruckDTO getTruckDTO(TruckDTO truck) { return dataBase.getTruck(truck.getId()); }
    public TruckDTO getTruckDTO(long id) { return dataBase.getTruck(id); }

    public DriverDTO getDriverDTO(long driver) { return dataBase.getDriver(driver); }

    public LicenseDTO getLicenseDTO(int kg) { return dataBase.getLicense(kg); }

    public void addDriver(DriverDTO driver) {  dataBase.addDriver(driver); }

    public void addLicense(LicenseDTO lic){dataBase.addLicense(lic);}
    public void addItem(ItemDTO item){dataBase.addItem(item);}
    public void addShippingArea(ShippingAreaDTO ship){dataBase.addShippingArea(ship);}
    public void addAddress(AddressDTO add){dataBase.addAddress(add);}
    public void addBranch(BranchDTO site){dataBase.addBranch(site);}
    public void addSupplier(SupplierDTO site){dataBase.addSupplier(site);}
    public void addTruck(TruckDTO truck){ dataBase.addTruck(truck);}
    public void addTrucks(List<TruckDTO> trucks){ dataBase.setTrucks(trucks); }
    public void addBranches(List<BranchDTO> sites){dataBase.setBranches(sites);}
    public void addSuppliers(List<SupplierDTO> suppliers){dataBase.setSuppliers(suppliers);}
    public void addItems(List<ItemDTO> items){dataBase.setItems(items);}
    public void addDrivers(List<DriverDTO> drivers){ dataBase.setDrivers(drivers); }

    public void removeDriver(int id) { dataBase.removeDriver(id); }

    public List<SupplierDTO> getSuppliers() {return dataBase.getSuppliers(); }
    public void addTrns(TransportationDTO transportationDTO) { dataBase.addTransportation(transportationDTO); }
}
