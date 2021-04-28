package Business.ApplicationFacade;

import Business.ApplicationFacade.outObjects.Constraint;
import Business.ApplicationFacade.outObjects.Shift;
import Business.EmployeePKG.Employee;
import Business.ShiftPKG.ShiftController;
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

    public void setShiftController(ShiftController shiftController) {
        this.shiftController = shiftController;
    }


//    protected <E extends Enum<E>> boolean isInEnum(String value, Class<E> enumClass) {
//        log.debug("checking if is enum");
//        for (E e : enumClass.getEnumConstants()) {
//            if (e.name().equals(value)) {
//                return true;
//            }
//        }
//        log.error("Illegal value of enum string: " + value + " of enum class: " + enumClass);
//        return false;
//    }
//

    public void reset(int bid){
        setCurrBranchID(bid);
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
