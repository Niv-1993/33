package Presentation.Menu;

import Business.ApplicationFacade.outObjects.TransportationServiceDTO;
import Business.Type.Area;
import Presentation.Controllers;
import Presentation.TransportationController;

import java.util.List;
import java.util.Scanner;

public class TransportationMenu extends Menu{
    private int option;
    private int subOption;
    private final int numOfOptions = 7;
    private final Area[] areas={Area.South,Area.North,Area.Center };
    private final TransportationController transportationController;
    private boolean finish;


    public TransportationMenu(Controllers r , Scanner input){
        super(r,input);
        transportationController =new TransportationController(r.getMc());
        this.option=0;
        subOption=0;
        finish=false;
    }

    /**
     * Main function of the menu.
     */
    @Override
    public void show(){
        while(endOfProgram()){
            chooseOption();
            nextStep();
        }
    }


    /**
     *The starting choice of the user if to keep run the system or shut it off.
     */
    public void chooseOption(){
        System.out.print("1) See all Transportations.\n2) Delete Transportation.\nOption: ");
        option = chooseOp(numOfOptions);
        System.out.println();
    }

    /**
     * Delete transportation
     */
    private void delete() {
        System.out.println("\n*************************************************");
        System.out.println("************ Delete a Transportation ************");
        System.out.println("*************************************************\n");
        System.out.println("Please enter order id to delete.");
        long id= input.nextLong();
        try {
            transportationController.delete(id);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }



    /**
     *Prints to the user all the available transportations in the database
     * Used for the transportations printing option in the menu.
     */
    public void printAllTransportations(){
        System.out.println("\n*************************************************");
        System.out.println("************ Printing Business.Transportation ************");
        System.out.println("*************************************************\n");
        List<TransportationServiceDTO> sup= transportationController.getAllTransportations();
        for (TransportationServiceDTO tru:sup) { System.out.println(tru); }
    }

    /**
     *The starting menu of the system.
     * runs by the main of the project.
     * By user's input it keep running the system or shut it off.
     * @return : if to keep run the program or terminate it
     */
    public boolean endOfProgram(){
        System.out.println("\n****************** Transportation menu *******************");
        System.out.println("Hello.\nPlease choose an option:");
        System.out.print("1)Continue\n2)Exit\nOption: ");
        int numOfEndProgramOp = 2;
        return chooseOp(numOfEndProgramOp) == 1;
    }

    /**
     *Method to receive an input from the user with boundary limit.
     * @param con : the num of options the user can type. For boundary check.
     * @return : the choice of the user.
     */
    private int chooseOp(int con){
        boolean validInput = false;
        int userOption = -1;
        while (!validInput) {
            userOption = input.nextInt();
            if((userOption <= con) && (userOption >= 0)) {
                validInput = true;
            }else {
                System.out.println("your choose without bounds");
            }
        }
        return userOption;
    }

    /**
     *Method to direct the menu by the user's choice in the starting menu.
     */
    public void nextStep() {
        if(option==1)
        printAllTransportations();
        else
            delete();
    }
}
