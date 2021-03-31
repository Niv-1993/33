package Business.ApplicationFacade.outObjects;

import Business.EmployeePKG.*;
import Business.Type.RoleType;

import java.util.*;

public class Employee {
    public int EID;
    public String name;
    public String role;
    public int[] bankAccount;
    public int[] terms;
    public List<Shift> shifts;
    public List<Constraint> constraints;
    public int salary;

    //Constructor for getOnlyEmployeeShiftsAndConstraints()
    public Employee(int EID, String name, RoleType role, List<Shift> shifts, List<Constraint> constraints) {
        this.EID = EID;
        this.name = name;
        this.role = role.name();
        this.constraints = Collections.unmodifiableList(constraints);
        this.shifts = Collections.unmodifiableList(shifts);
    }

    //Constructor for getEmployeeDetails();
    public Employee(int EID, String name, RoleType role, BankAccount bankAccount,int salary,  TermsOfEmployment terms) {
        this.EID = EID;
        this.name = name;
        this.role = role.name();
        this.bankAccount = new int[]{bankAccount.getAccountNum(), bankAccount.getBankBranch(), bankAccount.getBankID()};
        this.terms = new int[]{terms.getEducationFun(), terms.getDaysOff(), terms.getDaysOff()};
        this.salary = salary;
    }


    public boolean containsConst(int cid) {
        for (Constraint c : constraints)
            if (c.CID == cid) {
                return true;
            }
        return false;
    }
}
