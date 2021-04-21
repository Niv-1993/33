package Presentation;

import Business.ApplicationFacade.*;
import Presentation.Menu.ManagerMenu;
import Presentation.Menu.Menu;
import Presentation.Menu.RegularMenu;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

public class CLIPresentation {
    private final Scanner input;
    private final iRegularRoleController rc;
    private final iManagerRoleController mc;
    private boolean isManager = false;

    public CLIPresentation() {
        rc = new RegularRoleController();
        mc = new ManagerRoleController(rc.getUtils());
        input = new Scanner(System.in);
    }

    public void start() {
        System.out.println("\n***********************************************");
        System.out.println("*** Welcome to Super Li Employee management ***");
        System.out.println("***********************************************\n");
        System.out.println("Will you like to initialize an existing program? Y/N: ");
        if (read().equalsIgnoreCase("y")) init();
        System.out.println("\n***** If you want to return to previous menu while being in a menu, press any key that is not an option and after press 1 *****\n");
        while (true) {
            System.out.println("Current available branches are: "
                    + ((rc.getBranches().isError()) ? "None.\n" : rc.getBranches().getData().toString()) +
                    "\nChoose an option:\n" +
                    "1) Enter a branch\n2) Create a new branch\n3) exit program");
            boolean success = false;
            while (!success) {
                String chosenOp = read();
                switch (chosenOp) {
                    case "3":
                        System.exit(0);
                    case "1":
                        enterBranch();
                        success = true;
                        break;
                    case "2":
                        createBranch();
                        success = true;
                        break;
                    default:
                        System.out.println("wrong input, please choose again. \n");
                        break;
                }
            }
        }
    }

    private void createBranch() {
        String code, name;
        int ID, AC, BB, BID, salary, fund, DO, SD;
        System.out.println("In order to create a new branch, enter management code and the personnel manager's details for this branch\n");
        while (true) {
            System.out.println("Please enter code: ");
            code = read();
            System.out.print("ID: ");
            ID = enterInt(read());
            System.out.print("name: ");
            name = read();
            System.out.print("bank account number: ");
            AC = enterInt(read());
            System.out.print("bank branch number: ");
            BB = enterInt(read());
            System.out.print("bank ID: ");
            BID = enterInt(read());
            System.out.print("salary: ");
            salary = enterInt(read());
            System.out.print("education fund: ");
            fund = enterInt(read());
            System.out.print("days-off: ");
            DO = enterInt(read());
            System.out.print("sick-days: ");
            SD = enterInt(read());
            System.out.println();
            if (showError(rc.createBranch(code, ID, name, new int[]{AC, BB, BID}, salary, new int[]{fund, DO, SD}))) {
                if (goBack()) return;
            } else break;
        }
    }

    private boolean showError(Response response) {
        if (response.isError()) {
            System.out.println("ERROR: " + response.getError());
            return true;
        }else System.out.println("Success.\n");
        return false;
    }

    private int enterInt(String s) {
        while (true) {
            try {
                return Integer.parseInt(s);
            } catch (Exception e) {
                System.out.println("input is not a number, please insert a number");
                s = read();
            }
        }
    }


    private void enterBranch() {
        while (true) {
            System.out.print("Branch Number to enter: ");
            int branchNum = enterInt(read());
            if (showError(rc.loadData(branchNum))) {
                if (goBack()) return;
                continue;
            }
            if (!rc.hasDefaultShifts().getData()) {
                AddDefaultWeekShifts();
            }
            break;
        }
        login();
    }

    private void AddDefaultWeekShifts() {
        Map<String, Map<String, Integer>> defaults = new HashMap<>();
        List<String> shiftTypes = rc.getShiftTypes().getData();
        while (true) {
            System.out.println("\nNo default shifts were set, please set default shifts for this branch.");
            for (String shiftType : shiftTypes) {
                System.out.println("For shift type: " + shiftType + " enter default amounts for each role.");
                Map<String, Integer> roleAmount = chooseRolesAmount();
                defaults.put(shiftType, roleAmount);
            }
            if (showError(mc.defaultShifts(defaults))) {
                if (goBack()) return;
            } else break;
        }
    }

    private void login() {
        int EID;
        String role;
        while (true) {
            System.out.println("\n\n************* Login **************");
            System.out.println("Please enter your ID and role");
            System.out.print("ID: ");
            EID = enterInt(read());
            role = chooseRole();
            if (showError(rc.Login(EID, role)))
                if (goBack()) return;
                else continue;
            break;
        }
        isManager = role.equals("PersonnelManager");
        allFunctionsMenu();
    }



    private boolean goBack() {
        System.out.println("\n***[If you wish you go back to previous menu enter 1, else 0]***");
        return read().equals("1");
    }

    private void allFunctionsMenu() {
            if(!isManager){
                Menu reg = new RegularMenu(rc,input);
                reg.show();
            }else {
                Menu man = new ManagerMenu(rc,mc,input);
                man.show();
            }
    }


    private String chooseRole() {
        System.out.println("\nChoose a role");
        List<String> roles = rc.getRoleTypes().getData();
        int counter = 1;
        for (String r : roles) {
            System.out.println(counter++ + ") " + r);
        }
        int s;
        while (true) {
            s = enterInt(read());
            if (s < 1 || s > roles.size()) {
                System.out.println("Selected option is not in menu, please try again");
            } else break;
        }
        return roles.get(s - 1);
    }
    private Map<String, Integer> chooseRolesAmount() {
        System.out.println("Insert the amount of each role");
        Map<String, Integer> rolesAmount = new HashMap<>();
        List<String> roleTypes = rc.getRoleTypes().getData();
        for (String role : roleTypes) {
            if (role.equals("PersonnelManager") || role.equals("BranchManager")) continue;
            System.out.print(role + ": ");
            int amount = enterInt(read());
            rolesAmount.put(role, amount);
        }
        return rolesAmount;
    }

    private String read() {
        return input.nextLine();
    }


    private void init() {
        int[] bankDetails = {123, 456, 789};
        int[] terms = {1000, 5, 10};
        rc.createBranch("00000", 1, "PersonnelManager", bankDetails, 150000, terms);
        rc.Login(1, "PersonnelManager");
        mc.addEmployee(2, "DriverA", bankDetails, 10000, "Driver", LocalDate.now(), terms);
        mc.addEmployee(3, "CashierA", bankDetails, 10000, "Cashier", LocalDate.now(), terms);
        mc.addEmployee(4, "CashierB", bankDetails, 10000, "Cashier", LocalDate.now(), terms);
        mc.addEmployee(5, "SorterA", bankDetails, 10000, "Sorter", LocalDate.now(), terms);
        mc.addEmployee(6, "SorterB", bankDetails, 10000, "Sorter", LocalDate.now(), terms);
        mc.addEmployee(7, "ShiftManagerA", bankDetails, 40000, "ShiftManager", LocalDate.now(), terms);
        mc.addEmployee(8, "ShiftManagerB", bankDetails, 40000, "ShiftManager", LocalDate.now(), terms);
        Map<String, Integer> morning = new HashMap<>();
        morning.put("Driver", 1);
        morning.put("Cashier", 1);
        morning.put("Sorter", 2);
        morning.put("ShiftManager", 1);
        morning.put("StoreKeeper",0);
        Map<String, Integer> night = new HashMap<>();
        night.put("Cashier", 1);
        night.put("ShiftManager", 1);
        night.put("Driver", 0);
        night.put("Sorter", 0);
        night.put("StoreKeeper",0);
        SortedMap<String, Map<String, Integer>> defaultRolesAmount = new TreeMap<>();
        defaultRolesAmount.put("Night", night);
        defaultRolesAmount.put("Morning", morning);
        mc.defaultShifts(defaultRolesAmount);
        rc.Logout();
        rc.Login(2, "Driver");
        rc.addConstConstraint(DayOfWeek.SUNDAY, "Night", "tired");
        rc.Logout();
        rc.Login(1, "PersonnelManager");
        mc.createWeekShifts();
        rc.Logout();
    }

}
