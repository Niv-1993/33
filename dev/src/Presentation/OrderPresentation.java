package Presentation;

import BusinessLayer.StockBusiness.Fcade.StorageService;
import BusinessLayer.StockBusiness.Fcade.outObjects.NeededReport;
import BusinessLayer.SupplierBusiness.facade.OrderService;
import BusinessLayer.SupplierBusiness.facade.Tresponse;
import BusinessLayer.SupplierBusiness.facade.outObjects.*;
import BusinessLayer.SupplierBusiness.facade.response;
import org.apache.log4j.Logger;

import java.io.InputStreamReader;
import java.util.*;

public class OrderPresentation {

    private final OrderService service;
    private final int branchId;
    final static Logger log = Logger.getLogger(Presentation.SuppliersPresentation.class);

    public OrderPresentation(int branchId) {
        service = new OrderService(branchId);
        this.branchId = branchId;
    }

    public void loadData() {
        try {
            service.LoadData();
        } catch (Exception e) {
            System.out.println("there is no data here");
        }
    }

    public OrderService getService() {
        return service;
    }

    //need to tell Alex to add setStockService that get as parameter OrderPresentation.

    /*public void setStockService(StorageService service) {
        this.service.setStockService(service);
    }*/

    public void newData() {
        service.newData();
    }

    public void mainRun(boolean firstTime) {
        Scanner scanner = new Scanner(System.in);
        String[] mainMenuArray = {"showing methods", "adding methods", "removing methods", "back to choice menu", "END PROGRAM"};
        int option = -1;
        while (true) {
            System.out.println("please select an option: ");
            for (int i = 1; i <= mainMenuArray.length; i++) {
                System.out.println(i + ") " + mainMenuArray[i - 1]);
            }
            option = menuCheck(scanner);
            switch (option) {
                case 1 -> {
                    showingMethods();
                    break;
                }
                case 2 -> {
                    addingMethods();
                    break;
                }
                case 3 -> {
                    removingMethods();
                    break;
                }
                case 4 -> {
                    return;
                }
                case 5 -> {
                    System.exit(0);
                }
                default -> System.out.println("illegal option!!!");
            }
        }
    }

    private void showingMethods() {
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        int option = -1;
        String[] showingMethodArray = {"show Order Of Supplier", "show All Orders Of Supplier", "show Total Amount",
                                       "back to the main menu", "END PROGRAM"};
        System.out.println("please select the showing method: ");
        while (true) {
            for (int i = 1; i <= showingMethodArray.length; i++) {
                System.out.println(i + ") " + showingMethodArray[i - 1]);
            }
            option = menuCheck(scanner);
            switch (option) {
                case 1 -> {
                    int orderId = intScan(scanner, "please enter orderId", "orderId must be a number");
                    service.showTotalAmount(branchId, orderId);
                    Tresponse<Order> response = service.showOrderOfSupplier(branchId , orderId);
                    if (response.isError()) System.out.println(response.getError() + "\n");
                    else {
                        System.out.println(response.getOutObject().toString());
                        System.out.println("\tship to us: " + service.ShipToUs(branchId , orderId));
                        Tresponse<List<Item>> items = service.showAllItemsOfOrder(branchId , orderId);
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
                case 2 -> {
                    Tresponse<List<Order>> responsesList = service.showAllOrdersOfSupplier(branchId);
                    if (responsesList.isError()) System.out.println(responsesList.getError() + "\n");
                    else {
                        List<Order> responses = responsesList.getOutObject();
                        for (Order order : responses) {
                            System.out.println(order.toString());
                            Tresponse<List<Item>> items = service.showAllItemsOfOrder(branchId, Integer.parseInt(order.toStringId()));
                            if (items.isError()) System.out.println(items.getError() + "\n");
                            else System.out.println("\tship to us: " + service.ShipToUs( branchId , Integer.parseInt(order.toStringId()) ));
                            List<Item> responseItem = items.getOutObject();
                            for (Item item : responseItem) {
                                System.out.println(item.toString(order.toStringAmount(item.toStringId())));
                            }
                        }
                    }
                    toContinue(scanner);
                }
                case 3 -> {
                    int orderId = intScan(scanner, "please enter orderId", "orderId must be a number");
                    Tresponse<Order> response = service.showTotalAmount(branchId, orderId);
                    if (response.isError()) System.out.println(response.getError() + "\n");
                    else System.out.println("total amount is: " + response.getOutObject().toStringTotalAmount());
                    toContinue(scanner);
                }
                case 4 -> {
                    return;
                }
                case 5 -> {
                    System.exit(0);
                }
                default -> {
                    System.out.println("illegal option!!!");
                    toContinue(scanner);
                }
            }
        }
    }

    private void addingMethods() {
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        int option = -1;
        String[] showingMethodArray = {"add Regular Order", "add needed report", "add Item To Order", "back to the main menu", "END PROGRAM"};
        System.out.println("please select the showing method: ");
        while (true) {
            for (int i = 1; i <= showingMethodArray.length; i++) {
                System.out.println(i + ") " + showingMethodArray[i - 1]);
            }
            option = menuCheck(scanner);
            int BN;
            switch (option) {
                case 1 -> {
                    int branchID = intScan(scanner, "please enter branchId for deliver", "branchId must be a number");
                    Hashtable<Integer, Integer> items = hashScan(scanner);
                    Tresponse<Order> response = service.addRegularOrder(branchId , branchID, items);
                    if (response.isError()) System.out.println(response.getError() + "\n");
                    else System.out.println("orderId is: " + response.getOutObject().toStringId() + "\n");
                    toContinue(scanner);
                }
                case 2 -> {
                    Tresponse<NeededReport> tresponse = service.getNeededItems();
                    if (tresponse.isError()) System.out.println(tresponse.getError() + "\n");
                    else {
                        Dictionary<Integer, Integer> dictionary = tresponse.getOutObject().get_list();
                        for (Integer i : Collections.list(dictionary.keys())) {
                            String ans = stringScan(scanner, "pres yes to if you want to order the item: " + i + "otherwise press enter.");
                            if (ans.equals("yes")) {
                                response response = service.addNeededOrder(i, dictionary.get(i), tresponse.getOutObject().getStoreID());
                                if (response.isError()) System.out.println(response.getError() + "\n");
                                else System.out.println("The operation was completed successfully\n");
                            }
                        }
                    }
                }
                case 3 -> {
                    int orderId = intScan(scanner, "please enter orderId", "orderId must be a number");
                    int itemId = intScan(scanner, "please enter itemId", "itemId must be a number");
                    int amount = intScan(scanner, "please enter amount of the item", "amount must be a number");
                    response response = service.addItemToOrder(branchId, orderId, itemId, amount);
                    if (response.isError()) System.out.println(response.getError() + "\n");
                    else System.out.println("The operation was completed successfully\n");
                }
                case 4 -> {
                    return;
                }
                case 5 -> {
                    System.exit(0);
                }
                default -> {
                    System.out.println("illegal option!!!\n");
                    toContinue(scanner);
                }
            }
        }
    }

    private void removingMethods() {
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        int option = -1;
        String[] removeMethodArray = {"remove item from regular order", "remove amount of items from regular order", "back to the main menu", "END PROGRAM"};
        System.out.println("please select the showing method: ");
        while (true) {
            for (int i = 1; i <= removeMethodArray.length; i++) {
                System.out.println(i + ") " + removeMethodArray[i - 1]);
            }
            option = menuCheck(scanner);
            int BN;
            switch (option) {
                case 1 -> {
                    int orderId = intScan(scanner, "please enter orderId", "orderId must be a number");
                    int itemId = intScan(scanner, "please enter itemId", "itemId must be a number");
                    response response = service.removeItemFromRegularOrder(branchId, orderId, itemId);
                    if (response.isError()) System.out.println(response.getError() + "\n");
                    else System.out.println("The operation was completed successfully\n");
                }
                case 2 -> {
                    int orderId = intScan(scanner, "please enter orderId", "orderId must be a number");
                    int itemId = intScan(scanner, "please enter itemId", "itemId must be a number");
                    int amount = intScan(scanner, "please enter the amount of the item", "amount must be a number");
                    response response = service.removeAmountItemFromRegularOrder(branchId, orderId, itemId, amount);
                    if (response.isError()) System.out.println(response.getError() + "\n");
                    else System.out.println("The operation was completed successfully\n");
                }
                case 3 -> {
                    return;
                }
                case 4 -> {
                    System.exit(0);
                }
                default -> {
                    System.out.println("illegal option!!!");
                    toContinue(scanner);
                }
            }
        }
    }

    private String read(Scanner scanner) {
        return scanner.nextLine().toLowerCase().replaceAll("\\s", "");
    }

    private int intScan(Scanner scanner, String before, String after) {
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

    private String stringScan(Scanner scanner, String before) {
        System.out.println(before);
        return read(scanner);
    }

    private Hashtable<Integer, Integer> hashScan(Scanner scanner) {
        Hashtable<Integer, Integer> items = new Hashtable<>();
        while (true) {
            int itemId = intScan(scanner, "please enter itemId", "itemId must be a number");
            int amount = intScan(scanner, "please enter the amount of the item", "amount must be a number");
            items.put(itemId, amount);
            String toStop = stringScan(scanner, "to put more items please type more");
            if (!toStop.equals("more")) break;
        }
        return items;
    }

    private void toContinue(Scanner scanner) {
        while (true) {
            System.out.println("\nto continue please use enter");
            String isEnter = read(scanner);
            if (isEnter.equals("")) break;
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
            } catch (NumberFormatException e) {
                System.out.println("illegal!\n please enter a number");
                scanner.nextLine();
            }
        }
        return toReturn;
    }
}

