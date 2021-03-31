package Business.EmployeePKG;
import Business.ShiftPKG.*;
import Business.Type.RoleType;
import Business.Type.ShiftType;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class PersonnelManager extends Employee{
    final static Logger log = Logger.getLogger(PersonnelManager.class);
    public PersonnelManager(int EID, String name, int[] bankDetails, int salary, RoleType role, LocalDate startWorkDate, int[] terms) {
        super(EID, name, bankDetails, salary, role, startWorkDate, terms);
    }

    /**
     * Note: if there will be more different kinds of employees we will make a Employee Factory to create them
     * for now, only Regular employees
     * Assuming there is a Personnel Manager in the system already
     */
    @Override
    public Employee addEmployee(int newEID, String name, int[] bankDetails, int salary, RoleType role, LocalDate startWorkDate, int[] terms, Map<Integer, Employee> employees) throws Exception {
        if(employees.containsKey(newEID))
            throw new Exception("Employee with id: "+ newEID+ " already exits in this branch");
        //ADD TO DATABASE
        Employee reg = new Regular(newEID,name,bankDetails,salary,role,startWorkDate,terms);
        employees.put(reg.getEID(),reg);
        return reg;
    }

    /**
     * Note : when we will build the database there will be a chart of non-employed workers for record that this function
     * will add this employee there.
     * for now only remove from RAM
     */
    @Override
    public void fireEmployee(int fireEID, Map<Integer, Employee> employees) throws Exception {
        if(!employees.containsKey(fireEID))
            throw new Exception("Employee with id: "+fireEID+" doesn't work in this branch");
        //REMOVE FROM DATABASE
        employees.remove(fireEID);
    }

    @Override
    public void updateEmployeeName(Employee updateE, String newName) {
        //UPDATE DATABASE
        updateE.setName(newName);
    }

    @Override
    public void updateEmployeeSalary(Employee updateE, int newSalary) {
        //UPDATE DATABASE
        updateE.setSalary(newSalary);
    }

    @Override
    public void updateEmployeeBANum(Employee updateE, int newAccountNumber) {
        //UPDATE DATABASE
        updateE.getBankAccount().setAccountNum(newAccountNumber);
    }

    @Override
    public void updateEmployeeBABranch(Employee updateE, int newBranch) {
        //UPDATE DATABASE
        updateE.getBankAccount().setBankBranch(newBranch);
    }

    @Override
    public void updateEmployeeBAID(Employee updateE, int newBankID) {
        //UPDATE DATABASE
        updateE.getBankAccount().setBankID(newBankID);
    }

    @Override
    public void updateEmployeeEducationFund(Employee updateE, int newEducationFund) {
        //UPDATE DATABASE
        updateE.getTermsOfEmployment().setEducationFun(newEducationFund);
    }

    @Override
    public void updateEmployeeDaysOff(Employee updateE, int newAmount) {
        //UPDATE DATABASE
        updateE.getTermsOfEmployment().setDaysOff(newAmount);
    }

    @Override
    public void updateEmployeeSickDays(Employee updateE, int newAmount) {
        //UPDATE DATABASE
        updateE.getTermsOfEmployment().setSickDays(newAmount);
    }

    @Override
    public Business.ShiftPKG.Shift createShift(Map<RoleType, Integer> rolesAmount, LocalDate date, ShiftType shiftType, Map<RoleType, List<String[]>> employees, ShiftController shiftController) throws Exception {
        //SHIFT PKG NEEDS TO UPDATE DATABASE
        return shiftController.createShift(rolesAmount,date,shiftType,employees);
    }

    @Override
    public List<Shift> getShiftsAndEmployees(ShiftController shiftController) {
        return shiftController.getShiftsAndEmployees();
    }


    @Override
    public void removeEmpFromShift(int SID, int removeEID, ShiftController shiftController) throws Exception {
        //SHIFT PKG NEEDS TO UPDATE DATABASE
        shiftController.removeEmpFromShift(SID,removeEID);
    }

    @Override
    public void addEmpToShift(int SID, int addEID, RoleType role, String name, ShiftController shiftController) throws Exception {
        //SHIFT PKG NEEDS TO UPDATE DATABASE
        shiftController.addEmpToShift(SID,addEID,role,name);
    }

    @Override
    public void updateAmountRole(int SID, RoleType role, int newAmount, ShiftController shiftController) throws Exception {
        //SHIFT PKG NEEDS TO UPDATE DATABASE
        shiftController.updateAmountRole(SID,role,newAmount);
    }
}
