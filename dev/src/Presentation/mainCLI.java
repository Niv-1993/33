package Presentation;

import org.apache.log4j.Logger;

import java.util.Scanner;

public class mainCLI {
    PresentationCL presentationCL;
    StockCLI stockCLI;
    Scanner scan=new Scanner(System.in);
    final static Logger log= Logger.getLogger(StockCLI.class);

    String read(){
        return scan.nextLine().toLowerCase().replaceAll("\\s", "");
    }

    public mainCLI(){
        presentationCL = new PresentationCL();
        stockCLI = new StockCLI();
    }

    public void choice(){
        String in;
        while (true){
            System.out.println("please enter 1 for Suppliers and 2 for Stock");
            in = read();
            try {
                if(Integer.parseInt(in) == 1) presentationCL.mainRun(true);
                else if(Integer.parseInt(in) == 2) stockCLI.start();
                else System.out.println("illegal input");
            }catch (Exception e){
                System.out.println("illegal input");
            }
        }
    }

}
