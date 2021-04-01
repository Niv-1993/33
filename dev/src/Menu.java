import ServiceLayer.Controller;

public class Menu {
    private Controller controller;
    private Menu(){
        controller = Controller.initial();
    }
    public void startMenu(){
        //while the use didnt done the loop will continue
        while (true) {
            //print the use choose
            System.out.println("press 1 to see all available Transportations ");
            System.out.println("press 2 to create a new Transportation");
            //take the user choose
            int choose = 1;
            if(choose == 1){
                System.out.println(controller.getAllTransportations());
            }else {
               //the story of new transportation
            }
        }
    }
    private boolean ChooseADriver(){
        System.out.println(controller.getAllDrivers().toString());
        boolean isDone = false;
        while (!isDone){
            if(!controller.setDriverOnTransportation(null)){
                //we choose maybe he want to change driver or end the transportation..

            }else {
                isDone = true;
            }

        }
        return isDone;
    }
}
