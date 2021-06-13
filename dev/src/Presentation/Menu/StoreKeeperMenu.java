package Presentation.Menu;


import Business.ApplicationFacade.ResponseData;
import Business.ApplicationFacade.outObjects.TransportationServiceDTO;
import Presentation.Controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class StoreKeeperMenu extends Menu{
    public StoreKeeperMenu(Controllers r, Scanner input) {
        super(r, input);
    }
    @Override
    public void show() {
        while (true) {
            System.out.println("\n\n************* Functions Menu *************");
            System.out.println("1) My details");
            System.out.println("2) My constraints operations");
            System.out.println("3) My shifts and constraints");
            System.out.println("4) Stock Menu");
            System.out.println("5) Orders from suppliers");
            System.out.println("6) Suppliers card managements");
            System.out.println("7) Accept incoming orders");
            System.out.println("8) Cancel delivery");
            System.out.println("9) Logout");
            System.out.println("10) previous menu");
            System.out.println("Choose an option:");
            String option = read();
            switch (option) {
                case "1":
                    printMyDetails();
                    break;
                case "2":
                    Menu consM = new ConstraintMenu(r, input);
                    consM.show();
                    break;
                case "3":
                    printMyShifts();
                    System.out.println();
                    printMyConstraints();
                    System.out.println();
                    break;
                case "4":
                    //TODO: get function for this method alex and yaki[the stock menu only]
                case "5":
                    new OrdersMenu(r,input).show();
                    break;
                case "6":
                    new SuppliersMenu(r,input).show();
                    break;
                case "7":
                    List<TransportationServiceDTO> trans =  r.getTc().getTransportations(r.getCurrBID(), LocalDate.now(), LocalTime.now());
                    for (TransportationServiceDTO t : trans)
                        System.out.println(t.toString());
                    TransportationServiceDTO acceptT = getAcceptID(trans);
                    if(acceptT == null) break; //go back to main menu
                    //TODO: send to alex the transportationDTO to update stock acceptTransportation(acceptT);
                case "8":
                    cancelDelivery();
                    break;
                case "9":
                    r.getRc().Logout();
                    return;
                case "10":
                    return;
                default:
                    System.out.println("Invalid input,please choose a number again");
                    if (goBack()) return;
                    break;
            }
        }
    }

    private TransportationServiceDTO getAcceptID(List<TransportationServiceDTO> list) {
        while (true) {
            System.out.print("Enter transportation id to accept: ");
            long num = enterInt(read());
            if (num <= 0) {
                System.out.println("invalid id - negative number.");
                if (goBack()) return null;
                else
                    continue;
            }
            for (TransportationServiceDTO t : list){
                if(t.getId() == num) {
                    return t;
                }
            }
            System.out.println("invalid id - transportation id does no exits in list option");
            if (goBack()) return null;
        }
    }
}
