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


public class TransportationService {

    private final TruckService truckService;
    private final DataControl dataControl;
    private long idCounter = 0;
    private final DriverRoleController drivers;

    public TransportationService(iManagerRoleController mc) {

        dataControl=new DataControl();
        idCounter=getId();
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

    public void setDriver(long transId, Driver driver) throws Exception {
        Transportation t = getTransportationById(transId);
        dataControl.setDriverOnTrans(transId,driver);
        t.setDriver(driver);
    }


    /**
     * Adding truck to a specific transportation.
     * @param transId: to transportation id to add to.
     * @param truck : the truck object to add.
     */
    public void setTruck(long transId, Truck truck) throws Exception {
        Transportation t = getTransportationById(transId);
        t.setTruck(truck);
        dataControl.setTruckOnTrans(transId,truck);

    }

    /**
     * Add time to new transportation.
     * @param id :  the transportation id to add to.
     * @param leavingTime : the time to set.
     */
    public void setTransportationTime(long id, LocalTime leavingTime) throws Exception {
        getTransportationById(id).setLeavingTime(leavingTime);
        dataControl.setTime(id,leavingTime);
    }

    /**
     *
     * @param id :  the transportation id to add to.
     * @param date : the date to set.
     */
    public void setDate(long id, LocalDate date) throws Exception {
        getTransportationById(id).setDate(date);
        dataControl.setDate(id,date);
    }

    /**
     *
     * @param id :  the transportation id to add to.
     * @param weight: the weight to set.
     */
    public void setTransportationWeight(long id, int weight) throws Exception {

        getTransportationById(id).setWeight(weight);
        dataControl.setWeight(id,weight);
    }

    /**
     * Initializing new transportation object.
     * @return : the new object
     */
    public Transportation newTransportation() throws Exception {
        Transportation tra=new Transportation(idCounter);
        dataControl.addTransportation(tra);
        idCounter++;
        return tra;
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
     * sets an area to new transportation.
     * @param id : The transportation id.
     * @param area : the area should be added.
     */
    public void setArea(long id, Area area) throws Exception {

        getTransportationById(id).setShippingArea(area);
    }

    /**
     * Method to return all transportations that has been made.
     * @return : list of all transportations.
     */
    public List<Transportation> getTransportationsList() throws Exception {
        return dataControl.getTransportationsList();
    }

    public void deleteTransport() {
        idCounter--;
        dataControl.remove(idCounter);
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
    public void cancelTran(){
        //TODO
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
