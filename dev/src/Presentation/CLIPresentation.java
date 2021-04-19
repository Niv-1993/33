package Presentation;

import Business.ApplicationFacade.*;
import Business.ApplicationFacade.outObjects.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

public class CLIPresentation {
    private final Scanner input;
    private final iManagerRoleController service;
    private boolean isManager = false;

    public CLIPresentation() {
        service = new ManagerRoleController();
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
                    + ((service.getBranches().isError()) ? "None.\n" : service.getBranches().getData().toString()) +
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
            if (showError(service.createBranch(code, ID, name, new int[]{AC, BB, BID}, salary, new int[]{fund, DO, SD}))) {
                if (goBack()) return;
            } else break;
        }
    }

    private boolean showError(Response response) {
        if (response.isError()) {
            System.out.println("ERROR: " + response.getError());
            return true;
        }
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
            if (showError(service.loadData(branchNum))) {
                if (goBack()) return;
                continue;
            }
            if (!service.hasDefaultShifts().getData()) {
                AddDefaultWeekShifts();
            }
            break;
        }
        login();
    }

    private void AddDefaultWeekShifts() {
        Map<String, Map<String, Integer>> defaults = new HashMap<>();
        List<String> shiftTypes = service.getShiftTypes().getData();
        while (true) {
            System.out.println("\nNo default shifts were set, please set default shifts for this branch.");
            for (String shiftType : shiftTypes) {
                System.out.println("For shift type: " + shiftType + " enter default amounts for each role.");
                Map<String, Integer> roleAmount = chooseRolesAmount();
                defaults.put(shiftType, roleAmount);
            }
            if (showError(service.defaultShifts(defaults))) {
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
            if (showError(service.Login(EID, role)))
                if (goBack()) return;
                else continue;
            break;
        }
        isManager = role.equals("PersonnelManager");
        allFunctionsMenu();
    }

    private String chooseRole() {
        System.out.println("\nChoose a role");
        List<String> roles = service.getRoleTypes().getData();
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

    private boolean goBack() {
        System.out.println("\n***[If you wish you go back to previous menu enter 1, else 0]***");
        return read().equals("1");
    }

    private void printPersonnelOperation() {
        System.out.println("5) Employee operations menu");
        System.out.println("6) Shift operations menu");
    }

    private void allFunctionsMenu() {
        while (true) {
            System.out.println("\n\n************* Functions Menu *************");
            printRegularOperations();
            if (isManager) printPersonnelOperation();
            System.out.println("Choose an option:");
            String option = read();
            if (option.equals("1")) {
                printMyDetails();
            } else if (option.equals("2")) {
                printConstraintsOperations();
            } else if (option.equals("3")) {
                printMyShifts();
                System.out.println();
                printMyConstraints();
                System.out.println();
            } else if (option.equals("4")) {
                if (!showError(service.Logout())) return;
            } else if (option.equals("5") && isManager) {
                printEmployeeOperations();
            } else if (option.equals("6") && isManager) {
                printShiftOperations();
            } else {
                System.out.println("Invalid input,please choose a number again");
                if (goBack()) return;
            }
        }
    }


    private void printMyShifts() {
        System.out.println("Your Shifts: ");
        ResponseData<List<Shift>> shifts = service.getMyShifts();
        if (!showError(shifts)) {
            if (shifts.getData().isEmpty()) System.out.println("You don't have any shifts");
            else {
                for (Shift s : shifts.getData())
                    System.out.println(s);
            }
        }
    }


    private void printRegularOperations() {
        System.out.println("1) My details");
        System.out.println("2) My constraints operations");
        System.out.println("3) My shifts and constraints");
        System.out.println("4) Logout");
    }


    private void printMyDetails() {
        ResponseData<Employee> res = service.getEmployeeDetails();
        if (!showError(res)) {
            System.out.println(res.getData().toString());
        }
    }


    private void printConstraintsOperations() {
        while (true) {
            System.out.println("\n\n************* Constraint Menu *************\n");
            System.out.println("1) Add const constraint");
            System.out.println("2) Add temporal constraint");
            System.out.println("3) Remove constraint");
            System.out.println("4) Update reason");
            System.out.println("5) Update shift type");
            System.out.println("Choose option: ");
            String option = read();
            switch (option) {
                case "1":
                    addConstConstraint();
                    break;
                case "2":
                    addTempConstraint();
                    break;
                case "3":
                    removeConstraint();
                    break;
                case "4":
                    updateReason();
                    break;
                case "5":
                    updateShiftType();
                    break;
                default:
                    System.out.println("Invalid input,please choose a number again");
                    if (goBack()) return;
                    break;
            }
        }
    }

    private void updateShiftType() {
        while (true) {
            if (!printMyConstraints()) break;
            System.out.println("Choose a constraint ID to update shift type");
            int CID = enterInt(read());
            if (showError(service.updateShiftTypeConstraint(CID, chooseShiftType()))) {
                if (goBack()) return;
            } else break;
        }
    }

    private void updateReason() {
        while (true) {
            if (!printMyConstraints()) break;
            System.out.println("Choose a constraint ID to update reason");
            int CID = enterInt(read());
            if (showError(service.updateReasonConstraint(CID, getReason()))) {
                if (goBack()) return;
            } else break;
        }
    }

    private boolean printMyConstraints() {
        System.out.println("Your constraints: ");
        ResponseData<List<Constraint>> constraints = service.getMyConstraints();
        if (!showError(constraints)) {
            if (constraints.getData().isEmpty()) {
                System.out.println("You don't have any constraint");
                return false;
            } else {
                for (Constraint c : constraints.getData())
                    System.out.println(c);
            }
        }
        return true;
    }

    private void removeConstraint() {
        while (true) {
            if (!printMyConstraints()) break;
            System.out.println("Choose a constraint ID to remove");
            int CID = enterInt(read());
            if (showError(service.removeConstraint(CID))) {
                if (goBack()) return;
            } else break;
        }
    }

    private void addTempConstraint() {
        System.out.println("To add a temporal constraint details are requested");
        showError(service.addConstraint(chooseDate(), chooseShiftType(), getReason()));
    }

    private LocalDate chooseDate() {
        System.out.println("Choose date: ");
        while (true) {
            System.out.println("Year: ");
            int year = enterInt(read());
            System.out.println("Month: ");
            int month = enterInt(read());
            System.out.println("Day: ");
            int day = enterInt(read());
            try {
                return LocalDate.of(year, month, day);
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage() + "\nEnter valid inputs");
            }
        }
    }

    private void addConstConstraint() {
        System.out.println("To add a const constraint details are requested");
        showError(service.addConstConstraint(chooseDayOfWeek(), chooseShiftType(), getReason()));
    }

    private String getReason() {
        System.out.println("Reason:");
        return read();
    }

    private String chooseShiftType() {
        System.out.println("Choose a shift type");
        List<String> shiftTypes = service.getShiftTypes().getData();
        int counter = 1;
        for (String shiftType : shiftTypes) {
            System.out.println(counter++ + ") " + shiftType);
        }
        int s;
        while (true) {
            s = enterInt(read());
            if (s < 1 || s > shiftTypes.size()) {
                System.out.println("Selected option is not in menu, please try again");
            } else break;
        }
        return shiftTypes.get(s - 1);
    }

    private DayOfWeek chooseDayOfWeek() {
        System.out.println("Choose the day of the shift");
        int counter = 1;
        for (DayOfWeek day : DayOfWeek.values()) {
            System.out.println(counter++ + ") " + day.toString());
        }
        int day;
        while (true) {
            day = enterInt(read());
            if (day < 1 || day > 7) {
                System.out.println("Selected option is not in menu, please try again");
            } else break;
        }
        return DayOfWeek.of(day);
    }


    private void printEmployeeOperations() {
        while (true) {
            System.out.println("\n*********** Employee Menu ***********");
            System.out.println("1) Add new employee");
            System.out.println("2) Fire employee");
            System.out.println("3) Update employee name");
            System.out.println("4) Update employee salary");
            System.out.println("5) Update employee bank details");
            System.out.println("6) Update employee terms of employment");
            System.out.println("7) Add a new role to employee");
            System.out.println("8) Print all employees");
            System.out.println("Choose option: ");
            String option = read();
            switch (option) {
                case "1":
                    addEmployee();
                    break;
                case "2":
                    fireEmployee();
                    break;
                case "3":
                    updateEmp("name");
                    break;
                case "4":
                    updateEmp("salary");
                    break;
                case "5":
                    updateEmp("bank details");
                    break;
                case "6":
                    updateEmp("terms of employment");
                    break;
                case "7":
                    addNewRole();
                    break;
                case "8":
                    printAllEmployees("all");
                    break;
                default:
                    System.out.println("Invalid input,please choose a number again");
                    if (goBack()) return;
                    break;
            }
        }

    }

    private void addNewRole() {
        while (true) {
            printAllEmployees("");
            System.out.print("Choose a employee ID to add a role: ");
            int EID = enterInt(read());
            if (showError(service.addRoleToEmployee(EID, chooseRole()))) {
                if (goBack()) return;
            } else break;
        }
    }

    private void updateEmp(String s) {
        while (true) {
            printAllEmployees("");
            System.out.print("Choose a employee ID to update " + s + ": ");
            int EID = enterInt(read());
            switch (s) {
                case "name":
                    System.out.print("New name: ");
                    String name = read();
                    if (showError(service.updateEmployeeName(EID, name))) {
                        if (goBack()) return;
                    } else return;
                    break;
                case "salary":
                    System.out.print("New salary: ");
                    int salary = enterInt(read());
                    if (showError(service.updateEmployeeSalary(EID, salary))) {
                        if (goBack()) return;
                    } else return;
                    break;
                case "bank details":
                    printBankDetailsOperation(EID);
                    break;
                case "terms of employment":
                    printTermsOfEmpOperation(EID);
                    break;
            }
        }
    }

    private void fireEmployee() {
        while (true) {
            printAllEmployees("");
            System.out.println("Choose a employee ID to fire");
            int EID = enterInt(read());
            if (showError(service.fireEmployee(EID))) {
                if (goBack()) return;
            } else break;
        }
    }

    private void printAllEmployees(String all) {
        System.out.println("All employees of this branch: ");
        ResponseData<List<Employee>> employees = service.getAllEmployees();
        if (!showError(employees)) {
            if (employees.getData().isEmpty()) System.out.println("No employees in this branch");
            else {
                for (Employee e : employees.getData())
                    if (all.equals("all")) {
                        System.out.println(e.toString());
                    } else
                        System.out.println(e.toStringForAllEmployee());
            }
        }
    }

    private void addEmployee() {
        String name, role;
        int ID, AC, BB, BID, salary, fund, DO, SD;
        System.out.println("In order to create a new employee, enter details for this employee\n");
        while (true) {
            System.out.print("ID: ");
            ID = enterInt(read());
            System.out.print("name: ");
            name = read();
            role = chooseRole();
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
            if (showError(service.addEmployee(ID, name, new int[]{AC, BB, BID}, salary, role, LocalDate.now(), new int[]{fund, DO, SD}))) {
                if (goBack()) return;
            } else break;
        }

    }

    private void printBankDetailsOperation(int EID) {
        while (true) {
            System.out.println("1) Update employee account number");
            System.out.println("2) Update employee branch number");
            System.out.println("3) Update employee bank ID\n");
            System.out.println("Choose option: ");
            String option = read();
            int x;
            switch (option) {
                case "1":
                    System.out.print("New account number: ");
                    x = enterInt(read());
                    if (!showError(service.updateEmployeeBANum(EID, x))) return;
                    if (goBack()) return;
                    break;
                case "2":
                    System.out.print("New branch number: ");
                    x = enterInt(read());
                    if (!showError(service.updateEmployeeBABranch(EID, x))) return;
                    if (goBack()) return;
                    break;
                case "3":
                    System.out.print("New bank ID: ");
                    x = enterInt(read());
                    if (!showError(service.updateEmployeeBAID(EID, x))) return;
                    if (goBack()) return;
                    break;
                default:
                    System.out.println("Invalid input,please choose a number again");
                    if (goBack()) return;
                    break;
            }

        }
    }

    private void printTermsOfEmpOperation(int EID) {
        while (true) {
            System.out.println("1) Update employee education fund");
            System.out.println("2) Update employee days-off");
            System.out.println("3) Update employee sick-days\n");
            System.out.println("Choose option: ");
            String option = read();
            int x;
            switch (option) {
                case "1":
                    System.out.print("New education fund: ");
                    x = enterInt(read());
                    if (!showError(service.updateEmployeeEducationFund(EID, x))) return;
                    break;
                case "2":
                    System.out.print("New days-off: ");
                    x = enterInt(read());
                    if (!showError(service.updateEmployeeDaysOff(EID, x))) return;
                    break;
                case "3":
                    System.out.print("New sick-days: ");
                    x = enterInt(read());
                    if (!showError(service.updateEmployeeSickDays(EID, x))) return;
                    break;
                default:
                    System.out.println("Invalid input,please choose a number again");
                    if (goBack()) return;
                    break;
            }

        }

    }

    private void printShiftOperations() {
        while (true) {
            System.out.println("\n*************** Shift Menu ***************");
            System.out.println("1) Add employee to shift");
            System.out.println("2) Remove employee from shift");
            System.out.println("3) Create Shift");
            System.out.println("4) Create shifts for next week");
            System.out.println("5) make placement for shifts of next week");
            System.out.println("6) Print all shifts");
            System.out.println("7) Update the amount of a specific role in a shift");
            System.out.println("Choose option: ");
            String option = read();
            switch (option) {
                case "1":
                    addEmployeeToShift();
                    break;
                case "2":
                    removeEmployeeFromShift();
                    break;
                case "3":
                    createShift();
                    break;
                case "4":
                    List<Shift> s = service.createWeekShifts().getData();
                    if (!s.isEmpty()) {
                        StringBuilder str = new StringBuilder();
                        s.forEach(shift -> {
                            str.append("Shift Date: ").append(shift.date).append("    Type: ").append(shift.shiftType).append("\n");
                        });
                        str.append("*** Do not have Shift Managers ***");
                        System.out.println(str.toString());
                    }else System.out.println("Successfully created shifts for all week\n");
                    break;
                case "5":
                    showError(service.selfMakeWeekShifts());
                    break;
                case "6":
                    printAllShifts("all", LocalDate.now().plusWeeks(2));
                    break;
                case "7":
                    updateAmountRole();
                    break;
                default:
                    System.out.println("Invalid input,please choose a number again");
                    if (goBack()) return;
                    break;
            }
        }


    }

    private void updateAmountRole() {
        while (true) {
            if (!printAllShifts("roles", LocalDate.now().plusWeeks(2))) return;
            System.out.print("Shift ID to update amount: ");
            int SID = enterInt(read());
            String role = chooseRole();
            System.out.print("New amount: ");
            int amount = enterInt(read());
            if (showError(service.updateAmountRole(SID, role, amount))) {
                if (goBack()) return;
            } else break;
        }
    }

    private void createShift() {
        while (true) {
            System.out.println("To create a new shift enter the following details: ");
            LocalDate date = chooseDate();
            String shiftType = chooseShiftType();
            Map<String, Integer> rolesAmount = chooseRolesAmount();
            if (showError(service.createShift(rolesAmount, date, shiftType))) {
                if (goBack()) return;
            } else break;
        }
    }

    private Map<String, Integer> chooseRolesAmount() {
        System.out.println("Insert the amount of each role");
        Map<String, Integer> rolesAmount = new HashMap<>();
        List<String> roleTypes = service.getRoleTypes().getData();
        for (String role : roleTypes) {
            if (role.equals("PersonnelManager") || role.equals("BranchManager")) continue;
            System.out.print(role + ": ");
            int amount = enterInt(read());
            rolesAmount.put(role, amount);
        }
        return rolesAmount;
    }

    private void removeEmployeeFromShift() {
        while (true) {
            if (!printAllShifts("all", LocalDate.now().plusWeeks(2))) return;
            System.out.print("shift ID to remove a employee: ");
            int SID = enterInt(read());
            System.out.print("employee ID: ");
            int EID = enterInt(read());
            if (showError(service.removeEmpFromShift(SID, EID))) {
                if (goBack()) return;
            } else break;
        }
    }

    private boolean printAllShifts(String all, LocalDate until) {
        System.out.println("All shifts of this branch until " + until + " :");
        ResponseData<List<Shift>> shifts = service.getShifts(until);
        if (!showError(shifts)) {
            if (shifts.getData().isEmpty()) {
                System.out.println("No shifts in this branch");
                return false;
            } else {
                ArrayList<Shift> missing = new ArrayList<>();
                ArrayList<Shift> hasSM = new ArrayList<>();
                for (Shift s : shifts.getData()) {
                    if (all.equals("all")) {
                        System.out.println(s.toStringAll());
                        if (s.status.equals("*** Missing ***")) missing.add(s);
                        if(!s.hasShiftManager) hasSM.add(s);
                    } else
                        System.out.println(s.toStringWithoutOptAndEmp());
                }
                if (!missing.isEmpty())
                    printMissing(missing);
                else if (all.equals("all")) System.out.println("\nAll shifts are staffed in all positions");
                if(!hasSM.isEmpty())
                    printMissingShiftManager(hasSM);
            }
        }
        return true;
    }

    private void printMissingShiftManager(ArrayList<Shift> hasSM) {
        System.out.print("*** Shift ID's are missing shift manager: ");
        StringBuilder s = new StringBuilder();
        s.append("[");
        hasSM.forEach(shift -> {
            s.append(shift.SID).append(", ");
        });
        s.deleteCharAt(s.length() - 1);
        s.deleteCharAt(s.length() - 1);
        s.append("] ***");
        System.out.println(s.toString());
    }

    private void printMissing(ArrayList<Shift> missing) {
        System.out.print("*** Shift ID's with unmanned positions: ");
        StringBuilder s = new StringBuilder();
        s.append("[");
        missing.forEach(shift -> {
            s.append(shift.SID).append(", ");
        });
        s.deleteCharAt(s.length() - 1);
        s.deleteCharAt(s.length() - 1);
        s.append("] ***");
        System.out.println(s.toString());
    }

    private void addEmployeeToShift() {
        while (true) {
            if (!printAllShifts("all", LocalDate.now().plusWeeks(2))) return;
            System.out.print("shift ID to add a employee: ");
            int SID = enterInt(read());
            System.out.print("employee ID: ");
            int EID = enterInt(read());
            if (showError(service.addEmpToShift(SID, EID, chooseRole()))) {
                if (goBack()) return;
            } else break;
        }
    }

    private String read() {
        return input.nextLine();
    }


    private void init() {
        int[] bankDetails = {123, 456, 789};
        int[] terms = {1000, 5, 10};
        service.createBranch("00000", 1, "PersonnelManager", bankDetails, 150000, terms);
        service.Login(1, "PersonnelManager");
        service.addEmployee(2, "DriverA", bankDetails, 10000, "Driver", LocalDate.now(), terms);
        service.addEmployee(3, "CashierA", bankDetails, 10000, "Cashier", LocalDate.now(), terms);
        service.addEmployee(4, "CashierB", bankDetails, 10000, "Cashier", LocalDate.now(), terms);
        service.addEmployee(5, "SorterA", bankDetails, 10000, "Sorter", LocalDate.now(), terms);
        service.addEmployee(6, "SorterB", bankDetails, 10000, "Sorter", LocalDate.now(), terms);
        service.addEmployee(7, "ShiftManagerA", bankDetails, 40000, "ShiftManager", LocalDate.now(), terms);
        service.addEmployee(8, "ShiftManagerB", bankDetails, 40000, "ShiftManager", LocalDate.now(), terms);
        Map<String, Integer> morning = new HashMap<>();
        morning.put("Driver", 1);
        morning.put("Cashier", 1);
        morning.put("Sorter", 2);
        morning.put("ShiftManager", 1);
        Map<String, Integer> night = new HashMap<>();
        night.put("Cashier", 1);
        night.put("ShiftManager", 1);
        SortedMap<String, Map<String, Integer>> defaultRolesAmount = new TreeMap<>();
        defaultRolesAmount.put("Night", night);
        defaultRolesAmount.put("Morning", morning);
        service.defaultShifts(defaultRolesAmount);
        service.Logout();
        service.Login(2, "Driver");
        service.addConstConstraint(DayOfWeek.SUNDAY, "Night", "tired");
        service.Logout();
        service.Login(1, "PersonnelManager");
        service.createWeekShifts();
        service.Logout();
    }

}
