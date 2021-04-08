import BusinessLayer.StoreController;
import Presentation.CLIPresentation;
import org.apache.log4j.Logger;

public class main {
    final static Logger log=Logger.getLogger(main.class);
    public static void main(String[] args) {

        CLIPresentation cli= new CLIPresentation();
        cli.start();

        StoreController sc = new StoreController();


        //test 1
//        sc.addCategory("1st");
//        for(int i=1;i<10;i++){
//            for(int j=1;j<5;j++) {
//                sc.addCategory("N" + (i*10+j), i);
//            }
//        }
//        log.debug(sc.getCategories());
//        log.debug(sc.getCategory(7));


        //test 2
//        sc.addCategory("1st");
//        for(int j=1;j<5;j++) {
//            sc.addProductType("P"+j,j,1,2,"P",12,1);
//        }
//        log.debug(sc.getProductTypes());
//
//        for(int i=2;i<10;i++){
//            sc.addProduct(i/2,new Date(System.currentTimeMillis()));
//        }
//
//        for(int i=1;i<10;i++) sc.logShelves();
//        sc.logTypeProductList(3);
//        log.debug(sc.getProductInfo(3*1000+1));
//        sc.reportDamage(3*1000+1);
//        log.debug(sc.getProductInfo(3*1000+1));
//        sc.removeProduct(3*1000+1);
//        sc.logTypeProductList(3);
    }
}
