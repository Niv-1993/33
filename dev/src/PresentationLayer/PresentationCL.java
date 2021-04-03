package PresentationLayer;

import java.io.InputStreamReader;
import java.util.Date;
import java.util.Scanner;

import  BussniesLayer.facade.SupplierService;
import BussniesLayer.facade.Tresponse;
import BussniesLayer.facade.outObjects.Item;
import BussniesLayer.facade.outObjects.Order;
import BussniesLayer.facade.response;

public class PresentationCL{

    SupplierService service = new SupplierService();

    public void mainRun(){
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        String[] mainMenueArray = {"method of showing." , "method of adding." , "method of removing." , "method of updating" ,"end program" };
        int option;
        while(true){
            System.out.println("please select an option: \n");
            for(int i = 1 ; i <= mainMenueArray.length ; i++){
                System.out.println(i + ") " + mainMenueArray[i-1] + "\n");
            }
            option = scanner.nextInt();
            if(option == 1) showingMethods();
            else if(option == 2) addingMethods();
            else if(option == 3) removingMethods();
            else if(option == 4) updatingMethods();
            else if(option == 5){
                System.out.println("PROGRAM DONE.\n");
                System.exit(0);
            }
            else System.out.println("illegal option!!!\n");
        }
    }

    private void showingMethods(){
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        int option;
        String[] showingMethodArray = {"show Supplier","show SupplierBN","show All Suppliers","show All Items Of Supplier","show All Items",
                                       "show Order Of Supplier","show All Orders Of Supplier","show Total Amount","show Deliver Time",
                                       "show Quantity Document","show Supplier Agreement,back to the main menu"};
        System.out.println("please select the showing method: \n");
        for(int i = 1 ; i <= showingMethodArray.length ; i++){
            System.out.println(i + ") " + showingMethodArray[i-1] + "\n");
        }
        option = scanner.nextInt();
        if(option == showingMethodArray.length -1) mainRun();
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
                    System.out.println("please enter supplier name\n");
                    String name;
                    try {
                        name = scanner.nextLine();
                    }catch (Exception e){
                        System.out.println("you must enter a name\n");
                        break;
                    }
                    System.out.println("please enter supplier bank account\n");
                    int bankAccount;
                    try {
                        bankAccount = scanner.nextInt();
                    }catch (Exception e){
                        System.out.println("bank account must be a number\n");
                        break;
                    }
                    System.out.println("please enter supplier payWay\n");
                    String payWay;
                    try {
                        payWay = scanner.nextLine();
                    }catch (Exception e){
                        System.out.println("you must enter pay eay.\n");
                        break;
                    }
                    response response = service.addSupplier(name , bankAccount , payWay);
                    if(response.isError()) System.out.println(response.getError());
                }
                case 2: {
                    System.out.println("please enter supplier BN\n");
                    try {
                        BN = scanner.nextInt();
                    }catch (Exception e){
                        System.out.println("supplier BN must be a number\n");
                        break;
                    }
                    System.out.println("please enter supplier contact phone\n");
                    String phone;
                    try {
                        phone = scanner.nextLine();
                    }catch (Exception e){
                        System.out.println("you must enter a phone\n");
                        break;
                    }
                    System.out.println("please enter supplier contact name\n");
                    String name;
                    try {
                        name = scanner.nextLine();
                    }catch (Exception e){
                        System.out.println("you must enter a name\n");
                        break;
                    }
                    response response = service.addContactPhone(BN , phone , name);
                    if(response.isError()) System.out.println(response.getError());
                }
                case 3: {
                    System.out.println("please enter supplier BN\n");
                    try {
                        BN = scanner.nextInt();
                    }catch (Exception e){
                        System.out.println("supplier BN must be a number\n");
                        break;
                    }
                    System.out.println("please enter supplier contact email\n");
                    String email;
                    try {
                        email = scanner.nextLine();
                    }catch (Exception e){
                        System.out.println("you must enter a email\n");
                        break;
                    }
                    System.out.println("please enter supplier contact name\n");
                    String name;
                    try {
                        name = scanner.nextLine();
                    }catch (Exception e){
                        System.out.println("you must enter a name\n");
                        break;
                    }
                    response response = service.addContactPhone(BN , email , name);
                    if(response.isError()) System.out.println(response.getError());
                }
                case 4: {
                    System.out.println("please enter supplier BN\n");
                    try {
                        BN = scanner.nextInt();
                    }catch (Exception e){
                        System.out.println("supplier BN must be a number\n");
                        break;
                    }
                    System.out.println("please enter item category\n");
                    String category;
                    try {
                        category = scanner.nextLine();
                    }catch (Exception e){
                        System.out.println("you must enter a category\n");
                        break;
                    }
                    System.out.println("please enter item price\n");
                    double price;
                    try {
                        price = scanner.nextDouble();
                    }catch (Exception e){
                        System.out.println("price must be a number\n");
                        break;
                    }
                    Tresponse<Item> response = service.addItem(BN , category , price);
                    if(response.isError()) System.out.println(response.getError());
                    else System.out.println("ItemId is: " + response.getOutObject().toStringId());
                }
                case 5:{
                    System.out.println("please enter supplier BN\n");
                    try {
                        BN = scanner.nextInt();
                    }catch (Exception e){
                        System.out.println("supplier BN must be a number\n");
                        break;
                    }
                    Tresponse<Order> response = service.addOrder(BN);
                    if(response.isError()) System.out.println(response.getError());
                    else System.out.println("orderId is: " + response.getOutObject().toStringId());
                }
                case 6:{
                    System.out.println("please enter supplier BN\n");
                    try {
                        BN = scanner.nextInt();
                    }catch (Exception e){
                        System.out.println("supplier BN must be a number\n");
                        break;
                    }
                    int orderId;
                    try {
                        orderId = scanner.nextInt();
                    }catch (Exception e){
                        System.out.println("orderId must be a number\n");
                        break;
                    }
                    int itemId;
                    try {
                        itemId = scanner.nextInt();
                    }catch (Exception e){
                        System.out.println("itemId must be a number\n");
                        break;
                    }
                    response response = service.addItemToOrder(BN , orderId , itemId);
                    if(response.isError()) System.out.println(response.getError());
                }
                case 7:{
                    System.out.println("please enter supplier BN\n");
                    try {
                        BN = scanner.nextInt();
                    }catch (Exception e){
                        System.out.println("supplier BN must be a number\n");
                        break;
                    }
                    int itemId;
                    try {
                        itemId = scanner.nextInt();
                    }catch (Exception e){
                        System.out.println("itemId must be a number\n");
                        break;
                    }
                    int minimalAmount;
                    try {
                        minimalAmount = scanner.nextInt();
                    }catch (Exception e){
                        System.out.println("minimal Amount must be a number\n");
                        break;
                    }
                    int discount;
                    try {
                        discount = scanner.nextInt();
                    }catch (Exception e){
                        System.out.println("discount must be a number\n");
                        break;
                    }
                    response response = service.addQuantityDocument(BN , itemId , minimalAmount, discount);
                    if(response.isError()) System.out.println(response.getError());
                }
                case 8:{
                    System.out.println("please enter supplier BN\n");
                    try {
                        BN = scanner.nextInt();
                    }catch (Exception e){
                        System.out.println("supplier BN must be a number\n");
                        break;
                    }
                    int minimalAmount;
                    try {
                        minimalAmount = scanner.nextInt();
                    }catch (Exception e){
                        System.out.println("minimal Amount must be a number\n");
                        break;
                    }
                    int discount;
                    try {
                        discount = scanner.nextInt();
                    }catch (Exception e){
                        System.out.println("discount must be a number\n");
                        break;
                    }
                    System.out.println("please enter true if the supplier comes in constant time or false otherwise\n");
                    boolean constantTime;
                    try {
                        constantTime = scanner.nextBoolean();
                    }catch (Exception e){
                        System.out.println("you must enter true/false.\n");
                        break;
                    }
                    System.out.println("please enter true if the supplier ship to us or false otherwise\n");
                    boolean shipToUs;
                    try {
                        shipToUs = scanner.nextBoolean();
                    }catch (Exception e){
                        System.out.println("you must enter true/false.\n");
                        break;
                    }
                    response response = service.addSupplierAgreement(BN , minimalAmount , discount , constantTime , shipToUs);
                    if(response.isError()) System.out.println(response.getError());
                }
                case 9:{
                    mainRun();
                }
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
                    System.out.println("please enter the SupplierBN\n");
                    try {
                        BN = scanner.nextInt();
                    }catch (Exception e){
                        System.out.println("supplier BN must a number\n");
                        break;
                    }
                    response response = service.removeSupplier(BN);
                    if(response.isError()) System.out.println(response.getError());
                }
                case 2: {
                    System.out.println("please enter the SupplierBN\n");
                    try {
                        BN = scanner.nextInt();
                    }catch (Exception e){
                        System.out.println("supplier BN must a number\n");
                        break;
                    }
                    System.out.println("please enter the required phone\n");
                    String phone;
                    try {
                        phone = scanner.nextLine();
                    }catch (Exception e){
                        System.out.println("you must enter a phone number\n");
                        break;
                    }
                    response response = service.removeContactPhone(BN, phone);
                    if(response.isError()) System.out.println(response.getError());
                }
                case 3: {
                    System.out.println("please enter the SupplierBN\n");
                    try {
                        BN = scanner.nextInt();
                    }catch (Exception e){
                        System.out.println("supplier BN must a number\n");
                        break;
                    }
                    System.out.println("please enter the required email\n");
                    String email;
                    try {
                        email = scanner.nextLine();
                    }catch (Exception e){
                        System.out.println("you must enter am email address\n");
                        break;
                    }
                    response response = service.removeContactPhone(BN, email);
                    if(response.isError()) System.out.println(response.getError());
                }
                case 4: {
                    System.out.println("please enter the itemId\n");
                    int itemId;
                    try {
                        itemId = scanner.nextInt();
                    }catch (Exception e){
                        System.out.println("itemId must be a number.\n");
                        break;
                    }
                    response response = service.removeItem(itemId);
                    if(response.isError()) System.out.println(response.getError());
                }
                case 5: {
                    System.out.println("please enter the SupplierBN\n");
                    try {
                        BN = scanner.nextInt();
                    }catch (Exception e){
                        System.out.println("supplier BN must a number\n");
                        break;
                    }
                    System.out.println("please enter the itemId\n");
                    int itemId;
                    try {
                        itemId = scanner.nextInt();
                    }catch (Exception e){
                        System.out.println("itemId must be a number.\n");
                        break;
                    }
                    response response = service.removeItemFromSupplier(BN, itemId);
                    if(response.isError()) System.out.println(response.getError());
                }
                case 6: {
                    System.out.println("please enter the SupplierBN\n");
                    try {
                        BN = scanner.nextInt();
                    }catch (Exception e){
                        System.out.println("supplier BN must a number\n");
                        break;
                    }
                    int itemId;
                    try {
                        itemId = scanner.nextInt();
                    }catch (Exception e){
                        System.out.println("itemId must be a number.\n");
                        break;
                    }
                    response response = service.removeQuantityDocument(BN, itemId);
                    if(response.isError()) System.out.println(response.getError());
                }
                case 7: {
                    mainRun();
                }
            }
            System.out.println("The operation was completed successfully\n");
        }
    }

    private void updatingMethods() {
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        int option;
        int BN = -1;
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
                    System.out.println("please enter the SupplierBN\n");
                    try {
                            BN = scanner.nextInt();
                        }catch (Exception e){
                        System.out.println("supplier BN must a number\n");
                            break;
                        }
                        System.out.println("please enter the new PayWay\n");
                        String payWay;
                        try {
                            payWay = scanner.nextLine();
                        }catch (Exception e){
                            System.out.println("you must enter a phone number\n");
                            break;
                        }
                        response response = service.updateSupplierPayWay(BN, payWay);
                        if (response.isError()) System.out.println(response.getError());
                }
                case 2: {
                    System.out.println("please enter the SupplierBN\n");
                    int bankAccount = -1;
                    try {
                        BN = scanner.nextInt();
                    }catch (Exception e){
                        System.out.println("supplier BN must a number\n");
                        break;
                    }
                    System.out.println("please enter the new bank account\n");
                    try {
                        bankAccount = scanner.nextInt();
                    }catch (Exception e){
                        System.out.println("bank account must be a number\n");
                        break;
                    }
                    response response = service.updateSupplierBankAccount(BN, bankAccount);
                    if (response.isError()) System.out.println(response.getError());
                }
                case 3: {
                    System.out.println("please enter the SupplierBN\n");
                    try {
                        BN = scanner.nextInt();
                    }catch (Exception e){
                        System.out.println("supplier BN must a number\n");
                        break;
                    }
                    System.out.println("please enter the new phone\n");
                    String phone;
                    try {
                        phone = scanner.nextLine();
                    }catch (Exception e){
                        System.out.println("you must enter phone number\n");
                        break;
                    }
                    response response = service.updateContactPhone(BN, phone);
                    if (response.isError()) System.out.println(response.getError());
                }
                case 4: {
                    System.out.println("please enter the SupplierBN\n");
                    try {
                        BN = scanner.nextInt();
                    }catch (Exception e){
                        System.out.println("supplier BN must a number\n");
                        break;
                    }
                    System.out.println("please enter the new phone\n");
                    String email;
                    try {
                        email = scanner.nextLine();
                    }catch (Exception e){
                        System.out.println("you must enter email address.\n");
                        break;
                    }
                    response response = service.updateContactPhone(BN, email);
                    if (response.isError()) System.out.println(response.getError());
                }
                case 5: {
                    System.out.println("please enter the SupplierBN\n");
                    try {
                        BN = scanner.nextInt();
                    }catch (Exception e){
                        System.out.println("supplier BN must be a number\n");
                        break;
                    }
                    int orderId;
                    try {
                        orderId = scanner.nextInt();
                    }catch (Exception e){
                        System.out.println("orderId must be a number.\n");
                        break;
                    }
                    System.out.println("please enter the new date in format of dd/mm/yy hour AM/PM\n");
                    String tryDate;
                    try {
                        tryDate = scanner.nextLine();
                    }catch (Exception e){
                        System.out.println("you must enter a date.\n");
                        break;
                    }
                    Date date;
                    try {
                        date = new Date(tryDate);
                    }catch (Exception e){
                        System.out.println("you must enter a date in format of dd/mm/yy hour AM/PM\n");
                        break;
                    }
                    response response = service.updateDeliverTime(BN, orderId, date);
                    if (response.isError()) System.out.println(response.getError());
                }
                case 6: {
                    System.out.println("please enter the SupplierBN\n");
                    try {
                        BN = scanner.nextInt();
                    }catch (Exception e){
                        System.out.println("supplier BN must a number\n");
                        break;
                    }
                    System.out.println("please enter the new itemId\n");
                    int itemId;
                    try {
                        itemId = scanner.nextInt();
                    }catch (Exception e){
                        System.out.println("itemId must be a number.\n");
                        break;
                    }
                    System.out.println("please enter the new minimal amount\n");
                    int minimalAmount;
                    try {
                         minimalAmount = scanner.nextInt();
                    }catch (Exception e){
                        System.out.println("minimal amount must be a number\n");
                        break;
                    }
                    response response = service.updateMinimalAmountOfQD(BN, itemId, minimalAmount);
                    if (response.isError()) System.out.println(response.getError());
                }
                case 7: {
                    System.out.println("please enter the SupplierBN\n");
                    try {
                        BN = scanner.nextInt();
                    }catch (Exception e){
                        System.out.println("supplier BN must a number\n");
                        break;
                    }
                    int itemId;
                    try {
                        itemId = scanner.nextInt();
                    }catch (Exception e){
                        System.out.println("itemId must be a number.\n");
                        break;
                    }
                    System.out.println("please enter the new discount\n");
                    int discount;
                    try {
                        discount = scanner.nextInt();
                    }catch (Exception e){
                        System.out.println("discount must be a number.\n");
                        break;
                    }
                    response response = service.updateDiscountOfQD(BN, itemId, discount);
                    if (response.isError()) System.out.println(response.getError());
                }
                case 8: {
                    System.out.println("please enter the SupplierBN\n");
                    try {
                        BN = scanner.nextInt();
                    }catch (Exception e){
                        System.out.println("supplier BN must a number\n");
                        break;
                    }
                    System.out.println("please enter the new minimal amount\n");
                    int minimalAmount;
                    try {
                        minimalAmount = scanner.nextInt();
                    }catch (Exception e){
                        System.out.println("minimalAmount must be a number.\n");
                        break;
                    }
                    response response = service.updateMinimalAmountOfSA(BN, minimalAmount);
                    if (response.isError()) System.out.println(response.getError());
                }
                case 9: {
                    System.out.println("please enter the SupplierBN\n");
                    try {
                        BN = scanner.nextInt();
                    }catch (Exception e){
                        System.out.println("supplier BN must a number\n");
                        break;
                    }
                    System.out.println("please enter the new discount\n");
                    int discount;
                    try {
                        discount = scanner.nextInt();
                    }catch (Exception e){
                        System.out.println("discount must be a number.\n");
                        break;
                    }
                    response response = service.updateDiscountOfSA(BN, discount);
                    if (response.isError()) System.out.println(response.getError());
                }
                case 10: {
                    System.out.println("please enter the SupplierBN\n");
                    try {
                        BN = scanner.nextInt();
                    }catch (Exception e){
                        System.out.println("supplier BN must a number\n");
                        break;
                    }
                    System.out.println("please enter true for constant time or false otherwise\n");
                    boolean constantTime;
                    try {
                        constantTime = scanner.nextBoolean();
                    } catch (Exception e){
                        System.out.println("constant time must be true or false.\n");
                        break;
                    }
                    response response = service.updateConstantTime(BN, constantTime);
                    if (response.isError()) System.out.println(response.getError());
                }
                case 11: {
                    System.out.println("please enter the SupplierBN\n");
                    try {
                        BN = scanner.nextInt();
                    }catch (Exception e){
                        System.out.println("supplier BN must be a number\n");
                        break;
                    }
                    System.out.println("please enter true for ship to us or false otherwise\n");
                    boolean shipToUs;
                    try {
                        shipToUs = scanner.nextBoolean();
                    } catch (Exception e){
                        System.out.println("shipToUs time must be true or false.\n");
                        break;
                    }
                    response response = service.updateShipToUs(BN, shipToUs);
                    if (response.isError()) System.out.println(response.getError());
                }
                case 12: {
                    System.out.println("itemId must be a number.\n");
                    try {
                        BN = scanner.nextInt();
                    }catch (Exception e){
                        System.out.println("supplier BN must a number\n");
                        break;
                    }
                    int itemId;
                    try {
                        itemId = scanner.nextInt();
                    }catch (Exception e){
                        System.out.println("itemId must be a number.\n");
                        break;
                    }
                    System.out.println("please enter the new price\n");
                    double price;
                    try {
                        price = scanner.nextDouble();
                    }catch (Exception e){
                        System.out.println("price must be a number\n");
                        break;
                    }
                    response response = service.updatePrice(BN, itemId, price);
                    if (response.isError()) System.out.println(response.getError());
                }
                case 13: {
                    mainRun();
                }
            }
            System.out.println("The operation was completed successfully\n");
        }
    }

}
