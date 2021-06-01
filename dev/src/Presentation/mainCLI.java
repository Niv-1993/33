package Presentation;

import DAL.Mapper;
import org.apache.log4j.Logger;

import java.util.Scanner;

public class mainCLI {
    SuppliersPresentation suppliersPresentation;
    OrderPresentation orderPresentation;
    StockCLI stockCLI;
    Scanner scan=new Scanner(System.in);
    final static Logger log= Logger.getLogger(StockCLI.class);
    String lastDB="test.db";

    String read(){
        return scan.nextLine().toLowerCase().replaceAll("\\s", "");
    }

    public mainCLI(){
        suppliersPresentation = new SuppliersPresentation(1);
        orderPresentation = new OrderPresentation(1);
        stockCLI = new StockCLI();
        stockCLI.setStockService(suppliersPresentation.getService());
        suppliersPresentation.setStockService(stockCLI.getService());
    }

    private int menuCheck(Scanner scanner) {
        String choice;
        int toReturn;
        while (true) {
            try {
                choice = read();
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

    public void start(boolean firstTime){
        String in;
        if(firstTime) {
            String[] load = {"use pre-initialized database", "use empty database"};
            while (true) {
                System.out.println("please select an option: ");
                for (int i = 1; i <= load.length; i++) {
                    System.out.println(i + ") " + load[i - 1]);
                }
                int n = -1;
                n = menuCheck(scan);
                switch (n) {
                    case 1 -> {
                        Mapper.getMap(lastDB);
                        suppliersPresentation.loadData(); // make a test that chek if it was load correctlly
                        orderPresentation.loadData();
                        break;
                    }
                    case 2 -> { /* Mapper.getMap("newDB.db"); */
                        lastDB="empty.db";
                        Mapper.getMap(lastDB);
                        suppliersPresentation.newData();
                        orderPresentation.loadData();
                        break;
                    }
                    default -> System.out.println("illegal option!!!");
                }
                if(n==1 || n==2) break;
            }
        }
        while (true){
            System.out.println("please enter:\n1 for Suppliers\n2 for Stock\n3 to exit");
            in = read();
            try {
                if(Integer.parseInt(in) == 1) suppliersPresentation.mainRun(true);
                else if(Integer.parseInt(in) == 2) stockCLI.start();
                else if(Integer.parseInt(in) == 3) System.exit(0);
                else System.out.println("illegal input!!!");
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

}
