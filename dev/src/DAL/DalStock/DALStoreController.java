package DAL.DalStock;

import DAL.DALObject;

public class DALStoreController extends DALObject {

    public DALStoreController(){}

    public DALStoreController(int storeID){}

    public DALStoreController(int storeID, int storeShelves, int shelves, int discountCounter,
                       int typeCounter, int categoryCounter, int maxProductsOnType){}
    //get categories controllers discounts and product types from controller

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
