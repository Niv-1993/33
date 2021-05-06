package Business.ShiftPKG;

import Business.EmployeePKG.Employee;
import Business.Type.RoleType;
import Business.Type.ShiftType;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.util.*;

public class Shift {
    private final static Logger log = Logger.getLogger(Shift.class);
    //-------------------------------------fields------------------------------------

    private final int SID;
    private final Map<Employee, RoleType> employees;
    private final Map<RoleType, Integer> rolesAmount;
    private final Map<RoleType, List<Employee>> optionals;
    private boolean complete;  //employees == roles amount
    private final LocalDate date;
    private final ShiftType shiftType;
    private boolean wasSelfMake;
    private boolean hasShiftManager;

    //------------------------------------constructor--------------------------------

    public Shift(int SID, Map<RoleType, Integer> rolesAmount, Map<RoleType, List<Employee>> optionals, LocalDate date, ShiftType shiftType) {
        this.SID = SID;
        employees = new HashMap<>();
        this.rolesAmount = rolesAmount;
        this.optionals = optionals;
        this.complete = false;
        this.date = date;
        this.shiftType = shiftType;
        this.wasSelfMake = false;
        if (optionals.containsKey(RoleType.ShiftManager))
            hasShiftManager = !optionals.get(RoleType.ShiftManager).isEmpty();
        log.debug("shift: " + SID + " created");
    }


    //-------------------------------------methods----------------------------------

    //shift controller call this function after create shift to insert shift manager to this shift
    public List<Employee> insertShiftManager() {
        Employee sm = optionals.get(RoleType.ShiftManager).remove(0);
        employees.put(sm, RoleType.ShiftManager);
        List<Employee> listOfSM = new LinkedList<>();
        listOfSM.add(sm);
        return listOfSM;
    }

    public List<Employee> self_make() {
        if (!wasSelfMake) {
            for (Map.Entry<RoleType, Integer> e : rolesAmount.entrySet()) {
                if (e.getKey().equals(RoleType.ShiftManager)) continue;
                RoleType role = e.getKey();
                int amount = e.getValue();
                while (amount > 0) {
                    if (optionals.get(role) == null || optionals.get(role).isEmpty()) {
                        break;
                    } else {
                        Employee emp = optionals.get(role).remove(0);  // delete from optionals
                        employees.put(emp, role);  //add to employees
                        removeEmpFromOptionals(emp);
                        amount--;
                    }
                }
            }
            ArrayList<Employee> emps = new ArrayList<>();
            employees.forEach((emp, roleType) -> emps.add(emp));
            complete = isComplete();
            wasSelfMake = true;
            return emps;
        }
        return new ArrayList<>();
    }

    public void addEmpToShift(RoleType role, Employee emp) {
        employees.put(emp, role);
        removeEmpFromOptionals(emp);
        complete = isComplete();
        if (role.equals(RoleType.ShiftManager))
            hasShiftManager = true;
        log.debug("EID: " + emp.getEID() + "added to SID: " + SID);
    }

    public void removeEmpFromShift(Employee emp) {
        RoleType roleOfRemoved = employees.remove(emp);  //return null if EID isn't in this shift
        complete = false;
        if (roleOfRemoved.equals(RoleType.ShiftManager)) {
            hasShiftManager = false;
        }
        (emp.getRole()).forEach(roleType -> {
            optionals.get(roleType).add(emp);
        });
        log.debug("EID: " + emp.getEID() + "removed from SID: " + SID);
    }

    public void removeFireEmp(Employee emp) {
        RoleType roleOfRemoved = employees.remove(emp);  //remove employee if he is in this shift
        if (roleOfRemoved != null) {
            complete = false;
            if (roleOfRemoved.equals(RoleType.ShiftManager)) {
                hasShiftManager = false;
            }
        }
        for (Map.Entry<RoleType, List<Employee>> o : optionals.entrySet()) {
            List<Employee> listOfEmp = o.getValue();
            if (listOfEmp != null) listOfEmp.remove(emp);
        }
        log.debug("EID: " + emp.getEID() + " removed(fire) from SID: " + SID);
    }

    public void addToOptionals(Employee emp, RoleType role) {
        if(!optionals.get(role).contains(emp))
            optionals.get(role).add(emp);
    }

    public void removeEmpFromOptionals(Employee emp) {
        for (Map.Entry<RoleType, List<Employee>> e : optionals.entrySet()) {
            List<Employee> l = e.getValue();
            if (!l.isEmpty())
                l.remove(emp);
        }
        log.debug("EID: " + emp.getEID() + " remove from optionals");
    }


    public List<Employee> updateRolesAmount(RoleType role, int newAmount) {
        int oldAmount = rolesAmount.get(role);
        rolesAmount.replace(role, newAmount);
        List<Employee> toRemove = new ArrayList<>();
        for (int i = 0; i < oldAmount - newAmount; i++) {
            for (Map.Entry<Employee, RoleType> m : employees.entrySet()) {
                if (m.getValue().equals(role)) {
                    employees.remove(m.getKey());
                    addToOptionals(m.getKey(), m.getValue());
                    toRemove.add(m.getKey());
                    break;
                }
            }
        }
        complete = isComplete();
        return toRemove;
    }

    private boolean roleIsFull(RoleType role) {
        int amount = rolesAmount.get(role);
        long count = employees.entrySet().stream().filter(x -> x.getValue().name().equals((role.name()))).count();
        return count >= amount;
    }


    private boolean isComplete() {
        int amount = 0;
        for (Map.Entry<RoleType, Integer> role : rolesAmount.entrySet())
            amount += role.getValue();
        return amount == employees.size();
    }

    //check if there is employee that optionals and his role not full
    public boolean optionalIsEmpty(){
        for (Map.Entry<RoleType, List<Employee>> role : optionals.entrySet()){
            if(!role.getValue().isEmpty() && !roleIsFull(role.getKey()))
                return false;
        }
        return true;
    }

    public boolean EIDIsOptionForSID(Employee emp) {
        for (Map.Entry<RoleType, List<Employee>> role : optionals.entrySet()){
            if(role.getValue().contains(emp) && !roleIsFull(role.getKey()))
                return true;
        }
        return false;
    }

    public boolean canWork(Employee emp, RoleType role) {
        //check if EID can work in this shift (in optionals)
        //check if there is empty role for this roleType in rolesAmount
        List<Employee> list = optionals.get(role);
        boolean canWork = list.contains(emp);
        return canWork && !roleIsFull((role));
    }
    //-------------------getters&setters----------------------------------------

    public LocalDate getDate() {
        return date;
    }

    public ShiftType getShiftType() {
        return shiftType;
    }

    public Map<Employee, RoleType> getEmployees() {
        return employees;
    }

    public int getSID() {
        return SID;
    }

    public Map<RoleType, Integer> getRolesAmount() {
        return rolesAmount;
    }

    public Map<RoleType, List<Employee>> getOptionals() {
        return optionals;
    }

    public boolean getComplete() {
        return complete;
    }

    public boolean WasSelfMake() {
        return wasSelfMake;
    }

    public boolean HasShiftManager() {
        return hasShiftManager;
    }


}