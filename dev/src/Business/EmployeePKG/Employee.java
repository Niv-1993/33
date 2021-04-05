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
    public Employee(int EID, String name, int[] bankDetails, int salary, RoleType role, LocalDate startWorkDate, int[] terms) throws Exception {
        checkName(name);
        this.name = name;
        checkSalary(salary);
        this.salary = salary;
        bankAccount = new BankAccount(bankDetails);
        termsOfEmployment = new TermsOfEmployment(terms);
        this.EID = EID;
        this.role = new ArrayList<>();
        this.role.add(role);
        this.startWorkingDate = startWorkDate;

    }


    //copy constructor
    public Employee(Employee other) throws Exception {
        this.EID = other.getEID();
        this.name = other.getName();
        this.bankAccount = new BankAccount(other.bankAccount);
        this.salary = other.getSalary();
        this.role = new ArrayList<>(other.getRole());
        this.startWorkingDate = other.getStartWorkingDate();
        termsOfEmployment = new TermsOfEmployment(other.termsOfEmployment);
    }


    public Constraint addConstConstraint(DayOfWeek day, ShiftType shiftType, String reason, ShiftController shiftController) {
        log.debug("enter add const constraint function");
        Constraint c = shiftController.addConstConstraint(EID, day, shiftType, reason);
        log.debug("successfully add const constraint");
        return c;
    }

    public Constraint addConstraint(LocalDate c_date, ShiftType shiftType, String reason, ShiftController shiftController) throws Exception {
        log.debug("enter add constraint function");
        Constraint c = shiftController.addConstraint(EID, c_date, shiftType, reason);
        log.debug("successfully add constraint");
        return c;
    }

    public Constraint removeConstraint(int CID, ShiftController shiftController) throws Exception {
        log.debug("enter remove constraint function CID: " + CID);
        Constraint c = shiftController.removeConstraint(CID,getEID());
        log.debug("successfully removed constraint CID: " + CID);
        return c;
    }

    public void updateReasonConstraint(int CID, String newReason, ShiftController shiftController) throws Exception {
        log.debug("enter update reason constraint function CID: " + CID);
        shiftController.updateReasonConstraint(CID, newReason,getEID());
        log.debug("successfully updated reason constraint CID: " + CID);
    }

    public void updateShiftTypeConstraint(int CID, ShiftType newType, ShiftController shiftController) throws Exception {
        log.debug("enter update shift type in constraint CID: " + CID);
        shiftController.updateShiftTypeConstraint(CID, newType,getEID());
        log.debug("successfully updated shift type in constraint CID: " + CID);
    }

    public List<Shift> getOnlyEmployeeShifts(ShiftController shiftController) {
        log.debug("forwarding command to shiftPKG");
        List<Shift> l = shiftController.getOnlyEmployeeShifts(getEID());
        log.debug("returned to EmployeePKG successfully");
        return l;
    }

    public List<Constraint> getOnlyEmployeeConstraints(ShiftController shiftController) {
        log.debug("forwarding command to shiftPKG");
        List<Constraint> l = shiftController.getOnlyEmployeeConstraints(getEID());
        log.debug("returned to EmployeePKG successfully");
        return l;
    }

    public boolean isQualified(RoleType role) {
        return this.role.contains(role);
    }

    /**
     * Abstract Functions for permissions management
     */
    public abstract Employee addEmployee(int newEID, String name, int[] bankDetails, int salary, RoleType role, LocalDate startWorkDate, int[] terms, Map<Integer, Employee> employees) throws Exception;

    public abstract Employee fireEmployee(int fireEID, Map<Integer, Employee> employees) throws Exception;

    public abstract void updateEmployeeName(int updateEID, String newName, Map<Integer, Employee> employees) throws Exception;

    public abstract void updateEmployeeSalary(int updateEID,int newSalary,Map<Integer, Employee> employees) throws Exception;

    public abstract void updateEmployeeBANum(int updateEID,int newAccountNumber,Map<Integer, Employee> employees) throws Exception;

    public abstract void updateEmployeeBABranch(int updateEID,int newBranch,Map<Integer, Employee> employees) throws Exception;

    public abstract void updateEmployeeBAID(int updateEID,int newBankID,Map<Integer, Employee> employees) throws Exception;

    public abstract void updateEmployeeEducationFund(int updateEID,int newEducationFund,Map<Integer, Employee> employees) throws Exception;

    public abstract void updateEmployeeDaysOff(int updateEID,int newAmount,Map<Integer, Employee> employees) throws Exception;

    public abstract void updateEmployeeSickDays(int updateEID,int newAmount,Map<Integer, Employee> employees) throws Exception;

    public abstract Shift createShift(Map<RoleType, Integer> rolesAmount, LocalDate date, ShiftType shiftType, Map<RoleType, List<String[]>> employees, ShiftController shiftController) throws Exception;

    public abstract List<Shift> getShiftsAndEmployees(ShiftController shiftController) throws Exception;

    public abstract void removeEmpFromShift(int SID, int removeEID, ShiftController shiftController) throws Exception;

    public abstract void addEmpToShift(int SID, int addEID, RoleType role, String name, ShiftController shiftController) throws Exception;

    public abstract void updateAmountRole(int SID, RoleType role, int newAmount, ShiftController shiftController) throws Exception;

    public abstract void defaultShifts(Map<ShiftType, Map<RoleType, Integer>> defaults, ShiftController shiftController) throws Exception;

    public abstract void addRoleToEmployee(int eid, RoleType role,Map<Integer, Employee> employees) throws Exception;
    public abstract void createWeekShifts(Map<RoleType, List<String[]>> optionals, ShiftController shiftController) throws Exception;
    public abstract void selfMakeWeekShifts(ShiftController shiftController) throws Exception;
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

    /**
     * Input checks
     */

    protected void checkSalary(int salary) throws Exception {
        log.debug("checking salary");
        if (salary <= 0) {
            log.error("salary is 0 or negative: " + salary);
            throw new Exception("Invalid salary input");
        }

    }

    protected void checkName(String name) throws Exception {
        log.debug("checking name alphabetical");
        boolean isValid = ((name != null) && (!name.equals("")) && (name.matches("^[a-zA-Z]*$")));
        if (!isValid) {
            log.error("name " + name + " is not alphabetical");
            throw new Exception("Invalid name: " + name);
        }
    }
    protected void checkWorking(int EID,Map<Integer, Employee> employees) throws Exception {
        if (!employees.containsKey(EID)) {
            log.error("user with id: " + EID + " is not in map of employees");
            throw new Exception("Employee with id: " + EID + " doesn't work in this branch");
        }
        log.debug("checked that employee is working in this branch - success");
    }



}

