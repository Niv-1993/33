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
        sc = new ShiftController(new HashMap<>(), new HashMap<>());
    }

    @After
    public void tearDown()  {
    }

    @Test
    public void addConstConstraint() {
        Constraint constraint = sc.addConstConstraint(2, DayOfWeek.MONDAY, ShiftType.Morning, "sick");
        Map<Integer, Constraint> constraints = sc.getConstraints();
        assertTrue(constraints.containsValue(constraint));
    }

    @Test
    public void addConstraint1() {
        try {
            Constraint constraint = sc.addConstraint(2, LocalDate.now(), ShiftType.Morning, "sick");
            Map<Integer, Constraint> constraints = sc.getConstraints();
            boolean cid = constraint.getCID()==0;
            assertTrue(cid & constraints.containsValue(constraint));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void addConstraint2() {
        try {
            Constraint constraint = sc.addConstraint(2, LocalDate.of(1990, 10, 19), ShiftType.Morning, "sick");
            fail();
        } catch (Exception e) {
        }
    }

    @Test
    public void addConstraint3() {
        try {
            sc.createShift(new HashMap<>(),LocalDate.of(2021,12,19),ShiftType.Morning,new HashMap<>());
            Constraint constraint = sc.addConstraint(2, LocalDate.of(2021, 12, 19), ShiftType.Morning, "sick");
            fail();  //cant add constraint for exists shift
        } catch (Exception e) {
        }
    }

    @Test
    public void removeConstraint() {
        try {
            Constraint constraint = sc.addConstraint(2, LocalDate.now(), ShiftType.Morning, "sick");
            sc.removeConstraint(0);
            Map<Integer, Constraint> constraints = sc.getConstraints();
            assertFalse(constraints.containsKey(0));
        } catch (Exception e) {
            fail();
        }
    }


    @Test
    public void updateReasonConstraint() {
        try {
            Constraint constraint = sc.addConstraint(2, LocalDate.of(2022, 1, 12), ShiftType.Morning, "sick");
            String str = "tired";
            sc.updateReasonConstraint(0, str);
            Map<Integer, Constraint> constraints = sc.getConstraints();
            Constraint c = constraints.get(0);
            assertEquals(str, c.getReason());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void updateShiftTypeConstraint() {
        try {
            Constraint constraint = sc.addConstraint(2, LocalDate.now(), ShiftType.Morning, "sick");
            sc.updateShiftTypeConstraint(0, ShiftType.Night);
            Map<Integer, Constraint> constraints = sc.getConstraints();
            Constraint c = constraints.get(0);
            assertEquals(ShiftType.Night, c.getShiftType());
        } catch (Exception e) {
            fail();
        }
    }


    @Test
    public void defaultShifts1() {
        try {
            Map<ShiftType, Map<RoleType, Integer>> defaultShifts = new HashMap<>();
            HashMap<RoleType, Integer> rolesAmount = new HashMap<>();
            rolesAmount.put(RoleType.Cashier, 2);
            rolesAmount.put(RoleType.Driver, 0);
            defaultShifts.put(ShiftType.Morning, rolesAmount);
            sc.defaultShifts(defaultShifts);
            boolean cashier = sc.getDefaultShifts().get(ShiftType.Morning).get(RoleType.Cashier) == 2;
            boolean driver = sc.getDefaultShifts().get(ShiftType.Morning).get(RoleType.Driver) == 0;
            assertTrue(cashier & driver);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void defaultShifts2() {
        try {
            Map<ShiftType, Map<RoleType, Integer>> defaultShifts = new HashMap<>();
            HashMap<RoleType, Integer> rolesAmount = new HashMap<>();
            rolesAmount.put(RoleType.Cashier, -2);
            defaultShifts.put(ShiftType.Morning, rolesAmount);
            sc.defaultShifts(defaultShifts);
            fail(); // role amount is negative
        } catch (Exception ignored) {
        }
    }

    @Test
    public void getOnlyEmployeeConstraints() {
        try {
            Constraint constraint1 = sc.addConstConstraint(2, DayOfWeek.MONDAY, ShiftType.Morning, "sick");
            Constraint constraint2 = sc.addConstraint(2, LocalDate.now(), ShiftType.Night, "feel bad");
            List<Constraint> list = sc.getOnlyEmployeeConstraints(2);
            boolean ok = list.size() == 2;
            for (Constraint c : list) {
                if (c.getEID() != 2 || !(c.getReason().equals("sick") | c.getReason().equals("feel bad"))) {
                    ok = false;
                    break;
                }
            }
            assertTrue(ok);
        } catch (Exception e) {
            fail();
        }
    }

}