import ServiceLayer.Controller;
import ServiceLayer.Objects.*;
import enums.Area;
import enums.Pair;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    Scanner sc;
    private int option;
    private int subOption;
    private final int numOfOptions = 7;
    private final Area[] areas={Area.Sout,Area.North,Area.Central };
    private final Controller controller;
    private boolean finish;
    public Menu(Scanner sc){
        controller =new Controller();
        this.sc = sc;
        this.option=0;
        subOption=0;
        finish=false;
    }

    /**
     *The starting choice of the user if to keep run the system or shut it off.
     */
    public void chooseOption(){
        System.out.println("press 1 to see all Transportations, press 2 to create a new Transportation");
        option = chooseOp(numOfOptions);
    }

    /**
     * prints menu and received the user's choice for which area is the new transportation.
     * @param t : the presentation's transportation object to show the user and to contact the business layer.
     */
    private void chooseArea(TransportationServiceDTO t){
        System.out.println("Please chose an Area");
        for (int i=0; i<areas.length;i++) {
            System.out.println((i+1)+") "+areas[i]);
        }
        int area=chooseOp(areas.length)-1;
        Area chosen=areas[area];
        t.setArea(chosen);
        controller.setTransportationArea(t);
    }

    /**
     *Asks and received the user's input for the wanted trans hour.
     * @param tran : the presentation's transportation object to show the user and to contact the business layer.
     */
    private void chooseTime(TransportationServiceDTO tran){
        boolean success=false;
        while (!success) {
            try {
                System.out.println("Please chose time for transportation");
                String tim = sc.next();
                LocalTime time = LocalTime.parse(tim);
                tran.setLeavingTime(time);
                controller.setTransportationLeavingTime(tran);
                success = true;
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Asks and received the user's input for the wanted trans date.
     * @param tran: the presentation's transportation object to show the user and to contact the business layer.
     */
    private void chooseDate(TransportationServiceDTO tran){
        boolean success=false;
        while (!success) {
            try {
                System.out.println("Please chose a date for transportation");
                String tim = sc.next();
                LocalDate date=LocalDate.parse(tim);
                tran.setDate(date);
                controller.setTransportationDate(tran);
                success = true;
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * This is the add transportation menu method. the whole user's input and dialog runs/called from here.
     */
    private void chooseAddOption(){
        finish=false;
        TransportationServiceDTO newTrans=controller.createNewTransportation();
        chooseArea(newTrans);
        chooseDate(newTrans);
        chooseTime(newTrans);
        while (!finish) {
            System.out.println("press 1 to add a truck");
            System.out.println("press 2 to add a driver");
            System.out.println("press 3 to add a suppliers and items");
            System.out.println("press 4 to add branches and items to a branches");
            System.out.println("press 5 to set the truck weight");
            System.out.println("press 6 to submit your transportation");
            System.out.println("press 0 to cancel transportation");
            subOption = chooseOp(numOfOptions);
            switch (subOption) {
                case 1 -> chooseTruck(newTrans);
                case 2 -> chooseDriver(newTrans);
                case 3 -> chooseSupplier(newTrans);
                case 4 -> chooseBranch(newTrans);
                case 5 -> chooseWeight(newTrans);
                case 6 -> submit(newTrans);
                case 0-> {Delete(); return;}
            }
            System.out.println(newTrans);
        }
    }

    /**
     * Delete new transportation
     */
    private void Delete() {

        try {
            controller.delete();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     *Asks and received the user's input for the wanted trans weight.
     * @param t: the presentation's transportation object to show the user and to contact the business layer.
     */
    private void chooseWeight(TransportationServiceDTO t){
        try {
            System.out.println("please enter transportation total weight:");
            int chose = sc.nextInt();
            t.setWeight(chose);
            controller.setTransportationWeight(t);
        }
        catch (Exception e){
            t.setTruck(null);
        }
    }

    /**
     *Method that called by the addTransportation menu. try to close the new trans with the all new data.
     * If one field is empty the method will no allow.
     * If finished, back to first menu.
     * @param t: the presentation's transportation object to show the user and to contact the business layer
     */
    private void submit(TransportationServiceDTO t){
        try {

            controller.setTransportation(t);
            finish=true;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     *Menu of suppliers and their items.
     * The method ask for a flow of suppliers until -1 number.
     * After each input, ask in a loop for item and quantity until -2 number.
     * @param t: the presentation's transportation object to show the user and to contact the business layer
     */
    private void chooseSupplier(TransportationServiceDTO t) {
        try {
            System.out.println("please select suppliers and items from the lists below, press -1 to finish:");
            printAllSuppliers();
            PrintAllItems();
            int chose;
            HashMap<SupplierServiceDTO, List<Pair<ItemServiceDTO,Integer>>> suppliers=new HashMap<>();
            do {
                System.out.println("select supplier: ");
                chose = sc.nextInt();
                if(chose==-1)
                    break;
                System.out.println("choose items from this supplier and quantity. press -2 to finish.");
                List<Pair<ItemServiceDTO, Integer>> lis =new LinkedList<>();
                long id;
                int num;
                do{
                    System.out.println("enter item and quantity: ");
                    id= sc.nextLong();
                    if(id==-2)
                        break;
                    num= sc.nextInt();

                    lis.add(new Pair<>(controller.getItem(id),num));
                }
                while(true);
                suppliers.put(controller.getSupplier(chose), lis);
            }
            while(true);
            t.setSuppliers(suppliers);
            controller.setSuppliersToTransportation(t);
        }
        catch (Exception e){
            t.setSuppliers(null);
            System.out.println("Error: "+e.getMessage());
        }
    }

    /**
     *Menu of suppliers and their items.
     * The method ask for a flow of branches until -1 number.
     * After each input, ask in a loop for item and quantity until -2 number.
     * @param t: the presentation's transportation object to show the user and to contact the business layer
     */
    private void chooseBranch(TransportationServiceDTO t) {
        try {
            System.out.println("please select branches and items from the lists below, press -1 to finish:");
            printAllBranches();
            PrintAllItems();
            int chose;
            HashMap<BranchServiceDTO, List<Pair<ItemServiceDTO,Integer>>> branches=new HashMap<>();
            do {
                System.out.println("select Branch: ");
                chose = sc.nextInt();
                if(chose==-1)
                    break;
                System.out.println("choose items to this branch and quantity. press -2 to finish.");
                List<Pair<ItemServiceDTO, Integer>> lis =new LinkedList<>();
                long id;
                int num;
                do{
                    System.out.println("enter item and quantity: ");
                    id= sc.nextLong();
                    if(id==-2)
                        break;
                    num= sc.nextInt();

                    lis.add(new Pair<>(controller.getItem(id),num));
                }
                while(true);
                branches.put(controller.getBranch(chose), lis);
            }
            while(true);
            t.setDeliveryItems(branches);
            controller.setDeliveryItemsToTransportation(t);
        }
        catch (Exception e){
            t.setDeliveryItems(null);
            System.out.println("Error: "+e.getMessage());
        }
    }

    /**
     *Asks and received from user the driver for the transportation by id number.
     * If the addition could not complete, the transportation's driver's details will not be changed.
     * @param t: the presentation's transportation object to show the user and to contact the business layer
     */
    private void chooseDriver(TransportationServiceDTO t) {
        try {
            System.out.println("please select driver id from the trucks list below:");
            printAllDrivers();
            long chose = sc.nextLong();
            t.setDriver(controller.getDriver(chose));
            controller.setDriverOnTransportation(t);
        }
        catch (Exception e){
            t.setDriver(null);
        }
    }

    /**
     *Asks and received from user the truck for the transportation by id number.
     * If the addition could not complete, the transportation's truck's details will not be changed.
     * @param t: the presentation's transportation object to show the user and to contact the business layer
     */
    private void chooseTruck(TransportationServiceDTO t) {

        try {
            System.out.println("please select truck id from the trucks list below:");
            printAllTucks();
            long chose = sc.nextLong();
            t.setTruck(controller.getTruck(chose));
            controller.setTruckOnTransportation(t);
        }
        catch (Exception e){
            t.setTruck(null);
        }
    }

    /**
     *Prints to the user all the available drivers in the database
     */
    public void printAllDrivers(){
       List<DriverServiceDTO> lis=controller.getAllDrivers();
        for (DriverServiceDTO dri:lis) { System.out.println(dri); }
    }

    /**
     *Prints to the user all the available trucks in the database
     */
    public void printAllTucks(){
        List<TruckServiceDTO> lis=controller.getAllTrucks();
        for (TruckServiceDTO tru:lis) { System.out.println(tru); }
    }

    /**
     *Prints to the user all the available branches in the database
     */
    public void printAllBranches(){
        List<BranchServiceDTO> bra=controller.getAllBranches();
        for (BranchServiceDTO tru:bra) { System.out.println(tru); }
    }

    /**
     *Prints to the user all the available suppliers in the database
     */
    public void printAllSuppliers(){
        List<SupplierServiceDTO> sup=controller.getAllSuppliers();
        for (SupplierServiceDTO tru:sup) { System.out.println(tru); }

    }

    /**
     *Prints to the user all the available items in the database
     */
    public void PrintAllItems(){
        List<ItemServiceDTO> sup=controller.getAllItems();
        for (ItemServiceDTO tru:sup) { System.out.println(tru); }

    }

    /**
     *Prints to the user all the available transportations in the database
     * Used for the transportations printing option in the menu.
     */
    public void printAllTransportations(){
        List<TransportationServiceDTO> sup=controller.getAllTransportations();
        for (TransportationServiceDTO tru:sup) { System.out.println(tru); }
    }

    /**
     *The starting menu of the system.
     * runs by the main of the project.
     * By user's input it keep running the system or shut it off.
     * @return : if to keep run the program or terminate it
     */
    public boolean endOfProgram(){
        System.out.println("to continue press 1, to end press 2");
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
            userOption = sc.nextInt();
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

        if(option==2)
            chooseAddOption();
        else
            printAllTransportations();
    }
}
