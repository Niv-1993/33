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

        CLIPresentation cli= new CLIPresentation();
        cli.setup();

//        StorageService ss= new StorageService();
//        try {
//            StorageService.init(ss);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        System.out.println(ss.getProductTypes());
//        System.out.println(ss.getProductsByType(120));

    }
}
