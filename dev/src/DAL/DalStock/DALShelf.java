package DAL.DalStock;

import DAL.DALObject;
import DAL.DalController;

public class DALShelf extends DALObject {

    public DALShelf(){
        super(null);
    }

    public DALShelf(int storeID, int typeID, int isStorage, int type, int curr, int max, DalController dc){
        super(dc);
    }

    public String getCreate() {
        return "CREATE TABLE IF NOT EXISTS Shelf (\n" +
                "\tstoreID INTEGER NOT NULL,\n" +
                "\tshelfID INTEGER NOT NULL,\n" +
                "\tlocation INTEGER NOT NULL,\n" +
                "\ttypeID INTEGER NOT NULL,\n" +
                "\tcurr INTEGER NOT NULL,\n" +
                "\tmaximum INTEGER NOT NULL,\n" +
                "\tPRIMARY KEY (storeID,shelfID, location),\n" +
                "\tFOREIGN KEY (typeID) REFERENCES Product(typeID)\n" +
                "\tON DELETE CASCADE ON UPDATE CASCADE,\n" +
                "\tFOREIGN KEY (storeID) REFERENCES Product(storeID)\n" +
                "\tON DELETE CASCADE ON UPDATE CASCADE\n" +
                ");";
    }

    public String getSelect() {
        return null;
    }

    public String getDelete() {
        return null;
    }

    public String getUpdate() {
        return null;
    }

    public String getInsert() {
        return null;
    }
}
