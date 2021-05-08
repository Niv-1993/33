package Business.ApplicationFacade;

import Business.ApplicationFacade.outObjects.*;
import Business.ShiftPKG.ShiftController;
import Business.Type.*;
import DataAccess.EmployeeMapper;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.util.*;


public class ManagerRoleController implements iManagerRoleController {
    final static Logger log = Logger.getLogger(ManagerRoleController.class);
    private final EmployeeMapper employeeMapper = EmployeeMapper.getInstance();
    private final ShiftController sc;
    private final Utils utils;


    /**
     * Constructors
     */
    public ManagerRoleController(Utils u) {
        utils = u;
        sc = u.getShiftController();
    }


    /**
     * Add a new employee to the system with all his details
     *
     * @param newEID        Identification number of the new employee
     * @param name          Name of the new employee
     * @param bankDetails   array of bank details :
     *                      bankDetails[0] -> account number
     *                      bankDetails[1] -> bank branch
     *                      bankDetails[2] -> bank id
     * @param salary        THe salary of the new employee
     * @param role          The role of the new employee
     * @param startWorkDate THe starting date of the new employee
     * @param terms         Terms of employments :
     *                      terms[0] -> education fund
     *                      terms[1] -> days off
     *                      terms[2] -> sick days
     * @return A response object. The response should contain a error message in case of an error
     */
    public ResponseData<Employee> addEmployee(int newEID, String name, int[] bankDetails, int salary, String role, LocalDate startWorkDate, int[] terms) {
        log.debug("enter add employee function");
        Business.EmployeePKG.Employee emp = new Business.EmployeePKG.Employee(newEID, name, bankDetails, salary, RoleType.valueOf(role), startWorkDate, terms);
        Employee employee = new Employee(emp);
        employeeMapper.insert(emp.getEID(), emp);
        sc.addToOptionals(emp,RoleType.valueOf(role),utils.generate_optionals());
        utils.setNeedToUpdateOps(true);
        log.debug("successfully added new employee EID: " + newEID + " to system");
        return new ResponseData<>(employee);
    }

    /**
     * fire an employee with fireID
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param fireEID Identifier of the employee to fire
     * @return A response object. The response should contain a error message in case of an error
     * <p>
     */
    public void fireEmployee(int fireEID) {
        log.debug("enter fire employee function");
        sc.removeFireEmp(employeeMapper.delete(fireEID),utils.generate_optionals());
        utils.setNeedToUpdateOps(true);
        log.debug("successfully fired employee");
    }

    /**
     * Update an existing employee's name in the system
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param updateEID The identifier of the employee
     * @param newName   The new name
     * @return A response object. The response should contain a error message in case of an error
     */
    public void updateEmployeeName(int updateEID, String newName) {
        log.debug("entered update employee function of: " + updateEID + " to: " + newName);
        employeeMapper.get(updateEID).setName(newName);
        employeeMapper.updateName(updateEID,newName);
        log.debug("successfully updated name to: " + newName);
    }

    /**
     * Update an existing employee's salary in the system
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param updateEID The identifier of the employee
     * @param newSalary The new salary of the employee
     * @return A response object. The response should contain a error message in case of an error
     */
    public void updateEmployeeSalary(int updateEID, int newSalary) {
        log.debug("entered update employee salary function of: " + updateEID + " to: " + newSalary);
        employeeMapper.get(updateEID).setSalary(newSalary);
        employeeMapper.updateSalary(updateEID,newSalary);
        log.debug("successfully updated salary to: " + newSalary);
    }

    /**
     * Update an existing employee's bank account number in the system
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param updateEID        The identifier of the employee
     * @param newAccountNumber The new account number of the employee
     * @return A response object. The response should contain a error message in case of an error
     */
    public void updateEmployeeBANum(int updateEID, int newAccountNumber) {
        log.debug("entered update employee bank account number function of: " + updateEID + " to: " + newAccountNumber);
        employeeMapper.get(updateEID).getBankAccount().setAccountNum(newAccountNumber);
        employeeMapper.updateBankAccountNum(updateEID,newAccountNumber);
        log.debug("successfully updated bank account number to: " + newAccountNumber);
    }

    /**
     * Update an existing employee's bank branch  in the system
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param updateEID The identifier of the employee
     * @param newBranch The new bank branch number of the employee
     * @return A response object. The response should contain a error message in case of an error
     */
    public void updateEmployeeBABranch(int updateEID, int newBranch) {
        log.debug("entered update employee bank branch number function of: " + updateEID + " to: " + newBranch);
        employeeMapper.get(updateEID).getBankAccount().setBankBranch(newBranch);
        employeeMapper.updateBankBranch(updateEID,newBranch);
        log.debug("successfully updated bank branch number to: " + newBranch);
    }

    /**
     * Update an existing employee's bank id number in the system
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param updateEID The identifier of the employee
     * @param newBankID The new bank id number of the employee
     * @return A response object. The response should contain a error message in case of an error
     */
    public void updateEmployeeBAID(int updateEID, int newBankID) {
        log.debug("entered update employee bank ID number function of: " + updateEID + " to: " + newBankID);
        employeeMapper.get(updateEID).getBankAccount().setBankID(newBankID);
        employeeMapper.updateBankID(updateEID,newBankID);
        log.debug("successfully updated bank ID number to: " + newBankID);
    }

    /**
     * Update an existing employee's education fund in the system
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param updateEID        The identifier of the employee
     * @param newEducationFund The education fund of the employee
     * @return A response object. The response should contain a error message in case of an error
     */
    public void updateEmployeeEducationFund(int updateEID, int newEducationFund) {
        log.debug("entered update employee education fund function of: " + updateEID + " to: " + newEducationFund);
        employeeMapper.get(updateEID).getTermsOfEmployment().setEducationFun(newEducationFund);
        employeeMapper.updateEducationFund(updateEID,newEducationFund);
        log.debug("successfully updated education fund to: " + newEducationFund);
    }

    /**
     * Update an existing employee's days off in the system
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param updateEID The identifier of the employee
     * @param newAmount The new amount of days off of the employee
     * @return A response object. The response should contain a error message in case of an error
     */
    public void updateEmployeeDaysOff(int updateEID, int newAmount) {
        log.debug("entered update employee days-off function of: " + updateEID + " to: " + newAmount);
        employeeMapper.get(updateEID).getTermsOfEmployment().setDaysOff(newAmount);
        employeeMapper.updateDaysOff(updateEID,newAmount);
        log.debug("successfully updated days-off to: " + newAmount);
    }

    /**
     * Update an existing employee's sick days in the system
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param updateEID The identifier of the employee
     * @param newAmount The new amount of sick days of the employee
     * @return A response object. The response should contain a error message in case of an error
     */
    public void updateEmployeeSickDays(int updateEID, int newAmount) {
        log.debug("entered update employee sick-days function of: " + updateEID + " to: " + newAmount);
        employeeMapper.get(updateEID).getTermsOfEmployment().setSickDays(newAmount);
        employeeMapper.updateSickDays(updateEID,newAmount);
        log.debug("successfully updated sick-days to: " + newAmount);
    }

    /**
     * Create a new shift with given roles and amount of each role at a specific date {@link LocalDate} and shift-type
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param rolesAmount Map with key: role and value: the amount
     * @param date        The date of the shift to take place
     * @param shiftType   type of the shift
     * @return A response object. The response should contain a error message in case of an error
     */
    public void createShift(Map<String, Integer> rolesAmount, LocalDate date, String shiftType) {
        log.debug("entered create shift function");
        Map<RoleType, List<Business.EmployeePKG.Employee>> optionals = utils.generate_optionals();
        Map<RoleType, Integer> rolesAndAmount = new HashMap<>();
        optionals.forEach((roleType, employees) -> {
            rolesAndAmount.put(roleType,0);
        });
        rolesAmount.forEach((key, value) -> rolesAndAmount.replace(RoleType.valueOf(key), value));
        sc.createShift(rolesAndAmount, date, ShiftType.valueOf(shiftType),optionals);
    }
    /**
     * set the default Shifts with roles and amount of each role
     * Note: Only the personnel manager is allowed to use this functionality
     *
     * @param defaultRolesAmount Map<ShiftType, Map<RoleType,amount>>
     * @return A response object. The response should contain a error message in case of an error
     */
    public void defaultShifts(Map<String, Map<String, Integer>> defaultRolesAmount) {
        log.debug("entered default shifts function");
        Map<ShiftType, Map<RoleType, Integer>> defaults = new HashMap<>();
        log.debug("parsing strings to enum types of map");
        for (Map.Entry<String, Map<String, Integer>> entry : defaultRolesAmount.entrySet()) {
            ShiftType s_type = ShiftType.valueOf(entry.getKey());
            defaults.put(s_type, new HashMap<>());
            Map<RoleType, Integer> rolesAmount = defaults.get(s_type);
            for (Map.Entry<String, Integer> r : entry.getValue().entrySet()) {
                rolesAmount.put(RoleType.valueOf(r.getKey()), r.getValue());
            }
        }
        sc.defaultShifts(defaults);
    }

    /**
     * creates shifts of week from SUNDAY to FRIDAY(FRIDAY only morning) default shifts
     * Note: Only the personnel manager is allowed to use this functionality
     *
     * @return A response object. The response should contain a error message in case of an error
     */
    public ResponseData<List<Shift>> createWeekShifts() {
        log.debug("entered create week shift function");
        List<Business.ShiftPKG.Shift> shifts = sc.createWeekShifts(utils.generate_optionals());
        return new ResponseData<>(utils.convertShifts(shifts));
    }

    /**
     * makes shifts of week from SUNDAY to FRIDAY(FRIDAY only morning) default shifts with all constraints and puts the employees in them
     * Note: Only the personnel manager is allowed to use this functionality
     *
     * @return A response object. The response should contain a error message in case of an error
     */
    public void selfMakeWeekShifts() {
        log.debug("entered self make week shift function");
        sc.selfMakeWeekShifts(utils.generate_optionals());
        log.debug("returned to EmployeePKG successfully");
    }


    /**
     * Gets the details all the shifts and the employees in the shifts including optionals
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @return A response object with a value set to shift containing the employees in it,
     * otherwise the response should contain a error message in case of an error
     */
    public ResponseData<List<Shift>> getShifts(LocalDate until) {
        log.debug("entered get shifts and employees function");
        return new ResponseData<>(utils.convertShifts(sc.getShifts(until,utils.generate_optionals())));
    }

    /**
     * Removes employee with removeEID id from an existing shift with SID
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param SID       Identifier of the shift
     * @param removeEID Identifier of the employee
     * @return A response object. The response should contain a error message in case of an error
     */
    public void removeEmpFromShift(int SID, int removeEID) {
        log.debug("entered remove employee from shift functions");
        sc.removeEmpFromShift(SID,employeeMapper.get(removeEID),utils.generate_optionals());
    }

    /**
     * Adds an employee with  id(addEID) and  his/her role to shift(SID)
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param SID    Identifier of the shift
     * @param addEID Identifier of the employee
     * @param role   The role of the employee will be in the shift
     * @return A response object. The response should contain a error message in case of an error
     */

    public void addEmpToShift(int SID, int addEID, String role) {
        log.debug("entered add employee to shift function");
        sc.addEmpToShift(SID,RoleType.valueOf(role), employeeMapper.get(addEID),utils.generate_optionals());
    }

    /**
     * Update the amount of a specific role in shift with SID id with new amount
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param SID       Identifier of the shift
     * @param role      The role that will be updated in the shift
     * @param newAmount nNew amount of the role
     * @return A response object. The response should contain a error message in case of an error
     */
    public void updateAmountRole(int SID, String role, int newAmount) {
        log.debug("entered update amount in role function");
       sc.updateAmountRole(SID, RoleType.valueOf(role), newAmount,utils.generate_optionals());
        log.debug("updated amount of role");
    }


    /**
     * Adds to a specific employee a role to his list
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param EID  the identifier of the employee to add the role
     * @param role the role
     * @return
     */
    public void addRoleToEmployee(int EID, String role) {
        log.debug("entered add role to employee function");
        //UPDATE DATABASE
        if(!employeeMapper.get(EID).getRole().contains(RoleType.valueOf(role))) {
            employeeMapper.get(EID).getRole().add(RoleType.valueOf(role));
            employeeMapper.addRole(EID,role);
        }
        sc.addToOptionals(employeeMapper.get(EID),RoleType.valueOf(role),utils.generate_optionals());
        utils.setNeedToUpdateOps(true);
        log.debug("successfully added role to role list of employee: "+EID);
    }


    /**
     * get all employees in current branch
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @return A response List. The response should contain a error message in case of an error
     */
    public ResponseData<List<Employee>> getAllEmployees() {
        return new ResponseData<>(utils.convertEmployee(new ArrayList<>(employeeMapper.loadEmployeesInBranch())));
    }
    public boolean optionalIsEmpty(int SID){
        return sc.optionalIsEmpty(SID,utils.generate_optionals());
    }

    public boolean EIDIsOptionForSID(int sid, int eid) {
        return sc.EIDIsOptionForSID(sid,employeeMapper.get(eid),utils.generate_optionals());
    }

    public boolean canWork(int sid, int eid, String role) {
        return sc.canWork(sid,employeeMapper.get(eid),RoleType.valueOf(role),utils.generate_optionals());
    }

    public boolean shiftIsEmpty(int sid) {
        return sc.shiftIsEmpty(sid,utils.generate_optionals());
    }

    public boolean EIDWorkInSID(int sid, int eid) {
        return sc.EIDWorkInSID(sid,employeeMapper.get(eid),utils.generate_optionals());
    }

    public boolean hasShiftManager(LocalDate date, String shiftType) {
        return sc.hasShiftManager(date,ShiftType.valueOf(shiftType));
    }

    public boolean checkIfSIDExist(int sid){
        return sc.checkIfSIDExist(sid,utils.generate_optionals());
    }




}
