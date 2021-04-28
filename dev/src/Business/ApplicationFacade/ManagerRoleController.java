package Business.ApplicationFacade;

import Business.ApplicationFacade.outObjects.*;
import Business.Type.*;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.util.*;


public class ManagerRoleController implements iManagerRoleController {
    final static Logger log = Logger.getLogger(ManagerRoleController.class);
    private Map<Integer, Business.EmployeePKG.Employee> employees;
    private Utils utils;


    /**
     * Constructors
     */
    public ManagerRoleController(Utils u) {
        utils = u;
        employees = u.getEmployees();
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
        //UPDATE DATABASE
        employees.put(emp.getEID(), emp);
        utils.getShiftController().addToOptionals(emp,RoleType.valueOf(role));
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
     * Note : when we will build the database there will be a chart of non-employed workers for record that this function
     * will add this employee there.
     * for now only remove from RAM
     */
    public void fireEmployee(int fireEID) {
        log.debug("enter fire employee function");
        //UPDATE DATABASE
        utils.getShiftController().removeFireEmp(employees.remove(fireEID));
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
        //UPDATE DATABASE
        employees.get(updateEID).setName(newName);
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
        //UPDATE DATABASE
        employees.get(updateEID).setSalary(newSalary);
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
        //UPDATE DATABASE
        employees.get(updateEID).getBankAccount().setAccountNum(newAccountNumber);
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
        //UPDATE DATABASE
        employees.get(updateEID).getBankAccount().setBankBranch(newBranch);
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
        //UPDATE DATABASE
        employees.get(updateEID).getBankAccount().setBankID(newBankID);
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
        //UPDATE DATABASE
        employees.get(updateEID).getTermsOfEmployment().setEducationFun(newEducationFund);
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
        //UPDATE DATABASE
        employees.get(updateEID).getTermsOfEmployment().setDaysOff(newAmount);
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
        //UPDATE DATABASE
        employees.get(updateEID).getTermsOfEmployment().setSickDays(newAmount);
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
        Map<RoleType, List<Business.EmployeePKG.Employee>> optionals = new HashMap<>();
        Map<RoleType, Integer> rolesAndAmount = new HashMap<>();
        EnumSet<RoleType> allRoles = EnumSet.allOf(RoleType.class);
        log.debug("initializing roles and amount map with all roles");
        for (RoleType role : allRoles) {
            optionals.put(role, new ArrayList<>());
            rolesAndAmount.put(role, 0);
        }
        log.debug("putting in optionals map all names and roles into array of strings to each role");
        for (Map.Entry<Integer, Business.EmployeePKG.Employee> entry : employees.entrySet()) {
            for (RoleType role : entry.getValue().getRole()) {
                optionals.get(role).add(entry.getValue());
            }
        }
        log.debug("checking map for valid role types and converting to enum");
        for (Map.Entry<String, Integer> entry : rolesAmount.entrySet()) {
            rolesAndAmount.replace(RoleType.valueOf(entry.getKey()), entry.getValue());
        }
        utils.getShiftController().createShift(rolesAndAmount, date, ShiftType.valueOf(shiftType),optionals);
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
        utils.getShiftController().defaultShifts(defaults);
    }

    /**
     * creates shifts of week from SUNDAY to FRIDAY(FRIDAY only morning) default shifts
     * Note: Only the personnel manager is allowed to use this functionality
     *
     * @return A response object. The response should contain a error message in case of an error
     */
    public ResponseData<List<Shift>> createWeekShifts() {
        log.debug("entered create week shift function");
        Map<RoleType, List<Business.EmployeePKG.Employee>> optionals = new HashMap<>();
        EnumSet<RoleType> allRoles = EnumSet.allOf(RoleType.class);
        log.debug("initializing roles and amount map with all roles");
        for (RoleType role : allRoles) {
            optionals.put(role, new ArrayList<>());
        }
        log.debug("initialized.");
        log.debug("putting in optionals map all names and roles into array of strings to each role");
        for (Map.Entry<Integer, Business.EmployeePKG.Employee> entry : employees.entrySet()) {
            for (RoleType role : entry.getValue().getRole()) {
                optionals.get(role).add(entry.getValue());
            }
        }
        log.debug("done.");
        List<Business.ShiftPKG.Shift> shifts = utils.getShiftController().createWeekShifts(optionals);
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
        utils.getShiftController().selfMakeWeekShifts();
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
        return new ResponseData<>(utils.convertShifts(utils.getShiftController().getShifts(until)));
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
        utils.getShiftController().removeEmpFromShift(SID,employees.get(removeEID));
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
        utils.getShiftController().addEmpToShift(SID,RoleType.valueOf(role), employees.get(addEID));
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
        utils.getShiftController().updateAmountRole(SID, RoleType.valueOf(role), newAmount);
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
        if(!employees.get(EID).getRole().contains(RoleType.valueOf(role)))
            employees.get(EID).getRole().add(RoleType.valueOf(role));
        utils.getShiftController().addToOptionals(employees.get(EID),RoleType.valueOf(role));
        log.debug("successfully added role to role list of employee: "+EID);
    }


    /**
     * get all employees in current branch
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @return A response List. The response should contain a error message in case of an error
     */
    public ResponseData<List<Employee>> getAllEmployees() {
        return new ResponseData<>(utils.convertEmployee(new ArrayList<>(employees.values())));
    }
    public boolean optionalIsEmpty(int SID){
        return utils.getShiftController().optionalIsEmpty(SID);
    }

    public boolean EIDIsOptionForSID(int sid, int eid) {
        return utils.getShiftController().EIDIsOptionForSID(sid,employees.get(eid));
    }

    public boolean canWork(int sid, int eid, String role) {
        return utils.getShiftController().canWork(sid,employees.get(eid),RoleType.valueOf(role));
    }

    public boolean shiftIsEmpty(int sid) {
        return utils.getShiftController().shiftIsEmpty(sid);
    }

    public boolean EIDWorkInSID(int sid, int eid) {
        return utils.getShiftController().EIDWorkInSID(sid,employees.get(eid));
    }

    public boolean hasShiftManager(LocalDate date, String shiftType) {
        return utils.getShiftController().hasShiftManager(date,ShiftType.valueOf(shiftType));
    }

    public boolean checkIfSIDExist(int sid){
        return utils.getShiftController().checkIfSIDExist(sid);
    }




}
