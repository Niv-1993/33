package BusinessLayer.StockBusiness.instance;

import DAL.DalStock.DALProduct;
import DAL.Mapper;
import Utility.Tuple;
import Utility.Util;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Product {
    DALProduct dal;
    private Location _location;
    final static Logger log=Logger.getLogger(Product.class);

    public Product(int storeId,int typeID,int id, Date expiration, Tuple<Integer,Location> shelf) {
        checkLocation(shelf);
        dal= Util.initDal(DALProduct.class,storeId,id,typeID,expiration,false,shelf.item1,shelf.item2.toString());
        _location=shelf.item2;
    }

    public Product(int storeID, Integer i) {
        List<Integer> list=new ArrayList<>();
        list.add(storeID);
        list.add(i);
        dal=(DALProduct) Mapper.getMap().getItem(DALProduct.class,list);
    }

    public int get_id() {
        log.debug("get_id()");
        return dal.get_id();
    }

    public Date get_expiration()
    {
        log.debug("get_expiration()");
        return dal.get_expiration();
    }

    public boolean is_isDamage() {
        log.debug("is_isDamage()");
        return dal.is_isDamage();
    }

    public void set_isDamage() {
        log.debug(String.format("set_isDamage()"));
        if (dal.is_isDamage())
        {
            String s=String.format("the product #%d , is damage already.",dal.get_id());
            log.warn(s);
            throw new IllegalArgumentException(s);
        }
        try {
            dal.set_isDamage(true);
        }
        catch (Exception e){
            String s="can not change the value of damage";
            log.warn(s);
            throw new IllegalArgumentException(s);
        }
    }

    public Tuple<Integer, Location> get_location() {
        return new Tuple<>(dal.getShelfNum(),_location);
    }

    public void set_location(Tuple<Integer, Location> location)
    {
        log.debug(String.format("set_location(Tuple<Integer, Location> _location)"));
        checkLocation(location);
        try {
            dal.setLocation(location.item1,location.item2.toString());
        }
        catch (Exception e){
            String s="can not change the value of damage";
            log.warn(s);
            throw new IllegalArgumentException(s);
        }
        _location = location.item2;
    }
    private void checkLocation(Tuple<Integer, Location> location){
        if (location.item1<1){
            String s=String.format("the value of shelf (#%d) is illegal",location.item1);
            log.warn(s);
            throw new IllegalArgumentException(s);
        }
    }

    public int getShelf() {
        return dal.getShelfNum();
    }

    @Override
    public String toString() {
        return "Product{" +
                "_id=" + dal.get_id() +
                ", _expiration=" + dal.get_expiration() +
                ", _isDamage=" + dal.is_isDamage() +
                ", _location=" + dal.getShelfNum() +","+_location+
                '}';
    }
}
