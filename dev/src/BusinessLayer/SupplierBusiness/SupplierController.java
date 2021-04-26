package BusinessLayer.SupplierBusiness;


import java.time.LocalDate;
import java.util.*;

public class SupplierController{
    private Dictionary<Integer , SupplierCard> suppliers;
    private int numOfItems;
    private int numOfOrders;

    public SupplierController(){
        suppliers = new Hashtable<>();
        numOfItems = 0;
        numOfOrders = 1;
    }

    public SupplierCard showSupplier(int supplierBN) throws Exception {
        SupplierCard supplierCard  = suppliers.get(supplierBN);
        if(supplierCard == null) throw new Exception("supplier BN does not exist.");
        return supplierCard;
    }

    public void addSupplier(String supplierName, int bankNumber , int branchNumber, int bankAccount, String payWay) throws Exception {
        if(bankNumber < 0) throw new Exception("bank number must be a positive number");
        if(branchNumber < 0) throw new Exception("branch must be a positive number");
        if(bankAccount < 0) throw new Exception("bank account must be a positive number");
        Enumeration<Integer> enumeration = suppliers.keys();
        while(enumeration.hasMoreElements()){
            SupplierCard supplierCard = suppliers.get(enumeration.nextElement());
            if(supplierCard.getSupplierName().equals(supplierName)) throw new Exception("supplier name all ready exist");
            if(supplierCard.getSupplierBankNumber() == bankNumber && supplierCard.getSupplierBranchNumber() == branchNumber && supplierCard.getSupplierAccountNumber() == bankAccount)
                throw new Exception("the combination of bank number , branch number and bank account must be unique");
        }
        if(!(payWay.equals("check") || payWay.equals("bank transfer") || payWay.equals("cash")))
            throw new Exception("pay way must be check/bank transfer/cash.");
        SupplierCard supplierCard = new SupplierCard(suppliers.size() ,supplierName, bankNumber,branchNumber,bankAccount,payWay);
        suppliers.put(suppliers.size() , supplierCard);
    }

    public void removeSupplier(int removeSupplier) throws Exception {
        SupplierCard supplierCard = suppliers.remove(removeSupplier);
        if(supplierCard == null) throw new Exception("supplier BN does not exist.");
        else supplierCard.removeSupplier();
        List<Item> toRemoveItems = supplierCard.showAllItemsOfSupplier();
        for(Item item : toRemoveItems){
            removeItemFromSupplier(removeSupplier , item.getItemId());
        }
        List<Order> toRemoveOrders = supplierCard.showAllOrdersOfSupplier();
        for(Order order : toRemoveOrders){
            supplierCard.removeAllOrders(order.getOrderId());
        }
    }

    public SupplierCard showSupplierBN(String supplierName) throws Exception {
        Enumeration<Integer> enumeration = suppliers.keys();
        while(enumeration.hasMoreElements()) {
            int current = enumeration.nextElement();
            if (suppliers.get(current).getSupplierName().equals(supplierName)) {
                return suppliers.get(current);
            }
        }
        throw new Exception("supplier name is not exist");
    }

    public void updateSupplierPayWay(int supplierBN, String payWay) throws Exception {
        SupplierCard supplierCard = suppliers.get(supplierBN);
        if(supplierCard == null) throw new Exception("supplier BN does not exist.");
            suppliers.get(supplierBN);
        try {
            suppliers.get(supplierBN).updateSupplierPayWay(payWay);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public void updateSupplierBankAccount(int supplierBN, int bankNumber , int branchNumber ,int bankAccount) throws Exception {
        SupplierCard supplierCard = suppliers.get(supplierBN);
        if(supplierCard == null) throw new Exception("supplier BN does not exist.");
        try {
            suppliers.get(supplierBN).updateSupplierBankAccount(bankNumber , branchNumber , bankAccount);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public void addContactPhone(int supplierBN, String phone, String name) throws Exception {
        SupplierCard supplierCard = suppliers.get(supplierBN);
        if(supplierCard == null) throw new Exception("supplier BN does not exist.");
        try {
            suppliers.get(supplierBN).addContactPhone(phone, name);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public void addContactEmail(int supplierBN, String email, String name) throws Exception {
        SupplierCard supplierCard = suppliers.get(supplierBN);
        if(supplierCard == null) throw new Exception("supplier BN does not exist.");
        try {
            suppliers.get(supplierBN).addContactEmail(email, name);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public void removeContactPhone(int supplierBN, String phone) throws Exception {
        SupplierCard supplierCard = suppliers.get(supplierBN);
        if(supplierCard == null) throw new Exception("supplier BN does not exist.");
        try {
            suppliers.get(supplierBN).removeContactPhone(phone);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public void removeContactEmail(int supplierBN, String email) throws Exception {
        SupplierCard supplierCard = suppliers.get(supplierBN);
        if(supplierCard == null) throw new Exception("supplier BN does not exist.");
        try {
            suppliers.get(supplierBN).removeContactEmail(email);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public void updateContactPhone(int supplierBN, String phone , String name) throws Exception {
        SupplierCard supplierCard = suppliers.get(supplierBN);
        if(supplierCard == null) throw new Exception("supplier BN does not exist.");
        try {
            suppliers.get(supplierBN).updateContactPhone(phone , name);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public void updateContactEmail(int supplierBN, String email , String name) throws Exception {
        SupplierCard supplierCard = suppliers.get(supplierBN);
        if(supplierCard == null) throw new Exception("supplier BN does not exist.");
        try {
            suppliers.get(supplierBN).updateContactEmail(email , name);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public List<SupplierCard> showAllSuppliers() {
        List<SupplierCard> list = new LinkedList<>();
        Enumeration<SupplierCard> enumeration = suppliers.elements();
        while (enumeration.hasMoreElements()) {
            list.add(0 , enumeration.nextElement());
        }
        return list;
    }

    public List<Item> showAllItemsOfSupplier(int supplierBN) throws Exception {
        SupplierCard supplierCard = suppliers.get(supplierBN);
        if(supplierCard == null) throw new Exception("supplier BN does not exist.");
        return suppliers.get(supplierBN).showAllItemsOfSupplier();
    }

    public List<Item> showAllItemsOfOrder(int supplierBN , int orderId) throws Exception {
        SupplierCard supplierCard = suppliers.get(supplierBN);
        if(supplierCard == null) throw new Exception("supplier BN does not exist.");
        return suppliers.get(supplierBN).showAllItemsOfOrder(orderId);
    }

    public List<Item> showAllItems() {
        List<Item> list = new LinkedList<>();
        Enumeration<SupplierCard> enumeration = suppliers.elements();
        while (enumeration.hasMoreElements()) {
            List<Item> toAdd = enumeration.nextElement().showAllItemsOfSupplier();
            list.addAll(0 , toAdd);
        }
        return list;
    }

    public Item showItemOfSupplier(int supplierBN , int itemId) throws Exception {
        SupplierCard supplierCard = suppliers.get(supplierBN);
        Item item;
        if(supplierCard == null) throw new Exception("supplier BN does not exist.");
        try {
            item = supplierCard.showItemOfSupplier(itemId);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return item;
    }



    public Item addItem(int supplierBN,String name , double price, int typeID, LocalDate expirationDate) throws Exception {
        Item item;
        SupplierCard supplierCard = suppliers.get(supplierBN);
        if(supplierCard == null) throw new Exception("supplier BN does not exist.");
        try {
            item = suppliers.get(supplierBN).addItem(numOfItems , name, price, typeID, expirationDate);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        numOfItems++;
        return item;
    }

    public void removeItem(int itemId) throws Exception {
        if(numOfItems <= itemId || itemId < 0) throw new Exception("itemId does not exist.");
        Enumeration<SupplierCard> enumeration = suppliers.elements();
        while (enumeration.hasMoreElements()) {
            SupplierCard supplierCard = enumeration.nextElement();
            supplierCard.removeItemFromSupplier(itemId , false);
        }
    }

    public void removeItemFromRegularOrder(int supplierBN, int orderId, int itemId) throws Exception {
        SupplierCard supplierCard = suppliers.get(supplierBN);
        if(supplierCard == null) throw new Exception("supplier BN does not exist.");
        try {
            supplierCard.removeItemFromRegularOrder(orderId , itemId);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public void removeAmountItemFromRegularOrder(int supplierBN, int orderId, int itemId, int amount) throws Exception {
        SupplierCard supplierCard = suppliers.get(supplierBN);
        if(supplierCard == null) throw new Exception("supplier BN does not exist.");
        try {
            supplierCard.removeAmountItemFromRegularOrder(orderId , itemId , amount);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public void removeItemFromSupplier(int supplierBN, int itemId) throws Exception {
        SupplierCard supplierCard = suppliers.get(supplierBN);
        if(supplierCard == null) throw new Exception("supplier BN does not exist.");
        if(numOfItems < itemId || itemId < 0) throw new Exception("itemId does not exist.");
        try {
            supplierCard.removeItemFromSupplier(itemId , true);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public Order addRegularOrder(int supplierBN , int branchId) throws Exception {
        SupplierCard supplierCard = suppliers.get(supplierBN);
        if(supplierCard == null) throw new Exception("supplier BN does not exist.");
        Order order;
        try {
            order = suppliers.get(supplierBN).addRegularOrder(numOfOrders, branchId);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        numOfOrders++;
        return order;
    }

    public void addConstantOrder(int supplierBN, int branchID , Hashtable<Integer , Integer> items) throws Exception {
        SupplierCard supplierCard = suppliers.get(supplierBN);
        if(supplierCard == null) throw new Exception("supplier BN does not exist.");
        try {
            suppliers.get(supplierBN).addConstantOrder(0, branchID , items);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public Order addNeededOrder(int typeID, int neededAmount, int branchID) throws Exception {
        Order order;
        Item item = null;
        double bestPrice = Integer.MAX_VALUE;
        int bestSupplier = 0;
        try {
            Enumeration<SupplierCard> enumeration = suppliers.elements();
            while(enumeration.hasMoreElements()) {
                SupplierCard temp = enumeration.nextElement();
                List<Item> items = temp.getSupplierItems();
                for (Item i : items) {
                    if (i.getTypeID() == typeID) {
                        double currentPrice = i.getPrice()*neededAmount;
                        if (neededAmount >= i.getQuantityDocument().getMinimalAmount()) {
                            currentPrice = currentPrice * (100-i.getQuantityDocument().getDiscount()) /100;
                        }
                        if (currentPrice < bestPrice) {
                            bestSupplier = temp.getSupplierBN();
                        }
                        item = i;
                    }
                }
            }
            order = suppliers.get(bestSupplier).addNeededOrder(numOfOrders, branchID, item, neededAmount);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        numOfOrders++;
        return order;
    }

    public void addItemToOrder(int supplierBN, int orderId, int itemId , int amount) throws Exception {
        SupplierCard supplierCard = suppliers.get(supplierBN);
        if(supplierCard == null) throw new Exception("supplier BN does not exist.");
        try{
            Order order = supplierCard.showOrderOfSupplier(orderId);
            if (!(order instanceof regularOrder)) {
                throw new Exception("you cannot add new items to needed order");
            }
            suppliers.get(supplierBN).addItemToOrder(orderId, itemId , amount);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public Order showOrderOfSupplier(int supplierBN, int orderId) throws Exception {
        SupplierCard supplierCard = suppliers.get(supplierBN);
        if(supplierCard == null) throw new Exception("supplier BN does not exist.");
        try{
            return suppliers.get(supplierBN).showOrderOfSupplier(orderId);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public List<Order> showAllOrdersOfSupplier(int supplierBN) throws Exception {
        SupplierCard supplierCard = suppliers.get(supplierBN);
        if(supplierCard == null) throw new Exception("supplier BN does not exist.");
        return suppliers.get(supplierBN).showAllOrdersOfSupplier();
    }


    public Order showTotalAmount(int supplierBN, int orderId) throws Exception {
        SupplierCard supplierCard = suppliers.get(supplierBN);
        if(supplierCard == null) throw new Exception("supplier BN does not exist.");
        return suppliers.get(supplierBN).showTotalAmount(orderId);
    }

    public Order showDeliverTime(int supplierBN, int orderId) throws Exception {
        SupplierCard supplierCard = suppliers.get(supplierBN);
        if(supplierCard == null) throw new Exception("supplier BN does not exist.");
        try {
            return suppliers.get(supplierBN).showDeliverTime(orderId);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public void updateDeliverTime(int supplierBN, int orderId, LocalDate deliverTime) throws Exception {
        SupplierCard supplierCard = suppliers.get(supplierBN);
        if(supplierCard == null) throw new Exception("supplier BN does not exist.");
        try {
            suppliers.get(supplierBN).updateDeliverTime(orderId, deliverTime);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public void addQuantityDocument(int supplierBN, int itemId, int minimalAmount, int discount) throws Exception {
        SupplierCard supplierCard = suppliers.get(supplierBN);
        if(supplierCard == null) throw new Exception("supplier BN does not exist.");
        try {
            suppliers.get(supplierBN).addQuantityDocument(itemId, minimalAmount, discount);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void removeQuantityDocument(int supplierBN, int itemId) throws Exception {
        SupplierCard supplierCard = suppliers.get(supplierBN);
        if(supplierCard == null) throw new Exception("supplier BN does not exist.");
        try {
            suppliers.get(supplierBN).removeQuantityDocument(itemId);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public QuantityDocument showQuantityDocument(int supplierBN, int itemId) throws Exception {
        SupplierCard supplierCard = suppliers.get(supplierBN);
        if(supplierCard == null) throw new Exception("supplier BN does not exist.");
        try {
            return suppliers.get(supplierBN).showQuantityDocument(itemId);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void updateMinimalAmountOfQD(int supplierBN, int itemId, int minimalAmount) throws Exception {
        SupplierCard supplierCard = suppliers.get(supplierBN);
        if(supplierCard == null) throw new Exception("supplier BN does not exist.");
        try {
            suppliers.get(supplierBN).updateMinimalAmountOfQD(itemId, minimalAmount);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void updateDiscountOfQD(int supplierBN, int itemId, int discount) throws Exception {
        SupplierCard supplierCard = suppliers.get(supplierBN);
        if(supplierCard == null) throw new Exception("supplier BN does not exist.");
        try {
            suppliers.get(supplierBN).updateDiscountOfQD(itemId,discount);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void addSupplierAgreement(int supplierBN, int minimalAmount, int discount, boolean constantTime, boolean shipToUs) throws Exception {
        SupplierCard supplierCard = suppliers.get(supplierBN);
        if(supplierCard == null) throw new Exception("supplier BN does not exist.");
        try {
            suppliers.get(supplierBN).addSupplierAgreement(minimalAmount, discount, constantTime, shipToUs);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public SupplierAgreement showSupplierAgreement(int supplierBN) throws Exception {
        SupplierCard supplierCard = suppliers.get(supplierBN);
        if(supplierCard == null) throw new Exception("supplier BN does not exist.");
        try {
            return suppliers.get(supplierBN).showSupplierAgreement();
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public void updateMinimalAmountOfSA(int supplierBN, int minimalAmount) throws Exception {
        SupplierCard supplierCard = suppliers.get(supplierBN);
        if(supplierCard == null) throw new Exception("supplier BN does not exist.");
        try {
            suppliers.get(supplierBN).updateMinimalAmountOfSA(minimalAmount);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public void updateDiscountOfSA(int supplierBN, int discount) throws Exception {
        SupplierCard supplierCard = suppliers.get(supplierBN);
        if(supplierCard == null) throw new Exception("supplier BN does not exist.");
        try {
            suppliers.get(supplierBN).updateDiscountOfSA(discount);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public void updateConstantTime(int supplierBN, boolean constantTime) throws Exception {
        SupplierCard supplierCard = suppliers.get(supplierBN);
        if(supplierCard == null) throw new Exception("supplier BN does not exist.");
        suppliers.get(supplierBN).updateConstantTime(constantTime);
    }

    public void updateShipToUs(int supplierBN, boolean shipToUs) throws Exception {
        SupplierCard supplierCard = suppliers.get(supplierBN);
        if(supplierCard == null) throw new Exception("supplier BN does not exist.");
        suppliers.get(supplierBN).updateShipToUs(shipToUs);
    }

    public void updatePrice(int supplierBN, int itemId, double price) throws Exception {
        SupplierCard supplierCard = suppliers.get(supplierBN);
        if(supplierCard == null) throw new Exception("supplier BN does not exist.");
        try {
            suppliers.get(supplierBN).updatePrice(itemId,price);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

}
