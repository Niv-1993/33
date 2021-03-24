package ServiceLayer;
import BussinessLayer.ServiceFaced;

import java.util.*;

public class Controller {
    private static Controller control = null;
    private ServiceFaced serviceControl;

    public static Controller initial(){
        if(control == null){
            control=new Controller();
        }
        return control;
    }
    private Controller (){
        serviceControl = ServiceFaced.initial();
    }
    public void orderMissing(List<Integer> misses){

        serviceControl.orderMisses(misses);
    }
}
