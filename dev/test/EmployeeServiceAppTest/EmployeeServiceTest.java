package EmployeeServiceAppTest;

import Business.ApplicationFacade.*;
import Business.ApplicationFacade.outObjects.Constraint;
import Business.ApplicationFacade.outObjects.Employee;
import Database.Database;
import org.junit.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class EmployeeServiceTest {
    iEmployeeService service;
    int[] bank = {1,2,3};
    int[] terms = {4,5,6};
    Database database = Database.getInstance();

    /**
     * Assume that database has in init:
     * Branch : 1
     * Employees in branch 1 : PersonnelManager with EID = 1
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        database.init();
        service = new EmployeeService();
        service.loadData(1);
        service.Login(1,"PersonnelManager");
        service.addEmployee(2,"moshe",bank,10000,"Driver", LocalDate.now(),terms);
        Map<String,Integer> rolesAmount = new HashMap<>();
        rolesAmount.put("Driver",1);
        service.createShift(rolesAmount,LocalDate.of(2021,8,2),"Morning");
        service.Logout();
    }

    @After
    public void tearDown() throws Exception {
        database.reset();
    }

    @Test
    public void login1() {
        Response response = service.Login(21, "Manager");
        Assert.assertTrue("Exception expected - there is no type of 'Manager'", response.isError());
    }

    @Test
    public void login2() {
        Response response = service.Login(30000, "Driver");
        Assert.assertTrue("Exception expected - there is no id of 30000 in system", response.isError());
    }
    @Test
    public void login3() {
        Response response = service.Login(2, "PersonnelManager");
        Assert.assertTrue("Exception expected - there is no id of 2 in branch 1", response.isError());
        Response s1 = service.createBranch("00000",2,"Niv",bank,30000,terms);
        Assert.assertFalse(s1.isError());
        Response l = service.loadData(2);
        Assert.assertFalse(l.isError());
        Response response1 = service.Login(2, "PersonnelManager");
        Assert.assertFalse("Not Exception expected", response.isError());
    }

    @Test
    public void addConstConstraint() {
        Response s5 = service.Login(2,"Driver");
        Assert.assertFalse(s5.isError());
        ResponseData<Constraint> res = service.addConstConstraint(DayOfWeek.MONDAY,"Morning","i'm tired");
        Assert.assertFalse(res.isError());
        ResponseData<Employee> cons = service.getOnlyEmployeeShiftsAndConstraints();
        boolean contains = cons.getData().containsConst(res.getData().CID);
        Assert.assertTrue("The constraint wasn't added properly",contains);
    }

    @Test
    public void addConstraint1() {
        Response s6 = service.Login(2,"Driver");
        Assert.assertFalse(s6.isError());
        ResponseData<Constraint> res = service.addConstraint(LocalDate.of(2021,8,2),"Sunday","i'm tired");
        Assert.assertTrue("Exception expected - shift type does not match the date",res.isError());
    }
    @Test
    public void addConstraint2() {
        Response s6 = service.Login(2,"Driver");
        Assert.assertFalse(s6.isError());
        ResponseData<Constraint> res = service.addConstraint(LocalDate.of(2222,8,2),"Morning","i'm tired");
        Assert.assertTrue("Exception expected - there is no such shift",res.isError());
    }
    @Test
    public void addConstraint3() {
        Response s6 = service.Login(2,"Driver");
        Assert.assertFalse(s6.isError());
        ResponseData<Constraint> res = service.addConstraint(LocalDate.of(2021,8,2),"Morning","i'm tired");
        Assert.assertFalse(res.isError());
        ResponseData<Employee> cons = service.getOnlyEmployeeShiftsAndConstraints();
        boolean contains = cons.getData().containsConst(res.getData().CID);
        Assert.assertTrue("The constraint wasn't added properly",contains);
    }

    @Test
    public void removeConstraint1() {
        Response s6 = service.Login(2,"Driver");
        Assert.assertFalse(s6.isError());
        ResponseData<Constraint> res = service.addConstraint(LocalDate.of(2021,8,2),"Morning","i'm tired");
        Assert.assertFalse(res.isError());
        ResponseData<Constraint> rem = service.removeConstraint(res.getData().CID);
        Assert.assertFalse(rem.isError());
        ResponseData<Employee> consList = service.getOnlyEmployeeShiftsAndConstraints();
        boolean contains = consList.getData().containsConst(rem.getData().CID);
        Assert.assertFalse("The constraint wasn't removed properly",contains);
    }
    @Test
    public void removeConstraint2() {
        Response s6 = service.Login(2,"Driver");
        Assert.assertFalse(s6.isError());
        ResponseData<Constraint> res = service.addConstConstraint(DayOfWeek.MONDAY,"Morning","i'm tired");
        Assert.assertFalse(res.isError());
        ResponseData<Constraint> removedC = service.removeConstraint(res.getData().CID);
        Assert.assertFalse(removedC.isError());
        ResponseData<Employee> consList = service.getOnlyEmployeeShiftsAndConstraints();
        boolean contains = consList.getData().containsConst(removedC.getData().CID);
        Assert.assertFalse("The constraint wasn't removed properly",contains);
    }

    @Test
    public void updateReasonConstraint() {
        Response s6 = service.Login(2,"Driver");
        Assert.assertFalse(s6.isError());
        ResponseData<Constraint> s7 = service.addConstConstraint(DayOfWeek.MONDAY,"Morning","i'm tired");
        Assert.assertFalse(s7.isError());
        Response s8 = service.updateReasonConstraint(s7.getData().CID,"xxx");
        Assert.assertFalse(s8.isError());
        ResponseData<Employee> constList = service.getOnlyEmployeeShiftsAndConstraints();
        Assert.assertFalse(constList.isError());
        Constraint c = constList.getData().constraints.get(0);
        Assert.assertNotEquals("Update Reason in constraint didnt work properly", "i'm tired", c.reason);
    }

    @Test
    public void updateShiftTypeConstraint() {
        Response s6 = service.Login(2,"Driver");
        Assert.assertFalse(s6.isError());
        ResponseData<Constraint> s7 = service.addConstConstraint(DayOfWeek.MONDAY,"Morning","i'm tired");
        Assert.assertFalse(s7.isError());
        Response s8 = service.updateShiftTypeConstraint(s7.getData().CID,"Night");
        Assert.assertFalse(s8.isError());
        ResponseData<Employee> constList = service.getOnlyEmployeeShiftsAndConstraints();
        Assert.assertFalse(constList.isError());
        Constraint c = constList.getData().constraints.get(0);
        Assert.assertNotEquals("Update shift type in constraint didnt work properly", "Morning", c.shiftType);
    }

    @Test
    public void addEmployee1() {
        Response s2 = service.Login(1,"PersonnelManager");
        Assert.assertFalse(s2.isError());
        Response s4 = service.addEmployee(2,"moshe",bank,10000,"Driver", LocalDate.now(),terms);
        Assert.assertFalse("Not expected exception adding an employee by personnel manager", s4.isError());
    }
    @Test
    public void addEmployee2() {
        Response s2 = service.Login(1,"PersonnelManager");
        Assert.assertFalse(s2.isError());
        Response s4 = service.addEmployee(2,"moshe",bank,10000,"Driver", LocalDate.now(),terms);
        Assert.assertTrue("Exception expected - employee already exists",s4.isError());
        Response s5 = service.addEmployee(3,"moshe2",bank,10000,"Driver", LocalDate.now(),terms);
        Assert.assertFalse(s5.isError());
        Response s6 = service.Logout();
        Assert.assertFalse(s6.isError());
        Response s7 = service.Login(1,"Driver");
        Assert.assertFalse(s7.isError());
        Response s8 = service.addEmployee(4,"moshe",bank,10000,"Driver", LocalDate.now(),terms);
        Assert.assertTrue("expected exception adding an employee by driver", s8.isError());
    }

    @Test
    public void fireEmployee1() {
        Response s6 = service.Login(2,"Driver");
        Assert.assertFalse(s6.isError());
        Response s7 = service.addEmployee(4,"moshe",bank,10000,"Driver", LocalDate.now(),terms);
        Assert.assertTrue("expected exception firing an employee by driver", s7.isError());
    }
    @Test
    public void fireEmployee2() {
        Response s2 = service.Login(1,"PersonnelManager");
        Assert.assertFalse(s2.isError());
        Response s5 = service.fireEmployee(2);
        Assert.assertFalse("Not expected exception adding an employee by personnel manager", s5.isError());
    }

    @Test
    public void updateEmployeeName1() {
        Response s2 = service.Login(1,"PersonnelManager");
        Assert.assertFalse(s2.isError());
        Response s5 = service.updateEmployeeName(2, "xxx");
        Response s6 = service.Logout();
        Assert.assertFalse(s6.isError());
        Response s7 = service.Login(2,"Driver");
        Assert.assertFalse(s7.isError());
        ResponseData<Employee> s8 = service.getEmployeeDetails();
        Assert.assertNotEquals("updating employee name didnt update","moshe", s8.getData().name);
    }
    @Test
    public void updateEmployeeName2() {
        Response s2 = service.Login(2,"Driver");
        Assert.assertFalse(s2.isError());
        Response s5 = service.updateEmployeeName(2, "xxx");
        Assert.assertTrue("Expected exception - driver cannot update details",s5.isError());
    }

    @Test
    public void updateEmployeeSalary1() {
        Response s2 = service.Login(1,"PersonnelManager");
        Assert.assertFalse(s2.isError());
        Response s5 = service.updateEmployeeSalary(2, 10);
        Response s6 = service.Logout();
        Assert.assertFalse(s6.isError());
        Response s7 = service.Login(2,"Driver");
        Assert.assertFalse(s7.isError());
        ResponseData<Employee> s8 = service.getEmployeeDetails();
        Assert.assertNotEquals("updating employee salary didnt update",10000, s8.getData().salary);
    }

    @Test
    public void updateEmployeeSalary2() {
        Response s2 = service.Login(2,"Driver");
        Assert.assertFalse(s2.isError());
        Response s5 = service.updateEmployeeSalary(2, 9999999);
        Assert.assertTrue("Expected exception - driver cannot update details",s5.isError());
    }

    @Test
    public void updateEmployeeBANum1() {
        Response s2 = service.Login(1,"PersonnelManager");
        Assert.assertFalse(s2.isError());
        Response s5 = service.updateEmployeeBANum(2, 10);
        Assert.assertFalse(s5.isError());
        Response s6 = service.Logout();
        Assert.assertFalse(s6.isError());
        Response s7 = service.Login(2,"Driver");
        Assert.assertFalse(s7.isError());
        ResponseData<Employee> s8 = service.getEmployeeDetails();
        Assert.assertNotEquals("updating employee BA number didnt update",1, s8.getData().bankAccount[0]);
    }
    @Test
    public void updateEmployeeBANum2() {
        Response s2 = service.Login(2,"Driver");
        Assert.assertFalse(s2.isError());
        Response s5 = service.updateEmployeeBANum(2, 9999999);
        Assert.assertTrue("Expected exception - driver cannot update details",s5.isError());
    }
    @Test
    public void updateEmployeeBABranch1() {
        Response s2 = service.Login(1,"PersonnelManager");
        Assert.assertFalse(s2.isError());
        Response s5 = service.updateEmployeeBABranch(2, 10);
        Response s6 = service.Logout();
        Assert.assertFalse(s6.isError());
        Response s7 = service.Login(2,"Driver");
        Assert.assertFalse(s7.isError());
        ResponseData<Employee> s8 = service.getEmployeeDetails();
        Assert.assertNotEquals("updating employee BA branch didnt update",2, s8.getData().bankAccount[1]);
    }

    @Test
    public void updateEmployeeBABranch2() {
        Response s2 = service.Login(2,"Driver");
        Assert.assertFalse(s2.isError());
        Response s5 = service.updateEmployeeBABranch(2, 9999999);
        Assert.assertTrue("Expected exception - driver cannot update details",s5.isError());
    }

    @Test
    public void updateEmployeeBAID1() {
        Response s2 = service.Login(1,"PersonnelManager");
        Assert.assertFalse(s2.isError());
        Response s5 = service.updateEmployeeBAID(2, 10);
        Response s6 = service.Logout();
        Assert.assertFalse(s6.isError());
        Response s7 = service.Login(2,"Driver");
        Assert.assertFalse(s7.isError());
        ResponseData<Employee> s8 = service.getEmployeeDetails();
        Assert.assertNotEquals("updating employee BA ID didnt update",3, s8.getData().bankAccount[2]);
    }
    @Test
    public void updateEmployeeBAID2() {
        Response s2 = service.Login(2,"Driver");
        Assert.assertFalse(s2.isError());
        Response s5 = service.updateEmployeeBAID(2, 9999999);
        Assert.assertTrue("Expected exception - driver cannot update details",s5.isError());
    }
    @Test
    public void updateEmployeeEducationFund1() {
        Response s2 = service.Login(1,"PersonnelManager");
        Assert.assertFalse(s2.isError());
        Response s5 = service.updateEmployeeEducationFund(2, 10);
        Response s6 = service.Logout();
        Assert.assertFalse(s6.isError());
        Response s7 = service.Login(2,"Driver");
        Assert.assertFalse(s7.isError());
        ResponseData<Employee> s8 = service.getEmployeeDetails();
        Assert.assertNotEquals("updating employee education fund didnt update",4, s8.getData().terms[0]);
    }
    @Test
    public void updateEmployeeEducationFund2() {
        Response s2 = service.Login(2,"Driver");
        Assert.assertFalse(s2.isError());
        Response s5 = service.updateEmployeeEducationFund(2, 9999999);
        Assert.assertTrue("Expected exception - driver cannot update details",s5.isError());
    }

    @Test
    public void updateEmployeeDaysOff1() {
        Response s2 = service.Login(1,"PersonnelManager");
        Assert.assertFalse(s2.isError());
        Response s5 = service.updateEmployeeDaysOff(2, 10);
        Response s6 = service.Logout();
        Assert.assertFalse(s6.isError());
        Response s7 = service.Login(2,"Driver");
        Assert.assertFalse(s7.isError());
        ResponseData<Employee> s8 = service.getEmployeeDetails();
        Assert.assertNotEquals("updating employee days off didnt update",5, s8.getData().terms[1]);
    }
    @Test
    public void updateEmployeeDaysOff2() {
        Response s2 = service.Login(2,"Driver");
        Assert.assertFalse(s2.isError());
        Response s5 = service.updateEmployeeDaysOff(2, 9999999);
        Assert.assertTrue("Expected exception - driver cannot update details",s5.isError());
    }

    @Test
    public void updateEmployeeSickDays1() {
        Response s2 = service.Login(1,"PersonnelManager");
        Assert.assertFalse(s2.isError());
        Response s5 = service.updateEmployeeSickDays(2, 10);
        Response s6 = service.Logout();
        Assert.assertFalse(s6.isError());
        Response s7 = service.Login(2,"Driver");
        Assert.assertFalse(s7.isError());
        ResponseData<Employee> s8 = service.getEmployeeDetails();
        Assert.assertNotEquals("updating employee sick days didnt update",6, s8.getData().terms[2]);
    }
    @Test
    public void updateEmployeeSickDays2() {
        Response s2 = service.Login(2,"Driver");
        Assert.assertFalse(s2.isError());
        Response s5 = service.updateEmployeeSickDays(2, 9999999);
        Assert.assertTrue("Expected exception - driver cannot update details",s5.isError());
    }

    @Test
    public void createShift() {
    }

    @Test
    public void getEmployeeDetails() {
    }

    @Test
    public void getShiftsAndEmployees() {
    }

    @Test
    public void removeEmpFromShift() {
    }

    @Test
    public void addEmpToShift() {
    }

    @Test
    public void updateAmountRole() {
    }

    @Test
    public void getOnlyEmployeeShiftsAndConstraints() {
    }

    @Test
    public void updateEmpRole() {
    }
}