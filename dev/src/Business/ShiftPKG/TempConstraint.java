package Business.ShiftPKG;

import Business.Type.ShiftType;
import org.apache.log4j.Logger;
import java.time.LocalDate;

public class TempConstraint extends Constraint {
    final static Logger log = Logger.getLogger(TempConstraint.class);
    //-------------------------------------fields------------------------------------
    private LocalDate date;

    //------------------------------------constructor--------------------------------
    TempConstraint(int EID, LocalDate date,ShiftType shiftType,String reason) throws Exception {
        super(EID,shiftType,reason);
        if(LocalDate.now().compareTo(date)>0){
            log.error("date : " + date +" is from the past");
            throw new Exception("date : " + date +" is from the past");
        }
        this.date = date;
        log.debug("Temp constraint "+getCID()+" created");
    }
    //--------------------------------------methods----------------------------------

    @Override
    boolean relevant(LocalDate date, ShiftType shiftType) {
        return ((this.date.equals(date)) && (this.shiftType.equals(shiftType)));
    }

    public LocalDate getDate() {
        return date;
    }
}
