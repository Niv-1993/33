package Presentation.Menu;

import Business.ApplicationFacade.ResponseData;
import Business.ApplicationFacade.iManagerRoleController;
import Business.ApplicationFacade.iRegularRoleController;
import Business.ApplicationFacade.outObjects.Employee;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class EmployeeMenu extends Menu {
    private final iManagerRoleController mc;

    public EmployeeMenu(iRegularRoleController rc, iManagerRoleController mc, Scanner input) {
        super(rc, input);
        this.mc = mc;
    }

    @Override
    public void show() {
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
            System.out.println("9) previous menu");
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
                case "9":
                    return;
                default:
                    System.out.println("Invalid input,please choose a number again");
                    if (goBack()) return;
                    break;
            }
        }
    }


    private void updateEmp(String s) {
        printAllEmployees("");
        System.out.print("Choose a employee ID to update " + s + ": ");
        int EID = enterInt(read());
        switch (s) {
            case "name":
                System.out.print("New name: ");
                String name = read();
                if (showError(mc.updateEmployeeName(EID, name))) {
                    if (goBack()) return;
                } else return;
                break;
            case "salary":
                System.out.print("New salary: ");
                int salary = enterInt(read());
                if (showError(mc.updateEmployeeSalary(EID, salary))) {
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

    private void fireEmployee() {
        while (true) {
            printAllEmployees("");
            System.out.println("Choose a employee ID to fire");
            int EID = enterInt(read());
            if (showError(mc.fireEmployee(EID))) {
                if (goBack()) return;
            } else break;
        }
    }

    private void printAllEmployees(String all) {
        System.out.println("All employees of this branch: ");
        ResponseData<List<Employee>> employees = mc.getAllEmployees();
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
            if (showError(mc.addEmployee(ID, name, new int[]{AC, BB, BID}, salary, role, LocalDate.now(), new int[]{fund, DO, SD}))) {
                if (goBack()) return;
            } else break;
        }

    }

    private void printBankDetailsOperation(int EID) {
        System.out.println("1) Update employee account number");
        System.out.println("2) Update employee branch number");
        System.out.println("3) Update employee bank ID\n");
        System.out.println("4) previous menu");
        System.out.println("Choose option: ");
        String option = read();
        int x;
        switch (option) {
            case "1":
                System.out.print("New account number: ");
                x = enterInt(read());
                if (!showError(mc.updateEmployeeBANum(EID, x))) return;
                if (goBack()) return;
                break;
            case "2":
                System.out.print("New branch number: ");
                x = enterInt(read());
                if (!showError(mc.updateEmployeeBABranch(EID, x))) return;
                if (goBack()) return;
                break;
            case "3":
                System.out.print("New bank ID: ");
                x = enterInt(read());
                if (!showError(mc.updateEmployeeBAID(EID, x))) return;
                if (goBack()) return;
                break;
            case "4": return;
            default:
                System.out.println("Invalid input,please choose a number again");
                if (goBack()) return;
                break;
        }
    }

    private void printTermsOfEmpOperation(int EID) {
        while (true) {
            System.out.println("1) Update employee education fund");
            System.out.println("2) Update employee days-off");
            System.out.println("3) Update employee sick-days\n");
            System.out.println("4) previous menu");
            System.out.println("Choose option: ");
            String option = read();
            int x;
            switch (option) {
                case "1":
                    System.out.print("New education fund: ");
                    x = enterInt(read());
                    if (!showError(mc.updateEmployeeEducationFund(EID, x))) return;
                    break;
                case "2":
                    System.out.print("New days-off: ");
                    x = enterInt(read());
                    if (!showError(mc.updateEmployeeDaysOff(EID, x))) return;
                    break;
                case "3":
                    System.out.print("New sick-days: ");
                    x = enterInt(read());
                    if (!showError(mc.updateEmployeeSickDays(EID, x))) return;
                    break;
                case "4": return;
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
            if (showError(mc.addRoleToEmployee(EID, chooseRole()))) {
                if (goBack()) return;
            } else break;
        }
    }

}
