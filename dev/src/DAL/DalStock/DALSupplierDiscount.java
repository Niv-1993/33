package DAL.DalStock;

import DAL.DalController;

public class DALSupplierDiscount extends DALDiscount{

    public DALSupplierDiscount(){
        super(null);
    }


    public DALSupplierDiscount(Integer storeID, Integer id, Integer typeID, Integer categoryID, Integer supplierID,
                               Double percent, String startDate, String endDate, DalController dc){
        super(dc);
    } // get supplier id from controller



    @Override
    public String getSelect() {
        return null;
    }

    @Override
    public String getDelete() {
        return null;
    }

    @Override
    public String getUpdate() {
        return null;
    }

    @Override
    public String getInsert() {
        return null;
    }
}
