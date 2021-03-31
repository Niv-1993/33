package Business.EmployeePKG;

import Business.ShiftPKG.*;
import Business.Type.RoleType;
import Business.Type.ShiftType;
import org.junit.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EmployeeTest {
    int bank[] = {1, 2, 3};
    int terms[] = {4, 5, 6};
    Employee personnelManager = new PersonnelManager(1, "Niv", bank, 1000, RoleType.PerssonelManger, LocalDate.now(), terms);
    Employee driver;
    Business.ShiftPKG.Shift s; //mock the shift with SID = 1
    ShiftController shiftController = new ShiftController(); //mock

    @Before
    public void setUp() throws Exception {
        driver = new Regular(2, "dor", bank, 3000, RoleType.Driver, LocalDate.now(), terms);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void addConstConstraint() {
        Business.ShiftPKG.Constraint constraint = driver.addConstConstraint(DayOfWeek.MONDAY, ShiftType.Morning, "cant", shiftController);
        List<Business.ShiftPKG.Constraint> list = driver.getOnlyEmployeeConstraints(shiftController);
        Assert.assertTrue(list.contains(constraint));
    }

    @Test
    public void addConstraint() {
        Business.ShiftPKG.Constraint constraint = driver.addConstraint(LocalDate.now(), ShiftType.Morning, "cant", shiftController);
        List<Business.ShiftPKG.Constraint> list = driver.getOnlyEmployeeConstraints(shiftController);
        Assert.assertTrue(list.contains(constraint));
    }

    @Test
    public void removeConstraint() throws Exception {
        Business.ShiftPKG.Constraint constraint = driver.addConstraint(LocalDate.now(), ShiftType.Morning, "cant", shiftController);
        driver.removeConstraint(constraint.getCID(), shiftController);
        List<Business.ShiftPKG.Constraint> list = driver.getOnlyEmployeeConstraints(shiftController);
        Assert.assertFalse(list.contains(constraint));
    }

    @Test
    public void updateReasonConstraint() throws Exception {
        Business.ShiftPKG.Constraint constraint = driver.addConstraint(LocalDate.now(), ShiftType.Morning, "cant", shiftController);
        driver.updateReasonConstraint(constraint.getCID(), "newReason", shiftController);
        Assert.assertNotEquals("didnt update reason", "cant", constraint.getReason());
    }

    @Test
    public void updateShiftTypeConstraint() throws Exception {
        Business.ShiftPKG.Constraint constraint = driver.addConstraint(LocalDate.now(), ShiftType.Morning, "cant", shiftController);
        driver.updateShiftTypeConstraint(constraint.getCID(), ShiftType.Night, shiftController);
        Assert.assertNotEquals("didnt update reason", ShiftType.Morning, constraint.getShiftType());
    }

    @Test
    public void addEmployee1() {
        try {
            Map<Integer, Employee> employees = new HashMap<>();
            personnelManager.addEmployee(5, "bla", bank, 222222, RoleType.Cashier, LocalDate.now(), terms, employees);
            Assert.assertTrue(employees.containsKey(5));
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void addEmployee2() {
        try {
            Map<Integer, Employee> employees = new HashMap<>();
            driver.addEmployee(5, "bla", bank, 222222, RoleType.Cashier, LocalDate.now(), terms, employees);
            Assert.fail();
        } catch (Exception e) {
        }
    }

    @Test
    public void fireEmployee1() {
        try {
            Map<Integer, Employee> employees = new HashMap<>();
            personnelManager.addEmployee(5, "bla", bank, 222222, RoleType.Cashier, LocalDate.now(), terms, employees);
            personnelManager.fireEmployee(5, employees);
            Assert.assertFalse(employees.containsKey(5));
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void fireEmployee2() {
        try {
            Map<Integer, Employee> employees = new HashMap<>();
            driver.fireEmployee(5, employees);
            Assert.fail();
        } catch (Exception e) {
        }
    }

    @Test
    public void updateEmployeeName1() {
        try {
            personnelManager.updateEmployeeName(driver, "bla");
            Assert.assertNotEquals("didnt update name", "dor", driver.getName());
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void updateEmployeeName2() {
        try {
            driver.updateEmployeeName(driver, "bla");
            Assert.fail();
        } catch (Exception e) {
        }
    }

    @Test
    public void updateEmployeeSalary1() {
        try {
            personnelManager.updateEmployeeSalary(driver, 60);
            Assert.assertNotEquals("didnt update name", 3000, driver.getSalary());
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void updateEmployeeSalary2() {
        try {
            driver.updateEmployeeSalary(driver, 60);
            Assert.fail();
        } catch (Exception e) {
        }
    }

    @Test
    public void updateEmployeeBANum1() {
        try {
            personnelManager.updateEmployeeBANum(driver, 8);
            Assert.assertNotEquals("didnt update bankA num", 1, driver.getBankAccount().getAccountNum());
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void updateEmployeeBANum2() {
        try {
            driver.updateEmployeeBANum(driver, 60);
            Assert.fail();
        } catch (Exception e) {
        }
    }

    @Test
    public void updateEmployeeBABranch1() {
        try {
            personnelManager.updateEmployeeBABranch(driver, 8);
            Assert.assertNotEquals("didnt update bank branch", 2, driver.getBankAccount().getBankBranch());
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void updateEmployeeBABranch2() {
        try {
            driver.updateEmployeeBABranch(driver, 60);
            Assert.fail();
        } catch (Exception e) {
        }
    }

    @Test
    public void updateEmployeeBAID1() {
        try {
            personnelManager.updateEmployeeBAID(driver, 8);
            Assert.assertNotEquals("didnt update bank id", 3, driver.getBankAccount().getBankID());
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void updateEmployeeBAID2() {
        try {
            driver.updateEmployeeBAID(driver, 60);
            Assert.fail();
        } catch (Exception e) {
        }
    }

    @Test
    public void updateEmployeeEducationFund1() {
        try {
            personnelManager.updateEmployeeEducationFund(driver, 8);
            Assert.assertNotEquals("didnt update education fund", 4, driver.getTermsOfEmployment().getEducationFun());
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void updateEmployeeEducationFund2() {
        try {
            driver.updateEmployeeEducationFund(driver, 60);
            Assert.fail();
        } catch (Exception e) {
        }
    }

    @Test
    public void updateEmployeeDaysOff1() {
        try {
            personnelManager.updateEmployeeDaysOff(driver, 8);
            Assert.assertNotEquals("didnt update days off", 5, driver.getTermsOfEmployment().getDaysOff());
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void updateEmployeeDaysOff2() {
        try {
            driver.updateEmployeeDaysOff(driver, 60);
            Assert.fail();
        } catch (Exception e) {
        }
    }

    @Test
    public void updateEmployeeSickDays1() {
        try {
            personnelManager.updateEmployeeSickDays(driver, 8);
            Assert.assertNotEquals("didnt update sick days", 6, driver.getTermsOfEmployment().getSickDays());
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void updateEmployeeSickDays2() {
        try {
            driver.updateEmployeeSickDays(driver, 60);
            Assert.fail();
        } catch (Exception e) {
        }
    }

    @Test
    public void createShift1() {
        try {
            Map<RoleType, Integer> rolesAmount = new HashMap<>();
            Map<RoleType, List<String[]>> employees = new HashMap<>();
            String[] pair = {String.valueOf(driver.getEID()), driver.getName()};
            List<String[]> l = new ArrayList<>();
            l.add(pair);
            employees.put(RoleType.Driver, l);
            Business.ShiftPKG.Shift shift = personnelManager.createShift(rolesAmount, LocalDate.of(2021, 8, 20), ShiftType.Morning, employees, shiftController);
            List<Business.ShiftPKG.Shift> list = personnelManager.getShiftsAndEmployees(shiftController);
            Assert.assertTrue(list.contains(shift));
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void createShift2() {
        try {
            Map<RoleType, Integer> rolesAmount = new HashMap<>();
            Map<RoleType, List<String[]>> employees = new HashMap<>();
            driver.createShift(rolesAmount, LocalDate.of(2021, 8, 20), ShiftType.Morning, employees, shiftController);
            Assert.fail();
        } catch (Exception e) {
        }
    }

    @Test
    public void removeEmpFromShift1() {
        try {
            personnelManager.addEmpToShift(s.getSID(), driver.getEID(), driver.getRole().get(0), driver.getName(), shiftController);
            personnelManager.removeEmpFromShift(s.getSID(), driver.getEID(), shiftController);
            Assert.assertFalse(s.getEmployees().containsKey(driver.getEID()));
        } catch (Exception e) {
            Assert.fail();
        }
    }
    @Test
    public void removeEmpFromShift2() {
        try {
            driver.removeEmpFromShift(s.getSID(), driver.getEID(), shiftController);
            Assert.fail();
        } catch (Exception e) {}
    }

    @Test
    public void addEmpToShift1() {
        try {
            personnelManager.addEmpToShift(s.getSID(), driver.getEID(), driver.getRole().get(0), driver.getName(), shiftController);
            Assert.assertTrue(s.getEmployees().containsKey(driver.getEID()));
        }
        catch (Exception e){Assert.fail();}
    }
    @Test
    public void addEmpToShift2() {
        try {
            driver.addEmpToShift(s.getSID(), driver.getEID(), driver.getRole().get(0), driver.getName(), shiftController);
            Assert.fail();
        }catch (Exception e){}
    }


    @Test
    public void updateAmountRole1(){
        try{
            personnelManager.updateAmountRole(1,RoleType.Driver,5,shiftController);
            Assert.assertEquals(5, (int) s.getRolesAmount().get(RoleType.Driver));
        }catch (Exception e){Assert.fail();}
    }

    @Test
    public void updateAmountRole2(){
        try{
            driver.updateAmountRole(1,RoleType.Driver,5,shiftController);
            Assert.fail();
        }catch (Exception e){}
    }
}