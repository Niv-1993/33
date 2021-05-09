package Presentation;

import Business.ApplicationFacade.*;
import Business.ApplicationFacade.iControllers.iDriverRoleController;
import Business.ApplicationFacade.iControllers.iManagerRoleController;
import Business.ApplicationFacade.iControllers.iRegularRoleController;

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

    public Controllers() {
        rc = new RegularRoleController();
        mc = new ManagerRoleController(rc.getUtils());
        dc = new DriverRoleController(mc);
        tc = new TransportationController();
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

    public void init() {
        initializeEmpsModule();
        initializeTransportationModule();
    }

    private void initializeTransportationModule() {

       tc.addItem (1,"SNICKERS");
        tc.addItem (2,"Lays");
        tc.addItem(3,"Bar Damri");
        tc.addItem(4,"Sniders");
        tc.addItem (5,"Shosh");
        tc.addItem (6,"Shtihim");
        tc.addItem (7,"Gat");
        tc.addItem (8,"Joint");
        tc.addItem (9,"LCD");
        tc.addItem (10,"Karton");
        tc.addItem (11,"Hashish");
        tc.addItem(12,"Pailu");
        tc.addItem(13,"Nagets");
        tc.addItem (14,"MamaOf");
        tc.addItem(15,"SNICKERS");
        tc.addItem(16,"Ramen");

        tc.addSupplier(1,"Yagon","beerSHeva",5,1,"South","Bar","052443755");
        tc.addSupplier(2,"Harov","beerSHeva",90,2,"South","Shimi","052443333");
        tc.addSupplier(3,"Tot","Haifa",7,1,"North","Ravid","052436518");
        tc.addSupplier(4,"ShederotRabin","TelAviv",5,1,"Center","Moshe","052443755");
        tc.addSupplier(5,"BenYehuda","beerSHeva",9,6,"Center","Yaki","052555755");
        tc.addSupplier(6,"Kineret","Haifa",14,1,"South","Mordi","052477755");

        tc.addSupplierItems(1,1);
        tc.addSupplierItems(2,1);
        tc.addSupplierItems(3,1);
        tc.addSupplierItems(4,1);
        tc.addSupplierItems(5,1);
        tc.addSupplierItems(6,2);
        tc.addSupplierItems(7,2);
        tc.addSupplierItems(8,2);
        tc.addSupplierItems(9,2);
        tc.addSupplierItems(10,3);
        tc.addSupplierItems(11,3);
        tc.addSupplierItems(12,3);
        tc.addSupplierItems(13,3);
        tc.addSupplierItems(14,4);
        tc.addSupplierItems(15,4);
        tc.addSupplierItems(16,4);
        tc.addSupplierItems (7,5);
        tc.addSupplierItems (8,5);
        tc.addSupplierItems (9,5);
        tc.addSupplierItems(10,5);
        tc.addSupplierItems (11,5);
        tc.addSupplierItems (12,5);
        tc.addSupplierItems (1,6);
        tc.addSupplierItems(2,6);
        tc.addSupplierItems (3,6);
        tc.addSupplierItems (4,6);
        tc.addSupplierItems (10,6);
        tc.addSupplierItems(11,6);
        tc.addSupplierItems (14,6);
        tc.addSupplierItems (16,6);

        tc.addBranch(1,"Yona Fisher","Haifa",14,1,"North","Mordi","052477755");
        tc.addBranch(2,"Menhimer","Karkur",3,1,"North","Yakov","052476655");
        tc.addBranch(3,"Yam","Natanya",2,1,"Center","Shaul","050377755");
        tc.addBranch(4,"Nahshon","Moran",1,1,"Center","Mimi","050477755");
        tc.addBranch(5,"Kineret","Omer",7,3,"South","Rash","052477745");
        tc.addBranch(6,"Kineret","Netivot",12,1,"South","Mordi","05255875");

        tc.addTruck(1,3500,"Subaro",3000,3500);
        tc.addTruck(2,4500,"Volvo",4000,5000);
        tc.addTruck(3,11000,"Mercedese",7000,12000);
        tc.addTruck(4,13500,"Dodge",10000,15000);

        tc.addTransportation(1,"Center","2021-12-01","02:20",4250,4,1);

        tc.addSuppliersItemsTrans(5,1,7,10);
        tc.addSuppliersItemsTrans(5,1,8,5);
        tc.addSuppliersItemsTrans(5,1,9,3);
        tc.addSuppliersItemsTrans(5,1,10,7);
        tc.addSuppliersItemsTrans(5,1,11,14);

        tc.addBranchesItemsTrans(2,1,9,2);
        tc.addBranchesItemsTrans(2,1,11,10);

    }


    private void initializeEmpsModule(){
        int[] bankDetails = {123, 456, 789};
        int[] terms = {1000, 5, 10};
        rc.createBranch("00000", 1, "PersonnelManager", bankDetails, 150000, terms);
        rc.EnterBranch(1);
        rc.Login(1);
        mc.addEmployee(2, "StoreKeeperA", bankDetails, 10000, "StoreKeeper", LocalDate.now(), terms);
        mc.addEmployee(3, "CashierA", bankDetails, 10000, "Cashier", LocalDate.now(), terms);
        mc.addEmployee(4, "CashierB", bankDetails, 10000, "Cashier", LocalDate.now(), terms);
        mc.addEmployee(5, "StoreKeeperB", bankDetails, 10000, "StoreKeeper", LocalDate.now(), terms);
        mc.addEmployee(6, "CashierC", bankDetails, 10000, "Cashier", LocalDate.now(), terms);
        mc.addEmployee(7, "ShiftManagerA", bankDetails, 40000, "ShiftManager", LocalDate.now(), terms);
        mc.addEmployee(8, "ShiftManagerB", bankDetails, 40000, "ShiftManager", LocalDate.now(), terms);
        Map<String, Integer> morning = new HashMap<>();
        morning.put("Driver", 0);
        morning.put("Cashier", 1);
        morning.put("Sorter", 0);
        morning.put("ShiftManager", 1);
        morning.put("StoreKeeper", 1);
        Map<String, Integer> night = new HashMap<>();
        night.put("Cashier", 1);
        night.put("ShiftManager", 1);
        night.put("Driver", 0);
        night.put("Sorter", 0);
        night.put("StoreKeeper", 1);
        SortedMap<String, Map<String, Integer>> defaultRolesAmount = new TreeMap<>();
        defaultRolesAmount.put("Night", night);
        defaultRolesAmount.put("Morning", morning);
        mc.defaultShifts(defaultRolesAmount);
        rc.Logout();
        rc.Login(2);
        rc.addConstConstraint(DayOfWeek.SUNDAY, "Night", "tired");
        rc.Logout();
        rc.Login(1);
        rc.EnterBranch(1);
        mc.createWeekShifts();
        rc.Logout();
    }
}
