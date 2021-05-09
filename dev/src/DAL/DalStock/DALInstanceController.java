package DAL.DalStock;

import DAL.DALObject;
import DAL.DalController;
import DAL.Mapper;
import Utility.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DALInstanceController extends DALObject {
    private int _storeID;
    private int _typeID;
    private int _counter=0;
    private List<Integer> _products=new ArrayList<>();
    String tableName="InstanceController";


    public DALInstanceController(){
        super(null);
    }

    public DALInstanceController(Integer storeID, Integer typeID, Integer counter, DalController dc){
        super(dc);
        _storeID=storeID;
        _typeID=typeID;
        _counter=counter;

    } // get products from controller

    @Override
    public String getCreate() {
        return "CREATE TABLE IF NOT EXISTS InstanceController  (\n" +
                "\tstoreID INTEGER NOT NULL UNIQUE,\n" +
                "\ttypeID INTEGER NOT NULL UNIQUE,\n" +
                "\tcounter INTEGER NOT NULL,\n" +
                "\tPRIMARY KEY (storeID, typeID),\n" +
                "\tFOREIGN KEY (storeID) REFERENCES StoreController(storeID)\n" +
                "\tON DELETE CASCADE ON UPDATE CASCADE,\n" +
                "\tFOREIGN KEY (typeID) REFERENCES ProductType(typeID)\n" +
                "\tON DELETE CASCADE ON UPDATE CASCADE\n" +
                ");";
    }
    private void loadProduct(){
        String query= """
                SELECT productID \s
                FROM Product \s
                WHERE StoreID=? AND typeId=?; 
                """;
        List<Integer> list=new ArrayList<>();
        list.add(_storeID);
        list.add(_typeID);
        try {
            List<Tuple<List<Class>,List<Object>>> tmp= DC.SelectMany(query,list);
            for (Tuple<List<Class>,List<Object>> t: tmp){
                _products= tmp.stream().map(x->(int)x.item2.get(0)).collect(Collectors.toList());
            }
        }
        catch (Exception e){
            throw new IllegalArgumentException("fail");
        }
    }

    @Override
    public String getSelect() {
        return """
                SELECT * \s
                FROM ?
                WHERE StoreID=? AND typeID=?;\s
                """;
    }

    @Override
    public String getDelete() {
        return """
                DELETE FROM ? \s
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
                INSERT INTO ? \s
                VALUE(?,?,?); \s
                """;
    }
    public void removeProduct(int i){
        List<Integer> key=new ArrayList<>();
        key.add(_storeID);
        key.add(i);
        DALProduct pt=(DALProduct) Mapper.getMap().getItem(DALProduct.class,key);
        pt.removeProduct();
        _products.remove(i);
    }
    public void addProduct(int i){
        List<Integer> key=new ArrayList<>();
        key.add(_storeID);
        key.add(i);
        DALProduct pt=(DALProduct) Mapper.getMap().getItem(DALProduct.class,key);
        pt.addProduct(_typeID);
        _products.add(i);
    }
    public int get_typeID(){return _typeID;}
    public int get_counter(){return _counter;}
    public void set_counter(int i){
        String query="UPDATE ? \n" +
                "SET counter=?\n" +
                "WHERE \n" +
                "storeID=?\n" +
                "AND typeID=?;";
        List<Tuple<Object,Class>> list=new ArrayList<>();
        list.add(new Tuple<>(tableName,String.class));
        list.add(new Tuple<>(_storeID,Integer.class));
        list.add(new Tuple<>(_typeID,Integer.class));
        try {
            DC.noSelect(query, list);
        }
        catch (Exception e){
            throw new IllegalArgumentException("fail");
        }
        _counter=i;
    }
    public List<Integer> getProducts(){
        String query= """
                SELECT productID \s
                FROM Product \s
                WHERE storeID=? AND typeID=?;\s
                """;
        List<Integer> list=new ArrayList<>();
        list.add(_storeID);
        list.add(_typeID);
        try{
            List<Tuple<List<Class>,List<Object>>> lst=DC.SelectMany(query,list);
            return DC.SelectMany(query,list).stream().map(x->(Integer)(x.item2.get(0))).collect(Collectors.toList());
        }
        catch (Exception e){
            throw new IllegalArgumentException("fail");
        }
    }
    public int getStoreID(){
        return _storeID;
    }
}
