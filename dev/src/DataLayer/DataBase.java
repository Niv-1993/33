package DataLayer;

import BussinessLayer.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import enums.Area;
import enums.Pair;

public class DataBase {
    private static DataBase db=null;
    private List<DriverDTO> drivers;
    private List<TruckDTO> trucks;
    private List<TransportationDTO> trans;
    private List<LicenseDTO> licenses;
    private List<BranchDTO> branches;
    private List<SupplierDTO> suppliers;
    private List<ShippingAreaDTO> shippingAreas;
    private List<AddressDTO> addresses;
    public List <ItemDTO> items;

    public static DataBase init() {
        if(db==null){
            db=new DataBase();
            db.initFakeData();
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
    public static DataBase initForTests(){
        if(db==null){
            db=new DataBase();
        }
        return db;
    }
    //TODO:create initializaion
    private void initFakeData() {
        licenses.add(new LicenseDTO(1000));
        licenses.add(new LicenseDTO(2000));
        licenses.add(new LicenseDTO(3000));
        licenses.add(new LicenseDTO(4000));
        licenses.add(new LicenseDTO(5000));
        drivers.add(new DriverDTO(1,"Meir malka",licenses.get(3)));
        drivers.add(new DriverDTO(2,"Ami vanunu",licenses.get(2)));
        drivers.add(new DriverDTO(3,"Roi rozenberg",licenses.get(4)));
        drivers.add(new DriverDTO(4,"Avi rozen",licenses.get(0)));
        trucks.add(new TruckDTO(12345678,licenses.get(4),10000,1500,"Jumpy"));
        trucks.add(new TruckDTO(25954557,licenses.get(2),10000,1000,"Kangoo"));
        trucks.add(new TruckDTO(68465185,licenses.get(4),10000,1200,"Sprinter"));
        trucks.add(new TruckDTO(98775656,licenses.get(2),10000,1000,"Rapid"));
        shippingAreas.add(new ShippingAreaDTO(Area.Sout));
        shippingAreas.add(new ShippingAreaDTO(Area.North));
        shippingAreas.add(new ShippingAreaDTO(Area.Central));
        items.add(new ItemDTO(9876,"Doritos"));
        items.add(new ItemDTO(8569,"Tapuchips"));
        items.add(new ItemDTO(7458,"Bamba"));
        items.add(new ItemDTO(3685,"Nachos"));
        items.add(new ItemDTO(1548,"Coca Cola"));
        items.add(new ItemDTO(8759,"Water"));
        items.add(new ItemDTO(7895,"Salt"));
        items.add(new ItemDTO(6258,"Sugar"));
        items.add(new ItemDTO(5489,"Tea"));
        items.add(new ItemDTO(3254,"Eggs L x12"));
        items.add(new ItemDTO(9875,"Milk"));
        addresses.add(new AddressDTO(9,"moshe rahim","Holon"));
        addresses.add(new AddressDTO(19,"Hanna senesh","kiryat gat"));
        addresses.add(new AddressDTO(90,"HaAvot","Ramat Gan"));
        addresses.add(new AddressDTO(43,"Emek hasofrim ","Netivot"));
        addresses.add(new AddressDTO(15,"shualey shimshon","Ofaquim"));
        addresses.add(new AddressDTO(24,"Jabutinski","Beer Sheva"));
        addresses.add(new AddressDTO(7,"Ha'Orgim","Beer Sheva"));
        suppliers.add(new SupplierDTO("0527745862","Amit Nahum",9845,addresses.get(2),shippingAreas.get(2)));
        suppliers.add(new SupplierDTO("0548569574","Omer Shalom",8542,addresses.get(0),shippingAreas.get(2)));
        suppliers.add(new SupplierDTO("0506328574","Ofer Neeman",2648,addresses.get(3),shippingAreas.get(0)));
        branches.add(new BranchDTO("0506895718","Yogev Halom",1,addresses.get(1),shippingAreas.get(0)));
        branches.add(new BranchDTO("0528759462","Ami Barlev",2,addresses.get(5),shippingAreas.get(0)));
        HashMap<BranchDTO, List<Pair<ItemDTO,Integer>>> lis=new HashMap<>();
        List<Pair<ItemDTO,Integer>> it=new LinkedList<>();
        it.add(new Pair<>(items.get(4),15));
        lis.put(branches.get(1),it);
        HashMap<SupplierDTO, List<Pair<ItemDTO,Integer>>> lis2=new HashMap<>();
        List<Pair<ItemDTO,Integer>> it2=new LinkedList<>();
        it2.add(new Pair<>(items.get(4),15));
        lis2.put(suppliers.get(0),it);
        trans.add(new TransportationDTO(123, LocalDate.now(), LocalTime.now(),drivers.get(1),trucks.get(1),1500,lis, lis2));
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
    public List<SupplierDTO> getSuppliers(){return suppliers;}
    public void setBranches(List<BranchDTO> branches) { this.branches = branches; }

    public void setSuppliers(List<SupplierDTO> suppliers) { this.suppliers = suppliers; }


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
    public void addSupplier(SupplierDTO branchDTO){if(!suppliers.contains(branchDTO)) suppliers.add(branchDTO);}
    public void addShippingArea(ShippingAreaDTO shippingAreaDTO){if(!shippingAreas.contains(shippingAreaDTO)) shippingAreas.add(shippingAreaDTO);}
    public void addAddress(AddressDTO address){if(!addresses.contains(address)) addresses.add(address);}

    public void removeAddress(AddressDTO addressDTO){addresses.remove(addressDTO);}
    public void removeDriver(int id){drivers.remove(getDriver(id));}
    public void removeDriver(DriverDTO driverDTO){if(drivers.contains(driverDTO)) drivers.remove(driverDTO);}
    public void removeTruck(TruckDTO truckDTO){if(trucks.contains(truckDTO)) trucks.remove(truckDTO);}
    public void removeLicense(LicenseDTO licenseDTO){if(licenses.contains(licenseDTO)) licenses.remove(licenseDTO);}
    public void removeTransportation(TransportationDTO transportationDTO){if(trans.contains(transportationDTO)) trans.remove(transportationDTO);}
    public void removeBranch(BranchDTO branchDTO){if(branches.contains(branchDTO)) branches.remove(branchDTO);}
    public void removeSupplier(SupplierDTO branchDTO){if(suppliers.contains(branchDTO)) suppliers.remove(branchDTO);}
    public void removeShippingArea(ShippingAreaDTO shippingAreaDTO){if(shippingAreas.contains(shippingAreaDTO)) shippingAreas.remove(shippingAreaDTO);}

    public TruckDTO getTruck(long id){
        for (TruckDTO truck:trucks) {
            if(id==truck.getId())
                return truck;
        }
        return null;
    }
    public DriverDTO getDriver(long id){
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
    public SupplierDTO getSupplier(String phone) {
        for (SupplierDTO site:suppliers) {
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
