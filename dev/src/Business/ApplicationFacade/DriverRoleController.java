package Business.ApplicationFacade;

import Business.ApplicationFacade.iControllers.iDriverRoleController;
import Business.ApplicationFacade.iControllers.iManagerRoleController;
import Business.Transportation.Driver;
import DataAccess.DriverMapper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

public class DriverRoleController implements iDriverRoleController {
    private iManagerRoleController mc;
    private final DriverMapper dm;

    public DriverRoleController(iManagerRoleController m) {
        mc = m;
        dm=DriverMapper.getMapper();
    }
    public DriverRoleController(){
        dm=DriverMapper.getMapper();
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

    public List<Driver> chooseDriver(LocalDate date, LocalTime leavingTime) throws Exception {
        String shiftType = leavingTime.getHour() < 14 ? "Morning" : "Night";
        List<Integer> driversID =  mc.getAllAvailableDrivers(date,shiftType);
        List<Driver> drivers = new LinkedList<>();
        for (int id:driversID ) {
            drivers.add(getDriver(id));
        }
        return (drivers.isEmpty())?(null):drivers;
    }
    public Driver getDriver(int id) throws Exception {
       return dm.select(id);
    }
    public void addDriverToShiftAndStoreKeeper(int BID,int driverID, LocalDate date, LocalTime leavingTime) {
        mc.EnterBranch(BID);
        String shiftType = leavingTime.getHour() < 14 ? "Morning" : "Night";
        mc.addDriverAndStoreKeeperToShift(driverID,date,shiftType);
    }
}
