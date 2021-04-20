import static org.junit.Assert.*;

import BussniesLayer.facade.SupplierService;
import BussniesLayer.facade.outObjects.Item;
import org.junit.Test;
import BussniesLayer.facade.response;

import java.time.LocalDate;

public class SupplierServiceTest {
    private final SupplierService service = new SupplierService();

    @Test
    public void testAddSupplier() {
        response r = service.addSupplier("Test1", 1, 11, 654321, "cash");
        assertFalse(r.isError());
        assertEquals(0, service.showSupplier(0).getOutObject().getSupplierBN());
        assertEquals("Test1", service.showSupplier(0).getOutObject().getSupplierName());
        assertEquals(1, service.showSupplier(0).getOutObject().getBankNumber());
        assertEquals(11, service.showSupplier(0).getOutObject().getBrunchNumber());
        assertEquals(654321, service.showSupplier(0).getOutObject().getAccountNumber());
        assertEquals("cash", service.showSupplier(0).getOutObject().getPayWay());
    }

    @Test
    public void testGetItemIDFailure() {
        response r1 = service.addItem(1, "dairy" , "milk", 3.5, 1, LocalDate.now());
        assertTrue(r1.isError());
        assertEquals("ERROR: supplier BN does not exist." , r1.getError());
        service.addSupplier("Test", 2 , 5 , 654321 , "cash");
        response r2 = service.addItem(0, "dairy", "yogurt",  -5, 2, LocalDate.now());
        assertTrue(r2.isError());
        assertEquals("ERROR: price must be a positive number!" , r2.getError());
    }

    @Test
    public void testGetItem(){
        service.LoadData();
        Item item = service.addItem(0, "dairy", "milk" ,  3.5,1,LocalDate.now() ).getOutObject();
        Item item2 = service.addItem(0, "dairy", "chocolate" ,  3.5,2, LocalDate.now()).getOutObject();
        Item item3 = service.addItem(0, "dairy", "cottage" , 3.5,3, LocalDate.now() ).getOutObject();
        assertEquals("19" , item.toStringId());
        assertEquals("20" , item2.toStringId());
        assertEquals("21" , item3.toStringId());
        response r = service.addItem(0, "dairy", "milka" , 7, 4, LocalDate.now());
        assertFalse(r.isError());
    }

    @Test
    public void testShowSupplier() {
        service.LoadData();
        BussniesLayer.facade.outObjects.SupplierCard s = service.showSupplier(0).getOutObject();
        assertEquals( 0, s.getSupplierBN());
        assertEquals( 1, s.getAccountNumber());
        assertEquals( "check", s.getPayWay());
        assertTrue(service.showSupplier(6).isError());
        service.addSupplier("anotherSupplier",2 , 1, 42, "bank transfer");
        assertFalse(service.showSupplier(6).isError());
    }

    @Test
    public void testUpdateSupplierPayWay() {
        service.LoadData();
        service.updateSupplierPayWay(0, "cash");
        assertEquals("cash", service.showSupplier(0).getOutObject().getPayWay());
        service.updateSupplierPayWay(0, "check");
        assertEquals("check", service.showSupplier(0).getOutObject().getPayWay());
        response r = service.updateSupplierPayWay(10, "check");
        assertTrue(r.isError());
        response r1 = service.updateSupplierPayWay(0, "just pay me");
        assertEquals("ERROR: pay way must be check/bank transfer/cash." , r1.getError());
    }

    @Test
    public void testRemoveSupplier() {
        service.LoadData();
        assertEquals(6, service.showAllSuppliers().getOutObject().size());
        service.removeSupplier(3);
        assertEquals(5, service.showAllSuppliers().getOutObject().size());
        assertTrue(service.showSupplier(3).isError());
        assertTrue(service.addRegularOrder(3,1).isError());
    }

    @Test
    public void testAddItemToNullSupplier() {
        service.LoadData();
        assertTrue(service.addItem(6,"Test" , "testItem", 10, 1, LocalDate.now()).isError());
        service.addSupplier("shouldPass", 2 , 6, 11111, "cash");
        assertFalse(service.addItem(6,"Test" , "testItem", 10, 1, LocalDate.now()).isError());
    }

    @Test
    public void testUpdateDiscountOfQD() {
        service.LoadData();
        service.updateDiscountOfQD(1, 3, 5);
        assertEquals(5, service.showQuantityDocument(1, 3).getOutObject().getDiscount());
        service.updateDiscountOfQD(1, 3, 10);
        assertEquals(10, service.showQuantityDocument(1, 3).getOutObject().getDiscount());
    }

    @Test
    public void testShowSupplierAgreement() {
        service.addSupplier("Test", 1 , 11 , 1, "cash");
        assertFalse(service.showSupplier(0).isError());
        assertTrue(service.showSupplierAgreement(0).isError());
        service.addSupplierAgreement(0, 5, 5 ,false, false);
        assertFalse(service.showSupplierAgreement(0).isError());
    }

    @Test
    public void testShowAllOrdersOfSupplier() {
        service.LoadData();
        assertEquals(2, service.showAllOrdersOfSupplier(4).getOutObject().size());
        service.addRegularOrder(4,1);
        service.addRegularOrder(4,1);
        assertEquals(4, service.showAllOrdersOfSupplier(4).getOutObject().size());
        service.addRegularOrder(4,1);
        assertEquals(5, service.showAllOrdersOfSupplier(4).getOutObject().size());
        service.addRegularOrder(2,2);
        assertEquals(5, service.showAllOrdersOfSupplier(4).getOutObject().size());
    }
}