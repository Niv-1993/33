import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Menu menu = new Menu(sc);
        while (menu.endOfProgram()){
            menu.chooseOption();
            menu.nextStep();
        }
    }
}
