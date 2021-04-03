package Business.ShiftPKG;

import Business.Type.ShiftType;
import Database.Database;
import org.apache.log4j.Logger;
import java.time.LocalDate;

public abstract class Constraint {
    final static Logger log = Logger.getLogger(Constraint.class);
    //--------------------------------fields-----------------------------
    private int CID;
    private int EID;
    protected ShiftType shiftType;
    private String reason;

    //------------------------------constructor---------------------------
    Constraint(int EID,ShiftType shiftType,String reason){
        Database database = Database.getInstance();
        this.CID = database.addConstraint();
        this.EID = EID;
        this.shiftType = shiftType;
        this.reason = reason;
    }
    //---------------------------------methods----------------------------

    abstract boolean relevant(LocalDate date, ShiftType shiftType);

    public void updateReason(String newReason){
        //update database
        reason = newReason;
    }

    public void updateShiftType(ShiftType newShiftType){
        //update database
        shiftType = newShiftType;
    }

    //-------------------------------getters&setters----------------------
    public int getCID() {
        return CID;
    }
    public int getEID() {return EID;}
    public ShiftType getShiftType() {return shiftType;}
    public String getReason() {return reason;}



}
