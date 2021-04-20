package Presentation.Menu;

import Business.ApplicationFacade.ResponseData;
import Business.ApplicationFacade.iManagerRoleController;
import Business.ApplicationFacade.iRegularRoleController;
import Business.ApplicationFacade.outObjects.Shift;

import java.time.LocalDate;
import java.util.*;

public class ShiftMenu extends Menu{
    private final iManagerRoleController mc;
    public ShiftMenu(iRegularRoleController rc,iManagerRoleController mc, Scanner input){
        super(rc, input);
        this.mc = mc;
    }
    @Override
    public void show() {
        while (true) {
            System.out.println("\n*************** Shift Menu ***************");
            System.out.println("1) Add employee to shift");
            System.out.println("2) Remove employee from shift");
            System.out.println("3) Create Shift");
            System.out.println("4) Create shifts for next week");
            System.out.println("5) make placement for shifts of next week");
            System.out.println("6) Print all shifts");
            System.out.println("7) Update the amount of a specific role in a shift");
            System.out.println("8) previous menu");
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
                    List<Shift> s = mc.createWeekShifts().getData();
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
                    if(!showError(mc.selfMakeWeekShifts())) System.out.println("\nNext week's shifts were successfully placed automatically\n");
                    break;
                case "6":
                    printAllShifts("all", LocalDate.now().plusWeeks(2));
                    break;
                case "7":
                    updateAmountRole();
                    break;
                case "8":
                    return;
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
            if (showError(mc.updateAmountRole(SID, role, amount))) {
                if (goBack()) return;
            } else break;
        }
    }
    private void removeEmployeeFromShift() {
        while (true) {
            if (!printAllShifts("all", LocalDate.now().plusWeeks(2))) return;
            System.out.print("shift ID to remove a employee: ");
            int SID = enterInt(read());
            System.out.print("employee ID: ");
            int EID = enterInt(read());
            if (showError(mc.removeEmpFromShift(SID, EID))) {
                if (goBack()) return;
            } else break;
        }
    }

    private void addEmployeeToShift() {
        while (true) {
            if (!printAllShifts("all", LocalDate.now().plusWeeks(2))) return;
            System.out.print("shift ID to add a employee: ");
            int SID = enterInt(read());
            System.out.print("employee ID: ");
            int EID = enterInt(read());
            if (showError(mc.addEmpToShift(SID, EID, chooseRole()))) {
                if (goBack()) return;
            } else break;
        }
    }

    private boolean printAllShifts(String all, LocalDate until) {
        System.out.println("All shifts of this branch until " + until + " :");
        ResponseData<List<Shift>> shifts = mc.getShifts(until);
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



    private void createShift() {
        while (true) {
            System.out.println("To create a new shift enter the following details: ");
            LocalDate date = chooseDate();
            String shiftType = chooseShiftType();
            Map<String, Integer> rolesAmount = chooseRolesAmount();
            if (showError(mc.createShift(rolesAmount, date, shiftType))) {
                if (goBack()) return;
            } else break;
        }
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


}
