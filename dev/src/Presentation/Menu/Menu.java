package Presentation.Menu;

import Business.ApplicationFacade.Response;
import Business.ApplicationFacade.ResponseData;
import Business.ApplicationFacade.iRegularRoleController;
import Business.ApplicationFacade.outObjects.Constraint;
import Business.ApplicationFacade.outObjects.Employee;
import Business.ApplicationFacade.outObjects.Shift;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public abstract class Menu {
    protected final Scanner input;
    protected final iRegularRoleController rc;
    public Menu(iRegularRoleController rc, Scanner input){
        this.input = input;
        this.rc = rc;
    }


    protected int enterInt(String s) {
        while (true) {
            try {
                return Integer.parseInt(s);
            } catch (Exception e) {
                System.out.println("input is not a number, please insert a number");
                s = read();
            }
        }
    }
    protected boolean goBack() {
        System.out.println("\n***[If you wish you go back to previous menu enter 1, else 0]***");
        return read().equals("1");
    }
    protected String read() {
        return input.nextLine();
    }


    protected LocalDate chooseDate() {
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
    protected String getReason() {
        System.out.println("Reason:");
        return read();
    }

    protected String chooseShiftType() {
        System.out.println("Choose a shift type");
        List<String> shiftTypes = rc.getShiftTypes().getData();
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

    protected DayOfWeek chooseDayOfWeek() {
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
    protected String chooseRole() {
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

    protected void printMyDetails() {
        ResponseData<Employee> res = rc.getEmployeeDetails();
        if (!showError(res)) {
            System.out.println(res.getData().toString());
        }
    }
    protected boolean showError(Response response) {
        if (response.isError()) {
            System.out.println("ERROR: " + response.getError());
            return true;
        }
        return false;
    }

    protected void printMyShifts() {
        System.out.println("Your Shifts: ");
        ResponseData<List<Shift>> shifts = rc.getMyShifts();
        if (!showError(shifts)) {
            if (shifts.getData().isEmpty()) System.out.println("You don't have any shifts");
            else {
                for (Shift s : shifts.getData())
                    System.out.println(s);
            }
        }
    }
    protected boolean printMyConstraints() {
        System.out.println("Your constraints: ");
        ResponseData<List<Constraint>> constraints = rc.getMyConstraints();
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
    public abstract void show();

}
