package DAL.DalStock;

import DAL.DALObject;

public class DALCategory extends DALObject {

    DALCategory(){}

    DALCategory(int storeID, int id){}

    DALCategory(int storeID, int id, String name){} // get child categories types and discounts from controller

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
