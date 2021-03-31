package Business.ApplicationFacade.outObjects;


import Business.Type.ShiftType;
import java.time.LocalDate;
import java.util.*;

public class Shift {
    public int SID;
    public LocalDate date;
    public String shiftType;
    public Map<Integer,String[]> employees;

    public Shift(int SID, LocalDate date, ShiftType shiftType, Map<Integer,String[]> employees) {
        this.SID = SID;
        this.date = date;
        this.employees = Collections.unmodifiableMap(employees);
        this.shiftType = shiftType.name();
    }

}
