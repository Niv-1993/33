import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        //use menu.object to display all relevant objects
        Scanner sc = new Scanner(System.in);
        Menu menu = new Menu(sc);
        TransportationDisplay td = new TransportationDisplay();
        while (menu.endOfProgram()){
            menu.chooseOption();
            menu.nextStep();

        }
    }
}
