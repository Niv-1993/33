package Business.ShiftPKG;

import Business.Type.RoleType;
import Business.Type.ShiftType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class ShiftTest {
    private Shift s;

    @Before
    public void setUp() throws Exception {
        HashMap<RoleType, Integer> rolesAmount = new HashMap<>();
        rolesAmount.put(RoleType.Cashier, 2);
        rolesAmount.put(RoleType.Sorter, 1);
        HashMap<RoleType, List<String[]>> optionals = new HashMap<>();
        List<String[]> l = new LinkedList<>();
        l.add(new String[]{String.valueOf(312), "Dor"});
        optionals.put(RoleType.Cashier, l);
        optionals.put(RoleType.Sorter,null);
        s = new Shift(0,rolesAmount, optionals, LocalDate.of(2023, 10, 10), ShiftType.Morning);
    }

    @After
    public void tearDown()  {
    }

    @Test
    public void self_make1() {
        try {
            List<RoleType> list = new LinkedList<>();
            list.add(RoleType.Cashier);
            List<RoleType> list2 = new LinkedList<>();
            list2.add(RoleType.Sorter);
            s.addToOptionals(333, "niv", list);
            s.addToOptionals(123, "ron", list2);
            s.self_make();
            boolean sorter = s.getEmployees().get(123)[0].equals(RoleType.Sorter.name());
            boolean cashier1 = s.getEmployees().get(333)[0].equals(RoleType.Cashier.name());
            boolean cashier2 = s.getEmployees().get(312)[0].equals(RoleType.Cashier.name());
            assertTrue(sorter & cashier1 & cashier2);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void self_make2Complete() {
        try {
            List<RoleType> list = new LinkedList<>();
            list.add(RoleType.Cashier);
            List<RoleType> list2 = new LinkedList<>();
            list2.add(RoleType.Sorter);
            s.addToOptionals(333, "niv", list);
            s.addToOptionals(123, "ron", list2);
            s.self_make();
            assertTrue(s.getComplete());
        } catch (Exception e) {
            fail();
        }

    }

    @Test
    public void self_make3() {
        try {
            List<RoleType> list = new LinkedList<>();
            list.add(RoleType.Cashier);
            s.addToOptionals(333, "niv", list);
            s.self_make();
            assertFalse(s.getComplete());
        } catch (Exception e) {
            fail();
        }
    }


    @Test
    public void addEmpToShift1() {
        try {
            s.addEmpToShift(312, RoleType.Cashier, "Dor");
            assertTrue(s.getEmployees().containsKey(312));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void addEmpToShift2(){
        try {
            s.addEmpToShift(312, RoleType.Driver, "Dor");
            fail();  //not need driver
        } catch (Exception ignored) {
        }
    }

    @Test
    public void removeEmpFromShift1() {
        try {
            s.addEmpToShift(312, RoleType.Cashier, "Dor");
            s.removeEmpFromShift(312);
            assertTrue(s.getEmployees().isEmpty());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void removeEmpFromShift2() {
        try {
            s.addEmpToShift(312, RoleType.Cashier, "Dor");
            s.removeEmpFromShift(31);
            fail();  //31 not in this shift
        } catch (Exception ignored) {
        }
    }


    @Test
    public void updateRolesAmount1() {
        try {
            s.updateRolesAmount(RoleType.Cashier, 4);
            assertEquals(4, (int) s.getRolesAmount().get(RoleType.Cashier));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void updateRolesAmount2() {
        try {
            s.updateRolesAmount(RoleType.Cashier, -2);
            fail(); //negative number
        } catch (Exception ignored) {
        }
    }


    @Test
    public void addToOptionals() {
        try {
            List<RoleType> l = new LinkedList<>();
            l.add(RoleType.Sorter);
            String[] arr = s.addToOptionals(313, "niv", l);
            assertTrue(s.getOptionals().get(RoleType.Sorter).contains(arr));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void removeFireEmp()  {
        try {
            s.addEmpToShift(312, RoleType.Cashier, "Dor");
            s.removeFireEmp(312, "Dor");
            String[] e = {String.valueOf(312), "Dor"};
            assertTrue(!(s.getOptionals().get(RoleType.Cashier).contains(e)) & s.getEmployees().get(312) == null);
        } catch (Exception e) {
            fail();
        }
    }


}