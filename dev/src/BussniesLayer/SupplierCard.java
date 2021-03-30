package BussniesLayer;

import java.util.*;


public class SupplierCard {
    private int supplierBN;
    private String supplierName;
    private int accountNumber;
    private String payWay;
    private List<Order> orders;
    private List<Item> items;
    private SupplierAgreement supplierAgreement;
    private Dictionary<String , String> contactPhone;
    private Dictionary<String , String> contactEmail;

    public SupplierCard(int supplierBN , String supplierName , int accountNumber , String payWay){
        this.supplierBN = supplierBN;
        this.supplierName = supplierName;
        this.accountNumber = accountNumber;
        this.payWay = payWay;
        orders = new LinkedList<Order>();
        items = new LinkedList<Item>();
        supplierAgreement = null;
        contactPhone = new Hashtable<String, String>();
        contactEmail = new Hashtable<String, String>();
    }


    public String getSupplierBN() {
        return supplierName;
    }

    public void updateSupplierPayWay(String payWay) {
        this.payWay = payWay;
    }

    public void updateSupplierBankAccount(int bankAccount) {
        this.accountNumber = accountNumber;
    }

    public void addContactPhone(String phone, String name) {
        contactPhone.put(phone, name);
    }

    public void addContactEmail(String email, String name) {
        contactEmail.put(email, name);
    }

    public void removeContactPhone(String phone) {
        contactPhone.remove(phone);
    }

    public void removeContactEmail(String email) {
        contactPhone.remove(email);
    }

    public void updateContactPhone(String phone) {
        String name = contactPhone.get(phone);
        removeContactPhone(phone);
        addContactPhone(phone , name);
    }

    public void updateContactEmail(String email) {
        String name = contactEmail.get(email);
        removeContactPhone(email);
        addContactPhone(email , name);
    }


    public void showAllItemsOfSupplier() {
    }

    public void addItem(String category, int ItemId) {
        Item newItem = new BussniesLayer.Item(supplierBN,category, ItemId);
        items.add(newItem);
    }

    public void removeItemFromSupplier(int itemId) {
        for (Item i : items) {
            if (i.getItemId() == itemId) {
                items.remove(i);
            }
        }
    }

    public void addOrder(int orderID) {
        Order order = new Order(orderID, supplierBN, 0, null);
        orders.add(order);
    }

    public void addItemToOrder(int orderId, int itemId) {
        Item toAdd = null;
        for (Item i : items) {
            if (i.getItemId() == itemId) {
                toAdd = i;
            }
        }
        for (Order o : orders) {
            if (o.getOrderId() == orderId) {
               o.addItem(toAdd);
            }
        }
    }

    public void showOrderOfSupplier(int orderId) {
    }

    public void showAllOrdersOfSupplier() {
    }

    public void showTotalAmount(int orderId) {
    }

    public void showDeliverTime(int orderId) {
    }

    public void updateDeliverTime(int orderId, Date deliverTime) {
        for (Order o : orders) {
            if (o.getOrderId() == orderId) {
                o.updateDeliverTime(deliverTime);
            }
        }
    }

    public void addQuantityDocument(int itemId, int minimalAmount, int discount) {
        for (Item i : items) {
            if (i.getItemId() == itemId) {
                i.addQuantityDocument(supplierBN, itemId, minimalAmount, discount);
            }
        }
    }

    public void removeQuantityDocument(int itemId) {
        for (Item i : items) {
            if (i.getItemId() == itemId) {
                i.removeQuantityDocument();
            }
        }
    }

    public void showQuantityDocument(int itemId) {
    }

    public void updateMinimalAmountOfQD(int itemId, int minimalAmount) {
        for (Item i : items) {
            if (i.getItemId() == itemId) {
                i.updateMinimalAmountOfQD(minimalAmount);
            }
        }
    }

    public void updateDiscountOfQD(int itemId, int discount) {
        for (Item i : items) {
            if (i.getItemId() == itemId) {
                i.updateDiscountOfQD(discount);
            }
        }
    }

    public void addSupplierAgreement(int minimalAmount, int discount, boolean constantTime, boolean shipToUs) {
        SupplierAgreement SA = new SupplierAgreement(supplierBN ,minimalAmount, discount, constantTime, shipToUs);
        supplierAgreement = SA;
    }

    public void showSupplierAgreement() {
    }

    public void updateMinimalAmountOfSA(int minimalAmount) {
        supplierAgreement.updateMinimalAmountOfSA(minimalAmount);
    }

    public void updateDiscountOfSA(int discount) {
        supplierAgreement.updateDiscountOfSA(discount);
    }

    public void updateConstantTime(boolean constantTime) {
        supplierAgreement.updateConstantTime(constantTime);
    }

    public void updateShipToUs(boolean shipToUs) {
        supplierAgreement.updateShipToUs(shipToUs);
    }
}
