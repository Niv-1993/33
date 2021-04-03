package Business.ShiftPKG;

import Business.ApplicationFacade.outObjects.Employee;
import Business.Type.RoleType;
import Business.Type.ShiftType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class ShiftControllerTest {
    ShiftController sc;

    @Before
    public void setUp() throws Exception {
        sc = new ShiftController(new HashMap<>(),new HashMap<>());
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void addConstConstraint() {
        Constraint constraint = sc.addConstConstraint(2,DayOfWeek.MONDAY, ShiftType.Morning, "sick");
        Map<Integer,Constraint> constraints = sc.getConstraints();
        assertTrue(constraints.containsValue(constraint));
    }

    @Test
    public void addConstraint1() throws Exception {
        Constraint constraint = sc.addConstraint(2, LocalDate.now(), ShiftType.Morning, "sick");
        Map<Integer,Constraint> constraints = sc.getConstraints();
        assertTrue(constraints.containsValue(constraint));
    }

    @Test
    public void addConstraint2() throws Exception {
        try {
            Constraint constraint = sc.addConstraint(2, LocalDate.of(1990,10,19), ShiftType.Morning, "sick");
            fail();
        }catch (Exception e){}
    }

    @Test
    public void removeConstraint() throws Exception {
        Constraint constraint = sc.addConstraint(2, LocalDate.now(), ShiftType.Morning, "sick");
        sc.removeConstraint(1);
        Map<Integer,Constraint> constraints = sc.getConstraints();
        assertFalse(constraints.containsKey(1));
    }



    @Test
    public void updateReasonConstraint() throws Exception {
        Constraint constraint = sc.addConstraint(2, LocalDate.of(2022,1,12), ShiftType.Morning, "sick");
        sc.updateReasonConstraint(1,"tiered");
        Map<Integer,Constraint> constraints = sc.getConstraints();
        Constraint c = constraints.get(1);
        assertEquals("tired", c.getReason());
    }

    @Test
    public void updateShiftTypeConstraint() throws Exception {
        Constraint constraint = sc.addConstraint(2, LocalDate.now(), ShiftType.Morning, "sick");
        sc.updateShiftTypeConstraint(1,ShiftType.Night);
        Map<Integer,Constraint> constraints = sc.getConstraints();
        Constraint c = constraints.get(1);
        assertEquals(ShiftType.Night, c.getShiftType());
    }


    @Test
    public void defaultShifts1() throws Exception {
        Map<ShiftType, Map<RoleType, Integer>> defaultShifts = new HashMap<>();
        HashMap<RoleType,Integer> rolesAmount = new HashMap<>();
        rolesAmount.put(RoleType.Cashier,2);
        rolesAmount.put(RoleType.Driver,0);
        defaultShifts.put(ShiftType.Morning,rolesAmount);
        sc.defaultShifts(defaultShifts);
        boolean cashier = sc.getDefaultShifts().get(ShiftType.Morning).get(RoleType.Cashier)==2;
        boolean driver = sc.getDefaultShifts().get(ShiftType.Morning).get(RoleType.Driver)==0;
        assertTrue(cashier&driver);
    }

    @Test
    public void defaultShifts2() throws Exception {
        Map<ShiftType, Map<RoleType, Integer>> defaultShifts = new HashMap<>();
        HashMap<RoleType,Integer> rolesAmount = new HashMap<>();
        rolesAmount.put(RoleType.Cashier,-2);
        defaultShifts.put(ShiftType.Morning,rolesAmount);
        sc.defaultShifts(defaultShifts);
        fail(); // role amount is negative
    }

    @Test
    public void getOnlyEmployeeConstraints() throws Exception {
        Constraint constraint1 = sc.addConstConstraint(2,DayOfWeek.MONDAY, ShiftType.Morning, "sick");
        Constraint constraint2 = sc.addConstraint(2,LocalDate.now(), ShiftType.Night, "feel bad");
        List<Constraint> list = sc.getOnlyEmployeeConstraints(2);
        boolean ok = list.size()==2;
        for(Constraint c : list){
            if (c.getEID() != 2 || !(c.getReason().equals("sick") | c.getReason().equals("feel bad"))) {
                ok = false;
                break;
            }
        }
        assertTrue(ok);
    }

}