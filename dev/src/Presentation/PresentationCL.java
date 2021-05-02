package Presentation;

import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

import  BusinessLayer.SupplierBusiness.facade.SupplierService;
import BusinessLayer.SupplierBusiness.facade.Tresponse;
import BusinessLayer.SupplierBusiness.facade.outObjects.*;
import BusinessLayer.SupplierBusiness.facade.response;

public class PresentationCL{

    private final SupplierService service;

    public PresentationCL() {
        service = new SupplierService();
    }

    public void loadData(){service.LoadData();}

    public void mainRun(boolean firstTime){
        Scanner scanner = new Scanner(System.in);
        String[] mainMenuArray = {"showing methods" , "adding methods" , "removing methods" , "updating methods" ,"end program" };
        int option = -1;
        while(true){
            System.out.println("please select an option: ");
            for(int i = 1 ; i <= mainMenuArray.length ; i++){
                System.out.println(i + ") " + mainMenuArray[i-1]);
            }
            option = menuCheck(scanner);
            switch (option) {
                case 1 -> showingMethods();
                case 2 -> addingMethods();
                case 3 -> removingMethods();
                case 4 -> updatingMethods();
                case 5 -> {
                    return;
                }
                default -> System.out.println("illegal option!!!");
            }
        }
    }

    private void showingMethods(){
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        int option = -1;
        String[] showingMethodArray = {"show Supplier","show SupplierBN","show All Suppliers", "show Item Of Supplier","show All Items Of Supplier",
                "show All Items", "show Order Of Supplier","show All Orders Of Supplier","show Total Amount",
                "show Deliver Time", "show Quantity Document","show Supplier Agreement","back to the main menu"};
        System.out.println("please select the showing method: ");
        while (true) {
            for (int i = 1; i <= showingMethodArray.length ; i++) {
                System.out.println(i + ") " + showingMethodArray[i - 1]);
            }
            option = menuCheck(scanner);
            int BN;
            switch (option) {
                case 1 -> {
                    BN = intScan(scanner , "please enter supplier BN" , "BN must be a number");
                    Tresponse<SupplierCard> response = service.showSupplier(BN);
                    if (response.isError()) System.out.println(response.getError());
                    else {
                        System.out.println(response.getOutObject().toString());
                        Tresponse<List<Item>> items = service.showAllItemsOfSupplier(BN);
                        if (items.isError()) System.out.println(items.getError() + "\n");
                        else {
                            List<Item> responsesItem = items.getOutObject();
                            System.out.println("\tItemsID:");
                            for (Item item : responsesItem) {
                                System.out.println("\t\t" +item.toStringId());
                            }
                            Tresponse<List<Order>> orders = service.showAllOrdersOfSupplier(BN);
                            if (orders.isError()) System.out.println(items.getError() + "\n");
                            else {
                                List<Order> responsesOrders = orders.getOutObject();
                                System.out.println("\tOrdersId:");
                                for (Order order : responsesOrders) {
                                    System.out.println("\t\t" + order.toStringId());
                                }
                            }
                        }
                    }
                    toContinue(scanner);
                }
                case 2 -> {
                    String name = stringScan(scanner , "please enter supplier name");
                    Tresponse<SupplierCard> response = service.showSupplierBN(name);
                    if (response.isError()) System.out.println(response.getError() + "\n");
                    else System.out.println("supplierBN is: " + response.getOutObject().toStringId());
                    toContinue(scanner);
                }
                case 3 -> {
                    Tresponse<List<SupplierCard>> responsesList = service.showAllSuppliers();
                    if (responsesList.isError()) System.out.println(responsesList.getError() + "\n");
                    else {
                        List<SupplierCard> responses = responsesList.getOutObject();
                        for (SupplierCard supplierCard : responses) {
                            System.out.println(supplierCard.toString());
                        }
                    }
                    toContinue(scanner);
                }
                case 4 -> {
                    BN = intScan(scanner , "please enter supplier BN" , "BN must be a number");
                    int itemId = intScan(scanner , "please enter itemId", "itemId must be a number");
                    Tresponse<Item> response = service.showItemOfSupplier(BN , itemId);
                    if (response.isError()) System.out.println(response.getError() + "\n");
                    else System.out.println(response.getOutObject().toString(false));
                    toContinue(scanner);
                }
                case 5 -> {
                    BN = intScan(scanner , "please enter supplier BN" , "BN must be a number");
                    Tresponse<List<Item>> responsesList = service.showAllItemsOfSupplier(BN);
                    if (responsesList.isError()) System.out.println(responsesList.getError() + "\n");
                    else {
                        List<Item> responses = responsesList.getOutObject();
                        for (Item item : responses) {
                            System.out.println(item.toString(false));
                        }
                    }
                    toContinue(scanner);
                }
                case 6 ->{
                    Tresponse<List<Item>> responsesList = service.showAllItems();
                    if (responsesList.isError()) System.out.println(responsesList.getError() + "\n");
                    else {
                        List<Item> responses = responsesList.getOutObject();
                        for (Item item : responses) {
                            System.out.println(item.toString(false));
                        }
                    }
                    toContinue(scanner);
                }
                case 7 -> {
                    BN = intScan(scanner , "please enter supplier BN" , "BN must be a number");
                    int orderId = intScan(scanner , "please enter orderId" , "orderId must be a number");
                    service.showTotalAmount(BN , orderId);
                    Tresponse<Order> response = service.showOrderOfSupplier(BN, orderId);
                    if (response.isError()) System.out.println(response.getError() + "\n");
                    else {
                        System.out.println(response.getOutObject().toString());
                        Tresponse<SupplierAgreement> supplierAgreement = service.showSupplierAgreement(BN);
                        if(supplierAgreement.isError()) System.out.println(supplierAgreement.getError() + "\n");
                        else{
                            System.out.println("\tship to us: " + supplierAgreement.getOutObject().toStringShipToUs());
                            //System.out.println("\tconstant time: " + supplierAgreement.getOutObject().toStringConstantTime());
                        }
                        Tresponse<List<Item>> items = service.showAllItemsOfOrder(BN, orderId);
                        if (items.isError()) System.out.println(items.getError() + "\n");
                        else {
                            List<Item> responseItem = items.getOutObject();
                            for (Item item : responseItem) {
                                System.out.println(item.toString(response.getOutObject().toStringAmount(item.toStringId())));
                            }
                        }
                    }
                    toContinue(scanner);
                }
                case 8 -> {
                    BN = intScan(scanner , "please enter supplier BN" , "BN must be a number");
                    Tresponse<List<Order>> responsesList = service.showAllOrdersOfSupplier(BN);
                    if (responsesList.isError()) System.out.println(responsesList.getError() + "\n");
                    else {
                        List<Order> responses = responsesList.getOutObject();
                        for (Order order : responses) {
                            System.out.println(order.toString());
                            Tresponse<List<Item>> items = service.showAllItemsOfOrder(BN, Integer.parseInt(order.toStringId()));
                            if (items.isError()) System.out.println(items.getError() + "\n");
                            else {
                                Tresponse<SupplierAgreement> supplierAgreement = service.showSupplierAgreement(BN);
                                if(supplierAgreement.isError()) System.out.println(supplierAgreement.getError() + "\n");
                                else{
                                    System.out.println("\tship to us: " + supplierAgreement.getOutObject().toStringShipToUs());
                                    //System.out.println("\tconstant time: " + supplierAgreement.getOutObject().toStringConstantTime());
                                }
                                List<Item> responseItem = items.getOutObject();
                                for (Item item : responseItem) {
                                    System.out.println(item.toString(order.toStringAmount(item.toStringId())));

                                }
                            }
                        }
                    }
                    toContinue(scanner);
                }
                case 9 -> {
                    BN = intScan(scanner , "please enter supplier BN" , "BN must be a number");
                    int orderId = intScan(scanner , "please enter orderId" , "orderId must be a number");
                    Tresponse<Order> response = service.showTotalAmount(BN, orderId);
                    if (response.isError()) System.out.println(response.getError() + "\n");
                    else System.out.println("total amount is: " + response.getOutObject().toStringTotalAmount());
                    toContinue(scanner);
                }
                case 10 -> {
                    BN = intScan(scanner , "please enter supplier BN" , "BN must be a number");
                    int orderId = intScan(scanner , "please enter orderId" , "orderId must be a number");
                    Tresponse<Order> response = service.showDeliverTime(BN, orderId);
                    if (response.isError()) System.out.println(response.getError() + "\n");
                    else System.out.println("deliver time is : " + response.getOutObject().toStringDeliverTime());
                    toContinue(scanner);
                }
                case 11 -> {
                    BN = intScan(scanner , "please enter supplier BN" , "BN must be a number");
                    int itemId = intScan(scanner , "please enter itemId", "itemId must be a number");
                    Tresponse<QuantityDocument> response = service.showQuantityDocument(BN, itemId);
                    if (response.isError()) System.out.println(response.getError() + "\n");
                    else System.out.println(response.getOutObject().toString());
                    toContinue(scanner);
                }
                case 12 -> {
                    BN = intScan(scanner , "please enter supplier BN" , "BN must be a number");
                    Tresponse<SupplierAgreement> response = service.showSupplierAgreement(BN);
                    if (response.isError()) System.out.println(response.getError() + "\n");
                    else System.out.println(response.getOutObject().toString());
                    toContinue(scanner);
                }
                case 13 -> mainRun(false);
                default -> {
                    System.out.println("illegal option!!!");
                    toContinue(scanner);
                }
            }
        }
    }

    private void addingMethods(){
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        int option = -1;
        String[] showingMethodArray = {"add supplier","add Contact Phone","add Contact Email","add Item", "add constant Order" ,
                "add Regular Order", "add Needed Order","add Item To Order","add Quantity Document",
                "add Supplier Agreement","back to the main menu"};
        System.out.println("please select the showing method: ");
        while (true) {
            for (int i = 1; i <= showingMethodArray.length; i++) {
                System.out.println(i + ") " + showingMethodArray[i - 1]);
            }
            option = menuCheck(scanner);
            int BN;
            switch (option) {
                case 1 -> {
                    String name = stringScan(scanner , "please enter supplier name");
                    int bankNumber = intScan(scanner , "please enter supplier bank number" , "bank number must be a number");
                    int branchNumber = intScan(scanner , "please enter supplier branch number" , "branch number must be a number");
                    int bankAccount = intScan(scanner , "please enter supplier bank account" , "bank account must be a number");
                    String payWay = stringScan(scanner , "please enter supplier payWay");
                    response response = service.addSupplier(name, bankNumber , branchNumber , bankAccount, payWay);
                    if (response.isError()) System.out.println(response.getError()+ "\n");
                    else System.out.println("The operation was completed successfully\n");
                }
                case 2 -> {
                    BN = intScan(scanner , "please enter supplier BN" , "BN must be a number");
                    String phone = stringScan(scanner , "please enter supplier contact phone");
                    String name = stringScan(scanner , "please enter supplier contact name");
                    response response = service.addContactPhone(BN, phone, name);
                    if (response.isError()) System.out.println(response.getError()+ "\n");
                    else System.out.println("The operation was completed successfully\n");
                }
                case 3 -> {
                    BN = intScan(scanner , "please enter supplier BN" , "BN must be a number");
                    String email = stringScan(scanner , "please enter supplier contact email");
                    String name = stringScan(scanner , "please enter supplier contact name");
                    response response = service.addContactEmail(BN, email, name);
                    if (response.isError()) System.out.println(response.getError()+ "\n");
                    else System.out.println("The operation was completed successfully\n");
                }
                case 4 -> {
                    BN = intScan(scanner , "please enter supplier BN" , "BN must be a number");
                    String name = stringScan(scanner , "please enter item name");
                    double price = doubleScan(scanner , "please enter item price" , "price must be a number");
                    int typeID = intScan(scanner , "please enter item typeId" , "typeId must be a number");
                    LocalDate expirationDate = dateScan(scanner , "expiration date of the item");
                    Tresponse<Item> response = service.addItem(BN,name, price, typeID, expirationDate );
                    if (response.isError()) System.out.println(response.getError()+ "\n");
                    else System.out.println("ItemId is: " + response.getOutObject().toStringId() + "\n");
                    toContinue(scanner);
                }
                case 5 -> {
                    BN = intScan(scanner , "please enter supplier BN" , "BN must be a number");
                    int branchID = intScan(scanner , "please enter branchId for deliver" , "branchId must be a number");
                    Hashtable<Integer , Integer> items = hashScan(scanner);
                    response response = service.addConstantOrder(BN, branchID , items);
                    if (response.isError()) System.out.println(response.getError() + "\n");
                    else System.out.println("constant Order added successfully");
                    toContinue(scanner);
                }
                case 6 ->{
                    BN = intScan(scanner , "please enter supplier BN" , "BN must be a number");
                    int branchID = intScan(scanner , "please enter branchId for deliver" , "branchId must be a number");
                    Tresponse<Order> response = service.addRegularOrder(BN, branchID);
                    if (response.isError()) System.out.println(response.getError() + "\n");
                    else System.out.println("orderId is: " + response.getOutObject().toStringId() + "\n");
                    toContinue(scanner);
                }
                case 7 -> {
                    int typeID = intScan(scanner , "please enter item typeId" , "typeId must be a number");
                    int neededAmount = intScan(scanner , "please enter amount of the item" , "amount must be a number");
                    int branchID = intScan(scanner , "please enter branchId for deliver" , "branchId must be a number");
                    Tresponse<Order> response = service.addNeededOrder(typeID, neededAmount, branchID);
                    if (response.isError()) System.out.println(response.getError() + "\n");
                    else System.out.println("orderId is: " + response.getOutObject().toStringId() + "\n");
                    toContinue(scanner);
                }
                case 8 -> {
                    BN = intScan(scanner , "please enter supplier BN" , "BN must be a number");
                    int orderId = intScan(scanner , "please enter orderId" , "orderId must be a number");
                    int itemId = intScan(scanner , "please enter itemId", "itemId must be a number");
                    int amount = intScan(scanner , "please enter amount of the item" , "amount must be a number");
                    response response = service.addItemToOrder(BN, orderId, itemId, amount);
                    if (response.isError()) System.out.println(response.getError() + "\n");
                    else System.out.println("The operation was completed successfully\n");
                }
                case 9 -> {
                    BN = intScan(scanner , "please enter supplier BN" , "BN must be a number");
                    int itemId = intScan(scanner , "please enter itemId", "itemId must be a number");
                    int minimalAmount = intScan(scanner , "please enter the minimal amount" , "minimal amount must be a number");
                    int discount = intScan(scanner , "please enter the discount" , "discount must be a number");
                    response response = service.addQuantityDocument(BN, itemId, minimalAmount, discount);
                    if (response.isError()) System.out.println(response.getError() + "\n");
                    else System.out.println("The operation was completed successfully\n");
                }
                case 10 -> {
                    BN = intScan(scanner , "please enter supplier BN" , "BN must be a number");
                    int minimalAmount = intScan(scanner , "please enter the minimal amount" , "minimal amount must be a number");
                    int discount = intScan(scanner , "please enter the discount" , "discount must be a number");
                    boolean constantTime = booleanScan(scanner , "please enter true for constant time , and false otherwise" , "you must enter true/false");
                    boolean shipToUs = booleanScan(scanner , "please enter true for ship to us , and false otherwise" , "you must enter true/false");
                    response response = service.addSupplierAgreement(BN, minimalAmount, discount, constantTime, shipToUs);
                    if (response.isError()) System.out.println(response.getError() + "\n");
                    else System.out.println("The operation was completed successfully\n");
                }
                case 11 -> mainRun(false);
                default ->{
                    System.out.println("illegal option!!!\n");
                    toContinue(scanner);
                }
            }
        }
    }

    private void removingMethods(){
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        int option = -1;
        String[] removeMethodArray = {"remove Supplier","remove Contact Phone","remove Contact Email","remove Item",
                "remove item from regular order" , "remove amount of item from regular order" ,
                "remove Quantity Document","back to the main menu"};
        System.out.println("please select the showing method: ");
        while (true) {
            for (int i = 1; i <= removeMethodArray.length; i++) {
                System.out.println(i + ") " + removeMethodArray[i - 1]);
            }
            option = menuCheck(scanner);
            int BN;
            switch (option) {
                case 1 -> {
                    BN = intScan(scanner , "please enter supplier BN" , "BN must be a number");
                    response response = service.removeSupplier(BN);
                    if (response.isError()) System.out.println(response.getError()+ "\n");
                    else System.out.println("The operation was completed successfully\n");
                }
                case 2 -> {
                    BN = intScan(scanner , "please enter supplier BN" , "BN must be a number");
                    String phone = stringScan(scanner , "please enter supplier contact phone");
                    response response = service.removeContactPhone(BN, phone);
                    if (response.isError()) System.out.println(response.getError()+ "\n");
                    else System.out.println("The operation was completed successfully\n");
                }
                case 3 -> {
                    BN = intScan(scanner , "please enter supplier BN" , "BN must be a number");
                    String email = stringScan(scanner , "please enter supplier contact email");
                    response response = service.removeContactEmail(BN, email);
                    if (response.isError()) System.out.println(response.getError()+ "\n");
                    else System.out.println("The operation was completed successfully\n");
                }
                case 4 -> {
                    int itemId = intScan(scanner , "please enter itemId", "itemId must be a number");
                    response response = service.removeItem(itemId);
                    if (response.isError()) System.out.println(response.getError()+ "\n");
                    else System.out.println("The operation was completed successfully\n");
                }
                case 5->{
                    BN = intScan(scanner , "please enter supplier BN" , "BN must be a number");
                    int orderId = intScan(scanner , "please enter orderId" , "orderId must be a number");
                    int itemId = intScan(scanner , "please enter itemId", "itemId must be a number");
                    response response = service.removeItemFromRegularOrder(BN , orderId , itemId);
                    if (response.isError()) System.out.println(response.getError()+ "\n");
                    else System.out.println("The operation was completed successfully\n");
                }
                case 6->{
                    BN = intScan(scanner , "please enter supplier BN" , "BN must be a number");
                    int orderId = intScan(scanner , "please enter orderId" , "orderId must be a number");
                    int itemId = intScan(scanner , "please enter itemId", "itemId must be a number");
                    int amount = intScan(scanner , "please enter the amount of the item" , "amount must be a number");
                    response response = service.removeAmountItemFromRegularOrder(BN , orderId , itemId , amount);
                    if (response.isError()) System.out.println(response.getError()+ "\n");
                    else System.out.println("The operation was completed successfully\n");
                }
                case 7 -> {
                    BN = intScan(scanner , "please enter supplier BN" , "BN must be a number");
                    int itemId = intScan(scanner , "please enter itemId", "itemId must be a number");
                    response response = service.removeQuantityDocument(BN, itemId);
                    if (response.isError()) System.out.println(response.getError()+ "\n");
                    else System.out.println("The operation was completed successfully\n");
                }
                case 8 -> mainRun(false);
                default ->{
                    System.out.println("illegal option!!!");
                    toContinue(scanner);
                }
            }
        }
    }

    private void updatingMethods() {
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        int option  = -1;
        int BN;
        String[] updateMethodArray = {"update Supplier PayWay", "update Supplier BankAccount", "update Contact Phone", "update Contact Email",
                "update Deliver Time", "update Minimal Amount Of Quantity Document", "update Discount Of Quantity Document",
                "update Minimal Amount Of Supplier Agreement", "update Discount Of Supplier Agreement",
                "update Constant Time", "update Ship To Us", "update Price", "back to the main menu"};
        System.out.println("please select the showing method: ");
        while (true) {
            for (int i = 1; i <= updateMethodArray.length; i++) {
                System.out.println(i + ") " + updateMethodArray[i - 1]);
            }
            option = menuCheck(scanner);
            switch (option) {
                case 1 -> {
                    BN = intScan(scanner , "please enter supplier BN" , "BN must be a number");
                    String payWay = stringScan(scanner , "please enter supplier payWay");
                    response response = service.updateSupplierPayWay(BN, payWay);
                    if (response.isError()) System.out.println(response.getError()+ "\n");
                    else System.out.println("The operation was completed successfully\n");
                }
                case 2 -> {
                    BN = intScan(scanner , "please enter supplier BN" , "BN must be a number");
                    int bankNumber = intScan(scanner , "please enter supplier bank number" , "bank number must be a number");
                    int branchNumber = intScan(scanner , "please enter supplier branch number" , "branch number must be a number");
                    int bankAccount = intScan(scanner , "please enter supplier bank account" , "bank account must be a number");
                    response response = service.updateSupplierBankAccount(BN, bankNumber , branchNumber, bankAccount);
                    if (response.isError()) System.out.println(response.getError()+ "\n");
                    else System.out.println("The operation was completed successfully\n");
                }
                case 3 -> {
                    BN = intScan(scanner , "please enter supplier BN" , "BN must be a number");
                    String phone = stringScan(scanner , "please enter supplier contact phone");
                    String name = stringScan(scanner , "please enter supplier contact name");
                    response response = service.updateContactPhone(BN, phone , name);
                    if (response.isError()) System.out.println(response.getError()+ "\n");
                    else System.out.println("The operation was completed successfully\n");
                }
                case 4 -> {
                    BN = intScan(scanner , "please enter supplier BN" , "BN must be a number");
                    String email = stringScan(scanner , "please enter supplier contact email");
                    String name = stringScan(scanner , "please enter supplier contact name");
                    response response = service.updateContactEmail(BN, email , name);
                    if (response.isError()) System.out.println(response.getError()+ "\n");
                    else System.out.println("The operation was completed successfully\n");
                }
                case 5 -> {
                    BN = intScan(scanner , "please enter supplier BN" , "BN must be a number");
                    int orderId = intScan(scanner , "please enter orderId" , "orderId must be a number");
                    LocalDate date = dateScan(scanner , "deliver time of the order");
                    response response = service.updateDeliverTime(BN, orderId, date);
                    if (response.isError()) System.out.println(response.getError()+ "\n");
                    else System.out.println("The operation was completed successfully\n");
                }
                case 6 -> {
                    BN = intScan(scanner , "please enter supplier BN" , "BN must be a number");
                    int itemId = intScan(scanner , "please enter itemId", "itemId must be a number");
                    int minimalAmount = intScan(scanner , "please enter the minimal amount" , "minimal amount must be a number");
                    response response = service.updateMinimalAmountOfQD(BN, itemId, minimalAmount);
                    if (response.isError()) System.out.println(response.getError()+ "\n");
                    else System.out.println("The operation was completed successfully\n");
                }
                case 7 -> {
                    BN = intScan(scanner , "please enter supplier BN" , "BN must be a number");
                    int itemId = intScan(scanner , "please enter itemId", "itemId must be a number");
                    int discount = intScan(scanner , "please enter the discount" , "discount must be a number");
                    response response = service.updateDiscountOfQD(BN, itemId, discount);
                    if (response.isError()) System.out.println(response.getError()+ "\n");
                    else System.out.println("The operation was completed successfully\n");
                }
                case 8 -> {
                    BN = intScan(scanner , "please enter supplier BN" , "BN must be a number");
                    int minimalAmount = intScan(scanner , "please enter the minimal amount" , "minimal amount must be a number");
                    response response = service.updateMinimalAmountOfSA(BN, minimalAmount);
                    if (response.isError()) System.out.println(response.getError()+ "\n");
                    else System.out.println("The operation was completed successfully\n");
                }
                case 9 -> {
                    BN = intScan(scanner , "please enter supplier BN" , "BN must be a number");
                    int discount = intScan(scanner , "please enter the discount" , "discount must be a number");
                    response response = service.updateDiscountOfSA(BN, discount);
                    if (response.isError()) System.out.println(response.getError()+ "\n");
                    else System.out.println("The operation was completed successfully\n");
                }
                case 10 -> {
                    BN = intScan(scanner , "please enter supplier BN" , "BN must be a number");
                    boolean constantTime = booleanScan(scanner , "please enter true for constant time and false otherwise" , "you must enter true/false");
                    response response = service.updateConstantTime(BN, constantTime);
                    if (response.isError()) System.out.println(response.getError()+ "\n");
                    else System.out.println("The operation was completed successfully\n");
                }
                case 11 -> {
                    BN = intScan(scanner , "please enter supplier BN" , "BN must be a number");
                    boolean shipToUs = booleanScan(scanner , "please enter true for ship to us , and false otherwise" , "you must enter true/false");
                    response response = service.updateShipToUs(BN, shipToUs);
                    if (response.isError()) System.out.println(response.getError()+ "\n");
                    else System.out.println("The operation was completed successfully\n");
                }
                case 12 -> {
                    BN = intScan(scanner , "please enter supplier BN" , "BN must be a number");
                    int itemId = intScan(scanner , "please enter itemId", "itemId must be a number");
                    double price = doubleScan(scanner , "please enter item price" , "price must be a number");
                    response response = service.updatePrice(BN, itemId, price);
                    if (response.isError()) System.out.println(response.getError()+ "\n");
                    else System.out.println("The operation was completed successfully\n");
                }
                case 13 -> mainRun(false);
                default ->{
                    System.out.println("illegal option!!!");
                    toContinue(scanner);
                }
            }
        }
    }

    private String read(Scanner scanner){
        return scanner.nextLine().toLowerCase().replaceAll("\\s", "");
    }

    private int intScan(Scanner scanner , String before , String after){
        int toReturn;
        String answer;
        while (true) {
            System.out.println(before);
            try {
                answer = read(scanner);
                toReturn = Integer.parseInt(answer);
                break;
            } catch (Exception e) {
                System.out.println(after);

            }
        }
        return toReturn;
    }

    private String stringScan(Scanner scanner , String before) {
        System.out.println(before);
        return read(scanner);
    }

    private boolean booleanScan(Scanner scanner , String before , String after) {
        boolean toReturn;
        String answer;
        while (true) {
            System.out.println(before);
            try {
                answer = read(scanner);
                toReturn = Boolean.parseBoolean(answer);
                break;
            } catch (Exception e) {
                System.out.println(after);

            }
        }
        return toReturn;
    }


    private double doubleScan(Scanner scanner , String before , String after) {
        double toReturn;
        String answer;
        while (true) {
            System.out.println(before);
            try {
                answer = read(scanner);
                toReturn = Double.parseDouble(answer);
                break;
            } catch (Exception e) {
                System.out.println(after);

            }
        }
        return toReturn;
    }

    private LocalDate dateScan(Scanner scanner , String rest){
        LocalDate toReturn;
        while (true){
            try {
                int year = intScan(scanner , "please enter the year of the " + rest , "year must be a number");
                int month = intScan(scanner , "please enter the month of the " + rest , "month must be a number");
                int day = intScan(scanner , "please enter the day of the " + rest , "day must be a number");
                toReturn = LocalDate.of(LocalDate.now().getYear(), month , day);
                break;
            }catch (Exception e){
                System.out.println("illegal values of dates");
            }
        }
        return toReturn;
    }

    private Hashtable<Integer , Integer> hashScan(Scanner scanner){
        Hashtable<Integer , Integer> items = new Hashtable<>();
        while(true){
            int itemId = intScan(scanner , "please enter itemId" , "itemId must be a number");
            int amount = intScan(scanner , "please enter the amount of the item" , "amount must be a number");
            items.put(itemId , amount);
            String toStop = stringScan(scanner , "to put more items please type more");
            if(!toStop.equals("more")) break;
        }
        return items;
    }

    private void toContinue(Scanner scanner){
        while (true) {
            System.out.println("\nto continue please use enter");
            String isEnter = read(scanner);
            if(isEnter.equals("")) break;
        }
    }

    private int menuCheck(Scanner scanner) {
        String choice;
        int toReturn;
        while (true) {
            try {
                choice = read(scanner);
                toReturn = Integer.parseInt(choice);
                break;
            }
            catch (NumberFormatException e) {
                System.out.println("illegal!\n please enter a number");
                scanner.nextLine();
            }
        }
        return toReturn;
    }

    /*private int typeScan(Scanner scanner){
        int type = -1;
        while (type == -1) {
            System.out.println("please enter the typeID");
            try {
                type = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("typeID must be a number\n");
                scanner.nextLine();
            }
        }
        return type;
    }

    private int branchIDScan(Scanner scanner){
        int branchID = -1;
        while (branchID == -1) {
            System.out.println("please enter the branchID");
            try {
                branchID = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("branchID must be a number\n");
                scanner.nextLine();
            }
        }
        return branchID;
    }

    private int itemScan(Scanner scanner){
        int itemId = -1;
        while (itemId == -1) {
            System.out.println("please enter the itemId.");
            try {
                itemId = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("itemId must be a number\n");
                scanner.nextLine();
            }
        }
        return itemId;
    }

    private int orderScan(Scanner scanner){
        int orderId = -1;
        while (orderId == -1) {
            System.out.println("please enter the orderId.");
            try {
                orderId = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("orderId must be a number\n");
                scanner.nextLine();
            }
        }
        return orderId;
    }

    private String supplierNameScan(Scanner scanner){
        boolean toContinue = true;
        String name = "";
        while (toContinue) {
            try {
                name = scanner.nextLine();
                if(!name.equals("")) toContinue = false;
                if(toContinue)System.out.println("please enter supplier name");
            }catch (Exception e) {
                System.out.println("you must enter a name\n");
            }
        }
        return name;
    }

    private int bankNumberScan(Scanner scanner){
        int bankNumber = -1;
        while(bankNumber== -1){
            System.out.println("please enter supplier bank number");
            try {
                bankNumber = scanner.nextInt();
            }catch (Exception e) {
                System.out.println("bank number must be a number\n");
                scanner.nextLine();
            }
        }
        return bankNumber;
    }

    private int branchNumberScan(Scanner scanner){
        int branchNumber = -1;
        while(branchNumber == -1){
            System.out.println("please enter supplier bank branch number");
            try {
                scanner.nextLine();
                branchNumber = scanner.nextInt();
            }catch (Exception e) {
                System.out.println("branch number must be a number\n");
                scanner.nextLine();
            }
        }
        return branchNumber;
    }

    private int bankAccountScan(Scanner scanner){
        int bankAccount = -1;
        while(bankAccount== -1){
            System.out.println("please enter supplier bank account");
            try {
                scanner.nextLine();
                bankAccount = scanner.nextInt();
            }catch (Exception e) {
                System.out.println("bank account must be a number\n");
                scanner.nextLine();
            }
        }
        return bankAccount;
    }

    private String payWayScan(Scanner scanner){
        boolean toContinue = true;
        String payWay = "";
        while (toContinue) {
            try {
                if(payWay.equals("")) payWay = scanner.nextLine();
                if(!payWay.equals("")) toContinue = false;
                if(toContinue) System.out.println("please enter supplier payWay");
            }catch (Exception e) {
                System.out.println("you must enter payWay.\n");
                toContinue = true;
            }
        }
        return payWay;
    }

    private int minimalAmountScan(Scanner scanner){
        int minimalAmount = -1;
        while(minimalAmount == -1){
            System.out.println("please enter the minimal amount");
            try {
                minimalAmount = scanner.nextInt();
            }catch (Exception e){
                System.out.println("minimal amount must be a number\n");
                scanner.nextLine();
            }
        }
        return minimalAmount;
    }

    private int discountScan(Scanner scanner){
        int discount = -1;
        while(discount == -1){
            scanner.nextLine();
            System.out.println("please enter the discount");
            try {
                discount = scanner.nextInt();
            }catch (Exception e){
                System.out.println("discount must be a number.\n");
                scanner.nextLine();
            }
        }
        return discount;
    }

    private String contactPhoneScan(Scanner scanner , String isNew) {
        boolean toContinue = true;
        String phone = "";
        while (toContinue) {
            try {
                phone = scanner.nextLine();
                if(!phone.equals("")) toContinue = false;
                if(toContinue) System.out.println("please enter the" + isNew + " supplier contact phone");
            }catch (Exception e) {
                System.out.println("you must enter a phone\n");
            }
        }
        return phone;
    }

    private String contactEmailScan(Scanner scanner , String isNew) {
        boolean toContinue = true;
        String email = "";
        while (toContinue) {
            try {
                email = scanner.nextLine();
                if(!email.equals("")) toContinue = false;
                if(toContinue) System.out.println("please enter the" + isNew +" supplier contact email");
            }catch (Exception e) {
                System.out.println("you must enter an email\n");
            }
        }
        return email;
    }

    private String contactNameScan(Scanner scanner) {
        boolean toContinue = true;
        String name = "";
        while (toContinue) {
            try {
                if(!name.equals("")) toContinue = false;
                if(toContinue) System.out.println("please enter supplier contact name");
                name = scanner.nextLine();
            } catch (Exception e) {
                System.out.println("you must enter a name\n");
            }
        }
        return name;
    }

    private boolean constantTimeScan(Scanner scanner) {
        boolean constantTime = true;
        boolean hasChanged = false;
        while(!hasChanged){
            System.out.println("please enter true for constant time or false otherwise");
            try {
                constantTime = scanner.nextBoolean();
                hasChanged = true;
            } catch (Exception e){
                System.out.println("you must enter true or false.\n");
                hasChanged = false;
                scanner.nextLine();
            }
        }
        return constantTime;
    }

    private boolean shipToUsScan(Scanner scanner) {
        boolean shipToUs = true;
        boolean hasChanged = false;
        while(!hasChanged){
            System.out.println("please enter true for ship to us or false otherwise");
            try {
                scanner.nextLine();
                shipToUs = scanner.nextBoolean();
                hasChanged = true;
            } catch (Exception e){
                System.out.println("shipToUs time must be true or false.\n");
                hasChanged = false;
                scanner.nextLine();
            }
        }
        return shipToUs;
    }*/

    /*private double priceScan(Scanner scanner){
        double price = -1;
        while(price == -1){
            System.out.println("please enter the price");
            try {
                price = scanner.nextDouble();
                scanner.nextLine();
            }catch (Exception e){
                System.out.println("price must be a number\n");
                scanner.nextLine();
            }
        }
        return price;
    }

    private int amountScan(Scanner scanner){
        int amount = -1;
        while(amount == -1){
            System.out.println("please enter the amount of item");
            try {
                amount = scanner.nextInt();
                scanner.nextLine();
            }catch (Exception e){
                System.out.println("amount must be a number.\n");
                scanner.nextLine();
            }
        }
        return amount;
    }

    private int deliverDaysScan(Scanner scanner){
        int deliverDays = -1;
        while(deliverDays == -1){
            System.out.println("please enter the deliver days of the regular Order.");
            try {
                deliverDays = scanner.nextInt();
                scanner.nextLine();
            }catch (Exception e){
                System.out.println("deliver days must be a number.\n");
                scanner.nextLine();
            }
        }
        return deliverDays;
    }*/

    /*private String itemNameScanner(Scanner scanner){
        boolean toContinue = true;
        String name = "";
        while (toContinue) {
            try {
                if(name.equals("")) name = scanner.nextLine();
                if(!name.equals("")) toContinue = false;
                if(toContinue)  System.out.println("please enter item name");
            }catch (Exception e) {
                System.out.println("you must enter a name\n");
            }
        }
        return name;
    }*/
}

