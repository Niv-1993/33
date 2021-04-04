package Business.ShiftPKG;

import Business.Type.RoleType;
import Business.Type.ShiftType;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;


public class ShiftController {
    final static Logger log = Logger.getLogger(ShiftController.class);


    //-------------------------------------fields------------------------------------
    private Map<Integer, Shift> shifts;
    private Map<Integer, Constraint> constraints;
    private Map<Integer, TempConstraint> buildShiftConstraints;
    private Map<ShiftType, Map<RoleType, Integer>> defaultShifts;
    private int shiftCounter;
    private int constraintCounter;
    //------------------------------------constructor--------------------------------

    public ShiftController() throws Exception {
        shifts = new HashMap<>();
        constraints = new HashMap<>();
        defaultShifts = new HashMap<>();
        shiftCounter = 0;
        constraintCounter = 0;
        buildShiftConstraints = new HashMap<>();
        //when will use DB shifts not empty so we need to create build constraints
        /*for (Map.Entry<Integer, Shift> m : shifts.entrySet()) {
            Shift s = m.getValue();
            createBuildConstraints(new ArrayList<>(s.getEmployees().keySet()), s.getShiftType(), s.getDate());
        }*/
        log.debug("finished constructor shiftController");
    }

    //--------------------------------------methods----------------------------------

    public Constraint addConstConstraint(int EID, DayOfWeek day, ShiftType shiftType, String reason) {
        //create constraint and add to constraints field
        Constraint newCon = new ConstConstraint(constraintCounter,EID, day, shiftType, reason);
        constraints.put(newCon.getCID(), newCon);
        constraintCounter++;
        log.debug("added new const constraint for EID: " + EID + ", Day: " + day.name() + " , ShiftType: " + shiftType);
        return newCon;
    }

    public Constraint addConstraint(int EID, LocalDate c_date, ShiftType shiftType, String reason) throws Exception {
        //create constraint and add to constraints field
        if(checkIfExists(c_date,shiftType)){
            log.error("EID: "+ EID +" try to add constraint for exists shift");
            throw new Exception("You can't add constraint for this shift please talk with perssonel manger");
        }
        Constraint newCon = new TempConstraint(constraintCounter,EID, c_date, shiftType, reason);
        constraints.put(newCon.getCID(), newCon);
        constraintCounter++;
        log.debug("added new const constraint for EID: " + EID + ", Date: " + c_date + " , ShiftType: " + shiftType);
        return newCon;
    }

    public Constraint removeConstraint(int CID) throws Exception {
        Constraint c = constraints.remove(CID);
        if (c == null) {
            log.error("CID: " + CID + " is not exist");
            throw new Exception("Not legal CID :" + CID);
        }
        log.debug("CID: " + CID + " was removed");
        return c;
    }

    public void updateReasonConstraint(int CID, String newReason) throws Exception {
        constraintIsExist(CID);
        (constraints.get(CID)).updateReason(newReason);
        log.debug("CID: " + CID + " update his reason");
    }

    public void updateShiftTypeConstraint(int CID, ShiftType newType) throws Exception {
        constraintIsExist(CID);
        (constraints.get(CID)).updateShiftType(newType);
        log.debug("CID: " + CID + " update his shift type");
    }

    //when add new employee to the branch
    public void addToOptionals(int EID, String name, List<RoleType> role) {
        for (Map.Entry<Integer, Shift> s : shifts.entrySet()) {
            s.getValue().addToOptionals(EID, name, role);
        }
    }

    //wehen fire employee from the branch
    public void removeFireEmp(int EID, String name) {
        for (Map.Entry<Integer, Shift> s : shifts.entrySet()) {
            s.getValue().removeFireEmp(EID, name);
        }
    }

    public void defaultShifts(Map<ShiftType, Map<RoleType, Integer>> defaultShifts) throws Exception {
        for (Map.Entry<ShiftType, Map<RoleType, Integer>> e : defaultShifts.entrySet()) {
            checkIfAmountNegative(e.getValue());
        }
        this.defaultShifts = defaultShifts;
        log.debug("default shifts defined");
    }

    public Shift createDefaultShift(LocalDate date, ShiftType shiftType, Map<RoleType, List<String[]>> optionals) throws Exception {
        return createShift(defaultShifts.get(shiftType), date, shiftType, optionals);
    }


    public Shift createShift(Map<RoleType, Integer> rolesAmount, LocalDate date, ShiftType shiftType, Map<RoleType, List<String[]>> optionals) throws Exception {
        //delete from optionals by constraints
        checkIfAmountNegative(rolesAmount);
        for (Map.Entry<Integer, Constraint> c : constraints.entrySet()) {
            if (c.getValue().relevant(date, shiftType))
                RemoveEmpFromOptionals(c.getValue().getEID(), optionals);
        }
        for (Map.Entry<Integer, TempConstraint> c : buildShiftConstraints.entrySet()) {
            if (c.getValue().relevant(date, shiftType)) {
                RemoveEmpFromOptionals(c.getValue().getEID(), optionals);
            }
        }
        Shift s = new Shift(shiftCounter++,rolesAmount, optionals, date, shiftType);
        shifts.put(s.getSID(), s);
        List<Integer> listOfEmployees = s.self_make(); // algorithm that choose employees for the shift
        //add constraint for all the employees in this shift cause employee can work in 1 shift per day
        createBuildConstraints(listOfEmployees, s.getShiftType(), s.getDate());
        return s;
    }



    //remove emp from every shift's optionals
    private void RemoveEmpFromOptionals(int EID, Map<RoleType, List<String[]>> optionals) {
        for (Map.Entry<RoleType, List<String[]>> e : optionals.entrySet()) {
            List<String[]> l = e.getValue();
            if(l!=null) {
                for (String[] arr : l) {
                    if (Integer.parseInt(arr[0]) == EID) {
                        l.remove(arr);
                    }
                }
            }
        }
        log.debug("EID: " + EID + " remove from optionals");
    }

    public boolean checkIfExists(LocalDate date, ShiftType shiftType) {
        for (Map.Entry<Integer, Shift> s : shifts.entrySet()) {
            if (s.getValue().getDate().equals(date) && s.getValue().getShiftType().equals(shiftType))
                return true;
        }
        return false;
    }

    public void removeEmpFromShift(int SID, int EID) throws Exception {
        shiftIsExsist(SID);
        Shift s = shifts.get(SID);
        s.removeEmpFromShift(EID);
        for (Map.Entry<Integer, TempConstraint> e : buildShiftConstraints.entrySet()) {   //remove buildConstraint
            TempConstraint c = e.getValue();
            if (c.getEID() == EID && c.getDate().equals(s.getDate())) {
                buildShiftConstraints.remove(c);
                log.debug("Build constraint - CID: "+ c.getCID()+ " removed");
            }
        }
    }

    //add to specific role in this Shift
    public void addEmpToShift(int SID, int EID, RoleType role, String name) throws Exception {
        shiftIsExsist(SID);
        (shifts.get(SID)).addEmpToShift(EID, role, name);
    }

    public void updateAmountRole(int SID, RoleType role, int newAmount) throws Exception {
        shiftIsExsist(SID);
        (shifts.get(SID)).updateRolesAmount(role, newAmount);
    }

    public List<Shift> getShiftsAndEmployees() {
        return new ArrayList<>(shifts.values());
    }

    public List<Shift> getOnlyEmployeeShifts(int EID) {
        List<Shift> list = new ArrayList<>();
        for (Map.Entry<Integer, Shift> s : shifts.entrySet()) {
            if (s.getValue().getEmployees().get(EID) != null)  //if EID is in this Shift
                list.add(s.getValue());
        }
        return list;
    }

    public List<Constraint> getOnlyEmployeeConstraints(int EID) {
        ArrayList<Constraint> l = new ArrayList<>();
        for (Map.Entry<Integer, Constraint> c : constraints.entrySet()) {
            if (c.getValue().getEID() == EID)
                l.add(c.getValue());
        }
        return l;
    }


    private void constraintIsExist(int CID) throws Exception {
        Constraint c = constraints.get(CID);
        if (c == null) {
            log.error("CID: " + CID + " is not exist");
            throw new Exception("Not legal CID: " + CID);
        }
    }

    private void shiftIsExsist(int SID) throws Exception {
        Shift s = shifts.get(SID);
        if (s == null) {
            log.error("SID: " + SID + " is not exists");
            throw new Exception("Not legal SID: " + SID);
        }
    }

    private void createBuildConstraints(List<Integer> listOfEmployees, ShiftType shiftType, LocalDate date) throws Exception {
        for (Integer EID : listOfEmployees) {
            if (shiftType.equals(ShiftType.Morning)) {
                TempConstraint bConstraint = new TempConstraint(constraintCounter, EID, date, ShiftType.Night, "Work in morning shift this day");
                buildShiftConstraints.put(bConstraint.getCID(), bConstraint);
            } else {
                TempConstraint bConstraint = new TempConstraint(constraintCounter, EID, date, ShiftType.Morning, "Work in Night shift this day");
                buildShiftConstraints.put(bConstraint.getCID(), bConstraint);
            }
        }
    }

    private void checkIfAmountNegative(Map<RoleType, Integer> defaultShifts) throws Exception {
        for (Map.Entry<RoleType, Integer> e : defaultShifts.entrySet()) {
            if (e.getValue() < 0) {
                log.error("Role amount for role: "+ e.getKey() + " is negative: " +e.getValue());
                throw new Exception("Role amount for role: " + e.getKey() + " is negative");
            }

        }
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
}