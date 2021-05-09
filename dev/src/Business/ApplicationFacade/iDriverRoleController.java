package Business.ApplicationFacade;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface iDriverRoleController {
    void addNewDriver(int newEID, String name, int[] bankDetails, int salary, String role, LocalDate startWorkDate, int[] terms, int license);

    List<Integer> chooseDriver(LocalDate date, LocalTime leavingTime);

    boolean checkAvailableStoreKeeperAndShifts(int BID,LocalDate date, LocalTime leavingTime);

    void addDriverToShiftAndStoreKeeper(int BID,int driverID, LocalDate date, LocalTime leavingTime);
}
