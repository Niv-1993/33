package BusinessLayer.Type;

import org.apache.log4j.Logger;

import java.util.Date;

public abstract class Discount {
    protected int _discountID;
    protected float _percent;
    protected Date _start;
    protected Date _end;
    final static Logger log=Logger.getLogger(Discount.class);


    public Discount(int id,float percent,Date start,Date end){
        checkValue(id,percent);
        if (end.before(start) || end.before(new Date(System.currentTimeMillis()))){
            String s="the Date is illegal";
            log.warn(s);
            throw new IllegalArgumentException(s);
        }
        _discountID= id;
        _percent=percent;
        _start=start;
        _end=end;
    }
    private void checkValue(Object... o){
        String s="the value of arg is illegal";;
        for (int i = 0; i < o.length; i++) {
            if (o[i] instanceof Integer && (Integer)o[i]<1)
            {
                log.debug(s);
                throw new IllegalArgumentException(s);
            }
            if (o[i] instanceof Float && ((float)o[i]<0 | (float)o[i]>=1)) {
                log.debug(s);
                throw new IllegalArgumentException(s);
            }
        }
    }



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

    public abstract void addTo(ProductType productType);

    public abstract void removeFrom(ProductType productType);
}
