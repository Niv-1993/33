package DAL;

import DAL.DalStock.*;
import DAL.DalSuppliers.*;
import Utility.Tuple;
import org.apache.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class Mapper {

    private class IntKey{
        private List<Integer> pk;

        IntKey(List<Integer> k){pk = k;}
        @Override
        public boolean equals(Object obj) {
            if(obj != null && obj instanceof IntKey) {
                IntKey o = (IntKey)obj;
                for(int i=0;i<pk.size();i++){
                    try{
                        o.pk.get(i);
                    }catch(Exception e){
                        return false;
                    }
                    if(!o.pk.get(i).equals(pk.get(i))) return false;
                }
                return true;
            }
            return false;
        }

        @Override
        public int hashCode() {
            Integer s=0;
            for(Integer i:pk){
                s+=i;
            }
            return s.hashCode();
        }
    }

    private static List<Class> allDAL=new ArrayList<>();
    private static Mapper instance=null;
    final static Logger log=Logger.getLogger(Mapper.class);
    DalController DC;
    HashMap<Class,HashMap<IntKey, DALObject>> map;
    private Mapper(String dbname){
            DC=new DalController(dbname);
            map=new HashMap<>();
            init();
    }
    private void init(){
        // THIS NEEDS UPDATE ON EACH NEW DAL OBJECT
        allDAL.add(DALCategory.class);
        allDAL.add(DALShelf.class);
        allDAL.add(DALSaleDiscount.class);
        allDAL.add(DALSupplierDiscount.class);
        allDAL.add(DALProduct.class);
        allDAL.add(DALStoreController.class);
        allDAL.add(DALInstanceController.class);
        allDAL.add(DALProductType.class);

        allDAL.add(DalItem.class);
        allDAL.add(DalOrder.class);
        allDAL.add(DalQuantityDocument.class);
        allDAL.add(DalSupplierAgreement.class);
        allDAL.add(DalSupplierCard.class);
        allDAL.add(DalSupplierController.class);


        for(Class c: allDAL){
            try {
                Constructor con = c.getConstructor(null);
                Method cre = c.getMethod("getCreate");
                DC.noSelect((String) cre.invoke(con.newInstance()),null);
                log.info("creating "+c.getName());
            } catch (Exception e){ log.warn("Class "+c.getName()+" not created in DB");}
        }
    }

    public static Mapper getMap(String dbname) {
        if (instance == null) {
            instance = new Mapper(dbname);
        }
        return instance;
    }

    public static Mapper getMap() {
        return instance;
    }
    
    public DALObject getItem(DALObject obj, List<Integer> pk) {
        Class cls=obj.getClass();
        if(map.containsKey(cls)){
            IntKey k=new IntKey(pk);
            if(!map.get(cls).containsKey(k)){
                try {
                    String select=obj.getSelect();
                    Tuple<List<Class>,List<Object>> tup=DC.Select(select, pk);
                    DALObject out = fromRS(tup, cls);
                if(out==null) return null;
                map.get(cls).put(k, out);
                return out;
                }catch (Exception e){
                    return null;
                }
            }
            return map.get(cls).get(k);
        }
        else{
            map.put(cls, new HashMap<>());
            return getItem(obj,pk);
        }
    }

    private DALObject fromRS(Tuple<List<Class>,List<Object>> tup, Class cls) {
        Class[] tarr= new Class[tup.item1.size()];
        tarr=tup.item1.toArray(tarr);
        Object[] varr= new Object[tup.item2.size()];
        varr=tup.item2.toArray(varr);

        try {
            Constructor con = cls.getConstructor(tarr);
            DALObject out = (DALObject) con.newInstance(varr);
            return out;
        }
        catch (Exception e) {
            return null;
        }
    }
}
