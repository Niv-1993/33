package Business.ShiftPKG;

import Business.Type.RoleType;
import Business.Type.ShiftType;
import Database.Database;

import java.time.LocalDate;
import java.util.*;

public class Shift {

    //-------------------------------------fields------------------------------------
    private int SID;
    private Map<Integer, String[]> employees;
    private Map<RoleType, Integer> rolesAmount;
    private Map<RoleType, List<String[]>> optionals;
    private boolean complete;
    private LocalDate date;
    private ShiftType shiftType;

    //------------------------------------constructor--------------------------------
    public Shift(Map<RoleType, Integer> rolesAmount, Map<RoleType, List<String[]>> optionals, LocalDate date, ShiftType shiftType) {
        Database db = Database.getInstance();
        this.SID = db.addShift();
        employees = new HashMap<>();
        this.rolesAmount = rolesAmount;
        this.optionals = optionals;
        this.complete = false;
        this.date = date;
        this.shiftType = shiftType;
    }

    //--------------------------------------methods----------------------------------

    public List<Integer> self_make() throws Exception {
        boolean comp = true;
        for (Map.Entry<RoleType, Integer> e : rolesAmount.entrySet()) {
            RoleType role = e.getKey();
            int amount = e.getValue();
            while (amount > 0) {
                if (!optionals.get(role).isEmpty()) {
                    comp = false;
                    break;
                } else {
                    String[] emp = optionals.get(role).remove(0);  // delete from optionals
                    employees.put(Integer.parseInt(emp[0]), new String[]{role.name(), emp[1]});  //add to employees
                    RemoveEmpFromOptionals(Integer.parseInt(emp[0]));
                    amount--;
                }
            }
        }
        complete = comp;
        return new ArrayList<>(employees.keySet());
    }

    public void removeEmpFromShift(int EID) throws Exception {
        String[] emp = employees.remove(EID);  //return null if EID isn't in this shift
        if (emp == null)
            throw new Exception("EID: " + EID + " isn't in this shift");
        complete = false;
    }

    public void addEmpToShift(int EID, RoleType role, String name) throws Exception {
        //check if EID can work in this shift (in optionals)
        List<String[]> list = optionals.get(role);
        boolean isInEmployees = false;
        for (String[] s : list) {
            if (Integer.parseInt(s[0]) == EID && s[1].equals(name)) {
                isInEmployees = true;
                break;
            }
        }
        if (!isInEmployees)
            throw new Exception("EID: " + EID + " isn't option to be " + role + " in this shift");
        //check if there is empty role for this roleType in rolesAmount
        if (roleIsFull(role))
            throw new Exception("all the options for Role: " + role + " are close");
        employees.put(EID, new String[]{role.name(), name});
    }

    public void updateRolesAmount(RoleType role, int newAmount) {
        rolesAmount.replace(role, newAmount);
    }

    //for tests
    public boolean employeeExists(int EID) {
        return employees.containsKey(EID);
    }

    private boolean roleIsFull(RoleType role) {
        int amount = rolesAmount.get(role);
        long count = employees.entrySet().stream().filter(x -> x.getValue()[0].equals((role.name()))).count();
        return count >= amount;
    }

    private void RemoveEmpFromOptionals(int EID) {
        for (Map.Entry<RoleType, List<String[]>> e : optionals.entrySet()) {
            List<String[]> l = e.getValue();
            for (String[] arr : l) {
                if (Integer.parseInt(arr[0]) == EID) {
                    l.remove(arr);
                }
            }
        }
    }

    //-------------------getters&setters----------------------------------------
    public LocalDate getDate() {
        return date;
    }

    public ShiftType getShiftType() {
        return shiftType;
    }

    public Map<Integer, String[]> getEmployees() {
        return employees;
    }

    public int getSID() {
        return SID;
    }

    public Map<RoleType, Integer> getRolesAmount() {
        return rolesAmount;
    }
}
