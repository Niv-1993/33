import ServiceLayer.Controller;
import ServiceLayer.Objects.BranchServiceDTO;
import ServiceLayer.Objects.ItemServiceDTO;
import ServiceLayer.Objects.SupplierServiceDTO;
import ServiceLayer.Objects.TransportationServiceDTO;
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
        controller = Controller.initial();
        this.sc = sc;
        this.option=0;
        subOption=0;
        finish=false;
    }

    /**
     *
     */
    public void chooseOption(){
        System.out.println("press 1 to see all available Transportations ");
        System.out.println("press 2 to create a new Transportation");
        option = chooseOp(numOfOptions);
    }
    private void chooseArea(TransportationServiceDTO t){
        System.out.println("Please chose an Area");
        for (int i=0; i<areas.length;i++) {
            System.out.println((i+1)+") "+areas[i]);
        }
        int area=chooseOp(areas.length)-1;
        System.out.println("area num: "+ area);
        Area chosen=areas[area];
        t.setArea(chosen);
        controller.setTransportationArea(t);
    }
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
    private void chooseAddOption(){
        finish=false;
        TransportationServiceDTO newTrans=controller.createNewTransportation();
        chooseArea(newTrans);
        chooseDate(newTrans);
        chooseTime(newTrans);
        while (!finish) {
            System.out.println("press 1 to add a truck");
            System.out.println("press 2 to add a driver");
            System.out.println("press 3 t add a supplier");
            System.out.println("press 4 to add branches and items to a branches");
            System.out.println("press 5 to set the truck weight");
            System.out.println("press 6 to submit your transportation");
            subOption = chooseOp(numOfOptions);
            switch (subOption) {
                case 1 -> chooseTruck(newTrans);
                case 2 -> chooseDriver(newTrans);
                case 3 -> chooseSupplier(newTrans);
                case 4 -> chooseBranch(newTrans);
                case 5 -> chooseWeight(newTrans);
                case 6 -> submit(newTrans);
            }
            System.out.println(newTrans);
        }
    }
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
    private void submit(TransportationServiceDTO t){
        try {

            controller.setTransportation(t);
            finish=true;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
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
        }
    }
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
        }
    }
    private void chooseDriver(TransportationServiceDTO t) {
        try {
            System.out.println("please select driver id from the trucks list below:");
            printAllDrivers();
            long chose = sc.nextLong();
            t.setDriver(controller.getDriver(chose));
            controller.setDriverOnTransportation(t);
        }
        catch (Exception e){
            t.setTruck(null);
        }
    }

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
        int numOfEndProgramOp = 2;
        return chooseOp(numOfEndProgramOp) == 1;
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

    public void nextStep() {

        if(option==2)
            chooseAddOption();
        else
            printAllTransportations();
    }
}
