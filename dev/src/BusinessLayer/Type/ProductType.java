package BusinessLayer.Type;

import BusinessLayer.instance.Location;
import BusinessLayer.instance.Product;
import Utility.Tuple;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductType {
    private int _typeID;
    private int _categoryID;
    private List<Integer> _products;
    private String _name;
    private int _minAmount;
    private int _shelfCurr=0;
    private int _storageCurr=0;
    private float _basePrice;
    private float _salePrice;
    private String _producer;
    private List<Integer> _suppliers;
    private List<SaleDiscount> _saleDiscounts;
    private List<SupplierDiscount> _supplierDiscounts;
    final static Logger log=Logger.getLogger(ProductType.class);



    public ProductType(int _typeID, int _categoryID, String _name, int _minAmount, float _basePrice, float _salePrice, String _producer, List<Integer> _suppliers) {
        checkValues(_typeID,  _categoryID,  _name, _minAmount, _basePrice, _salePrice, _producer, _suppliers);
        this._typeID = _typeID;
        this._categoryID = _categoryID;
        this._name = _name;
        this._minAmount = _minAmount;
        this._basePrice = _basePrice;
        this._salePrice = _salePrice;
        this._producer = _producer;
        this._suppliers=_suppliers;
        _products=new ArrayList<>();
        _saleDiscounts=new ArrayList<>();
        _supplierDiscounts=new ArrayList<>();
    }

    private void checkValues(Object... o){
        String s;
        for (int i = 0; i < o.length; i++) {
            if (o[i] instanceof String)
            {
                if (o[i]==null || o[i].equals(""))
                {
                    s="the value is illegal(String)";
                    log.warn(s);
                    throw new IllegalArgumentException(s);
                }
            }
            else if (o[i] instanceof Integer)
            {
                if ((Integer)o[i]<0)
                {
                    s="the value is illegal(String)";
                    log.warn(s);
                    throw new IllegalArgumentException(s);
                }
            }
            else
                log.warn("the checkValue get unanimous value");
        }


    }


    public ProductType(int i, String name, int minAmount, float basePrice, String producer, int supID, int category) {
        checkValues(i,name, minAmount, basePrice, producer,supID,category);
        _typeID=i;
        _name=name;
        _minAmount=minAmount;
        _basePrice=basePrice;
        _producer=producer;
        _suppliers=new ArrayList<>();
        _suppliers.add(supID);
        _categoryID=category;
        _saleDiscounts=new ArrayList<>();
        _supplierDiscounts=new ArrayList<>();
    }

    public int get_typeID() {
        return _typeID;
    }


    public int get_categoryID() {
        return _categoryID;
    }

    public void set_categoryID(int _categoryID) {
        this._categoryID = _categoryID;
    }

    public List<Integer> get_products() {
        return _products;
    }

    public void set_products(List<Integer> _products) {
        this._products = _products;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public int get_minAmount() {
        return _minAmount;
    }

    public void set_minAmount(int _minAmount) {
        this._minAmount = _minAmount;
    }

    public int get_shelfCurr() {
        return _shelfCurr;
    }

    public void set_shelfCurr(int _shelfCurr) {
        this._shelfCurr = _shelfCurr;
    }

    public int get_storageCurr() {
        return _storageCurr;
    }

    public void set_storageCurr(int _storageCurr) {
        this._storageCurr = _storageCurr;
    }

    public float get_basePrice() {
        return _basePrice;
    }

    public void set_basePrice(float _basePrice) {
        this._basePrice = _basePrice;
    }

    public float get_salePrice() {
        return _salePrice;
    }

    public void set_salePrice(float _salePrice) {
        this._salePrice = _salePrice;
    }

    public String get_producer() {
        return _producer;
    }

    public void set_producer(String _producer) {
        this._producer = _producer;
    }

    public List<Integer> get_suppliers() { return _suppliers;    }

    public void set_suppliers(List<Integer> _suppliers) { this._suppliers = _suppliers; }


    public void addSaleProductDiscount(int discountID,float percent, Date start, Date end) {
        log.debug(String.format("addSaleProductDiscount(float percent, Date start, Date end) Values:?,?,?",percent,start,end));
        _saleDiscounts.add(new SaleDiscount(discountID,percent,start,end));
    }

    public void addSaleProductDiscount(Discount discount) {
        log.debug(String.format("addSaleProductDiscount(Discount discount)"));
        discount.addTo(this);
    }

    public void addDiscount(SaleDiscount discount){
        _saleDiscounts.add(discount);
    }
    public void addDiscount(SupplierDiscount discount){
        _supplierDiscounts.add(discount);
    }
    public void addDiscount(Discount discount){
        String s="the function get illegal arg";
        log.error(s);
        throw new IllegalArgumentException(s);
    }

    public void removeDiscount(Discount discount) {
        log.debug(String.format("removeDiscount(Discount discount)"));
        discount.removeFrom(this);
    }
    public void removeDiscountFromList(SaleDiscount s){
        _saleDiscounts.remove(s);
    }
    public void removeDiscountFromList(SupplierDiscount s){
        _supplierDiscounts.remove(s);
    }
    public void removeDiscountFromList(Discount s){
        String str="the function get illegal arg";
        log.error(str);
        throw new IllegalArgumentException(str);
    }

    public List<SupplierDiscount> getSupplierDiscounts() {
        return _supplierDiscounts;
    }

    public List<SaleDiscount> getSalePrice() {
        return _saleDiscounts;
    }

    public void edit(String name, int minAmount, float basePrice, String producer, int supID, int category) {
        log.debug(String.format("edit(String name, int minAmount, float basePrice, String producer, int supID," +
                " int category) Value:?,?,?,?,?,?" ,name,minAmount,basePrice,producer,supID,category));
        checkValues(name,minAmount,basePrice,producer,supID,category);
        set_basePrice(basePrice);
        set_name(name);
        set_categoryID(category);
        set_minAmount(minAmount);
        set_producer(producer);
        _suppliers.add(supID);
    }

    public void removeProduct(int productID, Tuple<Integer, Location> location) {
        log.debug(String.format("removeProduct(int productID, Tuple<Integer, Location> location) Values: ?,?",productID,location));
        if (!_products.contains(productID)){
            String s=String.format("the product #? , is not exist in the ProductType List.",productID);
            log.error(s);
            throw new IllegalArgumentException(s);
        }
        if ((location.item2.equals(Location.Shelves)&&_shelfCurr<=0)|(location.item2.equals(Location.Storage)&&_storageCurr<=0)){
            String s=String.format("the product #? , is not accept to the value in ProductType.",productID);
            log.error(s);
            throw new IllegalArgumentException(s);
        }
        _products.remove(productID);
        if ((location.item2.equals(Location.Storage))) {
            _shelfCurr--;
        } else {
            _storageCurr--;
        }
    }

    public void addProduct(int productID, Location l) {
        log.debug(String.format("addProduct(int productID, Location l) Values: ?,?",productID,l));
        if (_products.contains(productID)){
            String s=String.format("the product #? , is not exist in the ProductType List.",productID);
            log.error(s);
            throw new IllegalArgumentException(s);
        }
        _products.add(productID);
        if ((l.equals(Location.Storage))) {
            _shelfCurr++;
        } else {
            _storageCurr++;
        }
    }

    public void relocateProduct(boolean toStorage) {
        log.debug("relocateProduct()");
        if ((toStorage && _shelfCurr<=0) ||(!toStorage && _storageCurr<=0))
        {
            String s="no have product in this place.";
            log.warn(s);
            throw new IllegalArgumentException(s);
        }
        if (toStorage){
            _storageCurr++;
            _shelfCurr--;
        }
        else
        {
            _storageCurr--;
            _shelfCurr++;
        }
    }

    public void reportDamage(Product i) {
        log.debug(String.format("reportDamage(int i) Value:?",i));
        if (!_products.contains(i.get_id())){
            String s=String.format("the ID of product is not exist in system");
            log.warn(s);
            throw new IllegalArgumentException(s);
        }
        if ((i.get_location().item2.equals(Location.Storage)&& _storageCurr<=0)||(i.get_location().item2.equals(Location.Shelves)&& _shelfCurr<=0)){
            String s="no have product in this place.";
            log.warn(s);
            throw new IllegalArgumentException(s);
        }
        if (i.get_location().item2.equals(Location.Storage)) {
            _storageCurr--;
        }
        else
            _shelfCurr--;
    }

    public Integer getNeededReport() {
        log.debug("getNeededReport()");
        return (_minAmount<=_storageCurr+_shelfCurr)? 0: _minAmount-_shelfCurr-_storageCurr;
    }
}
