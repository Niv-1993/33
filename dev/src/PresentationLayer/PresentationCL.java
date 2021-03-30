package PresentationLayer;

import java.io.InputStreamReader;
import java.util.Scanner;

public class PresentationCL{

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
        for(int i = 1 ; i <= removeMethodArray.length ; i++){
            System.out.println(i + ") " + removeMethodArray[i-1] + "\n");
        }
        option = scanner.nextInt();
        if(option == removeMethodArray.length -1) mainRun();
    }

    private void updatingMethods(){
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        int option;
        String[] updateMethodArray = {"update Supplier PayWay","update Supplier BankAccount","update Contact Phone","update Contact Email",
                                      "update Deliver Time","update Minimal Amount Of  Quantity Document" , "update Discount Of Quantity Document",
                                      "update Minimal Amount Of Supplier Agreement","update Discount Of Supplier Agreement",
                                      "update Constant Time","update Ship To Us","update Price" , "back to the main menu"};
        System.out.println("please select the showing method: \n");
        for(int i = 1 ; i <= updateMethodArray.length ; i++){
            System.out.println(i + ") " + updateMethodArray[i-1] + "\n");
        }
        option = scanner.nextInt();
        if(option == updateMethodArray.length -1) mainRun();
    }


}
