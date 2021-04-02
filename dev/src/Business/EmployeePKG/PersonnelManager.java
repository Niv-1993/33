package Business.EmployeePKG;

import Business.ShiftPKG.*;
import Business.Type.RoleType;
import Business.Type.ShiftType;
import Database.Database;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class PersonnelManager extends Employee {
    final static Logger log = Logger.getLogger(PersonnelManager.class);

    public PersonnelManager(int EID, String name, int[] bankDetails, int salary, RoleType role, LocalDate startWorkDate, int[] terms) throws Exception {
        super(EID, name, bankDetails, salary, role, startWorkDate, terms);
    }

    public PersonnelManager(Employee other) throws Exception {
        super(other);
        log.debug("copy constructor of PersonnelManager");
    }

    /**
     * Note: if there will be more different kinds of employees we will make a Employee Factory to create them
     * for now, only Regular employees
     * Assuming there is a Personnel Manager in the system already
     */
    @Override
    public Employee addEmployee(int newEID, String name, int[] bankDetails, int salary, RoleType role, LocalDate startWorkDate, int[] terms, Map<Integer, Employee> employees) throws Exception {
        log.debug("enter add employee function to add: " + name + " with EID: " + newEID);
        if (employees.containsKey(newEID)) {
            log.error("cannot add employee that already exists");
            throw new Exception("Employee with id: " + newEID + " already exits in this branch");
        }
        Employee reg = new Regular(newEID, name, bankDetails, salary, role, startWorkDate, terms);
        Database.getInstance().addEmployee(getEID(), reg);
        employees.put(reg.getEID(), reg);
        log.debug("successfully added new employee EID: " + newEID + " to system");
        return reg;
    }

    /**
     * Note : when we will build the database there will be a chart of non-employed workers for record that this function
     * will add this employee there.
     * for now only remove from RAM
     */
    @Override
    public Employee fireEmployee(int fireEID, Map<Integer, Employee> employees) throws Exception {
        log.debug("entered fire employee function to fire EID: " + fireEID);
        if (!employees.containsKey(fireEID)) {
            log.error("EID is not found");
            throw new Exception("Employee with id: " + fireEID + " doesn't work in this branch");
        }
        if (fireEID == getEID()) {
            log.error("a user cannot fire himself");
            throw new Exception("Cannot fire yourself");
        }
        Database.getInstance().fireEmployee(fireEID);
        Employee removedE = employees.remove(fireEID);
        log.debug("successfully fired employee");
        return removedE;
    }

    @Override
    public void updateEmployeeName(int updateEID, String newName, Map<Integer, Employee> employees) throws Exception {
        log.debug("entered update employee function of: " + updateEID + " to: " + newName);
        checkWorking(updateEID, employees);
        checkName(newName);
        Database.getInstance().updateEmployeeName(employees.get(updateEID).getEID(), newName);
        employees.get(updateEID).setName(newName);
        log.debug("successfully updated name to: " + newName);
    }

    @Override
    public void updateEmployeeSalary(int updateEID, int newSalary, Map<Integer, Employee> employees) throws Exception {
        log.debug("entered update employee salary function of: " + updateEID + " to: " + newSalary);
        checkWorking(updateEID, employees);
        checkSalary(newSalary);
        Database.getInstance().updateEmployeeSalary(employees.get(updateEID).getEID(), newSalary);
        employees.get(updateEID).setSalary(newSalary);
        log.debug("successfully updated salary to: " + newSalary);
    }

    @Override
    public void updateEmployeeBANum(int updateEID, int newAccountNumber, Map<Integer, Employee> employees) throws Exception {
        log.debug("entered update employee bank account number function of: " + updateEID + " to: " + newAccountNumber);
        checkWorking(updateEID, employees);
        employees.get(updateEID).getBankAccount().setAccountNum(newAccountNumber);
        Database.getInstance().updateEmployeeBANum(employees.get(updateEID).getEID(), newAccountNumber);
        log.debug("successfully updated bank account number to: " + newAccountNumber);
    }

    @Override
    public void updateEmployeeBABranch(int updateEID, int newBranch, Map<Integer, Employee> employees) throws Exception {
        log.debug("entered update employee bank branch number function of: " + updateEID + " to: " + newBranch);
        checkWorking(updateEID, employees);
        employees.get(updateEID).getBankAccount().setBankBranch(newBranch);
        Database.getInstance().updateEmployeeBABranch(employees.get(updateEID).getEID(), newBranch);
        log.debug("successfully updated bank branch number to: " + newBranch);
    }

    @Override
    public void updateEmployeeBAID(int updateEID, int newBankID, Map<Integer, Employee> employees) throws Exception {
        log.debug("entered update employee bank ID number function of: " + updateEID + " to: " + newBankID);
        checkWorking(updateEID, employees);
        employees.get(updateEID).getBankAccount().setBankID(newBankID);
        Database.getInstance().updateEmployeeBAID(employees.get(updateEID).getEID(), newBankID);
        log.debug("successfully updated bank ID number to: " + newBankID);
    }

    @Override
    public void updateEmployeeEducationFund(int updateEID, int newEducationFund, Map<Integer, Employee> employees) throws Exception {
        log.debug("entered update employee education fund function of: " + updateEID + " to: " + newEducationFund);
        checkWorking(updateEID, employees);
        employees.get(updateEID).getTermsOfEmployment().setEducationFun(newEducationFund);
        Database.getInstance().updateEmployeeEducationFund(employees.get(updateEID).getEID(), newEducationFund);
        log.debug("successfully updated education fund to: " + newEducationFund);
    }

    @Override
    public void updateEmployeeDaysOff(int updateEID, int newAmount, Map<Integer, Employee> employees) throws Exception {
        log.debug("entered update employee days-off function of: " + updateEID + " to: " + newAmount);
        checkWorking(updateEID, employees);
        employees.get(updateEID).getTermsOfEmployment().setDaysOff(newAmount);
        Database.getInstance().updateEmployeeDaysOff(employees.get(updateEID).getEID(), newAmount);
        log.debug("successfully updated days-off to: " + newAmount);
    }

    @Override
    public void updateEmployeeSickDays(int updateEID, int newAmount, Map<Integer, Employee> employees) throws Exception {
        log.debug("entered update employee sick-days function of: " + updateEID + " to: " + newAmount);
        checkWorking(updateEID, employees);
        employees.get(updateEID).getTermsOfEmployment().setSickDays(newAmount);
        Database.getInstance().updateEmployeeSickDays(employees.get(updateEID).getEID(), newAmount);
        log.debug("successfully updated sick-days to: " + newAmount);
    }

    @Override
    public Shift createShift(Map<RoleType, Integer> rolesAmount, LocalDate date, ShiftType shiftType, Map<RoleType, List<String[]>> employees, ShiftController shiftController) throws Exception {
        log.debug("forwarding command to shiftPKG");
        Shift s = shiftController.createShift(rolesAmount, date, shiftType, employees);
        log.debug("returned to EmployeePKG successfully");
        return s;
    }

    @Override
    public List<Shift> getShiftsAndEmployees(ShiftController shiftController) {
        log.debug("forwarding command to shiftPKG");
        List<Shift> shifts = shiftController.getShiftsAndEmployees();
        log.debug("returned to EmployeePKG successfully");
        return shifts;
    }


    @Override
    public void removeEmpFromShift(int SID, int removeEID, ShiftController shiftController) throws Exception {
        log.debug("forwarding command to shiftPKG");
        shiftController.removeEmpFromShift(SID, removeEID);
        log.debug("returned to EmployeePKG successfully");
    }

    @Override
    public void addEmpToShift(int SID, int addEID, RoleType role, String name, ShiftController shiftController) throws Exception {
        log.debug("forwarding command to shiftPKG");
        shiftController.addEmpToShift(SID, addEID, role, name);
        log.debug("returned to EmployeePKG successfully");
    }

    @Override
    public void updateAmountRole(int SID, RoleType role, int newAmount, ShiftController shiftController) throws Exception {
        log.debug("forwarding command to shiftPKG");
        shiftController.updateAmountRole(SID, role, newAmount);
        log.debug("returned to EmployeePKG successfully");
    }

    @Override
    public void defaultShifts(Map<ShiftType, Map<RoleType, Integer>> defaults, ShiftController shiftController) {
        log.debug("forwarding command to shiftPKG");
        shiftController.defaultShifts(defaults);
        log.debug("returned to EmployeePKG successfully");
    }

    @Override
    public Shift createDefaultShift(LocalDate date, ShiftType shiftType, ShiftController shiftController) {
        log.debug("forwarding command to shiftPKG");
        Shift s = shiftController.createDefaultShift(date, shiftType);
        log.debug("returned to EmployeePKG successfully");
        return s;
    }
}
