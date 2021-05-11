import BusinessLayer.StockBusiness.Fcade.Response;
import BusinessLayer.StockBusiness.Fcade.StorageService;
import BusinessLayer.StockBusiness.Fcade.outObjects.ProductType;
import BusinessLayer.StockBusiness.StoreController;
import DAL.DalController;
import DAL.DalStock.DALStoreController;
import DAL.Mapper;
import Presentation.mainCLI;
import Utility.Tuple;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class main {
    final static Logger log=Logger.getLogger(main.class);
    public static void main(String[] args) {
        Mapper.getMap("test.db");
         mainCLI CLI = new mainCLI();
         CLI.start(true);

//       Mapper map = Mapper.getMap("test.db");
//        List<Tuple<Object,Class>> b =new ArrayList<>();
//        b.add(new Tuple<>(1,Integer.class));
//        b.add(new Tuple<>(1,Integer.class));
//        b.add(new Tuple<>(1,Integer.class));
//        b.add(new Tuple<>(1,Integer.class));
//        b.add(new Tuple<>(1,Integer.class));
//        b.add(new Tuple<>(1,Integer.class));
//        b.add(new Tuple<>(1,Integer.class));
//
//        map.setItem(DALStoreController.class,b);
//        List<Integer> a=new ArrayList<>();
//        a.add(1);
//        DALStoreController sc=(DALStoreController) map.getItem(DALStoreController.class,a );
//        map.deleteItem(DALStoreController.class,a);
//        Mapper.getMap("test.db");
//        StorageService sc = new StorageService();
//        Response response;
//        response = sc.addStore();
//        if(response.isError) System.out.println("error: " + response.getError());
//        response = sc.useStore(1);
//        if(response.isError) System.out.println("error: " + response.getError());
//        response = sc.addCategory("dairy");
//        if(response.isError) System.out.println("error: " + response.getError());
//        response = sc.addCategory("meat");
//        if(response.isError) System.out.println("error: " + response.getError());
//        response =sc.addCategory("milk" , 1);
//        if(response.isError) System.out.println("error: " + response.getError());
//        response = sc.addProductType("milk" , 2 , Float.parseFloat("2.99") , Float.parseFloat("5.99")  , "tara" ,  0 , 1);
//        if(response.isError) System.out.println("error: " + response.getError());
//        response = sc.addProductType("meat" , 4 , Float.parseFloat("38.99") , Float.parseFloat("50.99")  , "zoglobek" ,  1 , 2);
//        if(response.isError) System.out.println("error: " + response.getError());
//        response = sc.addProduct(1 , new Date(2022-10- 2));
//        if(response.isError) System.out.println("error: " + response.getError());
//        response = sc.addProduct(2 , new Date(2023-2- 2));
//        if(response.isError) System.out.println("error: " + response.getError());
//        response = sc.addProduct(3 , new Date(2022-4- 2));
//        if(response.isError) System.out.println("error: " + response.getError());
//        response = sc.addSaleCategoryDiscount(1 , Float.parseFloat("5") , new Date(2021-10-2) , new Date(2022-10- 2));
//        if(response.isError) System.out.println("error: " + response.getError());
//        response = sc.addSaleCategoryDiscount(2 , Float.parseFloat("7") , new Date(2022-10-1) , new Date(2023-10- 2));
//        if(response.isError) System.out.println("error: " + response.getError());
//        response = sc.addSupplierDiscount(1, Float.parseFloat("9") , new Date(2022-10-1) , new Date(2023-10- 2) , 0);
//        if(response.isError) System.out.println("error: " + response.getError());
//        response = sc.addSupplierDiscount(2, Float.parseFloat("8") , new Date(2022-10-1) , new Date(2023-10- 2) , 1);
//        if(response.isError) System.out.println("error: " + response.getError());
//        response = sc.addSaleProductDiscount(1 , Float.parseFloat("5") , new Date(2021-10-2) , new Date(2023-10- 2));
//        if(response.isError) System.out.println("error: " + response.getError());
//        response = sc.addSaleProductDiscount(2 , Float.parseFloat("6") , new Date(2021-11-2) , new Date(2022-10- 2));
//        if(response.isError) System.out.println("error: " + response.getError());


    }
}
