package DataController;

import BussniesLayer.SupplierController;

import java.time.LocalDate;

public class data {

    private final SupplierController supplierController;

    public data(SupplierController supplierController){
        this. supplierController = supplierController;
        this.loadData();
    }

    private void loadData(){
        try {
            supplierController.addSupplier("BGU" , 1 , 11 , 1 , "check" );
            supplierController.addSupplier("INTEL" , 2 , 22 , 2 , "check" );
            supplierController.addSupplier("TAU" , 1, 11 , 60, "cash" );
            supplierController.addSupplier("MIT" , 6, 6 , 6 , "bank transfer" );
            supplierController.addSupplier("KingOfTheWorld" , 5 , 5 ,5 , "cash" );
            supplierController.addSupplier("GOD" ,100 , 100 , 100 , "bank transfer" );
            supplierController.addItem(0 , "meat" , "chicken" , 19.99, 1, LocalDate.now());
            supplierController.addItem(0 , "meat" ,"minced meat" ,  39.99, 2, LocalDate.now());
            supplierController.addItem(0 , "meat" ,"stake" , 70.5, 3, LocalDate.now());
            supplierController.addItem(1 , "dairy" , "yogurt" ,  9.99, 4, LocalDate.now());
            supplierController.addItem(1 , "dairy" , "cheese cake",  35.2, 5, LocalDate.now());
            supplierController.addItem(1 , "candy" , "gum"  ,1.99, 6, LocalDate.now());
            supplierController.addItem(1 , "candy" , "lollipop" ,  0.5, 7, LocalDate.now());
            supplierController.addItem(2 , "cleaning" , "windshield wiper",  15.99, 8, LocalDate.now());
            supplierController.addItem(2 , "cleaning" , "broom", 19.99, 9, LocalDate.now());
            supplierController.addItem(3 , "drink" , "soda" , 10.99, 10, LocalDate.now());
            supplierController.addItem(3 , "drink" , "sprite ",  10.99, 11, LocalDate.now());
            supplierController.addItem(3 , "drink" , "fanta" ,10.99, 12, LocalDate.now());
            supplierController.addItem(3 , "drink" , "grape juice" , 10.99,13, LocalDate.now());
            supplierController.addItem(3 , "drink" ,"orange juice" ,  10.99,14, LocalDate.now());
            supplierController.addItem(3 , "drink" , "water", 5.99,15, LocalDate.now());
            supplierController.addItem(4 , "fruit" , "apple", 1.99,16, LocalDate.now());
            supplierController.addItem(4 , "fruit" , "orange",1.99,17, LocalDate.now());
            supplierController.addItem(4 , "vegetable" , "tomato" ,0.99,18, LocalDate.now());
            supplierController.addItem(4 , "vegetable" , "onion" ,1.5,19, LocalDate.now());
            for(int i = 0 ; i < 19 ; i++  ){
                if(i<= 2) supplierController.addQuantityDocument(0 , i , 3 + i , 2 + i);
                else if(i<= 6) supplierController.addQuantityDocument(1 , i , 2 + i , 1 + i);
                else if(i<= 8) supplierController.addQuantityDocument(2 , i , i , 3);
                else if(i<= 14) supplierController.addQuantityDocument(3 , i , i - 8 ,1);
                else supplierController.addQuantityDocument(4 , i , i - 14 , i-14);
            }
            supplierController.addSupplierAgreement(0 , 200 , 1 , true , true);
            supplierController.addSupplierAgreement(1 , 100 , 0 , false , true);
            supplierController.addSupplierAgreement(2 , 150 , 2 , true , false);
            supplierController.addSupplierAgreement(3 , 100 , 1 , false , false);
            supplierController.addSupplierAgreement(4 , 50 , 0 , true , true);
            supplierController.addSupplierAgreement(5 , 0 , 100 , true , true);
            supplierController.addContactEmail(0 , "BGU@bgu.ac.il" , "Rami");
            supplierController.addContactEmail(0 , "Lucture@bgu.ac.il" , "Rami");
            supplierController.addContactEmail(1 , "Intel@gamil.com" , "Lidor");
            supplierController.addContactEmail(3 , "PleaseGiveUs100@bgu.ac.il" , "Kfir");
            supplierController.addContactEmail(3 , "PleaseGiveUs100@gamil.com" , "Ori");
            supplierController.addContactEmail(4 , "AmirTheKing@bgu.ac.il" , "Amir");
            supplierController.addContactEmail(4 , "Cotel@GOD.com" , "Gabriel");
            supplierController.addContactEmail(4 , "SneBoher@GOD.com" , "Moshes");
            supplierController.addContactPhone(0 , "050-0000000" , "Tali");
            supplierController.addContactPhone(1 , "000-0000000" , "Jesus");
            supplierController.addContactPhone(2 , "054-1234567" , "Hani");
            supplierController.addContactPhone(3 , "054-9849521" , "Kfir");
            supplierController.addContactPhone(3 , "052-4737283" , "Ori");
            supplierController.addRegularOrder(0,1);
            supplierController.addRegularOrder(2,2);
            supplierController.addRegularOrder(3,3);
            supplierController.addRegularOrder(4,1);
            supplierController.addRegularOrder(4,5);
            supplierController.addItemToOrder(0 , 0 , 0 , 2);
            supplierController.addItemToOrder(0 , 0 , 2 , 5);
            supplierController.addItemToOrder(2 , 1 , 8 , 10);
            supplierController.addItemToOrder(3 , 2 ,  11, 4);
            supplierController.addItemToOrder(4 , 3 , 15 , 5);
            supplierController.addItemToOrder(4 , 4 , 18 , 1);
        }catch (Exception e){}
    }
}
