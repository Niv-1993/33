package DataLayer;

import BussinessLayer.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import enums.Area;

public class DataBase {
    private static DataBase db=null;
    private List<DriverDTO> drivers;
    private List<TruckDTO> trucks;
    private List<TransportationDTO> trans;
    private List<LicenseDTO> licenses;
    private List<BranchDTO> branches;
    private List<supplierDTO> suppliers;
    private List<ShippingAreaDTO> shippingAreas;
    private List<AddressDTO> addresses;
    public List <ItemDTO> items;

    public static DataBase init() {
        if(db==null){
            db=new DataBase();
        }
        return db;
    }
    private DataBase(){
        drivers=new LinkedList<>();
        trucks=new LinkedList<>();
        trans=new LinkedList<>();
        licenses=new LinkedList<>();
        branches=new LinkedList<>();
        suppliers=new LinkedList<>();
        shippingAreas=new LinkedList<>();
        addresses=new LinkedList<>();
        items=new LinkedList<>();
    }

    public List<DriverDTO> getDrivers() { return drivers; }
    public List<LicenseDTO> getLicenses() { return licenses; }
    public List<ShippingAreaDTO> getShippingAreas() { return shippingAreas; }
    public List<TransportationDTO> getTrans() { return trans; }
    public void setDrivers(List<DriverDTO> drivers) { this.drivers = drivers; }
    public List<TruckDTO> getTrucks() { return trucks; }
    public List<AddressDTO> getAddresses() { return addresses; }
    public List<ItemDTO> getItems() { return items; }

    public List<BranchDTO> getBranches() { return branches; }
    public List<supplierDTO> getSuppliers(){return suppliers;}
    public void setBranches(List<BranchDTO> branches) { this.branches = branches; }

    public void setSuppliers(List<supplierDTO> suppliers) { this.suppliers = suppliers; }


    public void setItems(List<ItemDTO> items) { this.items = items; }
    public void setLicenses(List<LicenseDTO> licenses) { this.licenses = licenses; }
    public void setShippingAreas(List<ShippingAreaDTO> shippingAreas) { this.shippingAreas = shippingAreas; }
    public void setTrans(List<TransportationDTO> trans) { this.trans = trans; }
    public void setTrucks(List<TruckDTO> trucks) { this.trucks = trucks; }
    public void setAddresses(List<AddressDTO> addresses) { this.addresses = addresses; }

    public void addDriver(DriverDTO driverDTO){if(!drivers.contains(driverDTO)) drivers.add(driverDTO);}
    public void addTruck(TruckDTO truckDTO){if(!trucks.contains(truckDTO)) trucks.add(truckDTO);}
    public void addLicense(LicenseDTO licenseDTO){if(!licenses.contains(licenseDTO)) licenses.add(licenseDTO);}
    public void addTransportation(TransportationDTO transportationDTO){if(!trans.contains(transportationDTO)) trans.add(transportationDTO);}
    public void addBranch(BranchDTO branchDTO){if(!branches.contains(branchDTO)) branches.add(branchDTO);}
    public void addSupplier(supplierDTO branchDTO){if(!suppliers.contains(branchDTO)) suppliers.add(branchDTO);}
    public void addShippingArea(ShippingAreaDTO shippingAreaDTO){if(!shippingAreas.contains(shippingAreaDTO)) shippingAreas.add(shippingAreaDTO);}
    public void addAddress(AddressDTO address){if(!addresses.contains(address)) addresses.add(address);}

    public void removeAddress(AddressDTO addressDTO){addresses.remove(addressDTO);}
    public void removeDriver(int id){drivers.remove(getDriver(id));}
    public void removeDriver(DriverDTO driverDTO){if(drivers.contains(driverDTO)) drivers.remove(driverDTO);}
    public void removeTruck(TruckDTO truckDTO){if(trucks.contains(truckDTO)) trucks.remove(truckDTO);}
    public void removeLicense(LicenseDTO licenseDTO){if(licenses.contains(licenseDTO)) licenses.remove(licenseDTO);}
    public void removeTransportation(TransportationDTO transportationDTO){if(trans.contains(transportationDTO)) trans.remove(transportationDTO);}
    public void removeBranch(BranchDTO branchDTO){if(branches.contains(branchDTO)) branches.remove(branchDTO);}
    public void removeSupplier(supplierDTO branchDTO){if(suppliers.contains(branchDTO)) suppliers.remove(branchDTO);}
    public void removeShippingArea(ShippingAreaDTO shippingAreaDTO){if(shippingAreas.contains(shippingAreaDTO)) shippingAreas.remove(shippingAreaDTO);}

    public TruckDTO getTruck(long id){
        for (TruckDTO truck:trucks) {
            if(id==truck.getId())
                return truck;
        }
        return null;
    }
    public DriverDTO getDriver(int id){
        for (DriverDTO driver:drivers) {
            if(id==driver.getId())
                return driver;
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataBase dataBase = (DataBase) o;
        return Objects.equals(drivers, dataBase.drivers) && Objects.equals(trucks, dataBase.trucks) && Objects.equals(trans, dataBase.trans) && Objects.equals(licenses, dataBase.licenses) && Objects.equals(branches, dataBase.branches) && Objects.equals(suppliers, dataBase.suppliers) && Objects.equals(shippingAreas, dataBase.shippingAreas) && Objects.equals(addresses, dataBase.addresses) && Objects.equals(items, dataBase.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(drivers, trucks, trans, licenses, branches, suppliers, shippingAreas, addresses, items);
    }

    public BranchDTO getBranch(String phone) {
        for (BranchDTO site:branches) {
            if( site.phone==phone)
                return site;
        }
        return null;
    }
    public supplierDTO getSupplier(String phone) {
        for (supplierDTO site:suppliers) {
            if( site.phone==phone)
                return site;
        }
        return null;
    }

    public LicenseDTO getLicense(int kg) {

        for (LicenseDTO lic:licenses) {
            if(lic.getKg()==kg)
                return lic;
        }

        return null;

    }

    public AddressDTO getAddress(int number,String street,String city) {
        for(AddressDTO add: addresses ){
            if(add.getCity()==city & add.getStreet()==street & add.getNumber()==number)
                return add;
        }

        return null;
    }

    public ShippingAreaDTO getShippingArea(Area area) {

        for(ShippingAreaDTO add:shippingAreas){
            if(add.getArea().equals(area))
                return add;
        }
        return null;
    }

    public void addItem(ItemDTO item) {
        if(!items.contains(item))
            items.add(item);
    }
    public void removeItem(Item item){items.remove(item);}
}
