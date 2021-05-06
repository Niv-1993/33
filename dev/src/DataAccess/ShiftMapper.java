package DataAccess;

import Business.ShiftPKG.Shift;

import java.util.HashMap;

public class ShiftMapper extends Mapper {
    private static ShiftMapper shiftMapper = null;
    private HashMap<Integer, Shift> shifts;

    public static ShiftMapper getInstance() {
        if (shiftMapper == null)
            shiftMapper = new ShiftMapper();
        return shiftMapper;
    }

    private ShiftMapper(){
        super();
        shifts = new HashMap<>();
    }
    public HashMap<Integer, Shift> getShifts() {
        return shifts;
    }


}
