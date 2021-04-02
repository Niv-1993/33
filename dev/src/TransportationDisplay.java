import BussinessLayer.Driver;
import ServiceLayer.Controller;
import ServiceLayer.Objects.DriverServiceDTO;
import ServiceLayer.Objects.SupplierServiceDTO;
import ServiceLayer.Objects.TransportationServiceDTO;

import java.util.List;
import java.util.Scanner;

public class TransportationDisplay {
    TransportationServiceDTO transportation;
    Controller controller = Controller.initial();
    public void TransportationServiceDTO(){
        transportation = new TransportationServiceDTO();
    }
    public void createNewTransportation(){
        boolean done = false;
        while (done){
            int option = -1;
            List<SupplierServiceDTO> suppliers = controller.getAllSuppliers();
            System.out.println("choose a supplier");
            Scanner sc = new Scanner(System.in);
            option = sc.nextInt();

            System.out.println(suppliers);
        }
    }
    public void printAllTransportations(){
        System.out.println(controller.getAllTransportations());
    }
    private DriverServiceDTO selectDriver(int id){
        try {
            DriverServiceDTO driver = controller.getDriver(id);

            return driver;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
