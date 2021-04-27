package DAL.DalStock;

import DAL.DalController;

public class DALSaleDiscount extends DALDiscount{

    public DALSaleDiscount(){
        super(null);
    }

    public DALSaleDiscount(int storeID, int id, int typeID, int categoryID, int supplierID,
                           double percent, String startDate, String endDate, DalController dc){
        super(dc);
    }// get supplier id from controller


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
