package Business.ShiftPKG;

import Business.EmployeePKG.Employee;
import Business.Type.RoleType;
import Business.Type.ShiftType;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import org.apache.log4j.Logger;

public class ShiftController {
    private final static Logger log = Logger.getLogger(ShiftController.class);
    //-------------------------------------fields------------------------------------

    private final Map<Integer, Shift> shifts;
    private final Map<Integer, Constraint> constraints;
    private final Map<Integer, TempConstraint> buildShiftConstraints;
    private Map<ShiftType, Map<RoleType, Integer>> defaultShifts;
    private int shiftCounter;
    private int constraintCounter;

    //------------------------------------constructor--------------------------------

    public ShiftController() {
        shifts = new HashMap<>();
        constraints = new HashMap<>();
        defaultShifts = new HashMap<>();
        shiftCounter = 0;
        constraintCounter = 0;
        buildShiftConstraints = new HashMap<>();
        log.debug("finished constructor shiftController");
        //when will use DB shifts not empty so we need to create build constraints
        /*for (Map.Entry<Integer, Shift> m : shifts.entrySet()) {
            Shift s = m.getValue();
            createBuildConstraints(new ArrayList<>(s.getEmployees().keySet()), s.getShiftType(), s.getDate());
        }*/
    }

    //--------------------------------------methods----------------------------------

    public void addConstConstraint(int EID, DayOfWeek day, ShiftType shiftType, String reason) {
        //create constraint and add to constraints field
        Constraint newCon = new ConstConstraint(constraintCounter, EID, day, shiftType, reason);
        constraints.put(newCon.getCID(), newCon);
        constraintCounter++;
        log.debug("added new const constraint for EID: " + EID + ", Day: " + day.name() + " , ShiftType: " + shiftType);
    }

    public void addTempConstraint(Employee emp, LocalDate c_date, ShiftType shiftType, String reason) {
        Shift s = getShiftByDate(c_date, shiftType);
        Constraint newCon = new TempConstraint(constraintCounter, emp.getEID(), c_date, shiftType, reason);
        constraints.put(newCon.getCID(), newCon);
        constraintCounter++;
        s.removeEmpFromOptionals(emp);
        log.debug("added new const constraint for EID: " + emp.getEID() + ", Date: " + c_date + " , ShiftType: " + shiftType);
    }

    public void removeConstraint(int CID, int EID)  {
        constraints.remove(CID);
    }

    public String defaultShifts(Map<ShiftType, Map<RoleType, Integer>> defaultShifts) {
        for (Map.Entry<ShiftType, Map<RoleType, Integer>> e : defaultShifts.entrySet()) {
            String response = checkIfAmountNegative(e.getValue());
            if(response!=null) return response;
        }
        this.defaultShifts = defaultShifts;
        log.debug("default shifts defined");
        return "";
    }

    //assume that sunday-thursday will be default shifts and on friday only morning shift.
    public List<Shift> createWeekShifts(Map<RoleType, List<Employee>> optionals) {
        LocalDate date = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        List<Shift> shiftsWithoutShiftManager = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            for (Map.Entry<ShiftType, Map<RoleType, Integer>> m : defaultShifts.entrySet()) {
                ShiftType shiftType = m.getKey();
                if (shiftAlreadyCreated(date.plusDays(i), shiftType)) continue;
                Shift s = createShiftPrivate(defaultShifts.get(shiftType), date.plusDays(i), shiftType, optionals);  // default shift
                if (!s.HasShiftManager())
                    shiftsWithoutShiftManager.add(s);
            }
        }
        Shift friday = createShiftPrivate(defaultShifts.get(ShiftType.Morning), date.plusDays(5), ShiftType.Morning, optionals);  // default shift
        if (!friday.HasShiftManager())
            shiftsWithoutShiftManager.add(friday);
        return shiftsWithoutShiftManager;
    }

    private Shift createShiftPrivate(Map<RoleType, Integer> rolesAmount, LocalDate date, ShiftType shiftType, Map<RoleType, List<Employee>> optionals) {
        //delete from optionals by constraints
        optionals = deepCopy(optionals);
        createOptionals(optionals, date, shiftType);
        Shift s = new Shift(shiftCounter++, rolesAmount, optionals, date, shiftType);
        if (s.HasShiftManager()) {
            List<Employee> shiftManager = s.insertShiftManager();
            createBuildConstraintsAndRemoveFromOpt(shiftManager, shiftType, date);  //create build constraint for shift manager
        }
        shifts.put(s.getSID(), s);
        return s;
    }


    public void createShift(Map<RoleType, Integer> rolesAmount, LocalDate date, ShiftType shiftType, Map<RoleType, List<Employee>> optionals) {
        //delete from optionals by constraints
        optionals = deepCopy(optionals);
            createOptionals(optionals, date, shiftType);
            Shift s = new Shift(shiftCounter++, rolesAmount, optionals, date, shiftType);
            if (s.HasShiftManager()) {
                List<Employee> shiftManager = s.insertShiftManager();
                createBuildConstraintsAndRemoveFromOpt(shiftManager, shiftType, date);  //create build constraint for shift manager
            }
            shifts.put(s.getSID(), s);
    }

    private void createOptionals(Map<RoleType, List<Employee>> optionals,LocalDate date ,ShiftType shiftType){
        for (Map.Entry<Integer, Constraint> c : constraints.entrySet()) {
            if (c.getValue().relevant(date, shiftType))
                RemoveEmpFromOptionals(c.getValue().getEID(), optionals);
        }
        for (Map.Entry<Integer, TempConstraint> c : buildShiftConstraints.entrySet()) {
            if (c.getValue().relevant(date, shiftType)) {
                RemoveEmpFromOptionals(c.getValue().getEID(), optionals);
            }
        }
    }

    //self make for all shifts that next week and not was self make
    public void selfMakeWeekShifts()  {
        for (Map.Entry<Integer, Shift> m : shifts.entrySet()) {
            if (shiftIsNextWeek(m.getValue().getDate())) {
                Shift s = m.getValue();
                List<Employee> listOfEmployees = s.self_make();// algorithm that choose employees for the shift
                createBuildConstraintsAndRemoveFromOpt(listOfEmployees, s.getShiftType(), s.getDate());  //add constraint for all the employees in this shift cause employee can work in 1 shift per day
            }
        }
    }
    //TODO: add build constraint and remove from negative shift optionals
    //add to specific role in this Shift
    public void addEmpToShift(int SID, RoleType role, Employee emp){
         (shifts.get(SID)).addEmpToShift(role, emp);
    }


    public void removeEmpFromShift(int SID, Employee emp) {
        Shift s = shifts.get(SID);
        s.removeEmpFromShift(emp);
        emp.getRole().forEach(roleType -> s.addToOptionals(emp,roleType));
        removeBuildConstraint(emp,s);
    }

    private void removeBuildConstraint(Employee emp, Shift s){
        List<Integer> l = new ArrayList<>();
        for (Map.Entry<Integer, TempConstraint> e : buildShiftConstraints.entrySet()) {   //remove buildConstraint
            TempConstraint c = e.getValue();
            if (c.getEID() == emp.getEID() && c.getDate().equals(s.getDate())) {
                l.add(c.getCID());
            }
        }
        l.forEach(integer -> {
            buildShiftConstraints.remove(integer);
            log.debug("Build constraint - CID: " + integer + " removed");
            if(s.getShiftType().equals(ShiftType.Morning)){
                Shift opShift = getShiftByDate(s.getDate(),ShiftType.Night);
                if(opShift!=null) emp.getRole().forEach(roleType -> opShift.addToOptionals(emp,roleType));
            } else{
                Shift opShift = getShiftByDate(s.getDate(),ShiftType.Morning);
                if(opShift!=null) emp.getRole().forEach(roleType -> opShift.addToOptionals(emp,roleType));
            }
        });
    }

    //when fire employee from the branch
    public void removeFireEmp(Employee emp) {
        for (Map.Entry<Integer, Shift> s : shifts.entrySet()) {
            s.getValue().removeFireEmp(emp);
        }
    }

    //when add new employee to the branch
    public void addToOptionals(Employee emp, RoleType role) {
        for (Map.Entry<Integer, Shift> s : shifts.entrySet()) {
            s.getValue().addToOptionals(emp, role);
        }
    }

    public List<Shift> getShifts(LocalDate until) {
        ArrayList<Shift> filterShifts = new ArrayList<>();
        for (Map.Entry<Integer, Shift> m : shifts.entrySet()) {
            if (m.getValue().getDate().isAfter(LocalDate.now()) && m.getValue().getDate().isBefore(until)) {
                filterShifts.add(m.getValue());
            }
        }
        return filterShifts;
    }

    public List<Shift> getMyShifts(Employee emp) {
        List<Shift> list = new ArrayList<>();
        for (Map.Entry<Integer, Shift> s : shifts.entrySet()) {
            if (s.getValue().getEmployees().get(emp) != null)  //if EID is in this Shift
                list.add(s.getValue());
        }
        return list;
    }

    public List<Constraint> getMyConstraints(int EID) {
        ArrayList<Constraint> l = new ArrayList<>();
        for (Map.Entry<Integer, Constraint> c : constraints.entrySet()) {
            if (c.getValue().getEID() == EID)
                l.add(c.getValue());
        }
        return l;
    }

    public void updateAmountRole(int SID, RoleType role, int newAmount) {
        Shift s = shifts.get(SID);
        List<Employee> toRemove = s.updateRolesAmount(role, newAmount);
        toRemove.forEach(employee -> {removeBuildConstraint(employee,s);});
    }

    public void updateReasonConstraint(int CID, String newReason, int EID) {
        Constraint c = constraints.get(CID);
        c.updateReason(newReason);
        log.debug("CID: " + CID + " update his reason");
    }

    public void updateShiftTypeConstraint(int CID, ShiftType newType, int EID) {
        Constraint c = constraints.get(CID);
        c.updateShiftType(newType);
        log.debug("CID: " + CID + " update his shift type");
    }


    //remove emp from every shift's optionals
    private void RemoveEmpFromOptionals(int EID, Map<RoleType, List<Employee>> optionals) {
        for (Map.Entry<RoleType, List<Employee>> e : optionals.entrySet()) {
            List<Employee> l = e.getValue();
            if (!l.isEmpty()) {
                l.removeIf(emp -> emp.getEID() == EID);
            }
        }
        log.debug("EID: " + EID + " remove from optionals");
    }


    private void createBuildConstraintsAndRemoveFromOpt(List<Employee> listOfEmployees, ShiftType shiftType, LocalDate date)  {
        for (Employee emp : listOfEmployees) {
            TempConstraint bConstraint;
            if (shiftType.equals(ShiftType.Morning)) {
                Shift s  = getShiftByDate(date,ShiftType.Night);
                if (s != null)
                    s.removeEmpFromOptionals(emp);
                bConstraint = new TempConstraint(constraintCounter++, emp.getEID(), date, ShiftType.Night, "Work in morning shift this day");
            } else {
                Shift s  = getShiftByDate(date,ShiftType.Morning);
                if (s != null)
                    s.removeEmpFromOptionals(emp);
                bConstraint = new TempConstraint(constraintCounter++, emp.getEID(), date, ShiftType.Morning, "Work in Night shift this day");
            }
            buildShiftConstraints.put(bConstraint.getCID(), bConstraint);
        }
    }

    private boolean shiftIsNextWeek(LocalDate shiftDate) {   //shift date is between next sunday to next saturday
        LocalDate nextSunday = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        LocalDate nextSaturday = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SATURDAY)).plusWeeks(1);
        return !(shiftDate.isBefore(nextSunday) || shiftDate.isAfter(nextSaturday));
    }

    //return null if not exists
    private Shift getShiftByDate(LocalDate date, ShiftType shiftType) {
        for (Map.Entry<Integer, Shift> m : shifts.entrySet()) {
            Shift s = m.getValue();
            if (s.getDate().compareTo(date) == 0 & s.getShiftType() == shiftType)
                return s;
        }
        return null;
    }



    public boolean shiftAlreadyCreated(LocalDate date, ShiftType shiftType) {
        for (Map.Entry<Integer, Shift> shift : shifts.entrySet()) {
            if (shift.getValue().getDate().equals(date) && shift.getValue().getShiftType().equals(shiftType))
                return true;
        }
        return false;
    }

    public boolean constraintIsExist(int CID) {
        Constraint c = constraints.get(CID);
        return c != null;
    }


    private String checkIfAmountNegative(Map<RoleType, Integer> defaultShifts){
        for (Map.Entry<RoleType, Integer> e : defaultShifts.entrySet()) {
            if (e.getValue() < 0) {
                log.error("Role amount for role: " + e.getKey() + " is negative: " + e.getValue());
                return ("Role amount for role: " + e.getKey() + " is negative");
            }
            if (e.getKey().equals(RoleType.ShiftManager) && e.getValue() != 1) {
                log.error("Shift Manager amount is not legal: " + e.getValue());
                return ("Shift Manager amount is not legal: " + e.getValue());
            }
        }
        return null;
    }

    private Map<RoleType, List<Employee>> deepCopy (Map<RoleType, List<Employee>> optionals) {
        Map<RoleType, List<Employee>> copy = new HashMap<>();
        optionals.forEach((roleType, employees) -> {
            List<Employee> cloneL = new ArrayList<>(employees);
            copy.put(roleType, cloneL);
        });
        return copy;
    }


    public boolean optionalIsEmpty(int SID){
        return shifts.get(SID).optionalIsEmpty();
    }

    public boolean checkIfSIDExist(int SID){
        return shifts.containsKey(SID);
    }

    public boolean EIDIsOptionForSID(int SID, Employee emp){
        return shifts.get(SID).EIDIsOptionForSID(emp);
    }
    //can work in this shift - is optional and role is not full
    public boolean canWork(int SID, Employee emp, RoleType role){
        return shifts.get(SID).canWork(emp,role);
    }

    public boolean wasSelfMake(LocalDate date ,ShiftType shiftType){
        return Objects.requireNonNull(getShiftByDate(date, shiftType)).WasSelfMake();
    }

    public boolean shiftIsEmpty(int SID){
        return shifts.get(SID).getEmployees().isEmpty();
    }

    public boolean EIDWorkInSID(int SID, Employee emp){
        return shifts.get(SID).getEmployees().containsKey(emp);
    }

    public boolean hasShiftManager(LocalDate date, ShiftType shiftType){
        return Objects.requireNonNull(getShiftByDate(date, shiftType)).HasShiftManager();
    }
    //---------------------------------------------getters------------------------------------------------------

    public Map<Integer, Shift> getShifts() {
        return shifts;
    }

    public Map<Integer, Constraint> getConstraints() {
        return constraints;
    }

    public Map<Integer, TempConstraint> getBuildShiftConstraints() {
        return buildShiftConstraints;
    }

    public Map<ShiftType, Map<RoleType, Integer>> getDefaultShifts() {
        return defaultShifts;
    }

    public boolean hasDefaultShifts() {
        return !defaultShifts.isEmpty();
    }
}