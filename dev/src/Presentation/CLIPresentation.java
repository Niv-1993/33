package Presentation;

import BusinessLayer.Fcade.StorageService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CLIPresentation {
    StorageService SS;
    Scanner scan=new Scanner(System.in);
    public CLIPresentation(){
        SS=new StorageService();
    }

    public void start() {
        String in;
        while (true) {
            System.out.print("Welcome to SuperLi storage management.\n" +
                    "The current stores are: " + (SS.getStores().isError ? "None" : SS.getStores()) +
                    "\nEnter store number to use with a store, 'add' to add new store or 'exit' to exit.\n"
            );
            in = scan.next();
            if(in.equals("exit")) return;
            if(in.equals("add")){
                SS.addStore();
                System.out.print("new Store added.\n");
            }
            else{
                try{
                    int i=Integer.parseInt(in);
                    SS.useStore(i);
                    store();
                }catch (Exception e){
                    System.out.print("bad input, try again.\n");
                }
            }
        }
    }
    private void store()
    {
        String in;
        while(true) {
            System.out.print("Welcome to store number: " + SS.getCurrID() +
                    "\nTo get reports enter 'reports'\nTo access categories enter 'categories'" +
                    "\nTo access product types enter 'types'\nTo access specific products enter 'products'" +
                    "\nTo return to store selection enter 'return'\n");
            in = scan.next();
            if(in.equals("return")) return;
            if(in.equals("reports")) reports();
            else if(in.equals("categories")) categories();
            else if(in.equals("types")) types();
            else if(in.equals("products")) products();
            else System.out.print("bad input, try again.\n");
        }
    }

    private void products() {
        String in;
        while(true) {
            System.out.print("Welcome to report management for store number: " + SS.getCurrID() +
                    "\nTo get the weekly report enter 'weekly'\nTo get the waste report enter 'waste'" +
                    "\nTo get the missing items report enter 'missing'" +
                    "\nTo return to store "+SS.getCurrID()+" enter 'return'" +
                    "\nTo exit the program enter 'exit'");
            in = scan.next();
            if(in.equals("exit")) System.exit(0);
            if(in.equals("return")) return;
            if(in.equals("weekly")) weekly();
            else if(in.equals("waste")) SS.getWasteReport();
            else if(in.equals("missing")) SS.getNeededReport();
            else System.out.print("bad input, try again.\n");
        }

    }

    private void weekly() {
        String in;
        while(true) {
            System.out.print("Welcome to weekly report management for store number: " + SS.getCurrID() +
                    "\nTo get the weekly report for the entire store enter 'all'" +
                    "\nTo get the weekly report for a specific categories enter 'category'" +
                    "\nTo return to store "+SS.getCurrID()+" report management enter 'return'"+
                    "\nTo exit the program enter 'exit'");
            in = scan.next();
            if(in.equals("exit")) System.exit(0);
            if(in.equals("return")) return;
            if(in.equals("all")) System.out.print(SS.getWeeklyReport());
            else if(in.equals("category")) weeklyByCat();
            else System.out.print("bad input, try again.\n");
        }
    }

    private void weeklyByCat() {
        String in;
        while(true) {
            System.out.print("Welcome to weekly report by category for store number: " + SS.getCurrID() +
                    "\nTo list all of the categories enter 'list'" +
                    "\nTo get the weekly report for some categories enter the category ID numbers separated by commas" +
                    "\nTo return to store "+SS.getCurrID()+" weekly report management enter 'return'"+
                    "\nTo exit the program enter 'exit'");
            in = scan.next();
            if(in.equals("exit")) System.exit(0);
            if(in.equals("return")) return;
            if(in.equals("list")) System.out.print(SS.getCategories());
            try{
                String[] ls=in.split(",");
                List<Integer> ils=new ArrayList<>();
                for(String s: ls){
                    ils.add(Integer.parseInt(s));
                }
                System.out.print("NOT IMPLEMENTED YET");
            }catch(Exception e) {
                System.out.print("bad input, try again.\n");
            }
        }
    }

///TBD
    private void types() {
    }

    private void categories() {
    }

    private void reports() {
    }
}
