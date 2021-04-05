import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        //use menu.object to display all relevant objects
        Scanner sc = new Scanner(System.in);
        Menu menu = new Menu(sc);
        TransportationDisplay td = new TransportationDisplay();
        boolean endProgram = true;
        while (endProgram){
            //when u finish the database try this
            menu.printAllTucks();
            menu.printAllDrivers();
            menu.printAllBranches();
            menu.printAllSuppliers();
            menu.PrintAllItems();
            menu.printAllTransportations();

            endProgram = menu.endOfProgram();
        }
    }
}
