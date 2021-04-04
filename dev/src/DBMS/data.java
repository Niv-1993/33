package DBMS;

import BussniesLayer.SupplierController;

public class data {

    private SupplierController supplierController;
    //private static data Instance = null;

    public data(SupplierController supplierController){
        this. supplierController = supplierController;
        this.loadData();
    }

    /*
    public static data getInstance() {
        if(Instance == null) Instance = new data();
        return Instance;
    }
     */

    private void loadData(){
        try {
            supplierController.addSupplier("BGU" , 1 , "check" );
            supplierController.addSupplier("INTEL" , 2 , "check" );
            supplierController.addSupplier("TAU" , 3, "cash" );
            supplierController.addSupplier("MIT" , 4 , "bank transfer" );
            supplierController.addSupplier("KingOfTheWorld" ,5 , "cash" );
            supplierController.addSupplier("GOD" ,100 , "bank transfer" );
            supplierController.addItem(0 , "meat" , 19.99);
            supplierController.addItem(0 , "meat" , 39.99);
            supplierController.addItem(0 , "meat" , 70.5);
            supplierController.addItem(1 , "dairy" , 21.3);
            supplierController.addItem(1 , "dairy" , 35.2);
            supplierController.addItem(1 , "candy" , 1.99);
            supplierController.addItem(1 , "candy" , 0.1);
            supplierController.addItem(2 , "cleaning" , 15.99);
            supplierController.addItem(2 , "cleaning" , 19.99);
            supplierController.addItem(3 , "drink" , 10.99);
            supplierController.addItem(3 , "drink" , 10.99);
            supplierController.addItem(3 , "drink" , 10.99);
            supplierController.addItem(3 , "drink" , 10.99);
            supplierController.addItem(3 , "drink" , 10.99);
            supplierController.addItem(3 , "drink" , 10.99);
            supplierController.addItem(4 , "fruit" , 1.99);
            supplierController.addItem(4 , "fruit" , 1.99);
            supplierController.addItem(4 , "vegetable" , 0.99);
            supplierController.addItem(4 , "vegetable" , 1.5);
            for(int i = 0 ; i < 19 ; i++  ){
                if(i<= 2) supplierController.addQuantityDocument(0 , i , 3 + i , 2 + i);
                else if(i<= 6) supplierController.addQuantityDocument(1 , i , 2 + i , 1 + i);
                else if(i<= 8) supplierController.addQuantityDocument(2 , i , 1 + i , 3);
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
            supplierController.addOrder(0);
            supplierController.addOrder(2);
            supplierController.addOrder(3);
            supplierController.addOrder(4);
            supplierController.addOrder(4);
            supplierController.addItemToOrder(0 , 0 , 0 , 2);
            supplierController.addItemToOrder(0 , 0 , 2 , 5);
            supplierController.addItemToOrder(2 , 1 , 8 , 4);
            supplierController.addItemToOrder(3 , 2 ,  11, 4);
            supplierController.addItemToOrder(4 , 3 , 15 , 5);
            supplierController.addItemToOrder(4 , 4 , 18 , 1);
        }catch (Exception e){}
    }
}
