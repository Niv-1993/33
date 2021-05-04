package BusinessLayer.SupplierBusiness;

import DAL.DALObject;
import DAL.DalSuppliers.DalSupplierCard;
import DAL.DalSuppliers.DalSupplierController;
import DAL.Mapper;
import Utility.Tuple;
import Utility.Util;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.util.*;


public class SupplierCard {
    private List<Order> orders;
    private List<Item> items;
    private SupplierAgreement supplierAgreement;
    private regularOrder constantOrder;
    private DalSupplierCard dalSupplierCard;
    final static Logger log=Logger.getLogger(SupplierCard.class);

    public SupplierCard(int supplierBN , String supplierName ,int bankNumber , int branchNumber, int accountNumber , String payWay){
        //dalSupplierCard = Util.initDal(DalSupplierCard.class, 0 , supplierBN, supplierName, bankNumber, branchNumber,accountNumber, payWay);
        List<Tuple<Object,Class>> list=new ArrayList<>();
        list.add(new Tuple<>(supplierBN,Integer.class));
        list.add(new Tuple<>(supplierName,String.class));
        list.add(new Tuple<>(payWay,String.class));
        Mapper map=Mapper.getMap();
        map.setItem(DalSupplierCard.class,list);
        List<Integer> keyList=new ArrayList<>();
        DALObject check =map.getItem(DalSupplierCard.class ,keyList);
        if (DalSupplierCard.class==null || check==null ||(check.getClass()!=DalSupplierCard.class)){
            String s="the instance that return from Mapper is null";
            log.warn(s);
            throw new IllegalArgumentException(s);
        }
        else{
            log.info("create new Object");
            dalSupplierCard = (DalSupplierCard) check;
        }
        items = new LinkedList<>();
        orders = new LinkedList<>();
        constantOrder = null;
        dalSupplierCard = new DalSupplierCard();
    }

    public void removeSupplier() throws Exception {
        dalSupplierCard.removeSupplier();
        orders = new LinkedList<>();
        items = new LinkedList<>();
    }

    public void addConstantOrder(HashMap<Integer , Integer> amountsOfItem , int deliverDays) throws Exception {
    }

    public int getSupplierBN() {
        return dalSupplierCard.getSupplierBN();
    }

    public int getSupplierBankNumber() {
        return dalSupplierCard.getSupplierBankNumber();
    }

    public int getSupplierBranchNumber() {
        return dalSupplierCard.getSupplierBranchNumber();
    }

    public int getSupplierAccountNumber() {
        return dalSupplierCard.getSupplierAccountNumber();
    }

    public String getSupplierPayWay() {
        return dalSupplierCard.getSupplierPayWay();
    }

    public Dictionary<String , String> getContactPhone() {
        return dalSupplierCard.getContactPhone();
    }

    public Dictionary<String , String> getContactEmail() {
        return dalSupplierCard.getContactEmail();
    }

    public String getSupplierName() {
        return dalSupplierCard.getSupplierName();
    }

    public void updateSupplierPayWay(String payWay) throws Exception {
        if(!(payWay.equals("check") || payWay.equals("bank transfer") || payWay.equals("cash")))
            throw new Exception("pay way must be check/bank transfer/cash.");
        dalSupplierCard.updateSupplierPayWay(payWay);
    }

    public void updateSupplierBankAccount(int bankNumber , int branchNumber , int bankAccount) throws Exception {
        if(bankAccount < 0) throw new Exception("bank account must be a positive number");
        dalSupplierCard.updateSupplierBankAccount(bankNumber, branchNumber, bankAccount);
    }

    public void addContactPhone(String phone, String name) throws Exception {
        if(dalSupplierCard.getContactPhone().get(phone) != null)
            throw new Exception("contact phone all ready exist, you may want to use: update contact phone");
        dalSupplierCard.addContactPhone(phone, name);
    }

    public void addContactEmail(String email, String name) throws Exception {
        if(dalSupplierCard.getContactEmail().get(email) != null)
            throw new Exception("contact email all ready exist, you may want to use: update contact email");
        dalSupplierCard.addContactEmail(email, name);
    }

    public void removeContactPhone(String phone) throws Exception {
        if(dalSupplierCard.getContactPhone().get(phone) == null)
            throw new Exception("contact phone does not exist");
        dalSupplierCard.removeContactPhone(phone);
    }

    public void removeContactEmail(String email) throws Exception {
        if(dalSupplierCard.getContactEmail().get(email) == null)
            throw new Exception("contact email does not exist");
        dalSupplierCard.removeContactEmail(email);
    }



    public void updateContactPhone(String phone , String name) throws Exception {
        Enumeration<String> e1 = dalSupplierCard.getContactPhone().elements();
        while (e1.hasMoreElements()) {
            String element = e1.nextElement();
            if(name.equals(element)){
                Enumeration<String> e2 = dalSupplierCard.getContactPhone().keys();
                while (e2.hasMoreElements()) {
                    String oldPhone = e2.nextElement();
                    if (dalSupplierCard.getContactPhone().get(oldPhone).equals(name)) {
                        removeContactPhone(oldPhone);
                        break;
                    }
                }
            }
        }
        addContactPhone(phone, name);
    }

    public void updateContactEmail(String email , String name) throws Exception {
        Enumeration<String> e1 = dalSupplierCard.getContactEmail().elements();
        while (e1.hasMoreElements()) {
            String element = e1.nextElement();
            if(name.equals(element)){
                Enumeration<String> e2 = dalSupplierCard.getContactEmail().keys();
                while (e2.hasMoreElements()) {
                    String oldEmail = e2.nextElement();
                    if (dalSupplierCard.getContactEmail().get(oldEmail).equals(name)) {
                        removeContactEmail(oldEmail);
                        break;
                    }
                }
            }
        }
        addContactEmail(email, name);
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
        Item newItem = new BusinessLayer.SupplierBusiness.Item(ItemId , name , price, typeID, expirationDate);
        items.add(newItem);
        return newItem;
    }////////////need to addsupplierBN to signatures

    public List<Item> showAllItemsOfOrder(int orderId) throws Exception {
        for(Order order : orders){
            if(order.getOrderId() == orderId) return order.showAllItemsOfOrder();
        }
        throw new Exception("orderId does not exist.");
    }

    public void removeItemFromSupplier(int itemId , boolean single) throws Exception { /////////I think it need to be modified
        List<Item> copyItem = items;
        boolean found = false;
        for(Item item : copyItem){
            if(item.getItemId() == itemId){
                items.remove(item);
                found = true;
                break;
            }
        }
        if(single && !found) throw new Exception("itemId does not exist for this supplier");
    }

    public void removeItemFromRegularOrder(int orderId, int itemId) throws Exception {
        for(Order order : orders){
            if(order.getOrderId() == orderId){
                try {
                    regularOrder regularOrder = (BusinessLayer.SupplierBusiness.regularOrder) order;
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
                    regularOrder regularOrder = (BusinessLayer.SupplierBusiness.regularOrder) order;
                    regularOrder.removeAmountItemFromRegularOrder(itemId , amount);
                    break;
                }catch (Exception e){
                    throw new Exception(e.getMessage());
                }
            }
        }
    }

    public Order addRegularOrder(int orderId , int branchId){
        Order order = new regularOrder(orderId , branchId);
        orders.add(order);
        return order;
    }

    public void addConstantOrder(int orderID, int branchID , Hashtable<Integer , Integer> items) throws Exception {
        // check if it's veiled branchId.
        if(constantOrder == null) constantOrder = new regularOrder(orderID , branchID);
        for(Item item : this.items){
            if(items.keySet().contains(item.getItemId())) {
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
                order.removeOrder();
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
        return orders;
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

    public void updateConstantTime(boolean constantTime) throws Exception { supplierAgreement.updateConstantTime(constantTime); }

    public void updateShipToUs(boolean shipToUs) throws Exception {
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
