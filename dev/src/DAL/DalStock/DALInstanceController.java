package DAL.DalStock;

import DAL.DALObject;
import DAL.DalController;

public class DALInstanceController extends DALObject {

    public DALInstanceController(){
        super(null);
    }

    public DALInstanceController(int storeID, int typeID, int counter, DalController dc){
        super(dc);
    } // get products from controller

    @Override
    public String getCreate() {
        return "CREATE TABLE IF NOT EXISTS InstanceController (\n" +
                "\tstoreID INTEGER NOT NULL,\n" +
                "\ttypeID INTEGER NOT NULL,\n" +
                "\tcounter INTEGER NOT NULL,\n" +
                "\tPRIMARY KEY (storeID, typeID),\n" +
                "\tFOREIGN KEY (storeID) REFERENCES StoreController(storeID)\n" +
                "\tON DELETE CASCADE ON UPDATE CASCADE,\n" +
                "\tFOREIGN KEY (typeID) REFERENCES ProductType(typeID)\n" +
                "\tON DELETE CASCADE ON UPDATE CASCADE\n" +
                ");";
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
