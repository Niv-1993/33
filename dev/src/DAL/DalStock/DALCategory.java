package DAL.DalStock;

import DAL.DALObject;
import DAL.DalController;
import DAL.Mapper;
import Utility.Tuple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DALCategory extends DALObject {

    private int _categoryID;
    private String _name;
    private List<Integer> _productTypes= new ArrayList<>();
    private List<Integer> _productDiscounts=new ArrayList<>();
    private int _storeID;
    private int _superCategory=0;
    private List<Integer> _categories=new ArrayList<>();
    private String tableName="Category";
    private String tableName2="SubCategory";


    public DALCategory(){
        super(null);
    }

    public DALCategory(Integer storeID, Integer id, String name, DalController dc){
        super(dc);
        _storeID=storeID;
        _categoryID=id;
        _name=name;
    } // get child categories types and discounts from controller

    @Override
    public String getCreate() {
        return "CREATE TABLE IF NOT EXISTS Category (\n" +
                "\tstoreID INTEGER NOT NULL UNIQUE,\n" +
                "\tcategoryID INTEGER NOT NULL UNIQUE,\n" +
                "\tparentID INTEGER,"+
                "\tname VARCHAR NOT NULL,\n" +
                "\tPRIMARY KEY (storeID, categoryID),\n" +
                "\tFOREIGN KEY (storeID) REFERENCES StoreController(storeID)\n" +
                "\tON DELETE CASCADE ON UPDATE CASCADE\t\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE IF NOT EXISTS SubCategory (\n" +
                "\tstoreID INTEGER NOT NULL UNIQUE,\n" +
                "\tparentID INTEGER NOT NULL UNIQUE,\n" +
                "\tchildID INTEGER NOT NULL,\n" +
                "\tPRIMARY KEY (storeID, childID),\n" +
                "\tFOREIGN KEY (storeID) REFERENCES StoreController(storeID)\n" +
                "\tON DELETE CASCADE ON UPDATE CASCADE,\n" +
                "\tFOREIGN KEY (parentID) REFERENCES Category (categoryID)\n" +
                "\tON DELETE CASCADE ON UPDATE CASCADE,\n" +
                "\tFOREIGN KEY (childID) REFERENCES Category (categoryID)\n" +
                "\tON DELETE CASCADE ON UPDATE CASCADE\n" +
                ");";
    }
    public void init(){
        String query= """
                SELECT typeID \s
                FROM Product \s
                WHERE StoreID=? AND categoryId?=?; 
                """;
        _productTypes=fill(query,_storeID,_categoryID);
         query= """
                SELECT discountID \s
                FROM Discount \s
                WHERE StoreID=? AND categoryId?=?; 
                """;
        _productDiscounts=fill(query,_storeID,_categoryID);
        query= """
                SELECT categoryId \s
                FROM Category \s
                WHERE StoreID=? AND categoryId?=?; 
                """;
        _categories=fill(query,_storeID,_categoryID);
    }
    private List<Integer> fill(String query,Integer... i){
        List<Integer> list=new ArrayList<>();
        Collections.addAll(list, i);
        try {
            List<Tuple<List<Class>,List<Object>>> tmp= DC.SelectMany(query,list);
            for (Tuple<List<Class>,List<Object>> t: tmp){
                return tmp.stream().map(x->(int)x.item2.get(0)).collect(Collectors.toList());
            }
        }
        catch (Exception e){
            throw new IllegalArgumentException("fail");
        }
        throw new IllegalArgumentException("fail");
    }

    @Override
    public String getSelect() {
        return """
                SELECT * \s
                FROM ? \s
                WHERE storeId=? AND categoryID=?; \s
                """;
    }

    @Override
    public String getDelete() {
        return """
                DELETE FROM ? \s
                WHERE storeID=? AND categoryID=?; \s
                DELETE FROM ? \s
                WHERE storeID=? AND categoryID=?; \s
                """;
    }

    @Override
    public String getUpdate() {
        return null;
    }

    @Override
    public String getInsert() {
        return """
                INSERT INTO ? \s
                (?,?,?,?)
                """;
    }


    public int getCategoryID() {
        return _categoryID;
    }
    public String getName() {
        return _name;
    }
    public void setName(String name){
        String updateName="UPDATE ? \n" +
                "SET name=?\n" +
                "WHERE \n" +
                "storeID=?\n" +
                "AND categoryID=?;";
        List<Tuple<Object,Class>> list=prepareList(tableName,name,_storeID,_categoryID);
        try {
            DC.noSelect(updateName, list);
        }
        catch (Exception e){
            throw new IllegalArgumentException("fail");
        }
        _name=name;
    }
    public List<Integer> getProductTypes(){return _productTypes;}
    public List<Integer> getDiscounts(){return _productDiscounts;}
    public void setSuperCategory(int i){
        String updateParent="UPDATE ? \n" +
                "SET parentID=?\n" +
                "WHERE \n" +
                "storeID=?\n" +
                "AND categoryID=?;";
        List<Tuple<Object,Class>> list=prepareList(tableName,i,_storeID,_categoryID);
        try {
            DC.noSelect(updateParent, list);
        }
        catch (Exception e){
            throw new IllegalArgumentException("fail");
        }
        _superCategory=i;
    }


    public void addCategory(int i){
        String query="INSERT INTO ? \n" +
                "(storeID,parentID,childID) \n" +
                "VALUES(?,?,?) ;";
        List<Tuple<Object,Class>> params=prepareList(tableName2,_storeID,_categoryID,i);
        try {
            DC.noSelect(query,params);
        }
        catch (Exception e){
            throw new IllegalArgumentException("fail");
        }
        _categories.add(i);
    }
    private List<Tuple<Object,Class>> prepareList(Object... o){
        List<Tuple<Object,Class>> params=new ArrayList<>();
        for (Object o1:o){
            params.add(new Tuple<>(o1,o1.getClass()));
        }
        return params;
    }

    public void addProductType(int i){
        List<Integer> key=new ArrayList<>();
        key.add(_storeID);
        key.add(i);
        DALProductType pt=(DALProductType) Mapper.getMap().getItem(DALProductType.class,key);
        pt.setCategory(_categoryID);
        _productTypes.add(i);
    }
    public void setDiscounts(List<Integer> list){
        List<Integer> key;
        List<Integer> remove=_productDiscounts;
        for (Integer i:remove){
            removeDiscount(i);
        }
        for (Integer i:list){
            addDiscount(i);
        }
    }
    public void addDiscount(int i){
        List<Integer> key=new ArrayList<>();
        key.add(_storeID);
        key.add(i);
        DALSaleDiscount pt=(DALSaleDiscount) Mapper.getMap().getItem(DALSaleDiscount.class,key);
        pt.addCategory(_categoryID);
        _productDiscounts.add(i);
    }
    public void removeDiscount(int i){
        List<Integer>key=new ArrayList<>();
        key.add(_storeID);
        key.add(i);
        DALSaleDiscount pt=(DALSaleDiscount) Mapper.getMap().getItem(DALSaleDiscount.class,key);
        pt.removeCategory(_categoryID);
        _productDiscounts.remove(i);
    }
    public void removeCategory(int i){
        String query="DELETE ? \n" +
                "WHERE storeID=? AND parentID=? AND childID=? ;\n";
        List<Tuple<Object,Class>> params=prepareList(tableName2,_storeID,_categoryID,i);
        try {
            DC.noSelect(query,params);
        }
        catch (Exception e){
            throw new IllegalArgumentException("fail");
        }
        _categories.remove(i);
    }
    public int getParent(){
        return _superCategory;
    }
}
