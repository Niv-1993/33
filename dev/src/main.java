import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Menu menu = new Menu(sc);
        TransportationDisplay td = new TransportationDisplay();
        boolean endProgram = true;
        while (endProgram){
            menu.chooseOption();
            menu.chooseAddOption();

            endProgram = menu.endOfProgram();
        }
    }
}
