package PresentationLayer;

import java.io.InputStreamReader;
import java.util.Date;
import java.util.Scanner;
import  BussniesLayer.facade.SupplierService;
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
            switch (option) {
                case 1: {
                    System.out.println("please enter the SupplierBN\n");
                    response response = service.removeSupplier(scanner.nextInt());
                    if(response.isError()) System.out.println(response.getError());
                }
                case 2: {
                    System.out.println("please enter the SupplierBN\n");
                    int BN = scanner.nextInt();
                    System.out.println("please enter the required phone\n");
                    String phone = scanner.nextLine();
                    response response = service.removeContactPhone(BN, phone);
                    if(response.isError()) System.out.println(response.getError());
                }
                case 3: {
                    System.out.println("please enter the SupplierBN\n");
                    int BN = scanner.nextInt();
                    System.out.println("please enter the required email\n");
                    String phone = scanner.nextLine();
                    response response = service.removeContactPhone(BN, phone);
                    if(response.isError()) System.out.println(response.getError());
                }
                case 4: {
                    System.out.println("please enter the itemId\n");
                    response response = service.removeItem(scanner.nextInt());
                    if(response.isError()) System.out.println(response.getError());
                }
                case 5: {
                    System.out.println("please enter the SupplierBN\n");
                    int BN = scanner.nextInt();
                    System.out.println("please enter the itemId\n");
                    int itemId = scanner.nextInt();
                    response response = service.removeItemFromSupplier(BN, itemId);
                    if(response.isError()) System.out.println(response.getError());
                }
                case 6: {
                    System.out.println("please enter the SupplierBN\n");
                    int BN = scanner.nextInt();
                    System.out.println("please enter the itemId\n");
                    int itemId = scanner.nextInt();
                    response response = service.removeQuantityDocument(BN, itemId);
                    if(response.isError()) System.out.println(response.getError());
                }
                case 7: {
                    mainRun();
                }
            }
            System.out.println("\n");
        }
    }

    private void updatingMethods() {
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        int option;
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
                    int BN = scanner.nextInt();
                    System.out.println("please enter the new PayWay\n");
                    String payWay = scanner.nextLine();
                    response response = service.updateSupplierPayWay(BN, payWay);
                    if (response.isError()) System.out.println(response.getError());
                }
                case 2: {
                    System.out.println("please enter the SupplierBN\n");
                    int BN = scanner.nextInt();
                    System.out.println("please enter the new bank account\n");
                    int bankAccount = scanner.nextInt();
                    response response = service.updateSupplierBankAccount(BN, bankAccount);
                    if (response.isError()) System.out.println(response.getError());
                }
                case 3: {
                    System.out.println("please enter the SupplierBN\n");
                    int BN = scanner.nextInt();
                    System.out.println("please enter the new phone\n");
                    String phone = scanner.nextLine();
                    response response = service.updateContactPhone(BN, phone);
                    if (response.isError()) System.out.println(response.getError());
                }
                case 4: {
                    System.out.println("please enter the SupplierBN\n");
                    int BN = scanner.nextInt();
                    System.out.println("please enter the new email\n");
                    String email = scanner.nextLine();
                    response response = service.updateContactPhone(BN, email);
                    if (response.isError()) System.out.println(response.getError());
                }
                case 5: {
                    System.out.println("please enter the SupplierBN\n");
                    int BN = scanner.nextInt();
                    System.out.println("please enter the new orderId\n");
                    int orderId = scanner.nextInt();
                    System.out.println("please enter the new date in format of dd/mm/yy hour AM/PM\n");
                    Date date = new Date(scanner.nextLine());
                    response response = service.updateDeliverTime(BN, orderId, date);
                    if (response.isError()) System.out.println(response.getError());
                }
                case 6: {
                    System.out.println("please enter the SupplierBN\n");
                    int BN = scanner.nextInt();
                    System.out.println("please enter the new itemId\n");
                    int itemId = scanner.nextInt();
                    System.out.println("please enter the new minimal amount\n");
                    int minimalAmount = scanner.nextInt();
                    response response = service.updateMinimalAmountOfQD(BN, itemId, minimalAmount);
                    if (response.isError()) System.out.println(response.getError());
                }
                case 7: {
                    System.out.println("please enter the SupplierBN\n");
                    int BN = scanner.nextInt();
                    System.out.println("please enter the new itemId\n");
                    int itemId = scanner.nextInt();
                    System.out.println("please enter the new discount\n");
                    int discount = scanner.nextInt();
                    response response = service.updateDiscountOfQD(BN, itemId, discount);
                    if (response.isError()) System.out.println(response.getError());
                }
                case 8: {
                    System.out.println("please enter the SupplierBN\n");
                    int BN = scanner.nextInt();
                    System.out.println("please enter the new minimal amount\n");
                    int minimalAmount = scanner.nextInt();
                    response response = service.updateMinimalAmountOfSA(BN, minimalAmount);
                    if (response.isError()) System.out.println(response.getError());
                }
                case 9: {
                    System.out.println("please enter the SupplierBN\n");
                    int BN = scanner.nextInt();
                    System.out.println("please enter the new discount\n");
                    int discount = scanner.nextInt();
                    response response = service.updateDiscountOfSA(BN, discount);
                    if (response.isError()) System.out.println(response.getError());
                }
                case 10: {
                    System.out.println("please enter the SupplierBN\n");
                    int BN = scanner.nextInt();
                    System.out.println("please enter true for constant time or false otherwise\n");
                    boolean constantTime = scanner.nextBoolean();
                    response response = service.updateConstantTime(BN, constantTime);
                    if (response.isError()) System.out.println(response.getError());
                }
                case 11: {
                    System.out.println("please enter the SupplierBN\n");
                    int BN = scanner.nextInt();
                    System.out.println("please enter true for ship to us or false otherwise\n");
                    boolean shipToUs = scanner.nextBoolean();
                    response response = service.updateShipToUs(BN, shipToUs);
                    if (response.isError()) System.out.println(response.getError());
                }
                case 12: {
                    System.out.println("please enter the SupplierBN\n");
                    int BN = scanner.nextInt();
                    System.out.println("please enter the itemId\n");
                    int itemId = scanner.nextInt();
                    System.out.println("please enter the new price\n");
                    double price = scanner.nextDouble();
                    response response = service.updatePrice(BN, itemId, price);
                    if (response.isError()) System.out.println(response.getError());
                }
                case 13: {
                    mainRun();
                }
            }
            System.out.println("\n");
        }
    }

}
