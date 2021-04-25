package BusinessLayer.instance;


import BusinessLayer.StoreController;
import Utility.Tuple;
import org.apache.log4j.Logger;

import java.util.*;

public class InstanceController {
    private int _typeID;
    private int _counter=0;
    private static int _MAX_PRODUCTS_ON_PROTUCTTYPE= StoreController.getMaxProdOnType();
    private Dictionary<Integer,Product> _products=new Hashtable<>();
    final static Logger log=Logger.getLogger(InstanceController.class);
    public InstanceController(){//for testing
        _typeID=1000;
    }

    public InstanceController(int typeID) {
        _typeID=typeID;
    }
    public InstanceController(int typeID,int max){
        _typeID=typeID;
        _MAX_PRODUCTS_ON_PROTUCTTYPE=max;
    }



    public Product removeProduct(int i) {
        log.debug(String.format("removeProduct(int i) Value: "+i));
        Product p=checkProduct(i);
        _products.remove(i);
        log.info(String.format("the IC remove Product "+i));
        return p;
    }
    private Product checkProduct(int i){
        String s;
        if (i<=0)
        {
            s=String.format("the value of i is illegal :"+i);
            log.warn(s);
            throw new IllegalArgumentException(s);
        }
        Product p=_products.get(i);
        if (p==null)
        {
            s=String.format("does not have a product with #? ID",i);
            log.warn(s);
            throw new IllegalArgumentException(s);
        }
        return p;
    }

    public void addProduct(Product p) {
        log.debug("addProduct(Product p)");
        String s;
        if (p==null)
        {
            s=String.format("the product is null");
            log.warn(s);
            throw new IllegalArgumentException(s);
        }
        if (Collections.list(_products.elements()).contains(p))
        {
            s="the product in already exist in the system";
            log.warn(s);
            throw new IllegalArgumentException(s);
        }
        _products.put(p.get_id(),p);
        log.info(String.format("new item add to IC #?",_typeID));
    }

    public int addProduct(Date expiration, Location l,int shelf) {
        log.debug("addProduct(Date expiration, Location l) Values: "+expiration+", "+l+", "+shelf);

        /*if (expiration.before(new Date(System.currentTimeMillis()))){
            String s=String.format("the value of expiration "+expiration+" before "+(new Date(System.currentTimeMillis())));
            log.warn(s);
            throw new IllegalArgumentException(s);
        }*/

        int id=_typeID*_MAX_PRODUCTS_ON_PROTUCTTYPE+_counter;
        _counter++;
        _products.put(id ,new Product(id, expiration, new Tuple<>(shelf,l)));
        return id;
    }
    public List<Integer> getProduts(){
        return Collections.list(_products.keys());
    }

    public Product reportDamage(int i) {
        log.debug(String.format("reportDamage(int i) Value:?",i));
        Product p=checkProduct(i);
        p.set_isDamage();
        return p;
    }

    public Product getProduct(int i) {
        log.debug(String.format("getProduct(int i)",i));
        return checkProduct(i);
    }

    public Dictionary<Integer, Tuple<Integer,Boolean>> getWeeklyReport() {
        log.debug("getWeeklyReport()");
        Dictionary<Integer,Tuple<Integer,Boolean>> output=new Hashtable<>();
        List<Integer> ids=Collections.list(_products.keys());
        for(int i:ids){
            output.put(i,new Tuple(_products.get(i).getShelf(),_products.get(i).get_location().equals(Location.Storage)));
        }
        return output;
    }

    public void getWasteReport(List<Integer> list) {
        log.debug("getWasteReport(List<Integer> list)");
        for(Product p: Collections.list(_products.elements())){
            if(p.is_isDamage()) {
                list.add(p.get_id());
                _products.remove(p.get_id());
            }
        }
    }
}
