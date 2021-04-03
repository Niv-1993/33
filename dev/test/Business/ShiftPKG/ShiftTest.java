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
        HashMap<RoleType,Integer> rolesAmount = new HashMap<>();
        rolesAmount.put(RoleType.Cashier,2);
        rolesAmount.put(RoleType.Sorter,1);
        HashMap<RoleType, List<String[]>> optionals = new HashMap<>();
        List<String[]> l = new LinkedList<>();
        l.add(new String[] {String.valueOf(312),"Dor"});
        optionals.put(RoleType.Cashier,l);
        s = new Shift(rolesAmount,optionals,LocalDate.of(2023,10,10),ShiftType.Morning);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void self_make1() throws Exception {
        List<RoleType> list = new LinkedList<>();
        list.add(RoleType.Cashier);
        List<RoleType> list2 = new LinkedList<>();
        list2.add(RoleType.Sorter);
        s.addToOptionals(333,"niv",list);
        s.addToOptionals(123,"ron", list2);
        s.self_make();
        boolean sorter = s.getEmployees().get(123)[0].equals(RoleType.Sorter.name());
        boolean cashier1 = s.getEmployees().get(333)[0].equals(RoleType.Cashier.name());
        boolean cashier2 = s.getEmployees().get(312)[0].equals(RoleType.Cashier.name());
        assertTrue(sorter&cashier1&cashier2);
    }

    @Test
    public void self_make2Complete() throws Exception {
        List<RoleType> list = new LinkedList<>();
        list.add(RoleType.Cashier);
        List<RoleType> list2 = new LinkedList<>();
        list2.add(RoleType.Sorter);
        s.addToOptionals(333,"niv",list);
        s.addToOptionals(123,"ron", list2);
        s.self_make();
        assertTrue(s.getComplete());
    }
    @Test
    public void self_make3() throws Exception {
        List<RoleType> list = new LinkedList<>();
        list.add(RoleType.Cashier);
        s.addToOptionals(333,"niv",list);
        s.self_make();
        assertFalse(s.getComplete());
    }


    @Test
    public void addEmpToShift1() throws Exception {
        s.addEmpToShift(312,RoleType.Cashier,"Dor");
        assertTrue(s.getEmployees().containsKey(312));
    }
    @Test
    public void addEmpToShift2() throws Exception {
        s.addEmpToShift(312,RoleType.Driver,"Dor");
        fail();  //not need driver
    }

    @Test
    public void removeEmpFromShift1() throws Exception {
        s.addEmpToShift(312,RoleType.Cashier,"Dor");
        s.removeEmpFromShift(312);
        assertTrue(s.getEmployees().isEmpty());
    }

    @Test
    public void removeEmpFromShift2() throws Exception {
        s.addEmpToShift(312,RoleType.Cashier,"Dor");
        s.removeEmpFromShift(31);
        fail();  //31 not in this shift
    }


    @Test
    public void updateRolesAmount1() throws Exception {
        s.updateRolesAmount(RoleType.Cashier,4);
        assertEquals(4, (int) s.getRolesAmount().get(RoleType.Cashier));
    }

    @Test
    public void updateRolesAmount2() throws Exception {
        s.updateRolesAmount(RoleType.Cashier,-2);
        fail(); //negative number
    }


    @Test
    public void addToOptionals() {
        List<RoleType> l = new LinkedList<>();
        l.add(RoleType.Sorter);
        s.addToOptionals(312,"Dor",l);
        String[] e = {String.valueOf(312),"Dor"};
        assertTrue(s.getOptionals().get(RoleType.Sorter).contains(e));
    }

    @Test
    public void removeFireEmp() throws Exception {
        s.addEmpToShift(312,RoleType.Cashier,"Dor");
        s.removeFireEmp(312,"Dor");
        String[] e = {String.valueOf(312),"Dor"};
        assertTrue(!(s.getOptionals().get(RoleType.Cashier).contains(e)) & s.getEmployees().get(312)==null);
    }


}