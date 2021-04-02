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


    public int getSupplierBN() {
        return supplierBN;
    }

    public int getSupplierAccountNumber() {
        return accountNumber;
    }

    public String getSupplierPayWay() {
        return payWay;
    }

    public Dictionary getcontactPhone() {
        return contactPhone;
    }

    public Dictionary getcontactEmail() {
        return contactEmail;
    }

    public String getSupplierName() {
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


    public List<Item> showAllItemsOfSupplier() {
        return items;
    }

    public Item addItem(String category, int ItemId , double price) throws Exception {
        if(price < 0) throw new Exception("price must be a positive number!");
        Item newItem = new BussniesLayer.Item(supplierBN,category, ItemId , price);
        items.add(newItem);
        return newItem;
    }

    public void removeItemFromSupplier(int itemId) {
        for (Item i : items) {
            if (i.getItemId() == itemId) {
                items.remove(i);
            }
        }
    }

    public void addOrder(int orderID) {
        Order order = new Order(orderID, supplierBN,null);
        orders.add(order);
    }

    public void addItemToOrder(int orderId, int itemId) throws Exception {
        Item toAdd = null;
        for (Item i : items) {
            if (i.getItemId() == itemId) {
                toAdd = i;
            }
        }
        if(toAdd == null) throw new Exception("itemId does not exist");
        boolean found = false;
        for (Order o : orders) {
            if (o.getOrderId() == orderId) {
               o.addItemToOrder(toAdd);
               found = true;
            }
        }
        if(!found) throw new Exception("orderId does not exist");
    }

    public Order showOrderOfSupplier(int orderId) throws Exception {
        for (Order o : orders) {
            if (o.getOrderId() == orderId)
                return o;
        }
        throw new Exception("orderId does not exist.");
    }

    public List<Order> showAllOrdersOfSupplier() {
        return orders;
    }

    public double showTotalAmount(int orderId) throws Exception {
        try {
            for (Order o : orders) {
                if (o.getOrderId() == orderId)
                    return o.showTotalAmount();
            }
        }catch (Exception e) {
            throw new Exception(e);
        }
        throw new Exception("orderId does not exist.");
    }

    public Date showDeliverTime(int orderId) throws Exception {
        for (Order o : orders) {
            if (o.getOrderId() == orderId)
                try {
                    return o.showDeliverTime();
                } catch (Exception e){
                    throw new Exception(e);
                }
        }
        throw new Exception("orderId does not exist.");
    }

    public void updateDeliverTime(int orderId, Date deliverTime) throws Exception {
        boolean hasUpdated = false;
        for (Order o : orders) {
            if (o.getOrderId() == orderId) {
                o.updateDeliverTime(deliverTime);
                hasUpdated = true;
            }
        }
        if(!hasUpdated) throw new Exception("orderId does not exist.");
    }

    public void addQuantityDocument(int itemId, int minimalAmount, int discount) throws Exception {
        boolean hasAdded = false;
        for (Item i : items) {
            if (i.getItemId() == itemId) {
                try {
                    i.addQuantityDocument(supplierBN, itemId, minimalAmount, discount);
                } catch (Exception e){
                    throw new Exception(e);
                }
                hasAdded = true;
            }
        }
        if(!hasAdded) throw new Exception("itemId does not exist.");
    }

    public void removeQuantityDocument(int itemId) throws Exception {
        boolean hasRemoved = false;
        for (Item i : items) {
            if (i.getItemId() == itemId) {
                try {
                    i.removeQuantityDocument();
                }catch (Exception e){
                    throw new Exception(e);
                }
                hasRemoved = true;
            }
        }
        if(!hasRemoved) throw new Exception("itemId does not exist");
    }

    public QuantityDocument showQuantityDocument(int itemId) throws Exception {
        boolean hasFound = false;
        for (Item i : items) {
            if (i.getItemId() == itemId) {
                try {
                    return i.showQuantityDocument();
                } catch (Exception e){
                    throw new Exception(e);
                }
            }

        }
        throw new Exception("itemId does not exist.");
    }

    public void updateMinimalAmountOfQD(int itemId, int minimalAmount) throws Exception {
        boolean hasFound = false;
        for (Item i : items) {
            if (i.getItemId() == itemId) {
                try {
                    i.updateMinimalAmountOfQD(minimalAmount);
                }catch (Exception e){
                    throw new Exception(e);
                }
                hasFound = true;
            }
        }
        if(!hasFound) throw new Exception("itemId does not found");
    }

    public void updateDiscountOfQD(int itemId, int discount) throws Exception {
        boolean hasFound = false;
        for (Item i : items) {
            if (i.getItemId() == itemId) {
                try{
                    i.updateDiscountOfQD(discount);
                } catch (Exception e){
                    throw new Exception(e);
                }
                hasFound = true;
            }
        }
        if(!hasFound) throw new Exception("itemId does not exist");
    }

    public void addSupplierAgreement(int minimalAmount, int discount, boolean constantTime, boolean shipToUs) throws Exception {
        if(minimalAmount < 0 ) throw new Exception("minimal amount must be a positive number");
        if(discount < 0 || discount > 100) throw new Exception("minimal amount must be a positive number between 0 to 100");
        supplierAgreement = new SupplierAgreement(supplierBN ,minimalAmount, discount, constantTime, shipToUs);
    }

    public SupplierAgreement showSupplierAgreement() {
        return supplierAgreement;
    }

    public void updateMinimalAmountOfSA(int minimalAmount) throws Exception {
        try {
            supplierAgreement.updateMinimalAmountOfSA(minimalAmount);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    public void updateDiscountOfSA(int discount) throws Exception {
        try {
            supplierAgreement.updateDiscountOfSA(discount);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    public void updateConstantTime(boolean constantTime) {
        supplierAgreement.updateConstantTime(constantTime);
    }

    public void updateShipToUs(boolean shipToUs) {
        supplierAgreement.updateShipToUs(shipToUs);
    }

    public void updatePrice(int itemId , double price) throws Exception {
        boolean hasFound = false;
        for(Item item : items){
            if(item.getItemId() == itemId){
                try {
                    item.updatePrice(price);
                }catch (Exception e){
                    throw new Exception(e);
                }
                hasFound = true;
            }
        }
        if(!hasFound) throw new Exception("itemId does not found");
    }
}
