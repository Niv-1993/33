package Business.ShiftPKG;

import Business.Type.ShiftType;
import java.time.LocalDate;

public class TempConstraint extends Constraint {
    //-------------------------------------fields------------------------------------
    private LocalDate date;

    //------------------------------------constructor--------------------------------
    TempConstraint(int EID, LocalDate date,ShiftType shiftType,String reason){
        super(EID,shiftType,reason);
        this.date = date;
    }
    //--------------------------------------methods----------------------------------

    @Override
    boolean relevant(LocalDate date, ShiftType shiftType) {
        return ((this.date.equals(date)) && (this.shiftType.equals(shiftType)));
    }
}
