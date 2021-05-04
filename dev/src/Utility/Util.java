package Utility;

import DAL.DALObject;
import DAL.DalStock.DALCategory;
import DAL.Mapper;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public  class Util {
    final static Logger log=Logger.getLogger(Util.class);
    public static <T> T initDal(Class<T> c, int storeID, Integer key, Object... o){
        List<Tuple<Object,Class>> list=new ArrayList<>();
        list.add(new Tuple<>(storeID,Integer.class));
        list.add(new Tuple<>(key,Integer.class));
        for (Object o1: o) {
            list.add(new Tuple<>(o1,o1.getClass()));
        }
        Mapper map=Mapper.getMap();
        map.setItem(c,list);
        List<Integer> keyList=new ArrayList<>();
        keyList.add(storeID);
        keyList.add(key);
        DALObject check =map.getItem(c ,keyList);
        if (c==null || check==null ||(check.getClass()!=c)){
            String s="the instance that return from Mapper is null";
            log.warn(s);
            throw new IllegalArgumentException(s);

        }
        else{
            log.info("create new Object");
        }
        return (T) check;
    }
}
