package DAL.DalStock;

import DAL.DALObject;
import DAL.DalController;
import DAL.Mapper;
import org.apache.log4j.Logger;

public class DALStoreController extends DALObject {

    final static Logger log=Logger.getLogger(DALStoreController.class);

    public DALStoreController(){
        super(null);
    }

    public DALStoreController(Integer storeID, Integer storeShelves, Integer shelves, Integer discountCounter,
                              Integer typeCounter, Integer categoryCounter, Integer maxProductsOnType, DalController dc){
        super(dc);
    }
    //get categories controllers discounts and product types from controller

    @Override
    public String getCreate() {
        return "CREATE TABLE IF NOT EXISTS StoreController (\n" +
                "\tstoreID INTEGER PRIMARY KEY NOT NULL,\n" +
                "\tstoreShelves INTEGER NOT NULL,\n" +
                "\tnumberOfShelves INTEGER NOT NULL,\n" +
                "\tdiscountCounter INTEGER NOT NULL,\n" +
                "\ttypeCounter INTEGER NOT NULL,\n" +
                "\tcategoryCounter INTEGER NOT NULL,\n" +
                "\tmaxProductsOnType INTEGER NOT NULL\n" +
                ");";
    }

    @Override
    public String getSelect() {
        return "SELECT * FROM StoreController WHERE storeID=?";
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
