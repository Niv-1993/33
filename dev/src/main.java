import DAL.Mapper;
import Presentation.mainCLI;
import org.apache.log4j.Logger;

public class main {
    final static Logger log=Logger.getLogger(main.class);
    public static void main(String[] args) {
        Mapper.getMap("test.db");
        mainCLI CLI = new mainCLI();
//         CLI.start(true);
//        StorageService sc = new StorageService();
//        Response response;
//        response = sc.addStore();
//        if(response.isError) System.out.println("1- error: " + response.getError());
//        response = sc.useStore(1);
//        if(response.isError) System.out.println("2 - error: " + response.getError());
//        response = sc.addCategory("dairy");
//        if(response.isError) System.out.println("3 - error: " + response.getError());
//        response = sc.addCategory("meat");
//        if(response.isError) System.out.println("4 - error: " + response.getError());
//        response =sc.addCategory("milk" , 1);
//        if(response.isError) System.out.println("5 - error: " + response.getError());
//        response = sc.addProductType("milk" , 2 , 2.99 , 5.99  , "tara" ,  0 , 1);
//        if(response.isError) System.out.println("6 - error: " + response.getError());
//        response = sc.addProductType("meat" , 4 , 38.95 , 50.89 , "zoglobek" ,  1 , 2);
//        if(response.isError) System.out.println("7 - error: " + response.getError());
//        response = sc.addProduct(1 , new Date(2022-10-2));
//        if(response.isError) System.out.println("8 - error: " + response.getError());
//        response = sc.addProduct(2 , new Date(2023-2-2));
//        if(response.isError) System.out.println("9 - error: " + response.getError());
//        response = sc.getProductInfo(0);
//        if(response.isError) System.out.println("10 - error: " + response.getError());
        CLI.start(true);
        //CLI.start(true);


    }
}
