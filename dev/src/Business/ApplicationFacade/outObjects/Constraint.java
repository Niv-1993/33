package Business.ApplicationFacade.outObjects;

import Business.Type.ShiftType;

public class Constraint {
    public int CID;
    public int EID;
    public String shiftType;
    public String reason;

    public Constraint(int CID, int EID, String reason, ShiftType shiftType){
        this.CID = CID;
        this.EID = EID;
        this.reason = reason;
        this.shiftType = shiftType.name();
    }
    public Constraint(Business.EmployeePKG.Constraint c){
        this.CID = c.getCID();
        this.EID = c.getEID();
        this.reason = c.getReason();
        this.shiftType = c.getShiftType().name();
    }
}
