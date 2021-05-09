package DAL.DalStock;

import DAL.DALObject;
import DAL.DalController;
import DAL.Mapper;
import Utility.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DALProductType extends DALObject {
    private int _typeID;
    private int _categoryID;
    private List<Integer> _products=new ArrayList<>();
    private String _name;
    private int _minAmount;
    private int _shelfCurr=0;
    private int _storageCurr=0;
    private float _basePrice;
    private String _producer;
    private int storeId;
    private List<Integer> _suppliers=new ArrayList<>();
    private List<Integer> _saleDiscounts=new ArrayList<>();
    private List<Integer> _supplierDiscounts=new ArrayList<>();
    private String tableName="ProductType";
    private String supTable="Supplier";


    public DALProductType(){
        super(null);
    }

    public DALProductType(Integer storeID, Integer typeID, String name, Integer category, Integer min, Integer shelfCurr,
                          Integer storageCurr, Double basePrice, Double salePrice, String producer, DalController dc){
        super(dc);
    }
    // get products, suppliers and discounts from controller

    @Override
    public String getCreate() {
        return "CREATE TABLE IF NOT EXISTS ProductType (\n" +
                "\tstoreID INTEGER NOT NULL,\n" +
                "\ttypeID INTEGER NOT NULL,\n" +
                "\tname VARCHAR NOT NULL,\n" +
                "\tcategoryID INTEGER NOT NULL,\n" +
                "\tminimum INTEGER NOT NULL,\n" +
                "\tshelfCurr INTEGER NOT NULL,\n" +
                "\tstorageCurr INTEGER NOT NULL,\n" +
                "\tbasePrice DOUBLE NOT NULL,\n" +
                "\tsalePrice DOUBLE NOT NULL,\n" +
                "\tproducer VARCHAR NOT NULL,\n" +
                "\tPRIMARY KEY (storeID, typeID),\n" +
                "\tFOREIGN KEY (storeID) REFERENCES StoreController(storeID)\n" +
                "\tON DELETE CASCADE ON UPDATE CASCADE\n" +
                "\tFOREIGN KEY (categoryID) REFERENCES Category(categoryID)\n" +
                "\tON DELETE CASCADE ON UPDATE CASCADE\n" +
                ");\n"+
                "CREATE TABLE IF NOT EXISTS Supplier (\n" +
                "\tstoreID INTEGER NOT NULL,\n" +
                "\ttypeID INTEGER NOT NULL,\n" +
                "\tsupplierID INTEGER NOT NULL,\n" +
                "\tPRIMARY KEY (storeID, typeID, supplierID),\n" +
                "\tFOREIGN KEY (storeID) REFERENCES StoreController(storeID)\n" +
                "\tON DELETE CASCADE ON UPDATE CASCADE,\n" +
                "\tFOREIGN KEY (typeID) REFERENCES ProductType(typeID)\n" +
                "\tON DELETE CASCADE ON UPDATE CASCADE\n" +
                ");";
    }

    @Override
    public String getSelect() {
        return """
                SELECT * \s
                FROM ProductType \s
                WHERE storeID=? AND typeID=? ;\s
                """;
    }

    @Override
    public String getDelete() {
        return """
                DELETE FROM ProductType \s
                WHERE storeID=? AND typeID=? ;\s
                """;

    }

    @Override
    public String getUpdate() {
        return null;
    }

    @Override
    public String getInsert() {
        return """
                INSERT OR REPLACE INTO ProductType \s
                VALUES(?,?,?,?,?,?,?,?,?,?);
                """;
    }
    public void setCategory(int i){
        String query= """
                UPDATE ? \s
                Set categoryID=? \s
                WHERE \s
                storeID=? AND typeID=?;;
                """;
        List<Tuple<Object,Class>> list=prepareList(tableName,i,storeId,_typeID);
        try{
            DC.noSelect(query, list);
        }
        catch (Exception e){
            throw new IllegalArgumentException("fail");
        }
        _categoryID=i;


    }
    public void setSuppliers(List<Integer> sup){
        StringBuilder query=new StringBuilder("""
                DELETE ? \s
                WHERE storeID=? AND typeID=?;
                """);
        List<Tuple<Object,Class>> list=prepareList(supTable,storeId,_typeID);
        for (Integer i:sup){
            query.append("""
                    INSERT INTO ? \s
                    (storeID,typeID,SupplierID)\s
                    VALUES (?,?,?);
                    """);
            list.addAll(prepareList(supTable,storeId,_typeID,i));
        }
        try{
            DC.noSelect(query.toString(), list);
        }
        catch (Exception e){
            throw new IllegalArgumentException("fail");
        }
        _suppliers=sup;
    }
    public int get_typeID(){return _typeID;}
    public int get_categoryID(){return _categoryID;}

    public List<Integer> get_products(){return _products;}
    public void set_products(List<Integer> products){

        for (Integer i: _products)
        {
            removeProduct(i);

        }
        for (Integer i:products){
            addProduct(i);
        }

    }
    public String get_name(){return _name;}
    public void set_name(String s){
        String query= """
                UPDATE ? \s
                Set name=? \s
                WHERE \s
                storeID=? AND typeID=?;;
                """;
        List<Tuple<Object,Class>> list=prepareList(tableName,s,storeId,_typeID);
        try{
            DC.noSelect(query, list);
        }
        catch (Exception e){
            throw new IllegalArgumentException("fail");
        }
        _name=s;
    }
    public int get_minAmount(){return _minAmount;}
    public void set_minAmount(int i){
        String query= """
                UPDATE ? \s
                Set minimum=? \s
                WHERE \s
                storeID=? AND typeID=?;;
                """;
        List<Tuple<Object,Class>> list=prepareList(tableName,i,storeId,_typeID);
        try{
            DC.noSelect(query, list);
        }
        catch (Exception e){
            throw new IllegalArgumentException("fail");
        }
        _minAmount=i;
    }
    public int get_shelfCurr(){return _shelfCurr;}
    public int get_storageCurr(){return _storageCurr;}
    public float get_basePrice(){return _basePrice;}
    public void set_basePrice(float i){
        String query= """
                UPDATE ? \s
                Set basePrice=? \s
                WHERE \s
                storeID=? AND typeID=?;;
                """;
        List<Tuple<Object,Class>> list=prepareList(tableName,i,storeId,_typeID);
        try{
            DC.noSelect(query, list);
        }
        catch (Exception e){
            throw new IllegalArgumentException("fail");
        }
        _basePrice=i;
    }
    public String get_producer(){return _producer;}
    public void set_producer(String s){
        String query= """
                UPDATE ? \s
                Set producer=? \s
                WHERE \s
                storeID=? AND typeID=?;;
                """;
        List<Tuple<Object,Class>> list=prepareList(tableName,s,storeId,_typeID);
        try{
            DC.noSelect(query, list);
        }
        catch (Exception e){
            throw new IllegalArgumentException("fail");
        }
        _producer=s;
    }
    public List<Integer> get_suppliers(){
        return _suppliers;
    }

    public void addSaleProductDiscount(int i){
        List<Integer> key=new ArrayList<>();
        key.add(storeId);
        key.add(i);
        DALSaleDiscount p=(DALSaleDiscount) Mapper.getMap().getItem(DALSaleDiscount.class,key);
        p.setTypeID(_typeID);
        _saleDiscounts.add(i);
    }
    public void removeSaleDiscount(int i){
        List<Integer> key=new ArrayList<>();
        key.add(storeId);
        key.add(i);
        DALDiscount p=(DALDiscount) Mapper.getMap().getItem(DALDiscount.class,key);
        p.removeTypeID(_typeID);
        _saleDiscounts.remove(i);
    }
    public void addSupplier(int i){
        StringBuilder query=new StringBuilder("""
                INSERT INTO ? \s
                (storeID,typeID,SupplierID)\s
                VALUES (?,?,?);
                """);
        List<Tuple<Object,Class>> list=prepareList(supTable,storeId,_typeID);
        try{
            DC.noSelect(query.toString(), list);
        }
        catch (Exception e){
            throw new IllegalArgumentException("fail");
        }
        _suppliers.add(i);
    }
    public boolean productsContaions(int i){
        return _products.contains(i);
    }
    public void removeProduct(int i){
        List<Integer> key=new ArrayList<>();
        key.add(storeId);
        key.add(i);
        DALProduct p=(DALProduct) Mapper.getMap().getItem(DALProduct.class,key);
        p.removeProduct();
        _products.remove(i);
    }
    public void set_shelfCurr(int i){
        String query= """
                UPDATE ? \s
                Set shelfCurr=? \s
                WHERE \s
                storeID=? AND typeID=?;;
                """;
        List<Tuple<Object,Class>> list=prepareList(tableName,i,storeId,_typeID);
        try{
            DC.noSelect(query, list);
        }
        catch (Exception e){
            throw new IllegalArgumentException("fail");
        }
        _shelfCurr=i;
    }
    public void set_storageCurr(int i){
        String query= """
                UPDATE ? \s
                Set storageCurr=? \s
                WHERE \s
                storeID=? AND typeID=?;;
                """;
        List<Tuple<Object,Class>> list=prepareList(tableName,i,storeId,_typeID);
        try{
            DC.noSelect(query, list);
        }
        catch (Exception e){
            throw new IllegalArgumentException("fail");
        }
        _storageCurr=i;
    }
    public void addProduct(int i){
        List<Integer> key=new ArrayList<>();
        key.add(storeId);
        key.add(i);
        DALProduct p=(DALProduct) Mapper.getMap().getItem(DALProduct.class,key);
        p.addProduct(_typeID);
        _products.add(i);
    }
    private List<Tuple<Object,Class>> prepareList(Object... o){
        List<Tuple<Object,Class>> params=new ArrayList<>();
        for (Object o1:o){
            params.add(new Tuple<>(o1,o1.getClass()));
        }
        return params;
    }
    public List<Integer> getSaleDiscounts(){
        String query= """
                SELECT discountID \s
                FROM Discount \s
                WHERE storeID=? AND typeID=? AND supplierID=0;\s 
                """;
        List<Integer> list=new ArrayList<>();
        list.add(storeId);
        list.add(_typeID);
        try{
            List<Tuple<List<Class>,List<Object>>> lst=DC.SelectMany(query,list);
            return DC.SelectMany(query,list).stream().map(x->(Integer)(x.item2.get(0))).collect(Collectors.toList());
        }
        catch (Exception e){
            throw new IllegalArgumentException("fail");
        }
    }
    public int getStoreId(){
        return storeId;
    }
    public  List<Integer> getSupplierDiscounts(){
        String query= """
                SELECT discountID \s
                FROM Discount \s
                WHERE storeID=? AND typeID=?;\s 
                """;
        List<Integer> list=new ArrayList<>();
        list.add(storeId);
        list.add(_typeID);
        try{
            List<Tuple<List<Class>,List<Object>>> lst=DC.SelectMany(query,list);
            return DC.SelectMany(query,list).stream().map(x->(Integer)(x.item2.get(0))).collect(Collectors.toList());
        }
        catch (Exception e){
            throw new IllegalArgumentException("fail");
        }

    }
}
