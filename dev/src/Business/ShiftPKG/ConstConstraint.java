package Business.ShiftPKG;

import Business.Type.ShiftType;
import org.apache.log4j.Logger;
import java.time.LocalDate;
import java.time.DayOfWeek;

public class ConstConstraint extends Constraint {
    final static Logger log = Logger.getLogger(ConstConstraint.class);
    //-------------------------------------fields------------------------------------
    private DayOfWeek day;

    //------------------------------------constructor--------------------------------
    ConstConstraint(int EID, DayOfWeek day, ShiftType shiftType, String reason) {
        super(EID, shiftType, reason);
        this.day = day;
        log.debug("Temp constraint "+getCID()+" created");
    }

    //--------------------------------------methods----------------------------------

    @Override
    boolean relevant(LocalDate date, ShiftType shiftType) {
        return ((date.getDayOfWeek().equals(day)) && ((this.shiftType).equals(shiftType)));
    }


}
