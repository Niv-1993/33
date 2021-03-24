package Bussiness;

import java.util.List;

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
