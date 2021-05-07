package Business.ApplicationFacade;

import Business.ApplicationFacade.outObjects.Constraint;
import Business.ApplicationFacade.outObjects.Employee;
import Business.ApplicationFacade.outObjects.Shift;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public interface iRegularRoleController {
    /**
     * Logins EID with his/her role of to the system
     *
     * @param EID  The Identification number of the employee
     * @return A response object. The response should contain a error message in case of an error
     */
    void Login(int EID);

    /**
     * Logs out the connected employee
     *
     * @return A response object. The response should contain a error message in case of an error
     */
    void Logout();


    /**
     * Add a constraint of const day-type with type-shift and reason of a employee
     *
     * @param day       Day of the week the employee can't work
     * @param shiftType Type of shift of the day
     * @param reason    The reason of the constraint
     * @return A response object. The response should contain a error message in case of an error
     */
    void addConstConstraint(DayOfWeek day, String shiftType, String reason);

    /**
     * Add a constraint on date {@link LocalDate} with type-shift and reason of a employee
     *
     * @param c_date    The date that the employee can't work
     * @param shiftType Type of shift of the day
     * @param reason    The reason of the constraint
     * @return A response object. The response should contain a error message in case of an error
     */
    void addTempConstraint(LocalDate c_date, String shiftType, String reason);


    /**
     * Remove a constraint with identifier CID that the employee sees
     *
     * @param CID Identifier of the constraint to be removed
     * @return A response object. The response should contain a error message in case of an error
     */
    void removeConstraint(int CID);


    /**
     * Edit/Update an existing constraint with CID with the new reason
     *
     * @param CID       Identifier of the constraint to be updated
     * @param newReason The new reason of the constraint
     * @return A response object. The response should contain a error message in case of an error
     */
    void updateReasonConstraint(int CID, String newReason);

    /**
     * Edit/Update an existing constraint with CID with a different shift-type
     *
     * @param CID     Identifier of the constraint to be updated
     * @param newType The new shift type
     * @return A response object. The response should contain a error message in case of an error
     */
    void updateShiftTypeConstraint(int CID, String newType);


    /**
     * Gets the currently connected employee his/er's shifts
     *
     * @return A response object with a value set to employee containing the details,
     * otherwise the response should contain a error message in case of an error
     */
    ResponseData<List<Shift>> getMyShifts();


    /**
     * Gets the details of a connected employee
     *
     * @return A response object with a value set to the employee
     * otherwise the response should contain a error message in case of an error
     */
    ResponseData<Employee> getEmployeeDetails();

    /**
     * get all current connected user's constraints
     * @return A response List. The response should contain a error message in case of an error
     */
    ResponseData<List<Constraint>> getMyConstraints();

    /**
     * Loads the relevant data of a specific branch with BID identifier
     * Note : the BID is chosen in the first window options before identifying to the system the employee
     *
     * @param BID Identifier of the branch (1-9)
     * @return A response object. The response should contain a error message in case of an error
     */
    void EnterBranch(int BID);

    /**
     * gets all branches available from database
     *
     * @return A response List. The response should contain a error message in case of an error
     */
    ResponseData<List<String>> getBranches();

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
    void createBranch(String code, int newEID, String name, int[] bankDetails, int salary, int[] terms);

    /**
     * gets all role types
     *
     * @return A response String. The response should contain a error message in case of an error
     */
    ResponseData<List<String>> getRoleTypes();



    /**
     * gets all shift types
     *
     * @return A response String. The response should contain a error message in case of an error
     */
    ResponseData<List<String>> getShiftTypes();

    /**
     * checks if default shifts are initialized
     * @return true if yes else false
     */
    ResponseData<Boolean> hasDefaultShifts();

    boolean checkEIDExists(int id);
    Utils getUtils();

    boolean isQualified(int eid, String role);

     boolean checkConstExist(int CID);
    boolean empConnected();
    boolean checkIfMyConst(int cid);
     boolean checkIfShiftExist(LocalDate date, String shiftType);
     boolean checkIfShiftIsClose(LocalDate date, String shiftType);
}
