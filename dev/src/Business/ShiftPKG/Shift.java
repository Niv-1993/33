package Business.ShiftPKG;

import Business.Type.RoleType;
import Business.Type.ShiftType;
import org.apache.log4j.Logger;
import java.time.LocalDate;
import java.util.*;

public class Shift {
    private final static Logger log = Logger.getLogger(Shift.class);
    //-------------------------------------fields------------------------------------

    private final int SID;
    private Map<Integer, String[]> employees;  // [0]->RoleType in String, [1]->name
    private Map<RoleType, Integer> rolesAmount;
    private Map<RoleType, List<String[]>> optionals; //[0]-ID, [1]-Name
    private boolean complete;  //employees == roles amount
    private LocalDate date;
    private ShiftType shiftType;
    private boolean wasSelfMake;
    private boolean hasShiftManager;

    //------------------------------------constructor--------------------------------

    public Shift(int SID, Map<RoleType, Integer> rolesAmount, Map<RoleType, List<String[]>> optionals, LocalDate date, ShiftType shiftType) throws Exception {
        this.SID = SID;
        employees = new HashMap<>();
        checkIfAmountNegAndHasSM(rolesAmount);  //check if legal (not negative)
        this.rolesAmount = rolesAmount;
        this.optionals = optionals;
        this.complete = false;
        if (LocalDate.now().compareTo(date) > 0) {
            log.error("date: " + date + "is from the past");
            throw new Exception("date : " + date + " is from the past");
        }
        this.date = date;
        this.shiftType = shiftType;
        this.wasSelfMake = false;
        log.debug("shift: " + SID + " created");
        if(optionals.containsKey(RoleType.ShiftManager))
            hasShiftManager = !optionals.get(RoleType.ShiftManager).isEmpty();
    }


    //-------------------------------------methods----------------------------------

    //shift controller call this function after create shift to insert shift manager to this shift
    public List<Integer> insertShiftManager() {
        String[] sm = optionals.get(RoleType.ShiftManager).remove(0);
        employees.put(Integer.parseInt(sm[0]), new String[]{RoleType.ShiftManager.name(), sm[1]});
        List<Integer> listOfSM = new LinkedList<>();
        listOfSM.add(Integer.parseInt(sm[0]));
        return listOfSM;
    }

    public List<Integer> self_make() throws Exception {
        if (!wasSelfMake) {
            for (Map.Entry<RoleType, Integer> e : rolesAmount.entrySet()) {
                if (e.getKey().equals(RoleType.ShiftManager)) continue;
                RoleType role = e.getKey();
                int amount = e.getValue();
                while (amount > 0) {
                    if (optionals.get(role) == null || optionals.get(role).isEmpty()) {
                        break;
                    } else {
                        String[] emp = optionals.get(role).remove(0);  // delete from optionals
                        employees.put(Integer.parseInt(emp[0]), new String[]{role.name(), emp[1]});  //add to employees
                        RemoveEmpFromOptionals(Integer.parseInt(emp[0]));
                        amount--;
                    }
                }
            }
            complete = isComplete();
            wasSelfMake = true;
            return new ArrayList<>(employees.keySet());
        }
        return new ArrayList<>();
    }

    public void addEmpToShift(int EID, RoleType role, String name) throws Exception {
        //check if EID can work in this shift (in optionals)
        List<String[]> list = optionals.get(role);
        boolean canWork = false;
        if (list != null) {
            for (String[] s : list) {
                if (Integer.parseInt(s[0]) == EID && s[1].equals(name)) {
                    canWork = true;
                    break;
                }
            }
        }
        if (!canWork) {
            log.error("EID: " + EID + " isn't option to be: " + role + " in SID: " + SID);
            throw new Exception("Employee ID: " + EID + " isn't option to be " + role + " in this shift");
        }
        //check if there is empty role for this roleType in rolesAmount
        if (roleIsFull(role)) {
            log.error("all the option for Role: " + role + " are close");
            throw new Exception("All the options for Role: " + role + " are close");
        }
        employees.put(EID, new String[]{role.name(), name});
        RemoveEmpFromOptionals(EID);
        complete = isComplete();
        hasShiftManager = role.equals(RoleType.ShiftManager);
        log.debug("EID: " + EID + "added to SID: " + SID);
    }

    public void removeEmpFromShift(int EID, List<RoleType> roles) throws Exception {
        String[] emp = employees.remove(EID);  //return null if EID isn't in this shift
        if (emp == null) {
            log.error("EID: " + EID + " isn't in this shift");
            throw new Exception("Employee ID: " + EID + " isn't in this shift");
        }
        complete = false;
        if (RoleType.valueOf(emp[0]).equals(RoleType.ShiftManager)) {
            hasShiftManager = false;
        }
        roles.forEach(roleType -> {
            optionals.get(roleType).add(new String[]{String.valueOf(EID), emp[1]});
        });
        log.debug("EID: " + EID + "removed from SID: " + SID);
    }

    public void removeFireEmp(int EID, String name) {
        String[] s = employees.remove(EID);  //remove employee if he is in this shift
        if (s != null) {
            complete = false;
            if (RoleType.valueOf(s[0]).equals(RoleType.ShiftManager)) {
                hasShiftManager = false;
            }
        }
        for (Map.Entry<RoleType, List<String[]>> o : optionals.entrySet()) {
            List<String[]> listOfEmp = o.getValue();
            if (listOfEmp != null) {
                String[] toRemove = {String.valueOf(EID), name};
                listOfEmp.remove(toRemove);
            }
        }
        log.debug("EID: " + EID + " removed(fire) from SID: " + SID);
    }

    public String[] addToOptionals(int EID, String name, RoleType role) {
        String[] s = {String.valueOf(EID), name};
        optionals.get(role).add(s);
        return s;
    }

    public void RemoveEmpFromOptionals(int EID) {
        for (Map.Entry<RoleType, List<String[]>> e : optionals.entrySet()) {
            List<String[]> l = e.getValue();
            if (l != null) {
                l.removeIf(arr -> Integer.parseInt(arr[0]) == EID);
            }
        }
    }

    public void updateName(int eid, List<RoleType> roles, String newName) {
        if(employees.containsKey(eid))
            employees.get(eid)[1] = newName;
        roles.forEach(roleType -> {
            List<String[]> emps = optionals.get(roleType);
            emps.forEach(pair -> {
                if(Integer.parseInt(pair[0]) == eid)
                    pair[1] = newName;
            });
        });
    }

    public void updateRolesAmount(RoleType role, int newAmount) throws Exception {
        if (newAmount < 0) {
            throw new Exception("The new amount for role: " + role + " is negative");
        }
        rolesAmount.replace(role, newAmount);
    }

    private boolean roleIsFull(RoleType role) {
        int amount = rolesAmount.get(role);
        long count = employees.entrySet().stream().filter(x -> x.getValue()[0].equals((role.name()))).count();
        return count >= amount;
    }

    private void checkIfAmountNegAndHasSM(Map<RoleType, Integer> defaultShifts) throws Exception {
        for (Map.Entry<RoleType, Integer> e : defaultShifts.entrySet()) {
            if (e.getValue() < 0) {
                log.error("Role amount for role: " + e.getKey() + " is negative: " + e.getValue());
                throw new Exception("Role amount for role: " + e.getKey() + " is negative");
            }
            if (e.getKey().equals(RoleType.ShiftManager) && e.getValue()!=1) {
                log.error("Shift Manager amount is not legal: "+ e.getValue());
                throw new Exception("Shift Manager amount is not legal: "+ e.getValue());
            }
        }
    }

    private boolean isComplete() {
        int amount = 0;
        for (Map.Entry<RoleType, Integer> role : rolesAmount.entrySet())
            amount += role.getValue();
        return amount == employees.size();
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

    public boolean WasSelfMake() {
        return wasSelfMake;
    }

    public boolean HasShiftManager() {
        return hasShiftManager;
    }

}
