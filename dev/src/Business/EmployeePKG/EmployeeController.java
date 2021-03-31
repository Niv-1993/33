package Business.EmployeePKG;

import Business.Type.*;
import org.apache.log4j.Logger;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;


public class EmployeeController {
    final static Logger log = Logger.getLogger(EmployeeController.class);
    private int currBranchID;
    private int currConnectedEmpID;
    private RoleType currConnectedEmpRole;
    private Map<Integer, Employee> employees;
    private ShiftController shiftController;

    public EmployeeController() {
        employees = new HashMap<>();
        shiftController = new ShiftContorller();
    }

    /**
     * Logins EID with role of  enum role-type {@link RoleType} to the system
     *
     * @param EID  The Identification number of the employee
     * @param role The role he takes in "super-lee"
     */
    public void Login(int EID, RoleType role) throws Exception {
        checkWorking(EID);
        if (!employees.get(EID).isQualified(role))
            throw new Exception("Employee is unqualified to this role");
        currConnectedEmpID = EID;
        currConnectedEmpRole = role;
    }


    /**
     * logs out the current connected employee from the system
     */
    public void Logout() throws Exception {
        if (currConnectedEmpID == -1)
            throw new Exception("No one is logged in");
        currConnectedEmpID = -1;
        currConnectedEmpRole = null;
    }

    public void createBranch(String code, int newEID, String name, int[] bankDetails, int salary, int[] terms) throws Exception {
        if (!code.equals("00000"))
            throw new Exception("Access denied!");
        //create new branch in database
        Employee personnelM = new PersonnelManager(newEID, name, bankDetails, salary, RoleType.PerssonelManger, LocalDate.now(), terms);
        //add personnelM to database
    }

    /**
     * Add a constraint of const day {@link DayOfWeek} with type-shift {@link ShiftType}  and reason of a employee
     *
     * @param day       Day of the week the employee can't work
     * @param shiftType Type of shift of the day
     * @param reason    The reason of the constraint
     */
    public Business.ShiftPKG.Constraint addConstConstraint(DayOfWeek day, ShiftType shiftType, String reason) {
        return employees.get(currConnectedEmpID).addConstConstraint(day, shiftType, reason, shiftController);
    }

    /**
     * Add a constraint on date {@link LocalDate} with type-shift {@link ShiftType}  and reason of a employee
     *
     * @param c_date    The date that the employee can't work
     * @param shiftType Type of shift of the day
     * @param reason    The reason of the constraint
     */
    public Business.ShiftPKG.Constraint addConstraint(LocalDate c_date, ShiftType shiftType, String reason) {
        return employees.get(currConnectedEmpID).addConstraint(c_date, shiftType, reason, shiftController);
    }

    /**
     * Remove a constraint with identifier CID that the employee sees
     *
     * @param CID Identifier of the constraint to be removed
     */
    public Business.ShiftPKG.Constraint removeConstraint(int CID) {
        return employees.get(currConnectedEmpID).removeConstraint(CID, shiftController);
    }

    /**
     * Edit/Update an existing constraint with CID with the new reason
     *
     * @param CID       Identifier of the constraint to be updated
     * @param newReason The new reason of the constraint
     */
    public void updateReasonConstraint(int CID, String newReason) {
        employees.get(currConnectedEmpID).updateReasonConstraint(CID, newReason, shiftController);
    }

    /**
     * Edit/Update an existing constraint with CID with a different shift-type {@link ShiftType}
     *
     * @param CID     Identifier of the constraint to be updated
     * @param newType The new shift type
     */
    public void updateShiftTypeConstraint(int CID, ShiftType newType) {
        employees.get(currConnectedEmpID).updateShiftTypeConstraint(CID, newType, shiftController);
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
     * @param salary        THe salay of the new employee
     * @param role          The role of the new employee
     * @param startWorkDate THe starting date of the new employee {@link LocalDate}
     * @param terms         Terms of employments :
     *                      terms[0] -> education fund
     *                      terms[1] -> days off
     *                      terms[2] -> sick days
     */
    public void addEmployee(int newEID, String name, int[] bankDetails, int salary, RoleType role, LocalDate startWorkDate, int[] terms) throws Exception {
        Employee added = employees.get(currConnectedEmpID).addEmployee(newEID, name, bankDetails, salary, role, startWorkDate, terms, employees);
        employees.put(added.getEID(), added);
    }

    /**
     * fire an employee with fireID
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param fireEID Identifier of the employee to fire
     */
    public void fireEmployee(int fireEID) throws Exception {
        employees.get(currConnectedEmpID).fireEmployee(fireEID, employees);
        employees.remove(fireEID);
    }

    /**
     * Update an existing employee's name in the system
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param updateEID The identifier of the employee
     * @param newName   The new name
     */
    public void updateEmployeeName(int updateEID, String newName) throws Exception {
        checkWorking(updateEID);
        employees.get(currConnectedEmpID).updateEmployeeName(employees.get(updateEID), newName);
    }

    /**
     * Update an existing employee's salary in the system
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param updateEID The identifier of the employee
     * @param newSalary The new salary of the employee
     */
    public void updateEmployeeSalary(int updateEID, int newSalary) throws Exception {
        checkWorking(updateEID);
        employees.get(currConnectedEmpID).updateEmployeeSalary(employees.get(updateEID), newSalary);
    }

    /**
     * Update an existing employee's bank account number in the system
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param updateEID        The identifier of the employee
     * @param newAccountNumber The new account number of the employee
     */
    public void updateEmployeeBANum(int updateEID, int newAccountNumber) throws Exception {
        checkWorking(updateEID);
        employees.get(currConnectedEmpID).updateEmployeeBANum(employees.get(updateEID), newAccountNumber);
    }

    /**
     * Update an existing employee's bank branch  in the system
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param updateEID The identifier of the employee
     * @param newBranch The new bank branch number of the employee
     */
    public void updateEmployeeBABranch(int updateEID, int newBranch) throws Exception {
        checkWorking(updateEID);
        employees.get(currConnectedEmpID).updateEmployeeBABranch(employees.get(updateEID), newBranch);
    }

    /**
     * Update an existing employee's bank id number in the system
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param updateEID The identifier of the employee
     * @param newBankID The new bank id number of the employee
     */
    public void updateEmployeeBAID(int updateEID, int newBankID) throws Exception {
        checkWorking(updateEID);
        employees.get(currConnectedEmpID).updateEmployeeBAID(employees.get(updateEID), newBankID);
    }

    /**
     * Update an existing employee's education fund in the system
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param updateEID        The identifier of the employee
     * @param newEducationFund The education fund of the employee
     */
    public void updateEmployeeEducationFund(int updateEID, int newEducationFund) throws Exception {
        checkWorking(updateEID);
        employees.get(currConnectedEmpID).updateEmployeeEducationFund(employees.get(updateEID), newEducationFund);
    }

    /**
     * Update an existing employee's days off in the system
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param updateEID The identifier of the employee
     * @param newAmount The new amount of days off of the employee
     */
    public void updateEmployeeDaysOff(int updateEID, int newAmount) throws Exception {
        checkWorking(updateEID);
        employees.get(currConnectedEmpID).updateEmployeeDaysOff(employees.get(updateEID), newAmount);
    }

    /**
     * Update an existing employee's sick days in the system
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param updateEID The identifier of the employee
     * @param newAmount The new amount of sick days of the employee
     */
    public void updateEmployeeSickDays(int updateEID, int newAmount) throws Exception {
        checkWorking(updateEID);
        employees.get(currConnectedEmpID).updateEmployeeSickDays(employees.get(updateEID), newAmount);
    }

    /**
     * Create a new shift with given roles and amount of each role at a specific date {@link LocalDate} and shift-type {@link ShiftType}
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param rolesAmount Map with key: role and value: the amount
     * @param date        The date of the shift to take place
     * @param shiftType   type of the shift
     */
    public void createShift(Map<String, Integer> rolesAmount, LocalDate date, ShiftType shiftType) throws Exception {
        Map<RoleType, List<String[]>> optionals = new HashMap<>();
        Map<RoleType, Integer> rolesAndAmount = new HashMap<>();
        EnumSet<RoleType> allRoles = EnumSet.allOf(RoleType.class);
        for (RoleType role : allRoles) {
            optionals.put(role, new ArrayList<>());
            rolesAndAmount.put(role, 0);
        }
        for (Map.Entry<Integer, Employee> entry : employees.entrySet()) {
            String[] pairIDName = new String[]{Integer.toString(entry.getKey()), entry.getValue().getName()};
            for (RoleType role : entry.getValue().getRole()) {
                optionals.get(role).add(pairIDName);
            }
        }
        for (Map.Entry<String, Integer> entry : rolesAmount.entrySet()) {
            rolesAndAmount.put(RoleType.valueOf(entry.getKey()), entry.getValue() + 1);
        }
        employees.get(currConnectedEmpID).createShift(rolesAndAmount, date, shiftType, optionals, shiftController);
    }

    /**
     * Gets the details of a connected employee
     *
     * @return the current connected employee object
     */
    public Employee getEmployeeDetails() {
        return employees.get(currConnectedEmpID);
    }

    /**
     * Gets the details all the shifts and the employees in the shifts including optionals
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @return A list of all the shifts and the employees in every shift (names & roles)
     */
    public List<Business.ShiftPkG.Shift> getShiftsAndEmployees() throws Exception {
        return employees.get(currConnectedEmpID).getShiftsAndEmployees(shiftController);
    }

    /**
     * Removes employee with removeEID id from an existing shift with SID
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param SID       Identifier of the shift
     * @param removeEID Identifier of the employee
     */
    public void removeEmpFromShift(int SID, int removeEID) throws Exception {
        employees.get(currConnectedEmpID).removeEmpFromShift(SID, removeEID, shiftController);
    }

    /**
     * Adds an employee with addEID id to shift with SID id in role {@link RoleType}
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param SID    Identifier of the shift
     * @param addEID Identifier of the employee
     * @param role   The role of the employee will be in the shift
     */
    public void addEmpToShift(int SID, int addEID, RoleType role) throws Exception {
        employees.get(currConnectedEmpID).addEmpToShift(SID, addEID, role, employees.get(addEID).getName(), shiftController);
    }

    /**
     * Update the amount of role {@link RoleType} in shift with SID id with new amount
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param SID       Identifier of the shift
     * @param role      The role that will be updated in the shift
     * @param newAmount nNew amount of the role
     */
    public void updateAmountRole(int SID, RoleType role, int newAmount) throws Exception {
        employees.get(currConnectedEmpID).updateAmountRole(SID, role, newAmount, shiftController);
    }

    /**
     * gets the current connected employee's shifts
     *
     * @return A list of constraints of the current connected employee
     */
    public List<Business.ShiftPkG.Shift> getOnlyEmployeeShifts() {
        return employees.get(currConnectedEmpID).getOnlyEmployeeShifts(shiftController);
    }

    /**
     * Gets the currently connected employee his/er's constraints
     *
     * @return A list of constraints of the current connected employee
     */
    public List<Business.ShiftPkG.Constraint> getOnlyEmployeeConstraints() {
        return employees.get(currConnectedEmpID).getOnlyEmployeeConstraints(shiftController);
    }

    public void updateEmpRole(int SID, int EID, RoleType newRole) throws Exception {
        employees.get(currConnectedEmpID).updateEmpRole(SID, EID, newRole, shiftController);
    }

    /**
     * Loads the relevant data of a spcific branch with BID identifier
     * Note : the BID is chosen in the first window options before identifying to the system the employee
     *
     * @param BID Identifier of the branch (1-9)
     */
    public void loadData(int BID) {
        //Load the data from database first.
        currBranchID = BID;
    }

    public void checkWorking(int EID) throws Exception {
        if (!employees.containsKey(EID))
            throw new Exception("Employee with id: " + EID + " doesn't work in this branch");
    }

    /**
     * Getters/Setters
     */
    public int getCurrBranchID() {
        return currBranchID;
    }

    public Map<Integer, Employee> getEmployees() {
        return employees;
    }

    public int getCurrConnectedEmpID() {
        return currConnectedEmpID;
    }

    public RoleType getCurrConnectedEmpRole() {
        return currConnectedEmpRole;
    }

    public void setCurrBranchID(int currBranchID) {
        this.currBranchID = currBranchID;
    }

    public void setEmployees(Map<Integer, Employee> employees) {
        this.employees = employees;
    }

    public void setCurrConnectedEmpID(int currConnectedEmpID) {
        this.currConnectedEmpID = currConnectedEmpID;
    }

    public void setCurrConnectedEmpRole(RoleType currConnectedEmpRole) {
        this.currConnectedEmpRole = currConnectedEmpRole;
    }

    public String getCurrentConEmpName() {
        return employees.get(currConnectedEmpID).getName();
    }


}
