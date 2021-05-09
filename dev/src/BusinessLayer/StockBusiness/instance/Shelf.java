package BusinessLayer.StockBusiness.instance;

import DAL.DalStock.DALShelf;
import DAL.Mapper;
import Utility.Util;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Shelf {
    DALShelf dal;


    final static Logger log=Logger.getLogger(Shelf.class);

    public Shelf(int storeID,int _shelfID, Location _location, int _maxAmount) {
        dal=Util.initDal(DALShelf.class,storeID,_shelfID,_location.toString(),0,0,_maxAmount);
    }

    public Shelf(int id, Integer i) {
        List<Integer> list=new ArrayList<>();
        list.add(id);
        list.add(i);
        dal= (DALShelf) Mapper.getMap().getItem(DALShelf.class,list);
    }


    public int get_shelfID() {
        log.debug("get_shelfID()");
        checkTypeID();
        return dal.getID();
    }

    public Location get_location() {
        log.debug("get_location()");
        checkTypeID();
        return dal.getLocation();
    }

    public int get_cur() {
        log.debug("get_cur()");
        checkTypeID();
        return dal.getCur();
    }

    public void set_cur(int cur) {
        log.debug(String.format("set_cur(int cur)",cur));
        checkTypeID();
        if (cur>dal.getMax())
        {
            String s=String.format("the ? greater than the maximum(?) of product in this shelf ",cur,get_maxAmount());
            log.debug(s);
            throw new IllegalArgumentException(s);
        }
        dal.setCur(cur);
    }

    public int get_typeID() {
        log.debug("get_typeID()");
        checkTypeID();
        return dal.get_typeID();
    }

    public void set_typeID(int typeID) {
        log.debug(String.format("set_typeID(int typeID)",typeID));
        checkTypeID();
        if (typeID<1){
            String s=String.format("the value of ProductType "+typeID+" is illegal (<1)");
            log.warn(s);
            throw new IllegalArgumentException(s);
        }
        dal.setTypeID(typeID);
    }

    public int get_maxAmount() {
        log.debug("get_maxAmount()");
        checkTypeID();
        return dal.getMax();
    }

    public boolean isFull() {
        log.debug("isFull()");
        checkTypeID();
        return get_cur()==get_maxAmount();
    }

    public void addProduct() {
        log.debug("addProduct()");
        checkTypeID();
        if (isFull())
        {
            String s=String.format("the shelf #? is full",get_shelfID());
            log.warn(s);
            throw new IllegalArgumentException(s);
        }
        dal.setCur(dal.getCur()+1);
    }

    public void removeProduct() {
        log.debug("removeProduct()");
        checkTypeID();
        if (get_cur()==0)
        {
            String s=String.format("the shelf #? is empty",get_shelfID());
            log.warn(s);
            throw new IllegalArgumentException(s);
        }
        set_cur(get_cur()-1);

        if (get_cur()==0)
            set_typeID(0);
    }
    private void checkTypeID(){
        if (get_typeID()<0)
        {
            String s=String.format("the shelf #? without product type.");
            log.warn(s);
            throw new IllegalArgumentException(s);
        }
    }
}
