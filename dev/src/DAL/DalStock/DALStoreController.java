package DAL.DalStock;

import DAL.DALObject;

public class DALStoreController extends DALObject {

    DALStoreController(){}

    DALStoreController(int storeID){}

    DALStoreController(int storeID, int storeShelves, int shelves, int discountCounter,
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
