package DBMS;

import BussniesLayer.SupplierController;

public class data {

    private SupplierController supplierController;
    private static data Instance = null;

    private data(){
        supplierController = new SupplierController();
    }

    public static data getInstance() {
        if(Instance == null) Instance = new data();
        return Instance;
    }

    private void loadData(){
        try {
            supplierController.addSupplier("BGU" , 1 , "check" );
            supplierController.addSupplier("INTEL" , 2 , "check" );
            supplierController.addSupplier("TAU" , 3, "cash" );
            supplierController.addSupplier("MIT" , 4 , "bank transfer" );
            supplierController.addSupplier("KingOfTheWorld" ,5 , "cash" );
            supplierController.addSupplier("GOD" ,100 , "bank transfer" );
            supplierController.addItem(1 , "meat" , 19.99);
            supplierController.addItem(1 , "meat" , 39.99);
            supplierController.addItem(1 , "meat" , 70.5);
            supplierController.addItem(2 , "dairy" , 21.3);
            supplierController.addItem(2 , "dairy" , 35.2);
            supplierController.addItem(2 , "candy" , 1.99);
            supplierController.addItem(2 , "candy" , 0.1);
            supplierController.addItem(3 , "cleaning" , 15.99);
            supplierController.addItem(3 , "cleaning" , 19.99);
            supplierController.addItem(4 , "drink" , 10.99);
            supplierController.addItem(4 , "drink" , 10.99);
            supplierController.addItem(4 , "drink" , 10.99);
            supplierController.addItem(4 , "drink" , 10.99);
            supplierController.addItem(4 , "drink" , 10.99);
            supplierController.addItem(4 , "drink" , 10.99);
            supplierController.addItem(5 , "fruit" , 1.99);
            supplierController.addItem(5 , "fruit" , 1.99);
            supplierController.addItem(5 , "vegetable" , 0.99);
            supplierController.addItem(5 , "vegetable" , 1.5);
            for(int i = 0 ; i < 19 ; i++  ){
                if(i<= 2) supplierController.addQuantityDocument(1 , i , 3 + i , 2 + i);
                else if(i<= 6) supplierController.addQuantityDocument(2 , i , 2 + i , 1 + i);
                else if(i<= 8) supplierController.addQuantityDocument(3 , i , 1 + i , 3);
                else if(i<= 14) supplierController.addQuantityDocument(4 , i , i - 10 ,1);
                else supplierController.addQuantityDocument(5 , i , i - 14 , i-14);
            }
            supplierController.addSupplierAgreement(1 , 200 , 1 , true , true);
            supplierController.addSupplierAgreement(2 , 100 , 0 , false , true);
            supplierController.addSupplierAgreement(3 , 150 , 2 , true , false);
            supplierController.addSupplierAgreement(4 , 100 , 1 , false , false);
            supplierController.addSupplierAgreement(5 , 50 , 0 , true , true);
            supplierController.addSupplierAgreement(6 , 0 , 100 , true , true);
            supplierController.addContactEmail(1 , "BGU@bgu.ac.il" , "Rami");
            supplierController.addContactEmail(1 , "Lucture@bgu.ac.il" , "Rami");
            supplierController.addContactEmail(2 , "Intel@gamil.com" , "Lidor");
            supplierController.addContactEmail(4 , "PleaseGiveUs100@bgu.ac.il" , "Kfir");
            supplierController.addContactEmail(4 , "PleaseGiveUs100@gamil.com" , "Ori");
            supplierController.addContactEmail(5 , "AmirTheKing@bgu.ac.il" , "Amir");
            supplierController.addContactEmail(5 , "Cotel@GOD.com" , "Gabriel");
            supplierController.addContactEmail(5 , "SneBoher@GOD.com" , "Moshes");
            supplierController.addContactPhone(1 , "050-0000000" , "Tali");
            supplierController.addContactPhone(2 , "000-0000000" , "Jesus");
            supplierController.addContactPhone(3 , "054-1234567" , "Hani");
            supplierController.addContactPhone(4 , "054-9849521" , "Kfir");
            supplierController.addContactPhone(4 , "052-4737283" , "Ori");
            supplierController.addOrder(1);
            supplierController.addOrder(3);
            supplierController.addOrder(4);
            supplierController.addOrder(5);
            supplierController.addOrder(5);
            supplierController.addItemToOrder(1 , 0 , 2 , 2);
            supplierController.addItemToOrder(1 , 0 , 3 , 5);
            supplierController.addItemToOrder(3 , 1 , 8 , 4);
            supplierController.addItemToOrder(4 , 2 ,  11, 4);
            supplierController.addItemToOrder(5 , 3 , 15 , 5);
            supplierController.addItemToOrder(5 , 4 , 18 , 1);
        }catch (Exception e){}
    }
}
