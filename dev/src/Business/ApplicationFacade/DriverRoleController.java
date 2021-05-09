package Business.ApplicationFacade;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class DriverRoleController implements iDriverRoleController {
    private iManagerRoleController mc;

    public DriverRoleController(iManagerRoleController m) {
        mc = m;
    }

    public void addNewDriver(int newEID, String name, int[] bankDetails, int salary, String role, LocalDate startWorkDate, int[] terms, int license) {

    }

    public boolean checkAvailableStoreKeeperAndShifts(int BID, LocalDate date, LocalTime leavingTime) {
        mc.EnterBranch(BID);
        String shiftType = leavingTime.getHour() < 14 ? "Morning" : "Night";
        boolean s_exists = mc.ShiftExist(date, shiftType);
        boolean store_keeper_available  = mc.StoreKeeperAvailable(date,shiftType);
        return s_exists && store_keeper_available;
    }

    public List<Integer> chooseDriver(LocalDate date, LocalTime leavingTime) {
        String shiftType = leavingTime.getHour() < 14 ? "Morning" : "Night";
        return mc.getAllAvailableDrivers(date,shiftType);
    }


    public void addDriverToShiftAndStoreKeeper(int BID,int driverID, LocalDate date, LocalTime leavingTime) {
        mc.EnterBranch(BID);
        String shiftType = leavingTime.getHour() < 14 ? "Morning" : "Night";
        mc.addDriverAndStoreKeeperToShift(driverID,date,shiftType);
    }
}
