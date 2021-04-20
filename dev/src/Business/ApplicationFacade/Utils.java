package Business.ApplicationFacade;

import Business.ApplicationFacade.outObjects.Constraint;
import Business.ApplicationFacade.outObjects.Shift;
import Business.EmployeePKG.Employee;
import Business.ShiftPKG.ShiftController;
import Business.Type.RoleType;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {
    final static Logger log = Logger.getLogger(Utils.class);
    private ShiftController shiftController;
    private Map<Integer, Employee> employees;
    private int currBranchID;

    public Utils(ShiftController s) {
        shiftController = s;
        employees = new HashMap<>();
        currBranchID = -1;
    }

    public ShiftController getShiftController() {
        return shiftController;
    }

    public void setEmployees(Map<Integer, Employee> employees) {
        this.employees = employees;
    }

    public Map<Integer, Employee> getEmployees() {
        return employees;
    }

    public void setCurrBranchID(int currBranchID) {
        this.currBranchID = currBranchID;
    }

    public int getCurrBranchID() {
        return currBranchID;
    }

    /**
     * Input checks
     */

    protected boolean checkSalary(int salary) {
        log.debug("checking salary");
        if (salary <= 0) {
            log.error("salary is 0 or negative: " + salary);
            return false;
        }
        return true;

    }

    public void setShiftController(ShiftController shiftController) {
        this.shiftController = shiftController;
    }

    protected boolean checkName(String name) {
        log.debug("checking name alphabetical");
        name = name.replaceAll("\\s+", "");
        boolean isValid = ((name != null) && (!name.equals("")) && (name.matches("^[a-zA-Z]*$")));
        if (!isValid) {
            log.error("name " + name + " is not alphabetical");
            return false;
        }
        return true;
    }

    protected boolean checkBank(int[] bankDetails) {
        log.debug("checking bank details");
        boolean isValid = bankDetails[0] > 0 && bankDetails[1] > 0 && bankDetails[2] > 0;
        if (!isValid) {
            log.error("bank details are invalid: AccountNum: " + bankDetails[0] + " BankBranch: " + bankDetails[1] + " BandID: " + bankDetails[2]);
            return false;
        }
        return true;
    }

    protected boolean checkTerms(int[] terms) {
        log.debug("checking terms");
        boolean isValid = terms[0] > 0 && terms[1] >= 0 && terms[2] >= 0;
        if (!isValid) {
            log.error("terms are invalid : education fund: " + terms[0] + " daysOff: " + terms[1] + " sickDays: " + terms[2]);
            return false;
        }
        return true;
    }

    protected <E extends Enum<E>> boolean isInEnum(String value, Class<E> enumClass) {
        log.debug("checking if is enum");
        for (E e : enumClass.getEnumConstants()) {
            if (e.name().equals(value)) {
                return true;
            }
        }
        log.error("Illegal value of enum string: " + value + " of enum class: " + enumClass);
        return false;
    }

    protected boolean checkEIDExists(int EID) {
        if (!employees.containsKey(EID)) {
            log.error("user with id: " + EID + " is not in map of employees");
            return false;
        }
        log.debug("checked that employee is working in this branch - success");
        return true;
    }


    protected String checkEmpDetails(int EID, String name, int[] ba, int[] terms, int salary, String role) {
        return !(EID > 0) ? "Invalid eid" :
                !checkName(name) ? "Invalid name: " + name :
                        !checkSalary(salary) ? "Invalid salary input" :
                                !checkBank(ba) ? "Invalid bank details" :
                                        !checkTerms(terms) ? "Invalid terms of employment" :
                                                !isInEnum(role, RoleType.class) ? "Invalid Role type" :
                                                        "";
    }

    protected String checkWorking(int eid) {
        return employees.containsKey(eid)? "": "Employee with id: " + eid + " doesn't work in this branch";
    }



    /**
     * Convert Help Functions of lists
     */


    protected List<Business.ApplicationFacade.outObjects.Employee> convertEmployee(List<Business.EmployeePKG.Employee> allEmployees) {
        log.debug("converting employees of business layer to out objects list");
        List<Business.ApplicationFacade.outObjects.Employee> employees = new ArrayList<>();
        allEmployees.forEach(e -> {
            employees.add(new Business.ApplicationFacade.outObjects.Employee(e));
        });
        log.debug("Done.");
        return employees;
    }
    protected List<Shift> convertShifts(List<Business.ShiftPKG.Shift> allShifts) {
        log.debug("converting shift of business layer to out objects list");
        List<Shift> shifts = new ArrayList<>();
        allShifts.forEach(s -> {
            Map<Business.ApplicationFacade.outObjects.Employee,String> emps = new HashMap<>();
            Map<String,List<Business.ApplicationFacade.outObjects.Employee>> ops = new HashMap<>();
            s.getEmployees().forEach((employee, roleType) -> {
                emps.put(new Business.ApplicationFacade.outObjects.Employee(employee),roleType.name());
            });
            s.getOptionals().forEach((roleType, employees1) -> {
                List<Business.ApplicationFacade.outObjects.Employee> e = new ArrayList<>();
                employees1.forEach(employee -> {
                    e.add(new Business.ApplicationFacade.outObjects.Employee(employee));
                });
                ops.put(roleType.name(),e);
            });
            shifts.add(new Shift(s,emps,ops));
        });
        log.debug("Done.");
        return shifts;
    }

    protected List<Constraint> convertConstrains(List<Business.ShiftPKG.Constraint> allConstraints) {
        log.debug("converting constraints of business layer to out objects list");
        List<Constraint> constraints = new ArrayList<>();
        allConstraints.forEach(c -> {
            constraints.add(new Constraint(c));
        });
        log.debug("Done.");
        return constraints;
    }
}
