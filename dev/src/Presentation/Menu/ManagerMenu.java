package Presentation.Menu;

import Business.ApplicationFacade.*;

import java.util.Scanner;

public class ManagerMenu extends Menu {
    private final iManagerRoleController mc;

    public ManagerMenu(iRegularRoleController rc, iManagerRoleController mc, Scanner input) {
        super(rc, input);
        this.mc = mc;
    }

    @Override
    public void show() {
        while (true) {
            System.out.println("\n\n************* Functions Menu *************");
            System.out.println("1) My details");
            System.out.println("2) My constraints operations");
            System.out.println("3) My shifts and constraints");
            System.out.println("4) Logout");
            System.out.println("5) Employee operations menu");
            System.out.println("6) Shift operations menu");
            System.out.println("7) previous menu");
            System.out.println("Choose an option:");
            String option = read();
            switch (option) {
                case "1":
                    printMyDetails();
                    break;
                case "2":
                    Menu consM = new ConstraintMenu(rc, input);
                    consM.show();
                    break;
                case "3":
                    printMyShifts();
                    System.out.println();
                    printMyConstraints();
                    System.out.println();
                    break;
                case "4":
                    if (!showError(rc.Logout())) return;
                    break;
                case "5":
                    Menu empM = new EmployeeMenu(rc, mc, input);
                    empM.show();
                    break;
                case "6":
                    Menu shftM = new ShiftMenu(rc, mc, input);
                    shftM.show();
                    break;
                case "7":
                    return;
                default:
                    System.out.println("Invalid input,please choose a number again");
                    if (goBack()) return;
                    break;
            }
        }
    }
}
