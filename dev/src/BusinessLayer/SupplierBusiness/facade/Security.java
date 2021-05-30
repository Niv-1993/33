package BusinessLayer.SupplierBusiness.facade;

import BusinessLayer.SupplierBusiness.facade.outObjects.*;

import java.rmi.server.ExportException;
import java.util.Hashtable;

public class Security {

    private Hashtable<Order , SupplierCard> suppliersOrder;
    private final OrderService orderService;
    private final SupplierService supplierService;

    public Security(int branchId){
        suppliersOrder = new Hashtable<>();
        orderService = new OrderService(branchId);
        supplierService = new SupplierService(branchId);
    }

    public void addOrder(int orderId) throws ExportException {
        Tresponse<Order> orderResponse = orderService.getOrder(orderId);
        if(orderResponse.isError()) throw new ExportException("no such orderId");
        Order order = orderResponse.getOutObject();
        Tresponse<SupplierCard> supplierCardResponse = supplierService.showSupplier(Integer.parseInt(order.toStringSupplierBN())); // return the supplierBN.
        if(supplierCardResponse.isError()) throw new ExportException("there is no supplier that has this order");
        suppliersOrder.put(order , supplierCardResponse.getOutObject());
    }


}
