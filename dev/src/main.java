import BusinessLayer.Fcade.StorageService;
import BusinessLayer.Fcade.outObjects.ProductType;
import BusinessLayer.StoreController;
import Presentation.CLIPresentation;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class main {
    final static Logger log=Logger.getLogger(main.class);
    public static void main(String[] args) {

        StorageService ss=new StorageService();
        try {
            init(ss);
        }catch (Exception e) {}
        CLIPresentation cli= new CLIPresentation(ss);
        cli.start();


//        StorageService ss=new StorageService();
//        try{
//            ResponseData<Integer> storeId = ss.addStore();
//            ss.useStore(storeId.data);
//            for (int i = 0; i < 5; i++) {
//                ss.addCategory("test"+i);
//            }
//            for (int i = 0; i < 5; i++) {
//                ss.addProductType("name"+i,15,59,15,"ddfd",15,2);
//            }
//            List<Integer> list=ss.getProductTypes().getData().getData();
//            for (Integer i: list) {
//                Date day = new Date();
//                day.setTime(day.getDate() + 1);
//                for (int j = 0; j < 15; j++)
//                    ss.addProduct(i, day);
//                if(ss.getProductTypeInfo(i).getData().getCount()!=15) log.warn("setup fail");
//                Assertions.assertEquals(15,ss.getProductTypeInfo(i).getData().getCount());
//            }
//        }
//        catch (Exception e){
//            log.warn("setup fail");
//        }

        //use4(ss);
        //use5(ss);
        //use7(ss);
    }



    private static void use4(StorageService ss){
        try {
            if(20!=ss.getProductTypes().getData().size()) log.warn("use4 setup fail");

            List<Integer> list=ss.getProductTypes().getData().getData();
            BusinessLayer.Fcade.outObjects.ProductType pt=ss.getProductTypeInfo(list.get(5)).data;
            ss.editProductType(pt.getTypeID(), "asdasd",15,7,15,"ddfd",15,4);
            ProductType pt2=ss.getProductTypeInfo(list.get(5)).getData();
            System.out.println(pt);
            System.out.println(pt2);
            if(pt.getCategoryID()==pt2.getCategoryID()) log.warn("use4 fail");
        }
        catch (Exception e){
            log.warn("setup fail");
        }
    }
    private static void use5(StorageService ss){
        try {
            List<Integer> list=ss.getProductTypes().getData().getData();

            for (Integer i: list){
                for (int j=0; j<5; j++)
                    ss.reportDamage(i* StoreController.getMaxProdOnType()+j+1);

                if(15!=ss.getProductTypeInfo(i).getData().getCount()) log.warn("use5 fail 1");
                for (int j=0; j<5; j++)
                    ss.removeProduct(1);
                if(15!=ss.getProductTypeInfo(i).getData().getCount()) log.warn("use5 fail 2");
            }
        }
        catch (Exception e){
            log.warn("use5 fail e");
        }
    }

    private static void use7(StorageService ss){
        try {
            List<Integer> list=ss.getProductTypes().getData().getData();
            Integer checkStore=ss.getShelvesAmount(list.get(0)).data;
            Integer checkStorage=ss.getStorageAmount(list.get(0)).data;
            log.info(checkStore);
            for (int i=0 ;i<10; i++) {
                Date day=new Date(+1);
                ss.addProduct(list.get(0), day);
            }
            Integer checkStore2=ss.getShelvesAmount(list.get(0)).data;
            Integer checkStorage2=ss.getStorageAmount(list.get(0)).data;
            log.info(checkStore+" "+checkStore2);
            if(checkStore==checkStore2) log.warn("use7 fail store 1");
            if(checkStorage!=checkStorage2) log.warn("use7 fail storage 1");
            for (int i=0 ;i<10; i++) {
                ss.relocateProduct(list.get(0)*StoreController.getMaxProdOnType()+i,true,990);
                log.info(list.get(0)*StoreController.getMaxProdOnType()+i);
            }
            checkStore=ss.getShelvesAmount(list.get(0)).data;
            checkStorage=ss.getStorageAmount(list.get(0)).data;
            log.info(checkStore+" "+checkStore2);
            if(checkStore==checkStore2) log.warn("use7 fail store 2");
            log.info(checkStorage+" "+checkStorage2);
            if(checkStorage==checkStorage2) log.warn("use7 fail storage 2");
        }
        catch (Exception e){
            log.warn("use7 fail e");
        }
    }

    private static void init(StorageService ss) throws ParseException {
        ss.addStore();
        ss.useStore(1);
        for(int i=1;i<10;i++) ss.addCategory("root"+i);
        for(int i=1;i<21;i++){
            ss.addCategory("ch"+i,i);

        }
        for(int i=1;i<15;i++){
            for(int j=1;j<10;j++){
                ss.addProductType("p"+i+""+j,2,i*j/2,i*j*j/4,"P"+i+""+j,1,i);
            }
        }
        for(int i=0;i<ss.getProductTypes().data.size();i++){
            for(int j=0;j<i*3;j++){
                ss.addProduct(i,new Date(System.currentTimeMillis()));
            }
        }
        ss.addSaleCategoryDiscount(1,12,new SimpleDateFormat("dd-MM-yyyy").parse("01-01-2000"),new SimpleDateFormat("dd-MM-yyyy").parse("01-01-2050"));
//        System.out.println("DONE INIT");

//        for(int i=0;i<ss.getProductTypes().data.size();i++){
//            System.out.println("products for type "+i+": "+ss.getProductsByType(i));
//        }
    }
}
