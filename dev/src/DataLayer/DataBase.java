package DataLayer;

import BussinessLayer.Address;
import BussinessLayer.Driver;
import BussinessLayer.Truck;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class DataBase {
    private static DataBase db=null;
    private List<DriverDTO> drivers;
    private List<TruckDTO> trucks;
    private List<TransportationDTO> trans;
    private List<LicenseDTO> licenses;
    private List<SiteDTO> sites;
    private List<ShippingAreaDTO> shippingAreas;

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
        sites=new LinkedList<>();
        shippingAreas=new LinkedList<>();
    }

    public List<DriverDTO> getDrivers() { return drivers; }
    public List<LicenseDTO> getLicenses() { return licenses; }
    public List<ShippingAreaDTO> getShippingAreas() { return shippingAreas; }
    public List<SiteDTO> getSites() { return sites; }
    public List<TransportationDTO> getTrans() { return trans; }
    public void setDrivers(List<DriverDTO> drivers) { this.drivers = drivers; }
    public List<TruckDTO> getTrucks() { return trucks; }

    public void setLicenses(List<LicenseDTO> licenses) { this.licenses = licenses; }
    public void setSites(List<SiteDTO> sites) { this.sites = sites; }
    public void setShippingAreas(List<ShippingAreaDTO> shippingAreas) { this.shippingAreas = shippingAreas; }
    public void setTrans(List<TransportationDTO> trans) { this.trans = trans; }
    public void setTrucks(List<TruckDTO> trucks) { this.trucks = trucks; }

    public void addDriver(DriverDTO driverDTO){if(!drivers.contains(driverDTO)) drivers.add(driverDTO);}
    public void addTruck(TruckDTO truckDTO){if(!trucks.contains(truckDTO)) trucks.add(truckDTO);}
    public void addLicense(LicenseDTO licenseDTO){if(!licenses.contains(licenseDTO)) licenses.add(licenseDTO);}
    public void addTransportation(TransportationDTO transportationDTO){if(!trans.contains(transportationDTO)) trans.add(transportationDTO);}
    public void addSite(SiteDTO siteDTO){if(!sites.contains(siteDTO)) sites.add(siteDTO);}
    public void addShippingArea(ShippingAreaDTO shippingAreaDTO){if(!shippingAreas.contains(shippingAreaDTO)) shippingAreas.add(shippingAreaDTO);}

    public void removeDriver(DriverDTO driverDTO){if(drivers.contains(driverDTO)) drivers.remove(driverDTO);}
    public void removeTruck(TruckDTO truckDTO){if(trucks.contains(truckDTO)) trucks.remove(truckDTO);}
    public void removeLicense(LicenseDTO licenseDTO){if(licenses.contains(licenseDTO)) licenses.remove(licenseDTO);}
    public void removeTransportation(TransportationDTO transportationDTO){if(trans.contains(transportationDTO)) trans.remove(transportationDTO);}
    public void removeSite(SiteDTO siteDTO){if(sites.contains(siteDTO)) sites.remove(siteDTO);}
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
        return Objects.equals(db, dataBase.db) && Objects.equals(drivers, dataBase.drivers) && Objects.equals(trucks, dataBase.trucks) && Objects.equals(trans, dataBase.trans) && Objects.equals(licenses, dataBase.licenses) && Objects.equals(sites, dataBase.sites) && Objects.equals(shippingAreas, dataBase.shippingAreas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(db, drivers, trucks, trans, licenses, sites, shippingAreas);
    }

    public SiteDTO getSite(Address address, String phone) {
        for (SiteDTO site:sites) {
            if(site.address==address& site.phone==phone)
                return site;
        }
        return null;
    }
}
