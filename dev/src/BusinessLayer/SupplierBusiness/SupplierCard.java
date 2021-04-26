package BusinessLayer.SupplierBusiness;

import DalAccessLayer.DalSupplierCard;

import java.time.LocalDate;
import java.util.*;


public class SupplierCard {
    private final int supplierBN;
    private final String supplierName;
    private int bankNumber;
    private int branchNumber;
    private int accountNumber;
    private String payWay;
    private List<Order> orders;
    private List<Item> items;
    private SupplierAgreement supplierAgreement;
    private Dictionary<String , String> contactPhone;
    private Dictionary<String , String> contactEmail;
    private regularOrder constantOrder;
    private DalSupplierCard dalSupplierCard;

    public SupplierCard(int supplierBN , String supplierName ,int bankNumber , int branchNumber, int accountNumber , String payWay){
        this.supplierBN = supplierBN;
        this.supplierName = supplierName;
        this.bankNumber = bankNumber;
        this.branchNumber = branchNumber;
        this.accountNumber = accountNumber;
        this.payWay = payWay;
        items = new LinkedList<>();
        orders = new LinkedList<>();
        supplierAgreement = null;
        contactPhone = new Hashtable<>();
        contactEmail = new Hashtable<>();
        constantOrder = null;
    }

    public void removeSupplier(){
        orders = new LinkedList<>();
        items = new LinkedList<>();
        contactPhone = new Hashtable<>();
        contactEmail = new Hashtable<>();
    }

    public int getSupplierBN() {
        return supplierBN;
    }

    public int getSupplierBankNumber() {
        return bankNumber;
    }

    public int getSupplierBranchNumber() {
        return branchNumber;
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

    public void updateSupplierBankAccount(int bankNumber , int branchNumber , int bankAccount) throws Exception {
        if(bankAccount < 0) throw new Exception("bank account must be a positive number");
        this.bankNumber = bankNumber;
        this.branchNumber = branchNumber;
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

    public void removeContactPhone(String phone) throws Exception {
        if(contactPhone.get(phone) == null)
            throw new Exception("contact phone does not exist");
        contactPhone.remove(phone);
    }

    public void removeContactEmail(String email) throws Exception {
        if(contactPhone.get(email) == null)
            throw new Exception("contact email does not exist");
        contactPhone.remove(email);
    }

    public void updateContactPhone(String phone , String name){
        Enumeration<String> e1 = contactPhone.elements();
        while (e1.hasMoreElements()) {
            String element = e1.nextElement();
            if(name.equals(element)){
                Enumeration<String> e2 = contactPhone.keys();
                while (e2.hasMoreElements()) {
                    String oldPhone = e2.nextElement();
                    if (contactPhone.get(oldPhone).equals(name)) {
                        contactPhone.remove(oldPhone);
                        break;
                    }
                }
            }
        }
        contactPhone.put(phone ,name);
    }

    public void updateContactEmail(String email , String name) {
        Enumeration<String> e1 = contactEmail.elements();
        while (e1.hasMoreElements()) {
            String element = e1.nextElement();
            if(name.equals(element)){
                Enumeration<String> e2 = contactEmail.keys();
                while (e2.hasMoreElements()) {
                    String oldEmail = e2.nextElement();
                    if (contactEmail.get(oldEmail).equals(name)) {
                        contactEmail.remove(oldEmail);
                        break;
                    }
                }
            }
        }
        contactEmail.put(email ,name);
    }


    public List<Item> showAllItemsOfSupplier() {
        return items;
    }

    public Item showItemOfSupplier(int itemId) throws Exception {
        for(Item item : items){
            if(item.getItemId() == itemId) return item;
        }
        throw new Exception("itemId does net exist for this supplier");
    }

    public Item addItem(int ItemId , String name , double price, int typeID, LocalDate expirationDate) throws Exception {
        if(price < 0) throw new Exception("price must be a positive number!");
        Item newItem = new Item(ItemId , name , price, typeID, expirationDate);
        items.add(newItem);
        return newItem;
    }

    public List<Item> showAllItemsOfOrder(int orderId) throws Exception {
        for(Order order : orders){
            if(order.getOrderId() == orderId) return order.showAllItemsOfOrder();
        }
        throw new Exception("orderId does not exist.");
    }

    public void removeItemFromSupplier(int itemId , boolean single) throws Exception {
        List<Item> copyItem = items;
        boolean found = false;
        Item i;
        for(Item item : copyItem){
            if(item.getItemId() == itemId){
                items.remove(item);
                found = true;
                i = item;
                break;
            }
        }
        if(single && !found) throw new Exception("itemId does not exist for this supplier");
        dalSupplierCard.Delete("items" , "i.getId = id");
    }

    public void removeItemFromRegularOrder(int orderId, int itemId) throws Exception {
        for(Order order : orders){
            if(order.getOrderId() == orderId){
                try {
                    regularOrder regularOrder = (regularOrder) order;
                    regularOrder.removeItemFromRegularOrder(itemId);
                    break;
                }catch (Exception e){
                    throw new Exception(e.getMessage());
                }
            }
        }
    }

    public void removeAmountItemFromRegularOrder(int orderId, int itemId, int amount) throws Exception {
        for(Order order : orders){
            if(order.getOrderId() == orderId){
                try {
                    regularOrder regularOrder = (regularOrder) order;
                    regularOrder.removeAmountItemFromRegularOrder(itemId , amount);
                    break;
                }catch (Exception e){
                    throw new Exception(e.getMessage());
                }
            }
        }
    }

    public Order addRegularOrder(int orderId , int branchId){
        Order order;
        if(constantOrder == null) order = new regularOrder(orderId , branchId);
        else order = constantOrder;
        orders.add(order);
        return order;
    }

    public void addConstantOrder(int orderID, int branchID , Hashtable<Integer , Integer> items) throws Exception {
        // check if it's veiled branchId.
        if(constantOrder == null) constantOrder = new regularOrder(orderID , branchID);
        for(Item item : this.items){
            if(items.containsKey(item.getItemId())) {
                try {
                    constantOrder.addItemToOrder(item , items.get(item.getItemId()));
                }catch (Exception e) {
                    throw new Exception(e);
                }
            }
        }
    }

    public Order addNeededOrder(int orderID, int branchID, Item item, int amount) {
        if (item == null || isItemExist(item.getItemId()) == null) return null;
        double totalAmount = calculateTotalAmount(item , amount);
        neededOrder order = new neededOrder(orderID ,LocalDate.now().plusDays(1), branchID, item, amount , totalAmount);
        orders.add(order);
        return order;
    }

    private double calculateTotalAmount(Item item , int amount){
        double totalAmount = 0.0;
        QuantityDocument qd = item.getQuantityDocument();
        if (qd != null) {
            totalAmount = totalAmount + item.getPrice() * amount;
            if (qd.getMinimalAmount() <= amount) {
                double discount = qd.getDiscount() / 100.0;
                totalAmount = totalAmount - item.getPrice() * discount * amount;
            }
        }
        return totalAmount;
    }

    private Item isItemExist(int itemId){
        for (Item i : items) {
            if (i.getItemId() == itemId) {
                return i;
            }
        }
        return null;
    }

    public void addItemToOrder(int orderId, int itemId , int amount) throws Exception {
        Item toAdd = isItemExist(itemId);
       if(toAdd == null) throw new Exception("the supplier does not have this item");
        boolean hasFound = false;
        for (Order o : orders) {
            if (o.getOrderId() == orderId) {
                regularOrder temp = (regularOrder) o;
                temp.addItemToOrder(toAdd , amount);
                hasFound = true;
                break;
            }
        }
        if(!hasFound) throw new Exception("orderId does not exist");
    }

    public void removeAllOrders(int orderId){
        List<Order> copyOrder = orders;
        for(Order order : copyOrder){
            if(order.getOrderId() == orderId){
                orders.remove(order);
                break;
            }
        }
    }

    public Order showOrderOfSupplier(int orderId) throws Exception {
        for (Order o : orders) {
            if (o.getOrderId() == orderId)
                return o;
        }
        throw new Exception("orderId does not exist.");
    }

    public List<Order> showAllOrdersOfSupplier() {
       return orders.subList(1 , orders.size() -1);
    }

    public Order showTotalAmount(int orderId) throws Exception {
        double totalAmount;
        boolean found = false;
        Order order = null;
        for(Order o : orders) {
            if (o.getOrderId() == orderId) {
                found = true;
                try {
                    totalAmount = o.getTotalAmount();
                } catch (Exception e) {
                    throw new Exception(e);
                }
                order = o;
                if(supplierAgreement.getMinimalAmount() <= order.getTotalAmount()){
                    double discount = 1 - supplierAgreement.getDiscount()/100.0;
                    totalAmount = totalAmount*discount;
                    regularOrder temp = (regularOrder) order;
                    temp.updateTotalAmount(totalAmount);
                }
            }
        }
        if(found) return order;
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

    public void updateDeliverTime(int orderId, LocalDate deliverTime) throws Exception {
        boolean hasFound = false;
        for (Order o : orders) {
            if (o.getOrderId() == orderId) {
                regularOrder temp = (regularOrder) o;
                temp.updateDeliverTime(deliverTime);
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
        if(!hasFound) throw new Exception("itemId does not exist for this supplier");
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

    public SupplierAgreement showSupplierAgreement() throws Exception {
        if(supplierAgreement == null) throw new Exception("there is no supplier Agreement");
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

    public List<Item> getSupplierItems() {
        return items;
    }
}
