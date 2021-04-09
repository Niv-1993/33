package Presentation;

import BusinessLayer.Fcade.StorageService;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class CLIPresentation {
    StorageService SS;
    Scanner scan=new Scanner(System.in);
    final static Logger log= Logger.getLogger(CLIPresentation.class);

    String read(){
        return scan.nextLine().toLowerCase().replaceAll("\\s", "");
    }

    public CLIPresentation(){
        SS=new StorageService();
    }
    public CLIPresentation(StorageService ss){
        SS=ss;
    }
    public void start() {
        String in;
        while (true) {
            System.out.print("Welcome to SuperLi storage management.\n" +
                    "The current stores are: " + (SS.getStores().isError ? "None" : SS.getStores()) +
                    "\nEnter store number to use with a store, 'add' to add new store or 'exit' to exit.\n"
            );
            in = read();
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
                    "\nTo access discounts enter 'discounts'"+
                    "\nTo return to store selection enter 'return'\n"+
                    "To exit the program enter 'exit'\n");
            in = read();
            if(in.equals("exit")) System.exit(0);
            if(in.equals("return")) return;
            if(in.equals("reports")) reports();
            else if(in.equals("categories")) categories();
            else if(in.equals("types")) types();
            else if(in.equals("products")) products();
            else if(in.equals("discounts")) discounts();
            else System.out.print("bad input, try again.\n");
        }
    }


    private void reports() {
        String in;
        while(true) {
            System.out.print("Welcome to report management for store number: " + SS.getCurrID() +
                    "\nTo get the weekly report enter 'weekly'\nTo get the waste report enter 'waste'" +
                    "\nTo get the missing items report enter 'missing'" +
                    "\nTo return to store "+SS.getCurrID()+" enter 'return'" +
                    "\nTo exit the program enter 'exit'\n");
            in = read();
            if(in.equals("exit")) System.exit(0);
            if(in.equals("return")) return;
            if(in.equals("weekly")) weekly();
            else if(in.equals("waste")) System.out.print(SS.getWasteReport());
            else if(in.equals("missing")) System.out.print(SS.getNeededReport());
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
                    "\nTo exit the program enter 'exit'\n");
            in = read();
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
                    "\nTo exit the program enter 'exit'\n");
            in = read();
            if(in.equals("exit")) System.exit(0);
            if(in.equals("return")) return;
            if(in.equals("list")) System.out.print(SS.getCategories());
            try{
                String[] ls=in.split(",");
                List<Integer> ils=new ArrayList<>();
                for(String s: ls){
                    ils.add(Integer.parseInt(s));
                }
                System.out.print(SS.getWeeklyReport(ils));
            }catch(Exception e) {
                System.out.print("bad input, try again.\n");
            }
        }
    }

///TBD
private void categories() {
    String in;
    while(true) {
        System.out.print("Welcome to category management for store number: " + SS.getCurrID() +
                "\nTo list all of the categories enter 'list'" +
                "\nTo get more information about a specific category enter 'info' followed by the category ID separated by comma"+
                "\nTo add a new category enter 'add'" +
                "\nTo edit an existing category enter 'edit'" +
                "\nTo return to store "+SS.getCurrID()+" enter 'return'" +
                "\nTo exit the program enter 'exit'\n");
        in = read();
        if(in.equals("exit")) System.exit(0);
        if(in.equals("return")) return;
        else if(in.equals("list")) System.out.print(SS.getCategories());
        else if(in.equals("edit")) editCat();
        else if(in.equals("add")) addCat();
        else {
            try {
                String[] tmp = in.split(",");
                if (tmp.length == 2) {
                    if (tmp[0].equals("info")) System.out.print(SS.getCategoryInfo(Integer.parseInt(tmp[1])));
                    else System.out.print("bad input, try again.\n");
                } else System.out.print("bad input, try again.\n");
            }
            catch (Exception e) {System.out.print("bad input, try again.\n");}
        }
    }
}

    private void addCat() {
        String in;
        while(true) {
            System.out.print("To add a category enter the category name then its parent category ID separated by comma"+
                    "\nIf there is no parent category then just enter the category name" +
                    "\nTo return to category management enter 'return'" +
                    "\nTo exit the program enter 'exit'\n");
            in = read();
            if(in.equals("exit")) System.exit(0);
            if(in.equals("return")) return;
            String[] tmp=in.split(",");
            if(tmp.length==1) {
                if ((SS.addCategory(tmp[0]).isError())) {
                    System.out.print("bad input, try again.\n");
                } else {
                    System.out.print("Category added\n");
                }
            }
            else if(tmp.length==2) {
                try{
                    if ((SS.addCategory(tmp[0], Integer.parseInt(tmp[1])).isError())) {
                        System.out.print("bad input, try again.\n");
                    } else {
                        System.out.print("Category added\n");
                    }
                }
                catch(Exception e){
                    System.out.print("bad input, try again.\n");
                }
            }
            else System.out.print("bad input, try again.\n");
        }
    }

    private void editCat() {
        String in;
        while(true) {
            System.out.print("To edit a category enter the category ID then a new name then its new parent category ID separated by comma"+
                    "\nIf there is no parent category then just enter the category ID then a new name separated by comma" +
                    "\nTo return to category management enter 'return'" +
                    "\nTo exit the program enter 'exit'\n");
            in = read();
            if(in.equals("exit")) System.exit(0);
            if(in.equals("return")) return;
            String[] tmp=in.split(",");
            if(tmp.length==2) {
                try {
                    if (SS.editCategory(Integer.parseInt(tmp[0]), tmp[1]).isError()) {
                        System.out.print("bad input, try again.\n");
                    } else {
                        System.out.print("Category changed\n");
                    }
                }
                catch(Exception e){
                    System.out.print("bad input, try again.\n");
                }
            }
            else if(tmp.length==3) {
                try{
                    if (SS.editCategory(Integer.parseInt(tmp[0]), tmp[1],Integer.parseInt(tmp[2])).isError()) {
                        System.out.print("bad input, try again.\n");
                    } else {
                        System.out.print("Category changed\n");
                    }
                }
                catch(Exception e){
                    System.out.print("bad input, try again.\n");
                }
            }
            else System.out.print("bad input, try again.\n");
        }
    }

    private void types() {
        String in;
        while(true) {
            System.out.print("Welcome to product type management for store number: " + SS.getCurrID() +
                    "\nTo list all of the product types enter 'list'" +
                    "\nTo get more information about a specific type enter 'info' followed by the type ID separated by comma"+
                    "\nTo get the amount in the store of a specific type enter 'store' followed by the type ID separated by comma"+
                    "\nTo get the amount in storage of a specific type enter 'storage' followed by the type ID separated by comma"+
                    "\nTo add a new type enter 'add'" +
                    "\nTo edit an existing type enter 'edit'" +
                    "\nTo return to store "+SS.getCurrID()+" enter 'return'" +
                    "\nTo exit the program enter 'exit'\n");
            in = read();
            if(in.equals("exit")) System.exit(0);
            if(in.equals("return")) return;
            else if(in.equals("list")) System.out.print(SS.getProductTypes());
            else if(in.equals("edit")) editType();
            else if(in.equals("add")) addType();
            else {
                String[] tmp = in.split(",");
                if (tmp.length == 2) {
                    if (tmp[0].equals("info")) System.out.print(SS.getProductTypeInfo(Integer.parseInt(tmp[1])));
                    else if (tmp[0].equals("store")) System.out.print(SS.getShelvesAmount(Integer.parseInt(tmp[1])));
                    else if (tmp[0].equals("storage")) System.out.print(SS.getStorageAmount(Integer.parseInt(tmp[1])));
                    else System.out.print("bad input, try again.\n");
                } else System.out.print("bad input, try again.\n");
            }
        }

    }

    private void addType() {
        String in;
        while(true) {
            System.out.print("To add a type enter the type name then its minimum amount, its base price, sale price, name of producer, ID of supplier" +
                    " and the ID of its parent category separated by comma"+
                    "\nTo return to category management enter 'return'" +
                    "\nTo exit the program enter 'exit'\n");
            in = read();
            if(in.equals("exit")) System.exit(0);
            if(in.equals("return")) return;
            String[] tmp=in.split(",");
            if(tmp.length==7) {
                if ((SS.addProductType(tmp[0],Integer.parseInt(tmp[1]),Float.parseFloat(tmp[2]),Float.parseFloat(tmp[3]),tmp[4],Integer.parseInt(tmp[5]),Integer.parseInt(tmp[6])).isError())) {
                    System.out.print("bad input, try again.\n");
                } else {
                    System.out.print("Type added\n");
                }
            }
            else System.out.print("bad input, try again.\n");
        }
    }

    private void editType() {
        String in;
        while(true) {
            System.out.print("To edit a type enter the type ID then its type name ,minimum amount, its base price, sale price, name of producer, ID of supplier" +
                    " and the ID of its parent category separated by comma"+
                    "\nTo return to category management enter 'return'" +
                    "\nTo exit the program enter 'exit'\n");
            in = read();
            if(in.equals("exit")) System.exit(0);
            if(in.equals("return")) return;
            String[] tmp=in.split(",");
            if(tmp.length==8) {
                if ((SS.editProductType(Integer.parseInt(tmp[0]),tmp[1],Integer.parseInt(tmp[2]),Float.parseFloat(tmp[3]),Float.parseFloat(tmp[4]),tmp[5],Integer.parseInt(tmp[6]),Integer.parseInt(tmp[7])).isError())) {
                    System.out.print("bad input, try again.\n");
                } else {
                    System.out.print("Type changed\n");
                }
            }
            else System.out.print("bad input, try again.\n");
        }
    }


    private void products() {
        String in;
        while(true) {
            System.out.print("Welcome to product management for store number: " + SS.getCurrID() +
                    "\nTo get more information about a specific product enter 'info' followed by the product ID separated by comma"+
                    "\nTo add a new product enter 'add'" +
                    "\nTo relocate an existing product enter 'relocate'" +
                    "\nTo remove an existing product enter 'remove'" +
                    "\nTo report damage on an existing product enter 'damage'" +
                    "\nTo return to store "+SS.getCurrID()+" enter 'return'" +
                    "\nTo exit the program enter 'exit'\n");
            in = read();
            if(in.equals("exit")) System.exit(0);
            if(in.equals("return")) return;
            else if(in.equals("add")) addProd();
            else if(in.equals("relocate")) relocateProd();
            String[] tmp=in.split(",");
            if(tmp.length==2) {
                try {
                    if (tmp[0].equals("damage")) {
                        if (SS.reportDamage(Integer.parseInt(tmp[1])).isError()) {
                            System.out.print("bad input, try again.\n");
                        } else {
                            System.out.print("bad input, try again.\\n");
                        }
                    } else if (tmp[0].equals("remove")) {
                        if (SS.removeProduct(Integer.parseInt(tmp[1])).isError()) {
                            System.out.print("bad input, try again.\n");
                        } else {
                            System.out.print("bad input, try again.\n");
                        }
                    }
                    else if (tmp[0].equals("info")) System.out.print(SS.getProductInfo(Integer.parseInt(tmp[1])));
                    else System.out.print("bad input, try again.\n");
                }
                catch(Exception e) {System.out.print("bad input, try again.\n");}
            }
            else System.out.print("bad input, try again.\n");
        }
    }

    private void addProd() {
        String in;
        while(true) {
            System.out.print("To add a product enter the product ID then its expiration date (in DD-MM-YYYY format) separated by comma"+
                    "\nTo return to product management enter 'return'" +
                    "\nTo exit the program enter 'exit'\n");
            in = read();
            if(in.equals("exit")) System.exit(0);
            if(in.equals("return")) return;
            String[] tmp=in.split(",");
            if(tmp.length==2) {
                try {
                    if ((SS.addProduct(Integer.parseInt(tmp[0]), new SimpleDateFormat("dd-MM-yyyy").parse(tmp[1])).isError())) {
                        System.out.print("bad input, try again.\n");
                    } else {
                        System.out.print("product added\n");
                    }
                }
                catch(Exception e){
                    System.out.print("bad input, try again.\n");
                }
            }
            else System.out.print("bad input, try again.\n");
        }
    }

    private void relocateProd() {
        String in;
        while(true) {
            System.out.print("To relocate a product enter the product ID then its target location (storage or store) then the new shelf number separated by comma"+
                    "\nTo return to product management enter 'return'" +
                    "\nTo exit the program enter 'exit'\n");
            in = read();
            if(in.equals("exit")) System.exit(0);
            if(in.equals("return")) return;
            String[] tmp=in.split(",");
            if(tmp.length==3) {
                try {
                    boolean b;
                    if(tmp[1].equals("storage")) b=true;
                    else if(tmp[1].equals("store")) b=false;
                    else throw new Exception("bad input\n");
                    if ((SS.relocateProduct(Integer.parseInt(tmp[0]),b,Integer.parseInt(tmp[2])).isError())) {
                        System.out.print("bad input, try again.\n");
                    } else {
                        System.out.print("product added\n");
                    }
                }
                catch(Exception e){
                    System.out.print("bad input, try again.\n");
                }
            }
            else System.out.print("bad input, try again.\n");
        }
    }

    private void discounts() {
        String in;
        while(true) {
            System.out.print("Welcome to discount management for store number: " + SS.getCurrID() +
                    "\nTo access supplier discounts enter 'supplier'"+
                    "\nTo access sale discounts enter 'sale'"+
                    "\nTo return to store "+SS.getCurrID()+" enter 'return'" +
                    "\nTo exit the program enter 'exit'\n");
            in = read();
            if(in.equals("exit")) System.exit(0);
            if(in.equals("return")) return;
            else if(in.equals("supplier")) supplier();
            else if(in.equals("sale")) sale();
            else System.out.print("bad input, try again.\n");
        }

    }

    private void sale() {
        String in;
        while(true) {
            System.out.print("Welcome to sale discount management for store number: " + SS.getCurrID() +
                    "\nTo get sale discounts for a specific type enter 'get' then the type ID separated by comma"+
                    "\nTo add a new sale discount enter 'add'"+
                    "\nTo return discount management enter 'return'" +
                    "\nTo exit the program enter 'exit'\n");
            in = read();
            if(in.equals("exit")) System.exit(0);
            if(in.equals("return")) return;
            else if(in.equals("add")) addSaleDiscount();
            String[] tmp=in.split(",");
            if(tmp.length==2) {
                try {
                    if (tmp[0].equals("get")) System.out.print(SS.getSaleDiscounts(Integer.parseInt(tmp[1])));
                }
                catch(Exception e){
                    System.out.print("bad input, try again.\n");
                }
            }
            else System.out.print("bad input, try again.\n");
        }

    }

    private void addSaleDiscount(){
        String in;
        while(true) {
        System.out.print("To add a sale discount for a specific type enter 'type' then the type ID then its discount percentage," +
                " then the start and the end date (in DD-MM-YYYY format) separated by comma"+
                "\nTo add a sale discount for a whole category enter 'category' then the category ID then its discount percentage," +
                " then the start and the end date (in DD-MM-YYYY format) separated by comma"+
                "\nTo return to product management enter 'return'" +
                "\nTo exit the program enter 'exit'\n");
        in = read();
        if(in.equals("exit")) System.exit(0);
        if(in.equals("return")) return;
        String[] tmp=in.split(",");
        if(tmp.length==5) {
            try {
                if(tmp[0].equals("type")) {
                    if ((SS.addSaleProductDiscount(Integer.parseInt(tmp[1]),Float.parseFloat(tmp[2]),
                            new SimpleDateFormat("dd-MM-yyyy").parse(tmp[3]), new SimpleDateFormat("dd-MM-yyyy").parse(tmp[4])).isError())) {
                        System.out.print("bad input, try again.\n");
                    } else {
                        System.out.print("product added\n");
                    }
                }
                if(tmp[0].equals("category")) {
                    if ((SS.addSaleCategoryDiscount(Integer.parseInt(tmp[1]),Float.parseFloat(tmp[2]),
                            new SimpleDateFormat("dd-MM-yyyy").parse(tmp[3]), new SimpleDateFormat("dd-MM-yyyy").parse(tmp[4])).isError())) {
                        System.out.print("bad input, try again.\n");
                    } else {
                        System.out.print("product added\n");
                    }
                }
                else System.out.print("bad input, try again.\n");
            }
            catch(Exception e){
                System.out.print("bad input, try again.\n");
            }
        }
        else System.out.print("bad input, try again.\n");
        }
    }

    private void supplier() {
        String in;
        while(true) {
            System.out.print("Welcome to supplier discount management for store number: " + SS.getCurrID() +
                    "\nTo get supplier discounts for a specific type enter 'get' then the type ID separated by comma"+
                    "\nTo add a new sale discount enter 'add'"+
                    "\nTo return discount management enter 'return'" +
                    "\nTo exit the program enter 'exit'\n");
            in = read();
            if(in.equals("exit")) System.exit(0);
            if(in.equals("return")) return;
            else if(in.equals("add")) addSupplierDiscount();
            String[] tmp=in.split(",");
            if(tmp.length==2) {
                try {
                    if (tmp[0].equals("get")) System.out.print(SS.getSupplierDiscounts(Integer.parseInt(tmp[1])));
                }
                catch(Exception e){
                    System.out.print("bad input, try again.\n");
                }
            }
            else System.out.print("bad input, try again.\n");
        }
    }

    private void addSupplierDiscount() {
        String in;
        while(true) {
            System.out.print("To add a supplier discount for a specific category enter category ID then its discount percentage," +
                    " then the start and the end date (in DD-MM-YYYY format) and then the supplier ID separated by comma"+
                    "\nTo return to product management enter 'return'" +
                    "\nTo exit the program enter 'exit'\n");
            in = read();
            if(in.equals("exit")) System.exit(0);
            if(in.equals("return")) return;
            String[] tmp=in.split(",");
            if(tmp.length==5) {
                try {
                    if ((SS.addSupplierDiscount(Integer.parseInt(tmp[0]),Float.parseFloat(tmp[1]),
                            new SimpleDateFormat("dd-MM-yyyy").parse(tmp[2]), new SimpleDateFormat("dd-MM-yyyy").parse(tmp[3]),Integer.parseInt(tmp[4])).isError())) {
                        System.out.print("bad input, try again.\n");
                    } else {
                        System.out.print("product added\n");
                    }
                }
                catch(Exception e){
                    System.out.print("bad input, try again.\n");
                }
            }
            else System.out.print("bad input, try again.\n");
        }
    }
}
