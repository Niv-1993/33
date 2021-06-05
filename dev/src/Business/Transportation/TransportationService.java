package Business.Transportation;

import Business.ApplicationFacade.DriverRoleController;
import Business.ApplicationFacade.ResponseData;
import Business.ApplicationFacade.iControllers.iManagerRoleController;
import Business.ApplicationFacade.outObjects.TransportationServiceDTO;
import Business.Employees.EmployeePKG.Driver;
import Business.Type.Area;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class TransportationService {

    private final TruckService truckService;
    private final DataControl dataControl;
    private final DriverRoleController drivers;

    public TransportationService(iManagerRoleController mc) {

        dataControl=new DataControl();
        drivers = new DriverRoleController(mc);
        truckService=new TruckService();
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
     * returns a transportation by it's id.
     * @param id :  the transportation id to add to.
     * @return: The transportation object that wanted.
     */
    public Transportation getTransportationById(long id) throws Exception {

        return dataControl.getTransportation(id);

    }


    /**
     * Method to return all transportations that has been made.
     * @return : list of all transportations.
     */
    public List<Transportation> getTransportationsList() throws Exception {
        return dataControl.getTransportationsList();
    }


    private Branch getBranchById(int id) throws Exception {
       return dataControl.getBranch(id);
    }

    public boolean updateOrderOnTrans(long tranId,Order updatedOrder) throws Exception {
        Transportation tran =dataControl.getTransportation(tranId);
        if(tran.canChange(updatedOrder)){
            dataControl.updateTransWeight(tranId,updatedOrder.getWeight(),updatedOrder);
            return  true;
        }
        return false;
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
    public void removeOrderFromTran(long tranId,int orderId) throws Exception {
        Transportation toDelete = dataControl.getTransportation(tranId);
        Order deleteOrder = toDelete.removeOrder(orderId);
        drivers.removeDriverFromShiftAndStorekeeper(deleteOrder.getBranchID(),toDelete.getDriver().getEID(),toDelete.getDate(),toDelete.getLeavingTime());
        deleteOrder.removeOrder();
        if(toDelete.isEmpty())
            dataControl.remove(tranId);

    }
    public long addOrderToTransportation(Order order) throws Exception {
        Branch bran = getBranchById(order.getBranchID());
        List<Transportation> trans = dataControl.getTransportationsByArea(bran.getArea());
        for (Transportation tran : trans) {
            if (tran.canAdd(order)) {
                dataControl.updateTransWeight(tran.getId(), order.getWeight(), order);
                return tran.getId();
            }
        }
        LocalDate days = (order.getOrderType() == 0) ? LocalDate.now().plusDays(7) : LocalDate.now().plusDays(2);
        LocalTime noon = LocalTime.parse("15:00");
        LocalTime morning = LocalTime.parse("09:00");
        List<Truck> trucks = dataControl.getTrucksByWeight(order.getWeight());
        Truck chooseTruck = null;
        if (trucks.isEmpty()) throw new IllegalArgumentException("No truck compatible for this order's weight. ");
        chooseTruck = trucks.get(0);

        Driver chosenDriver = null;
        LocalDate date = null;
        LocalTime leavingTime= null;
        for (LocalDate i = LocalDate.now(); i.compareTo(days) <= 0; i = LocalDate.now().plusDays(1)) {
            List<Driver> driverList = new LinkedList<>();
            if (drivers.checkAvailableStoreKeeperAndShifts(bran.getId(), i, morning) & drivers.checkAvailableDriver(bran.getId(), i, morning)) {
                driverList = drivers.chooseDriver(i, morning);
                leavingTime = morning;
            } else if (drivers.checkAvailableStoreKeeperAndShifts(bran.getId(), i, noon) & drivers.checkAvailableDriver(bran.getId(), i, noon)) {
                //when there are driver and storeKeeper for noon
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
            throw new IllegalArgumentException("no driver compatible for this order");
        }
        HashMap <Integer,Order> newOrdersList = new HashMap<>();
        newOrdersList.put(order.getOrderId(),order);
        Transportation transportation = new Transportation(getId(),date,leavingTime,chosenDriver,chooseTruck,order.getWeight(),newOrdersList);
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

        //TODO:implement
        return null;
    }
    public void addTruck(long id, int maxWeight, String model, int netWeight, int license){
        dataControl.addTruck(id, maxWeight,model,netWeight,license);
    }
    public List<TransportationServiceDTO> getTransportations(int currBID, LocalDate date, LocalTime time){
        List<TransportationServiceDTO> returnT = new LinkedList<>();
        try {
            List<Transportation> transportations = dataControl.getTransportations(currBID,date,time);
            for (Transportation t: transportations){
                returnT.add(toTransportationServiceDTO(t));
            }
            return returnT;
        }catch (Exception e){
            throw  new IllegalArgumentException(e.getMessage());
        }
    }
}
