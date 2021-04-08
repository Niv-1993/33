import BussinessLayer.Branch;
import BussinessLayer.ShippingArea;
import BussinessLayer.Supplier;
import BussinessLayer.Transportation;
import DataLayer.*;
import ServiceLayer.Controller;
import ServiceLayer.Objects.TransportationServiceDTO;
import enums.Area;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Nested;

class ControllerTest {
    private DataBase db;
    private Controller controller;


    @BeforeEach
    void setUp() {
        db = DataBase.initForTests();
        LicenseDTO l1 = new LicenseDTO(1000);
        LicenseDTO l2 = new LicenseDTO(2000);
        LicenseDTO l3 = new LicenseDTO(3000);
        LicenseDTO l4 = new LicenseDTO(4000);
        LicenseDTO l5 = new LicenseDTO(5000);

        db.addLicense(l1); db.addLicense(l2); db.addLicense(l3); db.addLicense(l4); db.addLicense(l5);

        DriverDTO d1 = new DriverDTO(1,"Meir malka",l1);
        DriverDTO d2 = new DriverDTO(2,"Ami vanunu",l2);
        DriverDTO d3 = new DriverDTO(3,"Roi rozenberg",l3);
        DriverDTO d4 = new DriverDTO(4,"Avi rozen",l4);

        TruckDTO t1 = new TruckDTO(12345678,l1,10000,1500,"Jumpy");
        TruckDTO t2 = new TruckDTO(25954557,l2,10000,1000,"Kangoo");
        TruckDTO t3 = new TruckDTO(68465185,l3,10000,1200,"Sprinter");
        TruckDTO t4 = new TruckDTO(98775656,l5,10000,1000,"Rapid");

        db.addTruck(t1); db.addTruck(t2); db.addTruck(t3); db.addTruck(t4);

        ShippingAreaDTO south = new ShippingAreaDTO(Area.Sout);
        ShippingAreaDTO north = new ShippingAreaDTO(Area.North);
        ShippingAreaDTO central = new ShippingAreaDTO(Area.Central);

        db.addShippingArea(south);
        db.addShippingArea(north);
        db.addShippingArea(central);
        ItemDTO doritos = (new ItemDTO(9876,"Doritos"));
        ItemDTO tapuchips = (new ItemDTO(8569,"Tapuchips"));
        ItemDTO bamba = (new ItemDTO(7458,"Bamba"));
        ItemDTO nachos = (new ItemDTO(3685,"Nachos"));
        ItemDTO cocaCola = (new ItemDTO(1548,"Coca Cola"));
        ItemDTO water = (new ItemDTO(8759,"Water"));
        ItemDTO salt = (new ItemDTO(7895,"Salt"));
        ItemDTO sugar = (new ItemDTO(6258,"Sugar"));
        ItemDTO tea = (new ItemDTO(5489,"Tea"));
        ItemDTO eggsL = (new ItemDTO(3254,"Eggs L x12"));
        ItemDTO milk = (new ItemDTO(9875,"Milk"));

        db.addItem(doritos); db.addItem(tapuchips); db.addItem(bamba); db.addItem(nachos); db.addItem(cocaCola); db.addItem(water);db.addItem(salt);
        db.addItem(sugar); db.addItem(tea); db.addItem(eggsL); db.addItem(milk);

        AddressDTO ad1 = (new AddressDTO(9,"moshe rahim","Holon"));
        AddressDTO ad2 = (new AddressDTO(19,"Hanna senesh","kiryat gat"));
        AddressDTO ad3 = (new AddressDTO(90,"HaAvot","Ramat Gan"));
        AddressDTO ad4 = (new AddressDTO(43,"Emek hasofrim ","Netivot"));
        AddressDTO ad5 = (new AddressDTO(15,"shualey shimshon","Ofaquim"));
        AddressDTO ad6 = (new AddressDTO(24,"Jabutinski","Beer Sheva"));
        AddressDTO ad7 = (new AddressDTO(7,"Ha'Orgim","Beer Sheva"));
        db.addAddress(ad1); db.addAddress(ad2); db.addAddress(ad3); db.addAddress(ad4); db.addAddress(ad5); db.addAddress(ad6); db.addAddress(ad7);

        SupplierDTO s1 = (new SupplierDTO("0527745862","Amit Nahum",9845,ad1,central));
        SupplierDTO s2 = (new SupplierDTO("0548569574","Omer Shalom",8542,ad2,north));
        SupplierDTO s3 = (new SupplierDTO("0506328574","Ofer Neeman",2648,ad3,south));
        BranchDTO b1 = (new BranchDTO("0506895718","Yogev Halom",1,ad4,central));
        BranchDTO b2 = (new BranchDTO("0528759462","Ami Barlev",2,ad5,central));

        db.addSupplier(s1); db.addSupplier(s2); db.addSupplier(s3);
        db.addBranch(b1); db.addBranch(b2);

        controller.loadDataT();
    }
    @Nested
    @DisplayName("When Creating a new Transportation")
    class TransportationExists{
        private TransportationServiceDTO t = new TransportationServiceDTO();

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void initial() {
    }

    @Test
    @DisplayName("Should set a Driver")
    void setDriverOnTransportation() {

        controller.setDriverOnTransportation(t);
    }

    @Test
    void setTruckOnTransportation() {
    }

    @Test
    void setTransportationWeight() {
    }

    @Test
    void setTransportation() {
    }

    @Test
    void setTransportationDate() {
    }

    @Test
    void setTransportationLeavingTime() {
    }

    @Test
    void setSuppliersToTransportation() {
    }

    @Test
    void setDeliveryItemsToTransportation() {
    }

    @Test
    void setTransportationArea() {
    }

    @Test
    void getAllDrivers() {
    }

    @Test
    void getAllItems() {
    }

    @Test
    void getAllSuppliers() {
    }

    @Test
    void getAllTransportations() {
    }

    @Test
    void getAllTrucks() {
    }

    @Test
    void getAllBranches() {
    }

    @Test
    void getDriver() {
    }

    @Test
    void getItem() {
    }

    @Test
    void getBranch() {
    }

    @Test
    void getSupplier() {
    }

    @Test
    void getTruck() {
    }

    @Test
    void createNewTransportation() {
    }
}