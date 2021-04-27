package DAL.DalStock;

import DAL.DALObject;

public class DALInstanceController extends DALObject {

    public DALInstanceController(){}

    public DALInstanceController(int storeID, int typeID){}

    public DALInstanceController(int storeID, int typeID, int counter){} // get products from controller

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
