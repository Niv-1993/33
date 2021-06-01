package BusinessLayer.SupplierBusiness.facade;

import BusinessLayer.StockBusiness.Fcade.outObjects.NeededReport;
import BusinessLayer.SupplierBusiness.Order;
import BusinessLayer.SupplierBusiness.SupplierAgreement;
import BusinessLayer.SupplierBusiness.SupplierCard;
import DAL.DALObject;
import DAL.DalSuppliers.DalOrder;
import DAL.DalSuppliers.DalSupplierCard;
import DAL.Mapper;
import org.apache.log4j.Logger;


import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Security {

    private static Security security = null;
    private Hashtable<Order, SupplierCard> suppliersOrder;
    //private final OrderService orderService;
    private final Hashtable<Integer , OrderService> orderServices;
    private final SupplierService supplierService;
    final static Logger log=Logger.getLogger(Security.class);

    public Security(){
        suppliersOrder = new Hashtable<>();
        //orderService = new OrderService(branchId);
        supplierService = new SupplierService();
        orderServices = new Hashtable<>();
        loadAllServices();
    }

    private void loadAllServices() {
        supplierService.LoadData();
        for (int i = 1 ; i < 10; i++) {
            OrderService temp = new OrderService(i);
            temp.LoadData();
            orderServices.put(i, temp);
        }
    }

    public static Security getInstance() {
        if (security == null) {
            security = new Security();
        }
        return security;
    }

    public void addOrder(int branchId, int orderId) throws ExportException {
        //Tresponse<BusinessLayer.SupplierBusiness.facade.outObjects.Order> orderResponse = orderServices.get(branchId).getOrder(orderId);
        Order order = getOrder(branchId, orderId);
        if(order == null) throw new ExportException("no such orderId");
        int supplierBN = order.getSupplierBN();
        //Tresponse<SupplierCard> supplierCardResponse = supplierService.showSupplier(retOrder.getSupplierBN()); // return the supplierBN.
        SupplierCard supplierCard = getSupplier(supplierBN);
        if(supplierCard == null) throw new ExportException("there is no supplier that has this order");
        suppliersOrder.put(order , supplierCard);
    }


    public Tresponse<NeededReport> getNeededItems() {
        return supplierService.getNeededItems();
    }

    public Order showTotalAmount(int orderId, int branchId) throws Exception {
        try {
            return suppliersOrder.get(orderId).showTotalAmount(orderId);
        }catch (Exception e){
            throw new Exception("there is no such orderId");
        }
    }

    public BusinessLayer.SupplierBusiness.Item getItem(int supplierBN, int itemId) {
        return supplierService.getItem(supplierBN, itemId).getOutObject();
    }

    public Tresponse<BusinessLayer.SupplierBusiness.facade.outObjects.Order> showOrderOfSupplier(int supplierBN, int branchId, int orderId) {
        /*int BN;
        try {
            BN = suppliersOrder.get(orderServices.get(orderId)).getSupplierBN();
        }catch (Exception e){
            throw new Exception("there is no supplier that supplies that orderId");
        }*/
        return supplierService.showOrderOfSupplier(supplierBN,orderId);
    }

    public SupplierAgreement getSupplierAgreement(int supplierBN, int orderId) throws Exception {
        try {
            return suppliersOrder.get(orderId).showSupplierAgreement();
        }catch (Exception e){
            throw new Exception("there is no such orderId");
        }
    }

    public SupplierCard getSupplier(int branchId, int OrderId) throws Exception {
        try {
            return suppliersOrder.get(OrderId);
        }catch (Exception e){
            throw new Exception("there is no such orderId");
        }
    }

    private Order getOrder(int branchId, int orderId) {
        DalOrder dalOrder;
        BusinessLayer.SupplierBusiness.Order retOrder;
        Mapper map=Mapper.getMap();
        List<Integer> keyList=new ArrayList<>();
        keyList.add(branchId);
        keyList.add(orderId);
        DALObject check =map.getItem(DalOrder.class ,keyList);
        if (check==null ||(check.getClass()!=DalOrder.class)){
            String s="the instance that return from Mapper is null";
            log.warn(s);
            throw new IllegalArgumentException(s);
        }
        else{
            log.info("create new Object");
            dalOrder = (DalOrder) check;
            retOrder = new Order(dalOrder);
        }
        return retOrder;
    }

    private SupplierCard getSupplier(int supplierBN) {
        DalSupplierCard dalSupplierCard;
        BusinessLayer.SupplierBusiness.SupplierCard supplierCard;
        Mapper map=Mapper.getMap();
        List<Integer> keyList=new ArrayList<>();
        keyList.add(supplierBN);
        DALObject check =map.getItem(DalSupplierCard.class ,keyList);
        if (check==null ||(check.getClass()!=DalSupplierCard.class)){
            String s="the instance that return from Mapper is null";
            log.warn(s);
            throw new IllegalArgumentException(s);
        }
        else{
            log.info("create new Object");
            dalSupplierCard = (DalSupplierCard) check;
            supplierCard = new SupplierCard(dalSupplierCard);
        }
        return supplierCard;
    }
}
