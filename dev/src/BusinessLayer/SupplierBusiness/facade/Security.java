package BusinessLayer.SupplierBusiness.facade;

import BusinessLayer.StockBusiness.Fcade.outObjects.NeededReport;
import BusinessLayer.SupplierBusiness.Order;
import BusinessLayer.SupplierBusiness.SupplierAgreement;
import BusinessLayer.SupplierBusiness.SupplierCard;


import java.rmi.server.ExportException;
import java.util.Hashtable;

public class Security {

    private static Security security = null;
    private Hashtable<Order, SupplierCard> suppliersOrder;
    //private final OrderService orderService;
    private final Hashtable<Integer , OrderService> orderServices;
    private final SupplierService supplierService;

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
        Tresponse<BusinessLayer.SupplierBusiness.facade.outObjects.Order> orderResponse = orderServices.get(branchId).getOrder(orderId);
        if(orderResponse.isError()) throw new ExportException("no such orderId");
        Order order = orderResponse.getOutObject();
        Tresponse<SupplierCard> supplierCardResponse = supplierService.showSupplier(Integer.parseInt(order.toStringSupplierBN())); // return the supplierBN.
        if(supplierCardResponse.isError()) throw new ExportException("there is no supplier that has this order");
        suppliersOrder.put(order , supplierCardResponse.getOutObject());
    }


    public Tresponse<NeededReport> getNeededItems() {
        return supplierService.getNeededItems();
    }

    public Order showTotalAmount(int orderId, int branchId) throws Exception {
        return suppliersOrder.get(orderId).showTotalAmount(orderId);
    }

    public BusinessLayer.SupplierBusiness.Item getItem(int supplierBN, int itemId) {
        return supplierService.getItem(supplierBN, itemId).getOutObject();
    }

    public Tresponse<BusinessLayer.SupplierBusiness.facade.outObjects.Order> showOrderOfSupplier(int supplierBN, int branchId, int orderId) {
        return supplierService.showOrderOfSupplier(supplierBN,orderId);
    }

    public SupplierAgreement getSupplierAgreement(int supplierBN, int orderId) {
        return suppliersOrder.get(orderId).showSupplierAgreement();
    }

    public SupplierCard getSupplier(int branchId, int OrderId) {
        return suppliersOrder.get(OrderId);
    }
}
