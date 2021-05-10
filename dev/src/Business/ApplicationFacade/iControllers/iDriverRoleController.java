package Business.ApplicationFacade.iControllers;

import Business.Employees.EmployeePKG.Driver;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface iDriverRoleController {
    void addNewDriver(int newEID, String name, int[] bankDetails, int salary, LocalDate startWorkDate, int[] terms, int license);

    List<Driver> chooseDriver(LocalDate date, LocalTime leavingTime) throws Exception;

    boolean checkAvailableStoreKeeperAndShifts(int BID,LocalDate date, LocalTime leavingTime);

    void addDriverToShiftAndStoreKeeper(int BID,int driverID, LocalDate date, LocalTime leavingTime);
}
