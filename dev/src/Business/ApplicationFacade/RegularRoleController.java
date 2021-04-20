package Business.ApplicationFacade;

import Business.ApplicationFacade.outObjects.*;
import Business.ShiftPKG.ShiftController;
import Business.Type.RoleType;
import Business.Type.ShiftType;
import org.apache.log4j.Logger;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

public class RegularRoleController implements iRegularRoleController {
    final static Logger log = Logger.getLogger(RegularRoleController.class);
    //private RoleType currConnectedEmpRole;
    private Business.EmployeePKG.Employee currConnectedEmp;
    private Utils utils;

    //WILL BE DELETED WHEN THERE WILL BE DATABASE
    private int branchCounter;
    private List<Integer> allBranches;

    public RegularRoleController(){
       // this.currConnectedEmpRole = null;
        this.currConnectedEmp = null;
        utils = new Utils(new ShiftController());
        //WILL BE DELETED WHEN THERE WILL BE DATABASE
        allBranches = new ArrayList<>();
        branchCounter = 1;
    }

    /**
     * Logins EID with his/her role of to the system
     *
     * @param EID  The Identification number of the employee
     * @param role The role he takes in "super-lee"
     * @return A response object. The response should contain a error message in case of an error
     */
    public Response Login(int EID, String role) {
        log.debug("entered login function with user id: " + EID + " and role " + role);
        if(!utils.isInEnum(role, RoleType.class)) return new Response("Invalid Role Type");
        if(!utils.checkEIDExists(EID)) return new Response(("user with id: " + EID + " is not in map of employees"));
        if (!utils.getEmployees().get(EID).isQualified(RoleType.valueOf(role))) {
            log.error("user with id: " + EID + " has no role of " + role + " in branch " + utils.getCurrBranchID());
            return new Response("Employee is unqualified to this role");
        }
        currConnectedEmp = utils.getEmployees().get(EID);
       // currConnectedEmpRole = RoleType.valueOf(role);
        log.debug("successfully logged in - user fields are updated to +" + EID + " with role " + role);
        return new Response();
    }

    /**
     * Logs out the connected employee
     *
     * @return A response object. The response should contain a error message in case of an error
     */
    public Response Logout() {
        log.debug("enter logout function when current connected id is: " + currConnectedEmp.getEID());
        if (currConnectedEmp == null) {
            log.error("try to logout a user that is not logged in before");
            return new Response("No one is logged in, please log in first");
        }
        currConnectedEmp= null;
       // currConnectedEmpRole = null;
        log.debug("successfully logged out - user fields are updated to -1 and null role");
        return new Response();
    }

    /**
     * Add a constraint of const day-type with type-shift and reason of a employee
     *
     * @param day       Day of the week the employee can't work
     * @param shiftType Type of shift of the day
     * @param reason    The reason of the constraint
     * @return A response object. The response should contain a error message in case of an error
     */
    public Response addConstConstraint(DayOfWeek day, String shiftType, String reason) {
        log.debug("enter add const constraint function");
        if(!utils.isInEnum(shiftType, ShiftType.class)) return new Response("Invalid Shift Type");
        utils.getShiftController().addConstConstraint(currConnectedEmp.getEID(), day, ShiftType.valueOf(shiftType), reason);
        log.debug("successfully added const constraint");
        return new Response();
    }

    /**
     * Add a constraint on date {@link LocalDate} with type-shift and reason of a employee
     *
     * @param c_date    The date that the employee can't work
     * @param shiftType Type of shift of the day
     * @param reason    The reason of the constraint
     * @return A response object. The response should contain a error message in case of an error
     */
    public Response addTempConstraint(LocalDate c_date, String shiftType, String reason) {
        log.debug("enter add constraint function");
        if(!utils.isInEnum(shiftType, ShiftType.class)) return new Response("Invalid Shift type.");
        String res = utils.getShiftController().addTempConstraint(currConnectedEmp,c_date, ShiftType.valueOf(shiftType), reason);
        return res.isEmpty() ? new Response() : new Response(res);
    }

    /**
     * Remove a constraint with identifier CID that the employee sees
     *
     * @param CID Identifier of the constraint to be removed
     * @return A response object. The response should contain a error message in case of an error
     */
    public Response removeConstraint(int CID) {
        log.debug("enter remove constraint function");
        String res = utils.getShiftController().removeConstraint(CID,currConnectedEmp.getEID());
        return res.isEmpty()? new Response() : new Response(res);
    }

    /**
     * Edit/Update an existing constraint with CID with the new reason
     *
     * @param CID       Identifier of the constraint to be updated
     * @param newReason The new reason of the constraint
     * @return A response object. The response should contain a error message in case of an error
     */
    public Response updateReasonConstraint(int CID, String newReason){
        log.debug("enter update reason constraint function");
        String res = utils.getShiftController().updateReasonConstraint(CID, newReason,currConnectedEmp.getEID());
        return res.isEmpty()? new Response() : new Response(res);
    }

    /**
     * Edit/Update an existing constraint with CID with a different shift-type
     *
     * @param CID     Identifier of the constraint to be updated
     * @param newType The new shift type
     * @return A response object. The response should contain a error message in case of an error
     */
    public Response updateShiftTypeConstraint(int CID, String newType) {
        log.debug("enter update shift type in constraint function");
        if(!utils.isInEnum(newType, ShiftType.class)) return new Response("Invalid shift type");
        String res = utils.getShiftController().updateShiftTypeConstraint(CID, ShiftType.valueOf(newType),currConnectedEmp.getEID());
        return res.isEmpty()? new Response() : new Response(res);
    }

    /**
     * Gets the currently connected employee his/er's shifts
     *
     * @return A response object with a value set to employee containing the details,
     * otherwise the response should contain a error message in case of an error
     */
    public ResponseData<List<Shift>> getMyShifts() {
        log.debug("entered getting shifts of employee: "+currConnectedEmp.getEID());
        List<Business.ShiftPKG.Shift> myShifts = utils.getShiftController().getMyShifts(currConnectedEmp);
        return new ResponseData<>(utils.convertShifts(myShifts));
    }

    /**
     * Gets the details of a connected employee
     *
     * @return A response object with a value set to the employee
     * otherwise the response should contain a error message in case of an error
     */
    public ResponseData<Employee> getEmployeeDetails() {
       return new ResponseData<>(new Employee(currConnectedEmp));
    }

    /**
     * get all current connected user's constraints
     *
     * @return A response List. The response should contain a error message in case of an error
     */
    public ResponseData<List<Constraint>> getMyConstraints() {
        return new ResponseData<>(utils.convertConstrains(utils.getShiftController().getMyConstraints(currConnectedEmp.getEID())));
    }

    /**
     * Loads the relevant data of a specific branch with BID identifier
     * Note : the BID is chosen in the first window options before identifying to the system the employee
     *
     * @param BID Identifier of the branch (1-9)
     * @return A response object. The response should contain a error message in case of an error
     */
    public Response loadData(int BID) {
        log.debug("loading data of branch id: "+BID);
        if(!allBranches.contains(BID)) return new Response("Branch does not exist");
        currConnectedEmp = null;
        //utils.setShiftController(new ShiftController());
        //currConnectedEmpRole = null;
        utils.setCurrBranchID(BID);
        //WHEN THERE WILL BE DATABASE
        //employees = new HashMap<>();
        log.debug("loaded all employees.");
        //WHAT EVER SHIFTPGK NEED TO MAKE SHIFTCONTOLLER
        // shiftController = new ShiftController();
        log.debug("Done loading data");
        return new Response();
    }

    /**
     * gets all branches available from database
     *
     * @return A response List. The response should contain a error message in case of an error
     */
    public ResponseData<List<String>> getBranches() {
        return new ResponseData<>(allBranches.stream().map(String::valueOf).collect(Collectors.toList()));
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
        log.debug("enter create branch function");
        if (!code.equals("00000")) {
            log.error("enter code: " + code);
            return new Response("Access code denied!");
        }
        log.debug("creating instance of the personnel manager in this new branch");
        String check = utils.checkEmpDetails(newEID,name,bankDetails,terms,salary,"PersonnelManager");
        if(!check.isEmpty()) return new Response(check);
        Business.EmployeePKG.Employee m = new Business.EmployeePKG.Employee(newEID, name, bankDetails, salary, RoleType.PersonnelManager, LocalDate.now(), terms);
        //UPDATE DATABASE
        utils.getEmployees().put(newEID, m);
        //will be deleted after database exits
        allBranches.add(branchCounter++);
        log.debug("successfully created branch");
        return new Response();
    }

    /**
     * gets all role types
     *
     * @return A response String. The response should contain a error message in case of an error
     */
    public ResponseData<List<String>> getRoleTypes() {
        return new ResponseData<>((new ArrayList<>(EnumSet.allOf(RoleType.class))).stream().map(Enum::name).collect(Collectors.toList()));
    }

    /**
     * gets all shift types
     *
     * @return A response String. The response should contain a error message in case of an error
     */
    public ResponseData<List<String>> getShiftTypes() {
        return new ResponseData<>((new ArrayList<>(EnumSet.allOf(ShiftType.class))).stream().map(Enum::name).collect(Collectors.toList()));
    }

    /**
     * checks if default shifts are initialized
     *
     * @return true if yes else false
     */
    public ResponseData<Boolean> hasDefaultShifts() {
        return new ResponseData<>(utils.getShiftController().hasDefaultShifts());
    }

    public Utils getUtils() {
        return utils;
    }
}
