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
    private final Controllers r;
    private boolean isManager = false;

    public CLIPresentation() {
        r = new Controllers();
        input = new Scanner(System.in);
    }

    public void start() {
        System.out.println("\n***********************************************");
        System.out.println("*** Welcome to Super Li Employee management ***");
        System.out.println("***********************************************\n");
        System.out.println("Will you like to initialize an existing program? Y/N: ");
        if (read().equalsIgnoreCase("y")) r.init();
        System.out.println("\n***** If you want to return to previous menu while being in a menu, press any key that is not an option and after press 1 *****\n");
        while (true) {
            System.out.println("Current available branches are: "
                    + ((r.getRc().getBranches().isError()) ? "None.\n" : r.getRc().getBranches().getData().toString()) +
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
    //TODO : get also all info of branch
    private void createBranch() {
        String code, name;
        int ID, AC, BB, BID, salary, fund, DO, SD;
        System.out.println("In order to create a new branch, enter management code and the personnel manager's details for this branch\n");
        while (true) {
            System.out.println("Please enter code: ");
            code = read();
            if (!checkCode(code)) {
                System.out.println("Invalid Code permission.");
                if (goBack()) return;
                else
                    continue;
            }
            ID = getValidPMID();
            if (ID == -1) return;
            name = getNameOfPM();
            if (name.equals("1")) return;
            AC = getBankAccountNumber();
            if (AC == -1) return;
            BB = getBankBranchNumber();
            if (BB == -1) return;
            BID = getBankBID();
            if (BID == -1) return;
            salary = getSalary();
            if (salary == -1) return;
            fund = getEducationFund();
            if (fund == -1) return;
            DO = getDaysOff();
            if (DO == -1) return;
            SD = getSickDays();
            if (SD == -1) return;
            System.out.println();
            r.getRc().createBranch(code, ID, name, new int[]{AC, BB, BID}, salary, new int[]{fund, DO, SD});
            break;
        }
    }

    private int getSickDays() {
        while (true) {
            System.out.print("sick-days: ");
            int num = enterInt(read());
            if (num < 0) {
                System.out.println("invalid sick days input.");
                if (goBack()) return -1;
                else
                    continue;
            }
            return num;
        }
    }

    private int getDaysOff() {
        while (true) {
            System.out.print("days-off: ");
            int num = enterInt(read());
            if (num < 0) {
                System.out.println("invalid days off input.");
                if (goBack()) return -1;
                else
                    continue;
            }
            return num;
        }
    }

    private int getEducationFund() {
        while (true) {
            System.out.print("education fund: ");
            int num = enterInt(read());
            if (num < 0) {
                System.out.println("invalid education fund number");
                if (goBack()) return -1;
                else
                    continue;
            }
            return num;
        }
    }

    private int getSalary() {
        while (true) {
            System.out.print("salary: ");
            int num = enterInt(read());
            if (num < 0) {
                System.out.println("invalid salary number");
                if (goBack()) return -1;
                else
                    continue;
            }
            return num;
        }
    }

    private int getBankBID() {
        while (true) {
            System.out.print("bank ID: ");
            int num = enterInt(read());
            if (num <= 0) {
                System.out.println("invalid bank id number.");
                if (goBack()) return -1;
                else
                    continue;
            }
            return num;
        }
    }

    private int getBankBranchNumber() {
        while (true) {
            System.out.print("bank branch number: ");
            int num = enterInt(read());
            if (num <= 0) {
                System.out.println("invalid branch number.");
                if (goBack()) return -1;
                else
                    continue;
            }
            return num;
        }
    }

    private int getBankAccountNumber() {
        while (true) {
            System.out.print("bank account number: ");
            int num = enterInt(read());
            if (num <= 0) {
                System.out.println("invalid account number.");
                if (goBack()) return -1;
                else
                    continue;
            }
            return num;
        }
    }

    private int getValidPMID() {
        while (true) {
            System.out.print("ID: ");
            int num = enterInt(read());
            if (num <= 0) {
                System.out.println("invalid id - negative number.");
                if (goBack()) return -1;
                else
                    continue;
            }
            if (r.getRc().checkEIDExists(num)) {
                System.out.println("Chosen id already exists in system.");
                if (goBack()) return -1;
                else continue;
            }
            return num;
        }
    }

    private String getNameOfPM() {
        while (true) {
            System.out.print("name: ");
            String name = read();
            if (!checkName(name)) {
                System.out.println("name " + name + " is not alphabetical");
                if (goBack()) return "1";
                else
                    continue;
            }
            return name;
        }
    }

    private boolean checkName(String name) {
        name = name.replaceAll("\\s+", "");
        return !name.equals("") && name.matches("^[a-zA-Z]*$");
    }

    private boolean checkCode(String code) {
        return code.equals("00000");
    }

    private boolean showError(Response response) {
        if (response.isError()) {
            System.out.println("ERROR: " + response.getError());
            return true;
        } else System.out.println("Success.\n");
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

    private int getExistingBranch() {
        while (true) {
            System.out.print("ID: ");
            int num = enterInt(read());
            if (num <= 0) {
                System.out.println("invalid id - negative number.");
                if (goBack()) return -1;
                else
                    continue;
            }
            if (!r.getRc().getBranches().getData().contains(String.valueOf(num))) {
                System.out.println("Id of this branch does not exist.");
                if (goBack()) return -1;
                else
                    continue;
            }
            return num;
        }
    }

    private void enterBranch() {
        while (true) {
            System.out.print("Branch Number to enter: ");
            int branchNum = getExistingBranch();
            if (branchNum == -1) return;
            r.getRc().EnterBranch(branchNum);
            if (!r.getRc().hasDefaultShifts().getData()) {
                AddDefaultWeekShifts();
            }
            break;
        }
        login();
    }

    private void AddDefaultWeekShifts() {
        Map<String, Map<String, Integer>> defaults = new HashMap<>();
        List<String> shiftTypes = r.getRc().getShiftTypes().getData();
        System.out.println("\nNo default shifts were set, please set default shifts for this branch.");
        for (String shiftType : shiftTypes) {
            System.out.println("For shift type: " + shiftType + " enter default amounts for each role.");
            Map<String, Integer> roleAmount = chooseRolesAmount();
            defaults.put(shiftType, roleAmount);
        }
        r.getMc().defaultShifts(defaults);
    }

    private void login() {
        int EID;
        String role;
        while (true) {
            System.out.println("\n\n************* Login **************");
            System.out.println("Please enter your ID and role");
            EID = getEmpID();
            role = chooseRole();
            if (!r.getRc().isQualified(EID, role)) {
                if (goBack()) return;
                else continue;
            }
            r.getRc().Login(EID);
            break;
        }
        isManager = role.equals("PersonnelManager");
        allFunctionsMenu();
    }

    private int getEmpID() {
        while (true) {
            System.out.print("ID: ");
            int num = enterInt(read());
            if (num <= 0) {
                System.out.println("invalid id - negative number.");
                if (goBack()) return -1;
                else
                    continue;
            }
            if (!r.getRc().checkEIDExists(num)) {
                System.out.println("invalid id -user with " + num + " does not exist .");
                if (goBack()) return -1;
                else
                    continue;
            }
            return num;
        }
    }


    private boolean goBack() {
        System.out.println("\n***[If you wish you go back to previous menu enter 1, else 0]***");
        return read().equals("1");
    }

    private void allFunctionsMenu() {
        if (!isManager) {
            Menu reg = new RegularMenu(r, input);
            reg.show();
        } else {
            Menu man = new ManagerMenu(r, input);
            man.show();
        }
    }

    private String chooseRole() {
        System.out.println("\nChoose a role");
        List<String> roles = r.getRc().getRoleTypes().getData();
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
        List<String> roleTypes = r.getRc().getRoleTypes().getData();
        for (String role : roleTypes) {
            if (role.equals("PersonnelManager") || role.equals("BranchManager") || role.equals("Driver")|| role.equals("Sorter")) continue;
            System.out.print(role + ": ");
            int amount = getAmount(role);
            rolesAmount.put(role, amount);
        }
        return rolesAmount;
    }

    private int getAmount(String role) {
        int amount;
        while (true) {
            amount = enterInt(read());
            if (amount < 0) {
                System.out.println("Invalid amount - negative");
                continue;
            }
            if (role.equals("ShiftManager") && amount != 1) {
                System.out.println("Invalid amount - shift manager amount need to be 1");
                continue;
            }
            break;
        }
        return amount;
    }

    private String read() {
        return input.nextLine();
    }




}
