package tests;

import BusinessLayer.StockBusiness.Fcade.ResponseData;
import BusinessLayer.StockBusiness.Fcade.StorageService;
import BusinessLayer.StockBusiness.Fcade.iStorageService;
import BusinessLayer.StockBusiness.Fcade.outObjects.ProductType;
import BusinessLayer.StockBusiness.Fcade.outObjects.SaleDiscount;
import BusinessLayer.StoreController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

class useCases {
    iStorageService ss;
    public useCases(){
        ss=new StorageService();
    }

    @BeforeEach
    void setUp() {
        try{
            ResponseData<Integer> storeId = ss.addStore();
            ss.useStore(storeId.data);
            for (int i = 0; i < 20; i++) {
                ss.addCategory("test"+i);
            }
            for (int i = 0; i < 20; i++) {
                ss.addProductType("name"+i,15,59,15,"ddfd",15,2);
            }
            List<Integer> list=ss.getProductTypes().getData().getData();
            for (Integer i: list) {
                Date day = new Date();
                day.setTime(day.getDate() + 1);
                for (int j = 0; j < 15; j++)
                    ss.addProduct(i, day);
                Assertions.assertEquals(15,ss.getProductTypeInfo(i).getData().getCount());
            }
        }
        catch (Exception e){
            Assertions.fail();
        }
    }

    @Test
    void useCase1(){
        try {
            for (int i = 0; i < 20; i++) {
                ss.addCategory("test2" + i,i+1);
            }
            Assertions.assertEquals(40, ss.getCategories().getData().size());
        }
        catch (Exception e){
            Assertions.fail();
        }
    }

    @Test
    void useCase2(){//same category name
        try {
            ss=new StorageService();
            ResponseData<Integer> storeId = ss.addStore();
            ss.useStore(storeId.data);
            for (int i = 0; i < 20; i++) {
                ss.addCategory("test");
            }
            for (int i = 0; i < 20; i++) {
                ss.addCategory("test",5);
            }
            Assertions.assertEquals(1, ss.getCategories().getData().size());
        }
        catch (Exception e){
            Assertions.fail();
        }
    }
    @Test
    void useCase3(){//add productType
        try {
            List<Integer> list=ss.getProductTypes().getData().getData();
            for (Integer i: list) {
                Assertions.assertEquals(15,ss.getProductTypeInfo(i).getData().getCount());
            }
        }
        catch (Exception e){
            Assertions.fail();
        }
    }
    @Test
    void useCase4(){//add productType
        try {
            Assertions.assertEquals(20,ss.getProductTypes().getData().size());

            List<Integer> list=ss.getProductTypes().getData().getData();
            ProductType pt=ss.getProductTypeInfo(list.get(5)).data;
            ss.editProductType(pt.getTypeID(), "asdasd",15,59,15,"ddfd",15,4);
            ProductType pt2=ss.getProductTypeInfo(list.get(5)).getData();
            Assertions.assertNotEquals(pt.getCategoryID(),pt2.getCategoryID());
        }
        catch (Exception e){
            Assertions.fail();
        }
    }
    @Test
    void useCase5(){
        try {
            List<Integer> list=ss.getProductTypes().getData().getData();

            for (Integer i: list){
                List<Integer> products=ss.getProductsByType(i).getData();
                for (int j=0; j<5; j++)
                    ss.reportDamage(products.get(j));
                Assertions.assertEquals(10,ss.getProductTypeInfo(i).getData().getCount());
                for (int j=0; j<5; j++)
                    ss.removeProduct(1);
                Assertions.assertEquals(10,ss.getProductTypeInfo(i).getData().getCount());
            }
        }
        catch (Exception e){
            Assertions.fail();
        }
    }
    @Test
    void useCase6(){
        try {
            List<Integer> list=ss.getProductTypes().getData().getData();

            for (Integer i: list){
                for (int j=0; j<5; j++)
                    ss.removeProduct(i* StoreController.getMaxProdOnType()+j+1);
                Assertions.assertEquals(10,ss.getProductTypeInfo(i).getData().getCount());
                for (int j=0; j<5; j++)
                    ss.removeProduct(1);
                Assertions.assertEquals(10,ss.getProductTypeInfo(i).getData().getCount());
            }
        }
        catch (Exception e){
            Assertions.fail();
        }
    }
    @Test
    void useCase7(){//amount of shelves + relocate
        try {
            List<Integer> list=ss.getProductTypes().getData().getData();
            Integer checkStore=ss.getShelvesAmount(list.get(0)).data;
            Integer checkStorage=ss.getStorageAmount(list.get(0)).data;
            for (int i=0 ;i<950; i++) {
                Date day=new Date(+1);
                ss.addProduct(list.get(0), day);
            }
            Integer checkStore2=ss.getShelvesAmount(list.get(0)).data;
            Integer checkStorage2=ss.getStorageAmount(list.get(0)).data;
            Assertions.assertNotEquals(checkStore,checkStore2);
            Assertions.assertEquals(checkStorage2,checkStorage);
            List<Integer> products=ss.getProductsByType(list.get(0)).data;
            for (int i=0 ;i<100; i++) {
                ss.relocateProduct(products.get(i),true,990);
            }
            checkStore=ss.getShelvesAmount(list.get(0)).data;
            checkStorage=ss.getStorageAmount(list.get(0)).data;
            Assertions.assertNotEquals(checkStore,checkStore2);
            Assertions.assertNotEquals(checkStorage2,checkStorage);
        }
        catch (Exception e){
            Assertions.fail();
        }
    }

    @Test
    void useCase8(){//sale discount
        try {
            List<Integer> productType = ss.getProductTypes().getData().getData();
            List<Integer> categories = ss.getCategories().data.getList();
            for (Integer i: productType){
                ss.addSaleProductDiscount(i,0.5F*i/100,new Date(+i),new Date(+8L *i));
                List<SaleDiscount> sale=ss.getSaleDiscounts(i).data.getDiscounts();
                for (SaleDiscount sd: sale){
                    Assertions.assertEquals(0.5F*i/100,sd.getPrecent());
                    Assertions.assertEquals(new Date(+i),sd.getStart());
                    Assertions.assertEquals(new Date(+8L *i),sd.getEnd());
                }
            }
        }
        catch (Exception e){
            Assertions.fail();
        }
    }

    @Test
    void useCase9(){//sale category discount
        try {
            ss=new StorageService();
            ss.useStore(ss.addStore().data);
            ss.addCategory("test");
            List<Integer> cat=ss.getCategories().getData().getList();
            ss.addCategory("test2",cat.get(0));
            List<Integer> categories = ss.getCategories().data.getList();

            for (int i = 0; i < 20; i++) {
                ss.addProductType("name"+i,15,59,15,"ddfd",15,cat.get(0));
            }

            List<Integer> productType = ss.getProductTypes().getData().getData();
            System.out.println(productType.size());
            for (Integer i: productType){
            //    ss.addSupplierDiscount(categories.get(0), 0.5F,new Date(+15),new Date(+30),15);
                Date start=new Date();
                Date end=new Date();
                start.setDate(start.getDate()+1);
                end.setDate(start.getDate()+6);
                ss.addSaleCategoryDiscount(categories.get(0),0.5F,start,end);
                ss.addSaleCategoryDiscount(categories.get(1),0.5F,start,end);
            }

            for (Integer i: productType){
                Assertions.assertEquals(productType.size(),ss.getSaleDiscounts(i).getData().size());
                ss.editProductType(i,"name"+i,15,59,15,"ddfd",
                        15,(cat.get(0)==categories.get(0)? categories.get(1): categories.get(0)));
                Assertions.assertEquals(40,ss.getSaleDiscounts(i).getData().size());
            }


        }
        catch (Exception e){
            Assertions.fail();
        }
    }



}