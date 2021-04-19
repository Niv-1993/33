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
    public ManagerRoleController() {
    }

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
        String check = utils.checkEmpDetails(newEID, name, bankDetails, terms, salary, role);
        if (!check.isEmpty()) return new ResponseData<>(check);
        String checkWorking = utils.checkWorking(newEID);
        if (!checkWorking.isEmpty()) return new ResponseData<>(checkWorking);
        Business.EmployeePKG.Employee emp = new Business.EmployeePKG.Employee(newEID, name, bankDetails, salary, RoleType.valueOf(role), startWorkDate, terms);
        Employee employee = new Employee(emp);
        //UPDATE DATABASE
        employees.put(emp.getEID(), emp);
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
    public Response fireEmployee(int fireEID) {
        log.debug("enter fire employee function");
        String checkWorking = utils.checkWorking(fireEID);
        if (!checkWorking.isEmpty()) {
            log.error("EID is not found");
            return new Response(checkWorking);
        }
        //UPDATE DATABASE
        utils.getShiftController().removeFireEmp(employees.remove(fireEID));
        log.debug("successfully fired employee");
        return new Response();
    }

    /**
     * Update an existing employee's name in the system
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param updateEID The identifier of the employee
     * @param newName   The new name
     * @return A response object. The response should contain a error message in case of an error
     */
    public Response updateEmployeeName(int updateEID, String newName) {
        log.debug("entered update employee function of: " + updateEID + " to: " + newName);
        String checkWorking = utils.checkWorking(updateEID);
        if(!checkWorking.isEmpty()){
            log.error("user with id: " + updateEID + " is not in map of employees");
            return new Response(checkWorking);
        }
        if(!utils.checkName(newName)) return new Response("name " + newName + " is not alphabetical");
        //UPDATE DATABASE
        employees.get(updateEID).setName(newName);
        log.debug("successfully updated name to: " + newName);
        return new Response();
    }

    /**
     * Update an existing employee's salary in the system
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param updateEID The identifier of the employee
     * @param newSalary The new salary of the employee
     * @return A response object. The response should contain a error message in case of an error
     */
    public Response updateEmployeeSalary(int updateEID, int newSalary) {
        log.debug("entered update employee salary function of: " + updateEID + " to: " + newSalary);
        String checkWorking = utils.checkWorking(updateEID);
        if(!checkWorking.isEmpty()){
            log.error("user with id: " + updateEID + " is not in map of employees");
            return new Response(checkWorking);
        }
        if(!utils.checkSalary(newSalary)) return new Response("Invalid salary input (negative or zero)");
        //UPDATE DATABASE
        employees.get(updateEID).setSalary(newSalary);
        log.debug("successfully updated salary to: " + newSalary);
        return new Response();
    }

    /**
     * Update an existing employee's bank account number in the system
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param updateEID        The identifier of the employee
     * @param newAccountNumber The new account number of the employee
     * @return A response object. The response should contain a error message in case of an error
     */
    public Response updateEmployeeBANum(int updateEID, int newAccountNumber) {
        log.debug("entered update employee bank account number function of: " + updateEID + " to: " + newAccountNumber);
        String checkW = utils.checkWorking(updateEID);
        if(!checkW.isEmpty()) return new Response(checkW);
        if(newAccountNumber <=0) return new Response("Invalid account number");
        //UPDATE DATABASE
        employees.get(updateEID).getBankAccount().setAccountNum(newAccountNumber);
        log.debug("successfully updated bank account number to: " + newAccountNumber);
        return new Response();
    }

    /**
     * Update an existing employee's bank branch  in the system
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param updateEID The identifier of the employee
     * @param newBranch The new bank branch number of the employee
     * @return A response object. The response should contain a error message in case of an error
     */
    public Response updateEmployeeBABranch(int updateEID, int newBranch) {
        log.debug("entered update employee bank branch number function of: " + updateEID + " to: " + newBranch);
        String checkW = utils.checkWorking(updateEID);
        if(!checkW.isEmpty()) return new Response(checkW);
        if(newBranch <=0) return new Response("Invalid branch number");
        //UPDATE DATABASE
        employees.get(updateEID).getBankAccount().setBankBranch(newBranch);
        log.debug("successfully updated bank branch number to: " + newBranch);
        return new Response();
    }

    /**
     * Update an existing employee's bank id number in the system
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param updateEID The identifier of the employee
     * @param newBankID The new bank id number of the employee
     * @return A response object. The response should contain a error message in case of an error
     */
    public Response updateEmployeeBAID(int updateEID, int newBankID) {
        log.debug("entered update employee bank ID number function of: " + updateEID + " to: " + newBankID);
        String checkW = utils.checkWorking(updateEID);
        if(!checkW.isEmpty()) return new Response(checkW);
        if(newBankID <=0) return new Response("Invalid bank ID number");
        //UPDATE DATABASE
        employees.get(updateEID).getBankAccount().setBankID(newBankID);
        log.debug("successfully updated bank ID number to: " + newBankID);
        return new Response();
    }

    /**
     * Update an existing employee's education fund in the system
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param updateEID        The identifier of the employee
     * @param newEducationFund The education fund of the employee
     * @return A response object. The response should contain a error message in case of an error
     */
    public Response updateEmployeeEducationFund(int updateEID, int newEducationFund) {
        log.debug("entered update employee education fund function of: " + updateEID + " to: " + newEducationFund);
        String checkW = utils.checkWorking(updateEID);
        if(!checkW.isEmpty()) return new Response(checkW);
        if(newEducationFund <=0) return new Response("Invalid education fund number");
        //UPDATE DATABASE
        employees.get(updateEID).getTermsOfEmployment().setEducationFun(newEducationFund);
        log.debug("successfully updated education fund to: " + newEducationFund);
        return new Response();
    }

    /**
     * Update an existing employee's days off in the system
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param updateEID The identifier of the employee
     * @param newAmount The new amount of days off of the employee
     * @return A response object. The response should contain a error message in case of an error
     */
    public Response updateEmployeeDaysOff(int updateEID, int newAmount) {
        log.debug("entered update employee days-off function of: " + updateEID + " to: " + newAmount);
        String checkW = utils.checkWorking(updateEID);
        if(!checkW.isEmpty()) return new Response(checkW);
        if(newAmount < 0) return new Response("Invalid days-off amount");
        //UPDATE DATABASE
        employees.get(updateEID).getTermsOfEmployment().setDaysOff(newAmount);
        log.debug("successfully updated days-off to: " + newAmount);
        return new Response();
    }

    /**
     * Update an existing employee's sick days in the system
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param updateEID The identifier of the employee
     * @param newAmount The new amount of sick days of the employee
     * @return A response object. The response should contain a error message in case of an error
     */
    public Response updateEmployeeSickDays(int updateEID, int newAmount) {
        log.debug("entered update employee sick-days function of: " + updateEID + " to: " + newAmount);
        String checkW = utils.checkWorking(updateEID);
        if(!checkW.isEmpty()) return new Response(checkW);
        if(newAmount < 0) return new Response("Invalid sick-days amount");
        //UPDATE DATABASE
        employees.get(updateEID).getTermsOfEmployment().setSickDays(newAmount);
        log.debug("successfully updated sick-days to: " + newAmount);
        return new Response();
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
    public Response createShift(Map<String, Integer> rolesAmount, LocalDate date, String shiftType) {
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
            rolesAndAmount.put(RoleType.valueOf(entry.getKey()), entry.getValue());
        }
        if(utils.isInEnum(shiftType,ShiftType.class)) return new Response("Invalid shift type");
        String res = utils.getShiftController().createShift(rolesAndAmount, date, ShiftType.valueOf(shiftType),optionals);
        return res.isEmpty()? new Response() : new Response(res);
    }

    /**
     * set the default Shifts with roles and amount of each role
     * Note: Only the personnel manager is allowed to use this functionality
     *
     * @param defaultRolesAmount Map<ShiftType, Map<RoleType,amount>>
     * @return A response object. The response should contain a error message in case of an error
     */
    public Response defaultShifts(Map<String, Map<String, Integer>> defaultRolesAmount) {
        log.debug("entered default shifts function");
        Map<ShiftType, Map<RoleType, Integer>> defaults = new HashMap<>();
        log.debug("parsing strings to enum types of map");
        for (Map.Entry<String, Map<String, Integer>> entry : defaultRolesAmount.entrySet()) {
            if(!utils.isInEnum(entry.getKey(), ShiftType.class)) return new Response("Invalid shift type");
            ShiftType s_type = ShiftType.valueOf(entry.getKey());
            defaults.put(s_type, new HashMap<>());
            Map<RoleType, Integer> rolesAmount = defaults.get(s_type);
            for (Map.Entry<String, Integer> r : entry.getValue().entrySet()) {
                if(!utils.isInEnum(r.getKey(), RoleType.class)) return new Response("Invalid role type");
                rolesAmount.put(RoleType.valueOf(r.getKey()), r.getValue());
            }
        }
        String res = utils.getShiftController().defaultShifts(defaults);
        return res.isEmpty()? new Response() : new Response(res);
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
    public Response selfMakeWeekShifts() {
        log.debug("entered self make week shift function");
        utils.getShiftController().selfMakeWeekShifts();
        log.debug("returned to EmployeePKG successfully");
        return new Response();
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
    public Response removeEmpFromShift(int SID, int removeEID) {
        log.debug("entered remove employee from shift functions");
        String checkWorking = utils.checkWorking(removeEID);
        if(!checkWorking.isEmpty()) return new Response(checkWorking);
        String res = utils.getShiftController().removeEmpFromShift(SID,employees.get(removeEID));
        return res.isEmpty()? new Response() : new Response(res);
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
    public Response addEmpToShift(int SID, int addEID, String role) {
        log.debug("entered add employee to shift function");
        String checkWorking = utils.checkWorking(addEID);
        if(!checkWorking.isEmpty()) return new Response(checkWorking);
        if(!utils.isInEnum(role,RoleType.class)) return new Response("Invalid role type");
        String res = utils.getShiftController().addEmpToShift(SID,RoleType.valueOf(role), employees.get(addEID));
        return res.isEmpty()? new Response() : new Response(res);
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
    public Response updateAmountRole(int SID, String role, int newAmount) {
        log.debug("entered update amount in role function");
        if(!utils.isInEnum(role,RoleType.class)) return new Response("Invalid role type");
        String res = utils.getShiftController().updateAmountRole(SID, RoleType.valueOf(role), newAmount);
        return res.isEmpty()? new Response() : new Response(res);
    }


    /**
     * Adds to a specific employee a role to his list
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param EID  the identifier of the employee to add the role
     * @param role the role
     * @return
     */
    public Response addRoleToEmployee(int EID, String role) {
        log.debug("entered add role to employee function");
        if(!utils.isInEnum(role,RoleType.class)) return new Response("Invalid role type");
        String working = utils.checkWorking(EID);
        if(!working.isEmpty()) return new Response(working);
        //UPDATE DATABASE
        if(!employees.get(EID).getRole().contains(RoleType.valueOf(role)))
            employees.get(EID).getRole().add(RoleType.valueOf(role));
        utils.getShiftController().addToOptionals(employees.get(EID),RoleType.valueOf(role));
        log.debug("successfully added role to role list of employee: "+EID);
        return new Response();
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




}
