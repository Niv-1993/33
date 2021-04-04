package PresentationLayer;

import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import  BussniesLayer.facade.SupplierService;
import BussniesLayer.facade.Tresponse;
import BussniesLayer.facade.outObjects.*;
import BussniesLayer.facade.response;

public class PresentationCL{

    SupplierService service = new SupplierService();

    public void mainRun(){
        Scanner scanner = new Scanner(System.in);
        String[] mainMenueArray = {"method of showing." , "method of adding." , "method of removing." , "method of updating" ,"end program" };
        int option;
        while(true){
            System.out.println("please select an option: \n");
            for(int i = 1 ; i <= mainMenueArray.length ; i++){
                System.out.println(i + ") " + mainMenueArray[i-1] + "\n");
            }
            option = scanner.nextInt();
            switch (option){
                case 1: showingMethods();
                case 2: addingMethods();
                case 3: removingMethods();
                case 4: updatingMethods();
                case 5:{
                    System.out.println("PROGRAM DONE.\n");
                    System.exit(0);
                }
                default: System.out.println("illegal option!!!\n");
            }
        }
    }

    private void showingMethods(){
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        int option;
        String[] showingMethodArray = {"show Supplier","show SupplierBN","show All Suppliers","show All Items Of Supplier","show All Items",
                                       "show Order Of Supplier","show All Orders Of Supplier","show Total Amount","show Deliver Time",
                                       "show Quantity Document","show Supplier Agreement,back to the main menu"};
        System.out.println("please select the showing method: \n");
        while (true) {
            for (int i = 1; i <= showingMethodArray.length; i++) {
                System.out.println(i + ") " + showingMethodArray[i - 1] + "\n");
            }
            option = scanner.nextInt();
            int BN;
            switch (option) {
                case 1: {
                    BN = BNScan(scanner);
                    Tresponse<SupplierCard> response = service.showSupplier(BN);
                    if (!response.isError()) System.out.println(response.getError());
                    else{
                        System.out.println(response.getOutObject().toString());
                        Tresponse<List<Item>> items = service.showAllItemsOfSupplier(BN);
                        if(items.isError()) System.out.println(items.getError());
                        else {
                            List<Item> responsesItem = items.getOutObject();
                            for (Item item : responsesItem) {
                                System.out.println(item.toString());
                            }
                            Tresponse<List<Order>> orders = service.showAllOrdersOfSupplier(BN);
                            if(orders.isError()) System.out.println(orders.getError());
                            else {
                                List<Order> responsesOrders = orders.getOutObject();
                                for (Order order : responsesOrders) {
                                    System.out.println(order.toString());
                                }
                            }
                        }
                    }
                }
                case 2: {
                    String name = supplierNameScan(scanner);
                    Tresponse<SupplierCard> response = service.showSupplierBN(name);
                    if (!response.isError()) System.out.println(response.getError());
                    else System.out.println(response.getOutObject().toStringId());
                }
                case 3: {
                    Tresponse<List<SupplierCard>> responsesList = service.showAllSuppliers();
                    if (!responsesList.isError()) System.out.println(responsesList.getError());
                    else {
                        List<SupplierCard> responses = responsesList.getOutObject();
                        for (SupplierCard supplierCard : responses) {
                            System.out.println(supplierCard.toString());
                        }
                    }
                }
                case 4: {
                    BN = BNScan(scanner);
                    Tresponse<List<Item>> responsesList = service.showAllItemsOfSupplier(BN);
                    if (!responsesList.isError()) System.out.println(responsesList.getError());
                    else {
                        List<Item> responses = responsesList.getOutObject();
                        for (Item item : responses) {
                            System.out.println(item.toString());
                        }
                    }
                }
                case 5:{
                     Tresponse<List<Item>> responsesList = service.showAllItems();
                    if (!responsesList.isError()) System.out.println(responsesList.getError());
                    else {
                        List<Item> responses = responsesList.getOutObject();
                        for (Item item : responses) {
                            System.out.println(item.toString());
                        }
                    }
                }
                case 6:{
                    BN = BNScan(scanner);
                    int orderId = orderScan(scanner);
                    Tresponse<Order> response = service.showOrderOfSupplier(BN , orderId);
                    if (!response.isError()) System.out.println(response.getError());
                    else{
                        System.out.println(response.getOutObject().toString());
                        Tresponse<List<Item>> items = service.showAllItemsOfOrder(BN , orderId);
                        if(items.isError()) System.out.println(items.getError());
                        else{
                            List<Item> responseItem = items.getOutObject();
                            for(Item item : responseItem){
                                System.out.println(item.toString());
                            }
                        }
                    }

                }
                case 7:{
                    BN = BNScan(scanner);
                    Tresponse<List<Order>> responsesList = service.showAllOrdersOfSupplier(BN);
                    if (!responsesList.isError()) System.out.println(responsesList.getError());
                    else {
                        List<Order> responses = responsesList.getOutObject();
                        for (Order order : responses) {
                            System.out.println(order.toString());
                            Tresponse<List<Item>> items = service.showAllItemsOfOrder(BN , Integer.parseInt(order.toStringId()));
                            if(items.isError()) System.out.println(items.getError());
                            else{
                                List<Item> responseItem = items.getOutObject();
                                for(Item item : responseItem){
                                    System.out.println(item.toString());
                                }
                            }
                        }
                    }
                }
                case 8:{
                    BN = BNScan(scanner);
                    int orderId = orderScan(scanner);
                    Tresponse<Order> response = service.showTotalAmount(BN , orderId);
                    if (!response.isError()) System.out.println(response.getError());
                    else System.out.println(response.getOutObject().toStringTotalAmount());
                }
                case 9:{
                    BN = BNScan(scanner);
                    int orderId = orderScan(scanner);
                   Tresponse<Order> response = service.showDeliverTime(BN , orderId);
                    if (!response.isError()) System.out.println(response.getError());
                    else System.out.println(response.getOutObject().toStringDeliverTime());
                }
                case 10:{
                    BN = BNScan(scanner);
                    int itemId = itemScan(scanner);
                    Tresponse<QuantityDocument> response = service.showQuantityDocument(BN , itemId);
                    if (!response.isError()) System.out.println(response.getError());
                    else System.out.println(response.getOutObject().toString());
                }
                case 11:{
                    BN = BNScan(scanner);
                    Tresponse<SupplierAgreement> response = service.showSupplierAgreement(BN);
                    if (!response.isError()) System.out.println(response.getError());
                    else System.out.println(response.getOutObject().toString());
                }
                default: System.out.println("illegal option!!!\n");
            }
        }
    }

    private void addingMethods(){
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        int option;
        String[] showingMethodArray = {"add supplier","add Contact Phone","add Contact Email","add Item","add Order",
                                       "add Item To Order","add Quantity Document","add Supplier Agreement","back to the main menu"};
        System.out.println("please select the showing method: \n");
        while (true) {
            for (int i = 1; i <= showingMethodArray.length; i++) {
                System.out.println(i + ") " + showingMethodArray[i - 1] + "\n");
            }
            option = scanner.nextInt();
            int BN;
            switch (option){
                case 1: {
                    String name = supplierNameScan(scanner);
                    int bankAccount = bankAccountScan(scanner);
                    String payWay = payWayScan(scanner);
                    response response = service.addSupplier(name , bankAccount , payWay);
                    if(response.isError()) System.out.println(response.getError());
                }
                case 2: {
                    BN = BNScan(scanner);
                    String phone = contactNameScan(scanner);
                    String name = contactNameScan(scanner);
                    response response = service.addContactPhone(BN , phone , name);
                    if(response.isError()) System.out.println(response.getError());
                }
                case 3: {
                    BN = BNScan(scanner);
                    String email = contactEmailScan(scanner);
                    String name = contactNameScan(scanner);
                    response response = service.addContactEmail(BN , email , name);
                    if(response.isError()) System.out.println(response.getError());
                }
                case 4: {
                    BN = BNScan(scanner);
                    String category = categoryScan(scanner);
                    double price = priceScan(scanner);
                    Tresponse<Item> response = service.addItem(BN , category , price);
                    if(response.isError()) System.out.println(response.getError());
                    else System.out.println("ItemId is: " + response.getOutObject().toStringId());
                }
                case 5:{
                    BN = BNScan(scanner);
                    Tresponse<Order> response = service.addOrder(BN);
                    if(response.isError()) System.out.println(response.getError());
                    else System.out.println("orderId is: " + response.getOutObject().toStringId());
                }
                case 6:{
                    BN =BNScan(scanner);
                    int orderId = orderScan(scanner);
                    int itemId = itemScan(scanner);
                    response response = service.addItemToOrder(BN , orderId , itemId);
                    if(response.isError()) System.out.println(response.getError());
                }
                case 7:{
                    BN = BNScan(scanner);
                    int itemId = itemScan(scanner);
                    int minimalAmount = minimalAmountScan(scanner);
                    int discount = discountScan(scanner);
                    response response = service.addQuantityDocument(BN , itemId , minimalAmount, discount);
                    if(response.isError()) System.out.println(response.getError());
                }
                case 8:{
                    BN = BNScan(scanner);
                    int minimalAmount = minimalAmountScan(scanner);
                    int discount = discountScan(scanner);
                    boolean constantTime = constantTimeScan(scanner);
                    boolean shipToUs = shipToUsScan(scanner);
                    response response = service.addSupplierAgreement(BN , minimalAmount , discount , constantTime , shipToUs);
                    if(response.isError()) System.out.println(response.getError());
                }
                case 9:{
                    scanner.close();
                    mainRun();
                }
                default: System.out.println("illegal option!!!\n");
            }
            System.out.println("The operation was completed successfully\n");
        }
    }

    private void removingMethods(){
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        int option;
        String[] removeMethodArray = {"remove Supplier","remove Contact Phone","remove Contact Email","remove Item",
                                      "remove Item From Supplier","remove Quantity Document","back to the main menu"};
        System.out.println("please select the showing method: \n");
        while (true) {
            for (int i = 1; i <= removeMethodArray.length; i++) {
                System.out.println(i + ") " + removeMethodArray[i - 1] + "\n");
            }
            option = scanner.nextInt();
            int BN;
            switch (option) {
                case 1: {
                    BN = BNScan(scanner);
                    response response = service.removeSupplier(BN);
                    if(response.isError()) System.out.println(response.getError());
                }
                case 2: {
                    BN = BNScan(scanner);
                    String phone = contactPhoneScan(scanner);
                    response response = service.removeContactPhone(BN, phone);
                    if(response.isError()) System.out.println(response.getError());
                }
                case 3: {
                    BN = BNScan(scanner);
                    String email =contactEmailScan(scanner);
                    response response = service.removeContactEmail(BN, email);
                    if(response.isError()) System.out.println(response.getError());
                }
                case 4: {
                    int itemId = itemScan(scanner);
                    response response = service.removeItem(itemId);
                    if(response.isError()) System.out.println(response.getError());
                }
                case 5: {
                    BN = BNScan(scanner);
                    int itemId = itemScan(scanner);
                    response response = service.removeItemFromSupplier(BN, itemId);
                    if(response.isError()) System.out.println(response.getError());
                }
                case 6: {
                    BN = BNScan(scanner);
                    int itemId = itemScan(scanner);
                    response response = service.removeQuantityDocument(BN, itemId);
                    if(response.isError()) System.out.println(response.getError());
                }
                case 7: {
                    scanner.close();
                    mainRun();
                }
                default: System.out.println("illegal option!!!\n");
            }
            System.out.println("The operation was completed successfully\n");
        }
    }

    private void updatingMethods() {
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        int option;
        int BN;
        String[] updateMethodArray = {"update Supplier PayWay", "update Supplier BankAccount", "update Contact Phone", "update Contact Email",
                "update Deliver Time", "update Minimal Amount Of Quantity Document", "update Discount Of Quantity Document",
                "update Minimal Amount Of Supplier Agreement", "update Discount Of Supplier Agreement",
                "update Constant Time", "update Ship To Us", "update Price", "back to the main menu"};
        System.out.println("please select the showing method: \n");
        while (true) {
            for (int i = 1; i <= updateMethodArray.length; i++) {
                System.out.println(i + ") " + updateMethodArray[i - 1] + "\n");
            }
            option = scanner.nextInt();
            switch (option) {
                case 1: {
                    BN = BNScan(scanner);
                    String payWay = payWayScan(scanner);
                    response response = service.updateSupplierPayWay(BN, payWay);
                    if (response.isError()) System.out.println(response.getError());
                }
                case 2: {
                    BN = BNScan(scanner);
                    int bankAccount = bankAccountScan(scanner);
                    response response = service.updateSupplierBankAccount(BN, bankAccount);
                    if (response.isError()) System.out.println(response.getError());
                }
                case 3: {
                    BN = BNScan(scanner);
                    String phone = contactPhoneScan(scanner);
                    response response = service.updateContactPhone(BN, phone);
                    if (response.isError()) System.out.println(response.getError());
                }
                case 4: {
                    BN = BNScan(scanner);
                    String email = contactEmailScan(scanner);
                    response response = service.updateContactEmail(BN, email);
                    if (response.isError()) System.out.println(response.getError());
                }
                case 5: {
                    BN = BNScan(scanner);
                    int orderId = orderScan(scanner);
                    Date date = dateScan(scanner);
                    response response = service.updateDeliverTime(BN, orderId, date);
                    if (response.isError()) System.out.println(response.getError());
                }
                case 6: {
                    BN = BNScan(scanner);
                    int itemId = itemScan(scanner);
                    int minimalAmount = minimalAmountScan(scanner);
                    response response = service.updateMinimalAmountOfQD(BN, itemId, minimalAmount);
                    if (response.isError()) System.out.println(response.getError());
                }
                case 7: {
                    BN = BNScan(scanner);
                    int itemId = itemScan(scanner);
                    int discount = discountScan(scanner);
                    response response = service.updateDiscountOfQD(BN, itemId, discount);
                    if (response.isError()) System.out.println(response.getError());
                }
                case 8: {
                    BN = BNScan(scanner);
                    int minimalAmount = minimalAmountScan(scanner);
                    response response = service.updateMinimalAmountOfSA(BN, minimalAmount);
                    if (response.isError()) System.out.println(response.getError());
                }
                case 9: {
                    BN = BNScan(scanner);
                    int discount = discountScan(scanner);
                    response response = service.updateDiscountOfSA(BN, discount);
                    if (response.isError()) System.out.println(response.getError());
                }
                case 10: {
                    BN = BNScan(scanner);
                    boolean constantTime = constantTimeScan(scanner);
                    response response = service.updateConstantTime(BN, constantTime);
                    if (response.isError()) System.out.println(response.getError());
                }
                case 11: {
                    BN = BNScan(scanner);
                    boolean shipToUs = shipToUsScan(scanner);
                    response response = service.updateShipToUs(BN, shipToUs);
                    if (response.isError()) System.out.println(response.getError());
                }
                case 12: {
                    BN = BNScan(scanner);
                    int itemId = itemScan(scanner);
                    double price = priceScan(scanner);
                    response response = service.updatePrice(BN, itemId, price);
                    if (response.isError()) System.out.println(response.getError());
                }
                case 13: {
                    scanner.close();
                    mainRun();
                }
                default: System.out.println("illegal option!!!\n");
            }
            System.out.println("The operation was completed successfully\n");
        }
    }

    private int BNScan(Scanner scanner){
        int BN = -1;
        while (BN == -1) {
            System.out.println("please enter the SupplierBN\n");
            try {
                BN = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("supplier BN must be a number\n");
            }
        }
        return BN;
    }

    private int itemScan(Scanner scanner){
        int itemId = -1;
        while (itemId == -1) {
            System.out.println("please enter the itemId.\n");
            try {
                itemId = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("itemId must be a number\n");
            }
        }
        return itemId;
    }

    private int orderScan(Scanner scanner){
        int orderId = -1;
        while (orderId == -1) {
            System.out.println("please enter the orderId.\n");
            try {
                orderId = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("orderId must be a number\n");
            }
        }
        return orderId;
    }

    private String supplierNameScan(Scanner scanner){
        String name = "";
        while(name.equals("")){
            System.out.println("please enter supplier name\n");
            try {
                name = scanner.nextLine();
            }catch (Exception e) {
                System.out.println("you must enter a name\n");
            }
        }
        return name;
    }

    private int bankAccountScan(Scanner scanner){
        int bankAccount = -1;
        while(bankAccount== -1){
            System.out.println("please enter supplier bank account\n");
            try {
               bankAccount = scanner.nextInt();
            }catch (Exception e) {
                System.out.println("bank account must be a number\n");
            }
        }
        return bankAccount;
    }

    private String payWayScan(Scanner scanner){
        String payWay = "";
        while(payWay.equals("")){
            System.out.println("please enter supplier payWay\n");
            try {
                payWay = scanner.nextLine();
            }catch (Exception e){
                System.out.println("you must enter payWay.\n");
                break;
            }
        }
        return payWay;
    }

    private int minimalAmountScan(Scanner scanner){
        int minimalAmount = -1;
        while(minimalAmount == -1){
            System.out.println("please enter the minimal amount\n");
            try {
                minimalAmount = scanner.nextInt();
            }catch (Exception e){
                System.out.println("minimal amount must be a number\n");
            }
        }
        return minimalAmount;
    }

    private int discountScan(Scanner scanner){
        int discount = -1;
        while(discount == -1){
            System.out.println("please enter the discount\n");
            try {
                discount = scanner.nextInt();
            }catch (Exception e){
                System.out.println("discount must be a number.\n");
                break;
            }
        }
        return discount;
    }

    private String contactPhoneScan(Scanner scanner) {
        String phone = "";
        while(phone.equals("")){
            System.out.println("please enter supplier contact phone\n");
            try {
                phone = scanner.nextLine();
            }catch (Exception e){
                System.out.println("you must enter a phone\n");
            }
        }
        return phone;
    }

    private String contactEmailScan(Scanner scanner) {
        String email = "";
        while(email.equals("")){
            System.out.println("please enter supplier contact email\n");
            try {
                email = scanner.nextLine();
            }catch (Exception e){
                System.out.println("you must enter an email\n");
            }
        }
        return email;
    }

    private String contactNameScan(Scanner scanner) {
        String name = "";
        while(name.equals("")){
            System.out.println("please enter supplier contact name\n");
            try {
                name = scanner.nextLine();
            }catch (Exception e){
                System.out.println("you must enter a name\n");
                break;
            }
        }
        return name;
    }

    private boolean constantTimeScan(Scanner scanner) {
        boolean constantTime = true;
        boolean hasChanged = false;
        while(!hasChanged){
            System.out.println("please enter true for constant time or false otherwise\n");
            try {
                constantTime = scanner.nextBoolean();
                hasChanged = true;
            } catch (Exception e){
                System.out.println("you must enter true or false.\n");
                hasChanged = false;
            }
        }
        return constantTime;
    }

    private boolean shipToUsScan(Scanner scanner) {
        boolean shipToUs = true;
        boolean hasChanged = false;
        while(!hasChanged){
            System.out.println("please enter true for ship to us or false otherwise\n");
            try {
                shipToUs = scanner.nextBoolean();
                hasChanged = true;
            } catch (Exception e){
                System.out.println("shipToUs time must be true or false.\n");
                hasChanged = false;
            }
        }
        return shipToUs;
    }

    private double priceScan(Scanner scanner){
        double price = -1;
        while(price == -1){
            System.out.println("please enter the price\n");
            try {
                price = scanner.nextDouble();
            }catch (Exception e){
                System.out.println("price must be a number\n");
            }
        }
        return price;
    }

    private String categoryScan(Scanner scanner){
        String category = "";
        while(category.equals("")){
            System.out.println("please enter item category\n");
            try {
                category = scanner.nextLine();
            }catch (Exception e){
                System.out.println("you must enter a category\n");
                break;
            }
        }
        return category;
    }

    private Date dateScan(Scanner scanner){
        boolean hasChanged = false;
        String tryDate = "";
        Date date = new Date();
        while (!hasChanged) {
            System.out.println("please enter the new date in format of dd/mm/yy hour AM/PM\n");
            try {
                tryDate = scanner.nextLine();
                hasChanged = true;
            } catch (Exception e) {
                System.out.println("you must enter a date.\n");
                hasChanged = false;
            }
            if(hasChanged) {
                try {
                    date = new Date(tryDate);
                    hasChanged = true;
                } catch (Exception e) {
                    System.out.println("you must enter a date in format of dd/mm/yy hour AM/PM\n");
                    hasChanged = false;
                }
            }
        }
        return date;
    }
}


