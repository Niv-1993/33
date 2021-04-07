package Business.ApplicationFacade;

import Business.ApplicationFacade.outObjects.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * This is the Employee module Service interface - done by Dor Elad & Niv Dan
 * This interface includes only functions that are needed for part 1 before uniting with other module
 * ShiftType , DayOfWeek , RoleType are Strings that will be converted into enums and passed on
 * RoleType = {PersonnelManager, BranchManager, ShiftManager, Driver, Cashier,...etc.}
 * DayOfWeek = {SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THIRSTY, FRIDAY, SATURDAY }
 * ShiftType = {Morning, Night}
 */

public interface iEmployeeService {

    /**
     * Logins EID with his/her role of to the system
     *
     * @param EID  The Identification number of the employee
     * @param role The role he takes in "super-lee"
     * @return A response object. The response should contain a error message in case of an error
     */
    Response Login(int EID, String role);

    /**
     * Logs out the connected employee
     *
     * @return A response object. The response should contain a error message in case of an error
     */
    Response Logout();

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
    Response createBranch(String code, int newEID, String name, int[] bankDetails, int salary, int[] terms);

    /**
     * Add a constraint of const day-type with type-shift and reason of a employee
     *
     * @param day       Day of the week the employee can't work
     * @param shiftType Type of shift of the day
     * @param reason    The reason of the constraint
     * @return A response object. The response should contain a error message in case of an error
     */
    ResponseData<Constraint> addConstConstraint(DayOfWeek day, String shiftType, String reason);

    /**
     * Add a constraint on date {@link LocalDate} with type-shift and reason of a employee
     *
     * @param c_date    The date that the employee can't work
     * @param shiftType Type of shift of the day
     * @param reason    The reason of the constraint
     * @return A response object. The response should contain a error message in case of an error
     */
    ResponseData<Constraint> addConstraint(LocalDate c_date, String shiftType, String reason);

    /**
     * Remove a constraint with identifier CID that the employee sees
     *
     * @param CID Identifier of the constraint to be removed
     * @return A response object. The response should contain a error message in case of an error
     */
    ResponseData<Constraint> removeConstraint(int CID);

    /**
     * Edit/Update an existing constraint with CID with the new reason
     *
     * @param CID       Identifier of the constraint to be updated
     * @param newReason The new reason of the constraint
     * @return A response object. The response should contain a error message in case of an error
     */
    Response updateReasonConstraint(int CID, String newReason);

    /**
     * Edit/Update an existing constraint with CID with a different shift-type
     *
     * @param CID     Identifier of the constraint to be updated
     * @param newType The new shift type
     * @return A response object. The response should contain a error message in case of an error
     */
    Response updateShiftTypeConstraint(int CID, String newType);

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
    ResponseData<Employee> addEmployee(int newEID, String name, int[] bankDetails, int salary, String role, LocalDate startWorkDate, int[] terms);

    /**
     * fire an employee with fireID
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param fireEID Identifier of the employee to fire
     * @return A response object. The response should contain a error message in case of an error
     */
    Response fireEmployee(int fireEID);

    /**
     * Update an existing employee's name in the system
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param updateEID The identifier of the employee
     * @param newName   The new name
     * @return A response object. The response should contain a error message in case of an error
     */
    Response updateEmployeeName(int updateEID, String newName);

    /**
     * Update an existing employee's salary in the system
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param updateEID The identifier of the employee
     * @param newSalary The new salary of the employee
     * @return A response object. The response should contain a error message in case of an error
     */
    Response updateEmployeeSalary(int updateEID, int newSalary);

    /**
     * Update an existing employee's bank account number in the system
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param updateEID        The identifier of the employee
     * @param newAccountNumber The new account number of the employee
     * @return A response object. The response should contain a error message in case of an error
     */
    Response updateEmployeeBANum(int updateEID, int newAccountNumber);

    /**
     * Update an existing employee's bank branch  in the system
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param updateEID The identifier of the employee
     * @param newBranch The new bank branch number of the employee
     * @return A response object. The response should contain a error message in case of an error
     */
    Response updateEmployeeBABranch(int updateEID, int newBranch);

    /**
     * Update an existing employee's bank id number in the system
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param updateEID The identifier of the employee
     * @param newBankID The new bank id number of the employee
     * @return A response object. The response should contain a error message in case of an error
     */
    Response updateEmployeeBAID(int updateEID, int newBankID);

    /**
     * Update an existing employee's education fund in the system
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param updateEID        The identifier of the employee
     * @param newEducationFund The education fund of the employee
     * @return A response object. The response should contain a error message in case of an error
     */
    Response updateEmployeeEducationFund(int updateEID, int newEducationFund);

    /**
     * Update an existing employee's days off in the system
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param updateEID The identifier of the employee
     * @param newAmount The new amount of days off of the employee
     * @return A response object. The response should contain a error message in case of an error
     */
    Response updateEmployeeDaysOff(int updateEID, int newAmount);

    /**
     * Update an existing employee's sick days in the system
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param updateEID The identifier of the employee
     * @param newAmount The new amount of sick days of the employee
     * @return A response object. The response should contain a error message in case of an error
     */
    Response updateEmployeeSickDays(int updateEID, int newAmount);

    /**
     * Create a new shift with given roles and amount of each role at a specific date {@link LocalDate} and shift-type
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param rolesAmount Map with key: role and value: the amount
     * @param date        The date of the shift to take place
     * @param shiftType   type of the shift
     * @returnA response object with a value set to the employee
     * *         otherwise the response should contain a error message in case of an error
     */
    ResponseData<Shift> createShift(Map<String, Integer> rolesAmount, LocalDate date, String shiftType);

    /**
     * set the default Shifts with roles and amount of each role
     * Note: Only the personnel manager is allowed to use this functionality
     *
     * @param defaultRolesAmount Map<ShiftType, Map<RoleType,amount>>
     * @return A response object. The response should contain a error message in case of an error
     */
    Response defaultShifts(Map<String, Map<String, Integer>> defaultRolesAmount);

    /**
     * creates shifts of week from SUNDAY to FRIDAY(FRIDAY only morning) default shifts
     * Note: Only the personnel manager is allowed to use this functionality
     *
     * @return A response object. The response should contain a error message in case of an error
     */
    Response createWeekShifts();

    /**
     * makes shifts of week from SUNDAY to FRIDAY(FRIDAY only morning) default shifts with all constraints and puts the employees in them
     * Note: Only the personnel manager is allowed to use this functionality
     *
     * @return A response object. The response should contain a error message in case of an error
     */
    Response selfMakeWeekShifts();

    /**
     * Gets the details of a connected employee
     *
     * @return A response object with a value set to the employee
     * otherwise the response should contain a error message in case of an error
     */
    ResponseData<Employee> getEmployeeDetails();

    /**
     * Gets the details all the shifts and the employees in the shifts including optionals
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @return A response object with a value set to shift containing the employees in it,
     * otherwise the response should contain a error message in case of an error
     */
    ResponseData<List<Shift>> getShifts(LocalDate until);

    /**
     * Removes employee with removeEID id from an existing shift with SID
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param SID       Identifier of the shift
     * @param removeEID Identifier of the employee
     * @return A response object. The response should contain a error message in case of an error
     */
    Response removeEmpFromShift(int SID, int removeEID);

    /**
     * Adds an employee with  id(addEID) and  his/her role to shift(SID)
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param SID    Identifier of the shift
     * @param addEID Identifier of the employee
     * @param role   The role of the employee will be in the shift
     * @return A response object. The response should contain a error message in case of an error
     */
    Response addEmpToShift(int SID, int addEID, String role);

    /**
     * Update the amount of a specific role in shift with SID id with new amount
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param SID       Identifier of the shift
     * @param role      The role that will be updated in the shift
     * @param newAmount new amount of the role
     * @return A response object. The response should contain a error message in case of an error
     */
    Response updateAmountRole(int SID, String role, int newAmount);

    /**
     * Gets the currently connected employee his/er's shifts
     *
     * @return A response object with a value set to employee containing the details,
     * otherwise the response should contain a error message in case of an error
     */
    ResponseData<List<Shift>> getMyShifts();

    /**
     * Adds to a specific employee a role to his list
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param EID  the identifier of the employee to add the role
     * @param role the role
     * @return
     */
    Response addRoleToEmployee(int EID, String role);

    /**
     * Loads the relevant data of a spcific branch with BID identifier
     * Note : the BID is chosen in the first window options before identifying to the system the employee
     *
     * @param BID Identifier of the branch (1-9)
     * @return A response object. The response should contain a error message in case of an error
     */
    Response loadData(int BID);

    /**
     * gets all branches available from database
     *
     * @return A response List. The response should contain a error message in case of an error
     */
    ResponseData<List<String>> getBranches();

    /**
     * gets all shift types
     *
     * @return A response String. The response should contain a error message in case of an error
     */
    ResponseData<List<String>> getShiftTypes();

    /**
     * get all current connected user's constraints
     * @return A response List. The response should contain a error message in case of an error
     */
    ResponseData<List<Constraint>> getMyConstraints();

    /**
     * get all employees in current branch
     *  Note : Only the personnel manager is allowed to use this functionality
     * @return  A response List. The response should contain a error message in case of an error
     */
    ResponseData<List<Employee>> getAllEmployees();

    /**
     * checks if default shifts are initialized
     * @return true if yes else false
     */
    ResponseData<Boolean> hasDefaultShifts();

    /**
     * gets all role types
     *
     * @return A response String. The response should contain a error message in case of an error
     */
    ResponseData<List<String>> getRoleTypes();
}
