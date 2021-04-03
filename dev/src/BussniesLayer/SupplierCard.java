package BussniesLayer;

import java.util.*;


public class SupplierCard {
    private final int supplierBN;
    private final String supplierName;
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
        orders = new LinkedList<>();
        items = new LinkedList<>();
        supplierAgreement = null;
        contactPhone = new Hashtable<>();
        contactEmail = new Hashtable<>();
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

    public Dictionary<String , String> getContactPhone() {
        return contactPhone;
    }

    public Dictionary<String , String> getContactEmail() {
        return contactEmail;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void updateSupplierPayWay(String payWay) throws Exception {
        if(!(payWay.equals("check") || payWay.equals("bank transfer") || payWay.equals("cash")))
            throw new Exception("pay way must be check/bank transfer/cash.");
        this.payWay = payWay;
    }

    public void updateSupplierBankAccount(int bankAccount) throws Exception {
        if(bankAccount < 0) throw new Exception("bank account must be a positive number");
        this.accountNumber = bankAccount;
    }

    public void addContactPhone(String phone, String name) throws Exception {
        if(contactPhone.get(phone) != null)
            throw new Exception("contact phone all ready exist, you may want to use: update contact phone");
        contactPhone.put(phone, name);
    }

    public void addContactEmail(String email, String name) throws Exception {
        if(contactPhone.get(email) != null)
            throw new Exception("contact email all ready exist, you may want to use: update contact email");
        contactEmail.put(email, name);
    }

    public void removeContactPhone(String phone){
        contactPhone.remove(phone);
    }

    public void removeContactEmail(String email){
        contactPhone.remove(email);
    }

    public void updateContactPhone(String phone) throws Exception {
        String name = contactPhone.get(phone);
        if(name == null) throw new Exception("phone does npt exist , you may want to use: add contact phone");
        contactPhone.put(phone ,name);
    }

    public void updateContactEmail(String email) throws Exception {
        String name = contactPhone.get(email);
        if(name == null) throw new Exception("phone does npt exist , you may want to use: add contact phone");
        contactEmail.put(email ,name);
    }


    public List<Item> showAllItemsOfSupplier() {
        return items;
    }

    public Item addItem(String category, int ItemId , double price) throws Exception {
        if(price < 0) throw new Exception("price must be a positive number!");
        Item newItem = new BussniesLayer.Item(category, ItemId , price);
        items.add(newItem);
        return newItem;
    }

    public void removeItemFromSupplier(int itemId) {
        items.removeIf(item -> item.getItemId() == itemId);
    }

    public Order addOrder(int orderID) {
        Order order = new Order(orderID ,null);
        orders.add(order);
        return order;
    }

    public void addItemToOrder(int orderId, int itemId) throws Exception {
        Item toAdd = null;
        for (Item i : items) {
            if (i.getItemId() == itemId) {
                toAdd = i;
                break;
            }
        }
        if(toAdd == null) throw new Exception("the supplier does not have this item");
        boolean hasFound = false;
        for (Order o : orders) {
            if (o.getOrderId() == orderId) {
               o.addItemToOrder(toAdd);
                hasFound = true;
                break;
            }
        }
        if(!hasFound) throw new Exception("orderId does not exist");
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

    public Order showTotalAmount(int orderId) throws Exception {
        for(Order o : orders) {
            if (o.getOrderId() == orderId) {
                try {
                    return o.showTotalAmount();
                } catch (Exception e) {
                    throw new Exception(e);
                }
            }
        }
        throw new Exception("orderId does not exist.");
    }

    public Order showDeliverTime(int orderId) throws Exception {
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
        boolean hasFound = false;
        for (Order o : orders) {
            if (o.getOrderId() == orderId) {
                o.updateDeliverTime(deliverTime);
                hasFound = true;
            }
            if(hasFound) break;
        }
        if(!hasFound) throw new Exception("orderId does not exist.");
    }

    public void addQuantityDocument(int itemId, int minimalAmount, int discount) throws Exception {
        boolean hasFound = false;
        for (Item i : items) {
            if (i.getItemId() == itemId) {
                try {
                    i.addQuantityDocument(minimalAmount, discount);
                    hasFound = true;
                } catch (Exception e){
                    throw new Exception(e);
                }
           }
            if(hasFound) break;
        }
        if(!hasFound) throw new Exception("itemId does not exist.");
    }

    public void removeQuantityDocument(int itemId) throws Exception {
        boolean hasFound = false;
        for (Item i : items) {
            if (i.getItemId() == itemId) {
                try {
                    i.removeQuantityDocument();
                    hasFound = true;
                }catch (Exception e){
                    throw new Exception(e);
                }
            }
            if(hasFound) break;
        }
        if(!hasFound) throw new Exception("itemId does not exist");
    }

    public QuantityDocument showQuantityDocument(int itemId) throws Exception {
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
                    hasFound = true;
                }catch (Exception e){
                    throw new Exception(e);
                }
            }
            if(hasFound) break;
        }
        if(!hasFound) throw new Exception("itemId does not found");
    }

    public void updateDiscountOfQD(int itemId, int discount) throws Exception {
        boolean hasFound = false;
        for (Item item : items) {
            if (item.getItemId() == itemId) {
                try{
                    item.updateDiscountOfQD(discount);
                    hasFound = true;
                } catch (Exception e){
                    throw new Exception(e);
                }
            }
            if(hasFound) break;
        }
        if(!hasFound) throw new Exception("itemId does not exist");
    }

    public void addSupplierAgreement(int minimalAmount, int discount, boolean constantTime, boolean shipToUs) throws Exception {
        if(minimalAmount < 0 ) throw new Exception("minimal amount must be a positive number");
        if(discount < 0 || discount > 100) throw new Exception("minimal amount must be a positive number between 0 to 100");
        supplierAgreement = new SupplierAgreement(minimalAmount, discount, constantTime, shipToUs);
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

    public void updateConstantTime(boolean constantTime) { supplierAgreement.updateConstantTime(constantTime); }

    public void updateShipToUs(boolean shipToUs) {
        supplierAgreement.updateShipToUs(shipToUs);
    }

    public void updatePrice(int itemId , double price) throws Exception {
        boolean hasFound = false;
        for(Item item : items){
            if(item.getItemId() == itemId){
                try {
                    item.updatePrice(price);
                    hasFound = true;
                }catch (Exception e){
                    throw new Exception(e);
                }
            }
            if(hasFound) break;
        }
        if(!hasFound) throw new Exception("itemId does not found");
    }
}
