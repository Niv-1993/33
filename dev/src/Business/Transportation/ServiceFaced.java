package Business.Transportation;
import Business.ApplicationFacade.*;
import Business.ApplicationFacade.Objects.*;
import Business.Type.Area;
import Business.Type.Pair;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class ServiceFaced {
    private final TruckService truckService;
    private final SiteService siteService;
    private final TransportationService transportationService;
    private final ItemService itemService;
    private final DataControl dataControl;
    private final DriverRoleController drivers;



    public ServiceFaced()  {
        truckService = new TruckService();
        siteService = new SiteService();
        transportationService = new TransportationService();
        itemService = new ItemService();
        dataControl=new DataControl();
        drivers = new DriverRoleController(dataControl);
    }

    /**
     *Getters for return an object by it's identifier.
     * @param id: the identifier of the object.
     * @return : the object.
     */
    public ResponseData<DriverServiceDTO> getDriver(long id){
        try {
            Driver driver = drivers.getDriver(id);
            return (driver == null)? new ResponseData<>("No driver is available..."):new ResponseData<>(toDriverServiceDTO(drivers.getDriver(id)));
        }catch (Exception e){
            return new ResponseData<>(e.getMessage());
        }
    }
    public ResponseData<BranchServiceDTO> getBranch(int id){
        try{
            return new ResponseData<>(toBranchServiceDTO(siteService.getBranch(id)));
        }catch (Exception e){
            return new ResponseData<>(e.getMessage());
        }
    }
    public ResponseData<SupplierServiceDTO> getSuppliers(int id){
        try {
            return new ResponseData<>(toSupplierServiceDTO(siteService.getSupplier(id)));
        }catch (Exception e){
            return new ResponseData<>(e.getMessage());
        }
    }
    public ResponseData<TruckServiceDTO> getTruck(long id){
        try{
            return new ResponseData<>(toTruckServiceDTO(truckService.getTruck(id)));
        }catch (Exception e){
            return new ResponseData<>(e.getMessage());
        }
    }
    public ResponseData<ItemServiceDTO> getItem(long id){
        try {
            return new ResponseData<>(toItemServiceDTO(itemService.getItem(id)));
        }catch (Exception e){
            return new ResponseData<>(e.getMessage());
        }
    }


    /**
     * Getters for every object's DTO list.
     * Converts from business objects to presentation objects
     * @return : Response with the objects list inside or an Exception if failed.
     */
    public ResponseData<List<DriverServiceDTO>> getDTODrivers(LocalDate date, LocalTime leavingTIme){
        List<DriverServiceDTO> returnD = new LinkedList<>();
        try {
            List<Driver> drivers1 = drivers.chooseDriver(date,leavingTIme);
            for (Driver d: drivers1) {
                returnD.add(toDriverServiceDTO(d));
            }
            return new ResponseData<>(returnD);
        }catch (Exception e){
            return new ResponseData<>(e.getMessage());
        }
    }
    public ResponseData<List<BranchServiceDTO>> getDTOBranches(){
        List<BranchServiceDTO> returnB = new LinkedList<>();
        try {
            List<Branch> branches = siteService.getBranchesList();
            for (Branch b: branches) {
                returnB.add(toBranchServiceDTO(b));
            }
            return new ResponseData<>(returnB);
        }catch (Exception e){
            return new ResponseData<>(e.getMessage());
        }
    }
    public ResponseData<List<SupplierServiceDTO>> getDTOSuppliers(){
        List<SupplierServiceDTO> returnS = new LinkedList<>();
        try {
            List<Supplier> suppliers = siteService.getSuppliersList();
            for (Supplier s:suppliers) {
                returnS.add(toSupplierServiceDTO(s));
            }
            return new ResponseData<>(returnS);
        }catch (Exception e){
            return new ResponseData<>(e.getMessage());
        }
    }

    public ResponseData<List<ItemServiceDTO>> getDTOItemsBySupplier(int id) {
        List<ItemServiceDTO> returnS = new LinkedList<>();
        try {
            List<Item> supplierItems = itemService.getItemsBySupplier(id);
            for (Item s:supplierItems) {
                returnS.add(toItemServiceDTO(s));
            }
            return new ResponseData<>(returnS);
        }catch (Exception e){
            return new ResponseData<>(e.getMessage());
        }
    }
    public ResponseData<List<TruckServiceDTO>> getDTOTrucks(){
        List<TruckServiceDTO> returnT = new LinkedList<>();
        try {
            List<Truck> trucks = truckService.getTrucksList();
            for (Truck t:trucks) {
                returnT.add(toTruckServiceDTO(t));
            }
            return new ResponseData<>(returnT);
        }catch (Exception e){
            return new ResponseData<>(e.getMessage());
        }
    }

    public ResponseData<List<TransportationServiceDTO>> getDTOTransportations() {
        List<TransportationServiceDTO> returnT = new LinkedList<>();
        try {
            List<Transportation> transportations = transportationService.getTransportationsList();
            for (Transportation t: transportations){
                returnT.add(toTransportationServiceDTO(t));
            }
            return new ResponseData<>(returnT);
        }catch (Exception e){
            return new ResponseData<>(e.getMessage());
        }
    }

    public ResponseData<List<ItemServiceDTO>> getAllDTOItems(){
        List<ItemServiceDTO> returnI = new LinkedList<>();
        try {
            List<Item> allItems = itemService.getItemsList();
            for (Item i: allItems){
                returnI.add(toItemServiceDTO(i));
            }
            return new ResponseData<>(returnI);
        }catch (Exception e){
            return new ResponseData<>(e.getMessage());
        }
    }

    /**
     * Setter methods, for each field of the transportation object try to update in the business layer
     * @param t : the presentation's transportation object to show the user and to contact the business layer
     * @return: Response object with the Business.Transportation obj inside or throws an Exception if failed.
     */
    public ResponseData<TransportationServiceDTO> setTransportationDriver(TransportationServiceDTO t){
        try {
            Driver d = drivers.getDriver(t.getDriver().getId());
            transportationService.setDriver(t.getId(), d);
            //if we success just return the same
            return new ResponseData<>(t);
        }catch (Exception e){
            return new ResponseData<>(e.getMessage());
        }
    }
    public ResponseData<TransportationServiceDTO> setTransportationDeliveryItems(TransportationServiceDTO t ){
             HashMap<Branch,List<Pair<Item,Integer>>> deliveryItemsB = new HashMap<>();
        try {
            List<BranchServiceDTO> branches = t.getBranches();
            for (BranchServiceDTO b: branches){
                if(!drivers.checkAvailableStoreKeeperAndShifts(b.getId(),t.getDate(),t.getLeavingTime())){
                    return new ResponseData<>("branch: " + b.getId()+ "does not have available store-keeper.");
                }
            }
            HashMap<BranchServiceDTO,List<Pair<ItemServiceDTO,Integer>>> deliveryItems = t.getDeliveryItems();
            for (Map.Entry<BranchServiceDTO,List<Pair<ItemServiceDTO,Integer>>> entry: deliveryItems.entrySet()){
                List<Pair<Item,Integer>> delivery = new LinkedList<>();
                Branch b = siteService.getBranch(entry.getKey().getId());
                for (Pair<ItemServiceDTO,Integer> item : entry.getValue()){
                    delivery.add(new Pair<>(itemService.getItem(item.getFir().getId()),item.getSec()));
                }
                deliveryItemsB.put(b,delivery);
            }
            transportationService.setDeliveryItems(t.getId(),deliveryItemsB);
            return new ResponseData<>(t);
        }catch (Exception e){
            return new ResponseData<>(e.getMessage());
        }
    }
    public ResponseData<TransportationServiceDTO> setTransportationSuppliersItems(TransportationServiceDTO t ){
        HashMap<Supplier,List<Pair<Item,Integer>>> deliveryItemsB = new HashMap<>();
        try {
            HashMap<SupplierServiceDTO,List<Pair<ItemServiceDTO,Integer>>> deliveryItems = t.getSuppliers();
            for (Map.Entry<SupplierServiceDTO,List<Pair<ItemServiceDTO,Integer>>> entry: deliveryItems.entrySet()){
                List<Pair<Item,Integer>> delivery = new LinkedList<>();
                Supplier b = siteService.getSupplier(entry.getKey().getId());
                for (Pair<ItemServiceDTO,Integer> item : entry.getValue()){
                    delivery.add(new Pair<>(itemService.getItem(item.getFir().getId()),item.getSec()));
                }
                deliveryItemsB.put(b,delivery);
            }
            transportationService.setSuppliersItem(t.getId(),deliveryItemsB);
            return new ResponseData<>(t);
        }catch (Exception e){
            return new ResponseData<>(e.getMessage());
        }
    }

    public ResponseData<TransportationServiceDTO> setTransportationTruck(TransportationServiceDTO t){
        try {
            Truck truck = truckService.getTruck(t.getTruck().getId());
            transportationService.setTruck(t.getId(),truck);
            return new ResponseData<>(t);
        }catch (Exception e){
            return new ResponseData<>(e.getMessage());
        }
    }
    public ResponseData<TransportationServiceDTO> setTransportationTime(TransportationServiceDTO t){
        try {
            transportationService.setTransportationTime(t.getId(),t.getLeavingTime());
            return new ResponseData<>(t);
        }catch (Exception e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }
    public ResponseData<TransportationServiceDTO> setTransportationDate(TransportationServiceDTO t){
        try {
            transportationService.setDate(t.getId(),t.getDate());
            return new ResponseData<>(t);
        }catch (Exception e){
            return new ResponseData<>(e.getMessage());
        }
    }
    public ResponseData<TransportationServiceDTO> setTransportation(TransportationServiceDTO t){
        try {
            List<BranchServiceDTO> Branches = t.getBranches();
            for (BranchServiceDTO b: Branches){
                drivers.addDriverToShiftAndStoreKeeper(b.getId(),t.getDriver().getId(),t.getDate(),t.getLeavingTime());
            }
            return new ResponseData<>(toTransportationServiceDTO( transportationService.saveTransportation(t.getId())));
        }
        catch (Exception e){
            return new ResponseData<>(e.getMessage());
        }
    }
    public ResponseData<TransportationServiceDTO> setArea(TransportationServiceDTO t){
        try {
            transportationService.setArea(t.getId(),t.getArea());
            return new ResponseData<>(t);
        }catch (Exception e){
            return new ResponseData<>(e.getMessage());
        }
    }

    public ResponseData<TransportationServiceDTO> setTransportationWeight(TransportationServiceDTO t) {
        try {
            transportationService.setTransportationWeight(t.getId(),t.getWeight());
            return new ResponseData<>(t);
        }catch (Exception e){
            return new ResponseData<>(e.getMessage());
        }
    }

    /**
     * Method for converting an object to its presentation's form.
     * @param d :the business object.
     * @return : it's form for the presentation.
     */
    private DriverServiceDTO toDriverServiceDTO(Driver d){
        if(d==null){
            return null;
        }
        return new DriverServiceDTO(d.getId(),(d.getLicense()));
    }
    private BranchServiceDTO toBranchServiceDTO(Branch b){
        if(b==null)
            return null;
        return new BranchServiceDTO(b.getPhone(),b.getContactName(),b.getId(),b.getShippingArea().getArea().toString());
    }
    private Pair<ItemServiceDTO,Integer> toItemPairServiceDTO(Pair<Item,Integer> i){
        if(i==null)
            return null;
        return new Pair<>(new ItemServiceDTO(i.getFir().getId(),i.getFir().getName()),i.getSec());
    }
    private ItemServiceDTO toItemServiceDTO(Item i){
        if(i==null)
            return null;
        return new ItemServiceDTO(i.getId(),i.getName());
    }
    private SupplierServiceDTO toSupplierServiceDTO(Supplier s){
        if(s==null)
            return null;
        return new SupplierServiceDTO(s.getPhone(),s.getContactName(),s.getId(),s.getShippingArea().getArea().toString());
    }
    private TruckServiceDTO toTruckServiceDTO(Truck t){
        if(t==null)
            return null;
        return new TruckServiceDTO(t.getId(),t.getLicense(),t.getMaxWeight(),t.getNetWeight(),t.getModel());
    }
    private TransportationServiceDTO toTransportationServiceDTO(Transportation t){
        List<Pair<Item,Integer>> i;
        HashMap<Supplier, List<Pair<Item, Integer>>> suppliers = t.getSuppliers();
        HashMap<SupplierServiceDTO, List<Pair<ItemServiceDTO, Integer>>> newSup =null;
        if(t.getSuppliers()!=null) {
            newSup= new HashMap<>();
            for (Map.Entry<Supplier, List<Pair<Item, Integer>>> entry : suppliers.entrySet()) {
                i = entry.getValue();
                List<Pair<ItemServiceDTO, Integer>> iDTO = new LinkedList<>();
                for (Pair<Item, Integer> it : i) {
                    iDTO.add(toItemPairServiceDTO(it));
                }
                newSup.put(toSupplierServiceDTO(entry.getKey()), iDTO);
            }
        }
        HashMap<Branch, List<Pair<Item, Integer>>> items = t.getDeliveryItems();
        HashMap<BranchServiceDTO, List<Pair<ItemServiceDTO, Integer>>> newItems=null;
        if(t.getDeliveryItems()!=null) {
            newItems=new HashMap<>();
            for (Map.Entry<Branch, List<Pair<Item, Integer>>> entry : items.entrySet()) {
                i = entry.getValue();
                List<Pair<ItemServiceDTO, Integer>> iDTO = new LinkedList<>();
                for (Pair<Item, Integer> it : i) {
                    iDTO.add(toItemPairServiceDTO(it));
                }
                newItems.put(toBranchServiceDTO(entry.getKey()), iDTO);
            }
        }
        return new TransportationServiceDTO(t.getId(),t.getDate(),t.getLeavingTime(),toDriverServiceDTO(t.getDriver()),toTruckServiceDTO(t.getTruck()),t.getWeight(),newItems,newSup,toArea( t.getShippingArea()));
    }


    private Area toArea(ShippingArea shippingArea) {
        if(shippingArea==null)
            return null;
        return shippingArea.getArea();
    }

    /**
     * Method for initializing new transportation to create.
     * @return : returns the new transportation object
     */
    public ResponseData<TransportationServiceDTO> createNewTransportation() {

        try {
            return new ResponseData<>(toTransportationServiceDTO(transportationService.newTransportation()));
        }
        catch (Exception e){
            return new ResponseData<>(e.getMessage());
        }

    }
    public ResponseData<TransportationServiceDTO> getTransportation(long id) {
        try {
            return new ResponseData<>(toTransportationServiceDTO(transportationService.getTransportationById(id)));
        }catch (Exception e) {
            return new ResponseData<>(e.getMessage());
        }
    }

    public void deleteTrans() {
        transportationService.deleteTransport();
    }
    public void addTruck(long id, int maxweight,String model, int netWeight, int license){dataControl.addTruck(id,maxweight,model,netWeight,license);}
    public void addItem(long id , String name){dataControl.addItem(id,name);}
    public void addSupplier(long sid,String street, String city,int number,int enter,String area,String contact,String phone){dataControl.addSupplier(sid,street,city,number,enter,area,contact,phone);}
    public void addBranch(long sid,String street, String city,int number,int enter,String area,String contact,String phone){dataControl.addBranch(sid,street,city,number,enter,area,contact,phone);}
    public void addSuppliersItemsTrans(long supid,long tranid, long itemid,int quantity){dataControl.addSuppliersItemsTrans(supid,tranid,itemid,quantity);}
    public void addBranchesItemsTrans(long branid,long tranid, long itemid,int quantity){dataControl.addBranchesItemsTrans(branid,tranid,itemid,quantity);}
    public void addSupplierItems(long id, long supp){dataControl.addSupplierItems(id,supp);}


    public void addTransportation(int i, String center, String s, String s1, int i1, int i2, int i3) {

        dataControl.addTransportation(i,center,s,s1,i1,i2,i3);
    }
}
