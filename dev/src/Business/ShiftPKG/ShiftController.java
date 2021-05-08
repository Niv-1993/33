package Business.ShiftPKG;

import Business.EmployeePKG.Employee;
import Business.Type.RoleType;
import Business.Type.ShiftType;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import DataAccess.ConstraintMapper;
import DataAccess.ShiftMapper;
import org.apache.log4j.Logger;

public class ShiftController {
    private final static Logger log = Logger.getLogger(ShiftController.class);
    //-------------------------------------fields------------------------------------

    private Map<ShiftType, Map<RoleType, Integer>> defaultShifts;

    //------------------------------------constructor---------------------------------

    public ShiftController() {
        defaultShifts = new HashMap<>();
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
        int CID = ConstraintMapper.getInstance().getNextCID();
        ConstConstraint newCon = new ConstConstraint(CID, EID, day, shiftType, reason);
        ConstraintMapper.getInstance().insertConstConstraint(newCon);
        log.debug("added new const constraint for EID: " + EID + ", Day: " + day.name() + " , ShiftType: " + shiftType);
    }

    public void addTempConstraint(Employee emp, LocalDate c_date, ShiftType shiftType, String reason) {
        Shift s = getShiftByDate(c_date, shiftType);
        int CID = ConstraintMapper.getInstance().getNextCID();
        TempConstraint newCon = new TempConstraint(CID, emp.getEID(), c_date, shiftType, reason);
        ConstraintMapper.getInstance().insertTempConstraint(newCon);
        s.removeEmpFromOptionals(emp);
        log.debug("added new const constraint for EID: " + emp.getEID() + ", Date: " + c_date + " , ShiftType: " + shiftType);
    }

    public void removeConstraint(int CID) {
        ConstraintMapper.getInstance().deleteConstraint(CID);
    }

    public void defaultShifts(Map<ShiftType, Map<RoleType, Integer>> defaultShifts) {
        this.defaultShifts = defaultShifts;
        log.debug("default shifts defined");
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
        int SID = ShiftMapper.getInstance().getNextSID();
        Shift s = new Shift(SID, rolesAmount, optionals, date, shiftType);
        if (s.HasShiftManager()) {
            List<Employee> shiftManager = s.insertShiftManager();
            createBuildConstraintsAndRemoveFromOpt(shiftManager, shiftType, date);  //create build constraint for shift manager
        }
        ShiftMapper.getInstance().insertNewShift(s);
        return s;
    }


    public void createShift(Map<RoleType, Integer> rolesAmount, LocalDate date, ShiftType shiftType, Map<RoleType, List<Employee>> optionals) {
        //delete from optionals by constraints
        optionals = deepCopy(optionals);
        createOptionals(optionals, date, shiftType);
        int SID = ShiftMapper.getInstance().getNextSID();
        Shift s = new Shift(SID, rolesAmount, optionals, date, shiftType);
        if (s.HasShiftManager()) {
            List<Employee> shiftManager = s.insertShiftManager();
            createBuildConstraintsAndRemoveFromOpt(shiftManager, shiftType, date);  //create build constraint for shift manager
        }
        ShiftMapper.getInstance().insertNewShift(s);
    }

    private void createOptionals(Map<RoleType, List<Employee>> optionals, LocalDate date, ShiftType shiftType) {
        Map<Integer, Constraint> constraints = ConstraintMapper.getInstance().selectAllConstraints();
        for (Map.Entry<Integer, Constraint> c : constraints.entrySet()) {
            if (c.getValue().relevant(date, shiftType))
                RemoveEmpFromOptionals(c.getValue().getEID(), optionals);
        }
    }

    private void loadOptionals(Map<RoleType, List<Employee>> ops, LocalDate date, ShiftType shiftType, Map<Employee, RoleType> employees) {
        createOptionals(ops,date,shiftType);
        employees.forEach((employee, roleType) -> RemoveEmpFromOptionals(employee.getEID(),ops));
    }


    //self make for all shifts that next week and not was self make
    public void selfMakeWeekShifts(Map<RoleType, List<Employee>> optionals) {
        List<Shift> shifts = ShiftMapper.getInstance().selectShiftsFromUntil(LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SUNDAY)), LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SATURDAY)).plusWeeks(1));
        loadShift(shifts,optionals);
        for (Shift s : shifts) {
            List<Employee> listOfEmployees = s.self_make();// algorithm that choose employees for the shift
            createBuildConstraintsAndRemoveFromOpt(listOfEmployees, s.getShiftType(), s.getDate());  //add constraint for all the employees in this shift cause employee can work in 1 shift per day
        }
    }

    //TODO: add build constraint and remove from negative shift optionals
    //add to specific role in this Shift
    public void addEmpToShift(int SID, RoleType role, Employee emp,Map<RoleType, List<Employee>> optionals) {
        Shift s = get(SID,optionals);
        s.addEmpToShift(role, emp);
    }

    private Shift get(int sid,Map<RoleType, List<Employee>> optionals) {
        Shift s = ShiftMapper.getInstance().getShift(sid);
        ArrayList<Shift> ls = new ArrayList<>();
        ls.add(s);
        loadShift(ls,optionals);
        if(ls.isEmpty())
            return null;
        return ls.get(0);
    }


    public void removeEmpFromShift(int SID, Employee emp,Map<RoleType, List<Employee>> optionals) {
        Shift s = get(SID,optionals);
        s.removeEmpFromShift(emp);
        emp.getRole().forEach(roleType -> s.addToOptionals(emp, roleType));
        removeBuildConstraint(emp, s);
    }

    private void removeBuildConstraint(Employee emp, Shift s) {
        List<Constraint> constraintsOfEmpInDateS = ConstraintMapper.getInstance().getConstraint(emp.getEID(), s.getDate());
        constraintsOfEmpInDateS.forEach(tempCon -> {
            log.debug("Build constraint - CID: " + tempCon.getCID() + " removed");
            ConstraintMapper.getInstance().deleteConstraint(tempCon.getCID());
            if (s.getShiftType().equals(ShiftType.Morning)) {
                Shift opShift = getShiftByDate(s.getDate(), ShiftType.Night);
                if (opShift != null) emp.getRole().forEach(roleType -> opShift.addToOptionals(emp, roleType));
            } else {
                Shift opShift = getShiftByDate(s.getDate(), ShiftType.Morning);
                if (opShift != null) emp.getRole().forEach(roleType -> opShift.addToOptionals(emp, roleType));
            }
        });
    }

    //when fire employee from the branch
    public void removeFireEmp(Employee emp,Map<RoleType, List<Employee>> optionals) {
        List<Shift> shifts = ShiftMapper.getInstance().selectShiftsFromUntil(LocalDate.now(), LocalDate.now().plusWeeks(2));
        loadShift(shifts,optionals);
        for (Shift s : shifts)
            s.removeFireEmp(emp);
    }

    //when add new employee to the branch
    public void addToOptionals(Employee emp, RoleType role,Map<RoleType, List<Employee>> optionals) {
        List<Shift> shifts = ShiftMapper.getInstance().selectShiftsFromUntil(LocalDate.now(), LocalDate.now().plusWeeks(2));
        loadShift(shifts,optionals);
        for (Shift s : shifts) {
            s.addToOptionals(emp, role);
        }
    }

    public List<Shift> getShifts(LocalDate until, Map<RoleType, List<Employee>> optionals) {
        List<Shift> ls = ShiftMapper.getInstance().selectShiftsFromUntil(LocalDate.now(), until);
        loadShift(ls,optionals);
        return ls;
    }

    private void loadShift(List<Shift> ls,Map<RoleType, List<Employee>> optionals){
        ls.forEach(shift -> {
            if (shift.getOptionals() == null) {
                Map<RoleType, List<Employee>> ops = deepCopy(optionals);
                loadOptionals(ops, shift.getDate(), shift.getShiftType(),shift.getEmployees());
                shift.setOptionals(ops);
                shift.updateComplete();
                shift.setHasShiftManager();
            }
        });
    }

    public List<Shift> getMyShifts(Employee emp,Map<RoleType, List<Employee>> optionals) {
        List<Shift> ls = ShiftMapper.getInstance().getShiftsOfEID(emp.getEID());
        loadShift(ls,optionals);
        return ls;
    }

    public List<Constraint> getMyConstraints(int EID) {
        return ConstraintMapper.getInstance().getConstraintsOfEID(EID);
    }

    public void updateAmountRole(int SID, RoleType role, int newAmount,Map<RoleType, List<Employee>> optionals) {
        Shift s = get(SID,optionals);
        List<Employee> toRemove = s.updateRolesAmount(role, newAmount);
        toRemove.forEach(employee -> {
            removeBuildConstraint(employee, s);
        });
    }

    //Not need EID
    public void updateReasonConstraint(int CID, String newReason, int EID) {
        ConstraintMapper.getInstance().updateReason(CID, newReason);
        log.debug("CID: " + CID + " update his reason");
    }

    //Not need EID
    public void updateShiftTypeConstraint(int CID, ShiftType newType, int EID) {
        ConstraintMapper.getInstance().updateShiftType(CID, newType.name());
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


    private void createBuildConstraintsAndRemoveFromOpt(List<Employee> listOfEmployees, ShiftType shiftType, LocalDate date) {
        for (Employee emp : listOfEmployees) {
            TempConstraint bConstraint;
            int CID = ConstraintMapper.getInstance().getNextCID();
            if (shiftType.equals(ShiftType.Morning)) {
                Shift s = getShiftByDate(date, ShiftType.Night);
                if (s != null)
                    s.removeEmpFromOptionals(emp);
                bConstraint = new TempConstraint(CID, emp.getEID(), date, ShiftType.Night, "Work in morning shift this day");
            } else {
                Shift s = getShiftByDate(date, ShiftType.Morning);
                if (s != null)
                    s.removeEmpFromOptionals(emp);
                bConstraint = new TempConstraint(CID, emp.getEID(), date, ShiftType.Morning, "Work in Night shift this day");
            }
            ConstraintMapper.getInstance().insertTempConstraint(bConstraint);
        }
    }

/*    private boolean shiftIsNextWeek(LocalDate shiftDate) {   //shift date is between next sunday to next saturday
        LocalDate nextSunday = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        LocalDate nextSaturday = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SATURDAY)).plusWeeks(1);
        return !(shiftDate.isBefore(nextSunday) || shiftDate.isAfter(nextSaturday));
    }*/

    //return null if not exists
    private Shift getShiftByDate(LocalDate date, ShiftType shiftType) {
        return ShiftMapper.getInstance().getShiftByDate(date, shiftType.name());
    }


    public boolean shiftAlreadyCreated(LocalDate date, ShiftType shiftType) {
        return (ShiftMapper.getInstance().getShiftByDate(date, shiftType.name())) != null;
    }

    public boolean constraintIsExist(int CID) {
        return (ConstraintMapper.getInstance().getConstraint(CID)) != null;
    }


/*    private String checkIfAmountNegative(Map<RoleType, Integer> defaultShifts) {
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
    }*/

    private Map<RoleType, List<Employee>> deepCopy(Map<RoleType, List<Employee>> optionals) {
        Map<RoleType, List<Employee>> copy = new HashMap<>();
        optionals.forEach((roleType, employees) -> {
            List<Employee> cloneL = new ArrayList<>(employees);
            copy.put(roleType, cloneL);
        });
        return copy;
    }


    public boolean optionalIsEmpty(int SID,Map<RoleType, List<Employee>> optionals) {
        return get(SID,optionals).optionalIsEmpty();
    }

    public boolean checkIfSIDExist(int SID,Map<RoleType, List<Employee>> optionals) {
        return get(SID,optionals) != null;
    }

    public boolean EIDIsOptionForSID(int SID, Employee emp,Map<RoleType, List<Employee>> optionals) {
        return get(SID,optionals).EIDIsOptionForSID(emp);
    }

    //can work in this shift - is optional and role is not full
    public boolean canWork(int SID, Employee emp, RoleType role,Map<RoleType, List<Employee>> optionals) {
        return get(SID,optionals).canWork(emp, role);
    }

    public boolean wasSelfMake(LocalDate date, ShiftType shiftType) {
        return Objects.requireNonNull(getShiftByDate(date, shiftType)).WasSelfMake();
    }

    public boolean shiftIsEmpty(int SID,Map<RoleType, List<Employee>> optionals) {
        return get(SID,optionals).getEmployees().isEmpty();
    }

    public boolean EIDWorkInSID(int SID, Employee emp,Map<RoleType, List<Employee>> optionals) {
        return get(SID,optionals).getEmployees().containsKey(emp);
    }

    public boolean hasShiftManager(LocalDate date, ShiftType shiftType) {
        return Objects.requireNonNull(getShiftByDate(date, shiftType)).HasShiftManager();
    }
    //---------------------------------------------getters------------------------------------------------------

/*    public Map<Integer, Shift> getShifts() {
        return shifts;
    }*/

    public Map<Integer, Constraint> getConstraints() {
        return ConstraintMapper.getInstance().selectAllConstraints();
    }

/*    public Map<Integer, TempConstraint> getBuildShiftConstraints() {
        return buildShiftConstraints;
    }

    public Map<ShiftType, Map<RoleType, Integer>> getDefaultShifts() {
        return defaultShifts;
    }*/

    public boolean hasDefaultShifts() {
        return !defaultShifts.isEmpty();
    }

}
