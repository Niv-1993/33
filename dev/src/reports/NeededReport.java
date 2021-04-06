package reports;

import org.apache.log4j.Logger;

import java.util.Date;
import java.util.Dictionary;

public class NeededReport implements Report{
    private Dictionary<Integer, Integer> _list;
    private int _storeID;
    private Date _date=new Date(System.currentTimeMillis());
    private final String TYPE="NeededReport";
    final static Logger log=Logger.getLogger(NeededReport.class);
    public NeededReport(int id,Dictionary<Integer, Integer> output) {
        if (output==null || id<1)
        {
            String s=String.format("the values of ? is illegal",TYPE);
            log.warn(s);
            throw new IllegalArgumentException(s);
        }
        _storeID=id;
        _list=output;
    }

    @Override
    public String getType() {
        log.debug("getType()");
        return TYPE;
    }

    @Override
    public int getStore() {
        log.debug("getStore()");
        return _storeID;
    }

    @Override
    public Date getDate() {
        log.debug("getDate()");
        return _date;
    }
    @Override
    public String toString(){
        return _list.toString();
    }
}
