package Business.ApplicationFacade;

import Business.ApplicationFacade.outObjects.*;
import Business.EmployeePKG.EmployeeController;
import Business.Type.*;
import org.apache.log4j.Logger;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class EmployeeService implements iEmployeeService {
    private EmployeeController employeeController;
    final static Logger log = Logger.getLogger(EmployeeService.class);

    public EmployeeService() {
        this.employeeController = new EmployeeController();
    }


    /**
     * Logins EID with his/her role of to the system
     *
     * @param EID  The Identification number of the employee
     * @param role The role he takes in "super-lee"
     * @return A response object. The response should contain a error message in case of an error
     */
    public Response Login(int EID, String role) {
        try {
            log.debug("login request service");
            employeeController.Login(EID, role);
            log.debug("successful response from service");
            return new Response();
        } catch (Exception e) {
            log.error(e.getMessage());
            return new Response(e.getMessage());
        }
    }

    /**
     * Logs out the connected employee
     *
     * @return A response object. The response should contain a error message in case of an error
     */
    public Response Logout() {
        try {
            log.debug("logout request service");
            employeeController.Logout();
            log.debug("successful response from service");
            return new Response();
        } catch (Exception e) {
            log.error(e.getMessage());
            return new Response(e.getMessage());
        }
    }

    /**
     * In order to create a new branch in the system, one must enter the unique code and all the details of the PersonnelEmployee of that branch
     *
     * @param code        for now code to create a branch = 00000;
     * @param newEID      identifier of the new PersonnelManager
     * @param name        his name
     * @param bankDetails bank details of the PersonnelEmployee
     * @param salary      his salary
     * @param terms
     * @return A response object. The response should contain a error message in case of an error
     */
    public Response createBranch(String code, int newEID, String name, int[] bankDetails, int salary, int[] terms) {
        try {
            log.debug("create a new branch service");
            employeeController.createBranch(code, newEID, name, bankDetails, salary, terms);
            log.debug("successful response from service");
            return new Response();
        } catch (Exception e) {
            log.error(e.getMessage());
            return new Response(e.getMessage());
        }
    }

    /**
     * Add a constraint of const day-type with type-shift and reason of a employee
     *
     * @param day       Day of the week the employee can't work
     * @param shiftType Type of shift of the day
     * @param reason    The reason of the constraint
     * @return A response object. The response should contain a error message in case of an error
     */
    public ResponseData<Constraint> addConstConstraint(DayOfWeek day, String shiftType, String reason) {
        try {
            log.debug("add a const constraint service");
            Constraint c = new Constraint(employeeController.addConstConstraint(day, shiftType, reason));
            log.debug("successful response data from service");
            return new ResponseData<>(c);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseData<>(e.getMessage());
        }
    }

    /**
     * Add a constraint on date {@link LocalDate} with type-shift and reason of a employee
     *
     * @param c_date    The date that the employee can't work
     * @param shiftType Type of shift of the day
     * @param reason    The reason of the constraint
     * @return A response object. The response should contain a error message in case of an error
     */
    public ResponseData<Constraint> addConstraint(LocalDate c_date, String shiftType, String reason) {
        try {
            log.debug("add a constraint service");
            Constraint c = new Constraint(employeeController.addConstraint(c_date, shiftType, reason));
            log.debug("successful response data from service");
            return new ResponseData<>(c);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseData<>(e.getMessage());
        }
    }

    /**
     * Remove a constraint with identifier CID that the employee sees
     *
     * @param CID Identifier of the constraint to be removed
     * @return A response object. The response should contain a error message in case of an error
     */
    public ResponseData<Constraint> removeConstraint(int CID) {
        try {
            log.debug("remove a constraint service");
            Constraint c = new Constraint(employeeController.removeConstraint(CID));
            log.debug("successful response data from service");
            return new ResponseData<>(c);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseData<>(e.getMessage());
        }
    }

    /**
     * Edit/Update an existing constraint with CID with the new reason
     *
     * @param CID       Identifier of the constraint to be updated
     * @param newReason The new reason of the constraint
     * @return A response object. The response should contain a error message in case of an error
     */
    public Response updateReasonConstraint(int CID, String newReason) {
        try {
            log.debug("update reason in constraint service");
            employeeController.updateReasonConstraint(CID, newReason);
            log.debug("successful response from service");
            return new Response();
        } catch (Exception e) {
            log.error(e.getMessage());
            return new Response(e.getMessage());
        }
    }

    /**
     * Edit/Update an existing constraint with CID with a different shift-type
     *
     * @param CID     Identifier of the constraint to be updated
     * @param newType The new shift type
     * @return A response object. The response should contain a error message in case of an error
     */
    public Response updateShiftTypeConstraint(int CID, String newType) {
        try {
            log.debug("update shift type in constraint service");
            employeeController.updateShiftTypeConstraint(CID, newType);
            log.debug("successful response from service");
            return new Response();
        } catch (Exception e) {
            log.error(e.getMessage());
            return new Response(e.getMessage());
        }
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
        try {
            log.debug("add a new employee service");
            Business.EmployeePKG.Employee emp = employeeController.addEmployee(newEID, name, bankDetails, salary, role, startWorkDate, terms);
            Employee employee = new Employee(emp.getEID(), emp.getName(), emp.getRole(), emp.getBankAccount(), emp.getSalary(), emp.getTermsOfEmployment());
            log.debug("successful response data from service");
            return new ResponseData<>(employee);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseData<>(e.getMessage());
        }
    }

    /**
     * fire an employee with fireID
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param fireEID Identifier of the employee to fire
     * @return A response object. The response should contain a error message in case of an error
     */
    public Response fireEmployee(int fireEID) {
        try {
            log.debug("fire employee service");
            employeeController.fireEmployee(fireEID);
            log.debug("successful response from service");
            return new Response();
        } catch (Exception e) {
            log.error(e.getMessage());
            return new Response(e.getMessage());
        }
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
        try {
            log.debug("update employee name service");
            employeeController.updateEmployeeName(updateEID, newName);
            log.debug("successful response from service");
            return new Response();
        } catch (Exception e) {
            log.error(e.getMessage());
            return new Response(e.getMessage());
        }
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
        try {
            log.debug("update employee salary service");
            employeeController.updateEmployeeSalary(updateEID, newSalary);
            log.debug("successful response from service");
            return new Response();
        } catch (Exception e) {
            log.error(e.getMessage());
            return new Response(e.getMessage());
        }
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
        try {
            log.debug("update employee bank account number service");
            employeeController.updateEmployeeBANum(updateEID, newAccountNumber);
            log.debug("successful response from service");
            return new Response();
        } catch (Exception e) {
            log.error(e.getMessage());
            return new Response(e.getMessage());
        }
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
        try {
            log.debug("update employee bank branch number service");
            employeeController.updateEmployeeBABranch(updateEID, newBranch);
            log.debug("successful response from service");
            return new Response();
        } catch (Exception e) {
            log.error(e.getMessage());
            return new Response(e.getMessage());
        }
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
        try {
            log.debug("update employee bank ID number service");
            employeeController.updateEmployeeBAID(updateEID, newBankID);
            log.debug("successful response from service");
            return new Response();
        } catch (Exception e) {
            log.error(e.getMessage());
            return new Response(e.getMessage());
        }
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
        try {
            log.debug("update employee education fund service");
            employeeController.updateEmployeeEducationFund(updateEID, newEducationFund);
            log.debug("successful response from service");
            return new Response();
        } catch (Exception e) {
            log.error(e.getMessage());
            return new Response(e.getMessage());
        }
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
        try {
            log.debug("update employee days-off service");
            employeeController.updateEmployeeDaysOff(updateEID, newAmount);
            log.debug("successful response from service");
            return new Response();
        } catch (Exception e) {
            log.error(e.getMessage());
            return new Response(e.getMessage());
        }
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
        try {
            log.debug("update employee sick-days service");
            employeeController.updateEmployeeSickDays(updateEID, newAmount);
            log.debug("successful response from service");
            return new Response();
        } catch (Exception e) {
            log.error(e.getMessage());
            return new Response(e.getMessage());
        }
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
    public ResponseData<Shift> createShift(Map<String, Integer> rolesAmount, LocalDate date, String shiftType) {
        try {
            log.debug("create new shift service");
            Shift s = new Shift(employeeController.createShift(rolesAmount, date, ShiftType.valueOf(shiftType)));
            log.debug("successful response from service");
            return new ResponseData<>(s);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseData<>(e.getMessage());
        }
    }

    /**
     * set the default Shifts with roles and amount of each role
     * Note: Only the personnel manager is allowed to use this functionality
     *
     * @param defaultRolesAmount Map<ShiftType, Map<RoleType,amount>>
     * @return A response object. The response should contain a error message in case of an error
     */
    public Response defaultShifts(Map<String, Map<String, Integer>> defaultRolesAmount) {
        try {
            log.debug("default shift request service");
            employeeController.defaultShifts(defaultRolesAmount);
            log.debug("successful response from service");
            return new Response();
        } catch (Exception e) {
            log.error(e.getMessage());
            return new Response(e.getMessage());
        }
    }

    /**
     * creates shifts of week from SUNDAY to FRIDAY(FRIDAY only morning) default shifts
     * Note: Only the personnel manager is allowed to use this functionality
     *
     * @return A response object. The response should contain a error message in case of an error
     */
    public Response createWeekShifts() {
        try {
            log.debug("create week shifts service");
            employeeController.createWeekShifts();
            log.debug("successful response from service");
            return new Response();
        } catch (Exception e) {
            log.error(e.getMessage());
            return new Response(e.getMessage());
        }
    }

    /**
     * makes shifts of week from SUNDAY to FRIDAY(FRIDAY only morning) default shifts with all constraints and puts the employees in them
     * Note: Only the personnel manager is allowed to use this functionality
     *
     * @return A response object. The response should contain a error message in case of an error
     */
    public Response selfMakeWeekShifts() {
        try {
            log.debug("self make week shifts service");
            employeeController.selfMakeWeekShifts();
            log.debug("successful response from service");
            return new Response();
        } catch (Exception e) {
            log.error(e.getMessage());
            return new Response(e.getMessage());
        }
    }


    /**
     * Gets the details of a connected employee
     *
     * @return A response object with a value set to the employee
     * otherwise the response should contain a error message in case of an error
     */
    public ResponseData<Employee> getEmployeeDetails() {
        try {
            log.debug("get employee details service");
            Business.EmployeePKG.Employee emp = employeeController.getEmployeeDetails();
            Employee employee = new Employee(emp.getEID(), emp.getName(), emp.getRole(), emp.getBankAccount(), emp.getSalary(), emp.getTermsOfEmployment());
            log.debug("successful response from service");
            return new ResponseData<>(employee);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseData<>(e.getMessage());
        }
    }

    /**
     * Gets the details all the shifts and the employees in the shifts including optionals
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @return A response object with a value set to shift containing the employees in it,
     * otherwise the response should contain a error message in case of an error
     */
    public ResponseData<List<Shift>> getShifts(LocalDate until) {
        try {
            log.debug("get all shifts and employee service");
            List<Shift> shifts = convertShifts(employeeController.getShifts(until));
            log.debug("successful response from service");
            return new ResponseData<>(shifts);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseData<>(e.getMessage());
        }
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
        try {
            log.debug("remove employee from shift service");
            employeeController.removeEmpFromShift(SID, removeEID);
            log.debug("successful response from service");
            return new Response();
        } catch (Exception e) {
            log.error(e.getMessage());
            return new Response(e.getMessage());
        }
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
        try {
            log.debug("add employee to shift service");
            employeeController.addEmpToShift(SID, addEID, RoleType.valueOf(role));
            log.debug("successful response from service");
            return new Response();
        } catch (Exception e) {
            log.error(e.getMessage());
            return new Response(e.getMessage());
        }
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
        try {
            log.debug("update amount in role to shift service");
            employeeController.updateAmountRole(SID, RoleType.valueOf(role), newAmount);
            log.debug("successful response from service");
            return new Response();
        } catch (Exception e) {
            log.error(e.getMessage());
            return new Response(e.getMessage());
        }
    }

    /**
     * Gets the currently connected employee his/er's shifts
     *
     * @return A response object with a value set to employee containing the details,
     * otherwise the response should contain a error message in case of an error
     */
    public ResponseData<List<Shift>> getMyShifts() {
        try {
            log.debug("get current employee's shifts service");
            List<Shift> l = convertShifts(employeeController.getMyShifts());
            log.debug("successful response from service");
            return new ResponseData<>(l);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseData<>(e.getMessage());
        }
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
        try {
            log.debug("add role to employee service");
            employeeController.addRoleToEmployee(EID, role);
            log.debug("successful response from service");
            return new Response();
        } catch (Exception e) {
            log.error(e.getMessage());
            return new Response(e.getMessage());
        }
    }


    /**
     * Loads the relevant data of a spcific branch with BID identifier
     * Note : the BID is chosen in the first window options before identifying to the system the employee
     *
     * @param BID Identifier of the branch (1-9)
     * @return A response object. The response should contain a error message in case of an error
     */
    public Response loadData(int BID) {
        try {
            log.debug("load data service");
            employeeController.loadData(BID);
            log.debug("successful response from service");
            return new Response();
        } catch (Exception e) {
            log.error(e.getMessage());
            return new Response(e.getMessage());
        }
    }

    /**
     * gets all brnaches available from database
     *
     * @return A response String. The response should contain a error message in case of an error
     */
    public ResponseData<List<String>> getBranches() {
        try {
            log.debug("fetching all branches from database");
            List<String> b = employeeController.getBranches();
            log.debug("successfully got all branches list");
            return new ResponseData<>(b);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseData<>(e.getMessage());
        }
    }

    /**
     * gets all shift types
     *
     * @return A response String. The response should contain a error message in case of an error
     */
    public ResponseData<List<String>> getShiftTypes() {
        try {
            log.debug("fetching all shift types");
            List<String> s = employeeController.getShiftTypes();
            log.debug("successfully got all shift types list");
            return new ResponseData<>(s);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseData<>(e.getMessage());
        }
    }

    /**
     * get all current connected user's constraints
     *
     * @return A response String. The response should contain a error message in case of an error
     */
    public ResponseData<List<Constraint>> getMyConstraints() {
        try {
            log.debug("fetching all constraints");
            List<Constraint> c = convertConstrains(employeeController.getMyConstraints());
            log.debug("successfully got all constrains");
            return new ResponseData<>(c);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseData<>(e.getMessage());
        }
    }

    /**
     * get all employees in current branch
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @return A response List. The response should contain a error message in case of an error
     */
    public ResponseData<List<Employee>> getAllEmployees() {
        try {
            log.debug("fetching all employees");
            List<Employee> emp = convertEmployee(employeeController.getAllEmployees());
            log.debug("successfully got all employees");
            return new ResponseData<>(emp);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseData<>(e.getMessage());
        }
    }

    /**
     * checks if default shifts are initialized
     *
     * @return true if yes else false
     */
    public ResponseData<Boolean> hasDefaultShifts() {
        try {
            log.debug("has defaults questions service");
            boolean ans = employeeController.hasDefaultShifts();
            log.debug("successfully answer");
            return new ResponseData<>(ans);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseData<>(e.getMessage());
        }
    }

    /**
     * gets all role types
     *
     * @return A response String. The response should contain a error message in case of an error
     */
    public ResponseData<List<String>> getRoleTypes() {
        try {
            log.debug("fetching all role types");
            List<String> rolesT = employeeController.getRoleTypes();
            log.debug("successfully got all role types");
            return new ResponseData<>(rolesT);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseData<>(e.getMessage());
        }
    }


    /**
     * Convert Help Functions of lists
     */
    private List<Shift> convertShifts(List<Business.ShiftPKG.Shift> allShifts) {
        log.debug("converting shift of business layer to out objects list");
        List<Shift> shifts = new ArrayList<>();
        allShifts.forEach(s -> {
            shifts.add(new Shift(s));
        });
        log.debug("Done.");
        return shifts;
    }

    private List<Constraint> convertConstrains(List<Business.ShiftPKG.Constraint> allConstraints) {
        log.debug("converting constraints of business layer to out objects list");
        List<Constraint> constraints = new ArrayList<>();
        allConstraints.forEach(c -> {
            constraints.add(new Constraint(c));
        });
        log.debug("Done.");
        return constraints;
    }

    private List<Employee> convertEmployee(List<Business.EmployeePKG.Employee> allEmployees) {
        log.debug("converting employees of business layer to out objects list");
        List<Employee> employees = new ArrayList<>();
        allEmployees.forEach(e -> {
            employees.add(new Employee(e));
        });
        log.debug("Done.");
        return employees;
    }
}
