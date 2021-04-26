package DAL;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;



public class Mapper {

    private class IntKey{
        private List<Integer> pk;
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


    private static Mapper instance=null;
    DalController DC;
    HashMap<Class,HashMap<Integer, DalObject>> map;
    private Mapper(String dbname){
            DC=new DalController(dbname);
            map=new HashMap<>();
            initLoop();
    }
    private void initLoop(){
        //DO CREATE IF NOT EXIST
    }

    public static Mapper getMap(String dbname) {
        if (instance == null) {
            instance = new Mapper(dbname);
        }
        return instance;
    }
    public static Mapper getMap() {
        if (instance == null) {
            instance = new Mapper("");
        }
        return instance;
    }
    
    public DalObject getItem(Class cls, Integer... pk) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        if(!map.containsKey(cls)){
            if(!map.get(cls).containsKey(pk)){
                Method m=cls.getMethod("getSelectQuery");
                String s= (String) m.invoke(null);

                map.get(cls).put(DC.Select(s,));
            }
        }
        else{
            map.put(cls, new HashMap<>());
            return getItem(cls,pk);
        }
    }
}
