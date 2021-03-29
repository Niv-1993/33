package BusinessLayer.instance;

public class Shelf {
    private int _shelfID;

    public Shelf(int _shelfID, Location _location, int _maxAmount) {
        this._shelfID = _shelfID;
        this._location = _location;
        this._maxAmount = _maxAmount;
    }

    private Location _location;
    private int _cur;
    private int _typeID;
    private int _maxAmount;

    public int get_shelfID() {
        return _shelfID;
    }

    public Location get_location() {
        return _location;
    }

    public int get_cur() {
        return _cur;
    }

    public void set_cur(int _cur) {
        this._cur = _cur;
    }

    public int get_typeID() {
        return _typeID;
    }

    public void set_typeID(int _typeID) {
        this._typeID = _typeID;
    }

    public int get_maxAmount() {
        return _maxAmount;
    }

}
