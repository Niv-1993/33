package BusinessLayer.StockBusiness.Type;

import org.apache.log4j.Logger;

import java.util.Date;

public abstract class Discount {

    final static Logger log=Logger.getLogger(Discount.class);


    public Discount(int storeID,int id,double percent,Date start,Date end){
        checkValue(id,percent);
        if (end.before(start) || end.before(new Date(System.currentTimeMillis()))){
            String s="the Date is illegal";
            log.warn(s);
            throw new IllegalArgumentException(s);
        }
        init(storeID,id,percent,start,end);
    }
    public Discount(){
    }
    protected abstract void init(int storeID,int id,double percent,Date start,Date end);

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



    public abstract int get_discountID();

    public abstract float get_percent();

    public abstract Date get_start();

    public abstract Date get_end();

    public abstract void addTo(ProductType productType);

    public abstract void removeFrom(ProductType productType);

    public boolean relevant() {
        Date today=new Date();
        return today.before(get_end()) && today.after(get_start());
    }
}
