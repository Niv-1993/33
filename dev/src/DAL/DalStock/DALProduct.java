package DAL.DalStock;

import DAL.DALObject;
import DAL.DalController;

public class DALProduct extends DALObject {

    public DALProduct(){
        super(null);
    }

    public DALProduct(Integer storeID, Integer typeID, Integer id, String date, Integer isDamaged, DalController dc){
        super(dc);
    } //get location from controller



    @Override
    public String getCreate() {
        return "CREATE TABLE IF NOT EXISTS Product (\n" +
                "\tstoreID INTEGER NOT NULL,\n" +
                "\ttypeID INTEGER NOT NULL,\n" +
                "\tproductID INTEGER NOT NULL,\n" +
                "\texpiration VARCHAR NOT NULL,\n" +
                "\tisDamaged INTEGER NOT NULL,\n" +
                "\tPRIMARY KEY (storeID, productID),\n" +
                "\tFOREIGN KEY (storeID) REFERENCES StoreController(storeID)\n" +
                "\tON DELETE CASCADE ON UPDATE CASCADE,\n" +
                "\tFOREIGN KEY (typeID) REFERENCES InstanceController(typeID)\n" +
                "\tON DELETE CASCADE ON UPDATE CASCADE\n" +
                ");\n"+
                "CREATE TABLE IF NOT EXISTS ShelfProduct (\n" +
                "\tshelfID INTEGER NOT NULL,\n" +
                "\tlocation INTEGER NOT NULL,\n" +
                "\tproductID INTEGER NOT NULL,\n" +
                "\ttypeID INTEGER NOT NULL,\n" +
                "\tcurr INTEGER NOT NULL,\n" +
                "\tPRIMARY KEY (shelfID, location, productID),\n" +
                "\tFOREIGN KEY (productID) REFERENCES Product(productID)\n" +
                "\tON DELETE CASCADE ON UPDATE CASCADE\n" +
                "\tFOREIGN KEY (typeID) REFERENCES Product(typeID)\n" +
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
