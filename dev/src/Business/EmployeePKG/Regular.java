package Business.EmployeePKG;

import Business.ShiftPKG.*;
import Business.Type.*;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Regular extends Employee{
    final static Logger log = Logger.getLogger(Regular.class);
    public Regular(int EID, String name, int[] bankDetails, int salary, RoleType role, LocalDate startWorkDate, int[] terms) throws Exception {
        super(EID, name, bankDetails, salary, role, startWorkDate, terms);
    }
    public Regular(Employee other) throws Exception {
        super(other);
    }

    @Override
    public Employee addEmployee(int newEID, String name, int[] bankDetails, int salary, RoleType role, LocalDate startWorkDate, int[] terms, Map<Integer, Employee> employees) throws Exception {
        throw new Exception("You are not allowed to add any new employee to this branch");
    }

    @Override
    public Employee fireEmployee(int fireEID, Map<Integer, Employee> employees) throws Exception {
        throw new Exception("You are not allowed to fire any employee from this branch");
    }

    @Override
    public void updateEmployeeName(int updateEID, String newName, Map<Integer, Employee> employees) throws Exception {
        throw new Exception("You are not allowed to change the name of any employee from this branch");
    }

    @Override
    public void updateEmployeeSalary(int updateEID, int newSalary, Map<Integer, Employee> employees) throws Exception {
        throw new Exception("You are not allowed to change the salary of any employee from this branch");
    }

    @Override
    public void updateEmployeeBANum(int updateEID, int newAccountNumber, Map<Integer, Employee> employees) throws Exception {
        throw new Exception("You are not allowed to update bank account of any employee from this branch");
    }

    @Override
    public void updateEmployeeBABranch(int updateEID, int newBranch, Map<Integer, Employee> employees) throws Exception {
        throw new Exception("You are not allowed to update bank account of any employee from this branch");
    }

    @Override
    public void updateEmployeeBAID(int updateEID, int newBankID, Map<Integer, Employee> employees) throws Exception {
        throw new Exception("You are not allowed to update bank account of any employee from this branch");
    }

    @Override
    public void updateEmployeeEducationFund(int updateEID, int newEducationFund, Map<Integer, Employee> employees) throws Exception {
        throw new Exception("You are not allowed to update terms of employment of any employee from this branch");
    }

    @Override
    public void updateEmployeeDaysOff(int updateEID, int newAmount, Map<Integer, Employee> employees) throws Exception {
        throw new Exception("You are not allowed to update terms of employment of any employee from this branch");
    }

    @Override
    public void updateEmployeeSickDays(int updateEID, int newAmount, Map<Integer, Employee> employees) throws Exception {
        throw new Exception("You are not allowed to update terms of employment of any employee from this branch");
    }

    @Override
    public Shift createShift(Map<RoleType, Integer> rolesAmount, LocalDate date, ShiftType shiftType, Map<RoleType, List<String[]>> employees, ShiftController shiftController) throws Exception {
        throw new Exception("You are not allowed to create shifts in this branch");
    }

    @Override
    public List<Shift> getShiftsAndEmployees(ShiftController shiftController) throws Exception {
        throw new Exception("You are not allowed to request this kind of data in this branch");
    }


    @Override
    public void removeEmpFromShift(int SID, int removeEID, ShiftController shiftController) throws Exception {
        throw new Exception("You are not allowed to remove anyone from shifts");
    }

    @Override
    public void addEmpToShift(int SID, int addEID, RoleType role, String name, ShiftController shiftController) throws Exception {
        throw new Exception("You are not allowed to add anyone to shifts");
    }

    @Override
    public void updateAmountRole(int SID, RoleType role, int newAmount, ShiftController shiftController) throws Exception {
        throw new Exception("You are not allowed to update any shift");
    }

    @Override
    public void defaultShifts(Map<ShiftType, Map<RoleType, Integer>> defaults, ShiftController shiftController) throws Exception {
        throw new Exception("You are not allowed to set default shifts");
    }


    @Override
    public void addRoleToEmployee(int eid, RoleType role, Map<Integer, Employee> employees) throws Exception {
        throw new Exception("You are not allowed to add role to employee");
    }

    @Override
    public void createWeekShifts(Map<RoleType, List<String[]>> optionals, ShiftController shiftController) throws Exception {
        throw new Exception("You are not allowed to create week shifts");
    }

    @Override
    public void selfMakeWeekShifts(ShiftController shiftController) throws Exception {
        throw new Exception("You are not allowed to self make week shifts");
    }

}
