package Presentation.Menu;

import Business.ApplicationFacade.iRegularRoleController;

import java.util.Scanner;

public class ConstraintMenu extends Menu{

    public ConstraintMenu(iRegularRoleController rc, Scanner input){
        super(rc, input);
    }

    @Override
    public void show() {
        while (true) {
            System.out.println("\n\n************* Constraint Menu *************\n");
            System.out.println("1) Add const constraint");
            System.out.println("2) Add temporal constraint");
            System.out.println("3) Remove constraint");
            System.out.println("4) Update reason");
            System.out.println("5) Update shift type");
            System.out.println("6) previous menu");
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
                case "6": return;
                default:
                    System.out.println("Invalid input,please choose a number again");
                    if (goBack()) return;
                    break;
            }
        }
    }
    private void addConstConstraint() {
        System.out.println("To add a const constraint details are requested");
        showError(rc.addConstConstraint(chooseDayOfWeek(), chooseShiftType(), getReason()));
    }

    private void addTempConstraint() {
        System.out.println("To add a temporal constraint details are requested");
        showError(rc.addTempConstraint(chooseDate(), chooseShiftType(), getReason()));
    }
    private void removeConstraint() {
        while (true) {
            if (!printMyConstraints()) break;
            System.out.println("Choose a constraint ID to remove");
            int CID = enterInt(read());
            if (showError(rc.removeConstraint(CID))) {
                if (goBack()) return;
            } else break;
        }
    }

    private void updateShiftType() {
        while (true) {
            if (!printMyConstraints()) break;
            System.out.println("Choose a constraint ID to update shift type");
            int CID = enterInt(read());
            if (showError(rc.updateShiftTypeConstraint(CID, chooseShiftType()))) {
                if (goBack()) return;
            } else break;
        }
    }

    private void updateReason() {
        while (true) {
            if (!printMyConstraints()) break;
            System.out.println("Choose a constraint ID to update reason");
            int CID = enterInt(read());
            if (showError(rc.updateReasonConstraint(CID, getReason()))) {
                if (goBack()) return;
            } else break;
        }
    }

}
