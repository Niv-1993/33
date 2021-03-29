package BusinessLayer.instance;

import Utility.Tuple;

import java.util.Date;

public class Product {
    private int _id;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public Date get_expiration() {
        return _expiration;
    }

    public void set_expiration(Date _expiration) {
        this._expiration = _expiration;
    }

    public boolean is_isDamage() {
        return _isDamage;
    }

    public void set_isDamage(boolean _isDamage) {
        this._isDamage = _isDamage;
    }

    public Tuple<Integer, Location> get_location() {
        return _location;
    }

    public void set_location(Tuple<Integer, Location> _location) {
        this._location = _location;
    }

    private Date _expiration;
    private boolean _isDamage;
    private Tuple<Integer,Location> _location;
}
