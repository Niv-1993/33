package Presentation;

import Business.ApplicationFacade.DriverRoleController;
import Business.ApplicationFacade.ManagerRoleController;
import Business.ApplicationFacade.RegularRoleController;
import Business.ApplicationFacade.iControllers.iDriverRoleController;
import Business.ApplicationFacade.iControllers.iManagerRoleController;
import Business.ApplicationFacade.iControllers.iRegularRoleController;
import Business.StockBusiness.Fcade.StorageService;
import Business.SupplierBusiness.facade.SupplierService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class Controllers {
    private final iRegularRoleController rc;
    private final iManagerRoleController mc;
    private final iDriverRoleController dc;
    private final TransportationController tc;
    private final SupplierService sc;
    private final StorageService st;
    private int currBID;

    public Controllers() {
        rc = new RegularRoleController();
        mc = new ManagerRoleController(rc.getUtils());
        dc = new DriverRoleController(mc);
        tc = new TransportationController(mc);
        sc = new SupplierService();
        st = new StorageService(); //TODO: check if this is the constructor to call
        currBID = -1;
    }

    public iManagerRoleController getMc() {
        return mc;
    }

    public iRegularRoleController getRc() {
        return rc;
    }

    public iDriverRoleController getDc() {
        return dc;
    }

    public TransportationController getTc() {
        return tc;
    }

    public SupplierService getSc() {
        return sc;
    }

    public StorageService getSt() {
        return st;
    }

    public void init() {
        initializeEmpsModule();
        initializeTransportationModule();
        initializeStorage();
    }
    void initializeStorage(){
        StorageService s=new StorageService();
        s.addStore(1);
        s.addStore(2);
        s.addStore(3);
    }

    private void initializeTransportationModule() {


        tc.addTruck(1,3500,"Subaro",3000,3500);
        tc.addTruck(2,4500,"Volvo",4000,5000);
        tc.addTruck(3,11000,"Mercedese",7000,12000);
        tc.addTruck(4,13500,"Dodge",10000,15000);


        int[] bankDetails = {123, 456, 789};
        int[] terms = {1000, 5, 10};
        dc.addNewDriver(100, "Driver1", bankDetails, 40000,LocalDate.now(),terms,13000);
        dc.addNewDriver(101, "Driver2", bankDetails, 40000,LocalDate.now(),terms,13000);
    }


    private void initializeEmpsModule(){
        int[] bankDetails = {123, 456, 789};
        int[] terms = {1000, 5, 10};
        rc.createBranch(1, "PersonnelManager", bankDetails, 150000, terms,"sivan","Tel Aviv",12,2,"South","rom","507350111");
        rc.createBranch(2, "PersonnelManager", bankDetails, 120000, terms,"Zalman","Haifa",12,2,"Center","dor","512156465");
        rc.createBranch(3, "PersonnelManager", bankDetails, 100000, terms,"Alenbi","Beer-Sheva",12,2,"North","bar","507350111");
        rc.EnterBranch(1);
        rc.Login(1);
        mc.addEmployee(6, "StoreKeeperA", bankDetails, 10000, "StoreKeeper", LocalDate.now(), terms);
        mc.addEmployee(7, "CashierA", bankDetails, 10000, "Cashier", LocalDate.now(), terms);
        mc.addEmployee(8, "CashierB", bankDetails, 10000, "Cashier", LocalDate.now(), terms);
        mc.addEmployee(9, "StoreKeeperB", bankDetails, 10000, "StoreKeeper", LocalDate.now(), terms);
        mc.addEmployee(10, "CashierC", bankDetails, 10000, "Cashier", LocalDate.now(), terms);
        mc.addEmployee(11, "ShiftManagerA", bankDetails, 40000, "ShiftManager", LocalDate.now(), terms);
        mc.addEmployee(12, "ShiftManagerB", bankDetails, 40000, "ShiftManager", LocalDate.now(), terms);
        mc.addEmployee(200, "BranchManagerA", bankDetails, 40000, "BranchManager", LocalDate.now(), terms);
        Map<String, Integer> morning = new HashMap<>();
        morning.put("Driver", 0);
        morning.put("Cashier", 1);
        morning.put("Sorter", 1);
        morning.put("ShiftManager", 1);
        morning.put("StoreKeeper", 0);
        Map<String, Integer> night = new HashMap<>();
        night.put("Cashier", 1);
        night.put("ShiftManager", 1);
        night.put("Driver", 0);
        night.put("Sorter", 1);
        night.put("StoreKeeper", 0);
        SortedMap<String, Map<String, Integer>> defaultRolesAmount = new TreeMap<>();
        defaultRolesAmount.put("Night", night);
        defaultRolesAmount.put("Morning", morning);
        mc.defaultShifts(defaultRolesAmount);
        rc.Logout();

        rc.EnterBranch(2);
        rc.Login(2);
        mc.addEmployee(13, "StoreKeeperA", bankDetails, 10000, "StoreKeeper", LocalDate.now(), terms);
        mc.addEmployee(14, "CashierA", bankDetails, 10000, "Cashier", LocalDate.now(), terms);
        mc.addEmployee(15, "CashierB", bankDetails, 10000, "Cashier", LocalDate.now(), terms);
        mc.addEmployee(16, "StoreKeeperB", bankDetails, 10000, "StoreKeeper", LocalDate.now(), terms);
        mc.addEmployee(17, "CashierC", bankDetails, 10000, "Cashier", LocalDate.now(), terms);
        mc.addEmployee(18, "ShiftManagerA", bankDetails, 40000, "ShiftManager", LocalDate.now(), terms);
        mc.addEmployee(19, "ShiftManagerB", bankDetails, 40000, "ShiftManager", LocalDate.now(), terms);
        mc.addEmployee(201, "BranchManagerB", bankDetails, 40000, "BranchManager", LocalDate.now(), terms);
        mc.defaultShifts(defaultRolesAmount);
        rc.Logout();

        rc.EnterBranch(3);
        rc.Login(3);
        mc.addEmployee(20, "StoreKeeperA", bankDetails, 10000, "StoreKeeper", LocalDate.now(), terms);
        mc.addEmployee(21, "CashierA", bankDetails, 10000, "Cashier", LocalDate.now(), terms);
        mc.addEmployee(22, "CashierB", bankDetails, 10000, "Cashier", LocalDate.now(), terms);
        mc.addEmployee(23, "StoreKeeperB", bankDetails, 10000, "StoreKeeper", LocalDate.now(), terms);
        mc.addEmployee(24, "CashierC", bankDetails, 10000, "Cashier", LocalDate.now(), terms);
        mc.addEmployee(25, "ShiftManagerA", bankDetails, 40000, "ShiftManager", LocalDate.now(), terms);
        mc.addEmployee(26, "ShiftManagerB", bankDetails, 40000, "ShiftManager", LocalDate.now(), terms);
        mc.addEmployee(202, "BranchManagerC", bankDetails, 40000, "BranchManager", LocalDate.now(), terms);
        mc.defaultShifts(defaultRolesAmount);
        rc.Logout();


        rc.EnterBranch(1);
        rc.Login(6);
        rc.addConstConstraint(DayOfWeek.SUNDAY, "Night", "tired");
        rc.Logout();
        rc.Login(1);
        rc.EnterBranch(1);
        mc.createWeekShifts();
        rc.Logout();

    }

    public int getCurrBID() {
        return currBID;
    }

    public void setCurrBID(int currBID) {
        this.currBID = currBID;
    }


}
