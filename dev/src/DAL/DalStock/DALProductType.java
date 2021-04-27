package DAL.DalStock;

import DAL.DALObject;

public class DALProductType extends DALObject {

    public DALProductType(){}

    public DALProductType(int storeID,int typeID){}

    public DALProductType(int storeID, int typeID, String name, int category, int min, int shelfCurr,
                   double basePrice, double salePrice, String producer){}
    // get products, suppliers and discounts from controller

    @Override
    public String getCreate() {
        return null;
    }

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
