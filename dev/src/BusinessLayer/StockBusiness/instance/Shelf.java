package BusinessLayer.StockBusiness.instance;

import org.apache.log4j.Logger;

public class Shelf {
    private int _shelfID;
    private Location _location;
    private int _cur;
    private int _typeID=0;
    private int _maxAmount;
    final static Logger log=Logger.getLogger(Shelf.class);

    public Shelf(int _shelfID, Location _location, int _maxAmount) {
        this._shelfID = _shelfID;
        this._location = _location;
        this._maxAmount = _maxAmount;
    }


    public int get_shelfID() {
        log.debug("get_shelfID()");
        checkTypeID();
        return _shelfID;
    }

    public Location get_location() {
        log.debug("get_location()");
        checkTypeID();
        return _location;
    }

    public int get_cur() {
        log.debug("get_cur()");
        checkTypeID();
        return _cur;
    }

    public void set_cur(int cur) {
        log.debug(String.format("set_cur(int cur)",cur));
        checkTypeID();
        if (cur>_maxAmount)
        {
            String s=String.format("the ? greater than the maximum(?) of product in this shelf ",cur,_maxAmount);
            log.debug(s);
            throw new IllegalArgumentException(s);
        }
        this._cur = cur;
    }

    public int get_typeID() {
        log.debug("get_typeID()");
        checkTypeID();
        return _typeID;
    }

    public void set_typeID(int typeID) {
        log.debug(String.format("set_typeID(int typeID)",typeID));
        checkTypeID();
        if (typeID<1){
            String s=String.format("the value of ProductType "+typeID+" is illegal (<1)");
            log.warn(s);
            throw new IllegalArgumentException(s);
        }
        this._typeID = typeID;
    }

    public int get_maxAmount() {
        log.debug("get_maxAmount()");
        checkTypeID();
        return _maxAmount;
    }

    public boolean isFull() {
        log.debug("isFull()");
        checkTypeID();
        return _cur==_maxAmount;
    }

    public void addProduct() {
        log.debug("addProduct()");
        checkTypeID();
        if (isFull())
        {
            String s=String.format("the shelf #? is full",_shelfID);
            log.warn(s);
            throw new IllegalArgumentException(s);
        }
        _cur++;
    }

    public void removeProduct() {
        log.debug("removeProduct()");
        checkTypeID();
        if (_cur==0)
        {
            String s=String.format("the shelf #? is empty",_shelfID);
            log.warn(s);
            throw new IllegalArgumentException(s);
        }
        _cur--;
        if (_cur==0)
            _typeID=0;
    }
    private void checkTypeID(){
        if (_typeID<0)
        {
            String s=String.format("the shelf #? without product type.");
            log.warn(s);
            throw new IllegalArgumentException(s);
        }
    }
}
