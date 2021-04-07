package BusinessLayer;

import BusinessLayer.Type.Category;
import BusinessLayer.Type.ProductType;
import BusinessLayer.instance.InstanceController;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import reports.Report;

import java.util.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class iStoreControllerTest {
    iStoreController sc;

    public iStoreControllerTest(){
        sc=new StoreController();
    }
    @BeforeEach
    void setUp() {
        Category c=mock(Category.class);
        when(c.get_categories()).thenReturn(new ArrayList<>());
    }


    @Test
    void getID() {
        int check=sc.getID();
        if (check<0)
            Assert.fail();
        for (int i=0; i<20; i++)
            if (check!=sc.getID())
                Assert.fail();
    }
    private List<ProductType> listOfProductType(int num){
        List<ProductType> productTypeList=new ArrayList<>();
        for (int i = 0; i < num; i++) {
            productTypeList.add(mock(ProductType.class));
        }
        return productTypeList;
    }
    private List<Category> listOfCategory(int num){
        List<Category> list=new ArrayList<>();
        for (int i = 0; i < num; i++) {
            list.add(mock(Category.class));
        }
        return list;
    }
    private List<InstanceController> listOfIC(int num) {
        List<InstanceController> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            list.add(mock(InstanceController.class));
        }
        return list;
    }

    @Test
    @ParameterizedTest
    @ValueSource(ints = {0,1,5,10,20})
    void getWeeklyReport(int i) {
        Report r=sc.getWeeklyReport();
        Assertions.assertEquals(r.getType(), "WeeklyReport", "the type should be WeeklyReport");
        report(r);

        Dictionary<ProductType,InstanceController> d=createList(i);
        for (Enumeration<InstanceController> ic=d.elements(); ic.hasMoreElements();)
            when(ic.nextElement().getWeeklyReport()).thenReturn(new Hashtable<>());
        sc.setList(d);
        Report r2=sc.getWeeklyReport();
        Assertions.assertEquals(r2.sizeOfList(),i,"should be 20");
    }
    @Test
    void report(Report r){
        Assertions.assertTrue(r.getDate().before(new Date(System.currentTimeMillis())),"the date is not valid");
        Assertions.assertEquals(r.getStore(),sc.getID(),"the storeId is not equals");
    }

    private Dictionary<ProductType,InstanceController> createList(int i){
        Dictionary<ProductType,InstanceController> d=new Hashtable<>();
        List<ProductType> list1=listOfProductType(i);
        List<InstanceController> list2=listOfIC(i);
        for (int j = 0; j < i; j++) {
            d.put(list1.get(j), list2.get(j));
        }
        return d;
    }

    @Test
    @ParameterizedTest
    @ValueSource(ints = {0,1,5,10,20})
    void getNeededReport(int i) {
        Report r=sc.getNeededReport();
        Assertions.assertEquals(r.getType(), "NeededReport", "the type should be NeededReport");
        report(r);

        Dictionary<ProductType,InstanceController> d=createList(i);
        for (Enumeration<ProductType> ic=d.keys(); ic.hasMoreElements();)
            when(ic.nextElement().getNeededReport()).thenReturn(i);
        sc.setList(d);
        Report r2=sc.getNeededReport();
        Assertions.assertEquals(r2.sizeOfList(),i,"should be 20");

        d=createList(i);
        for (Enumeration<ProductType> ic=d.keys(); ic.hasMoreElements();)
            when(ic.nextElement().getNeededReport()).thenReturn(0);
        sc.setList(d);
        r2=sc.getNeededReport();
        Assertions.assertEquals(r2.sizeOfList(),0,"should be 20");

    }

    @Test
    void getWasteReport() {
        Report r=sc.getWasteReport();
        Assertions.assertEquals(r.getType(), "WasteReport", "the type should be WasteReport");
        report(r);
    }

    @Test
    void counterCategory() {
        int counter=sc.counterCategory();
        for (int i=0; i<20; i++)
        {
            sc.addCategory("test "+i);
            if (counter+i!=sc.counterCategory())
                Assert.fail("the SC does not added Category #"+i);
        }
    }

    @Test
    void getCategory() {
        for (int i=0; i<20; i++)
        {
            sc.addCategory("test "+i);
            if (i!=sc.counterCategory())
                Assert.fail("the SC does not added Category #"+i);
        }
    }

    @Test
    void addCategory() {

    }

    @Test
    void getCategories() {
    }

    @Test
    void addProductType() {
    }

    @Test
    void getProductTypes() {
    }

    @Test
    void getProductTypeInfo() {
    }

    @Test
    void getShelvesAmount() {
    }

    @Test
    void shelvesAmountExist() {
    }

    @Test
    void storageAmountExist() {
    }

    @Test
    void getStorageAmount() {
    }

    @Test
    void testAddCategory() {
    }

    @Test
    void addSaleProductDiscount() {
    }

    @Test
    void counterDiscount() {
    }

    @Test
    void addSaleCategoryDiscount() {
    }

    @Test
    void addSupplierDiscount() {
    }

    @Test
    void getSupplierDiscounts() {
    }

    @Test
    void getSaleDiscounts() {
    }

    @Test
    void editCategory() {
    }

    @Test
    void testEditCategory() {
    }

    @Test
    void editProductType() {
    }

    @Test
    void addProduct() {
    }

    @Test
    void removeProduct() {
    }

    @Test
    void reportDamage() {
    }

    @Test
    void getProductInfo() {
    }

    @Test
    void relocateProduct() {
    }

}