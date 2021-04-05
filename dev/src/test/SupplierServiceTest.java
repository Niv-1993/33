package test;

import static org.junit.Assert.*;

import BussniesLayer.SupplierCard;
import BussniesLayer.facade.SupplierService;
import BussniesLayer.facade.outObjects.Item;
import org.junit.Test;
import BussniesLayer.facade.response;
import BussniesLayer.facade.Tresponse;

public class SupplierServiceTest {
    SupplierService service = new SupplierService();

    @Test
    public void testAddSupplier()  {
        response r = service.addSupplier("Test", 654321 , "cash");
        assertFalse(r.isError());;
    }

    @Test
    public void testGetItemIDFailure() {
        response r = service.addItem(1, "dairy", 3.5);
        assertTrue(r.isError());
        assertEquals("supplier BN is not exist" , r.getError());
        service.addSupplier("Test", 654321 , "cash");
        response r2 = service.addItem(0, "dairy", -5);
        assertTrue(r2.isError());
        assertEquals("price must be a positive number!" , r2.getError());
    }

    @Test
    public void testGetItemIDWithLoad(){
        service.LoadData();
        Item item = service.addItem(0, "dairy", 3.5).getOutObject();
        assertEquals("19" , item.toStringId());
    }

    @Test
    public void testShowSupplier() {
        service.LoadData();
        BussniesLayer.facade.outObjects.SupplierCard s = service.showSupplier(0).getOutObject();
        assertEquals( 0, s.getSupplierBN());
        assertEquals( 1, s.getAccountNumber());
        assertEquals( "check", s.getPayWay());
    }

    @Test
    public void testUpdateSupplierPayWay() {
        service.LoadData();
        service.updateSupplierPayWay(0, "cash");
        assertEquals("cash", service.showSupplier(0).getOutObject().getPayWay());
    }

    @Test
    public void testRemoveSupplier() {
        service.LoadData();
        assertEquals(6, service.showAllSuppliers().getOutObject().size());
        service.removeSupplier(3);
        assertEquals(5, service.showAllSuppliers().getOutObject().size());
        assertTrue(service.showSupplier(3).isError());
    }

    @Test
    public void testAddItemToNullSupplier() {
        service.LoadData();
        assertTrue(service.addItem(10,"Test", 10).isError());
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
        service.addSupplier("Test", 1 , "cash");
        assertTrue(service.showSupplierAgreement(0).isError());
        service.addSupplierAgreement(0, 5, 5 ,false, false);
        assertFalse(service.showSupplierAgreement(0).isError());
    }

    @Test
    public void testShowAllOrdersOfSupplier() {
        service.LoadData();
        assertEquals(2, service.showAllOrdersOfSupplier(4).getOutObject().size());
        service.addOrder(4);
        service.addOrder(4);
        assertEquals(4, service.showAllOrdersOfSupplier(4).getOutObject().size());
        service.addOrder(4);
        assertEquals(5, service.showAllOrdersOfSupplier(4).getOutObject().size());
        service.addOrder(2);
        assertEquals(5, service.showAllOrdersOfSupplier(4).getOutObject().size());
    }
}