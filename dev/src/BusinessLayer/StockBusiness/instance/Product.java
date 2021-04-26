package BusinessLayer.StockBusiness.instance;

import Utility.Tuple;
import org.apache.log4j.Logger;

import java.util.Date;

public class Product {
    private int _id;
    private Date _expiration;
    private boolean _isDamage;
    private Tuple<Integer,Location> _location;
    final static Logger log=Logger.getLogger(Product.class);

    public Product(int id, Date expiration, Tuple<Integer,Location> shelf) {
    _id=id;
    _expiration=expiration;
    checkLocation(shelf);
    _location=shelf;
    }

    public int get_id() {
        log.debug("get_id()");
        return _id;
    }

    public Date get_expiration()
    {
        log.debug("get_expiration()");
        return _expiration;
    }

    public boolean is_isDamage() {
        log.debug("is_isDamage()");
        return _isDamage;
    }

    public void set_isDamage() {
        log.debug(String.format("set_isDamage()"));
        if (_isDamage)
        {
            String s=String.format("the product #%d , is damage already.",_id);
            log.warn(s);
            throw new IllegalArgumentException(s);
        }
        this._isDamage = true;
    }

    public Tuple<Integer, Location> get_location() {
        return _location;
    }

    public void set_location(Tuple<Integer, Location> location)
    {
        log.debug(String.format("set_location(Tuple<Integer, Location> _location)"));
        checkLocation(location);
        this._location = location;
    }
    private void checkLocation(Tuple<Integer, Location> location){
        if (location.item1<1){
            String s=String.format("the value of shelf (#%d) is illegal",location.item1);
            log.warn(s);
            throw new IllegalArgumentException(s);
        }
    }

    public int getShelf() {
        return _location.item1;
    }

    @Override
    public String toString() {
        return "Product{" +
                "_id=" + _id +
                ", _expiration=" + _expiration +
                ", _isDamage=" + _isDamage +
                ", _location=" + _location +
                '}';
    }
}
