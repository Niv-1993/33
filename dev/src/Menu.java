import ServiceLayer.Controller;

import java.util.Scanner;

public class Menu {
    Scanner sc;
    private int option;
    private final int numOfEndProgramOp = 2;
    private final int numOfOptions = 2;
    private int numOfSteps = 7;
    private Controller controller;
    public Menu(Scanner sc){
        controller = Controller.initial();

        this.sc = sc;
    }

    public int chooseOption(){
        System.out.println("press 1 to see all available Transportations ");
        System.out.println("press 2 to create a new Transportation");
        option = chooseOp(numOfOptions);
        return option;
    }
    public int chooseAddOption(){
        System.out.println("press 1 to set the date and the living time of the transportation");
        System.out.println("press 2 to add a truck");
        System.out.println("press 3 to add a driver");
        System.out.println("press 4 t add a supplier");
        System.out.println("press 5 to add branches");
        System.out.println("press 6 to add items to a branches");
        System.out.println("press 7 to set the truck weight");
        return chooseOp(numOfOptions);
    }
    public void printAllDrivers(){
        System.out.println(controller.getAllDrivers().toString());
    }
    public void printAllTucks(){
        System.out.println(controller.getAllTrucks().toString());
    }
    public void printAllBranches(){
        System.out.println(controller.getAllBranches().toString());
    }
    public void printAllSuppliers(){
        System.out.println(controller.getAllSuppliers().toString());
    }
    public void PrintAllItems(){
        System.out.println(controller.getAllItems());
    }
    public void printAllTransportations(){
        System.out.println(controller.getAllTransportations());
    }

    public boolean endOfProgram(){
        System.out.println("to continue press 1, to end press 2");
        if(chooseOp(numOfEndProgramOp) == 1){
            return true;
        }else {
            return false;
        }
    }
    private int chooseOp(int con){
        boolean validInput = false;
        int userOption = -1;
        while (!validInput) {
            userOption = sc.nextInt();
            if((userOption <= con) && (userOption > 0)) {
                validInput = true;
            }else {
                System.out.println("your choose without bounds");
            }
        }
        return userOption;
    }
    private boolean setSDriver(){
        System.out.println(controller.getAllDrivers().toString());
        boolean isDone = false;
        while (!isDone){
            if(!controller.setDriverOnTransportation(null)){
                //we choose maybe he want to change driver or end the transportation..

            }else {
                isDone = true;
            }

        }
        return isDone;
    }
}
