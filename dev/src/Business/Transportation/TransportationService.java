package Business.Transportation;

import Business.ApplicationFacade.DriverRoleController;
import Business.ApplicationFacade.ResponseData;
import Business.ApplicationFacade.iControllers.iManagerRoleController;
import Business.ApplicationFacade.outObjects.DriverServiceDTO;
import Business.ApplicationFacade.outObjects.TransportationServiceDTO;
import Business.ApplicationFacade.outObjects.TruckServiceDTO;
import Business.Employees.EmployeePKG.Driver;
import Business.SupplierBusiness.Order;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


public class TransportationService {

    private final DataControl dataControl;
    private final DriverRoleController drivers;

    public TransportationService(iManagerRoleController mc) {

        dataControl=new DataControl();
        drivers = new DriverRoleController(mc);
    }

    private long getId() {
        try {
            return dataControl.getCurrID()+1;
        }
        catch (Exception e){
            return -1;
        }
    }

    /**
     * Method to return all transportations that has been made.
     * @return : list of all transportations.
     */
    public List<Transportation> getTransportationsList() throws Exception {
        return dataControl.getTransportationsList();
    }


    public Branch getBranchById(int id) throws Exception {
       return dataControl.getBranch(id);
    }

    public void cancelTran(long tranId) throws Exception {
        Transportation toDelete = dataControl.getTransportation(tranId);
        List<Order> orders = toDelete.getOrderList();
        for (Order order:orders){
            drivers.removeDriverFromShiftAndStorekeeper(order.getBranchID(),toDelete.getDriver().getEID(),toDelete.getDate(),toDelete.getLeavingTime());
            order.removeOrder();
            dataControl.remove(tranId);
        }
    }


    public long addOrderToTransportation(Order order) throws Exception {
        Branch bran = getBranchById(order.getBranchID());
        List<Transportation> trans = dataControl.getTransportationsByArea(bran.getArea());
        for (Transportation tran : trans) {
            if (tran.canAdd(order)) {
                dataControl.updateTransWeight(tran.getId(), order.getTotalWeight(), order);
                return tran.getId();
            }
        }
        LocalDate days = (order.getOrderType() == 0) ? LocalDate.now().plusDays(7) : LocalDate.now().plusDays(2);
        LocalTime noon = LocalTime.parse("15:00");
        LocalTime morning = LocalTime.parse("09:00");
        List<Truck> trucks = dataControl.getTrucksByWeight(order.getTotalWeight());
        if (trucks.isEmpty()) {
            for(Integer eid: drivers.allPersonnelManager(bran.getId())) {
                dataControl.insertAlerts(bran.getId(), eid,days,"Can not add order to a new transportation. No trucks available. Order number: "+ order.getOrderId());
            }
            throw new IllegalArgumentException("No truck compatible for this order's weight. ");
        }
        Truck  chooseTruck = trucks.get(0);
        Driver chosenDriver = null;
        LocalDate date = null;
        LocalTime leavingTime= null;
        for (LocalDate i = LocalDate.now(); i.compareTo(days) <= 0; i = LocalDate.now().plusDays(1)) {
            List<Driver> driverList = new LinkedList<>();
            if (drivers.checkAvailableStoreKeeperAndShifts(bran.getId(), i, morning) & drivers.checkAvailableDriver(bran.getId(), i, morning)) {
                driverList = drivers.chooseDriver(i, morning);
                leavingTime = morning;
            } else if (drivers.checkAvailableStoreKeeperAndShifts(bran.getId(), i, noon) & drivers.checkAvailableDriver(bran.getId(), i, noon)) {
                driverList = drivers.chooseDriver(i, noon);
                leavingTime = noon;
            }
            for (Driver d:driverList) {
                if(d.getLicense()>=chooseTruck.getLicense()){
                    date = i;
                    chosenDriver = d;
                    break;
                }
            }
        }
        if(chosenDriver == null){
            for(Integer eid: drivers.allPersonnelManager(bran.getId())) {
                dataControl.insertAlerts(bran.getId(), eid,days,"Can not add order to a new transportation. No drivers available .Order number: "+ order.getOrderId());
            }
            throw new IllegalArgumentException("no driver compatible for this order");
        }
        HashMap <Integer,Order> newOrdersList = new HashMap<>();
        newOrdersList.put(order.getOrderId(),order);
        Transportation transportation = new Transportation(getId(),date,leavingTime,chosenDriver,chooseTruck,order.getTotalWeight(),newOrdersList);
        dataControl.addTransportation(transportation);
        drivers.addDriverToShiftAndStoreKeeper(order.getBranchID(),chosenDriver.getEID(),date,leavingTime);
        return transportation.getId();
    }

    public ResponseData<List<TransportationServiceDTO>> getDTOTransportations() {
        List<TransportationServiceDTO> returnT = new LinkedList<>();
        try {
            List<Transportation> transportations = getTransportationsList();
            for (Transportation t: transportations){
                returnT.add(toTransportationServiceDTO(t));
            }
            return new ResponseData<>(returnT);
        }catch (Exception e){
            return new ResponseData<>(e.getMessage());
        }
    }
    private TransportationServiceDTO toTransportationServiceDTO(Transportation t){

        HashMap<Integer,Business.SupplierBusiness.facade.outObjects.Order> ordersService=new HashMap<>();
        for(Order order : t.getOrderList())
            ordersService.put(order.getOrderId(),new Business.SupplierBusiness.facade.outObjects.Order(order));
        return new TransportationServiceDTO(t.getId(),t.getDate(),t.getLeavingTime(),new DriverServiceDTO(t.getDriver()),new TruckServiceDTO(t.getTruck()),t.getWeight(),ordersService);
    }
    public void addTruck(long id, int maxWeight, String model, int netWeight, int license){
        dataControl.addTruck(id, maxWeight,model,netWeight,license);
    }
    public List<TransportationServiceDTO> getTransportations( LocalDate date, LocalTime time){
        List<TransportationServiceDTO> returnT = new LinkedList<>();
        try {
            List<Transportation> transportations = dataControl.getTransportations(date,time);
            for (Transportation t: transportations){
                returnT.add(toTransportationServiceDTO(t));
            }
            return returnT;
        }catch (Exception e){
            throw  new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * @return All drivers who can replace the driver in the transportation.
     * @throws Exception
     */
    public List<Integer> checkAvailableDriverSubs(int driverID, String time, LocalDate date, List<Integer> optionalDrivers) throws Exception {
        try {
            Transportation t = dataControl.selectTransWithDriverShift(driverID, time, date);
            if (t == null){
                throw new IllegalArgumentException("No such Transportation to change drivers.");
            }
            List <Driver> drivers=dataControl.getDriversList(optionalDrivers);
            return (drivers.stream().filter(d-> d.getLicense()>=t.getWeight()).collect(Collectors.toList()))
                    .stream().map(d->d.getEID()).collect(Collectors.toList()); //return the list of optional drivers.

        }
        catch (Exception e){
            throw e;
        }
    }

    public void swapDrivers(int newDriverID, int oldDriverID, String time, LocalDate date) throws Exception {

        Transportation t = dataControl.selectTransWithDriverShift(oldDriverID, time, date);
        if (t != null) {

            dataControl.replaceDrivers(t.getId(), newDriverID);
            drivers.changeDriver(t.getTransBranches(),oldDriverID, newDriverID,date,t.getLeavingTime());
        }
        else {
            throw new IllegalArgumentException("No such transportation for driver change.");
        }
    }

    public void removeOrderFromTransportation(long transID, int orderId) {
        try {
            boolean empty = dataControl.removeOrderFromTransportation(transID, orderId);
            if(empty){
                Transportation t=dataControl.getTransportation(transID);
                Order order= new Order();
                Order or=order.getOrder(orderId);
                drivers.removeDriverFromShiftAndStorekeeper(or.getBranchID(),t.getDriver().getEID(),t.getDate(),t.getLeavingTime());
                dataControl.remove(t.getId());
            }
        }
        catch(Exception e){
            System.out.println("Could not remove order number: "+orderId+" from transportation number: "+transID+" .Error: "+e.getMessage());
        }

    }
}
