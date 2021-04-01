package BussinessLayer;

import java.util.HashMap;
import java.util.List;
// costumer ask for a new transportation
// the system ask for missing <dic<item,Integer>,sites> ,we get the trucks, we get the driver
// we check for the validity of the input
// if yes => create new transportation
// if not => return error
// return the error message

public class ServiceFaced {
    private DriverService driverService;
    private TruckService truckService;
    private SiteService siteService;
    private TransportationService transportationService;
    private static ServiceFaced serviceFaced = null;

    public static ServiceFaced initial() {
        if(serviceFaced == null){
            serviceFaced = new ServiceFaced();
        }
        return serviceFaced;
    }
    public StringBuilder createTransportation(){
        StringBuilder acc = new StringBuilder();
        return acc;
    }
    public void orderMisses(List<Integer> missingItems){

    }
    public StringBuilder chooseDriver(){
        HashMap<Integer,Driver> drivers = driverService.getDrivers();
        StringBuilder acc = new StringBuilder();
        for (Driver d: drivers) {
            acc.append(d).append("\n");
        }
        return acc;
    }
    public StringBuilder chooseSite(){
        List<Site> sites = siteService.getSites();
        StringBuilder acc = new StringBuilder();
        for (Site s:sites) {
            acc.append(s).append("\n");
        }
        return acc;
    }
    public StringBuilder chooseTruck(){
        StringBuilder acc = new StringBuilder();
        acc.append("Choose a truck: ").append(truckService.getTrucks());
        return acc;
    }
    public StringBuilder registerADriver(int id){
        StringBuilder acc = new StringBuilder();
        try{
            acc.append(driverService.getDriver(id));
        }catch(Exception e){
            return acc.append(e.getMessage());
        }
        return acc;
    }
    public StringBuilder registerATruck(int truckId, int transportationId){
        StringBuilder acc = new StringBuilder();
        try{
            //set a the truck to this transportation
            transportationService.getTransportationById(transportationId).setTruck(truckService.getTruck(truckId));
            acc.append("Truck :").append(truckId).append("was register to a transportation: ").append((transportationId));
        }catch(Exception e){
            acc.append(e.getMessage());
        }
        return acc;
    }
    public StringBuilder registerADriver(int driverID, int transportationId){
        StringBuilder acc = new StringBuilder();
        try {
            transportationService.getTransportationById(transportationId).setDriver(driverService.getDriver(driverID));
            acc.append("Driver : ").append(driverID).append("was register to a transportation: ").append(transportationId);
        }catch (Exception e){
            acc.append(e.getMessage());
        }
        return acc;
    }
}
