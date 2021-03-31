package Business.EmployeePKG;

import Business.ShiftPKG.*;
import Business.Type.*;
import org.apache.log4j.Logger;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Employee {
    final static Logger log = Logger.getLogger(Employee.class);
    private int EID;
    private String name;
    private BankAccount bankAccount;
    private int salary;
    private List<RoleType> role;
    private LocalDate startWorkingDate;
    private TermsOfEmployment termsOfEmployment;

    /**
     * Constructor
     */
    public Employee(int EID, String name,int[] bankDetails,int salary,RoleType role,LocalDate startWorkDate,int[] terms){
        this.EID = EID;
        this.name = name;
        bankAccount = new BankAccount(bankDetails[0],bankDetails[1],bankDetails[2]);
        this.salary = salary;
        this.role = new ArrayList<>();
        this.role.add(role);
        this.startWorkingDate = startWorkDate;
        termsOfEmployment = new TermsOfEmployment(terms[0],terms[1],terms[2]);
    }


    public Constraint addConstConstraint(DayOfWeek day, ShiftType shiftType, String reason, ShiftController shiftController) {
        return shiftController.addConstConstraint(EID,day,shiftType,reason);
    }
    public Constraint addConstraint(LocalDate c_date, ShiftType shiftType, String reason, ShiftController shiftController) {
        return shiftController.addConstraint(EID,c_date,shiftType,reason);
    }
    public Constraint removeConstraint(int CID, ShiftController shiftController) throws Exception {
        return shiftController.removeConstraint(CID);
    }
    public void updateReasonConstraint(int CID, String newReason, ShiftController shiftController) throws Exception {
        shiftController.updateReasonConstraint(CID,newReason);
    }
    public void updateShiftTypeConstraint(int CID, ShiftType newType, ShiftController shiftController) throws Exception {
        shiftController.updateShiftTypeConstraint(CID,newType);
    }
    public List<Shift> getOnlyEmployeeShifts(ShiftController shiftController){return shiftController.getOnlyEmployeeShifts(getEID());}
    public List<Constraint> getOnlyEmployeeConstraints(ShiftController shiftController){return shiftController.getOnlyEmployeeConstraints(getEID());}
    public void addToDB(){}


    public boolean isQualified(RoleType role){
        return this.role.contains(role);
    }

    /**
     *Abstract Functions
     */
    public abstract Employee addEmployee(int newEID, String name, int[] bankDetails, int salary, RoleType role, LocalDate startWorkDate, int[] terms, Map<Integer, Employee> employees) throws Exception;
    public abstract void fireEmployee(int fireEID, Map<Integer, Employee> employees) throws Exception;
    public abstract void updateEmployeeName(Employee updateE,String newName) throws Exception;
    public abstract void updateEmployeeSalary(Employee updateE,int newSalary) throws Exception;
    public abstract void updateEmployeeBANum(Employee updateE,int newAccountNumber) throws Exception;
    public abstract void updateEmployeeBABranch(Employee updateE,int newBranch) throws Exception;
    public abstract void updateEmployeeBAID(Employee updateE,int newBankID) throws Exception;
    public abstract void updateEmployeeEducationFund(Employee updateE,int newEducationFund) throws Exception;
    public abstract void updateEmployeeDaysOff(Employee updateE,int newAmount) throws Exception;
    public abstract void updateEmployeeSickDays(Employee updateE,int newAmount) throws Exception;
    public abstract Shift createShift(Map<RoleType,Integer> rolesAmount, LocalDate date, ShiftType shiftType, Map<RoleType,List<String[]>> employees,ShiftController shiftController) throws Exception;
    public abstract List<Shift> getShiftsAndEmployees(ShiftController shiftController) throws Exception;
    public abstract void removeEmpFromShift(int SID,int removeEID,ShiftController shiftController) throws Exception;
    public abstract void addEmpToShift(int SID, int addEID, RoleType role, String name, ShiftController shiftController) throws Exception;
    public abstract void updateAmountRole(int SID,RoleType role,int newAmount, ShiftController shiftController) throws Exception;


    /**
     * Getters/Setters
     */
    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public LocalDate getStartWorkingDate() {
        return startWorkingDate;
    }

    public int getEID() {
        return EID;
    }

    public int getSalary() {
        return salary;
    }

    public List<RoleType> getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public TermsOfEmployment getTermsOfEmployment() {
        return termsOfEmployment;
    }


    public void setName(String name) {
        this.name = name;
    }


    public void setSalary(int salary) {
        this.salary = salary;
    }

}
