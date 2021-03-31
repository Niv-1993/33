package Business.ShiftPKG;

import Business.Type.RoleType;
import Business.Type.ShiftType;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShiftController {
    //-------------------------------------fields------------------------------------
    private Map<Integer, Shift> shifts;
    private Map<Integer, Constraint> constraints;
    private Map<Integer, Constraint> buildShiftConstraints;

    //------------------------------------constructor--------------------------------

    public ShiftController(Map<Integer, Shift> shifts, Map<Integer, Constraint> constraints) {
        this.shifts = shifts;
        this.constraints = constraints;
        buildShiftConstraints = new HashMap<>(); //when create add build constraint for all employees in all shifts
    }

    //--------------------------------------methods----------------------------------

    public Constraint addConstConstraint(int EID, DayOfWeek day, ShiftType shiftType, String reason) {
        //create constraint and add to constraints field
        Constraint newCon = new ConstConstraint(EID, day, shiftType, reason);
        constraints.put(newCon.getCID(), newCon);
        return newCon;
    }

    public Constraint addConstraint(int EID, LocalDate c_date, ShiftType shiftType, String reason) {
        //create constraint and add to constraints field
        Constraint newCon = new TempConstraint(EID, c_date, shiftType, reason);
        constraints.put(newCon.getCID(), newCon);
        return newCon;
    }

    public Constraint removeConstraint(int CID) throws Exception {
        Constraint c = constraints.remove(CID);
        if (c == null)
            throw new Exception("CID: " + CID + " is not exists");
        return c;
    }

    public void updateReasonConstraint(int CID, String newReason) throws Exception {
        constraintIsExsist(CID);
        (constraints.get(CID)).updateReason(newReason);
    }

    public void updateShiftTypeConstraint(int CID, ShiftType newType) throws Exception {
        constraintIsExsist(CID);
        (constraints.get(CID)).updateShiftType(newType);
    }

    public Shift createShift(Map<RoleType, Integer> rolesAmount, LocalDate date, ShiftType shiftType, Map<RoleType, List<String[]>> optionals) throws Exception {
        //delete from optionals by constraints
        for (Map.Entry<Integer, Constraint> c : constraints.entrySet()) {
            if (c.getValue().relevant(date, shiftType))
                RemoveEmpFromOptionals(c.getValue().getEID(), optionals);
        }
        for (Map.Entry<Integer, Constraint> c : buildShiftConstraints.entrySet()) {
            if (c.getValue().relevant(date, shiftType)) {
                RemoveEmpFromOptionals(c.getValue().getEID(), optionals);
            }
        }
        Shift s = new Shift(rolesAmount, optionals, date, shiftType);
        shifts.put(s.getSID(), s);
        List<Integer> listOfEmployees = s.self_make(); // algorithm that choose employees for the shift
        //add constraint for all the employees in this shift cause employee can work in 1 shift per day
        for (Integer EID : listOfEmployees) {
            if (shiftType.equals(ShiftType.Morning)) {
                Constraint bConstraint = new TempConstraint(EID, date, ShiftType.Night, "Work in morning shift this day");
                buildShiftConstraints.put(bConstraint.getCID(), bConstraint);
            } else {
                Constraint bConstraint = new TempConstraint(EID, date, ShiftType.Morning, "Work in Night shift this day");
                buildShiftConstraints.put(bConstraint.getCID(), bConstraint);
            }
        }
        return s;
    }

    private void RemoveEmpFromOptionals(int EID, Map<RoleType, List<String[]>> optionals) {
        for (Map.Entry<RoleType, List<String[]>> e : optionals.entrySet()) {
            List<String[]> l = e.getValue();
            for (String[] arr : l) {
                if (Integer.parseInt(arr[0]) == EID) {
                    l.remove(arr);
                }
            }
        }
    }

 /*   public boolean checkIfExists(LocalDate date, ShiftType shiftType) {
        for (Map.Entry<Integer, Shift> s : shifts.entrySet()) {
            if (s.getValue().getDate().equals(date) && s.getValue().getShiftType().equals(shiftType))
                return true;
        }
        return false;
    }*/

    public void removeEmpFromShift(int SID, int EID) throws Exception {
        shiftIsExsist(SID);
        (shifts.get(SID)).removeEmpFromShift(EID);
    }

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

    public void LoadData(int BID) {

    }

    private void constraintIsExsist(int CID) throws Exception {
        Constraint c = constraints.get(CID);
        if (c == null)
            throw new Exception("CID: " + CID + " is not exists");
    }

    private void shiftIsExsist(int SID) throws Exception {
        Shift s = shifts.get(SID);
        if (s == null)
            throw new Exception("SID: " + SID + " is not exists");
    }

}
