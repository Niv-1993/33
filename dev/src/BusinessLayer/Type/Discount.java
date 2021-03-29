package BusinessLayer.Type;

import java.util.Date;

public abstract class Discount {
    protected int _discountID;
    protected float _percent;
    protected Date _start;

    public Discount(int _discountID, float _percent, Date _start, Date _end) {
        this._discountID = _discountID;
        this._percent = _percent;
        this._start = _start;
        this._end = _end;
    }

    protected Date _end;

    public int get_discountID() {
        return _discountID;
    }

    public float get_percent() {
        return _percent;
    }

    public Date get_start() {
        return _start;
    }

    public Date get_end() {
        return _end;
    }
}
