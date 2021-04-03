package Business.ShiftPKG;

import Business.Type.RoleType;
import Business.Type.ShiftType;
import Database.Database;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.util.*;

public class Shift {
    final static Logger log = Logger.getLogger(Shift.class);
    //-------------------------------------fields------------------------------------
    private int SID;
    private Map<Integer, String[]> employees;  // [0]->RoleType in String, [1]->name
    private Map<RoleType, Integer> rolesAmount;
    private Map<RoleType, List<String[]>> optionals; //[0]-ID, [1]-Name
    private boolean complete;
    private LocalDate date;
    private ShiftType shiftType;

    //------------------------------------constructor--------------------------------
    public Shift(Map<RoleType, Integer> rolesAmount, Map<RoleType, List<String[]>> optionals, LocalDate date, ShiftType shiftType) throws Exception {
        Database db = Database.getInstance();
        this.SID = db.addShift();
        employees = new HashMap<>();
        checkIfAmountNegative(rolesAmount);
        this.rolesAmount = rolesAmount;
        this.optionals = optionals;
        this.complete = false;
        if (LocalDate.now().compareTo(date) > 0) {
            log.error("date: " + date + "is from the past");
            throw new Exception("date : " + date + " is from the past");
        }
        this.date = date;
        this.shiftType = shiftType;
        log.debug("shift: " + SID + " created");
    }

    //-------------------------------------methods----------------------------------

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
        if (emp == null) {
            log.error("EID: " + EID + " isn't in this shift");
            throw new Exception("EID: " + EID + " isn't in this shift");
        }
        complete = false;
        log.debug("EID: " + EID + "removed from SID: " + SID);
    }

    public void addEmpToShift(int EID, RoleType role, String name) throws Exception {
        //check if EID can work in this shift (in optionals)
        List<String[]> list = optionals.get(role);
        boolean canWork = false;
        for (String[] s : list) {
            if (Integer.parseInt(s[0]) == EID && s[1].equals(name)) {
                canWork = true;
                break;
            }
        }
        if (!canWork) {
            log.error("EID: " + EID + "isn't option to be: " + role + " in SID: " + SID);
            throw new Exception("EID: " + EID + " isn't option to be " + role + " in this shift");
        }
        //check if there is empty role for this roleType in rolesAmount
        if (roleIsFull(role)) {
            log.error("all the option for Role: " + role + " are close");
            throw new Exception("all the options for Role: " + role + " are close");

        }
        employees.put(EID, new String[]{role.name(), name});
        RemoveEmpFromOptionals(EID);
        log.debug("EID: " + EID + "added to SID: " + SID);
    }

    public void updateRolesAmount(RoleType role, int newAmount) throws Exception {
        if (newAmount < 0) {
            throw new Exception("The new amount for role: " + role + " is negative");
        }
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

    public void addToOptionals(int EID, String name, List<RoleType> role) {
        for (RoleType roleType : role) {
            String[] s = {String.valueOf(EID), name};
            optionals.get(roleType).add(s);
        }
    }

    public void removeFireEmp(int EID, String name) {
        String[] s = employees.remove(EID);  //remove employee if he is in this shift
        if (s != null)
            complete = false;
        for (Map.Entry<RoleType, List<String[]>> o : optionals.entrySet()) {
            List<String[]> listOfEmp = o.getValue();
            String[] toRemove = {String.valueOf(EID), name};
            listOfEmp.remove(toRemove);
        }
        log.debug("EID: "+EID + " removed(fire) from SID: "+ SID);
    }

    private void checkIfAmountNegative(Map<RoleType, Integer> defaultShifts) throws Exception {
        for (Map.Entry<RoleType, Integer> e : defaultShifts.entrySet()) {
            if (e.getValue() < 0) {
                log.error("Role amount for role: "+ e.getKey() + " is negative: " +e.getValue());
                throw new Exception("Role amount for role: " + e.getKey() + " is negative");
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

    public Map<RoleType, List<String[]>> getOptionals() {
        return optionals;
    }

    public boolean getComplete() {
        return complete;
    }


}
