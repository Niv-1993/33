package Business.ShiftPKG;

import Business.Type.ShiftType;
import java.time.LocalDate;
import java.time.DayOfWeek;

public class ConstConstraint extends Constraint {

    //-------------------------------------fields------------------------------------
    private DayOfWeek day;

    //------------------------------------constructor--------------------------------
    ConstConstraint(int EID, DayOfWeek day, ShiftType shiftType, String reason) {
        super(EID, shiftType, reason);
        this.day = day;
    }

    //--------------------------------------methods----------------------------------

    @Override
    boolean relevant(LocalDate date, ShiftType shiftType) {
        return ((date.getDayOfWeek().equals(day)) && ((this.shiftType).equals(shiftType)));
    }


}
