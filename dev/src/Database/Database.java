package Database;

import Business.EmployeePKG.Employee;
import Business.EmployeePKG.PersonnelManager;
import Business.EmployeePKG.Regular;
import Business.Type.RoleType;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Database {
    final static Logger log = Logger.getLogger(Database.class);
    private static Database database = null;
    //---------------------------------fields---------------------------
    private int counterSID;
    private int counterCID;
    private int counterBID;
    private Map<Integer, Map<Integer, Employee>> employeesInBranches;
    private Map<Integer, Map<Integer, Employee>> firedEmployees;

    //--------------------------------constructor-----------------------
    private Database(){
        init();
    }

    public static Database getInstance() {
        if (database == null)
            database = new Database();
        return database;
    }

    //-------------------------------methods----------------------------
    public int addShift() {
        //add to DB and return SID
        counterSID++;
        return counterSID;
    }

    public int addConstraint() {
        //add to DB and return CID
        counterCID++;
        return counterCID;
    }


    public void init() {
        this.counterBID = 1;
        this.counterSID = 0;
        this.counterCID = 0;
        this.employeesInBranches = new HashMap<>();
        this.firedEmployees = new HashMap<>();
        int[] bank = {1, 2, 3};
        int[] terms = {4, 5, 6};
        //employeesInBranches.get(1).put()
    }

    private int getEmployeeBranch(int EID) throws Exception {
        for (Map.Entry<Integer, Map<Integer, Employee>> branch : employeesInBranches.entrySet()) {
            if (branch.getValue().containsKey(EID))
                return branch.getKey();
        }
        log.error("employee with EID: "+EID+" not found");
        throw new Exception("Employee with ID:" + EID + "is not available in database");
    }

    public void addEmployee(int personnelID, Employee toAdd) throws Exception {
        //init only for unit test of employee
        if(toAdd.getEID() == personnelID){
            employeesInBranches.put(1,new HashMap<>());
            employeesInBranches.get(1).put(personnelID,toAdd);
            return;
        }
        log.debug("enter database - adding new instance of employee map");
        int branchNum = getEmployeeBranch(personnelID);
        employeesInBranches.get(branchNum).put(toAdd.getEID(), new Regular(toAdd));
        log.debug("added successfully "+toAdd.getEID()+" to database");
    }

    public void fireEmployee(int fireEID) throws Exception {
        log.debug("entered database - firing EID: "+fireEID);
        int branchNum = getEmployeeBranch(fireEID);
        Employee fired = employeesInBranches.get(branchNum).remove(fireEID);
        if (!firedEmployees.containsKey(branchNum))
            firedEmployees.put(branchNum, new HashMap<>());
        firedEmployees.get(branchNum).put(fireEID, fired);
        log.debug("added fired employee to history of database");
    }

    public void updateEmployeeName(int eid, String newName) throws Exception {
        log.debug("entered database - update name");
        int branch = getEmployeeBranch(eid);
        employeesInBranches.get(branch).get(eid).setName(newName);
        log.debug("updated name: "+ newName+" in database");
    }

    public void updateEmployeeSalary(int eid, int newSalary) throws Exception {
        log.debug("entered database - update salary");
        int branch = getEmployeeBranch(eid);
        employeesInBranches.get(branch).get(eid).setSalary(newSalary);
        log.debug("updated salary: "+ newSalary+" in database");
    }

    public void updateEmployeeBANum(int eid, int newAccountNumber) throws Exception {
        log.debug("entered database - update bank account number");
        int branch = getEmployeeBranch(eid);
        employeesInBranches.get(branch).get(eid).getBankAccount().setAccountNum(newAccountNumber);
        log.debug("updated bank account number: "+ newAccountNumber+" in database");
    }

    public void updateEmployeeBABranch(int eid, int newBranch) throws Exception {
        log.debug("entered database - update bank branch number");
        int branch = getEmployeeBranch(eid);
        employeesInBranches.get(branch).get(eid).getBankAccount().setBankBranch(newBranch);
        log.debug("updated bank branch number: "+ newBranch+" in database");
    }

    public void updateEmployeeBAID(int eid, int newBankID) throws Exception {
        log.debug("entered database - update bank ID");
        int branch = getEmployeeBranch(eid);
        employeesInBranches.get(branch).get(eid).getBankAccount().setBankID(newBankID);
        log.debug("updated bank ID: "+ newBankID+" in database");
    }

    public void updateEmployeeEducationFund(int eid, int newEducationFund) throws Exception {
        log.debug("entered database - update education fund");
        int branch = getEmployeeBranch(eid);
        employeesInBranches.get(branch).get(eid).getTermsOfEmployment().setEducationFun(newEducationFund);
        log.debug("updated education fund: "+ newEducationFund+" in database");
    }

    public void updateEmployeeDaysOff(int eid, int newAmount) throws Exception {
        log.debug("entered database - update days off");
        int branch = getEmployeeBranch(eid);
        employeesInBranches.get(branch).get(eid).getTermsOfEmployment().setDaysOff(newAmount);
        log.debug("updated days off: "+ newAmount+" in database");
    }

    public void updateEmployeeSickDays(int eid, int newAmount) throws Exception {
        log.debug("entered database - update sick days");
        int branch = getEmployeeBranch(eid);
        employeesInBranches.get(branch).get(eid).getTermsOfEmployment().setSickDays(newAmount);
        log.debug("updated sick days: "+ newAmount+" in database");
    }

    public void createBranch(Employee personnelM) throws Exception {
        log.debug("enter database - creating new branch with id " +counterBID);
        employeesInBranches.put(counterBID++,new HashMap<>());
        employeesInBranches.get(counterBID-1).put(personnelM.getEID(),new PersonnelManager(personnelM));
        log.debug("added a new instance of personnel manager with id "+personnelM.getEID()+" to database");
    }

    public int getCounterBID() {
        return counterBID;
    }

    public int getCounterCID() {
        return counterCID;
    }

    public Map<Integer, Employee> getEmployeesInBranches(int BID) {
        return employeesInBranches.get(BID);
    }

    public Map<Integer, Map<Integer, Employee>> getFiredEmployees() {
        return firedEmployees;
    }

    public void addRoleToEmployee(int eid, RoleType role) throws Exception {
        log.debug("entered database - add role to employee");
        int branch = getEmployeeBranch(eid);
        employeesInBranches.get(branch).get(eid).getRole().add(role);
        log.debug("updated role list of employee: "+ eid+" with role:"+role.name()+" in database");
    }
}
