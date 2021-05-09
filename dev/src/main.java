import DAL.DalStock.DALStoreController;
import DAL.Mapper;
import Utility.Tuple;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class main {
    final static Logger log=Logger.getLogger(main.class);
    public static void main(String[] args) {

       // StockCLI cli= new StockCLI();
       // cli.setup();

        Mapper map = Mapper.getMap("test.db");
        List<Tuple<Object,Class>> b =new ArrayList<>();
        b.add(new Tuple<>(2,Integer.class));
        b.add(new Tuple<>(1,Integer.class));
        b.add(new Tuple<>(1,Integer.class));
        b.add(new Tuple<>(1,Integer.class));
        b.add(new Tuple<>(1,Integer.class));
        b.add(new Tuple<>(1,Integer.class));
        b.add(new Tuple<>(2,Integer.class));

        map.setItem(DALStoreController.class,b);
        List<Integer> a=new ArrayList<>();
        a.add(1);
        DALStoreController sc=(DALStoreController) map.getItem(DALStoreController.class,a );
    }
}
