package BussinessLayer;

import java.util.List;
// costumer ask for a new transportation
// the system ask for missing <dic<item,Integer>,sites> ,we get the trucks, we get the driver
// we check for the validity of the input
// if yes => create new transportation
// if not => return error
// return the error message

public class ServiceFaced {
    private DriverService driverService;
    private TruckService trackService;
    private SiteService siteService;
    private ServiceFaced(){

    }
    public static ServiceFaced initial() {

    }
    public void orderMisses(List<Integer> missingItems){

    }
    public String chooseDriver(){
        System.out.println("chose driver");
        //loop that prints drivers

    }

}
