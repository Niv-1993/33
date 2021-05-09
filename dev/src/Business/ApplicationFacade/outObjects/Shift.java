package Business.ApplicationFacade.outObjects;


import Business.Type.RoleType;

import java.time.LocalDate;
import java.util.*;


public class Shift {
    public int SID;
    public LocalDate date;
    public String shiftType;
    public Map<Employee, String> employees;
    public Map<String, List<Employee>> optionals;
    public Map<String, Integer> rolesAmount;
    public String status;
    public boolean hasShiftManager;

    public Shift(Business.Employees.ShiftPKG.Shift shift, Map<Employee, String> employees, Map<String, List<Employee>> optionals) {
        this.SID = shift.getSID();
        this.date = shift.getDate();
        this.employees = Collections.unmodifiableMap(employees);
        this.shiftType = shift.getShiftType().name();
        this.optionals = Collections.unmodifiableMap(optionals);
        this.rolesAmount = Collections.unmodifiableMap(converterRolesA(shift.getRolesAmount()));
        this.status = (shift.getComplete()) ? "Full" : "*** Missing ***";
        this.hasShiftManager = shift.HasShiftManager();
    }

    public String toStringWithoutOptAndEmp() {
        return
                "----------------------------------------------------------------------\n" +
                        ((hasShiftManager) ? "" : "\t\t\t\t*** Missing Shift Manager ***\n") +
                        "Shift ID: " + SID +
                        "       Status: " + status +
                        "       Date: " + date +
                        "       ShiftType: '" + shiftType + '\'' + "\n" +
                        "Role amount:\n" + printRolesAmount();
    }


    @Override
    public String toString() {
        return "----------------------------------------------------------------------\n" +
                ((hasShiftManager) ? "" : "\t\t\t\t*** Missing Shift Manager ***\n") +
                "Shift ID: " + SID +
                "       Status: " + status +
                "       Date: " + date +
                "       ShiftType: '" + shiftType + '\'' + "\n" +
                "Employees:" + printEmployees();
    }

    public String toStringAll() {
        return
                "----------------------------------------------------------------------\n" +
                        ((hasShiftManager) ? "" : "\t\t\t\t*** Missing Shift Manager ***\n") +
                        "Shift ID: " + SID +
                        "       Status: " + status +
                        "       Date: " + date +
                        "       ShiftType: '" + shiftType + '\'' + "\n" +
                        "Employees:" + printEmployees() +
                        "Optionals:\n" + printOptionals() +
                        "Role amount:\n" + printRolesAmount() + "\n";
    }


    private String printOptionals() {
        StringBuilder opt = new StringBuilder();
        for (Map.Entry<String, List<Employee>> m : optionals.entrySet()) {
            if (rolesAmount.containsKey(m.getKey()) && rolesAmount.get(m.getKey()) > 0) {
                opt.append("\t").append(m.getKey()).append(": ");
                for (Employee emp : m.getValue())
                    opt.append("[").append(emp.EID).append(",").append(emp.name).append("] ");
                opt.append("\n");
            }
        }
        return opt.toString();
    }


    private String printRolesAmount() {
        StringBuilder ra = new StringBuilder();
        for (Map.Entry<String, Integer> m : rolesAmount.entrySet()) {
            if (m.getValue() != 0)
                ra.append("\t").append(m.getKey()).append(": ").append(m.getValue()).append("\n");
        }
        return ra.toString();
    }


    private String printEmployees() {
        if (employees.isEmpty()) return " None\n";
        StringBuilder emps = new StringBuilder();
        emps.append("\n");
        for (Map.Entry<String, Integer> role : rolesAmount.entrySet()) {
            if (role.getValue() > 0) {
                emps.append("\t").append(role.getKey()).append(": ");
                for (Map.Entry<Employee, String> m : employees.entrySet()) {
                    if (m.getValue().equals(role.getKey()))
                        emps.append("[").append(m.getKey().EID).append(",").append(m.getKey().name).append("] ");
                }
                emps.append("\n");
            }
        }
        return emps.toString();
    }

    private Map<String, Integer> converterRolesA(Map<RoleType, Integer> rolesA) {
        Map<String, Integer> ret = new HashMap<>();
        rolesA.forEach((key, value) -> ret.put(key.name(), value));
        return ret;
    }

}
