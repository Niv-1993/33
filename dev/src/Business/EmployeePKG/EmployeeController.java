package Business.EmployeePKG;

import Business.ShiftPKG.*;
import Business.Type.*;
import org.apache.log4j.Logger;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


public class EmployeeController {
    final static Logger log = Logger.getLogger(EmployeeController.class);
    private int currBranchID;
    private int currConnectedEmpID;
    private RoleType currConnectedEmpRole;
    protected Map<Integer, Employee> employees;
    private ShiftController shiftController;
    //WILL BE DELETED WHEN THERE WILL BE DATABASE
    private int branchCounter;
    private List<String> allBranches;

    public EmployeeController() {
        employees = new HashMap<>();
        shiftController = new ShiftController();

        //WILL BE DELETED WHEN THERE WILL BE DATABASE
        allBranches = new ArrayList<>();
        branchCounter = 1;

    }

    /**
     * Logins EID with role of  enum role-type {@link RoleType} to the system
     *
     * @param EID  The Identification number of the employee
     * @param role The role he takes in "super-lee"
     */
    public void Login(int EID, String role) throws Exception {
        log.debug("entered login function with user id: " + EID + " and role " + role);
        isInEnum(role, RoleType.class);
        checkEID(EID);
        if (!employees.get(EID).isQualified(RoleType.valueOf(role))) {
            log.error("user with id: " + EID + " has no role of " + role + " in branch " + currBranchID);
            throw new Exception("Employee is unqualified to this role");
        }
        currConnectedEmpID = EID;
        currConnectedEmpRole = RoleType.valueOf(role);
        log.debug("successfully logged in - user fields are updated to +" + EID + " with role " + role);
    }


    /**
     * logs out the current connected employee from the system
     */
    public void Logout() throws Exception {
        log.debug("enter logout function when current connected id is: " + currConnectedEmpID);
        if (currConnectedEmpID == -1) {
            log.error("try to logout a user that is not logged in before");
            throw new Exception("No one is logged in");
        }
        currConnectedEmpID = -1;
        currConnectedEmpRole = null;
        log.debug("successfully logged out - user fields are updated to -1 and null role");
    }

    public void createBranch(String code, int newEID, String name, int[] bankDetails, int salary, int[] terms) throws Exception {
        log.debug("enter create branch function");
        if (!code.equals("00000")) {
            log.error("enter code: " + code);
            throw new Exception("Access code denied!");
        }
        log.debug("creating instance of the personnel manager in this new branch");
        Employee personnelM = new PersonnelManager(newEID, name, bankDetails, salary, RoleType.PersonnelManager, LocalDate.now(), terms);
        //UPDATE DATABASE
        employees.put(newEID,personnelM);
        //will be deleted after database exits
        allBranches.add(String.valueOf(branchCounter++));
        log.debug("successfully created branch");
    }

    /**
     * Add a constraint of const day {@link DayOfWeek} with type-shift {@link ShiftType}  and reason of a employee
     *
     * @param day       Day of the week the employee can't work
     * @param shiftType Type of shift of the day
     * @param reason    The reason of the constraint
     */
    public Constraint addConstConstraint(DayOfWeek day, String shiftType, String reason) throws Exception {
        log.debug("enter add const constraint function");
        isInEnum(shiftType, ShiftType.class);
        Constraint c = employees.get(currConnectedEmpID).addConstConstraint(day, ShiftType.valueOf(shiftType), reason, shiftController);
        log.debug("successfully added const constraint");
        return c;
    }

    /**
     * Add a constraint on date {@link LocalDate} with type-shift {@link ShiftType}  and reason of a employee
     *
     * @param c_date    The date that the employee can't work
     * @param shiftType Type of shift of the day
     * @param reason    The reason of the constraint
     */
    public Constraint addConstraint(LocalDate c_date, String shiftType, String reason) throws Exception {
        log.debug("enter add constraint function");
        isInEnum(shiftType, ShiftType.class);
        Constraint c = employees.get(currConnectedEmpID).addConstraint(c_date, ShiftType.valueOf(shiftType), reason, shiftController);
        log.debug("successfully added constraint");
        return c;
    }

    /**
     * Remove a constraint with identifier CID that the employee sees
     *
     * @param CID Identifier of the constraint to be removed
     */
    public Constraint removeConstraint(int CID) throws Exception {
        log.debug("enter remove constraint function");
        Constraint c = employees.get(currConnectedEmpID).removeConstraint(CID, shiftController);
        log.debug("successfully removed constraint");
        return c;
    }

    /**
     * Edit/Update an existing constraint with CID with the new reason
     *
     * @param CID       Identifier of the constraint to be updated
     * @param newReason The new reason of the constraint
     */
    public void updateReasonConstraint(int CID, String newReason) throws Exception {
        log.debug("enter update reason constraint function");
        employees.get(currConnectedEmpID).updateReasonConstraint(CID, newReason, shiftController);
        log.debug("successfully updated reason in constraint");
    }

    /**
     * Edit/Update an existing constraint with CID with a different shift-type {@link ShiftType}
     *
     * @param CID     Identifier of the constraint to be updated
     * @param newType The new shift type
     */
    public void updateShiftTypeConstraint(int CID, String newType) throws Exception {
        log.debug("enter update shift type in constraint function");
        isInEnum(newType, ShiftType.class);
        employees.get(currConnectedEmpID).updateShiftTypeConstraint(CID, ShiftType.valueOf(newType), shiftController);
        log.debug("successfully updated shift type in constraint");
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
    public Employee addEmployee(int newEID, String name, int[] bankDetails, int salary, String role, LocalDate startWorkDate, int[] terms) throws Exception {
        log.debug("enter add employee function");
        isInEnum(role, RoleType.class);
        Employee e = employees.get(currConnectedEmpID).addEmployee(newEID, name, bankDetails, salary, RoleType.valueOf(role), startWorkDate, terms, employees);
        shiftController.addToOptionals(e.getEID(), e.getName(), RoleType.valueOf(role));
        log.debug("successfully added a new employee");
        return e;
    }

    /**
     * fire an employee with fireID
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param fireEID Identifier of the employee to fire
     */
    public void fireEmployee(int fireEID) throws Exception {
        log.debug("enter fire employee function");
        Employee removed = employees.get(currConnectedEmpID).fireEmployee(fireEID, employees);
        shiftController.removeFireEmp(removed.getEID(), removed.getName());
        log.debug("successfully fired employee");
    }

    /**
     * Update an existing employee's name in the system
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param updateEID The identifier of the employee
     * @param newName   The new name
     */
    public void updateEmployeeName(int updateEID, String newName) throws Exception {
        log.debug("entered update employee name functions");
        employees.get(currConnectedEmpID).updateEmployeeName(updateEID, newName, employees);
        shiftController.updateName(updateEID,employees.get(updateEID).getRole(),newName);
        log.debug("successfully updated employee name");
    }

    /**
     * Update an existing employee's salary in the system
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param updateEID The identifier of the employee
     * @param newSalary The new salary of the employee
     */
    public void updateEmployeeSalary(int updateEID, int newSalary) throws Exception {
        log.debug("entered update employee salary function");
        employees.get(currConnectedEmpID).updateEmployeeSalary(updateEID, newSalary, employees);
        log.debug("successfully updated employee salary");
    }

    /**
     * Update an existing employee's bank account number in the system
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param updateEID        The identifier of the employee
     * @param newAccountNumber The new account number of the employee
     */
    public void updateEmployeeBANum(int updateEID, int newAccountNumber) throws Exception {
        log.debug("entered update employee bank account number function");
        employees.get(currConnectedEmpID).updateEmployeeBANum(updateEID, newAccountNumber, employees);
        log.debug("successfully updated employee bank account number");
    }

    /**
     * Update an existing employee's bank branch  in the system
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param updateEID The identifier of the employee
     * @param newBranch The new bank branch number of the employee
     */
    public void updateEmployeeBABranch(int updateEID, int newBranch) throws Exception {
        log.debug("entered update employee bank branch number function");
        employees.get(currConnectedEmpID).updateEmployeeBABranch(updateEID, newBranch, employees);
        log.debug("successfully updated employee bank branch number");
    }

    /**
     * Update an existing employee's bank id number in the system
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param updateEID The identifier of the employee
     * @param newBankID The new bank id number of the employee
     */
    public void updateEmployeeBAID(int updateEID, int newBankID) throws Exception {
        log.debug("entered update employee bank ID number function");
        employees.get(currConnectedEmpID).updateEmployeeBAID(updateEID, newBankID, employees);
        log.debug("successfully updated employee bank ID number");
    }

    /**
     * Update an existing employee's education fund in the system
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param updateEID        The identifier of the employee
     * @param newEducationFund The education fund of the employee
     */
    public void updateEmployeeEducationFund(int updateEID, int newEducationFund) throws Exception {
        log.debug("entered update employee education fund function");
        employees.get(currConnectedEmpID).updateEmployeeEducationFund(updateEID, newEducationFund, employees);
        log.debug("successfully updated employee education fund");
    }

    /**
     * Update an existing employee's days off in the system
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param updateEID The identifier of the employee
     * @param newAmount The new amount of days off of the employee
     */
    public void updateEmployeeDaysOff(int updateEID, int newAmount) throws Exception {
        log.debug("entered update employee days-off function");
        employees.get(currConnectedEmpID).updateEmployeeDaysOff(updateEID, newAmount, employees);
        log.debug("successfully updated employee days-off");
    }

    /**
     * Update an existing employee's sick days in the system
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param updateEID The identifier of the employee
     * @param newAmount The new amount of sick days of the employee
     */
    public void updateEmployeeSickDays(int updateEID, int newAmount) throws Exception {
        log.debug("entered update employee sick-days function");
        employees.get(currConnectedEmpID).updateEmployeeSickDays(updateEID, newAmount, employees);
        log.debug("successfully updated employee sick-days");
    }

    /**
     * Create a new shift with given roles and amount of each role at a specific date {@link LocalDate} and shift-type {@link ShiftType}
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param rolesAmount Map with key: role and value: the amount
     * @param date        The date of the shift to take place
     * @param shiftType   type of the shift
     */
    public Shift createShift(Map<String, Integer> rolesAmount, LocalDate date, ShiftType shiftType) throws Exception {
        log.debug("entered create shift function");
        Map<RoleType, List<String[]>> optionals = new HashMap<>();
        Map<RoleType, Integer> rolesAndAmount = new HashMap<>();
        EnumSet<RoleType> allRoles = EnumSet.allOf(RoleType.class);
        log.debug("initializing roles and amount map with all roles");
        for (RoleType role : allRoles) {
            optionals.put(role, new ArrayList<>());
            rolesAndAmount.put(role, 0);
        }
        log.debug("initialized.");
        log.debug("putting in optionals map all names and roles into array of strings to each role");
        for (Map.Entry<Integer, Employee> entry : employees.entrySet()) {
            String[] pairIDName = new String[]{Integer.toString(entry.getKey()), entry.getValue().getName()};
            for (RoleType role : entry.getValue().getRole()) {
                optionals.get(role).add(pairIDName);
            }
        }
        log.debug("done.");
        log.debug("checking map for valid role types and converting to enum");
        for (Map.Entry<String, Integer> entry : rolesAmount.entrySet()) {
            isInEnum(entry.getKey(), RoleType.class);
            rolesAndAmount.put(RoleType.valueOf(entry.getKey()), entry.getValue());
        }
        log.debug("done.");
        Shift s = employees.get(currConnectedEmpID).createShift(rolesAndAmount, date, shiftType, optionals, shiftController);
        if(!s.HasShiftManager()) throw new Exception("Shift Date:" +s.getDate()+" has been created BUT does not have a ShiftManager");
        log.debug("successfully created a new shift");
        return s;
    }

    public void createWeekShifts() throws Exception {
        log.debug("entered create week shift function");
        Map<RoleType, List<String[]>> optionals = new HashMap<>();
        EnumSet<RoleType> allRoles = EnumSet.allOf(RoleType.class);
        log.debug("initializing roles and amount map with all roles");
        for (RoleType role : allRoles) {
            optionals.put(role, new ArrayList<>());
        }
        log.debug("initialized.");
        log.debug("putting in optionals map all names and roles into array of strings to each role");
        for (Map.Entry<Integer, Employee> entry : employees.entrySet()) {
            String[] pairIDName = new String[]{Integer.toString(entry.getKey()), entry.getValue().getName()};
            for (RoleType role : entry.getValue().getRole()) {
                optionals.get(role).add(pairIDName);
            }
        }
        log.debug("done.");
        employees.get(currConnectedEmpID).createWeekShifts(optionals,shiftController);
        log.debug("successfully created week shifts");
    }


    public void selfMakeWeekShifts() throws Exception{
        log.debug("entered self make week shift function");
        employees.get(currConnectedEmpID).selfMakeWeekShifts(shiftController);
        log.debug("successfully self made week shifts");
    }
        /**
         * sets default shifts with roles and amount
         *
         * @param defaultRolesAmount the default map
         * @throws Exception
         */
    public void defaultShifts(Map<String, Map<String, Integer>> defaultRolesAmount) throws Exception {
        log.debug("entered default shifts function");
        Map<ShiftType, Map<RoleType, Integer>> defaults = new HashMap<>();
        log.debug("parsing strings to enum types of map");
        for (Map.Entry<String, Map<String, Integer>> entry : defaultRolesAmount.entrySet()) {
            isInEnum(entry.getKey(), ShiftType.class);
            ShiftType s_type = ShiftType.valueOf(entry.getKey());
            defaults.put(s_type, new HashMap<>());
            Map<RoleType, Integer> rolesAmount = defaults.get(s_type);
            for (Map.Entry<String, Integer> r : entry.getValue().entrySet()) {
                isInEnum(r.getKey(), RoleType.class);
                rolesAmount.put(RoleType.valueOf(r.getKey()), r.getValue());
            }
        }
        log.debug("done.");
        shiftController.defaultShifts(defaults);
        log.debug("successfully set default shifts");
    }

    /**
     * Gets the details of a connected employee
     *
     * @return the current connected employee object
     */
    public Employee getEmployeeDetails() {
        log.debug("entered get employee details function");
        Employee e = employees.get(currConnectedEmpID);
        log.debug("successfully got details");
        return e;
    }

    /**
     * Gets the details all the shifts and the employees in the shifts including optionals
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @return A list of all the shifts and the employees in every shift (names & roles)
     */
    public List<Shift> getShifts(LocalDate until) throws Exception {
        log.debug("entered get shifts and employees function");
        List<Shift> l = employees.get(currConnectedEmpID).getShifts(until,shiftController);
        log.debug("successfully got all shifts and employees details");
        return l;
    }

    /**
     * Removes employee with removeEID id from an existing shift with SID
     * Note : Only the personnel manager is allowed to use this functionality
     *
     * @param SID       Identifier of the shift
     * @param removeEID Identifier of the employee
     */
    public void removeEmpFromShift(int SID, int removeEID) throws Exception {
        log.debug("entered remove employee from shift functions");
        if(!employees.containsKey(removeEID)) throw new Exception("Employee does not exist in this branch");
        employees.get(currConnectedEmpID).removeEmpFromShift(SID, removeEID, employees.get(removeEID).getRole(),shiftController);
        log.debug("successfully removed employee: " + removeEID + " from shift: " + SID);
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
        log.debug("entered add employee to shift function");
        employees.get(currConnectedEmpID).addEmpToShift(SID, addEID, role, employees.get(addEID).getName(), shiftController);
        log.debug("successfully added employee: "+addEID+" to shift: "+SID+" in role of: "+role.name());
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
        log.debug("entered update amount in role function");
        employees.get(currConnectedEmpID).updateAmountRole(SID, role, newAmount, shiftController);
        log.debug("successfully updated shift: "+SID+" with new amount: "+newAmount+" in role: "+role.name());
    }

    /**
     * gets the current connected employee's shifts
     *
     * @return A list of constraints of the current connected employee
     */
    public List<Shift> getMyShifts() {
        log.debug("entered getting shifts of employee: "+currConnectedEmpID);
        List<Shift> l = employees.get(currConnectedEmpID).getMyShifts(shiftController);
        log.debug("successfully got all shifts of employee: "+currConnectedEmpID);
        return l;
    }

    /**
     * Gets the currently connected employee his/er's constraints
     *
     * @return A list of constraints of the current connected employee
     */
    public List<Constraint> getOnlyEmployeeConstraints() {
        log.debug("entered getting current connected employee's constraints: "+currConnectedEmpID);
        List<Constraint> l =  employees.get(currConnectedEmpID).getOnlyEmployeeConstraints(shiftController);
        log.debug("successfully got all constraints of employee: "+currConnectedEmpID);
        return l;
    }

    /**
     * Adds to a specific employee a role to his list
     * Note : Only the personnel manager is allowed to use this functionality
     * @param eid  the identifier of the employee to add the role
     * @param role the role
     * @return
     */
    public void addRoleToEmployee(int eid, String role) throws Exception {
        log.debug("entered add role to employee function");
        isInEnum(role,RoleType.class);
        employees.get(currConnectedEmpID).addRoleToEmployee(eid,RoleType.valueOf(role),employees);
        shiftController.addToOptionals(eid, employees.get(eid).getName(), RoleType.valueOf(role));
        log.debug("successfully added role to role list of employee: "+eid);
    }

    /**
     * Loads the relevant data of a spcific branch with BID identifier
     * Note : the BID is chosen in the first window options before identifying to the system the employee
     *
     * @param BID Identifier of the branch (1-9)
     */
    public void loadData(int BID) throws Exception {
        log.debug("loading data of branch id: "+BID);
        if(!getBranches().contains(String.valueOf(BID))) throw new Exception("Branch does not exist");
        currConnectedEmpID = -1;
        currConnectedEmpRole = null;
        currBranchID = BID;
        //WHEN THERE WILL BE DATABASE
        //employees = new HashMap<>();
        log.debug("loaded all employees.");
        //WHAT EVER SHIFTPGK NEED TO MAKE SHIFTCONTOLLER
       // shiftController = new ShiftController();
        log.debug("Done loading data");

    }
    public List<String> getBranches() throws Exception {
        if(allBranches.isEmpty()) throw new Exception("No available branches");
        return allBranches;
    }

    public List<String> getShiftTypes() {
        return (new ArrayList<>(EnumSet.allOf(ShiftType.class))).stream().map(Enum::name).collect(Collectors.toList());
    }

    public List<Constraint> getMyConstraints() {
        return employees.get(currConnectedEmpID).getMyConstraints(shiftController);
    }

    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees.values());
    }

    public boolean hasDefaultShifts() {
        return shiftController.hasDefaultShifts();
    }

    public List<String> getRoleTypes() {
        return (new ArrayList<>(EnumSet.allOf(RoleType.class))).stream().map(Enum::name).collect(Collectors.toList());
    }

    private void checkEID(int EID) throws Exception {
        if (!employees.containsKey(EID)) {
            log.error("user with id: " + EID + " is not in map of employees");
            throw new Exception("EID: " + EID + " not found");
        }
        log.debug("checked that employee is working in this branch - success");
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

    /**
     * https://stackoverflow.com/questions/10199462/how-to-check-if-a-given-string-is-a-part-of-any-given-enum-in-java
     *
     * @param value
     * @param enumClass
     * @param <E>
     * @return
     */
    private <E extends Enum<E>> void isInEnum(String value, Class<E> enumClass) throws Exception {
        log.debug("checking if is enum");
        for (E e : enumClass.getEnumConstants()) {
            if (e.name().equals(value)) {
                return;
            }
        }
        log.error("Illegal value of enum string: " + value + " of enum class: " + enumClass);
        throw new Exception("illegal string value");
    }


}
