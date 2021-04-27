package DAL.DalStock;

import DAL.DALObject;

public class DALCategory extends DALObject {

    public DALCategory(){}

    public DALCategory(int storeID, int id){}

    public DALCategory(int storeID, int id, String name){} // get child categories types and discounts from controller

    @Override
    public String getCreate() {
        return "CREATE TABLE IF NOT EXISTS Category (\n" +
                "\tcategoryID INTEGER PRIMARY KEY,\n" +
                "\tname VARCHAR\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE IF NOT EXISTS SubCategory (\n" +
                "\tparentID INTEGER,\n" +
                "\tchildID INTEGER,\n" +
                "\tPRIMARY KEY (parentID, childID),\n" +
                "\tFOREIGN KEY (parentID) REFERENCES Category (categoryID)\n" +
                "\tON DELETE CASCADE ON UPDATE CASCADE,\n" +
                "\tFOREIGN KEY (childID) REFERENCES Category (categoryID)\n" +
                "\tON DELETE CASCADE ON UPDATE CASCADE\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE IF NOT EXISTS CategoryTypes (\n" +
                "\tcategoryID INTEGER,\n" +
                "\ttypeID INTEGER,\n" +
                "\tPRIMARY KEY (categoryID, typeID),\n" +
                "\tFOREIGN KEY (categoryID) REFERENCES Category (categoryID)\n" +
                "\tON DELETE CASCADE ON UPDATE CASCADE,\n" +
                "\tFOREIGN KEY (typeID) REFERENCES ProductType (typeID)\n" +
                "\tON DELETE CASCADE ON UPDATE CASCADE\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE IF NOT EXISTS CategoryDiscounts (\n" +
                "\tcategoryID INTEGER,\n" +
                "\tdiscountID INTEGER,\n" +
                "\tPRIMARY KEY (categoryID, discountID),\n" +
                "\tFOREIGN KEY (categoryID) REFERENCES Category (categoryID)\n" +
                "\tON DELETE CASCADE ON UPDATE CASCADE,\n" +
                "\tFOREIGN KEY (discountID) REFERENCES Discount (discountID)\n" +
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
